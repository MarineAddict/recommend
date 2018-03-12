package com.yihuisoft.product.macrofactor.entity;

public class MARCO_MONEY_SUPPLY {
    private Integer id;
    private String  ms_date;//货币供应量日期
    private Double m2;//货币和准货币[数量(亿元)]
    private Double  m2_yoy;//货币和准货币同比增长率
    private Double m2_mom;//货币和准货币环比增长率
    private Double m1;//货币[数量(亿元)]
    private Double m1_yoy;//货币同比增长率
    private Double m1_mom;//货币环比增长率
    private Double m0;//流通中的现金[数量(亿元)]
    private Double m0_yoy;//流通中的现金同比增长率
    private Double m0_mom;//流通中的现金环比增长率

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMs_date() {
        return ms_date;
    }

    public void setMs_date(String ms_date) {
        this.ms_date = ms_date;
    }

    public Double getM2() {
        return m2;
    }

    public void setM2(Double m2) {
        this.m2 = m2;
    }

    public Double getM2_yoy() {
        return m2_yoy;
    }

    public void setM2_yoy(Double m2_yoy) {
        this.m2_yoy = m2_yoy;
    }

    public Double getM2_mom() {
        return m2_mom;
    }

    public void setM2_mom(Double m2_mom) {
        this.m2_mom = m2_mom;
    }

    public Double getM1() {
        return m1;
    }

    public void setM1(Double m1) {
        this.m1 = m1;
    }

    public Double getM1_yoy() {
        return m1_yoy;
    }

    public void setM1_yoy(Double m1_yoy) {
        this.m1_yoy = m1_yoy;
    }

    public Double getM1_mom() {
        return m1_mom;
    }

    public void setM1_mom(Double m1_mom) {
        this.m1_mom = m1_mom;
    }

    public Double getM0() {
        return m0;
    }

    public void setM0(Double m0) {
        this.m0 = m0;
    }

    public Double getM0_yoy() {
        return m0_yoy;
    }

    public void setM0_yoy(Double m0_yoy) {
        this.m0_yoy = m0_yoy;
    }

    public Double getM0_mom() {
        return m0_mom;
    }

    public void setM0_mom(Double m0_mom) {
        this.m0_mom = m0_mom;
    }
}
