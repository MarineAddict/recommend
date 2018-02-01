package com.grand.fund.web;

import com.grand.fund.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FundController {

    @Autowired
    FundService fundService;

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public int update(String name){
        return fundService.update(name);
    }
}
