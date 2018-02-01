package com.yihuisoft.product.group.mapper;

import com.yihuisoft.product.group.entity.ProductGroup;
import com.yihuisoft.product.group.entity.ProductGroupBasic;
import com.yihuisoft.product.group.entity.ProductGroupDetails;
import com.yihuisoft.product.group.entity.dao.ProductNavadjDAO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyinyuo on 2018/1/17.
 */
public interface ProductGroupMapper {
	
	List<ProductGroup> getProductGroupYieldRatioLine(Map<String,String> map);
	
	ProductGroupBasic getProductGroupDetailsBasic(@Param("product_group_id") String product_group_id);
	
	List<ProductGroupDetails> getProductGroupDetailsInfo(@Param("product_group_id") String product_group_id);

    int insertProductGroup(Map<String,Object> map);
    
    int updateProductGroupSharpRatio(@Param("product_group_id") int product_group_id,@Param("sharpRatio") double sharpRatio);

    int insertProductGroupDetails(@Param("product_group_id") int product_group_id, @Param("templist") List<ProductGroupDetails> templist);
    
    int deleteProductGroup(@Param("product_group_id") String product_group_id, @Param("time") String time);

	List<String> getProductGroup(Map<String,String> map);
	
	String getNavadjStartTime(@Param("product_group_id") String product_group_id);
    
    List<ProductNavadjDAO> getProductNavadj(@Param("product_group_id") String product_group_id,@Param("type") String type,@Param("startTime")String startTime);
    

    int insertProductGroupNavadj(@Param("templist") List<ProductNavadjDAO> productGroupList);
}