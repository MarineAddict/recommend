package com.yihuisoft.product.validate.serviceimp.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihuisoft.product.validate.entity.Indicators;
import com.yihuisoft.product.validate.service.YamlReader;

public class FinanceValidate {
   
	
	private static YamlReader yaml=new YamlReader();
	
	public static List<List> decideIndicator(Map values){
	List<List> resultlist=new ArrayList<List>();
	 List<Integer> indicatorList=new ArrayList<Integer>(); //记录对应待测指标的index值，便于后续拼接url
	 List<Map> paramList=new ArrayList<Map>(); //记录对应需要的参数
	 List<String> titleList=new ArrayList<String>(); //记录返回Excel的TITLE的名字
	 List<String> indexList=new ArrayList<String>(); //记录待测指标
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
			indexList.add("yieldRate");
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
			indexList.add("netValue");
		}
	}
	//包含预期收益率且包含产品代码
	if(values.containsKey("expYieldRate")){
			Map paraMap=new HashMap();
			paraMap.put("productCode", values.get("prodCode"));
			indicatorList.add(Indicators.expYieldRate.getIndex());
			paramList.add(paraMap);
			titleList.add(yaml.getYMLTitleInExcel("calculatedExpYieldRate"));
			indexList.add("expYieldRate");
	}
	//包含预期风险率且包含产品代码
			if(values.containsKey("expRiskRate")){
					Map paraMap=new HashMap();
					paraMap.put("productCode", values.get("prodCode"));
					indicatorList.add(Indicators.expRiskRate.getIndex());
					paramList.add(paraMap);
					titleList.add(yaml.getYMLTitleInExcel("calculatedExpRiskRate"));
					indexList.add("expRiskRate");
			}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	resultlist.add(indicatorList);
	resultlist.add(paramList);
	resultlist.add(titleList);
	resultlist.add(indexList);
	return resultlist;
	}
	
}
