package com.yihuisoft.product.finance.mapper;

import java.util.List;
import java.util.Map;


import com.yihuisoft.product.finance.entity.FinanceDataDay;



public interface FinanceDataDayMapper {

	//获取某段时间内的收益率和产品净值
	List<FinanceDataDay> getFinanceYieldRatioLine(Map<String,String> map);

	//要查询的开始天-结束时间的单位净值
	List<FinanceDataDay> getGrowth(Map<String,String> map);
	//查询时间段对应每天的风险率
	List<FinanceDataDay> getDataDay(Map<String,String> map);

	//查询对应时间段每年的风险率
	List<FinanceDataDay> getDataYear(Map<String,String> map);
	
	//查询对应时间段每月的风险率
	List<FinanceDataDay> getDataMonth(Map<String,String> map);
	
	//查询对应时间段每周的风险率
	List<FinanceDataDay> getDataWeek(Map<String,String> map);

	
}












