package com.yihuisoft.product.macrofactor.mapper;

import com.yihuisoft.product.macrofactor.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MacroFactorMapper {

    List<MARCO_CPI> getAll_MARCO_CPI(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<MARCO_GDP> getAll_MARCO_GDP(@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<MARCO_MONEY_SUPPLY> getAll_MARCO_MONEY_SUPPLY(@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<MARCO_PMI> getAll_MARCO_PMI(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<MARCO_PPI> getAll_MARCO_PPI(@Param("startTime") String startTime,@Param("endTime") String endTime);

}
