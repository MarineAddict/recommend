package com.yihuisoft.product.schedule.product_category.entity;

import java.util.Date;

public class DepositBasicData {

	private Integer id;
	private String code;
	private String name;
	private Date subStartDay;
	private Date subEndDay;
	private Date intStartDay;
	private Date intEndDay;
	private Double interest;
	private Integer theTerm;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Integer getTheTerm() {
		return theTerm;
	}
	public void setTheTerm(Integer theTerm) {
		this.theTerm = theTerm;
	}
	@Override
	public String toString() {
		return "DepositBasicData [id=" + id + ", code=" + code + ", name=" + name + ", subStartDay=" + subStartDay
				+ ", subEndDay=" + subEndDay + ", intStartDay=" + intStartDay + ", intEndDay=" + intEndDay
				+ ", interest=" + interest + ", theTerm=" + theTerm + "]";
	}
	
	
	
	
}
