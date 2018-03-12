package com.yihuisoft.product.fund.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihuisoft.product.fund.entity.FundMonetaryYield;
import com.yihuisoft.product.fund.entity.FundTrack;
import com.yihuisoft.product.fund.entity.dao.FundBasicDAO;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.fund.service.FundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyinyuo on 2018/1/18.
 */
@RestController
@RequestMapping("/fund/")
public class FundController {

    @Autowired
    FundService fundService;
    
    /**
     * wangyinuo	2018-01-23
     * 返回基金的风险率和收益率序列
     * @param productCode	基金代码
     * @param startTime		开始时间
     * @param endTime		结束时间
     * @return
     */
    @RequestMapping("/yieldRatioLine")
    public List<FundProductDAO> getFundYieldRisk(String productCode, String startTime, String endTime){
        return fundService.getFundYieldRisk(productCode, startTime, endTime);
    }
    
    /**
     * wangyinuo	2018-01-23
     * @param productCode	产品代码
     * @param startTime		开始时间	
     * @param endTime		结束时间
     * @param cycle			查询周期（day：天，week：周，month：月，year：年）
     * @param calculateType	预期风险率计算类型
     * @param tarReturn		目标收益率
     * @return
     */
    @RequestMapping("/cycle/{cycle}/getFundBasicInfo")
    public List<FundBasicDAO> getFundBasicInfo( String productCode, String startTime, String endTime,@PathVariable String cycle,String calculateType,Double tarReturn){
    	return fundService.getFundBasicInfo(productCode, startTime, endTime,cycle,calculateType,tarReturn);
    }
    
    /**
     * @author zhaodc 2018-02-01
     * @param page
     * @param rows
     * @param productCode
     * @param startTime
     * @param endTime
     * @return
     * @throws JsonProcessingException 
     */
    @RequestMapping("/cycle/{cycle}/qryFundBasicInfo")
    public String qryFundBasicInfo( @RequestParam int page ,@RequestParam int rows ,String productCode, String startTime, String endTime,String scode,String funStatus,String fundTyp,String cxfunTyp) throws JsonProcessingException{
    	int start = (page-1)*rows+1;
        int end = page*rows;
        List<FundBasicDAO> fundlst=fundService.qryFundBasicInfoList(start, end,productCode, startTime, endTime,scode,funStatus,fundTyp,cxfunTyp);
    	int total = fundService.qryFundBasicInfoListRows(start, end, productCode,startTime, endTime,scode,funStatus,fundTyp,cxfunTyp);
    	Map map = new HashMap();
    	map.put("total", total);
		map.put("rows", fundlst);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
    }

    /**
     * 基金基本信息
     * @author zhaodc
     * @param code
     * @param bidCode
     * @return
     */
    @RequestMapping("/toFundDetail")
    public ModelAndView  toFundDetail(String code,String bidCode){
    	ModelAndView mv= new ModelAndView();
    	mv.addObject("fundCode", code);
    	Map map=fundService.qryFundDetail(code,bidCode);
    	mv.addObject("fund", map);
    	mv.setViewName("product/fund/fundDetail");
    	return mv;
    }
    
    /**
     * 基金指数涨幅走势
     * @author zhaodc
     * @param code 产品代码
     * @param bidCode 标的代码
     * @param sCode 小类代码
     * 
     * @return
     */
    @RequestMapping("/toFundAchie")
    public Map  toFundAchie(String code,String sCode,String bidCode,Integer days,String startTime,String endTime,String flag){
    	return fundService.qryFundTrack(code,sCode,bidCode,days,startTime,endTime,flag);
    }

    /**
     * @Auth: yongquan.xiong
     * @Desc: 产品回撤数据
     * @Date: 2018-03-06
     * @param startDate 开始时间
     *         endDate   结束时间
     *         productCode 产品代码
     *         benchmarkCode 基准代码
     * @return
     */
    @RequestMapping("/toFundDrawdowns")
    public Map getDrawdowns(String startDate,String endDate,String productCode,String benchmarkCode){
    	return fundService.qryDrawdowns(startDate,endDate,productCode,benchmarkCode);
    }
    
    /**
     * 基金单位净值走势
     * @author zhaodc
     * @param code
     * @return
     */
    @RequestMapping("/fundValue")
    public List<FundTrack>  getFundValue(String code,Integer days,String startTime,String endTime,String flag){
    	return fundService.qryFundNetValue(code,days,startTime,endTime,flag);
    }
    
    /**
     * 获取基金标本差，sharp比率
     * @author zhaodc
     * @param code
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/fundSharp")
    public Map  getFundSharp(String code,String startTime,String endTime){
    	return fundService.qryFundNetSharp(code, startTime, endTime);
    }
    
    /**
     * 获取货币型基金七日年化、万份收益率
     * @author zhaodc
     * @param code
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/fundMonetaryYield")
    public List<FundMonetaryYield>  fundMonetaryYield(String code,Integer days,String startTime,String endTime,String flag){
    			List<FundMonetaryYield> li	=	fundService.qrygetFundMonetaryYield(code,startTime,endTime,days,flag);
    			return li;
    	
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
        String IndName="FundCode";//python文件名
        return fundService.CallPythonMethod(IndName);
    }
    
}
