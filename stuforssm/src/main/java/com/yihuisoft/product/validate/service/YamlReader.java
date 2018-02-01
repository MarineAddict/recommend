package com.yihuisoft.product.validate.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import com.yihuisoft.product.validate.util.ValidateUtil;

@Component("yamlReaderImp")
public class YamlReader {

	/*public static final String URL="D:\\项目文档\\python\\code\\stuforssm\\src\\main\\resources\\validate-config.yml"; *///yml 文件地址，部署需要更改
	private static String URL = YamlReader.class.getClassLoader().getResource("validate-config.yml").getPath();
	private static final String URLMAP="urlMap"; //url路径Map
	private static final String EXCELPARAMMAP="excelMap"; //Excel对应的Map
	private static final String EXCELTITLEMAP="excelTitle"; //写入对应Excel的标题
	private static final String HTTPURL="httpUrl";
	
	private static Logger logger=LoggerFactory.getLogger(YamlReader.class);
	
	public String getUrl(){
		Yaml yl=new Yaml();
		try{
		Map<String,Object> map=(Map<String,Object>)yl.load(new FileInputStream(new File(URL)));	
		Map reurlMap=(Map)map.get(HTTPURL);
		return reurlMap.get("test").toString();
		}catch(FileNotFoundException e){
			logger.error("读取文件失败 ：" +URL);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Map<String,Map> getYMLUrlMap(String productType){
		Yaml yl=new Yaml();
		try{
		Map<String,Object> map=(Map<String,Object>)yl.load(new FileInputStream(new File(URL)));	
		Map reurlMap=(Map)map.get(URLMAP);
		return (Map)(reurlMap.get(productType));
		}catch(FileNotFoundException e){
			logger.error("读取yml文件失败 ：" +URL);
			e.printStackTrace();
		}
		return null;
	}
	
	public String getYMLExcel2Standard(String paramName){
		Yaml yl=new Yaml();
		String str=null;
		try{
		Map<String,Object> map=(Map<String,Object>)yl.load(new FileInputStream(new File(URL)));	
		Map reurlMap=(Map)map.get(EXCELPARAMMAP);
		try{
		str= reurlMap.get(paramName).toString();
		  }catch(Exception e){
			logger.error("找不到：\"" +paramName +"\" 对应的字段");
			
		  }
		}catch(FileNotFoundException e){
			logger.error("读取文件失败 ：" +URL);
		
		}
		return str;
	}
	
	
	public String getYMLTitleInExcel(String paramName){
		Yaml yl=new Yaml();
		String str=null;
		try{
		Map<String,Object> map=(Map<String,Object>)yl.load(new FileInputStream(new File(URL)));	
		Map reurlMap=(Map)map.get(EXCELTITLEMAP);
		try{
		str= reurlMap.get(paramName).toString();
		  }catch(Exception e){
			logger.error("找不到：\"" +paramName +"\" 对应的字段");
			e.printStackTrace();
		  }
		}catch(FileNotFoundException e){
			logger.error("读取文件失败 ：" +URL);
			e.printStackTrace();
		}
		return str;
	}
	
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
		YamlReader s=new YamlReader();
		String m=s.getYMLTitleInExcel("calculatedNetValue");
		System.out.println(m);
	}
	
}
