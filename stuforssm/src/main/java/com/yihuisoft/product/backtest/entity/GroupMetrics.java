package com.yihuisoft.product.backtest.entity;

import java.util.List;

public class GroupMetrics {

    private String errorInfo;//错误信息

    private String[] codeList;
    private String[] codeNameList;
    private String bestYear;
    private String cagr; //复合年化收益率
    private String marketCorrelation; //市场相关
    private String maxDrawdown;	 //最大回撤
    private String sharpeRatio; //sharp比率

    private String sortinoRatio; //sortino比率
    private String stdev; //标准差
    private String worstYear;	//最大亏损
    private String alphaAnnualized;  //阿尔法(年化)
    private String beta; //Beta(*)

    private String compoundReturnAnnualized; //复合回报（年化）
    private String compoundReturnMonthly; //复合收益（每月）
    private String conditionalValue; //有条件的风险价值
    private String deltaNormalValue;  //delta-Normal分析风险价值
    private String downsideDeviationMonthly; //下行偏差（月度）

    private String excessKurtosis; //峰度
    private String financialBalance; //最终价值
    private String gainLossRatio;//收益/损失率
    private String historicalValue; //历史风险价值
    private String meanReturnAnnualized; //平均回报（年化）

    private String meanReturnMonthly; //平均回报（每月）
    private String positivePeriods; //正期间
    private String R2; //R^2
    private String skewness; //偏态
    private String treynorRatio; //特雷诺比比率

    private String volatilityMonthly; //波动（每月）
    private String volatilityYearly; //波动（年化）
    private String diversificationRatio; //多样化比例
    // 以上 为 标量

    private String[] monthEndMoneyDate; //资金对应日期 （ 年月）
    private String[] monthEndMoneyValueArr; //对应每月资金
    private String[] monthRetValueArr;  //对应每月收益
    private String[] drawdownsArr;  //回撤序列
    private List<Object[]> corrList;    //相关系数
    private String[] assetIndexTitleArr;    //表格1 标题
    private List<String[]> assetIndexValuesList;    //表格1 数据
    private String[] assetTitleArr; //表格 2 标题
    private List<String[]> assetValuesList;    //表格2 数据
    private String[] BenchmarkDateArr;  //标的日期
    private String[] monthEndBenchmarkMoneyValueArr; //每月基准资金
    private String[] monthRetBenchmarkRetValueArr;  //每月基准收益率
    private String[] monthBenchmarkDrawdownArr;  //每月基准回撤
    private String[] monthMoneyGrowth;  //组合资金涨幅
    private String[] benchmarkMoneyGrowth;  //基准资金涨幅

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String[] getCodeList() {
        return codeList;
    }

    public void setCodeList(String[] codeList) {
        this.codeList = codeList;
    }

    public String[] getCodeNameList() {
        return codeNameList;
    }

    public void setCodeNameList(String[] codeNameList) {
        this.codeNameList = codeNameList;
    }

    public String getBestYear() {
        return bestYear;
    }

    public void setBestYear(String bestYear) {
        this.bestYear = bestYear;
    }

    public String getCagr() {
        return cagr;
    }

    public void setCagr(String cagr) {
        this.cagr = cagr;
    }

    public String getMarketCorrelation() {
        return marketCorrelation;
    }

    public void setMarketCorrelation(String marketCorrelation) {
        this.marketCorrelation = marketCorrelation;
    }

