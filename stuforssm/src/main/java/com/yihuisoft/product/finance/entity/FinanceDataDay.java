package com.yihuisoft.product.finance.entity;


public class FinanceDataDay{

	private String id; // 实体id
	private String financeCode; // 理财产品代码
	private Double yieldRatio; //收益率
	private Double riskRatio; //风险率
	private String navDate; // 净值日期
	private Double navaDj; // 单位净值
	
	@Override
	public String toString() {
		return "FinanceDataDay [id=" + id + ", financeCode=" + financeCode
				+ ", yieldRatio=" + yieldRatio + ", riskRatio=" + riskRatio
				+ ", navDate=" + navDate + ", navaDj=" + navaDj + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFinanceCode() {
		return financeCode;
	}
	public void setFinanceCode(String financeCode) {
		this.financeCode = financeCode;
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
	

}