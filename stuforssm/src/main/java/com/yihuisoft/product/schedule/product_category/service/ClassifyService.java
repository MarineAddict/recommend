package com.yihuisoft.product.schedule.product_category.service;

import java.util.List;

import com.yihuisoft.product.schedule.product_category.entity.DepositBasicData;
import com.yihuisoft.product.schedule.product_category.entity.FundBasicData;
import com.yihuisoft.product.schedule.product_category.entity.Page;
import com.yihuisoft.product.schedule.product_category.entity.ProductCategoryData;

/**
 * 分类总接口
 * @author X&Q
 *
 */
public interface ClassifyService {
   
	
	List<FundBasicData> getFundBasicData(Page page);
	
	/*以下为取出数据分类后的数据*/
	List<ProductCategoryData> generateProductCategory(List<FundBasicData> fundBasicList);
	List<ProductCategoryData> generateDepositProductCategory(List<DepositBasicData> depositBasicList);
	
	/*以下为定时任务调用的口*/
	void startClassifyAllFund();
	void startClassifyAllDeposit();
	
}
