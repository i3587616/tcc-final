package org.tcc.cloud.web.app.ucc;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tcc.cloud.pay.api.PayService;
import org.tcc.cloud.points.api.PointsService;

@Service
public class WebAppUcc implements IWebAppUcc{

	
	@Autowired
	PayService payService;
	
	@Autowired
	PointsService pointsService;
	
	public void doBusiness(String accountNo, BigDecimal money, String tccId){
		//tcc全局事务ID,全分部式系统内唯一，通常传入业务主键做为tccId
		//money分别传入10和600，测试try完成全功走通全程confirm, money=600时失败，全部cancel
		this.pointsService.addPoints(accountNo, 100, tccId);
		this.payService.pay(accountNo, money, tccId);	
	}
	

}
