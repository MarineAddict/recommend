package com.yihuisoft.product.macrofactor.service;

import com.yihuisoft.product.macrofactor.mapper.MacroFactorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MacroFactoryService {

    @Autowired
    private MacroFactorMapper macroFactorMapper;


    public List getAllInfo(String entity,String startTime,String endTime) {
        if("MACRO_CPI".equalsIgnoreCase(entity)){
            return  macroFactorMapper.getAll_MARCO_CPI(startTime,endTime);
        }else if("MACRO_GDP".equalsIgnoreCase(entity)){
            return  macroFactorMapper.getAll_MARCO_GDP(startTime,endTime);
        }else if("MACRO_MONEY_SUPPLY".equalsIgnoreCase(entity)){
            return  macroFactorMapper.getAll_MARCO_MONEY_SUPPLY(startTime,endTime);
        }else if("MACRO_PMI".equalsIgnoreCase(entity)){
            return  macroFactorMapper.getAll_MARCO_PMI(startTime,endTime);
        }else if("MACRO_PPI".equalsIgnoreCase(entity)){
            return  macroFactorMapper.getAll_MARCO_PPI(startTime,endTime);
        }
         return null;
    }
}
