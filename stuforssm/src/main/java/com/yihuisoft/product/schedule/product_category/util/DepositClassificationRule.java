package com.yihuisoft.product.schedule.product_category.util;
/**
 * 存单全部归为固收类
 * @author X&Q
 *
 */

import com.yihuisoft.product.schedule.product_category.entity.DepositBasicData;
import com.yihuisoft.product.schedule.product_category.entity.ProductCategoryData;
import com.yihuisoft.product.schedule.product_category.entity.fundtype.CategorySmall;
import com.yihuisoft.product.schedule.product_category.entity.fundtype.ClassificationSystem;
import com.yihuisoft.product.util.ProductType;

public class DepositClassificationRule {

	/**
	 * 直接存入纯债小类
	 * @param depositBasicData
	 * @return
	 */
	public static ProductCategoryData classifyDeposit(DepositBasicData depositBasicData){
		String prdCode=depositBasicData.getCode();
		String systemCode=ClassificationSystem.工行体系.getIndex();
		return new ProductCategoryData(systemCode, CategorySmall.货币.getIndex(), prdCode,ProductType.存款产品.getIndex());
		
	}
	
	
	
}
