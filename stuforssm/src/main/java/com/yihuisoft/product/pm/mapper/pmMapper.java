package com.yihuisoft.product.pm.mapper;

import java.util.List;
import java.util.Map;

import com.yihuisoft.product.pm.entity.PmBasic;
import com.yihuisoft.product.pm.entity.pmProduct;

/** 
 * @Description:贵金属Mapper
 * 
 * @author 	:lixiaosong
 * @date 	:2018年1月16日 下午5:07:51 
 * @version :V1.0 
 */
public interface pmMapper {
	
	//获取一段时间区间内某个产品的收益率
	List<pmProduct> getYieldRatioLine(Map map);
	
	//查询所有产品代码
	List<String> getProductCodeList();
	
	//查询所有产品
	List<PmBasic> getPmList(Map map);
}
