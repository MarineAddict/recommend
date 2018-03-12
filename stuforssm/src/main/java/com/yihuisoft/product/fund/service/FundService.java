package com.yihuisoft.product.fund.service;

import com.yihuisoft.product.finance.service.FinanceService;
import com.yihuisoft.product.fund.entity.FundMonetaryYield;
import com.yihuisoft.product.fund.entity.FundTrack;
import com.yihuisoft.product.fund.entity.dao.FundBasicDAO;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.fund.mapper.FundMapper;
import com.yihuisoft.product.pm.service.pmService;
import com.yihuisoft.product.util.CommonCal;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangyinyuo on 2018/1/18.
 */
@Service
public class FundService {
    @Autowired
    private FundMapper fundMapper;

	private Logger logger = LoggerFactory.getLogger(FundService.class);

	private static final DecimalFormat df = new DecimalFormat(".0000");		//保留 4 位

	private static String pyexe;
	private static String pyfundsrc;

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
    
    
    
    
    /**
     * 基金列表
     * @author zhaodc 2018-02-01
     * @param start
     * @param end
     * @param starTime
     * @param endTime
     * @return
     */
    public List<FundBasicDAO> qryFundBasicInfoList(int start,int end,String code,String starTime,String endTime,String scode,String funStatus,String fundTyp,String cxfunTyp){
    	Map map =new HashMap();
    	map.put("start", start);
    	map.put("end", end);
    	map.put("code", code);
    	map.put("starTime", starTime);
    	map.put("endTime", endTime);
    	map.put("scode",scode);
    	map.put("funStatus", funStatus);
    	map.put("fundTyp", fundTyp);
    	map.put("cxfunTyp", cxfunTyp);
    	List<FundBasicDAO> fundLst=null;
		try {
			fundLst = fundMapper.getFundBasicInfoList(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询基金产品失败:"+e.getMessage());
		}finally{
			return fundLst;
		}
    }
    
    /**
     * 基金列表sss
     * @author zhaodc 
     * @param start
     * @param end
     * @param code
     * @param starTime
     * @param endTime
     * @param funStatus
     * @return
     */
    public int qryFundBasicInfoListRows(int start,int end,String code,String starTime,String endTime,String scode,String funStatus,String fundTyp,String cxfunTyp){
    	Map map =new HashMap();
    	map.put("start", start);
    	map.put("end", end);
    	map.put("code", code);
    	map.put("starTime", starTime);
    	map.put("endTime", endTime);
    	map.put("scode",scode);
    	map.put("funStatus", funStatus);
    	map.put("fundTyp", fundTyp);
    	map.put("cxfunTyp", cxfunTyp);
    	int rows=0;
		try {
			rows = fundMapper.getFundBasicInfoListRows(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询基金产品失败:"+e.getMessage());
		}finally{
			return rows;
    	}
    }
    
    /**
     * 查询基金基本信息
     * @author zhaodc 2018-03-01
     * @param code
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map qryFundDetail(String fundCode,String bidCode){
    	Map map=new HashMap();
    	String trackError="0.00%";
		try {
			map = fundMapper.getFundDetail(fundCode);
			trackError=qryFundTrackEorror(fundCode, bidCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询基金基本信息失败："+e.getMessage());
		}finally{
			map.put("trackError", trackError);
			return map;
		}
    	
    }
    
    /**
     * 计算基金跟踪误差
     * @author zhaodc
     * @param fundCode
     * @param bidCode
     * @return
     */
    public  String qryFundTrackEorror(String fundCode,String bidCode){
    	
    	double trackError=0.00;
    	double fdyield=0.00;
    	double zsyield=0.00;
    	String trackrror = "0.00%";
    	
    	List<FundTrack> lst=null;
    	List<Double> fdLst = new ArrayList<Double>();
    	List<Double> zsLst = new ArrayList<Double>();
		try {
			lst=fundMapper.getFundTrackList(fundCode,bidCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询基金指数失败："+e.getMessage());
		}
		for(FundTrack li:lst){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("计算基金收益率，指数收益率失败："+e.getMessage());
		}
    	Double[] objs = cx.toArray(new Double[0]);
    	DecimalFormat  dfs  = new DecimalFormat("######0.0000");
		try {
			//trackError = CommonCal.StandardDiviation(objs)*Math.sqrt(252);
			trackError = CommonCal.StandardDiviation(objs)*100;
			trackrror=dfs.format(trackError)+"%";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("计算基金跟踪误差失败："+e.getMessage());
		}
    	return trackrror;
    }
    /**
     * 基金收益指数走势
     * @author zhaodc
     * @param fundCode
     * @param bidCode
     * @return
     */
    public Map qryFundTrack(String fundCode,String sCode,String bidCode,Integer days,String startTime,String endTime,String flag){
    	Map<String, Object> map=new HashMap<String, Object>();
    	Map<String, Object> m=new HashMap<String, Object>();
    	m.put("fundcode", fundCode);
    	m.put("scode", sCode);
    	m.put("bidcode", bidCode);
    	m.put("days", days);
    	m.put("startTime", startTime);
    	m.put("endTime", endTime);
    	m.put("flag", flag);
    	
    	List<FundTrack> lst =null;
		try {
			lst = fundMapper.getFundIncomeTrend(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询基金指数涨幅走势失败："+e.getMessage());
		}
    	String fdyield;
    	String zsyield;
    	List<Double> fdLst = new ArrayList<Double>();
    	List<Double> zsLst = new ArrayList<Double>();
    	List<String> dtLst = new ArrayList<String>();
    	for(FundTrack li:lst){
    		fdLst.add(li.getNavadj());
    		zsLst.add(li.getData());
    		dtLst.add(li.getNavlatestdate().substring(0, 10));
    	}

    	double d1 = fdLst.get(0);
    	double d2 = zsLst.get(0);
    	List<String> zsyieldLst = new ArrayList<String>();
    	List<String> fdyieldLst = new ArrayList<String>();
    	DecimalFormat  df  = new DecimalFormat("######0.0000"); 
    	if(fdLst.size() >0 && zsLst.size()>0){
			for(int j=0;j<fdLst.size()-1;j++){
				//fdyield = CommonCal.calculateYieldRatio(fdLst.get(j+1),fdLst.get(j));
				//zsyield = CommonCal.calculateYieldRatio(zsLst.get(j+1),zsLst.get(j));
				//zsyield = ((zsLst.get(j)-3264.926)/3264.926)*100;
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


	/**
	 * @deac 基金回撤走势图
	 * @param startDate：开始时间
	 * 		   endDate：结束时间
	 * 		   productCode：产品代码
	 * 		   benchmarkCode: 基准（指数）代码
	 * @return
	 */
	public Map qryDrawdowns(String startDate,String endDate,String productCode,String benchmarkCode){
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("startDate",startDate);
		paramMap.put("endDate",endDate);
		paramMap.put("productCode",productCode);
		paramMap.put("benchmarkCode",benchmarkCode);

		List<FundTrack> list = fundMapper.findDataForDrawdown(paramMap);
		List<Double> fundData=new ArrayList<>();	//基金收益率
		List<Double> benchmarkData=new ArrayList<>();		//基准（指数）收益率
		List<String> dates=new ArrayList<>();		//对应日期

		List<String> fundDrawdowns=new ArrayList<>();	//基金回撤数据
		List<String> benchmarkDrawdowns=new ArrayList<>();		//基准（指数）回撤数据

		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				//取出对应数据
				fundData.add(list.get(i).getNavadj());
				benchmarkData.add(list.get(i).getData());
				dates.add(list.get(i).getNavlatestdate());
				//计算每天的回撤
				fundDrawdowns.add(df.format(fundData.get(i)/ Collections.max(fundData) -1));
				benchmarkDrawdowns.add(df.format(benchmarkData.get(i)/Collections.max(benchmarkData) -1));
			}
		}

		//返回结果
		Map<String,List<String>> resultMap=new HashMap<>();
		resultMap.put("dates",dates);
		resultMap.put("fundDrawdowns",fundDrawdowns);
		resultMap.put("benchmarkDrawdowns",benchmarkDrawdowns);


		return resultMap;
	}

    /**
     * 基金单位净值走势
     * @author zhaodc
     * @param code
     * @return
     */
    public List<FundTrack> qryFundNetValue(String code,Integer days,String startTime,String endTime,String flag){
    	List<FundTrack> list=null;
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("code", code);
    	map.put("days", days);
    	map.put("startTime", startTime);
    	map.put("endTime", endTime);
    	map.put("flag", flag);
    	try {
			list= fundMapper.getFundNetValue(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询基金净值列表失败："+e.getMessage());
		}
    	return list;
    }
    
    /**
     * 货币型基金万份收益、七日年化
     * @author zhaodc
     * @param code
     * @param startTime
     * @param endTime
     * @return
     */
    public List<FundMonetaryYield>  qrygetFundMonetaryYield(String code,String startTime,String endTime,Integer days,String flag){
    	Map map=new HashMap();
    	map.put("code", code);
    	map.put("startTime", startTime);
    	map.put("endTime", endTime);
    	map.put("days", days);
    	map.put("flag", flag);
    	List<FundMonetaryYield> list=null;
    	try {
			list = fundMapper.getFundMonetaryYieldList(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询货币型基金收益列表失败："+e.getMessage());
		}
    	return list;
    }
    
    /**
     * 计算基金标准差、夏普比率
     * @author zhaodc
     * @param code
     * @param startTime
     * @param endTime
     * @return
     */
    public Map qryFundNetSharp(String code,String startTime,String endTime){
    	double standard=0.00;
    	double sharp=0.00;
    	double fd=0.00;
    	Map map1=new HashMap();
    	Map map=new HashMap();
    	map.put("code", code);
    	map.put("startTime", startTime);
    	map.put("endTime", endTime);
    	List<FundProductDAO> list=null;
    	List<Double> yieldLst=new ArrayList<Double>();
    	try {
			list= fundMapper.getFundNetYieldList(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询基金利率表失败："+e.getMessage());
		}
    	for(FundProductDAO li :list){
    		yieldLst.add(li.getYieldRatio());
    	}
    	Double[] objs = yieldLst.toArray(new Double[0]);
    	try {
			standard = CommonCal.StandardDiviation(objs)*Math.sqrt(220);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("计算基金标准差失败："+e.getMessage());
		}
    	try {
			fd =CommonCal.calculateYieldRatio(list.get(list.size()-1).getNavaDj(),list.get(0).getNavaDj());
			sharp = CommonCal.calculateSharpRatio(fd,0.0135,standard);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("计算基金sharp失败："+e.getMessage());
		}finally{
			map1.put("standard", standard);
			map1.put("sharp", sharp);
			return map1;
    	}
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
			pyfundsrc=prop.getProperty("PythonPmPath").trim();
			logger.info("Python路径:"+pyexe);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取python配置文件出错:"+e.getMessage());
		}

		String exe_param=pyfundsrc+"\\"+IndName+".py";
		logger.info("脚本文件路径:"+exe_param);
		ProcessBuilder pb=null;
		pb=new ProcessBuilder(pyexe,exe_param);
		pb.directory(new File(pyfundsrc));
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
		//return read(process.getErrorStream(), System.out);
		return read(process.getInputStream(), System.out);
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
