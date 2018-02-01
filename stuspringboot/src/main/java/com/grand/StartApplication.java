package com.grand;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SpringBoot 启动类
 */

@Controller
@SpringBootApplication
@Configuration
@MapperScan("com.grand.fund.mapper")
@ComponentScan
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @RequestMapping(value = "hello")
    @ResponseBody
    public String hello(){
        return "hello world！";
    }
}