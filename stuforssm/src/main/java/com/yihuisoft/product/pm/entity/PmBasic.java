package com.yihuisoft.product.pm.entity;
/** 
 * @Description:
 * 
 * @author 	:lixiaosong
 * @date 	:2018年1月22日 下午4:44:50 
 * @version :V1.0 
 */
public class PmBasic {
	private Integer id;
	private String productCode;
	private String CALCULATE_COVARIANCE;
	private String name;
	private String status;
	private String material;
	private Double weight;
	private String releaseDate;
	private String riskLevel;
	private String unit;
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}

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
	public String getCALCULATE_COVARIANCE() {
		return CALCULATE_COVARIANCE;
	}
	public void setCALCULATE_COVARIANCE(String cALCULATE_COVARIANCE) {
		CALCULATE_COVARIANCE = cALCULATE_COVARIANCE;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	

}
