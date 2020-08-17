package org.tcc.cloud.pay.dao;

import org.apache.ibatis.annotations.Param;
import org.tcc.cloud.pay.dao.AccountPayTccfinal;

public interface AccountPayTccfinalMapper {

	   public AccountPayTccfinal getByTccId(@Param("tccId") String tccId);

       public Integer save(AccountPayTccfinal model);
   
       public Integer updateByVer(AccountPayTccfinal model);
 
}
