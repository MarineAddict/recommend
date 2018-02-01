package com.yihuisoft.product.customegroup.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihuisoft.product.customegroup.entity.ProductGroupDetails;
import com.yihuisoft.product.customegroup.service.GroupService;

@Controller
@RequestMapping("/custom")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * @author tangjian
	 * @Description:获取客户的组合信息
	 * @param planId @param cusId
	 * @return  List<Double> 
	 * @date:   2018年1月18日 下午12:44:47
	 */
	@RequestMapping("/getCustomeGroup")
	@ResponseBody
	public List<ProductGroupDetails> getCustomeGroup(String planId,String cusId){
		List<ProductGroupDetails> listProductGroup =  groupService.getCustomeGroup(planId,cusId);
//		/Map<String,List<ProductGroupDetails>> map = new HashMap<String, List<ProductGroupDetails>>();
		return listProductGroup;
	}
	/**
	 * @Title: getCustomGroupInfo 
	 * @author tangjian
	 * @Description:获取组合的收益率和风险率和组合产品信息
	 * @param planId
	 * @param cusId
	 * @return  List<ProductGroupDataDay>
	 * @date:   2018年1月19日 下午8:02:14
	 */
	@RequestMapping("/getCustomGroupInfo")
	@ResponseBody
	public List<ProductGroupDetails> getCustomGroupInfo(String planId,String cusId){
		List<ProductGroupDetails> listProductGroup =  groupService.getCustomGroupInfo(planId,cusId);
		return listProductGroup;
	}
	/**
	 * 通过组合获取组合的历史收益信息
	 * @Title: getCustomGroupIncome 
	 * @author tangjian
	 * @param groupId
	 * @param startTime
	 * @param endTime
	 * @return  List<ProductGroupDetails>
	 * @date:   2018年1月25日 下午3:16:30
	 */
	@RequestMapping("/getCustomGroupIncome")
	@ResponseBody
	public List<ProductGroupDetails> getCustomGroupIncome(String groupId,String startTime,String endTime){
		List<ProductGroupDetails> details = groupService.getCustomGroupIncome(groupId, startTime, endTime);
		return details;
	}
	
	
}
