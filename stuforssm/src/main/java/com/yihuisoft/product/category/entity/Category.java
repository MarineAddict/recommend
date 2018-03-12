package com.yihuisoft.product.category.entity;

/**
 * 产品类别
 * @author zhaodc 2018-02-02
 *
 */

public class Category{
	
	private String bgCode;
	private String smCode;
	private String bgName;
	private String smName;
	private String bidName ;//标的名称
	private String bidCode ;//标的名称
	
	public String getBgCode() {
		return bgCode;
	}
	public void setBgCode(String bgCode) {
		this.bgCode = bgCode;
	}
	public String getSmCode() {
		return smCode;
	}
	public void setSmCode(String smCode) {
		this.smCode = smCode;
	}
	public String getBgName() {
		return bgName;
	}
	public void setBgName(String bgName) {
		this.bgName = bgName;
	}
	public String getSmName() {
		return smName;
	}
	public void setSmName(String smName) {
		this.smName = smName;
	}
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getBidCode() {
		return bidCode;
	}
	public void setBidCode(String bidCode) {
		this.bidCode = bidCode;
	}
	
}