package com.yihuisoft.product.finance.entity;


public class FinanceBasicData {

	private String id; // 实体id
	private String financeCode; // 理财产品代码
	private Double expYieldMax; // 期望最大收益率
	private Double realYield; // 真实收益率
	private String valueDate; // 起息日
	private String expiryDate; // 到息
	private Double expYieldMin; // 最小期望收益率
	private String risklevel; // 风险等级
	private Integer calculateCovariance;//是否计算协方差（1是 0 否）calculateCovariance
	private String 	financeName;//理财产品名称
	private Integer financeStatus;//理财产品状态
	private Double startMoney;//理财产品状态

	public Double getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(Double startMoney) {
		this.startMoney = startMoney;
	}

	public Integer getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(Integer financeStatus) {
		this.financeStatus = financeStatus;
	}

	public String getFinanceName() {
		return financeName;
	}

	public void setFinanceName(String financeName) {
		this.financeName = financeName;
	}

	@Override
	public String toString() {
		return "FinanceBasicData [id=" + id + ", financeCode=" + financeCode
				+ ", expYieldMax=" + expYieldMax + ", realYield=" + realYield
				+ ", valueDate=" + valueDate + ", expiryDate=" + expiryDate
				+ ", expYieldMin=" + expYieldMin + ", risklevel=" + risklevel
				+ ", calculateCovariance=" + calculateCovariance + ", financeName=" + financeName + ","
						+ " financeStatus=" + financeStatus + ", startMoney=" + startMoney + "]";
	}

	public Integer getCalculateCovariance() {
		return calculateCovariance;
	}

	public void setCalculateCovariance(Integer calculateCovariance) {
		this.calculateCovariance = calculateCovariance;
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

	public Double getExpYieldMax() {
		return expYieldMax;
	}

	public void setExpYieldMax(Double expYieldMax) {
		this.expYieldMax = expYieldMax;
	}

	public Double getRealYield() {
		return realYield;
	}

	public void setRealYield(Double realYield) {
		this.realYield = realYield;
	}

	
	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Double getExpYieldMin() {
		return expYieldMin;
	}

	public void setExpYieldMin(Double expYieldMin) {
		this.expYieldMin = expYieldMin;
	}

	public String getRisklevel() {
		return risklevel;
	}

	public void setRisklevel(String risklevel) {
		this.risklevel = risklevel;
	}
}
