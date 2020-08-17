package org.tcc.cloud.pay.api;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class PayServiceHystrix implements PayService{

	@Override
	public void pay(String accountNo, BigDecimal money, String tccId) {
		throw new RuntimeException("payserviceHystric try problem");
	}

	@Override
	public void confirmPay(String tccId) {
		throw new RuntimeException("payserviceHystric confirm problem");
		
	}

	@Override
	public void cancelPay(String tccId) {
		throw new RuntimeException("payserviceHystric cancel problem");
		
	}

}
