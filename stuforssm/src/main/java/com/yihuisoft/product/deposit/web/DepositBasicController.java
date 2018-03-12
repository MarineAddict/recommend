package com.yihuisoft.product.deposit.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihuisoft.product.deposit.entity.Deposit;
import com.yihuisoft.product.deposit.entity.DepositBasic;
import com.yihuisoft.product.deposit.service.DepositBasicService;

/**
 *  @Description：存单产品Controller
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/2/1-10:37
 *  @version    ：V_1.0.0
 */
@Controller
@RequestMapping("/deposit")
public class DepositBasicController {

    private static final Logger logger= LoggerFactory.getLogger(DepositBasicController.class);

    @Autowired
	@Qualifier("depositBasicServiceImpl")
    private DepositBasicService service;

    /**
     * 获取收益率曲线
     * @param productCode 存单代码
     * @param startTime	  开始时间
     * @param endTime	  结束时间
     * @return
     */
    @RequestMapping("/yieldRatioLine")
    @ResponseBody
    public List<Deposit> getYieldRatioLine(String productCode, String startTime, String endTime){
        return service.getYieldRatioLine(productCode, startTime, endTime);
    }

    /**
     * 存单信息展示
     * @param productCode 存单代码
     * @param productName 存单名称
     * @return
     */
    @RequestMapping(value = "/getDepositBasic")
    @ResponseBody
    public List<DepositBasic> getDepositBasic(String productCode, String productName){
        System.out.println(productCode);
        System.out.println(productName);
        return service.getDeposit(productCode, productName);
    }

    /**
     * 获取收益率、风险率
     * @param productCode 存单代码
     * @return
     */
    @RequestMapping(value = "/getIncomeAndRisk")
    @ResponseBody
    public Map<String,Object> getIncomeAndRisk(String productCode){
        Map<String,Object> map = new HashMap<String, Object>();
        //收益率
        float imcome = service.getImcome(productCode);
        //风险率
        float risk = service.getRisk(productCode);
        map.put("imcome",imcome);
        map.put("risk",risk);
        return map;
    }
    
    /**
     * 存单信息展示
     * @param productCode 存单代码
     * @param productName 存单名称
     * @return
     * @throws JsonProcessingException 
     */
    @RequestMapping(value = "/getDepositList")
    @ResponseBody
    public  String getDepositList(@RequestParam int page,@RequestParam int rows,String productCode, String productName) throws JsonProcessingException{
    	int start = (page-1)*rows+1;
        int end = page*rows;
        Map map =new HashMap();
        List<DepositBasic> list= service.qryDepositList(start, end, productCode, productName);
       int total= service.qryDepositListRows(start, end, productCode, productName);
    	map.put("total", total);
		map.put("rows", list);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
    }
    
    @RequestMapping("/toDepositDetail")
    public ModelAndView toDepositDetail(String prdCode,String name
    		,String  subEndDay,String subStartDay,String intStartDay,String intEndDay,String interest,
    		String assetType,String traceName,String term){
    	Map resultMap=new HashMap<>();
    	ModelAndView mv=new ModelAndView();
    	try {
			name=new String(name.getBytes("ISO8859-1"),"utf-8");
			assetType=new String(assetType.getBytes("ISO8859-1"),"utf-8");
			traceName=new String(traceName.getBytes("ISO8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	resultMap.put("traceName", traceName);
    	resultMap.put("assetType", assetType);
    	resultMap.put("prdCode", prdCode);
    	resultMap.put("name", name);
    	resultMap.put("subEndDay", subEndDay);
    	resultMap.put("subStartDay", subStartDay);
    	resultMap.put("intStartDay", intStartDay);
    	resultMap.put("intEndDay", intEndDay);
    	resultMap.put("interest", interest);
    	resultMap.put("term", term);
    	mv.addObject("deposit", resultMap);
    	mv.setViewName("product/deposit/depositDetail");
    	return mv;
    }
    /**
     * 根据存款代码查询出涨幅的比较
     * @param depositCode
     * @return
     */
    @RequestMapping("/depositIncreasePerformance")
    @ResponseBody
    public Map depositIncreasePerformance(String depositCode){
    	 return service.getDailyIncreaseList(depositCode);
    }
    

    
    
    
    
}
