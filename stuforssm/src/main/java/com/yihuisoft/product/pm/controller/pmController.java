package com.yihuisoft.product.pm.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihuisoft.product.pm.entity.PmBasic;
import com.yihuisoft.product.pm.entity.pmProduct;
import com.yihuisoft.product.pm.service.pmService;

/** 
 * @Description:
 * 
 * @author 	:lixiaosong
 * @date 	:2018年1月17日 上午9:44:02 
 * @version :V1.0 
 */
@Controller
@RequestMapping("/pm")
public class pmController {
	
	private static final Logger logger=LoggerFactory.getLogger(pmController.class);
	
	@Autowired
	private pmService pmservice;
	
	/**
	 * 
	 * @Description:贵金属收益率曲线
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月17日 下午1:54:00
	 *
	 * @Title getYieldRatioLine 
	 * @param productCode  产品代码
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @return 
	 * @return List<pmProduct>
	 */
	@RequestMapping("/yieldRatioLine")
	@ResponseBody
	public List<pmProduct> getYieldRatioLine(String productCode,String startTime,String endTime){
		return pmservice.getYieldRatioLine(productCode, startTime, endTime);
	}
	
	/**
	 * 
	 * @Description:获取所有贵金属产品
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月23日 下午3:16:13
	 *
	 * @Title getPmList 
	 * @param productCode
	 * @param name
	 * @param releaseDate
	 * @return 
	 * @return List<PmBasic>
	 */
	@RequestMapping("/pmList")
	@ResponseBody
	public List<PmBasic> getPmList(String productCode,String name,String releaseDate){
		return pmservice.getPmList(productCode, name, releaseDate);
	}
	
	/**
	 * 
	 * @Description:获取涨幅，最大回撤、预期收益率、预期风险率
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月23日 下午3:17:48
	 *
	 * @Title getCaculateValue 
	 * @param productCode
	 * @param startTime
	 * @param endTime
	 * @param cycle
	 * @return 
	 * @return Map<String,String>
	 */
	@RequestMapping("/getCaculateValue")
	@ResponseBody
	public Map<String,String> getCaculateValue(String productCode,String startTime,String endTime,String cycle){
		return pmservice.getCaculateValue(productCode, startTime, endTime, cycle);
	}
	
	
	/**
	 * 
	 * @Description:定时任务
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月17日 下午3:28:07
	 *
	 * @Title runJob 
	 * @return 
	 * @return int
	 */
	@RequestMapping("/runJob")
	@ResponseBody
	public int runJob(){
		String IndName="pmCalculate";//python文件名
		return pmservice.CallPythonMethod(IndName);
	}
	
}
