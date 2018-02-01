package com.yihuisoft.product.deposit.entity;
/** 
 * @Description:
 * 
 * @author 	:lixiaosong
 * @date 	:2018年1月18日 下午3:44:32 
 * @version :V1.0 
 */
public class Deposit {
	private Integer id;
	private String productCode;
	private Double yieldRatio;
	private Double riskRatio;
	private String navDate;
	private Double navaDj;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Double getYieldRatio() {
		return yieldRatio;
	}
	public void setYieldRatio(Double yieldRatio) {
		this.yieldRatio = yieldRatio;
	}
	public Double getRiskRatio() {
		return riskRatio;
	}
	public void setRiskRatio(Double riskRatio) {
		this.riskRatio = riskRatio;
	}
	public String getNavDate() {
		return navDate;
	}
	public void setNavDate(String navDate) {
		this.navDate = navDate;
	}
	public Double getNavaDj() {
		return navaDj;
	}
	public void setNavaDj(Double navaDj) {
		this.navaDj = navaDj;
	}
}
