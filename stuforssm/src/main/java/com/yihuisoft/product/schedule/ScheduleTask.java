package com.yihuisoft.product.schedule;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

import com.yihuisoft.product.schedule.product_category.service.ClassifyService;

public class ScheduleTask {

	@Autowired
	@Qualifier("classifyServiceImp")
	private ClassifyService classifyService;
	
	/**
	 * 每天凌晨5点定时分类基金
	 */
	@Scheduled(cron = "0 0 5 * * ?")
	 public void execute1(){
		classifyService.startClassifyAllFund();
	    }
	
	
	/**
	 * 每天凌晨5点定时分类存款
	 */
	@Scheduled(cron = "0 0 5 * * ?")
	 public void execute2(){
		classifyService.startClassifyAllDeposit();
	    }
	
	
}
