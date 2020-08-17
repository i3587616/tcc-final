package org.tcc.cloud.points.dao;

import org.apache.ibatis.annotations.Param;
import org.tcc.cloud.points.dao.AccountPointsTccfinal;

public interface AccountPointsTccfinalMapper {

	   public AccountPointsTccfinal getByTccId(@Param("tccId") String tccId);
   
       public Integer save(AccountPointsTccfinal model);
   
       public Integer updateByVer(AccountPointsTccfinal model);
 
}
