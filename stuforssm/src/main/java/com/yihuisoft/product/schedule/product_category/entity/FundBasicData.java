package com.yihuisoft.product.schedule.product_category.entity;

public class FundBasicData {

	/*实体标识*/
	private Integer Id;
	/*产品代码*/
	private String Code;
	/*产品名称*/
	private String Name;
	/*产品状态*/
	private String Status;
	/*投资类型1*/
	private Integer InvTypOne;
	/*投资类型2*/
	private String InvTypTwo;
	/*标的*/
	private String BidCode;
	/*晨星基金类型*/
	private Integer CXFundTyp;
	
	
	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}


	public String getCode() {
		return Code;
	}


	public void setCode(String code) {
		Code = code;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	public Integer getInvTypOne() {
		return InvTypOne;
	}


	public void setInvTypOne(Integer invTypOne) {
		InvTypOne = invTypOne;
	}


	public String getInvTypTwo() {
		return InvTypTwo;
	}


	public void setInvTypTwo(String invTypTwo) {
		InvTypTwo = invTypTwo;
	}


	public String getBidCode() {
		return BidCode;
	}


	public void setBidCode(String bidCode) {
		BidCode = bidCode;
	}


	public Integer getCXFundTyp() {
		return CXFundTyp;
	}


	public void setCXFundTyp(Integer cXFundTyp) {
		CXFundTyp = cXFundTyp;
	}


	@Override
	public String toString() {
		return "FundBasicData [Id=" + Id + ", Code=" + Code + ", Name=" + Name + ", Status=" + Status + ", InvTypOne="
				+ InvTypOne + ", InvTypTwo=" + InvTypTwo + ", BidCode=" + BidCode + ", CXFundTyp=" + CXFundTyp + "]";
	}
	
	
}
