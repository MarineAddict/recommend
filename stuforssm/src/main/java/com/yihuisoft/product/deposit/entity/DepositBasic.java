package com.yihuisoft.product.deposit.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yihuisoft.product.deposit.util.DepositCalculate;

/**
 *  Description ：存单基本信息
 *  @author     ：Machengxin
 *  @Date       ：2018/1/25-11:05
 *  @version    ：V_1.0.0
 */
public class DepositBasic {
	
    private String code;
    private String name;
    private Date subStartDay;
    private Date subEndDay;
    private Date intStartDay;
    private Date intEndDay;
    private float interest;
    private int theTerm;
    private Integer categorySmallCode;
    private String categorySmallName;
    private Integer categoryBigCode;
    private String categoryBigName;
    private Integer sysCode;
    private String sysName;
    private String traceName;
    private Integer traceCode;
    private Double startMoney;
    
    
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getSubStartDay() {
		return subStartDay;
	}
	public void setSubStartDay(Date subStartDay) {
		this.subStartDay = subStartDay;
	}
	public Date getSubEndDay() {
		return subEndDay;
	}
	public void setSubEndDay(Date subEndDay) {
		this.subEndDay = subEndDay;
	}
	public Date getIntStartDay() {
		return intStartDay;
	}
	public void setIntStartDay(Date intStartDay) {
		this.intStartDay = intStartDay;
	}
	public Date getIntEndDay() {
		return intEndDay;
	}
	public void setIntEndDay(Date intEndDay) {
		this.intEndDay = intEndDay;
	}
	public float getInterest() {
		return interest;
	}
	public void setInterest(float interest) {
		this.interest = interest;
	}
	public int getTheTerm() {
		return theTerm;
	}
	public void setTheTerm(int theTerm) {
		this.theTerm = theTerm;
	}
	public Integer getCategorySmallCode() {
		return categorySmallCode;
	}
	public void setCategorySmallCode(Integer categorySmallCode) {
		this.categorySmallCode = categorySmallCode;
	}
	public String getCategorySmallName() {
		return categorySmallName;
	}
	public void setCategorySmallName(String categorySmallName) {
		this.categorySmallName = categorySmallName;
	}
	public Integer getCategoryBigCode() {
		return categoryBigCode;
	}
	public void setCategoryBigCode(Integer categoryBigCode) {
		this.categoryBigCode = categoryBigCode;
	}
	public String getCategoryBigName() {
		return categoryBigName;
	}
	public void setCategoryBigName(String categoryBigName) {
		this.categoryBigName = categoryBigName;
	}
	public Integer getSysCode() {
		return sysCode;
	}
	public void setSysCode(Integer sysCode) {
		this.sysCode = sysCode;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getTraceName() {
		return traceName;
	}
	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}
	public Integer getTraceCode() {
		return traceCode;
	}
	public void setTraceCode(Integer traceCode) {
		this.traceCode = traceCode;
	}
	public Double getStartMoney() {
		return startMoney;
	}
	public void setStartMoney(Double startMoney) {
		this.startMoney = startMoney;
	}
	
	/**
	 * 计算该产品的涨幅
	 * @param basic
	 * @return
	 *//*
    public static List countDailyIncrease(Date){
    	List list=null;
    	Date intEndDay=basic.getIntEndDay();//结息日
    	Date intStartDay=basic.getIntStartDay();//起息日
    	Double startMoney=basic.getStartMoney();//最低存款额
    	Float interest=basic.getInterest();//利率
    	Integer term=basic.getTheTerm();//投资周期
    	//根据起息日结息日判断情况
    	
    list= DepositCalculate.calculateDailyIncrease(startMoney, intStartDay, intEndDay, interest, term);
 
    	
    	return list;
    }*/
	
	
    
}
