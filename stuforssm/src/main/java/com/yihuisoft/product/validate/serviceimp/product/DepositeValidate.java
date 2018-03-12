package com.yihuisoft.product.validate.serviceimp.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihuisoft.product.validate.entity.Indicators;
import com.yihuisoft.product.validate.service.YamlReader;

/**
 *  所有的存款验证的逻辑
 * @author X&Q
 *
 */
public class DepositeValidate {
 
	private static YamlReader yaml=new YamlReader();

	public static List<List> decideIndicator(Map values){
		List<List> resultlist=new ArrayList<List>();
		 List<Integer> indicatorList=new ArrayList<Integer>();
		 List<Map> paramList=new ArrayList<Map>();
		 List<String> titleList=new ArrayList<String>();
		 List<String> indexList=new ArrayList<String>(); //记录待测指标
		//包含利息,验证利息
		if(values.containsKey("interest")){
				Map paraMap=new HashMap();
				paraMap.put("prodCode", values.get("prodCode"));
				indicatorList.add(Indicators.depositeInterest.getIndex());
				paramList.add(paraMap);
				titleList.add(yaml.getYMLTitleInExcel("calculatedInterest"));
				indexList.add("interest");
		}
		//包含净值且包含日期,添加测试净值
		if(values.containsKey("riskRate")){
				//包含净值且包含日期,添加测试净值
				Map paraMap=new HashMap();
				paraMap.put("prodCode", values.get("prodCode"));
				indicatorList.add(Indicators.depositeRisk.getIndex());
				paramList.add(paraMap);
				titleList.add(yaml.getYMLTitleInExcel("calculatedRiskRate"));
				indexList.add("riskRate");
		}
	  
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		resultlist.add(indicatorList);
		resultlist.add(paramList);
		resultlist.add(titleList);
		resultlist.add(indexList);
		return resultlist;
		}
	
	
}
