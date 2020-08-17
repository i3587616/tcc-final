package cn.linc.tccfinal.core.transaction;

import java.lang.reflect.Method;

import cn.linc.tccfinal.core.aspect.TccAspectContext;

/**
 * 分布式事务业务参与者
 * @author chang.lin
 *
 */
@SuppressWarnings("serial")
public class BsActor implements java.io.Serializable{
	
	private transient Object target;

	private String confirmMethod;
	
	private String cancelMethod;
	
	private String interfaceName;
	
	private transient String tccId;//不需要序列化字段
	
	private transient Method method;//不需要序列化字段
	
	public BsActor(Object target,TccAspectContext tccContext){
		this.target = target;
		
		TccTransaction tcc = tccContext.getTcc();
		this.confirmMethod = tcc.confirmMethod();
		this.cancelMethod = tcc.cancelMethod();
		this.method = tccContext.getMethod();
		this.interfaceName = tccContext.getInterfaceName();
		
		//如果没有写明confirm或cancel方法，默认方法名为try的方法名首字母大写在加上confirm或cancel
		StringBuffer sb;	
		if("".equals(this.confirmMethod)){
			sb = new StringBuffer("confirm");
			sb.append(upperFirstCase(this.method.getName()));
			this.confirmMethod = sb.toString();
		}
		if("".equals(this.cancelMethod)){
			sb = new StringBuffer("cancel");
			sb.append(upperFirstCase(this.method.getName()));
			this.cancelMethod = sb.toString();
		}
	}
	
	/**
	 * 将字符串首字母大写
	 * @param str
	 * @return
	 */
	private String upperFirstCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
		ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
	
	public void confirm()throws Throwable{
		this.invokeMethod(this.confirmMethod);
	}
	
	public void cancel()throws Throwable{
		this.invokeMethod(this.cancelMethod);
	}
	
	private void invokeMethod(String mName)throws Throwable{
		
		Method m = target.getClass().getMethod(mName, String.class);
		
		m.invoke(target, tccId);
	}

	public void setTccId(String tccId) {
		this.tccId = tccId;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public String getInterfaceName() {
		return interfaceName;
	}
	
	
}
