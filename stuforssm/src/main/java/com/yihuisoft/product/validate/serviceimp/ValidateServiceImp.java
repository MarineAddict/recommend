package com.yihuisoft.product.validate.serviceimp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.yihuisoft.product.validate.serviceimp.product.DepositeValidate;
import com.yihuisoft.product.validate.serviceimp.product.FinanceValidate;
import com.yihuisoft.product.validate.serviceimp.product.FundValidate;
import com.yihuisoft.product.validate.util.ValidateUtil;

@Service("validateServiceImp")
public class ValidateServiceImp implements ValidateService{
	
	@Autowired
	@Qualifier("restHubeImp")
	RestHub restHub;
	
	@Autowired
	@Qualifier("excelReaderImp")
	ExcelReader excel;

	
	YamlReader yaml=new YamlReader();
	
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
		
			
	try{	
		for(int sheetNum=0;sheetNum<sheetList.size();sheetNum++){
			List<Map> list=sheetList.get(sheetNum);
	    /**/
		 for(Map map:list){
			 ProductType productType=ProductType.getPrdTypeByNumber(decideProductType(map)); //获得产品类型
			 List indicatorParamList=decideIndicator(productType,map);//获得需要测试的指标代码
			 
			 List<Integer> indicatorList= (List<Integer>)indicatorParamList.get(0); //得到指标list
			 List<Map> paramList= (List<Map>)indicatorParamList.get(1);; //得到参数list
			 List<String> titleList=(List<String>)indicatorParamList.get(2);//得到返回到Excel上的title的List
			 List<String> indexList=(List<String>)indicatorParamList.get(3);//得到Excel中的指标的key的List
			 Integer startCol=info.getTitles().get(sheetNum).size(); //起始列数
			    for(int i=0;i<indicatorList.size();i++){
			    	//首先打标题
			    	excel.writeExcel(wb,sheetNum,0,startCol+i,titleList.get(i));
			    	//发送请求
			    	String url=util.getValidateRestTemplateUrl(productType.getIndex(), indicatorList.get(i),paramList.get(i));//获得url
			    	String str=null;
			    	try{
			    		Object restResponse=restHub.startARestRequest(url);//进行请求(数据目前全部string)；
			    		String valueCalculated=null;
			    		String valueFromExcel=null;
			    		if (!ValidateUtil.isList(restResponse)){
			    		   //返回单个数值的，直接进入验证的方法
			    			valueCalculated=restResponse.toString();//获取到controller返回的值
			    			//获取对应要比较的值
			    			 valueFromExcel=map.get(indexList.get(i)).toString();
			    		}
			    		else{
			    	    //返回list类型的，需要拆数组(请求会返回所有需要的数据),拆出对应字段的数据getReturnedTitle（根据indicatorList对应）
			    			String returedTitle=Indicators.getIndicatorByNumber(indicatorList.get(i)).getReturnedTitle();
			    			try{
			    			List<Map> resList=(List<Map>)restResponse;
			    			Map resMap=resList.get(0);
			    		    valueCalculated=resMap.get(returedTitle).toString();//获取到controller返回的值
			    			//获取对应要比较的值
			    		    valueFromExcel=map.get(indexList.get(i)).toString();
			    			} catch (NullPointerException e) {
								logger.error("获取controller值时失败，可能是indicator指定的返回字段设置错误");
							}
			    		}
			    		/* str= MethodCompare.identical(valueCalculated, valueFromExcel);*/
			    		str=valueCalculated;
			    		
			    	}catch(Exception e){
			    		logger.error("请求失败，路径为: "+url);
			    		str="请求数据失败,路径为"+url;
			    	}
			    	
			    	excel.writeExcel(wb,sheetNum, Integer.parseInt(map.get("row").toString()),(info.getTitles().get(sheetNum).size()+i),str); 
			    } 
			   
		 }
		}
	}catch(Exception e){
		logger.error("过程中出现错误");
		e.printStackTrace();
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
			return Integer.parseInt(prodtype.substring(0, prodtype.indexOf(".")==-1?prodtype.length():prodtype.indexOf(".")));
		}else{
			return null;
		}
	}

	/**根据Excel中出现的字段判断要判断哪些金融数据，需要根据产品类型进行判断
	 * 
	 * @param values
	 * @return
	 */
	//逻辑判断需要测哪些数据 _______________________________________________返回的List是需要测试的单元，且带参数Map
	//所有的Excel判断逻辑加在这里
	private List<List> decideIndicator(ProductType prodType,Map values){
		List resultList=new ArrayList();
		try{
		 switch (prodType) {
		case deposit:
			resultList=DepositeValidate.decideIndicator(values);
			break;
        case finance:
        	resultList=FinanceValidate.decideIndicator(values);
			break;
        case fund:
        	resultList=FundValidate.decideIndicator(values);
			break;
			
		default:
			break;
		}
		}catch(NullPointerException e){
			logger.error("检测待测试指标时发生错误，位置:"+prodType+", values= "+values );
		}
		return resultList;
	}

	
	
	
	
	
	
	
}



