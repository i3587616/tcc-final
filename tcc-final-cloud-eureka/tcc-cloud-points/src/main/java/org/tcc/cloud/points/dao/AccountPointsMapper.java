package org.tcc.cloud.points.dao;

import org.apache.ibatis.annotations.Param;
import org.tcc.cloud.points.dao.AccountPoints;

public interface AccountPointsMapper {

	   public AccountPoints getByAccountNo(@Param("accountNo") String accountNo);
   
       public Integer save(AccountPoints model);
   
       public Integer updateByVer(AccountPoints model);
 
}
