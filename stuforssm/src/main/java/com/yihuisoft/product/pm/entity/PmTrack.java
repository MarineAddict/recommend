package com.yihuisoft.product.pm.entity;

public class PmTrack {

    private String code;
    private Double navadj;
    private String navlatestdate;
    private Double data;

    public String getNavlatestdate() {
        return navlatestdate;
    }

    public void setNavlatestdate(String navlatestdate) {
        this.navlatestdate = navlatestdate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getNavadj() {
        return navadj;
    }

    public void setNavadj(Double navadj) {
        this.navadj = navadj;
    }



    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }
}
