package com.yihuisoft.product.deposit.entity;


import java.sql.Date;

/**
 *  Description ：存单基本信息
 *  @author     ：Machengxin
 *  @Date       ：2018/1/25-11:05
 *  @version    ：V_1.0.0
 */
public class DepositBasic {
    public int id;
    public String code;
    public String name;
    public Date substartday;
    public Date subendday;
    public Date intestartday;
    public Date intendday;
    public float interest;
    public int theterm;
    public int calculate_covariance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSubstartday() {
        return substartday;
    }

    public void setSubstartday(Date substartday) {
        this.substartday = substartday;
    }

    public Date getSubendday() {
        return subendday;
    }

    public void setSubendday(Date subendday) {
        this.subendday = subendday;
    }

    public Date getIntestartday() {
        return intestartday;
    }

    public void setIntestartday(Date intestartday) {
        this.intestartday = intestartday;
    }

    public void setIntendday(Date intendday) {
        this.intendday = intendday;
    }

    public Date getIntendday() {
        return intendday;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public int getTheterm() {
        return theterm;
    }

    public void setTheterm(int theterm) {
        this.theterm = theterm;
    }

    public int getCalculate_covariance() {
        return calculate_covariance;
    }

    public void setCalculate_covariance(int calculate_covariance) {
        this.calculate_covariance = calculate_covariance;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
