package com.yihuisoft.product.category.entity;

/**
 *  @Description：产品大类
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/1/29-10:30
 *  @version    ：V_1.0.0
 */
public class CategoryBig {
    private int id;
    private int code;
    private String name;
    
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

    @Override
    public String toString() {
        return super.toString();
    }
}
