package com.yihuisoft.product.group.entity;


/**
 * Created by wangyinyuo on 2018/1/17.
 */
public class ProductGroupBasic {
    private int id;//组合id
    private String status;//启用状态
    private int create_source;//创建来源(1是后台创建，2是客户创建)
    private double sharpe_ratio;//夏普比率
    private String product_group_name;//组合名称
    private String start_time;//启用时间
    private String end_time;//禁用时间
    private double EXPECTED_ANNUALIZED_RETURN;//预期年化收益
    private double EXPECTED_RISK_RATIO;//预期风险率
    private String create_time;//组合创建时间

    public double getSharpe_ratio() {
		return sharpe_ratio;
	}

	public void setSharpe_ratio(double sharpe_ratio) {
		this.sharpe_ratio = sharpe_ratio;
	}

	public double getEXPECTED_ANNUALIZED_RETURN() {
		return EXPECTED_ANNUALIZED_RETURN;
	}

	public void setEXPECTED_ANNUALIZED_RETURN(double eXPECTED_ANNUALIZED_RETURN) {
		EXPECTED_ANNUALIZED_RETURN = eXPECTED_ANNUALIZED_RETURN;
	}

	public double getEXPECTED_RISK_RATIO() {
		return EXPECTED_RISK_RATIO;
	}

	public void setEXPECTED_RISK_RATIO(double eXPECTED_RISK_RATIO) {
		EXPECTED_RISK_RATIO = eXPECTED_RISK_RATIO;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreate_source() {
        return create_source;
    }

    public void setCreate_source(int create_source) {
        this.create_source = create_source;
    }

    public String getProduct_group_name() {
        return product_group_name;
    }

    public void setProduct_group_name(String product_group_name) {
        this.product_group_name = product_group_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
