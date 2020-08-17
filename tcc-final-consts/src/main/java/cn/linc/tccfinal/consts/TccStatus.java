package cn.linc.tccfinal.consts;

/**
 * tcc分布式事务状态
 * @author chang.lin
 *
 */
public enum TccStatus {

	
	TRY(0), CONFIRM(1), CANCEL(2);

	private Integer status;
	
	TccStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

}
