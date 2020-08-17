package cn.linc.tccfinal.core.transaction;

import java.util.LinkedList;
import java.util.List;

import cn.linc.tccfinal.consts.TccStatus;

/**
 * ucc参与者
 * @author chang.lin
 *
 */
public class UccActor {
	
	private String tccId;
	
	private TccStatus status;
	
	private List<BsActor> bsActors;//bs业务参与者列表
	
	public List<BsActor> getBsActors() {
		return bsActors;
	}

	public UccActor(){
		this.status = TccStatus.TRY;
		this.bsActors = new LinkedList<BsActor>();
	}

	public TccStatus getStatus() {
		return status;
	}

	public void setStatus(TccStatus status) {
		this.status = status;
	}

	public void addBsActor(BsActor bsActor){
		this.bsActors.add(bsActor);
	}
	
	public void confirm()throws Throwable{
		status = TccStatus.CONFIRM;
		for(BsActor bsActor : bsActors){
			bsActor.confirm();
		}
	}
	
	public void cancel()throws Throwable{
		status = TccStatus.CANCEL;
		for(BsActor bsActor : bsActors){
			bsActor.cancel();
		}
	}

	public String getTccId() {
		return tccId;
	}

	public void setTccId(String tccId) {
		this.tccId = tccId;
	}
}
