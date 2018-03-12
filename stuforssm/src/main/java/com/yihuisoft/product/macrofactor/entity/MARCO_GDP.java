package com.yihuisoft.product.macrofactor.entity;

public class MARCO_GDP {
    private Integer id;
    private String gdp_date; //国内生产总值季度日期
    private Double gdp_total; //国内生产总值[绝对值(亿元)]
    private Double gdp_first; //第一产业[绝对值(亿元)]
    private Double gdp_second; //第二产业[绝对值(亿元)]
    private Double gdp_third; //第三产业[绝对值(亿元)]
    private Double gdp_total_yoy; //国内生产总值同比增长率
    private Double gdp_first_yoy; //第一产业同比增长率
    private Double gdp_second_yoy; //第二产业同比增长率
    private Double gdp_third_yoy; //第三产业同比增长率

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGdp_date() {
        return gdp_date;
    }

    public void setGdp_date(String gdp_date) {
        this.gdp_date = gdp_date;
    }

    public Double getGdp_total() {
        return gdp_total;
    }

    public void setGdp_total(Double gdp_total) {
        this.gdp_total = gdp_total;
    }

    public Double getGdp_first() {
        return gdp_first;
    }

    public void setGdp_first(Double gdp_first) {
        this.gdp_first = gdp_first;
    }

    public Double getGdp_second() {
        return gdp_second;
    }

    public void setGdp_second(Double gdp_second) {
        this.gdp_second = gdp_second;
    }

    public Double getGdp_third() {
        return gdp_third;
    }

    public void setGdp_third(Double gdp_third) {
        this.gdp_third = gdp_third;
    }

    public Double getGdp_total_yoy() {
        return gdp_total_yoy;
    }

    public void setGdp_total_yoy(Double gdp_total_yoy) {
        this.gdp_total_yoy = gdp_total_yoy;
    }

    public Double getGdp_first_yoy() {
        return gdp_first_yoy;
    }

    public void setGdp_first_yoy(Double gdp_first_yoy) {
        this.gdp_first_yoy = gdp_first_yoy;
    }

    public Double getGdp_second_yoy() {
        return gdp_second_yoy;
    }

    public void setGdp_second_yoy(Double gdp_second_yoy) {
        this.gdp_second_yoy = gdp_second_yoy;
    }

    public Double getGdp_third_yoy() {
        return gdp_third_yoy;
    }

    public void setGdp_third_yoy(Double gdp_third_yoy) {
        this.gdp_third_yoy = gdp_third_yoy;
    }
}
