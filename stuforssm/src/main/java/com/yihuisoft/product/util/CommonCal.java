package com.yihuisoft.product.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		try {
			startDate = sdf.parse(sdf.format(startDate));
			endDate = sdf.parse(sdf.format(endDate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			Long time1 = cal.getTimeInMillis();
			cal.setTime(endDate);
			Long time2 = cal.getTimeInMillis();
			days = (int) ((time2 - time1)/ (1000*60*24));
		} catch (ParseException e) {
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




}