    public String getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(String maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public String getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(String sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public String getSortinoRatio() {
        return sortinoRatio;
    }

    public void setSortinoRatio(String sortinoRatio) {
        this.sortinoRatio = sortinoRatio;
    }

    public String getStdev() {
        return stdev;
    }

    public void setStdev(String stdev) {
        this.stdev = stdev;
    }

    public String getWorstYear() {
        return worstYear;
    }

    public void setWorstYear(String worstYear) {
        this.worstYear = worstYear;
    }

    public String getAlphaAnnualized() {
        return alphaAnnualized;
    }

    public void setAlphaAnnualized(String alphaAnnualized) {
        this.alphaAnnualized = alphaAnnualized;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getCompoundReturnAnnualized() {
        return compoundReturnAnnualized;
    }

    public void setCompoundReturnAnnualized(String compoundReturnAnnualized) {
        this.compoundReturnAnnualized = compoundReturnAnnualized;
    }

    public String getCompoundReturnMonthly() {
        return compoundReturnMonthly;
    }

    public void setCompoundReturnMonthly(String compoundReturnMonthly) {
        this.compoundReturnMonthly = compoundReturnMonthly;
    }

    public String getConditionalValue() {
        return conditionalValue;
    }

    public void setConditionalValue(String conditionalValue) {
        this.conditionalValue = conditionalValue;
    }

    public String getDeltaNormalValue() {
        return deltaNormalValue;
    }

    public void setDeltaNormalValue(String deltaNormalValue) {
        this.deltaNormalValue = deltaNormalValue;
    }

    public String getDownsideDeviationMonthly() {
        return downsideDeviationMonthly;
    }

    public void setDownsideDeviationMonthly(String downsideDeviationMonthly) {
        this.downsideDeviationMonthly = downsideDeviationMonthly;
    }

    public String getExcessKurtosis() {
        return excessKurtosis;
    }

    public void setExcessKurtosis(String excessKurtosis) {
        this.excessKurtosis = excessKurtosis;
    }

    public String getFinancialBalance() {
        return financialBalance;
    }

    public void setFinancialBalance(String financialBalance) {
        this.financialBalance = financialBalance;
    }

    public String getGainLossRatio() {
        return gainLossRatio;
    }

    public void setGainLossRatio(String gainLossRatio) {
        this.gainLossRatio = gainLossRatio;
    }

    public String getHistoricalValue() {
        return historicalValue;
    }

    public void setHistoricalValue(String historicalValue) {
        this.historicalValue = historicalValue;
    }

    public String getMeanReturnAnnualized() {
        return meanReturnAnnualized;
    }

    public void setMeanReturnAnnualized(String meanReturnAnnualized) {
        this.meanReturnAnnualized = meanReturnAnnualized;
    }

    public String getMeanReturnMonthly() {
        return meanReturnMonthly;
    }

    public void setMeanReturnMonthly(String meanReturnMonthly) {
        this.meanReturnMonthly = meanReturnMonthly;
    }

    public String getPositivePeriods() {
        return positivePeriods;
    }

    public void setPositivePeriods(String positivePeriods) {
        this.positivePeriods = positivePeriods;
    }

    public String getR2() {
        return R2;
    }

    public void setR2(String r2) {
        R2 = r2;
    }

    public String getSkewness() {
        return skewness;
    }

    public void setSkewness(String skewness) {
        this.skewness = skewness;
    }

    public String getTreynorRatio() {
        return treynorRatio;
    }

    public void setTreynorRatio(String treynorRatio) {
        this.treynorRatio = treynorRatio;
    }

    public String getVolatilityMonthly() {
        return volatilityMonthly;
    }

    public void setVolatilityMonthly(String volatilityMonthly) {
        this.volatilityMonthly = volatilityMonthly;
    }

    public String getDiversificationRatio() {
        return diversificationRatio;
    }

    public void setDiversificationRatio(String diversificationRatio) {
        this.diversificationRatio = diversificationRatio;
    }

    public String[] getMonthEndMoneyDate() {
        return monthEndMoneyDate;
    }

    public void setMonthEndMoneyDate(String[] monthEndMoneyDate) {
        this.monthEndMoneyDate = monthEndMoneyDate;
    }

    public String[] getMonthEndMoneyValueArr() {
        return monthEndMoneyValueArr;
    }

    public void setMonthEndMoneyValueArr(String[] monthEndMoneyValueArr) {
        this.monthEndMoneyValueArr = monthEndMoneyValueArr;
    }

    public String[] getMonthRetValueArr() {
        return monthRetValueArr;
    }

    public void setMonthRetValueArr(String[] monthRetValueArr) {
        this.monthRetValueArr = monthRetValueArr;
    }

    public String[] getDrawdownsArr() {
        return drawdownsArr;
    }

    public void setDrawdownsArr(String[] drawdownsArr) {
        this.drawdownsArr = drawdownsArr;
    }

    public List<Object[]> getCorrList() {
        return corrList;
    }

    public void setCorrList(List<Object[]> corrList) {
        this.corrList = corrList;
    }

    public String[] getAssetIndexTitleArr() {
        return assetIndexTitleArr;
    }

    public void setAssetIndexTitleArr(String[] assetIndexTitleArr) {
        this.assetIndexTitleArr = assetIndexTitleArr;
    }

    public List<String[]> getAssetIndexValuesList() {
        return assetIndexValuesList;
    }

    public void setAssetIndexValuesList(List<String[]> assetIndexValuesList) {
        this.assetIndexValuesList = assetIndexValuesList;
    }

    public String[] getAssetTitleArr() {
        return assetTitleArr;
    }

    public void setAssetTitleArr(String[] assetTitleArr) {
        this.assetTitleArr = assetTitleArr;
    }

    public List<String[]> getAssetValuesList() {
        return assetValuesList;
    }

    public void setAssetValuesList(List<String[]> assetValuesList) {
        this.assetValuesList = assetValuesList;
    }

    public String getVolatilityYearly() {
        return volatilityYearly;
    }

    public void setVolatilityYearly(String volatilityYearly) {
        this.volatilityYearly = volatilityYearly;
    }

    public String[] getMonthEndBenchmarkMoneyValueArr() {
        return monthEndBenchmarkMoneyValueArr;
    }

    public void setMonthEndBenchmarkMoneyValueArr(String[] monthEndBenchmarkMoneyValueArr) {
        this.monthEndBenchmarkMoneyValueArr = monthEndBenchmarkMoneyValueArr;
    }

    public String[] getMonthRetBenchmarkRetValueArr() {
        return monthRetBenchmarkRetValueArr;
    }

    public void setMonthRetBenchmarkRetValueArr(String[] monthRetBenchmarkRetValueArr) {
        this.monthRetBenchmarkRetValueArr = monthRetBenchmarkRetValueArr;
    }

    public String[] getBenchmarkDateArr() {
        return BenchmarkDateArr;
    }

    public void setBenchmarkDateArr(String[] benchmarkDateArr) {
        BenchmarkDateArr = benchmarkDateArr;
    }

    public String[] getMonthBenchmarkDrawdownArr() {
        return monthBenchmarkDrawdownArr;
    }

    public void setMonthBenchmarkDrawdownArr(String[] monthBenchmarkDrawdownArr) {
        this.monthBenchmarkDrawdownArr = monthBenchmarkDrawdownArr;
    }

    public String[] getMonthMoneyGrowth() {
        return monthMoneyGrowth;
    }

    public void setMonthMoneyGrowth(String[] monthMoneyGrowth) {
        this.monthMoneyGrowth = monthMoneyGrowth;
    }

    public String[] getBenchmarkMoneyGrowth() {
        return benchmarkMoneyGrowth;
    }

    public void setBenchmarkMoneyGrowth(String[] benchmarkMoneyGrowth) {
        this.benchmarkMoneyGrowth = benchmarkMoneyGrowth;
    }
}
