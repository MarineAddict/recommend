package com.yihuisoft.product.schedule.product_category.util;

import com.yihuisoft.product.schedule.product_category.entity.FundBasicData;
import com.yihuisoft.product.schedule.product_category.entity.ProductCategoryData;
import com.yihuisoft.product.schedule.product_category.entity.fundtype.CXFundType;
import com.yihuisoft.product.schedule.product_category.entity.fundtype.CategorySmall;
import com.yihuisoft.product.schedule.product_category.entity.fundtype.ClassificationSystem;
import com.yihuisoft.product.schedule.product_category.entity.fundtype.InvTypeOne;
import com.yihuisoft.product.schedule.product_category.entity.fundtype.InvTypeTwo;
import com.yihuisoft.product.util.ProductType;

/**
 * 基金产品的分类标准
 * @author X&Q
 *
 */
public class FundClassificationRule {

	public static final String GOLDBIDCODE="EMI00224692"; //标的为黄金的代号

	
	/**
	 * 判断是否为纯债,规则为：invtypOne类型为债券型,且晨星分类为  纯债基金
	 * @param fundBasic
	 * @return
	 */
	public static boolean isPureDept(FundBasicData fundBasic){
		if(fundBasic.getInvTypOne()==InvTypeOne.债券型基金.getIndex()){
		  if(fundBasic.getCXFundTyp()==CXFundType.纯债型基金.getIndex()){
			  return true;
		  }
		}
		return false;
	}
	
	/**
	 * 判断是否为混合债,规则为：invtypOne类型为债券型,且晨星分类为  普通债券型基金, 激进债券型基金
	 * @param fundBasic
	 * @return
	 */
	public static boolean isHybridDept(FundBasicData fundBasic){
		if(fundBasic.getInvTypOne()==InvTypeOne.债券型基金.getIndex()){
		  if(fundBasic.getCXFundTyp()==CXFundType.普通债券型基金.getIndex()||fundBasic.getCXFundTyp()==CXFundType.激进债券型基金.getIndex()){
			  return true;
		  }
		}
		return false;
	}
	
	/**
	 * 判断是否为黄金,规则为：invtypTwo类型为商品基金,且标的为 EMI00224692
	 * @param fundBasic
	 * @return
	 */
	public static boolean isGold(FundBasicData fundBasic){
		if(fundBasic.getInvTypTwo()==InvTypeTwo.商品型基金.getTypeName()){
		if(fundBasic.getBidCode()==GOLDBIDCODE){
			return true;
		  }
		}
		return false;
	}
	
	
	/**
	 * 将基金产品根据所有已有的规则进行分类
	 * @param fundBasic
	 * @return
	 */
	public static ProductCategoryData classifyFund(FundBasicData fundBasic){
		String prdCode=fundBasic.getCode();
		String systemCode=ClassificationSystem.工行体系.getIndex();
		if (isHybridDept(fundBasic))  return new ProductCategoryData(systemCode,CategorySmall.普通债.getIndex(),prdCode,ProductType.基金产品.getIndex());
		if (isPureDept(fundBasic))  return new ProductCategoryData(systemCode, CategorySmall.纯债.getIndex(), prdCode,ProductType.基金产品.getIndex());
		if(isGold(fundBasic)) return new ProductCategoryData(systemCode, CategorySmall.黄金.getIndex(), prdCode,ProductType.基金产品.getIndex());
		return new ProductCategoryData(systemCode, CategorySmall.默认.getIndex(), prdCode,ProductType.基金产品.getIndex());
	}
	
	
	
	
	
	
}
