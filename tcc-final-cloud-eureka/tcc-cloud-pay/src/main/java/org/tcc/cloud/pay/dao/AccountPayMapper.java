package org.tcc.cloud.pay.dao;

import org.apache.ibatis.annotations.Param;
import org.tcc.cloud.pay.dao.AccountPay;

public interface AccountPayMapper {

	   public AccountPay getByAccountNo(@Param("accountNo") String accountNo);
   
       public Integer save(AccountPay model);

       public Integer updateByVer(AccountPay model);
 
}
