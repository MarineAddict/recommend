package com.yihuisoft.product.backtest.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.backtest.entity.GroupInfo;

/**
 *  @Description：回归测试Mapper
 *  @author     ：yongquan.xiong
 *  @Data       ：2018.02.28
 */

public interface BackTestMapper {
    //根据组合Id 查找组合基本信息(MVO)
    List<GroupInfo> selectGroupBasicInfoForMvo(String groupCode);

    //根据组合Id 查找组合基本信息(Finance)
    List<GroupInfo> selectGroupBasicInfoForFinance(String groupCode);

    //根据组合Id 查找组合基本信息(Asset)
    List<GroupInfo> selectGroupBasicInfoForAsset(String groupCode);

    //自定义回测组合基本数据入库
    int insertBacktestProductGroup(Map<String,Object> map);

    //自定义回测组合详细数据入库
    int insertProductGroupDetails(@Param("productGroupId") String productGroupId, @Param("templist") List<GroupInfo> templist);

    //根据产品类型查询产品代码
    List<String> findProductCode(@Param("tableName")String tableName);

    //查询组合数据
    List<GroupInfo> selectPortfolioInfo();

    //查询基准数据
    List<GroupInfo> selectBenchmarkInfo();

}
