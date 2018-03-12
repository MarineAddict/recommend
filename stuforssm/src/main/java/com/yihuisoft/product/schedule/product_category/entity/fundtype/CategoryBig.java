package com.yihuisoft.product.schedule.product_category.entity.fundtype;

public enum CategoryBig {

	权益类(1,"权益类"),
	固定收益类(2,"固定收益类"),
	现金类(3,"现金类"),
	其他(4,"其他");
	
	private Integer index;
	private String CategoryBigName;
	private CategoryBig(Integer index, String categoryBigName) {
		this.index = index;
		CategoryBigName = categoryBigName;
	}
	public Integer getIndex() {
		return index;
	}
	public String getCategoryBigName() {
		return CategoryBigName;
	}

	
	
}
