package com.grand.fund.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface FundMapper {
    int update(@Param("map")Map map);
}
