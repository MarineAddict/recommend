package com.yihuisoft.product.schedule.product_category.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.schedule.product_category.entity.DepositBasicData;
import com.yihuisoft.product.schedule.product_category.entity.FundBasicData;
import com.yihuisoft.product.schedule.product_category.entity.Page;


public interface BaseDao {
   
	List<FundBasicData> getFundProductDataByPage(@Param("page")Page page);
	
	Integer getFundProductTotalNumber();
	
	List<DepositBasicData> getDepositProductDataByPage(@Param("page")Page page);
	
	Integer getDepositProductTotalNumber();
	
}
