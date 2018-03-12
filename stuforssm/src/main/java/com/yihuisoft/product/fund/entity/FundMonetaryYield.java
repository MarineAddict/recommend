package com.yihuisoft.product.fund.entity;

/**
 * 货币型收益
 * @author zhaodc
 *
 */
public class FundMonetaryYield {
	private String code;
	private double serevenyield;
	private double millionyield;
	private String pdate;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getSerevenyield() {
		return serevenyield;
	}
	public void setSerevenyield(double serevenyield) {
		this.serevenyield = serevenyield;
	}
	public double getMillionyield() {
		return millionyield;
	}
	public void setMillionyield(double millionyield) {
		this.millionyield = millionyield;
	}
	public String getPdate() {
		return pdate;
	}
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
	
}
