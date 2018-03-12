package com.yihuisoft.product.util;
/**
 * 记录所有的产品类型(和数据库对应)
 * @author X&Q
 *
 */
public enum ProductType {
 基金产品(1,"基金产品"),
 理财产品(2,"理财产品"),
 贵金属产品(3,"理财产品"),
 存款产品(4,"理财产品");
	
	private Integer index;
	private String typeName;
	private ProductType(Integer index,String typeName){
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
