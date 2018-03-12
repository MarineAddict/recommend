package com.yihuisoft.product.validate.serviceimp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.validate.entity.ExcelInfo;
import com.yihuisoft.product.validate.service.ExcelReader;
import com.yihuisoft.product.validate.service.YamlReader;

@Service("excelReaderImp")
public class ExcelReaderImp implements ExcelReader {

	private static String Excel_xls="xls";
	private static String Excel_xlsx="xlsx"; //excel版本
	private static List<List<String>> TITLES=new ArrayList();//用于存入所有的标题头
	private static int TITLE_COLUMNS;
	private static Logger logger=LoggerFactory.getLogger(ExcelReaderImp.class);
	private static final String UNPARSABLETITLE="未知列名";
	
	
	private YamlReader yml=new YamlReader();
	
	//获取Excel文件的数据
		public ExcelInfo getExcelDataList(String url) throws Exception{
		  if("".equals(url)||url==null){
			  return null;
		  }
		  ExcelInfo info=new ExcelInfo();
			File file= new File(url);
			List list=null;
			if (file.getName().endsWith(Excel_xls)){
				list=parseXLS(file);
			}
			else if(file.getName().endsWith(Excel_xlsx)){
				list=parseXLSX(file);
			}else{
				logger.error("不是excel文件，无法解析");
				return null;
			}
			info.setValues(list);
		    info.setExcelEdition(Excel_xls);
		    info.setExcelName(file.getName());
		    info.setTitles(TITLES);
		    return info;
		}
		
		
			
		  
   //处理xls
		private  List<List<Map<String,Object>>> parseXLS(File file) throws FileNotFoundException ,IOException   {
			InputStream is=new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(is);
		
			List<List<Map<String,Object>>> resultList= new ArrayList<List<Map<String,Object>>>();
			
			for(int numWork=0;numWork<workbook.getNumberOfSheets();numWork++){
				List<String> titleList=new ArrayList<String>();//用于存本worksheet的抬头
						
				HSSFSheet sheet=workbook.getSheetAt(numWork);
				if(sheet==null){
					continue;
				}
				
				List<Map<String,Object>> thisWorkSheetDataList=new ArrayList<Map<String,Object>>();
				int firstRowIndex=sheet.getFirstRowNum();
				for(int rowNum=firstRowIndex;rowNum<=sheet.getLastRowNum();rowNum++){
					
					HSSFRow row= sheet.getRow(rowNum);
					int minColIndex=0;
					try{
				    minColIndex=row.getFirstCellNum();
					}catch(Exception e){ 
						logger.error("读取excel时出现行找不到cell的情况,出现在 "+(rowNum+1)+" 行");
						e.printStackTrace();
						continue;
					}
				    int maxColIndex=row.getLastCellNum();
				    HashMap<String, Object> resultMap=new HashMap<String,Object>();
				    
				    for(int colIndex=minColIndex;colIndex<maxColIndex;colIndex++){
				    	
				    	if (rowNum==firstRowIndex){//首行的值为title，作为map的key
				    		String title=yml.getYMLExcel2Standard(row.getCell(colIndex).toString());
				    		if (title==null) {
				    			logger.error("第"+numWork+"个sheet的("+rowNum+": "+colIndex+") 出错");
				    			 title=UNPARSABLETITLE+colIndex;
				    		}
				    		titleList.add(title);  
				    		TITLE_COLUMNS=maxColIndex; //第一行title行的数据
				    	}else{
				    		if(colIndex<TITLE_COLUMNS){//添加一层判断避免出现一行的数据大于标题（第一行）数据的情况
					    		resultMap.put(titleList.get(colIndex),returnCellValue(row.getCell(colIndex)));	
					    		resultMap.put("row",rowNum);
					    	}   
				    	 }				    			
				    	}
				   if(rowNum!=firstRowIndex){//第一行不存入map中，去除
				    thisWorkSheetDataList.add(resultMap);
				   }
				}
				TITLES.add(titleList); //抬头存入list
				resultList.add(thisWorkSheetDataList);//数据存入value
			}
			is.close();
			return resultList;
		}
	
