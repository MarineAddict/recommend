package com.yihuisoft.product.backtest.mapper;

import java.util.List;

import com.yihuisoft.product.backtest.entity.GroupInfo;

/**
 *  @Description：产品组合Mapper
 *  @author     ：yongquan.xiong
 *  @Data       ：2018.02.27
 */

public interface GenerateGroupMapper {
    //查找新生成产品组合（组合Id 最大前 5 条）
    List<GroupInfo> selectGroupInfo();

}
