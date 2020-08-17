package cn.linc.tccfinal.core.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.linc.tccfinal.consts.TccStatus;
import cn.linc.tccfinal.core.dao.TccDao;
import cn.linc.tccfinal.core.transaction.BsActor;
import cn.linc.tccfinal.core.transaction.UccActor;
import cn.linc.tccfinal.core.transaction.TccTransaction;
import cn.linc.tccfinal.core.util.KryoUtil;
import cn.linc.tccfinal.core.util.TccException;

/**
 * 切面处理类
 * @author chang.lin
 *
 */
@Aspect
@Component
public class TccAspect {
	
	private final ThreadLocal<UccActor> uccActors = new ThreadLocal<UccActor>();
	private final Map<String,String> uccSigns = new HashMap<String,String>();
	private final String exist = "1";
	
    @Autowired
	private TccDao tccDao;
	
    @Pointcut("@annotation(cn.linc.tccfinal.core.transaction.TccTransaction)")
    public void tccTransaction() {
    	
    }
    
    
    /**
     * 切面处理tcc事务的方法
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("tccTransaction()")
    public Object tccMethod(ProceedingJoinPoint pjp) throws Throwable {
    	
    	TccAspectContext tccContext = new TccAspectContext(pjp);
    	TccTransaction tcc = tccContext.getTcc();
    
    	Object rtn = null;
    	
    	if(tcc.ucc()){
    		rtn = uccProceed(pjp,tccContext);
    	}else{
    		rtn = bsProceed(pjp,tccContext);
    	}
 
    	return rtn;
    	
    }
    

    
	private Object uccProceed(ProceedingJoinPoint pjp,TccAspectContext tccContext) throws Throwable {

		Object rtn = null;

		int tccVer = tccContext.getTcc().ver();
		String tccId = tccContext.tccId();
		String uccSign = tccContext.uccSign();
		tccDao.uccInsert(uccSign, tccId, tccVer);

		UccActor uccActor = new UccActor();
		uccActor.setTccId(tccId);
		uccActors.set(uccActor);
		try {
			try {
				rtn = pjp.proceed(pjp.getArgs());
			} catch (Throwable e) {

				//tccDao.uccStatusUpdate(TccStatus.CANCEL.getStatus(), tccId);不是confirm那么SureDoJob就执行cancel
				uccActor.cancel();
				tccDao.uccDelete(tccId);
				throw e;
			}

			this.setExists(uccSign, tccVer);// 执行到这就算走完全部流程了
			
			/**
			int test = 1;
			if(test ==1){
				throw new RuntimeException("test 非正常结束，由sureJob执行cancel");
			}
			**/
			
			tccDao.uccStatusUpdate(TccStatus.CONFIRM.getStatus(), tccId);
			
			/**
			int test = 1;
			if(test ==1){
				throw new RuntimeException("test 非正常结束，由sureJob执行confirm");
			}
			**/
			
			uccActor.confirm();
			tccDao.uccDelete(tccId);
		} finally {
			uccActors.remove();
		}

		return rtn;

	}
    
    private Object bsProceed(ProceedingJoinPoint pjp,TccAspectContext tccContext)throws Throwable{
    	
    	UccActor uccActor = uccActors.get();
    	
    	if(uccActor==null){
    		throw new TccException(TccException.code(TccException.UCC_UNEXIST));
    	}
    	
    	Object rtn = null;
    	
    	//只有分布式业务处于TRY阶段，需要加入参与者，其它阶段则是不正常的，直接返回，不执行pjp.proceed
    	if(uccActor.getStatus() == TccStatus.TRY){
    		
    		BsActor bsActor = new BsActor(pjp.getTarget(),tccContext);
    		bsActor.setTccId(uccActor.getTccId());
    		uccActor.addBsActor(bsActor);
    		
    		rtn = pjp.proceed(pjp.getArgs());

    	}
    	
    	return rtn;
    	
    }
    
    /**
     * uccSign标记的一个Ucc全部try bs走完，如未做记录，则做上记录
     * @param uccSign
     */
    private void setExists(String uccSign,int ver){
    	
    	if(uccSigns.get(uccSign)==null){
    		synchronized(this.getClass()){
    			if(uccSigns.get(uccSign)==null){
    				
    				uccSigns.put(uccSign, this.exist);
    				UccActor uccActor = uccActors.get();
    				try{
    					if(tccDao.hasUcc(uccSign, ver)){
    						return ;
    					}
    					
    					byte[] bsActors = KryoUtil.getKryoUtil().serialize(uccActor.getBsActors());
    					tccDao.bsInsert(uccSign, bsActors,ver);
    				}catch(Throwable e){
    					uccSigns.remove(uccSign);
    					e.printStackTrace();
    				}
    				
    			}
    		}
    	}
    }
}
