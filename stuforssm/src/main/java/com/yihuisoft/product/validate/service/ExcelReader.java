package com.yihuisoft.product.validate.service;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

import com.yihuisoft.product.validate.entity.ExcelInfo;


public interface ExcelReader {

	 ExcelInfo getExcelDataList(String url) throws Exception;
	
	 void writeExcel(Workbook wb,int worksheetNum,int rowNum, int colNum,String text);

	 Workbook getWorkBook(String url) throws IOException;
	 
}
		