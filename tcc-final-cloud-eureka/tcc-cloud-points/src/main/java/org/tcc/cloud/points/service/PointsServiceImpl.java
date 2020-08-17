package org.tcc.cloud.points.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tcc.cloud.points.dao.AccountPoints;
import org.tcc.cloud.points.dao.AccountPointsMapper;
import org.tcc.cloud.points.dao.AccountPointsTccfinal;
import org.tcc.cloud.points.dao.AccountPointsTccfinalMapper;
import cn.linc.tccfinal.consts.TccStatus;

@RestController
@Transactional
public class PointsServiceImpl{

	@Autowired
	AccountPointsMapper accountPointsMapper;
	
	@Autowired
	AccountPointsTccfinalMapper accountPointsTccfinalMapper;
	
	@RequestMapping(value = "/addPoints",method = RequestMethod.POST)
	public void addPoints(String accountNo, Integer  addNum, String tccId) {
		
		AccountPointsTccfinal modelTcc = accountPointsTccfinalMapper.getByTccId(tccId);
		if(modelTcc != null){
			return ;//幂等
		}
		
		modelTcc = new AccountPointsTccfinal();
		modelTcc.setAccountNo(accountNo);
		modelTcc.setTccId(tccId);
		modelTcc.setPoints(addNum);
		modelTcc.setStatus(TccStatus.TRY.getStatus());
		accountPointsTccfinalMapper.save(modelTcc);
	}

	@RequestMapping(value = "/confirmAddPoints",method = RequestMethod.POST)
	public void confirmAddPoints(String tccId) {
		AccountPointsTccfinal modelTcc = accountPointsTccfinalMapper.getByTccId(tccId);
		
		if(modelTcc == null){
			throw new RuntimeException("业务TCC记录不存在:" + tccId);
		}
		
		if(modelTcc.getStatus() == TccStatus.CONFIRM.getStatus()){
			return ;
		}
		
		AccountPoints model = accountPointsMapper.getByAccountNo(modelTcc.getAccountNo());
		model.setTotal(model.getTotal() + modelTcc.getPoints());
		accountPointsMapper.updateByVer(model);
		
		modelTcc.setStatus(TccStatus.CONFIRM.getStatus());
		accountPointsTccfinalMapper.updateByVer(modelTcc);
		
	}

	@RequestMapping(value = "/cancelAddPoints",method = RequestMethod.POST)
	public void cancelAddPoints(String tccId) {
		AccountPointsTccfinal modelTcc = accountPointsTccfinalMapper.getByTccId(tccId);
		
		if(modelTcc == null){
			return ;
		}
		
		if(modelTcc.getStatus() == TccStatus.CANCEL.getStatus()){
			return ;
		}
		
		modelTcc.setStatus(TccStatus.CANCEL.getStatus());
		accountPointsTccfinalMapper.updateByVer(modelTcc);
		
	}
}
