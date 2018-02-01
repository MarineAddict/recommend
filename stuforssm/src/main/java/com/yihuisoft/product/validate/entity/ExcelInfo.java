package com.yihuisoft.product.validate.entity;

import java.io.File;
import java.util.List;
import java.util.Map;


public class ExcelInfo {

	
	//文件的名字
	private String ExcelName;
	//文件的版本
	private String ExcelEdition;
	//所有的title
	private List<List<String>> titles;
	//对应的数据键值对
	private List<?> Values;
	public String getExcelName() {
		return ExcelName;
	}
	public void setExcelName(String excelName) {
		ExcelName = excelName;
	}
	public String getExcelEdition() {
		return ExcelEdition;
	}
	public void setExcelEdition(String excelEdition) {
		ExcelEdition = excelEdition;
	}
	public List<List<String>> getTitles() {
		return titles;
	}
	public void setTitles(List<List<String>> titles) {
		this.titles = titles;
	}
	public List<?> getValues() {
		return Values;
	}
	public void setValues(List<?> values) {
		Values = values;
	}
	@Override
	public String toString() {
		return "ExcelInfo [ExcelName=" + ExcelName + ", ExcelEdition=" + ExcelEdition + ", titles=" + titles
				+ ", Values=" + Values + "]";
	}
	
	
	
}
