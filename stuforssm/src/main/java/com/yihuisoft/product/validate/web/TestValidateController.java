package com.yihuisoft.product.validate.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihuisoft.product.validate.service.ValidateService;

@RestController
@RequestMapping("/test")
public class TestValidateController {

	@Autowired
	@Qualifier("validateServiceImp")
	private ValidateService service;
	
	@RequestMapping("/startVerify")
	@ResponseBody
	public String testStatus(String filePath){
		filePath="C:\\Users\\X&Q\\Desktop\\基金收益率及风险率数据计算.xlsx";
		service.startValidateProcess(filePath);
		return "111";
			}
	
	
	
	
	
	
}
