package com.yihuisoft.product.deposit.web;

import com.yihuisoft.product.deposit.entity.Deposit;
import com.yihuisoft.product.deposit.entity.DepositBasic;
import com.yihuisoft.product.deposit.service.DepositBasicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
