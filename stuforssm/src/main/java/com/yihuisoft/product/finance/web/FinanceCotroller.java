package com.yihuisoft.product.finance.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihuisoft.product.finance.entity.FinanceBasicData;
import com.yihuisoft.product.finance.entity.FinanceDataDay;
import com.yihuisoft.product.finance.service.FinanceService;

@Controller
@RequestMapping("/finance")
public class FinanceCotroller {

	@Autowired
	private FinanceService financeService;

	/**
	 * @Description:收益率曲线
	 * @author: tangjian
	 * @param productCode产品代码
	 * @param startTime起始时间
	 * @param endTime 结束时间
	 * @return List<FinanceDataDay>
	 */
	@RequestMapping("/getFinanceHistoryIncomeLine")
	@ResponseBody
	public List<FinanceDataDay> getFinanceHistoryIncomeLine(String productCode,
			String startTime, String endTime) {
		List<FinanceDataDay> financeDataDays = financeService
				.getFinanceHistoryIncomeLine(productCode, startTime, endTime);
		return financeDataDays;
	}

	/**
	 * @Title: getFinanceList
	 * @author tangjian
	 * @Description: 产品理财产品列表(通过产品状态，产品名称，产品代码)
	 * @return List<FinanceBasicData>
	 * @throws JsonProcessingException 
	 * @date: 2018年1月19日 上午11:44:43
	 */
	@RequestMapping("/getFinanceList")
	@ResponseBody
	public String  getFinanceList(@RequestParam int page,@RequestParam int rows,String financeName, String financeCode,String financeType,String financeRiskLevel, Integer financeStatus) throws JsonProcessingException {
		int start = (page-1)*rows+1;
        int end = page*rows;
		List<FinanceBasicData> financeBasicDatas = financeService.getFinanceList(start,end,financeName,financeCode,financeType,financeRiskLevel,financeStatus);
		int total= financeService.qryFinanceListRows(financeName,financeCode,financeType,financeRiskLevel,financeStatus);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", financeBasicDatas);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}


	/**
	 * @author tangjian
	 * @Description: 删除理财产品
	 * @param financeCode
	 * @return int
	 * @date: 2018年1月20日 下午4:03:44
	 */
	@RequestMapping("/delFinancePro")
	@ResponseBody
	public int delFinancePro(String productCode) {
		int delFlag = financeService.deleteFinancePro(productCode);
		return delFlag;
	}

	/**
	 * 预期收益率
	 * @author tangjian
	 * @param financeCode
	 * @param startTime
	 * @param endTime
	 * @param calType
	 * @return Double
	 * @date: 2018年1月22日 下午5:00:35
	 */
	@RequestMapping("/getFinanceExpIncome")
	@ResponseBody
	public Double getFinanceExpIncome(String productCode) {
		Double yieldRatio = financeService.calFinanceExpIncome(productCode);
		return yieldRatio;

	}

	/**
	 * 预期风险率
	 * 
	 * @author tangjian
	 * @param financeCode
	 * @param startTime
	 * @param endTime
	 * @param calType
	 * @return Double
	 * @date: 2018年1月22日 下午5:00:35
	 */
	@RequestMapping("/getFinanceExpRisk")
	@ResponseBody
	public Double getFinanceExpRisk(String productCode) {
		Double riskRatio = financeService.calFinanceExpRisk(productCode);
		return riskRatio;
	}
	/**
	 * @author tangjian
	 * @Description: 某段时间的增长率
	 * @param financeCode
	 * @param startTime
	 * @param endTime
	 * @return Double
	 * @date: 2018年1月22日 下午9:44:11
	 */
	@RequestMapping("/getFinanceExpIncrease")
	@ResponseBody
	public Double getFinanceExpIncrease(String productCode, String startTime,
			String endTime) {
		Double gorwthRatio = financeService.getFinanceExpIncrease(
				productCode, startTime, endTime);
		return gorwthRatio;
	}
	
	/**
	 * @Title: getFinanceMaxdrawdown 
	 * @author tangjian
	 * @Description:計算最大回撤
	 * @param financeCode
	 * @param startTime
	 * @param endTime
	 * @param calType maxdrawdown最大回撤
	 * @return  Double
	 * @date:   2018年1月22日 下午9:47:03
	 */
	@RequestMapping("/getFinanceMaxdrawdown")
	@ResponseBody
	public Double getFinanceMaxdrawdown(String productCode,
			String startTime, String endTime,String calType){
		Double maxdrawdown = financeService.getFinanceMaxdrawdown(productCode, startTime, endTime, calType);
		return maxdrawdown;
	}
	/**
	 * 通过目标收益率求对应风险率
	 * @author tangjian
	 * @param financeCode
	 * @param expYield
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/calRiskMakeLeftVariance")
	@ResponseBody
	public Double calRiskMakeLeftVariance(String productCode,Double targetYield,String startTime,String endTime){
		Double riskRatio = financeService.calRiskMakeLeftVariance(productCode, targetYield, startTime, endTime);
		return riskRatio;
	}
	/**
	 * 通过历史收益率计算风险率（半方差）
	 * @author tangjian
	 * @param financeCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/calRiskSemiVariance")
	@ResponseBody
	public Double calRiskSemiVariance(String productCode,String startTime,String endTime){
		Double riskRatio = financeService.calRiskSemiVariance(productCode, startTime, endTime);
		return riskRatio;
	}
	
	
	/**
	 * 加载理财基本信息
	 * @author zhaodc
	 * @param financecode
	 * @return
	 */
	@RequestMapping("/toFinanceDetail")
	@ResponseBody
	public ModelAndView toFinanceDetail(String code){
		ModelAndView mv= new ModelAndView();
		Map map= financeService.qryFinanceDetail(code);
		mv.addObject("finance", map);
		mv.setViewName("product/finance/financeDetail");
		return mv;
	}
	
	/**
	 * 理财涨幅曲线
	 * @author zhaodc
	 * @param code
	 * @return
	 */
	@RequestMapping("/toFinanceAchie")
	@ResponseBody
	public Map toFinanceAchie(String code,String bidcode){
		return financeService.qryFinanceTrend(code,bidcode);
	}
	
	/**
	 * 理财收益率曲线
	 * @author zhaodc
	 * @param code
	 * @return
	 */
	@RequestMapping("/toFinanceYields")
	@ResponseBody
	public Map toFinanceYields(String code){
		return financeService.qryFinanceYields(code);
	}
	
	
	
}
