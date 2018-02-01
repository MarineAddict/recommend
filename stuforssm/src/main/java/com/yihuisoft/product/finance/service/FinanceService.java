package com.yihuisoft.product.finance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yihuisoft.product.finance.entity.FinanceBasicData;
import com.yihuisoft.product.finance.entity.FinanceDataDay;
import com.yihuisoft.product.finance.mapper.FinanceBasicDataMapper;
import com.yihuisoft.product.finance.mapper.FinanceDataDayMapper;
import com.yihuisoft.product.util.CommonCal;

;

@Service
public class FinanceService {

	@Autowired
	private FinanceDataDayMapper financeDataDayMapper;

	@Autowired
	private FinanceBasicDataMapper financeBasicDataMapper;

	private Logger logger = LoggerFactory.getLogger(FinanceService.class);

	/**
	 * @author tangjian
	 * @Description: 获取理财产品的收益曲线图
	 * @param productCode
	 * @param startTime
	 * @param endTime
	 * @return List<FinanceDataDay>
	 * @date: 2018年1月19日 上午11:41:44
	 */
	public List<FinanceDataDay> getFinanceHistoryIncomeLine(String financeCode,
			String startTime, String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("financeCode", financeCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<FinanceDataDay> listFinanceIncome = new ArrayList<FinanceDataDay>();
		try {
			listFinanceIncome = financeDataDayMapper
					.getFinanceYieldRatioLine(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取理财产品历史收益率失败：" + e.getMessage());
		}
		return listFinanceIncome;

	}

	/**
	 * @author tangjian
	 * @Description: 查询理财产品列表
	 * @return List<FinanceBasicData>
	 * @date: 2018年1月19日 上午11:47:04
	 */
	public List<FinanceBasicData> getFinanceList(String code,
			String financeName, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("financeName", financeName);
		map.put("status", status);
		List<FinanceBasicData> financeList = new ArrayList<FinanceBasicData>();
		try {
			financeList = financeBasicDataMapper.selectFinanceList(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取理财产品列表失败：" + e.getMessage());
		}
		return financeList;
	}

	/**
	 * 删除理财产品
	 * @author tangjian
	 * @param financeCode
	 * @return int
	 * @date: 2018年1月20日 下午4:01:01
	 */
	public int deleteFinancePro(String financeCode) {
		int delFlag = 0;
		try {
			delFlag = financeBasicDataMapper.deleteFinancePro(financeCode);
		} catch (Exception e) {
			logger.error("理财产品删除失败");
			e.printStackTrace();
		}
		return delFlag;
	}

	/**
	 * @author tangjian
	 * @Description: 查询某一时间段的涨幅
	 * @param financeCode
	 * @param startTime
	 * @param endTime
	 * @return Double
	 * @date: 2018年1月22日 下午2:26:31
	 */
	public Double getFinanceExpIncrease(String financeCode, String startTime,
			String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("financeCode", financeCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<Double> gorwthRatioList = new ArrayList<Double>();
		Double gorwthRatio = 0.0;
		List<FinanceDataDay> list = financeDataDayMapper.getGrowth(map);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (FinanceDataDay financeDataDay : list) {
			gorwthRatioList.add(financeDataDay.getNavaDj());
		}
		gorwthRatio = (gorwthRatioList.get(gorwthRatioList.size() - 1) - gorwthRatioList
				.get(0)) / gorwthRatioList.get(0);
		return gorwthRatio;
	}

	/**
	 * @author tangjian
	 * @Description: 获取预期收益率(按周月年天数据分别计算)
	 * @param financeCode
	 * @param startTime
	 * @param endTime
	 * @return Double
	 * @date: 2018年1月22日 下午2:26:31
	 */
	public Double calFinanceExpIncome(String financeCode) {
		if(!"".equals(financeCode)&&financeCode != null){
			FinanceBasicData financeList = financeBasicDataMapper
					.selectFinanceInfo(financeCode);
			Double	yieldRatio = financeList.getExpYieldMax();
			return yieldRatio;
		}
		return null;
	}

	/**   
	 * @Title: calFinanceExpRisk 
	 * @author tangjian
	 * @Description: TODO
	 * @param financeCode
	 * @return  Double
	 * @date:   2018年1月29日 上午9:53:11         
	 */  
	public Double calFinanceExpRisk(String financeCode) {
		Double riskRatio = 0.0;
		FinanceBasicData financeList = financeBasicDataMapper
				.selectFinanceInfo(financeCode);
		if (!"".equals(financeList.getRealYield())
				&& financeList.getRealYield() != 0
				&& financeList.getRealYield() != null) {
			riskRatio = financeList.getRealYield()
					- financeList.getExpYieldMax();
			if (riskRatio > 0.0 || riskRatio == 0.0) {
				return 0.0;
			}
			return Math.abs(riskRatio);
		}else{
			String risklevel = financeList.getRisklevel();
			riskRatio = financeBasicDataMapper.selectExpYield(financeCode,risklevel);
			return riskRatio;
		}
	}

	/**
	 * @author tangjian
	 * @Description: 计算理财产品的最大回撤
	 * @param financeCode
	 * @param startTime
	 * @param endTime
	 * @return Double
	 * @date: 2018年1月22日 下午2:26:31
	 */
	public Double getFinanceMaxdrawdown(String financeCode, String startTime,
			String endTime, String calType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("financeCode", financeCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<Double> maxdrawdownList = new ArrayList<Double>();
		Double maxdrawdown = 0.0;
		if (calType.equalsIgnoreCase("day")) {
			List<FinanceDataDay> list = financeDataDayMapper.getDataDay(map);
			if (CollectionUtils.isEmpty(list)) {
				return null;
			}
			for (FinanceDataDay financeDataDay : list) {
				maxdrawdownList.add(financeDataDay.getNavaDj());
			}
			maxdrawdown = CommonCal.calculateMaxdrawdown(maxdrawdownList);
		} else if (calType.equalsIgnoreCase("week")) {
			List<FinanceDataDay> list = financeDataDayMapper.getDataWeek(map);
			if (CollectionUtils.isEmpty(list)) {
				return null;
			}
			for (FinanceDataDay financeDataDay : list) {
				maxdrawdownList.add(financeDataDay.getNavaDj());
			}
			maxdrawdown = CommonCal.calculateMaxdrawdown(maxdrawdownList);
		} else if (calType.equalsIgnoreCase("month")) {
			List<FinanceDataDay> list = financeDataDayMapper.getDataMonth(map);
			if (CollectionUtils.isEmpty(list)) {
				return null;
			}
			for (FinanceDataDay financeDataDay : list) {
				maxdrawdownList.add(financeDataDay.getNavaDj());
			}
			maxdrawdown = CommonCal.calculateMaxdrawdown(maxdrawdownList);
		} else if (calType.equalsIgnoreCase("year")) {
			List<FinanceDataDay> list = financeDataDayMapper.getDataYear(map);
			if (CollectionUtils.isEmpty(list)) {
				return null;
			}
			for (FinanceDataDay financeDataDay : list) {
				maxdrawdownList.add(financeDataDay.getNavaDj());
			}
			maxdrawdown = CommonCal.calculateMaxdrawdown(maxdrawdownList);
		}
		return maxdrawdown;
	}
	/**
	 * 通过目标收益率求对应风险率（偏左动差）
	 * @author tangjian
	 * @param financeCode 
	 * @param expYield 期望收益率（客户页面输入的期望）
	 * @param startDate
	 * @param endDate
	 * @return riskRatio 风险率
	 */
	public Double calRiskMakeLeftVariance(String financeCode,Double targetYield,String startTime,String endTime){
		Map<String, String> map = new HashMap<String, String>();
		map.put("financeCode", financeCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<FinanceDataDay> list = financeDataDayMapper.getDataDay(map);
		List<Double> YieldList = new ArrayList<Double>();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		for (FinanceDataDay financeDataDay : list) {
			YieldList.add(financeDataDay.getYieldRatio());
		}
		Double riskRatio =  CommonCal.calculateLeftVariance(YieldList, targetYield);
		return riskRatio;
	}
	/**
	 * 通过历史收益率计算风险率（半方差）
	 * @param financeCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Double calRiskSemiVariance(String financeCode,String startTime,String endTime){
		Map<String, String> map = new HashMap<String, String>();
		map.put("financeCode", financeCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<FinanceDataDay> list = financeDataDayMapper.getDataDay(map);
		List<Double> YieldList = new ArrayList<Double>();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		for (FinanceDataDay financeDataDay : list) {
			YieldList.add(financeDataDay.getYieldRatio());
		}
		Double riskRatio =  CommonCal.calculateSemiVariance(YieldList);
		return riskRatio;
	}
}
