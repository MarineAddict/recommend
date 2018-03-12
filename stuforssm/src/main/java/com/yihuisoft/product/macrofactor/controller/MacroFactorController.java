package com.yihuisoft.product.macrofactor.controller;

import com.yihuisoft.product.macrofactor.service.MacroFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/macrofactor")
public class MacroFactorController {

    @Autowired
    private MacroFactoryService macroFactoryService;

    @RequestMapping("/entity/{entity}/getAllInfo")
    @ResponseBody
    public List<Object> getAllInfo(@PathVariable String entity,String startTime,String endTime){
        return macroFactoryService.getAllInfo(entity,startTime,endTime);
    }

}
