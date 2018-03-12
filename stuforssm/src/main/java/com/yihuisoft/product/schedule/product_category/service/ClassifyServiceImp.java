package com.yihuisoft.product.schedule.product_category.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.schedule.product_category.dao.BaseDao;
import com.yihuisoft.product.schedule.product_category.entity.DepositBasicData;
import com.yihuisoft.product.schedule.product_category.entity.FundBasicData;
import com.yihuisoft.product.schedule.product_category.entity.Page;
import com.yihuisoft.product.schedule.product_category.entity.ProductCategoryData;
import com.yihuisoft.product.schedule.product_category.service.dbutil.DepositClassificationDBUtil;
import com.yihuisoft.product.schedule.product_category.service.dbutil.FundClassificationDBUtil;
import com.yihuisoft.product.schedule.product_category.util.DepositClassificationRule;
import com.yihuisoft.product.schedule.product_category.util.FundClassificationRule;

@Service("classifyServiceImp")
public class ClassifyServiceImp implements ClassifyService {

	@Autowired
	private BaseDao dao;
	private static Logger logger=LoggerFactory.getLogger(ClassifyServiceImp.class);
	/*从数据库最大提取条数*/
	public static final int maxItems=500; 
	
    /**
     * 公用方法：根据page的页数和size将数据拿出
     */
	@Override
	public List<FundBasicData> getFundBasicData(Page page) {
		return dao.getFundProductDataByPage(page);
	}
	
	/**
	 * 获得基金产品的总数
	 * @return
	 */
	private Integer getFundProductTotalNumber(){
		return dao.getFundProductTotalNumber();
	}
	/**
	 * 根据基金的原始数据生成为基金产品分类数据
	 * @param fundBasicList
	 * @return
	 */
	public List<ProductCategoryData> generateProductCategory(List<FundBasicData> fundBasicList){
		if (fundBasicList==null){
			logger.error("异常，基金List数据为空");
			return null;
		}	
		List<ProductCategoryData> result=new ArrayList<ProductCategoryData>();
		//对基金数据进行拆分解析
		for(FundBasicData fundbasic:fundBasicList){
			ProductCategoryData categoryData=FundClassificationRule.classifyFund(fundbasic);
			result.add(categoryData);
		}	
		return result;		
	}
	
	
	/**
	 * 根据基金的原始数据生成为基金产品分类数据
	 * @param fundBasicList
	 * @return
	 */
	public List<ProductCategoryData> generateDepositProductCategory(List<DepositBasicData> depositBasicList){
		if (depositBasicList==null){
			logger.error("异常，基金List数据为空");
			return null;
		}	
		List<ProductCategoryData> result=new ArrayList<ProductCategoryData>();
		//对基金数据进行拆分解析
		for(DepositBasicData depositBasic:depositBasicList){
			ProductCategoryData categoryData=DepositClassificationRule.classifyDeposit(depositBasic);
			result.add(categoryData);
		}	
		return result;		
	}
	
	
	/**
	 * 对基金产品进行分类
	 * @return
	 */
   public void startClassifyAllFund(){
	   FundClassificationDBUtil.deleteFundCategory();
	   
	   List<FundBasicData> result=new ArrayList<FundBasicData>();
	 //先计算数据总条数
	 		Integer total=dao.getFundProductTotalNumber();
	 		//计算分页，以及余数，数据将用于for循环获得所有数据库中所有的数据
	 		Integer totalPage=total/maxItems; //页数
	 		Integer restItem=total%maxItems; //最后一页余下数据的数量，作为Limit
	 		
	 		/*设置一个Page对象*/
	 		Page page=new Page();
	 		page.setLimit(maxItems);
	 		
	 		
	 		List<FundBasicData> fundData=null;
	 		List<ProductCategoryData> categoryData=null;
	 	   //获取对应页数的数据
	 		for (int i=1;i<=totalPage+1;i++){
                	page.setPage(i);
                fundData=dao.getFundProductDataByPage(page);
                //获取后进行转换
                categoryData=generateProductCategory(fundData);
                //调用对应的jdbc存入数据库--------------------------------------
                FundClassificationDBUtil.insertBatches(categoryData);
                //--------------------------------------
                //释放资源
                fundData.clear();
                categoryData.clear();
	 	    }
   }
	
   
   
	/**
	 * 获取数据库存款表的所有数据
	 * @return
	 */
  public void startClassifyAllDeposit(){
	   DepositClassificationDBUtil.deleteDepositCategory();
	   
	   List<DepositBasicData> result=new ArrayList<DepositBasicData>();
	 //先计算数据总条数
	 		Integer total=dao.getDepositProductTotalNumber();
	 		//计算分页，以及余数，数据将用于for循环获得所有数据库中所有的数据
	 		Integer totalPage=total/maxItems; //页数
	 		Integer restItem=total%maxItems; //最后一页余下数据的数量，作为Limit
	 		
	 		/*设置一个Page对象*/
	 		Page page=new Page();
	 		page.setLimit(maxItems);
	 		
	 		List<DepositBasicData> depositData=null;
	 		List<ProductCategoryData> categoryData=null;
	 	   //获取对应页数的数据
	 		for (int i=1;i<=totalPage+1;i++){
               	page.setPage(i);
               depositData=dao.getDepositProductDataByPage(page);
               //获取后进行转换
               categoryData=generateDepositProductCategory(depositData);
               //调用对应的jdbc存入数据库--------------------------------------
               FundClassificationDBUtil.insertBatches(categoryData);
               //--------------------------------------
               //释放资源
               depositData.clear();
               categoryData.clear();
	 	    }
  }

	
	
}
