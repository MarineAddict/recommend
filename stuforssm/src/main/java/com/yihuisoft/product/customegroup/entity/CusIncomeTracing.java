package com.yihuisoft.product.customegroup.entity;

/**
 * 
 * @ClassName:  CustomeIncomeTracing   
 * @Description:客户组合表
 * @author: tangjian
 * @date:   2018年1月11日 下午4:50:29
 */
public class CusIncomeTracing {

	private String cusId; //客户id
	private String groupId;//组合id
	private String planId;//规划id
	private String custName;//客戶姓名
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	@Override
	public String toString() {
		return "IncomeTracing [cusId=" + cusId + ", groupId=" + groupId
				+ ", planId=" + planId + ", custName=" + custName + "]";
	}
	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
}
