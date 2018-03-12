package com.yihuisoft.product.correlation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/** 
 * @Description:
 * 
 * @author 	:lixiaosong
 * @date 	:2018年2月6日 下午9:14:19 
 * @version :V1.0 
 */
public interface CorrelationMapper {

	List<String> getDistinctCode();
	
	List<String> getDistinctName();
	
	List<Double> getCodeData(@Param("code")String code,@Param("startTime")String startTime,@Param("endTime")String endTime);

	List<String> getInitTime();


	
}
