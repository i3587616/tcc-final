package cn.linc.tccfinal.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import cn.linc.tccfinal.core.transaction.TccId;
import cn.linc.tccfinal.core.transaction.TccTransaction;

/**
 * 切面上下文
 * @author chang.lin
 *
 */
public class TccAspectContext {
	
	private ProceedingJoinPoint pjp ;
	
	private Method method;//try方法

	private TccTransaction tcc;//try方法上的TccTransaction注解
	
	private String interfaceName;
	
	public TccAspectContext(ProceedingJoinPoint pjp){
		this.pjp = pjp;
		this.method = getTccTransactionMethod();
		this.tcc = method.getAnnotation(TccTransaction.class);
	}
	
	
    /**
     * 得到注释@TccAspect的接口方法
     * @param pjp
     * @return
     */
    private Method getTccTransactionMethod() {
    	MethodSignature ms = ((MethodSignature) (pjp.getSignature()));
    	this.interfaceName = ms.getDeclaringTypeName();
        Method method = ms.getMethod();
        return method;
    }

    /**
     * 得到注释@TccAspect的接口名称
     * @param pjp
     * @return
     */
    public String getInterfaceName() {
    	 return this.interfaceName;
    }

	public Method getMethod() {
		return method;
	}


	public TccTransaction getTcc() {
		return tcc;
	}
    
	/**
	 * 用于唯 一标识ucc方法
	 * @return
	 */
	public String uccSign(){
		StringBuilder sb = new StringBuilder(pjp.getTarget().getClass().getCanonicalName());
		sb.append(method.getName());
		for(Class<?> arg : method.getParameterTypes()){
			sb.append(arg.getSimpleName());
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取切面里的tccId参数
	 * @return
	 */
	public String tccId(){
		
        Annotation[][] annotations = this.getMethod().getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation.annotationType().equals(TccId.class)) {

                    Object[] params = pjp.getArgs();
                    Object tccId = params[i];

                    return tccId.toString();
                }
            }
        }
        return null;
	}
}
