package com.yihuisoft.product.fund.service;

import com.yihuisoft.product.fund.entity.dao.FundBasicDAO;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.fund.mapper.FundMapper;
import com.yihuisoft.product.util.CommonCal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyinyuo on 2018/1/18.
 */
@Service
public class FundService {
    @Autowired
    private FundMapper fundMapper;

    /**
     * wangyinuo	2018-01-23
     * 返回基金的风险率和收益率序列
     * @param productCode	基金代码
     * @param startTime		开始时间
     * @param endTime		结束时间
     * @return
     */
    public List<FundProductDAO> getFundYieldRisk(String productCode, String startTime, String endTime){
        Map<String,String> map=new HashMap<String,String>();
        map.put("productCode", productCode);
    	if(startTime == null && endTime == null) {
        	Calendar c = Calendar.getInstance();c.setTime(new Date());
            c.add(Calendar.YEAR, -1);
            Date y = c.getTime();
            startTime = new SimpleDateFormat("yyyy-MM-dd").format(y);
            endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            map.put("startTime", startTime);
            map.put("endTime", endTime);
    	}else if("".equalsIgnoreCase(startTime) &&"".equalsIgnoreCase(endTime)) {
        	Calendar c = Calendar.getInstance();c.setTime(new Date());
            c.add(Calendar.YEAR, -1);
            Date y = c.getTime();
            startTime = new SimpleDateFormat("yyyy-MM-dd").format(y);
            endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            map.put("startTime", startTime);
            map.put("endTime", endTime);
    	}else {
            map.put("startTime", startTime);
            map.put("endTime", endTime);
    	}
        map.put("cycle", "day");
        return fundMapper.getFundYieldRisk(map);
    }
    
    /**
     * wangyinuo	2018-01-23
     * @param productCode	产品代码
     * @param startTime		开始时间	
     * @param endTime		结束时间
     * @param cycle			查询周期（day：天，week：周，month：月，year：年）
     * @param calculateType	预期风险率计算类型
     * @param tarReturn		目标收益率
     * @return
     */
    public List<FundBasicDAO> getFundBasicInfo(String productCode, String startTime, String endTime, String cycle,String calculateType,Double tarReturn){
    	List<FundBasicDAO> fundBasicList = fundMapper.getFundBasicInfo(productCode);
    	Map<String,String> map=new HashMap<String,String>();
    	if(startTime == null && endTime == null) {
        	Calendar c = Calendar.getInstance();c.setTime(new Date());
            c.add(Calendar.YEAR, -1);
            Date y = c.getTime();
            startTime = new SimpleDateFormat("yyyy-MM-dd").format(y);
            endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            map.put("startTime", startTime);
            map.put("endTime", endTime);
    	}else if("".equalsIgnoreCase(startTime) &&"".equalsIgnoreCase(endTime)) {
        	Calendar c = Calendar.getInstance();c.setTime(new Date());
            c.add(Calendar.YEAR, -1);
            Date y = c.getTime();
            startTime = new SimpleDateFormat("yyyy-MM-dd").format(y);
            endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            map.put("startTime", startTime);
            map.put("endTime", endTime);
    	}else {
            map.put("startTime", startTime);
            map.put("endTime", endTime);
    	}
        map.put("cycle", cycle);
        List<Double> doubleList = new ArrayList<Double>();
        double growth = 0D;//计算涨幅
        Double expected_annualized_return = 0D;//计算预期收益率
        Double expected_risk_ratio = 0D;//计算预期风险率
        Double Maxdrawdown = 0D;//计算最大回撤
        List<FundProductDAO> list = new ArrayList<FundProductDAO>();
        for (FundBasicDAO fundBasicDAO : fundBasicList) {
            map.put("productCode", fundBasicDAO.getCODE());
        	list = fundMapper.getFundYieldRisk(map);
            //计算涨幅
        	growth = (list.get(list.size()-1).getNavaDj()-list.get(0).getNavaDj())/list.get(0).getNavaDj();
            fundBasicDAO.setGrowth(growth);
            //计算预期收益率
            for (FundProductDAO fundProductDAO : list) {
            	doubleList.add(fundProductDAO.getYieldRatio());
            }
            expected_annualized_return = CommonCal.calculateGeometricMean(doubleList)*220;
            fundBasicDAO.setExpected_annualized_return(expected_annualized_return);
            //计算预期风险率
            if ("standard".equals(calculateType) || "".equals(calculateType) || calculateType == null) { //标准差计算预期风险率
	            doubleList.clear();
	            for (FundProductDAO fundProductDAO : list) {
	            	doubleList.add(fundProductDAO.getRiskRatio());
	            }
	            expected_risk_ratio = CommonCal.calculateGeometricMean(doubleList)*Math.sqrt(220);
            }else if ("semiVariance".equals(calculateType)) { //半方差计算预期风险率
            	expected_risk_ratio = CommonCal.calculateSemiVariance(doubleList)*Math.sqrt(220);
            }else if ("leftVariance".equals(calculateType)) { //左偏动差计算预期风险率
            	expected_risk_ratio = CommonCal.calculateLeftVariance(doubleList,tarReturn)*Math.sqrt(220);
            }
            fundBasicDAO.setExpected_risk_ratio(expected_risk_ratio);
            doubleList.clear();
            //计算最大回撤
            if (!"day".equalsIgnoreCase(cycle)) {
                map.put("cycle", "day");
                list = fundMapper.getFundYieldRisk(map);
            }
            for (FundProductDAO fundProductDAO : list) {
            	doubleList.add(fundProductDAO.getNavaDj());
            }
            if (doubleList.size()>1) {
	            Maxdrawdown = CommonCal.calculateMaxdrawdown(doubleList);
	            fundBasicDAO.setMaxdrawdown(Maxdrawdown);
            }
            doubleList.clear();
        }
    	return fundBasicList;
    }
}
