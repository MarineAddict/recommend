package com.yihuisoft.product.validate.serviceimp;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yihuisoft.product.validate.service.RestHub;

@Service("restHubeImp")
public class RestHubImp implements RestHub{

	
	private RestTemplate template=new RestTemplate();

	public String verifyDepositeYieldRation() {
	 String productCode="120170010100030";
	 String startTime="2017-01-01";
	 String endTime="2017-01-31";		
	 String url="http://localhost:8088/stuforssm/deposit/yieldRatioLine?productCode="+productCode+"&startTime="+startTime+"&endTime="+endTime;
	 List list= template.postForEntity(url,null,List.class).getBody();
	return null;
	}

	public Object startARestRequest(String url) {	
		Object returned=template.postForEntity(url,null,Object.class).getBody();
		return returned;
	}
	
	
	
	
	
	
}
