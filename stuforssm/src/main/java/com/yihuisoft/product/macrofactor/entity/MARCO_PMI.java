package com.yihuisoft.product.macrofactor.entity;

public class MARCO_PMI {
    private Integer id;//
    private String pmi_date;//PMI月份
    private Double pmi_manu;//制造业PMI指数
    private Double pmi_non_manu;//非制造业PMI指数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPmi_date() {
        return pmi_date;
    }

    public void setPmi_date(String pmi_date) {
        this.pmi_date = pmi_date;
    }

    public Double getPmi_manu() {
        return pmi_manu;
    }

    public void setPmi_manu(Double pmi_manu) {
        this.pmi_manu = pmi_manu;
    }

    public Double getPmi_non_manu() {
        return pmi_non_manu;
    }

    public void setPmi_non_manu(Double pmi_non_manu) {
        this.pmi_non_manu = pmi_non_manu;
    }
}
