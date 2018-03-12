package com.yihuisoft.product.category.entity.dto;

public class ProductAssetInfo {

	private String code;
	private String name;
	private Integer categoryBigCode;
	private Integer categorySmallCode;
	private String categoryBigName;
	private String categorySmallName;
	private String bidCode;
	
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
	public Integer getCategoryBigCode() {
		return categoryBigCode;
	}
	public void setCategoryBigCode(Integer categoryBigCode) {
		this.categoryBigCode = categoryBigCode;
	}
	public Integer getCategorySmallCode() {
		return categorySmallCode;
	}
	public void setCategorySmallCode(Integer categorySmallCode) {
		this.categorySmallCode = categorySmallCode;
	}
	public String getCategoryBigName() {
		return categoryBigName;
	}
	public void setCategoryBigName(String categoryBigName) {
		this.categoryBigName = categoryBigName;
	}
	public String getCategorySmallName() {
		return categorySmallName;
	}
	public void setCategorySmallName(String categorySmallName) {
		this.categorySmallName = categorySmallName;
	}
	
	
	public String getBidCode() {
		return bidCode;
	}
	public void setBidCode(String bidCode) {
		this.bidCode = bidCode;
	}
	@Override
	public String toString() {
		return "ProductAssetInfo [code=" + code + ", name=" + name + ", categoryBigCode=" + categoryBigCode
				+ ", categorySmallCode=" + categorySmallCode + ", categoryBigName=" + categoryBigName
				+ ", categorySmallName=" + categorySmallName + "]";
	}
	
	
	
	
	
	
}
