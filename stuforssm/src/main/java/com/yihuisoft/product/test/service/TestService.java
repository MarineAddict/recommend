package com.yihuisoft.product.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.test.entity.TestEntity;
import com.yihuisoft.product.test.mapper.TestMapper;

/**
 * @Description:测试Service层
 * @author  : MaChengXin
 * @version : V1.0
 * @create  : 2017/12/1-16:13 
 */
@Service
public class TestService {

	@Autowired
	private TestMapper testMapper;

	public TestEntity findData(Integer id) {		
		return testMapper.selectData(id);
	}
	
}
