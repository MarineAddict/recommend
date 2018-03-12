package com.yihuisoft.product.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonCal {

    private static final Logger logger= LoggerFactory.getLogger(CommonCal.class);
	private static final DecimalFormat df = new DecimalFormat(".00000");		//保留 5 位
	/**
	 * @Title: calDateBetween 
	 * @author tangjian
	 * @Description: 计算两个时间相差天数
	 * @param startDate
	 * @param endDate
	 * @return  Integer 
	 * @date:   2018年1月17日 下午4:38:27
	 */
	public static Integer calDateBetween(Date startDate,Date endDate){
		Integer days = 0;
		Calendar cal=Calendar.getInstance();
		try {
			cal.setTime(startDate);
			while(cal.getTimeInMillis()<endDate.getTime()){
				days++;
				cal.add(cal.DATE, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}


	/**
	 * @author yongquan.xiong
	 * @Description: 计算几何平均数
	 * @date:   2018年1月22日 下午3:38:27
	 * @Param: x 变量序列 （如 收益率序列 or 风险率序列）
	 */
	public static Double calculateGeometricMean(List<Double> x){
		int m=x.size();
		if(m > 0){
			double sum=1;
			for(int i=0;i<m;i++){//计算x值的累乘
				if(x.get(i)!=null){
					sum*=(x.get(i)+1);
				}
			}
			return Double.parseDouble(df.format(Math.pow(sum,1.0/m) -1)) ;//返回sum的m次方根  保留 5 位
		}else{
			return null;
		}
	}


    /**
     * @author yongquan.xiong
     * @Description: 计算最大回撤
     * @date:   2018年1月22日
     * @Param: data(list) 收益价格序列 （如 基金的复权单位净值 序列）
     */
    public static Double calculateMaxdrawdown(List<Double> data){
        int length=data.size();
        List<Double> maxdraw=new ArrayList<Double>();
        if(length > 1){
            for(int i=length-1;i>0;i--){
                if(i != 0){
                    if(data.get(i) <= 0){
                        logger.info("Data cannot have values <= 0");
                        return null ;
                    }
                    Double maxValue= Collections.max(data.subList(0,i));
                    Double temp=data.get(i)/maxValue - 1;
                    maxdraw.add(temp);
                }
            }
        }else{
            logger.info("The length of data must > 1");
            return null;
        }
        maxdraw.add(0.0);   //当数据都为非负数 时 添加 默认值 0.0

        return  Double.parseDouble(df.format(Collections.min(maxdraw)));	//保留 5 位

    }

	/**
	 * @author yongquan.xiong
	 * @Description: 计算夏普比率
	 * @date:   2018年1月23日
	 * @Param:  exceptReturn	组合收益率
	 * @Param:  riskFreeReturn	无风险利率
	 * @Param:  exceptRisk		组合风险率
	 */
	public static Double calculateSharpRatio(Double exceptReturn,Double riskFreeReturn,Double exceptRisk){
		Double sharpeRatio=null;
		if(exceptRisk > 0){
			sharpeRatio=Double.parseDouble(df.format((exceptReturn - riskFreeReturn)/exceptRisk));	  //保留 5 位
		}
		return sharpeRatio;
	}

	/**
	 * @author yongquan.xiong
	 * @Description: 计算半方差
	 * @date:   2018年1月29日
	 * @Param:  yield	收益率
	 */
	public static Double calculateSemiVariance(List<Double> yield){
		if(yield!=null && yield.size()>1){
			double sum1=0;
			for(Double x1 : yield){
				sum1+=x1;
			}
			double avg=sum1/yield.size();	//计算平均数
			double sum2=0;
			for(Double x2 : yield){
				sum2+=Math.pow(Math.min(0,(x2-avg)),2);		//求和
			}
			return Double.parseDouble(df.format(Math.sqrt(sum2/(yield.size()-1))));		//开平方 保留 5 位
		}
			return null;
	}

	/**
	 * @author yongquan.xiong
	 * @Description: 计算左偏动差
	 * @date:   2018年1月29日
	 * @Param:  yield	收益率
	 * @Param:  tarReturn	目标收益率
	 */
	public static Double calculateLeftVariance(List<Double> yield,Double tarReturn){
		if(yield!=null && yield.size()>1){
			double sum=0;
			for(Double x : yield){
				sum+=Math.pow(Math.max(0,(tarReturn-x)),2);		//求和
			}
			return Double.parseDouble(df.format(Math.sqrt(sum/(yield.size()-1))));		//开平方 保留 5 位
		}
		return null;
	}

	/**
	 * @author yongquan.xiong
	 * @Description: 计算相关系数矩阵
	 * @date:   2018年02月06日
	 * @Param:  closeMapList	收盘价序列
	 */
	public static List<List<Double>> calculateCorrelationCoefficent(List<Map<String,List<Double>>> closeMapList){

		List<List<Double>> correlationMatrix=new ArrayList<>();
		if(closeMapList !=null && closeMapList.size()>0){

			List<Map<String,List<Double>>> yieldListMap=new ArrayList<>();

			for(int i=0;i<closeMapList.size();i++){

				Map<String,List<Double>> closeMap=closeMapList.get(i);
				Map<String,List<Double>> tempYieldMap=new HashMap<>();
				if(closeMap!=null && closeMap.size()>0){
					Iterator<Map.Entry<String,List<Double>>> entries = closeMap.entrySet().iterator();
					if( (entries.hasNext())){

						Map.Entry<String,List<Double>> entry = entries.next();
						String code=entry.getKey();
						List<Double> tempCloseList=entry.getValue();
						List<Double> yieldList=new ArrayList<>();
						//计算 收益率
						if(tempCloseList !=null && tempCloseList.size()>0){
							for(int j=0;j<tempCloseList.size()-1;j++){
								double yield=calculateYieldRatio(tempCloseList.get(j+1),tempCloseList.get(j));
								yieldList.add(yield);
							}
						}
						tempYieldMap.put(code,yieldList);

					}
				}
				yieldListMap.add(tempYieldMap);

			}
			//计算相关系数矩阵
			correlationMatrix=calculateCorrelationMatrix(yieldListMap);
		}
		return correlationMatrix;
	}


	/**
	 * @author yongquan.xiong
	 * @Description: 计算相关系数矩阵
	 * @date:   2018年02月06日
	 * @Param:  yieldMapList	收益率序列
	 */
	public static List<List<Double>> calculateCorrelationMatrix(List<Map<String,List<Double>>> yieldMapList) {
		List<List<Double>> correlationList = new ArrayList<>();
		if (yieldMapList != null && yieldMapList.size() > 0) {

			List<Map<String, List<Double>>> correlationListMap = new ArrayList<>();
			List<List<Double>> tempCorrelationList=new ArrayList<>();
			for (int i = 0; i < yieldMapList.size(); i++) {

				Map<String, List<Double>> yieldMap = yieldMapList.get(i);
				if(yieldMap!=null && yieldMap.size()>0){
					Iterator<Map.Entry<String,List<Double>>> entries = yieldMap.entrySet().iterator();
					if( (entries.hasNext())){
						Map.Entry<String,List<Double>> entry = entries.next();
						String code=entry.getKey();
						List<Double> tempYieldList=entry.getValue();

						List<Double> tempCorrelationList1=new ArrayList<>();
						//做第二次 循环
						for (int j = 0; j < yieldMapList.size(); j++) {
							Map<String, List<Double>> yieldMap1 = yieldMapList.get(j);
							if(yieldMap1!=null && yieldMap1.size()>0){
								Iterator<Map.Entry<String,List<Double>>> entries1 = yieldMap1.entrySet().iterator();
								if( (entries1.hasNext())){
									Map.Entry<String,List<Double>> entry1 = entries1.next();
									String code1=entry1.getKey();
									List<Double> tempYieldList1=entry1.getValue();

									//	计算相关系数
									Double correlation=calculateCorrelation(tempYieldList,tempYieldList1);
									tempCorrelationList1.add(correlation);

								}
							}

						}
						//Collections.reverse(tempCorrelationList1);	//list 逆序
						tempCorrelationList.add(tempCorrelationList1);

					}
				}

			}

			return tempCorrelationList;

		}
		return null;
	}

	/*
 	* 计算相关系数方法
 	*/
	public static Double calculateCorrelation(List<Double> temp1,List<Double> temp2){
		//相关系数
		double correlation=0d;

		//协方差
		Double covariance=getCovariance(temp1.toArray(new Double[0]),temp2.toArray(new Double[0]));
		//标准差
		Double standardDiviation1=StandardDiviation(temp1.toArray(new Double[0]));
		Double standardDiviation2=StandardDiviation(temp2.toArray(new Double[0]));
		if(standardDiviation1 !=0 && standardDiviation2 !=0 ){
			correlation=covariance/(standardDiviation1*standardDiviation2);
		}
		return correlation;
	}


	/*
 	* 计算收益率方法
 	*/
	public static Double calculateYieldRatio(Double navadj1,Double navadj2){
		Double yieldRatio=0d;
		if(navadj2!=0){
			yieldRatio = Math.log(navadj1/navadj2);

		}
		return yieldRatio;

	}

	/*
 	* 计算协方差
 	*/
	public static Double getCovariance(Double[] xArray, Double[] yArray){

		Covariance covariance=new Covariance();

		double[] Xd = new double[xArray.length];
		for (int i = 0; i < xArray.length; i++)
		{
			Xd[i] =  xArray[i];
		}

		double[] Yd = new double[yArray.length];
		for (int i = 0; i < yArray.length; i++)
		{
			Yd[i] =  yArray[i];
		}

		//调用Common Math 计算协方差, 且 须 （Yd）Xd.length>=2
		Double covarianceVal =covariance.covariance(Xd,Yd);
		return covarianceVal;
	}


	//计算(样本标准差 (n-1) )
	public static Double StandardDiviation(Double[] x) {
		int m=x.length;
		double sum=0;
		for(int i=0;i<m;i++){//求和
			sum+=x[i];
		}
		double dAve=sum/m;//求平均值
		double dVar=0;
		for(int i=0;i<m;i++){//求方差
			dVar+=(x[i]-dAve)*(x[i]-dAve);
		}
		if(m>1){
			return Math.sqrt(dVar/(m-1));
		}
		return 0d;
	}
	
	/**
	 * 计算涨幅（数据位置要和日期对应好）
	 * @param data
	 * @return
	 */
	public static List<Double> IncreaseAmount(List<Double> data){
		List<Double> list=new ArrayList<>();
		Double base=data.get(0);
		for(Double d:data){
			list.add(((d-base)/base)*100);
		}
		return list;
	}
	
	
	/**
	 * 
	 *返回起始日和结束日之间的所有日期为List
	 */
	public static List<String> StringDateListInALength(Date startDay,Date EndDate){
		List<String> list=new ArrayList<String>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();

		cal.setTime(startDay);
		while(cal.getTimeInMillis()<=EndDate.getTime()){
			String date=sdf.format(cal.getTime());
			list.add(date);
			cal.add(cal.DATE, 1);
		}
     return list;
		
		
	}
	
	


}
