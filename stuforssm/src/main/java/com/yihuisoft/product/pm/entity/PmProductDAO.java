package com.yihuisoft.product.pm.entity;

public class PmProductDAO {
    private Integer id;
    private String productCode;//贵金属代码
    private Double yieldRatio;//收益率
    private Double riskRatio;//风险率
    private String navDate;//净值日期
    private Double navaDj;//复权单位净值

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
