package com.yihuisoft.product.test.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yihuisoft.product.test.entity.TestEntity;

public interface TestMapper {

	@Select("select * from TEST_TABLE where ID = #{id}")
	TestEntity selectData(@Param("id") Integer id);
}
