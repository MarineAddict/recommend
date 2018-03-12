package com.yihuisoft.product.pm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.*;

import com.yihuisoft.product.fund.entity.FundTrack;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.pm.entity.PmProductDAO;
import com.yihuisoft.product.pm.entity.PmTrack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private static final DecimalFormat df = new DecimalFormat(".0000");		//保留 4 位
	
	private static String pyexe;
	private static String pypmsrc;
	
	
	@Autowired
	private pmMapper pmMapper;
	
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
			list= pmMapper.getYieldRatioLine(map);
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
			list=pmMapper.getProductCodeList();
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
		return pmMapper.getPmList(map);
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
        List<pmProduct> list= pmMapper.getYieldRatioLine(map);
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
            list = pmMapper.getYieldRatioLine(map);
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

	
	
	
	/**
	 * 分页查询贵金属
	 * @author zhaodc
	 * @param preCode
	 * @param preName
	 * @param releaseDate
	 * @return
	 */
	public List<PmBasic> qryPmList(int start, int end, String preCode,
			String preName, String releaseDate,String preStatus) {
		Map map=new HashMap();
		map.put("start",start);
		map.put("end",end);
		map.put("preCode", preCode);
		map.put("preName", preName);
		map.put("releaseDate", releaseDate);
		map.put("preStatus", preStatus);
		List<PmBasic> list= null;
		try {
			list=pmMapper.getPmBasicList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		}
	}

	/**
	 * @author zhaodc
	 * @param start
	 * @param end
	 * @param preCode
	 * @param preName
	 * @param releaseDate
	 * @return
	 */
	public int qryPmListRows(int start, int end, String preCode,
			String preName, String releaseDate,String preStatus) {
		Map map=new HashMap();
		map.put("start",start);
		map.put("end",end);
		map.put("preCode", preCode);
		map.put("preName", preName);
		map.put("releaseDate", releaseDate);
		map.put("preStatus", preStatus);
		int rows=0;
		try {
			rows=pmMapper.getPmBasicListRows(map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			return rows;
		}
	}

    public Map qryPmDetail(String code,String bidCode) {
		Map map=new HashMap();
		String trackError="0.00%";
		try {
			map = pmMapper.getPmDetail(code);
			trackError=qryPmTrackEorror(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询贵金属基本信息失败："+e.getMessage());
		}finally{
			map.put("trackError", trackError);
			return map;
		}
    }


	/**
	 * 计算贵金属跟踪误差
	 * @author lixiaosong
	 * @param pmCode
	 * @return
	 */
	public  String qryPmTrackEorror(String pmCode){

		double trackError=0.00;
		double fdyield=0.00;
		double zsyield=0.00;
		String trackrror = "0.00%";

		List<PmTrack> lst=null;
		List<Double> fdLst = new ArrayList<Double>();
		List<Double> zsLst = new ArrayList<Double>();
		try {
			lst=pmMapper.getPmTrackList(pmCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询贵金属指数失败："+e.getMessage());
		}
		for(PmTrack li:lst){
			fdLst.add(li.getNavadj());
			zsLst.add(li.getData());
		}
		List<Double> zsyieldLst = new ArrayList<Double>();
		List<Double> fdyieldLst = new ArrayList<Double>();

		List<Double> cx=new ArrayList<Double>();
		try {
			if(fdLst.size() >0 && zsLst.size()>0){
				for(int j=0;j<fdLst.size()-1;j++){
					fdyield = CommonCal.calculateYieldRatio(fdLst.get(j+1),fdLst.get(j));
					zsyield = CommonCal.calculateYieldRatio(zsLst.get(j+1),zsLst.get(j));
					fdyieldLst.add(fdyield);
					zsyieldLst.add(zsyield);
					cx.add(zsyield-fdyield);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("计算贵金属收益率，指数收益率失败："+e.getMessage());
		}
		Double[] objs = cx.toArray(new Double[0]);
		DecimalFormat dfs  = new DecimalFormat("######0.0000");
		try {
			//trackError = CommonCal.StandardDiviation(objs)*Math.sqrt(252);
			trackError = CommonCal.StandardDiviation(objs)*100;
			trackrror=dfs.format(trackError)+"%";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("计算贵金属跟踪误差失败："+e.getMessage());
		}
		return trackrror;
	}

	public Map qryPmTrack(String code, Integer days, String startTime, String endTime, String flag) {
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("code", code);
		m.put("days", days);
		m.put("startTime", startTime);
		m.put("endTime", endTime);
		m.put("flag", flag);

		List<PmTrack> lst =null;
		try {
			lst = pmMapper.getPmIncomeTrend(m);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询贵金属指数涨幅走势失败："+e.getMessage());
		}
		String fdyield;
		String zsyield;
		List<Double> fdLst = new ArrayList<Double>();
		List<Double> zsLst = new ArrayList<Double>();
		List<String> dtLst = new ArrayList<String>();
		for(PmTrack li:lst){
			fdLst.add(li.getNavadj());
			zsLst.add(li.getData());
			dtLst.add(li.getNavlatestdate().substring(0,10));
		}

		double d1 = fdLst.get(0);
		double d2 = zsLst.get(0);
		List<String> zsyieldLst = new ArrayList<String>();
		List<String> fdyieldLst = new ArrayList<String>();
		DecimalFormat  df  = new DecimalFormat("######0.0000");
		if(fdLst.size() >0 && zsLst.size()>0){
			for(int j=0;j<fdLst.size()-1;j++){
				fdyield = df.format((fdLst.get(j)-d1)/d1*100);
				zsyield = df.format(((zsLst.get(j)-d2)/d2)*100);
				fdyieldLst.add(fdyield);
				zsyieldLst.add(zsyield);
			}
		}
		map.put("fdyieldLst", fdyieldLst);
		map.put("zsyieldLst", zsyieldLst);
		map.put("dtLst", dtLst);
		return map;
	}

	public List<PmTrack> qryPmNetValue(String code, Integer days, String startTime, String endTime, String flag) {
		List<PmTrack> list=new ArrayList<PmTrack>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code", code);
		map.put("days", days);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("flag", flag);
		try {
			list= pmMapper.getPmNetValue(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询贵金属净值列表失败："+e.getMessage());
		}
		return list;
	}


	public Map qryDrawdowns(String startDate, String endDate, String productCode) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("startDate",startDate);
		paramMap.put("endDate",endDate);
		paramMap.put("code",productCode);
		paramMap.put("flag",1);

		List<PmTrack> list = pmMapper.getPmIncomeTrend(paramMap);
		List<Double> pmData=new ArrayList<>();	//贵金属收益率
		List<Double> benchmarkData=new ArrayList<>();		//基准（指数）收益率
		List<String> dates=new ArrayList<>();		//对应日期

		List<String> pmDrawdowns=new ArrayList<>();	//贵金属回撤数据
		List<String> benchmarkDrawdowns=new ArrayList<>();		//基准（指数）回撤数据

		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				//取出对应数据
				pmData.add(list.get(i).getNavadj());
				benchmarkData.add(list.get(i).getData());
				dates.add(list.get(i).getNavlatestdate());
				//计算每天的回撤
				pmDrawdowns.add(df.format(pmData.get(i)/ Collections.max(pmData) -1));
				benchmarkDrawdowns.add(df.format(benchmarkData.get(i)/Collections.max(benchmarkData) -1));
			}
		}

		//返回结果
		Map<String,List<String>> resultMap=new HashMap<>();
		resultMap.put("dates",dates);
		resultMap.put("pmDrawdowns",pmDrawdowns);
		resultMap.put("benchmarkDrawdowns",benchmarkDrawdowns);


		return resultMap;
	}

	public Map qryPmNetSharp(String code, String startTime, String endTime) {
		double standard=0.00;
		double sharp=0.00;
		double fd=0.00;
		Map map1=new HashMap();
		Map map=new HashMap();
		map.put("code", code);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<PmProductDAO> list=null;
		List<Double> yieldLst=new ArrayList<Double>();
		try {
			list= pmMapper.getPmNetYieldList(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询贵金属利率表失败："+e.getMessage());
		}
		for(PmProductDAO li :list){
			yieldLst.add(li.getYieldRatio());
		}
		Double[] objs = yieldLst.toArray(new Double[0]);
		try {
			standard = CommonCal.StandardDiviation(objs)*Math.sqrt(220);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("计算贵金属标准差失败："+e.getMessage());
		}
		try {
			fd =CommonCal.calculateYieldRatio(list.get(list.size()-1).getNavaDj(),list.get(0).getNavaDj());
			sharp = CommonCal.calculateSharpRatio(fd,0.0135,standard);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("计算贵金属sharp失败："+e.getMessage());
		}finally{
			map1.put("standard", standard);
			map1.put("sharp", sharp);
			return map1;
		}
	}
}
