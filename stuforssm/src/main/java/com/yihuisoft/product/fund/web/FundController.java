package com.yihuisoft.product.fund.web;

import com.yihuisoft.product.fund.entity.dao.FundBasicDAO;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.fund.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
