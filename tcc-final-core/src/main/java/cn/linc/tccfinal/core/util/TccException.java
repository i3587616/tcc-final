package cn.linc.tccfinal.core.util;

public class TccException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int UCC_UNEXIST=0;//ucc不存在
	
	public TccException(String msg){		
		super(msg);
	}

	public static String code(int code){
		
		String msg = "tcc事务异常";
		
		switch(code){
		
			case UCC_UNEXIST:
				msg = "ucc不存在";
				break;
			default :
				break;
		}
		
		return msg;
	}
}
