package com.yihuisoft.product.deposit.web;

import com.yihuisoft.product.deposit.service.DepositBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/deposit/verification")
public class DepositDataVerificationController {

    @Autowired
	@Qualifier("depositBasicServiceImpl")
    private DepositBasicService depositBasicService;

    @RequestMapping(value = "/risk")
    @ResponseBody
    public float detectionRisk(String prodCode){
        float risk = depositBasicService.getRisk(prodCode);
        return risk;
    }

    @RequestMapping(value = "/imcome")
    @ResponseBody
    public float detectionImcome(String prodCode){
        float imcom = depositBasicService.getImcome(prodCode);
        return imcom;
    }
}
