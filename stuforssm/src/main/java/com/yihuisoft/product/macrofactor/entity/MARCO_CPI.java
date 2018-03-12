package com.yihuisoft.product.macrofactor.entity;

public class MARCO_CPI {

    private Integer id;
    private String cpi_date;  //cpi月份
    private Double cpi_country_month;  //全国当月指数
    private Double cpi_country_total; //全国累计指数
    private Double cpi_city_month; //城市当月指数
    private Double cpi_city_total; //城市累计指数
    private Double cpi_pural_month; //农村当月指数
    private Double cpi_pural_total; //农村累计指数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpi_date() {
        return cpi_date;
    }

    public void setCpi_date(String cpi_date) {
        this.cpi_date = cpi_date;
    }

    public Double getCpi_country_month() {
        return cpi_country_month;
    }

    public void setCpi_country_month(Double cpi_country_month) {
        this.cpi_country_month = cpi_country_month;
    }

    public Double getCpi_country_total() {
        return cpi_country_total;
    }

    public void setCpi_country_total(Double cpi_country_total) {
        this.cpi_country_total = cpi_country_total;
    }

    public Double getCpi_city_month() {
        return cpi_city_month;
    }

    public void setCpi_city_month(Double cpi_city_month) {
        this.cpi_city_month = cpi_city_month;
    }

    public Double getCpi_city_total() {
        return cpi_city_total;
    }

    public void setCpi_city_total(Double cpi_city_total) {
        this.cpi_city_total = cpi_city_total;
    }

    public Double getCpi_pural_month() {
        return cpi_pural_month;
    }

    public void setCpi_pural_month(Double cpi_pural_month) {
        this.cpi_pural_month = cpi_pural_month;
    }

    public Double getCpi_pural_total() {
        return cpi_pural_total;
    }

    public void setCpi_pural_total(Double cpi_pural_total) {
        this.cpi_pural_total = cpi_pural_total;
    }


}
