package com.grand.bond.service;

import com.grand.bond.mapper.BondMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BondService {

    @Autowired
    BondMapper bondMapper;

    public int update(String name){
        Map map = new HashMap();
        map.put("name",name);
        return 1;
    }
}
