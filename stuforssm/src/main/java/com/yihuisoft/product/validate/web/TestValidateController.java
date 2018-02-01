package com.yihuisoft.product.validate.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihuisoft.product.validate.service.ExcelReader;
import com.yihuisoft.product.validate.service.RestHub;
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
		filePath="C:\\Users\\X&Q\\Desktop\\理财模拟数据.xlsx";
		service.startValidateProcess(filePath);
		return "111";
		
	}
	
	
	
	
	
	
}
