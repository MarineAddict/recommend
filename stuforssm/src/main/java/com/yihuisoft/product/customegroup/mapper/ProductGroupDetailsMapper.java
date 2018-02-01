package com.yihuisoft.product.customegroup.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.customegroup.entity.ProductGroupDetails;

public interface ProductGroupDetailsMapper {

	//查询客户组合产品的信息
	List<ProductGroupDetails> selectProductGroupByGroupId(@Param("groupId")String goupId);

	//查询客户组合产品的收益和产品信息
	List<ProductGroupDetails> selectCusProGroupByGroupId(@Param("groupId")String goupId);
	
	//获取客户历史收益
	List<ProductGroupDetails> selectGroupById(Map<String, String> map);

	

}
