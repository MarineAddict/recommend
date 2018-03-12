package com.yihuisoft.product.finance.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yihuisoft.product.category.entity.CategoryData;
import com.yihuisoft.product.category.mapper.CategoryMapper;
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
	public List<FinanceDataDay> getFinanceHistoryIncomeLine(String productCode,
			String startTime, String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productCode", productCode);
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
	public List<FinanceBasicData> getFinanceList(int start,int end ,String financeName, String financeCode,String financeType,String financeRiskLevel, Integer financeStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("financeName", financeName);
		map.put("financeCode", financeCode);
		map.put("financeType", financeType);
		map.put("financeRiskLevel", financeRiskLevel);
		map.put("financeStatus",financeStatus);
		
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
	 * @author zhaodc 2018-02-01
	 * @param code
	 * @param financeName
	 * @param status
	 * @return
	 */
	public int qryFinanceListRows(String financeName, String financeCode,String financeType,String financeRiskLevel, Integer financeStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("financeName", financeName);
		map.put("financeCode", financeCode);
		map.put("financeType", financeType);
		map.put("financeRiskLevel", financeRiskLevel);
		map.put("financeStatus",financeStatus);
		int rows=0;
		try {
			rows = financeBasicDataMapper.selectFinanceListRows(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询产品记录数失败：" + e.getMessage());
		}
		return rows;
	}
	
	
	

	/**
	 * 删除理财产品
	 * @author tangjian
	 * @param productCode
	 * @return int
	 * @date: 2018年1月20日 下午4:01:01
	 */
	public int deleteFinancePro(String productCode) {
		int delFlag = 0;
		try {
			delFlag = financeBasicDataMapper.deleteFinancePro(productCode);
		} catch (Exception e) {
			logger.error("理财产品删除失败");
			e.printStackTrace();
		}
		return delFlag;
	}

	/**
	 * @author tangjian
	 * @Description: 查询某一时间段的涨幅
	 * @param productCode
	 * @param startTime
	 * @param endTime
	 * @return Double
	 * @date: 2018年1月22日 下午2:26:31
	 */
	public Double getFinanceExpIncrease(String productCode, String startTime,
			String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productCode", productCode);
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
	 * @param productCode
	 * @param startTime
	 * @param endTime
	 * @return Double
	 * @date: 2018年1月22日 下午2:26:31
	 */
	public Double calFinanceExpIncome(String productCode) {
		if(!"".equals(productCode)&&productCode != null){
			FinanceBasicData financeList = financeBasicDataMapper
					.selectFinanceInfo(productCode);
			Double	yieldRatio = financeList.getExpYieldMax();
			return yieldRatio;
		}
		return null;
	}

	/**   
	 * @Title: calFinanceExpRisk 
	 * @author tangjian
	 * @Description: TODO
	 * @param productCode
	 * @return  Double
	 * @date:   2018年1月29日 上午9:53:11         
	 */  
	public Double calFinanceExpRisk(String productCode) {
		Double riskRatio = 0.0;
		FinanceBasicData financeList = financeBasicDataMapper
				.selectFinanceInfo(productCode);
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
			riskRatio = financeBasicDataMapper.selectExpYield(productCode,risklevel);
			return riskRatio;
		}
	}

	/**
	 * @author tangjian
	 * @Description: 计算理财产品的最大回撤
	 * @param productCode
	 * @param startTime
	 * @param endTime
	 * @return Double
	 * @date: 2018年1月22日 下午2:26:31
	 */
	public Double getFinanceMaxdrawdown(String productCode, String startTime,
			String endTime, String calType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productCode", productCode);
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
	 * @param productCode 
	 * @param expYield 期望收益率（客户页面输入的期望）
	 * @param startDate
	 * @param endDate
	 * @return riskRatio 风险率
	 */
	public Double calRiskMakeLeftVariance(String productCode,Double targetYield,String startTime,String endTime){
		Map<String, String> map = new HashMap<String, String>();
		map.put("productCode", productCode);
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
	 * @param productCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Double calRiskSemiVariance(String productCode,String startTime,String endTime){
		Map<String, String> map = new HashMap<String, String>();
		map.put("productCode", productCode);
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
	
	
	/**
	 * 查询理财基本信息
	 * @author zhaodc
	 * @param financecode
	 * @return
	 */
	public Map qryFinanceDetail(String financecode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("financecode", financecode);
		Map remap=null;
		try {
			remap = financeBasicDataMapper.getFinanceDetail(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询理财基本信息失败：", e.getMessage());
		}
		return remap;
	}
	
	/**
	 * 理财产品涨幅走势
	 * @author zhaodc
	 * @param code
	 * @return
	 */
	public Map qryFinanceTrend(String code,String bidcode){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		map1.put("financecode",code);
		Map map3=null;
		Map<String,Object> remap=new HashMap<String, Object>();
		List<String> dates=null;
		List<String> yield =null;
		
;		List<CategoryData> catelist=null;
		List<Double> zsLst =null;
		List<String> zsyieldLst =null;
		try {
			map3 = financeBasicDataMapper.getFinanceTrend(map1);
			double period =  Double.parseDouble(map3.get("PERIOD").toString());
			double maxyield =  Double.parseDouble(map3.get("EXP_YIELD_MAX").toString());
			double startMoney =  Double.parseDouble(map3.get("START_MONEY").toString());
			String startTime = map3.get("VALUE_DATE").toString().substring(0,10);
			String endTime = map3.get("EXPIRY_DATE").toString().substring(0,10);
			String scode = map3.get("SCODE").toString();
			
			map2.put("startTime", startTime);
			map2.put("endTime", endTime);
			map2.put("starTime", startTime);
			map2.put("code", scode);
			dates = financeBasicDataMapper.getListDays(map2);
			 
			 yield = new ArrayList<String>();
			 DecimalFormat  df  = new DecimalFormat("######0.0000"); 
			for(int i=0;i<= period;i++){
				yield.add(df.format(maxyield/360*i));
			}
			
			//跟踪指数
			catelist = financeBasicDataMapper.getTheBidData(map2);
			
			//跟踪指数涨幅计算
			if(catelist==null||catelist.size()==0){
				zsyieldLst.add("0.00");
			}else if(catelist.size()>0){
				for(CategoryData cates:catelist){
					zsLst.add(cates.getData());
				}
				double d=zsLst.get(0);
				if(zsLst.size() >0){
					for(int j=0;j<zsLst.size()-1;j++){
						zsyieldLst.add(df.format((zsLst.get(j)-d)/d*100));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		remap.put("dates", dates);
		remap.put("yields", yield);
		remap.put("zsyieldLst", zsyieldLst);
		return  remap;
		
	}
	
	/**
	 * 查看收益率走势
	 * @author zhaodc
	 * @param code
	 * @return
	 */
	public Map qryFinanceYields(String code){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String,Object> remap=new HashMap<String, Object>();
		map1.put("financecode",code);
		Map m=null;
		List<String> dates=null;
		List<Double> yieldLst=null;
		try {
			m = financeBasicDataMapper.getFinanceTrend(map1);
			
			double period =  Double.parseDouble(m.get("PERIOD").toString());
			double maxyield =  Double.parseDouble(m.get("EXP_YIELD_MAX").toString());
			double startMoney =  Double.parseDouble(m.get("START_MONEY").toString());
			String startTime = m.get("VALUE_DATE").toString().substring(0,10);
			String endTime = m.get("EXPIRY_DATE").toString().substring(0,10);
			map2.put("startTime", startTime);
			map2.put("endTime", endTime);
			dates = financeBasicDataMapper.getListDays(map2);
			List<Double> list = new ArrayList<Double>();
			yieldLst = new ArrayList<Double>();
			Double yields = 0.00;
			for(int i=0;i<= period;i++){
				double d = startMoney +(startMoney*maxyield/360)*i;
				list.add(d);
			}
			if(list.size() >0){
				for(int j=0;j<list.size()-1;j++){
					yields = CommonCal.calculateYieldRatio(list.get(j+1),list.get(j));
					yieldLst.add(yields);
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		remap.put("yieldLst",yieldLst);
		remap.put("dates",dates);
 		return remap;
	}
	
	
}
