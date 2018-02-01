package com.grand.fund.service;

import com.grand.fund.mapper.FundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FundService {

    @Autowired
    FundMapper fundMapper;

    public int update(String name){
        Map map = new HashMap();
        map.put("name",name);
        return fundMapper.update(map);
    }
}
