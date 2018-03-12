package com.yihuisoft.product.correlation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihuisoft.product.correlation.service.CorrelationService;
import com.yihuisoft.product.util.CommonCal;

/** 
 * @Description:
 * 
 * @author 	:lixiaosong
 * @date 	:2018年2月6日 下午4:38:02 
 * @version :V1.0 
 */
@Controller
@RequestMapping("/correlation")
public class CorrelationController {
	
	private CommonCal commonCal;
	
	@Resource
	private CorrelationService correlationService;
	
	@RequestMapping("/getDistinctName")
	@ResponseBody
	List<String> getDistinctName(){
		return correlationService.getDistinctName();
	}
	@RequestMapping("/getDistinctCode")
	@ResponseBody
	List<String> getDistinctCode(){
		return correlationService.getDistinctCode();
	}
	
	@RequestMapping("/getCorrelationCoefficent")
	@ResponseBody
	public List<List<Double>> getCorrelationCoefficent(String startTime,String endTime){
		List<Map<String,List<Double>>> dlist=new ArrayList<Map<String,List<Double>>>();
		List<String> distinctCode=new ArrayList<String>();
		distinctCode=correlationService.getDistinctCode();
		for(int i=0;i<distinctCode.size();i++){
			Map<String,List<Double>> map=new HashMap<String, List<Double>>();
			List<Double> list=new ArrayList<Double>();
			list=correlationService.getCodeData(distinctCode.get(i),startTime,endTime);
			map.put(distinctCode.get(i), list);
			dlist.add(map);
		}
		return commonCal.calculateCorrelationCoefficent(dlist);
	}
	/**
	 * 
	 * @Description:获取数据初始时间和结束时间
	 *
	 * @author: lixiaosong
	 * @date  : 2018年3月5日 上午11:45:42
	 *
	 * @Title getInitTime 
	 * @return 
	 * @return List<String>
	 */
	@RequestMapping("/getInitTime")
	@ResponseBody
	public List<String> getInitTime(){
		return correlationService.getInitTime();
	}
	
}
