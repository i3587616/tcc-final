package org.tcc.cloud.points.api;

import org.springframework.stereotype.Component;

@Component
public class PointsServiceHystrix implements PointsService{

	@Override
	public void addPoints(String accountNo, Integer addNum, String tccId) {
		throw new RuntimeException("pointsServiceHystric try problem");
	}

	@Override
	public void confirmAddPoints(String tccId) {
		throw new RuntimeException("pointsServiceHystric confirm problem");
		
	}

	@Override
	public void cancelAddPoints(String tccId) {
		throw new RuntimeException("pointsServiceHystric cancel problem");
		
	}

}
