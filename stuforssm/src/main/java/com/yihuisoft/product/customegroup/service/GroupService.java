package com.yihuisoft.product.customegroup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yihuisoft.product.customegroup.entity.CusIncomeTracing;
import com.yihuisoft.product.customegroup.entity.ProductGroupDetails;
import com.yihuisoft.product.customegroup.mapper.CusIncomeTracingMapper;
import com.yihuisoft.product.customegroup.mapper.ProductGroupDetailsMapper;
import com.yihuisoft.product.finance.service.FinanceService;

@Service
public class GroupService {

	@Autowired
	private CusIncomeTracingMapper cusincomeMapper;

	@Autowired
	private ProductGroupDetailsMapper groupDetailsMapper;

	private Logger logger = LoggerFactory.getLogger(FinanceService.class);

	/**
	 * @Title: getCustomeGroup
	 * @author tangjian
	 * @Description: 通过规划id和客户id查询客户规划下对应的产品组合信息
	 * @param planId
	 * @param cusId
	 * @return ProductGroupDetails 组合下的产品列表
	 * @date: 2018年1月19日 下午1:26:10
	 */
	public List<ProductGroupDetails> getCustomeGroup(String planId, String cusId) {
		if ((!"".equals(planId) && planId != null)
				&& (!"".equals(cusId) && cusId != null)) {
			CusIncomeTracing cusIncomeTracing = cusincomeMapper
					.selectGroupIdByPlanAndCusId(planId, cusId);
			List<ProductGroupDetails> groupProductList = groupDetailsMapper
					.selectProductGroupByGroupId(cusIncomeTracing.getGroupId());
			if (CollectionUtils.isEmpty(groupProductList)) {
				return null;
			}
			return groupProductList;
		}

		return null;
	}

	/**
	 * @Title: getCustomGroupInfo
	 * @author tangjian
	 * @Description: 查询客户组合产品的收益和信息
	 * @param planId
	 * @param cusId
	 * @return List<ProductGroupDetails>
	 * @date: 2018年1月20日 上午11:36:08
	 */
	public List<ProductGroupDetails> getCustomGroupInfo(String planId,
			String cusId) {
		if ("".equals(planId) || planId == null && "".equals(cusId)
				|| cusId == null) {
			return null;
		}
		CusIncomeTracing cusIncomeTracing = cusincomeMapper
				.selectGroupIdByPlanAndCusId(planId, cusId);
		List<ProductGroupDetails> customGroupInfo = groupDetailsMapper
				.selectCusProGroupByGroupId(cusIncomeTracing.getGroupId());
		if (CollectionUtils.isEmpty(customGroupInfo)) {
			return null;
		}
		return customGroupInfo;
	}

	/**
	 * 获取客户历史收益通过组合id
	 * @Title: getCustomGroupIncome 
	 * @author tangjian
	 * @param groupId
	 * @param startTime
	 * @param endTime
	 * @return  List<ProductGroupDetails>
	 * @date:   2018年1月25日 下午3:21:13
	 */
	public List<ProductGroupDetails> getCustomGroupIncome(String groupId,
			String startTime, String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		if (!"".equals(groupId) && groupId != null){
			map.put("groupId", groupId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
		}
		List<ProductGroupDetails> groupDetails = new ArrayList<ProductGroupDetails>();
		try {
			groupDetails = groupDetailsMapper.selectGroupById(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("组合历史收益信息获取失败");
		}
		return groupDetails;
	}
}
