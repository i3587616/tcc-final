package org.tcc.cloud.points.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linc.tccfinal.core.transaction.TccTransaction;

/**
 * 积分服务（测试简化例子）
 * @author chang.lin
 *
 */
@FeignClient(value = "tcc-cloud-points", fallback = PointsServiceHystrix.class )
public interface PointsService {
	
	@RequestMapping(value = "/addPoints",method = RequestMethod.POST)
	@TccTransaction
	public void addPoints(@RequestParam(value = "accountNo") String accountNo,
			@RequestParam(value = "addNum") Integer addNum,@RequestParam(value = "tccId") String tccId);

	@RequestMapping(value = "/confirmAddPoints",method = RequestMethod.POST)
	public void confirmAddPoints(@RequestParam(value = "tccId") String tccId);
	
	@RequestMapping(value = "/cancelAddPoints",method = RequestMethod.POST)
	public void cancelAddPoints(@RequestParam(value = "tccId") String tccId);
}
