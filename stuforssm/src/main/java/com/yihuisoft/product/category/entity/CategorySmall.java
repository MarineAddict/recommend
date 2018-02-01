package com.yihuisoft.product.category.entity;

/**
 *  @Description：产品小类
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/1/29-10:33
 *  @version    ：V_1.0.0
 */
public class CategorySmall {

    private int id;
    private int code;
    private String name;
    private int fcode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFcode() {
        return fcode;
    }

    public void setFcode(int fcode) {
        this.fcode = fcode;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
