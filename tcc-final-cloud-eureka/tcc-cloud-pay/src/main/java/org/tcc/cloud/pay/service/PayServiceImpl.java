package org.tcc.cloud.pay.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tcc.cloud.pay.dao.AccountPay;
import org.tcc.cloud.pay.dao.AccountPayMapper;
import org.tcc.cloud.pay.dao.AccountPayTccfinal;
import org.tcc.cloud.pay.dao.AccountPayTccfinalMapper;
import cn.linc.tccfinal.consts.TccStatus;

@RestController
@Transactional
public class PayServiceImpl{

	@Autowired
	AccountPayMapper accountPayMapper;
	
	@Autowired
	AccountPayTccfinalMapper accountPayTccfinalMapper;

	@RequestMapping(value = "/pay",method = RequestMethod.POST)
	public void pay(String accountNo, BigDecimal money, String tccId) {
		AccountPayTccfinal modelTcc = accountPayTccfinalMapper.getByTccId(tccId);
		if(modelTcc != null){
			return ;//幂等
		}
		
		AccountPay model = accountPayMapper.getByAccountNo(accountNo);
		BigDecimal balance = model.getAmount();
		if(balance.compareTo(money) == -1){
			throw new RuntimeException("账户余额不足");
		}
		model.setAmount(balance.subtract(money));
		accountPayMapper.updateByVer(model);
		
		modelTcc = new AccountPayTccfinal();
		modelTcc.setAccountNo(accountNo);
		modelTcc.setPayAmount(money);
		modelTcc.setTccId(tccId);
		modelTcc.setStatus(TccStatus.TRY.getStatus());
		accountPayTccfinalMapper.save(modelTcc);
	}

	@RequestMapping(value = "/confirmPay",method = RequestMethod.POST)
	public void confirmPay(String tccId) {
		AccountPayTccfinal modelTcc = accountPayTccfinalMapper.getByTccId(tccId);
		if(modelTcc == null){
			throw new RuntimeException("业务TCC记录不存在:" + tccId);
		}
		if(modelTcc.getStatus() == TccStatus.CONFIRM.getStatus()){
			return ;
		}
		modelTcc.setStatus(TccStatus.CONFIRM.getStatus());
		accountPayTccfinalMapper.updateByVer(modelTcc);
	}

	@RequestMapping(value = "/cancelPay",method = RequestMethod.POST)
	public void cancelPay(String tccId) {
		AccountPayTccfinal modelTcc = accountPayTccfinalMapper.getByTccId(tccId);
		if(modelTcc == null){
			return ;
		}
		
		if(modelTcc.getStatus() == TccStatus.CANCEL.getStatus()){
			return ;
		}
		
		AccountPay model = accountPayMapper.getByAccountNo(modelTcc.getAccountNo());
		model.setAmount(modelTcc.getPayAmount().add(model.getAmount()));
		accountPayMapper.updateByVer(model);
		
		modelTcc.setStatus(TccStatus.CANCEL.getStatus());
		accountPayTccfinalMapper.updateByVer(modelTcc);
	}
}
