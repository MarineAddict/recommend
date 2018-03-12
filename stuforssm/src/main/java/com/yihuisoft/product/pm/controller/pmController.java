package com.yihuisoft.product.pm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihuisoft.product.fund.entity.FundTrack;
import com.yihuisoft.product.pm.entity.PmTrack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihuisoft.product.pm.entity.PmBasic;
import com.yihuisoft.product.pm.entity.pmProduct;
import com.yihuisoft.product.pm.service.pmService;
import org.springframework.web.servlet.ModelAndView;

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
	private pmService pmService;
	
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
		return pmService.getYieldRatioLine(productCode, startTime, endTime);
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
		return pmService.getPmList(productCode, name, releaseDate);
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
		return pmService.getCaculateValue(productCode, startTime, endTime, cycle);
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
		return pmService.CallPythonMethod(IndName);
	}
	
	/**
	 * 分页查询贵金属
	 * @author zhaodc
	 * @param page
	 * @param rows
	 * @param productCode
	 * @param name
	 * @param releaseDate
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping("/qryPmList")
	@ResponseBody
	public String qryPmBasicList(@RequestParam int page,@RequestParam int rows,String preCode,String preName,String releaseDate ,String preStatus) throws JsonProcessingException{
		int start = (page-1)*rows+1;
        int end = page*rows;
        Map map =new HashMap();
        List<PmBasic> list= pmService.qryPmList(start,end,preCode, preName, releaseDate,preStatus);
        int total=pmService.qryPmListRows(start, end, preCode, preName, releaseDate,preStatus);
        map.put("total", total);
		map.put("rows", list);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}


	/**
	 * 贵金属基本信息
	 * @author lixiaosong
	 * @param code
	 * @return
	 */
	@RequestMapping("/toPmDetail")
	public ModelAndView  toPmDetail(String code,String bidCode){
		ModelAndView mv= new ModelAndView();
		mv.addObject("pmCode", code);
		Map map=pmService.qryPmDetail(code,bidCode);
		mv.addObject("pm", map);
		mv.setViewName("product/precious/pmDetail");
		return mv;
	}

	/**
	 * 贵金属指数涨幅走势
	 * @author lixiaosong
	 * @param code 产品代码
	 *
	 * @return
	 */
	@RequestMapping("/toPmAchie")
	@ResponseBody
	public Map  toPmAchie(String code,Integer days,String startTime,String endTime,String flag){
		return pmService.qryPmTrack(code,days,startTime,endTime,flag);
	}

	/**
	 * 基金单位净值走势
	 * @author zhaodc
	 * @param code
	 * @return
	 */
	@RequestMapping("/pmValue")
	@ResponseBody
	public List<PmTrack>  getFundValue(String code, Integer days, String startTime, String endTime, String flag){
		return pmService.qryPmNetValue(code,days,startTime,endTime,flag);
	}


	/**
	 * @Auth: lixiaosong
	 * @Desc: 产品回撤数据
	 * @param startDate 开始时间
	 *         endDate   结束时间
	 *         productCode 产品代码
	 *         benchmarkCode 基准代码
	 * @return
	 */
	@RequestMapping("/toPmDrawdowns")
	@ResponseBody
	public Map getDrawdowns(String startDate,String endDate,String productCode){
		return pmService.qryDrawdowns(startDate,endDate,productCode);
	}

	/**
	 * 获取贵金属标本差，sharp比率
	 * @author lixiaosong
	 * @param code
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/pmSharp")
	@ResponseBody
	public Map  getFundSharp(String code,String startTime,String endTime){
		return pmService.qryPmNetSharp(code, startTime, endTime);
	}



}
