package com.yihuisoft.product.deposit.mapper;

import com.yihuisoft.product.deposit.entity.Deposit;
import com.yihuisoft.product.deposit.entity.DepositBasic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 *  Description ：存单基本信息数据处理
 *  @author     ：Machengxin
 *  @Data       ：2018/1/25-12:50
 *  @version    ：V_1.0.0
 */
public interface DepositBasicMapper {

    /**
     * 查询所有存单基本信息
     * @param map
     * @return
     */
    List<DepositBasic> getDepositBasicList(@Param("map")Map map);

    /**
     * 获取收益率曲线
     * @param map
     * @return
     */
    List<Deposit>getYieldRatioLine(@Param("map")Map map);

    /**
     * 获取收益率
     * @param map
     * @return
     */
    @Select("SELECT INTEREST FROM DEPOSIT_BASIC_DATA WHERE CODE = #{map.productCode}")
    float getIncome(@Param("map")Map map);

    @Select("SELECT DISTINCT RISK_RATIO FROM DEPOSIT_DATA_DAY WHERE CODE = #{map.productCode}")
    float getRisk(@Param("map")Map map);
}
