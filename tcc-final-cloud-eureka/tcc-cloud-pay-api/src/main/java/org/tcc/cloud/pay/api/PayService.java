package org.tcc.cloud.pay.api;

import java.math.BigDecimal;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linc.tccfinal.core.transaction.TccTransaction;

/**
 * 支付服务（测试例子，简单化处理)
 * @author chang.lin
 *
 */
@FeignClient(value = "tcc-cloud-pay", fallback = PayServiceHystrix.class)
public interface PayService {
	
	@RequestMapping(value = "/pay",method = RequestMethod.POST)
	@TccTransaction
	public void pay(@RequestParam(value = "accountNo") String accountNo,
			@RequestParam(value = "money") BigDecimal money,@RequestParam(value = "tccId") String tccId);
	
	@RequestMapping(value = "/confirmPay",method = RequestMethod.POST)
	public void confirmPay(@RequestParam(value = "tccId") String tccId);
	
	@RequestMapping(value = "/cancelPay",method = RequestMethod.POST)
	public void cancelPay(@RequestParam(value = "tccId") String tccId);
}
