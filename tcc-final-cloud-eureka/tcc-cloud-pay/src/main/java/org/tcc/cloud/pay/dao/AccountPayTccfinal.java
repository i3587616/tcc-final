package org.tcc.cloud.pay.dao;

/** 
 * 表名： 账户资金表
 */
@SuppressWarnings("serial")
public class AccountPayTccfinal implements java.io.Serializable{

	/**
	 * tcc账户资金业务记录表Id
	 */
	private Integer id;
	
	
	/**
	 * 支付账号
	 */
	private String accountNo;
	
	
	/**
	 * 支付金额
	 */
	private java.math.BigDecimal payAmount;
	
	
	/**
	 * tcc事务ID
	 */
	private String tccId;
	
	
	/**
	 * 版本号
	 */
	private Integer version;
	
	
	/**
	 * 业务状态：0-try，1-confirm，2-cancel
	 */
	private Integer status;
	
	
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	
	
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
	}
	
	
    public String getAccountNo() {
        return accountNo;
    }
    
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
	}
	
	
    public java.math.BigDecimal getPayAmount() {
        return payAmount;
    }
    
    public void setPayAmount(java.math.BigDecimal payAmount) {
        this.payAmount = payAmount;
	}
	
	
    public String getTccId() {
        return tccId;
    }
    
    public void setTccId(String tccId) {
        this.tccId = tccId;
	}
	
	
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
	}
	
	
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
	}
	
	
    public java.util.Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
	}
	
	
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
	}
	
	
}