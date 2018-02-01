package com.yihuisoft.product.pm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.pm.entity.PmBasic;
import com.yihuisoft.product.pm.entity.pmProduct;
import com.yihuisoft.product.pm.mapper.pmMapper;
import com.yihuisoft.product.util.CommonCal;

/** 
 * @Description:
 * 
 * @author 	:lixiaosong
 * @date 	:2018年1月17日 上午9:48:34 
 * @version :V1.0 
 */
@Service
public class pmService {
	
	private Logger logger=LoggerFactory.getLogger(pmService.class);
	
	private static String pyexe;
	private static String pypmsrc;
	
	
	@Autowired
	private pmMapper pmmapper;
	
	/**
	 * 
	 * @Description:获取收益率曲线数据
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月17日 下午3:33:39
	 *
	 * @Title getYieldRatioLine 
	 * @param productCode
	 * @param startTime
	 * @param endTime
	 * @return 
	 * @return List<pmProduct>
	 */
	public List<pmProduct> getYieldRatioLine(String productCode,String startTime,String endTime){
		Map<String,String> map=new HashMap<String,String>();
		List<pmProduct> list=new ArrayList<pmProduct>();
		map.put("productCode", productCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("cycle", "day");
		try {
			list= pmmapper.getYieldRatioLine(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取贵金属收益率列表失败："+e.getMessage());
		}
		 return list;
	}
	
	/**
	 * 
	 * @Description:获取贵金属产品代码列表
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月19日 下午1:17:16
	 *
	 * @Title getProductCodeList 
	 * @return 
	 * @return List<String>
	 */
	public List<String> getProductCodeList(){
		
		List<String> list=new ArrayList<String>();
		try {
			list=pmmapper.getProductCodeList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取贵金属产品代码列表失败："+e.getMessage());
			return null;
		}
		return list;
	}
	/**
	 * 
	 * @Description:获取贵金属列表
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月22日 下午5:04:39
	 *
	 * @Title getPmList 
	 * @return 
	 * @return List<PmBasic>
	 */
	public List<PmBasic> getPmList(String productCode,String name,String releaseDate){
		Map<String,String> map=new HashMap<String,String> ();
		map.put("productCode", productCode);
		map.put("name", name);
		map.put("releaseDate", releaseDate);
		return pmmapper.getPmList(map);
	}
	
	/**
	 * 
	 * @Description:计算涨幅、预期收益率、预期风险率、最大回撤
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月23日 上午10:40:53
	 *
	 * @Title getCaculateValue 
	 * @param productCode
	 * @param startTime
	 * @param endTime
	 * @param cycle
	 * @return 
	 * @return Map<String,String>
	 */
	public Map<String,String> getCaculateValue(String productCode,String startTime,String endTime,String cycle){
		CommonCal commonCal = new CommonCal();
		List<Double> doubleList = new ArrayList<Double>();
		Map<String,String> map=new HashMap<String,String>();
		//结果集
		Map<String,String> resultMap=new HashMap<String,String>();
        map.put("productCode", productCode);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("cycle", cycle);
        List<pmProduct> list= pmmapper.getYieldRatioLine(map);
        //计算涨幅
        double growth = (list.get(list.size()-1).getNavaDj()-list.get(0).getNavaDj())/list.get(0).getNavaDj();
        resultMap.put("growth",growth+"");
        
        //计算预期收益率
        for (pmProduct pm : list) {
        	doubleList.add(pm.getYieldRatio());
        }
        Double expected_annualized_return = commonCal.calculateGeometricMean(doubleList);
        resultMap.put("expected_annualized_return", expected_annualized_return+"");
        doubleList.clear();
        
        //计算预期风险率
        for (pmProduct pm : list) {
        	doubleList.add(pm.getRiskRatio());
        }
        Double expected_risk_ratio = commonCal.calculateGeometricMean(doubleList);
        resultMap.put("expected_risk_ratio", expected_risk_ratio+"");
        doubleList.clear();
        
        //计算最大回撤
        if (!"day".equalsIgnoreCase(cycle)) {
            map.put("cycle", "day");
            list = pmmapper.getYieldRatioLine(map);
        }
        for (pmProduct pm : list) {
        	doubleList.add(pm.getNavaDj());
        }
        Double Maxdrawdown = commonCal.calculateMaxdrawdown(doubleList);
        resultMap.put("Maxdrawdown", Maxdrawdown+"");
        doubleList.clear();
        return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description:调用底层python方法
	 *
	 * @author: lixiaosong
	 * @date  : 2018年1月17日 下午3:34:48
	 *
	 * @Title CallPythonMethod 
	 * @return 
	 * @return int
	 */
	public int CallPythonMethod(String IndName){
		//读取python配置文件
		Properties prop=new Properties();
		InputStream in = pmService.class.getClassLoader().getResourceAsStream("python.properties");
		try {
			prop.load(in);
			pyexe=prop.getProperty("PythonExePath").trim();
			pypmsrc=prop.getProperty("PythonPmPath").trim();
			logger.info("Python路径:"+pyexe);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取python配置文件出错:"+e.getMessage());
		}
		
		String exe_param=pypmsrc+"\\"+IndName+".py";
		logger.info("脚本文件路径:"+exe_param);
		ProcessBuilder pb=null;
		pb=new ProcessBuilder(pyexe,exe_param);
		pb.directory(new File(pypmsrc));
		Process proc=null;
		try {
			proc=pb.start();
			String res = readProcessOutput(proc);
			logger.info(res);

			proc.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("调用Python文件出错:"+e.getMessage());
			return 0;
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("调用Python文件出错:"+e.getMessage());
			return 0;
		}
		logger.info("调用Python文件结束");
		return 1;
	}

	private String readProcessOutput(final Process process) {
		return read(process.getErrorStream(), System.out);
		//return read(process.getInputStream(), System.out);
		
	}

	private String read(InputStream inputStream, PrintStream out) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	
}
