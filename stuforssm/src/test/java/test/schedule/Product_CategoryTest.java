package test.schedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yihuisoft.product.deposit.service.impl.DepositBasicServiceImpl;
import com.yihuisoft.product.schedule.product_category.dao.BaseDao;
import com.yihuisoft.product.schedule.product_category.service.ClassifyService;
import com.yihuisoft.product.util.CommonCal;
import com.yihuisoft.product.util.CategoryData.CategoryData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class Product_CategoryTest {

	@Autowired
	private BaseDao dao;
	
	@Autowired
	@Qualifier("classifyServiceImp")
	ClassifyService service;

	@Autowired
	DepositBasicServiceImpl depositBasicService;
	
	
	
	
	@Test
 public void test(){
		service.startClassifyAllFund();
		service.startClassifyAllDeposit();
 }
	
/*	@Test
 public void test2(){
		service.startClassifyAllDeposit();;
 }*/
	
/*	@Test
 public void test3(){
		System.out.println(CategoryData.getAllRequiredCategoryData(7, "2018-01-01", "2018-03-08"));
 }*/
	
	/*@Test
	 public void test4(){
			System.out.println(depositBasicService.getDailyIncreaseList("CMBR20180310"));
	 }*/
	
/*	@Test
	 public void test4(){
		Date startDay=new Date();
		Date EndDate=new Date(startDay.getTime()+1000*60*60*24*6);
		List list=CommonCal.StringDateListInALength(startDay, EndDate);
		System.out.println(list);
	 }*/
	
/*	@Test
	 public void test4(){
		
		Map map=depositBasicService.getDailyIncreaseList("CMBR20180311");
	 }*/
	
	
//	
	
}
