package com.yihuisoft.product.customegroup.entity;

/**
 * @ClassName:  ProductGroupDetails   
 * @Description:组合产品的信息   
 * @author: tangjian
 * @date:   2018年1月20日 上午11:34:09
 */
public class ProductGroupDetails {

	private String productCode;// 产品代码
	private String productGroupName;// 产品组合名称
	private Double proportion;// 产品占比
	private Double yieldRadio;// 收益率
	private Double riskRadio;// 风险率
	private String productGroupId;// 组合id
	private String navDate; // 净值日期
	private Double navaDj; // 单位净值


	public Double getNavaDj() {
		return navaDj;
	}

	public void setNavaDj(Double navaDj) {
		this.navaDj = navaDj;
	}

	public String getNavDate() {
		return navDate;
	}

	public void setNavDate(String navDate) {
		this.navDate = navDate;
	}


	public Double getYieldRadio() {
		return yieldRadio;
	}

	public void setYieldRadio(Double yieldRadio) {
		this.yieldRadio = yieldRadio;
	}

	public Double getRiskRadio() {
		return riskRadio;
	}

	public void setRiskRadio(Double riskRadio) {
		this.riskRadio = riskRadio;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	@Override
	public String toString() {
		return "ProductGroupDetails [productCode=" + productCode
				+ ", productGroupName=" + productGroupName + ", proportion="
				+ proportion + ", navDate=" + navDate + ", navaDj=" + navaDj + "]";
	}


}
