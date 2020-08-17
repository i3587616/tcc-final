package org.tcc.springcloud.starter;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.reflect.PerClauseKind;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AspectJAdvisorFactory;
import org.springframework.aop.aspectj.annotation.AspectMetadata;
import org.springframework.aop.aspectj.annotation.BeanFactoryAspectInstanceFactory;
import org.springframework.aop.aspectj.annotation.MetadataAwareAspectInstanceFactory;
import org.springframework.aop.aspectj.annotation.PrototypeAspectInstanceFactory;
import org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import cn.linc.tccfinal.core.transaction.TccTransaction;

@Component
public class TccProcessor implements BeanPostProcessor, BeanFactoryAware{
	
	private BeanFactory beanFactory;
	private AspectJAdvisorFactory aspectJAdvisorFactory;
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		Class<?>[] interfaces = clazz.getInterfaces();
		
		NameMatchMethodPointcutAdvisor tccAdvisor = null;
		
		for(Class<?> api : interfaces){
			Method[] methods = api.getDeclaredMethods();
			for(Method m :methods){
				if(m.isAnnotationPresent(TccTransaction.class)){
					FeignClient feignAnno = api.getAnnotation(FeignClient.class);
					if(feignAnno!=null){
						if(clazz==feignAnno.fallback()){
							return bean;//断链器有可能继承分布式业务接口，排除掉
						}
					}
					
					try{
					//@TccTransaction只应放在接口上，如果实现类某方法上出现了这个注解，这跳过该方法，不需要增强
						Method m2 = clazz.getMethod(m.getName(), m.getParameterTypes());
						if((m2.isAnnotationPresent(TccTransaction.class))){
							continue;
						}
					}catch(NoSuchMethodException e){
						//接口实现类不可能不存在接口的方法
					}
					
					if(tccAdvisor == null){
						tccAdvisor = new NameMatchMethodPointcutAdvisor();
						Advisor advisor = getTccAdvisor();
						tccAdvisor.setAdvice(advisor.getAdvice());
					}
					tccAdvisor.addMethodName(m.getName());
				}
			}
		}
		
		if(tccAdvisor!=null){
			ProxyFactory proxyFactory = new ProxyFactory(bean);
			proxyFactory.addAdvisor(tccAdvisor);
			return proxyFactory.getProxy();
		}
		
		return bean;
	}

	private Advisor getTccAdvisor(){
		
		MetadataAwareAspectInstanceFactory factory ;
		
		String beanName = "tccAspect";
		Class<?> beanType = this.beanFactory.getType(beanName);
		AspectMetadata amd = new AspectMetadata(beanType, beanName);
		if (amd.getAjType().getPerClause().getKind() == PerClauseKind.SINGLETON) {
			factory = new BeanFactoryAspectInstanceFactory(this.beanFactory, beanName);
		
		}else {
			if (this.beanFactory.isSingleton(beanName)) {
				throw new IllegalArgumentException("Bean with name '" + beanName +
						"' is a singleton, but aspect instantiation model is not singleton");
			}
		    factory = new PrototypeAspectInstanceFactory(this.beanFactory, beanName);
		}
		List<Advisor> classAdvisors = this.aspectJAdvisorFactory.getAdvisors(factory);
		
		if(classAdvisors == null || classAdvisors.isEmpty()){
			throw new IllegalArgumentException("Aspect Bean with name tccAspect unexists");
		}
		
		return classAdvisors.get(0);
		
	}

	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException{
		
		this.beanFactory = beanFactory;	
		if (this.aspectJAdvisorFactory == null) {
			this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
		}
	}



	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
}
