package com.yihuisoft.product.schedule.product_category.entity.fundtype;
/**
 * 小类
 * @author X&Q
 *
 */
public enum CategorySmall {
	国内小盘股(1,"国内小盘股"),
	国内大盘股(2,"国内大盘股"),
	港股(3,"港股"),
	美股(4,"美股"),
	货币(5,"货币"),
	普通债(6,"普通债"),
	纯债(7,"纯债"),
	黄金(8,"黄金"),
	默认(1,"未知,设置默认");
	
	private Integer index;
	private String categoryName;
	private CategorySmall(Integer index,String categoryName){
		this.index=index;
		this.categoryName=categoryName;
	}
	public Integer getIndex() {
		return index;
	}
	public String getCategoryName() {
		return categoryName;
	}
	
}
