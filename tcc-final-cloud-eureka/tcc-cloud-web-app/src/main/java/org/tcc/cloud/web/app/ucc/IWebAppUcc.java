package org.tcc.cloud.web.app.ucc;

import java.math.BigDecimal;

import cn.linc.tccfinal.core.transaction.TccId;
import cn.linc.tccfinal.core.transaction.TccTransaction;

public interface IWebAppUcc {

	//@TccTransaction(ucc = true,ver = 1)//测试版本号变更会生成新的tcc_bs
	@TccTransaction(ucc = true)
	public void doBusiness(String accountNo, BigDecimal money, @TccId String tccId);
}
