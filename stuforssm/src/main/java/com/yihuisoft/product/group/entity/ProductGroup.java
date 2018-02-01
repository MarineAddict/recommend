package com.yihuisoft.product.group.entity;

/**
 * Created by wangyinyuo on 2018/1/18.
 */
public class ProductGroup {
    private String id;
    private String productCode;
    private Double yieldRatio;
    private Double riskRatio;
    private String navDate;
    private Double navaDj;

    public String getId() {
        return id;
    }
    public void setId(String id) {
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
