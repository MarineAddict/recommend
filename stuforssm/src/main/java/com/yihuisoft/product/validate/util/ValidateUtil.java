package com.yihuisoft.product.validate.util;
/**
 * 验证需要用的工具类
 * @author X&Q
 *
 */

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yihuisoft.product.validate.entity.Indicators;
import com.yihuisoft.product.validate.entity.ProductType;
import com.yihuisoft.product.validate.service.YamlReader;

@Component
public class ValidateUtil {
	@Autowired
	static YamlReader reader=new YamlReader();
	private static Logger logger=LoggerFactory.getLogger(ValidateUtil.class);
	
	private String root=reader.getUrl();

	/**
	 * 生成对应的url
	 * @param productTypeIndex 产品类型的号码（ProductType Enum）
	 * @param IndicatorIndex 指标类型的号码(Indicators Enum)
	 * @param variables (Map 类型的参数)
	 * @return
	 * @throws FileNotFoundException
	 */
   @SuppressWarnings("unchecked")
public String getValidateRestTemplateUrl(Integer productTypeIndex,Integer IndicatorIndex,Map variables){	
	   String strPrdType=null;
	   String strIndicator=null;
	   String header=root;
	   StringBuilder sb=new StringBuilder(header);
	   try{
	   strPrdType=ProductType.getPrdTypeByNumber(productTypeIndex).getTitle();
	   }catch(Exception e){
		   logger.error("找不到类型号为"+ productTypeIndex+" 的产品类型");
		   e.printStackTrace();
		   return null;
	   }
	   
	   Map map=reader.getYMLUrlMap(strPrdType); //根据产品类型读取对应的url Map
	   if(map==null){
		   return null;
	   }
	   try{
		   strIndicator=Indicators.getIndicatorByNumber(IndicatorIndex).getTitle();
		   strIndicator=map.get(strIndicator).toString();
	   }catch(Exception e){
		   e.printStackTrace();
		   return null;
	   }
	   sb.append("/"+strPrdType);
	   sb.append("/"+strIndicator); 
	 if(variables!=null){
		 sb.append("?");
		Iterator it= variables.entrySet().iterator();
	 while(it.hasNext()){
		 Map.Entry<String, Object> entry=(Map.Entry<String, Object>)it.next();
		 sb.append(entry.getKey());
		 sb.append("=");
		
		sb.append(String.valueOf(entry.getValue()));
		 sb.append("&");
	 }
		 sb.delete(sb.length()-1, sb.length());
	 }
	   return sb.toString();
   }
	
   
   
	public static void main(String[] args){
		Map map=new HashMap();
		map.put("ddad", 1);
		map.put("ddd", 2);
		String  str=new ValidateUtil().getValidateRestTemplateUrl(1,1,map);
		System.out.println(str);
	}
   
}
