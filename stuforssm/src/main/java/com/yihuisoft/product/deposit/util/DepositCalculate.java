package com.yihuisoft.product.deposit.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihuisoft.product.util.CommonCal;

public class DepositCalculate {
	
	/**
	 * 计算一段时间的日增长率（存款产品）
	 * @param startMoney
	 * @param intStartDay
	 * @param intEndDay
	 * @param interest
	 * @return
	 */
	public static List<Double> calculateDailyIncrease(Double startMoney,Date intStartDay,Date intEndDay,Float interest,Integer term){
		List list=new ArrayList<>();
	/*	Map map=new HashMap<>();暂时不用，数据直接插入list*/
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");*/
		/*Calendar can=Calendar.getInstance();
		can.setTime(intStartDay);*/
		Integer days=CommonCal.calDateBetween(intStartDay, intEndDay);
		for(int day=0;day<=days;day++){
			
			
			Double increase=(startMoney*(interest/(term))*day)/startMoney;//增长率的公式
			list.add(increase);
		}
		return list;
		
	}
	
	

}
