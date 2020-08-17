package org.tcc.cloud.pay.dao;

/** 
 * 表名： 账户资金表
 */
@SuppressWarnings("serial")
public class AccountPay implements java.io.Serializable{

	/**
	 * 支付账号ID
	 */
	private Integer id;
	
	
	/**
	 * 用户账号
	 */
	private String accountNo;
	
	
	/**
	 * 账户金额
	 */
	private java.math.BigDecimal amount;
	
	
	/**
	 * 版本号
	 */
	private Integer version;
	
	
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
	
	
    public java.math.BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
	}
	
	
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
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