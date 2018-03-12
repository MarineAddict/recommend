package com.yihuisoft.product.validate.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;


public class YamlReader {

	public static final String URL = /*YamlReader.class.getClassLoader().getResource("validate-config.yml").getPath();*/
	"D:\\项目文档\\python\\code\\stuforssm\\src\\main\\resources\\validate-config.yml";
	/*YamlReader.class.getClassLoader().getResource("validate-config.yml").getPath();*/
	public static final String URLMAP="urlMap"; //url路径Map
	public static final String EXCELPARAMMAP="excelMap"; //Excel对应的Map
	public static final  String EXCELTITLEMAP="excelTitle"; //写入对应Excel的标题
	public static final  String HTTPURL="httpUrl";
	public static final String ERRORRATE="errorRate";
	
	private static Logger logger=LoggerFactory.getLogger(YamlReader.class);
	/**
	 * 获取http的请求路径
	 * @return
	 */
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
	
	/**
	 * 根据产品类型获得所有旗下的指标对应的url
	 * @param productType
	 * @return
	 */
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
	
	
	public String getBaseUrlByProdType(String productType){
		Map map=getYMLUrlMap(productType);
		return map.get("base_url").toString();
	}
	
	/**
	 * 获取Excel数据时转换为统一的英文key
	 * @param paramName
	 * @return
	 */
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
	
	/**
	 * 将对应的统一的英文key转换为写入Excel的Title值
	 * @param paramName
	 * @return
	 */
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
	/**
	 * 获取误差率
	 * @return
	 */
	public double getErrorRate(){
		Yaml yl=new Yaml();
		double str=0;
		try{
		Map<String,Object> map=(Map<String,Object>)yl.load(new FileInputStream(new File(URL)));	
		  str=Double.parseDouble(map.get(ERRORRATE).toString());
		}catch (Exception e) {
			logger.error("找不到对应的误差字段");
		}
		return str;
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
		YamlReader s=new YamlReader();
		double m=s.getErrorRate();
		System.out.println(m);
	}
	
}
