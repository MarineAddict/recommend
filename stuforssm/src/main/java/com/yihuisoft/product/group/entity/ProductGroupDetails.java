package com.yihuisoft.product.group.entity;

/**
 * Created by wangyinyuo on 2018/1/17.
 */
public class ProductGroupDetails {
    private int id;//实体id
    private String product_group_id;//产品组合id
    private String product_code;//产品code
    private String product_type;//产品类型
    private String proportion;//产品所占权重
    private String last_mod_time;//最后修改时间

    public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_group_id() {
        return product_group_id;
    }

    public void setProduct_group_id(String product_group_id) {
        this.product_group_id = product_group_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getLast_mod_time() {
        return last_mod_time;
    }

    public void setLast_mod_time(String last_mod_time) {
        this.last_mod_time = last_mod_time;
    }
}
