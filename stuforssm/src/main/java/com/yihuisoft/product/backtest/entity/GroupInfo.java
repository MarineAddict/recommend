package com.yihuisoft.product.backtest.entity;

public class GroupInfo {
    private String groupCode;   //组合id
    private String groupName; //组合名称
    private Double weights; //产品权重
    private String createDate;    //创建日期
    private Double asset;   //资产额度
    private Double yieldRatio;  //收益率
    private Double riskRatio;   //风险率
    private String riskLevel;   //风险级别

    private String productCode;   //产品code
    private String productName; //产品名称
    private String productType;//产品类型

    private String moneySum;    //资产金额
    private String growth;  //涨幅
    private String drawDowns;   //回撤

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getWeights() {
        return weights;
    }

    public void setWeights(Double weights) {
        this.weights = weights;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Double getAsset() {
        return asset;
    }

    public void setAsset(Double asset) {
        this.asset = asset;
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(String moneySum) {
        this.moneySum = moneySum;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getDrawDowns() {
        return drawDowns;
    }

    public void setDrawDowns(String drawDowns) {
        this.drawDowns = drawDowns;
    }
}
