package cn.linc.tccfinal.core.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 这个注解只能用于接口方法
 * @author chang.lin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TccTransaction {

	public boolean ucc() default false;//这个事务是否Ucc事务，默认非
	
	public int ver() default 0;//版本号默认0。当UCC事务有发生变动时，更改版本号使得重新记录整个UCC的BS参与者
	
	public String confirmMethod() default "";
	
	public String cancelMethod() default "";

}