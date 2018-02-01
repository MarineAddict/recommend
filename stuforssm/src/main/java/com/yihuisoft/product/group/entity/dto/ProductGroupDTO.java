package com.yihuisoft.product.group.entity.dto;

import java.util.List;

import com.yihuisoft.product.group.entity.ProductGroupDetails;

public class ProductGroupDTO {
    private int id;//组合id
    private String product_group_name;//组合名称
    private int create_source;//创建来源(1是后台创建，2是客户创建)
    private String status;//启用状态（0是禁用，1是启用）
    private double sharpeRatio;//夏普比率
    private String create_time;//组合创建时间
    private double EXPECTED_ANNUALIZED_RETURN;//预期年化收益
    private double EXPECTED_RISK_RATIO;//预期风险率
	private double Maxdrawdown;//最大回撤
	private List<ProductGroupDetails> pgdList;
    public double getMaxdrawdown() {
		return Maxdrawdown;
	}
	public void setMaxdrawdown(double maxdrawdown) {
		Maxdrawdown = maxdrawdown;
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
	public int getCreate_source() {
		return create_source;
	}
	public void setCreate_source(int create_source) {
		this.create_source = create_source;
	}
	public double getSharpeRatio() {
		return sharpeRatio;
	}
	public void setSharpeRatio(double sharpeRatio) {
		this.sharpeRatio = sharpeRatio;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct_group_name() {
		return product_group_name;
	}
	public void setProduct_group_name(String product_group_name) {
		this.product_group_name = product_group_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public List<ProductGroupDetails> getPgdList() {
		return pgdList;
	}
	public void setPgdList(List<ProductGroupDetails> pgdList) {
		this.pgdList = pgdList;
	}

}
