package org.tcc.cloud.points.dao;

/** 
 * 表名： 账户积分表
 */
@SuppressWarnings("serial")
public class AccountPointsTccfinal implements java.io.Serializable{

	/**
	 * tcc账户积分业务记录表Id
	 */
	private Integer id;
	
	
	/**
	 * 用户账号
	 */
	private String accountNo;
	
	
	/**
	 * 本次业务获得积分
	 */
	private Integer points;
	
	
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
	
	
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
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