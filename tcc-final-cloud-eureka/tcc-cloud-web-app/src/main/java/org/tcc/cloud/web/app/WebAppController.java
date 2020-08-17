package org.tcc.cloud.web.app;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tcc.cloud.web.app.ucc.IWebAppUcc;


@RestController
public class WebAppController {
	
	@Autowired
	private IWebAppUcc webAppUcc;
	
	@RequestMapping("/payForSomething/{amount}")
	public Object payForSomething(@PathVariable BigDecimal amount){
		
		long time =System.nanoTime();
		String tccId =String.valueOf(time);//tccId使用全分布系统唯一，这里测试方便，随便取的
		
		try{
			webAppUcc.doBusiness("20100001", amount, tccId);
		}catch(Throwable e){
			e.printStackTrace();//这里是测试用例方便
		}
		
		return "success";
		
	}

}
