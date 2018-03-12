package com.yihuisoft.product.schedule.product_category.entity;

public class ProductCategoryData {
	/*实体标识*/
	private Integer Id;	
	/*体系编号*/
	private String SystemCode;
	/*小类*/
	private Integer CategorySmall;
	/*产品代码*/
	private String PrdCode;
	/*产品类型*/
	private Integer prdType;
	
	
	
	public Integer getPrdType() {
		return prdType;
	}
	public void setPrdType(Integer prdType) {
		this.prdType = prdType;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getSystemCode() {
		return SystemCode;
	}
	public void setSystemCode(String systemCode) {
		SystemCode = systemCode;
	}
	public Integer getCategorySmall() {
		return CategorySmall;
	}
	public void setCategorySmall(Integer categorySmall) {
		CategorySmall = categorySmall;
	}
	public String getPrdCode() {
		return PrdCode;
	}
	public void setPrdCode(String prdCode) {
		PrdCode = prdCode;
	}
	

	
	public ProductCategoryData(String systemCode, Integer categorySmall, String prdCode, Integer prdType) {
		super();
		SystemCode = systemCode;
		CategorySmall = categorySmall;
		PrdCode = prdCode;
		this.prdType = prdType;
	}
	@Override
	public String toString() {
		return "ProductCategoryData [Id=" + Id + ", SystemCode=" + SystemCode + ", CategorySmall=" + CategorySmall
				+ ", PrdCode=" + PrdCode + "]";
	}
	
}
