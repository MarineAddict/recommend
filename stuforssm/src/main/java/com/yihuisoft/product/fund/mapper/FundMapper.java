package com.yihuisoft.product.fund.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.fund.entity.dao.FundBasicDAO;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;

/**
 * Created by wangyinyuo on 2018/1/18.
 */
public interface FundMapper {
    //获取一段时间区间内某个产品的收益率
    List<FundProductDAO> getFundYieldRisk(Map<String,String> map);
    
    //获取基金产品的基本信息
    List<FundBasicDAO> getFundBasicInfo(@Param("code") String code);
}
