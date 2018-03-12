package com.yihuisoft.product.schedule.product_category.entity.fundtype;
/**
 * 一级投资类型
 * @author X&Q
 *
 */
public enum InvTypeOne {
 
	混合型基金(1,"混合型基金"),
	债券型基金(2,"债券型基金"),
	股票型基金(3,"股票型基金"),
	货币市场型基金(4,"货币市场型基金"),
	其他基金(5,"其他基金");
	
	private Integer index;
    private String typeName;
    private InvTypeOne(Integer index,String typeName){
    	this.index=index;
    	this.typeName=typeName;
    }
	public Integer getIndex() {
		return index;
	}
	public String getTypeName() {
		return typeName;
	}
	
    
	
}
