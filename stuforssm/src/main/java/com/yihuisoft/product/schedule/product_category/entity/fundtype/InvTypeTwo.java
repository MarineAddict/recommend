package com.yihuisoft.product.schedule.product_category.entity.fundtype;

public enum InvTypeTwo {

	商品型基金("商品型基金");
	
	private String typeName;
	private InvTypeTwo( String typeName){
		this.typeName=typeName;
	}
	public String getTypeName() {
		return typeName;
	}
	
	
	
}
