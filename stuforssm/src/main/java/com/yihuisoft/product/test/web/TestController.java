package com.yihuisoft.product.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihuisoft.product.test.entity.TestEntity;
import com.yihuisoft.product.test.service.TestService;

/**
 * @Description:测试控制层
 *
 * @author  :MaChengXin
 * @date    :2017年11月30日 下午3:40:03
 * @version :V1.0
 */

@Controller
@RequestMapping(value = "test")
public class TestController {

	@Autowired
	private TestService testService;
	
	/**
	 * RestFul 风格的请求
	 */
	@RequestMapping(value = "/getdata/{id}")
	@ResponseBody
	public TestEntity getData(@PathVariable Integer id) {
		System.out.println(id);
		return testService.findData(id);
	}

	@RequestMapping(value ="/test2")
	public String test2(){
		return "STU_JS/Js_01";
	}
	
	
}