	//处理XLSX
		private  List<List<Map<String,Object>>> parseXLSX(File file) throws Exception{
			InputStream is=new FileInputStream(file);
			XSSFWorkbook workbook=new XSSFWorkbook(is); //workbook
			List<List<Map<String,Object>>> resultList= new ArrayList<List<Map<String,Object>>>();
			
			for(int numWork=0;numWork<workbook.getNumberOfSheets();numWork++){
				List<String> titleList=new ArrayList<String>();//用于存本worksheet的抬头
				XSSFSheet sheet=workbook.getSheetAt(numWork);
				if(sheet==null){
					continue;
				}
				
				List<Map<String,Object>> thisWorkSheetDataList=new ArrayList<Map<String,Object>>();
				int firstRowIndex=sheet.getFirstRowNum();
				for(int rowNum=firstRowIndex;rowNum<=sheet.getLastRowNum();rowNum++){
					
					XSSFRow row= sheet.getRow(rowNum);
					int minColIndex=0;
					try{
				     minColIndex=row.getFirstCellNum();
					}catch(Exception e){ 
						logger.error("读取excel时出现行找不到cell的情况,出现在 "+(rowNum+1)+" 行");
						e.printStackTrace();
						continue;
					}
				    int maxColIndex=row.getLastCellNum();
				    HashMap<String, Object> resultMap=new HashMap<String,Object>();
				    for(int colIndex=minColIndex;colIndex<maxColIndex;colIndex++){
				    	
				    	if (rowNum==firstRowIndex){//首行的值为title，作为map的key
				    		String title=yml.getYMLExcel2Standard(row.getCell(colIndex).toString());
				    		if (title==null) {
				    			logger.error("第"+numWork+"个sheet的("+rowNum+": "+colIndex+") 出错");
				    			 title=UNPARSABLETITLE+colIndex;
				    		}
				    		titleList.add(title);  
				    		TITLE_COLUMNS=maxColIndex; //第一行title行的数据
				    	}else{
				    		if(colIndex<TITLE_COLUMNS){//添加一层判断避免出现一行的数据大于标题（第一行）数据的情况
				    		resultMap.put(titleList.get(colIndex),returnCellValue(row.getCell(colIndex)));
				    		resultMap.put("row",rowNum);
				    		}
				    	}				    			
				    	}
				   if(rowNum!=firstRowIndex){//第一行不存入map中，去除
				    thisWorkSheetDataList.add(resultMap);
				   }
				}
				TITLES.add(titleList); //抬头存入list
				resultList.add(thisWorkSheetDataList);
			}
			is.close();
			return resultList;
		}
		

		//针对cell的类型进行不同的数据处理
		private static Object returnCellValue(Cell cell){
			try{
				
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				//数字
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					//时间类型
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				}else {
					return cell.getNumericCellValue();
				}
			case Cell.CELL_TYPE_BOOLEAN:
				return cell.getBooleanCellValue();
			case Cell.CELL_TYPE_STRING:
				/*需要对此再次判断下是否是时间类的string*/
				String str=cell.getStringCellValue();
				if(str.contains("月")){
					String regex="\\s+";
					str=str.replaceAll(regex,"");
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM月-yy");
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
				str=sdf2.format(sdf.parse(str));
				}
			       return str;
			case Cell.CELL_TYPE_BLANK:
				return "";
			default:
				break;
						}
			return null;
			}catch(Exception e){
				logger.error("解析cell的数据类型时发现无法解析的类型");
				e.printStackTrace();
			}
			return null;
		}
		

		public static void main(String[] args){
			try{
			ExcelReaderImp imp=new ExcelReaderImp();
				ExcelInfo info=imp.getExcelDataList("C:\\Users\\X&Q\\Desktop\\理财模拟数据.xlsx");
				System.out.println(info);
				/*Workbook wb=imp.getWorkBook("C:\\Users\\X&Q\\Desktop\\licai .xls");
				FileOutputStream fos=new FileOutputStream(new File("C:\\Users\\X&Q\\Desktop\\licai .xls"));
		    	imp.writeExcel(wb, 1, 1, 1, "222");		
		    	wb.write(fos);*/
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		
		
		


		public void writeExcel(Workbook wb,int worksheetNum,int rowNum, int colNum,String value) {
			if (wb==null){
				logger.error("没有找到工作本");
				return;
			}
			try{
			Sheet worksheet=wb.getSheetAt(worksheetNum); //获取工作本
			Row row=worksheet.getRow(rowNum);
			/*if(row!=null){
			worksheet.removeRow(row); //如果有，移除这一行
			}
			row=worksheet.createRow(rowNum);*/
			Cell cell=row.createCell(colNum);
			cell.setCellValue(value); //设置单元格的值
			}catch(Exception e){
				e.printStackTrace();
				logger.error("设置 单元格("+(rowNum+1)+":"+(colNum+1)+")时出错");
			}
		}

	  
		
		
		
		


		public Workbook getWorkBook(String url) throws IOException {
			File file=new File(url);
			Workbook wb=null;
			if(file.getName().endsWith(Excel_xls)){
				wb= new HSSFWorkbook(new FileInputStream(file));
			}else if(file.getName().endsWith(Excel_xlsx)){
				wb= new XSSFWorkbook(new FileInputStream(file));
			}
			 return wb;
		}





	
		
}
