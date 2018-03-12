package com.yihuisoft.product.macrofactor.entity;

public class MARCO_PPI {
    private Integer id;
    private String ppi_date;//PPI月份
    private Double ppi_month;//当月同比PPI指数
    private Double ppi_total;//累计同比PPI指数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPpi_date() {
        return ppi_date;
    }

    public void setPpi_date(String ppi_date) {
        this.ppi_date = ppi_date;
    }

    public Double getPpi_month() {
        return ppi_month;
    }

    public void setPpi_month(Double ppi_month) {
        this.ppi_month = ppi_month;
    }

    public Double getPpi_total() {
        return ppi_total;
    }

    public void setPpi_total(Double ppi_total) {
        this.ppi_total = ppi_total;
    }
}
