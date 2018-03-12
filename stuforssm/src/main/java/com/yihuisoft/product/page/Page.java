package com.yihuisoft.product.page;

import java.util.Map;

/**
 * EasyUi分页用
 * @author X&Q
 *
 */
public class Page {
	
  private Integer page;
  private Integer rows;
  private Integer rowStart;
  private Integer rowEnd;
  private Map param;
  
public Integer getPage() {
	return page;
}
public void setPage(Integer page) {
	this.page = page;
}
public Integer getRows() {
	return rows;
}
public void setRows(Integer rows) {
	this.rows = rows;
}
public Integer getRowStart() {
	return rowStart;
}
public void setRowStart(Integer rowStart) {
	this.rowStart = rowStart;
}
public Integer getRowEnd() {
	return rowEnd;
}
public void setRowEnd(Integer rowEnd) {
	this.rowEnd = rowEnd;
}


public Map getParam() {
	return param;
}
public void setParam(Map param) {
	this.param = param;
}
public Page(Integer page, Integer rows) {
	super();
	this.page = page;
	this.rows = rows;
	this.rowStart=(page-1)*rows+1;
	this.rowEnd=page*rows;
}
  

  
	
}
