package com.yihuisoft.product.finance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.category.entity.CategoryData;
import com.yihuisoft.product.finance.entity.FinanceBasicData;

public interface FinanceBasicDataMapper {

	//查询理财产品列表(产品基本信息展示)
	List<FinanceBasicData> selectFinanceList(@Param("map") Map<String, Object> map);
	int selectFinanceListRows(@Param("map") Map<String, Object> map);

	//根据理财产品代码删除理财产品
	int deleteFinancePro(@Param("productCode")String productCode);

	//查詢理財產品信息計算風險率和收益率
	FinanceBasicData selectFinanceInfo(@Param("productCode")String productCode);
	
	//查询同风险等级下的产品的平均预期收益率
	Double selectExpYield(@Param("productCode")String productCode,@Param("risklevel") String risklevel);
	
	//查看理财详情
	Map getFinanceDetail(@Param("map") Map<String,Object> map);
	
	//查看理财收益率涨势
	Map getFinanceTrend(@Param("map") Map<String,Object> map);
	List<String> getListDays(@Param("map") Map<String,Object> map);
	
	List<CategoryData> getTheBidData(@Param("map") Map<String,Object> map);
}
