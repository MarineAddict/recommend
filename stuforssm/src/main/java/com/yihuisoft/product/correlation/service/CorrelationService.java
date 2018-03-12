package com.yihuisoft.product.correlation.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yihuisoft.product.correlation.mapper.CorrelationMapper;

/** 
 * @Description:
 * 
 * @author 	:lixiaosong
 * @date 	:2018年2月6日 下午9:13:21 
 * @version :V1.0 
 */
@Service
public class CorrelationService {
	
	@Resource
	private CorrelationMapper correlationMapper;
	
	public List<String> getDistinctCode(){
		return correlationMapper.getDistinctCode();
	}
	
	public List<Double> getCodeData(String code,String startTime,String endTime){
		return correlationMapper.getCodeData(code,startTime,endTime);
	}

	public List<String> getDistinctName() {
		return  correlationMapper.getDistinctName();
	}

	public List<String> getInitTime() {
		return correlationMapper.getInitTime();
	}

}
