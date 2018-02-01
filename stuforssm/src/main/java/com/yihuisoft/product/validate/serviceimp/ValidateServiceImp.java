package com.yihuisoft.product.validate.serviceimp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.validate.entity.ExcelInfo;
import com.yihuisoft.product.validate.entity.Indicators;
import com.yihuisoft.product.validate.entity.ProductType;
import com.yihuisoft.product.validate.service.ExcelReader;
import com.yihuisoft.product.validate.service.RestHub;
import com.yihuisoft.product.validate.service.ValidateService;
import com.yihuisoft.product.validate.service.YamlReader;
import com.yihuisoft.product.validate.util.ValidateUtil;

@Service("validateServiceImp")
public class ValidateServiceImp implements ValidateService{
	
	@Autowired
	@Qualifier("restHubeImp")
	RestHub restHub;
	
	@Autowired
	@Qualifier("excelReaderImp")
	ExcelReader excel;

	@Autowired
	@Qualifier("yamlReaderImp")
	YamlReader yaml;
	
	@Autowired
	ValidateUtil util;
	
	private static Logger logger=LoggerFactory.getLogger(ValidateServiceImp.class);
	/**
	 * 通过文件路径开始验证
	 */
	public ExcelInfo getFileIntoExcelInfo(String filePath) {
		ExcelInfo excelInfo=null;
       try {
    	   excelInfo=excel.getExcelDataList(filePath);
	} catch (FileNotFoundException e) {
		logger.error("找不到该路径："+filePath+" 的文件");
		e.printStackTrace();
	}
       catch (Exception e) {
		logger.error("读取excel文件时发生错误");
		e.printStackTrace();
		
	}
       return excelInfo;
 	}
	
	/**
	 * 主方法
	 * @param filePath
	 * @throws IOException 
	 */
	public void startValidateProcess(String filePath){
		/*首先获取文件的信息*/
		ExcelInfo info=getFileIntoExcelInfo(filePath);
		Workbook wb=null;
		FileOutputStream fos=null;
		try{
		 wb=excel.getWorkBook(filePath);
		}catch(Exception e){
			logger.error("查找文件时出错");
			e.printStackTrace();
			return;
		}
		//创建输出流
		try {
			fos = new FileOutputStream(filePath);
		} catch (FileNotFoundException e2) {
			logger.error("无法写入Excel文件，请检查是否打开了这个文件");
			e2.printStackTrace();
			return;
		}
		
		List<List> sheetList=(List<List>) info.getValues(); //获取所有worksheet的数据
		
		for(int sheetNum=0;sheetNum<sheetList.size();sheetNum++){
			List<Map> list=sheetList.get(sheetNum);
	    /**/
		 for(Map map:list){
			 Integer productType=decideProductType(map); //获得产品类型
			 List indicatorParamList=decideIndicator(map);//获得需要测试的指标代码
			 List<Integer> indicatorList= (List<Integer>)indicatorParamList.get(0); //得到指标list
			 List<Map> paramList= (List<Map>)indicatorParamList.get(1);; //得到参数list
			 List<String> titleList=(List<String>)indicatorParamList.get(2);
			 Integer startCol=info.getTitles().get(0).size(); //起始列数
			    for(int i=0;i<indicatorList.size();i++){
			    	//首先打标题
			    	excel.writeExcel(wb,sheetNum,0,startCol+i,titleList.get(i));
			    	
			    	//发送请求
			    	String url=util.getValidateRestTemplateUrl(productType, indicatorList.get(i),paramList.get(i));//获得url
			    	String str=null;
			    	try{
			    		str=restHub.startARestRequest(url);//进行请求(数据目前全部string)；
			    	}catch(Exception e){
			    		logger.error("请求失败，路径为: "+url);
			    		str="请求数据失败";
			    	}
			    	excel.writeExcel(wb,sheetNum, Integer.parseInt(map.get("row").toString()),(info.getTitles().get(0).size()+i),str); //假设都是第一个worksheet
			    } 
			   
		 }
		}
		 try{
			    wb.write(fos);
			    }catch (Exception e) {
			    	logger.error("写入文件失败");
			    	try {
						fos.close();
					} catch (IOException e1) {
					   e1.printStackTrace();
					}
				}
	}
	

	/**
	 * 根据数据确定产品类型
	 * @param list
	 */
	private Integer decideProductType(Map values){		
		if (values.containsKey("prodType")){
			String prodtype=values.get("prodType").toString();
			return Integer.parseInt(prodtype.substring(0, prodtype.indexOf(".")));
		}else{
			return null;
		}
	}

	/**根据Excel中出现的字段判断要判断哪些金融数据
	 * 
	 * @param values
	 * @return
	 */
	//逻辑判断需要测哪些数据 _______________________________________________返回的List是需要测试的单元，且带参数Map
	//所有的Excel判断逻辑加在这里
	private List<List> decideIndicator(Map values){
		 List<List> resultlist=new ArrayList<List>();
		 List<Integer> indicatorList=new ArrayList<Integer>();
		 List<Map> paramList=new ArrayList<Map>();
		 List<String> titleList=new ArrayList<String>();
		//包含收益率且包含日期,添加测试收益率
		if(values.containsKey("yieldRate")){
			if(values.containsKey("startDate")&&values.containsKey("endDate")){
				Map paraMap=new HashMap();
				paraMap.put("startTime",values.get("startDate"));
				paraMap.put("endTime",values.get("endDate"));
				paraMap.put("productCode", values.get("prodCode"));
				indicatorList.add(Indicators.yieldRate.getIndex());
				paramList.add(paraMap);
				titleList.add(yaml.getYMLTitleInExcel("calculatedYieldRate"));
			}
		}
		//包含净值且包含日期,添加测试净值
		if(values.containsKey("netValue")){
			if(values.containsKey("startDate")&&values.containsKey("endDate")){
				//包含净值且包含日期,添加测试净值
				Map paraMap=new HashMap();
				paraMap.put("startTime",values.get("startDate"));
				paraMap.put("endTime",values.get("endDate"));
				paraMap.put("productCode", values.get("prodCode"));
				indicatorList.add(Indicators.netValue.getIndex());
				paramList.add(paraMap);
				titleList.add(yaml.getYMLTitleInExcel("calculatedNetValue"));
			}
		}
		//包含预期收益率且包含产品代码
		if(values.containsKey("expYieldRate")){
				Map paraMap=new HashMap();
				paraMap.put("productCode", values.get("prodCode"));
				indicatorList.add(Indicators.expYieldRate.getIndex());
				paramList.add(paraMap);
				titleList.add(yaml.getYMLTitleInExcel("calculatedExpYieldRate"));
		}
		//包含预期风险率且包含产品代码
				if(values.containsKey("expRiskRate")){
						Map paraMap=new HashMap();
						paraMap.put("productCode", values.get("prodCode"));
						indicatorList.add(Indicators.expRiskRate.getIndex());
						paramList.add(paraMap);
						titleList.add(yaml.getYMLTitleInExcel("calculatedExpRiskRate"));
				}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		resultlist.add(indicatorList);
		resultlist.add(paramList);
		resultlist.add(titleList);
		return resultlist;
	}

	
	
	
	
	
	
}



