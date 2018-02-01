package com.yihuisoft.product.customegroup.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.customegroup.entity.CusIncomeTracing;

public interface CusIncomeTracingMapper {

	CusIncomeTracing selectGroupIdByPlanAndCusId(@Param("planId")String planId,@Param("cusId")String cusId);

}
