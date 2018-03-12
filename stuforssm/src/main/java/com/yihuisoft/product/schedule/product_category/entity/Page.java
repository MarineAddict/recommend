package com.yihuisoft.product.schedule.product_category.entity;


public class Page {
	
	/*页数*/
	private Integer page;
	/*每页多少数据*/
	private Integer limit;
	/*起始页*/
	private long StartRow;
	/*结束页*/
	private long EndRow;
	
	public Page(Integer page, Integer limit) {
		super();
		this.page = page;
		this.limit = limit;
		this.StartRow=(page-1)*limit+1;
		this.EndRow=page*limit;
	}
	
	
	

	public Page() {
		super();
	}




	public long getStartRow() {
		return StartRow;
	}

	public void setStartRow(long startRow) {
		StartRow = startRow;
	}





	public long getEndRow() {
		return EndRow;
	}





	public void setEndRow(long endRow) {
		EndRow = endRow;
	}





	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
		this.StartRow=(page-1)*limit+1;
		this.EndRow=page*limit;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
