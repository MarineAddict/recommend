package com.yihuisoft.product.category.web;

import com.yihuisoft.product.category.entity.CategoryBig;
import com.yihuisoft.product.category.entity.CategorySmall;
import com.yihuisoft.product.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping(value = "/addbig")
    @ResponseBody
    public void addCategoryBig(String categoryBig){
        service.createNewCategoryBig(categoryBig);
    }

    @RequestMapping(value = "/addsmall")
    @ResponseBody
    public void addCategorySmall(String categorySmall, Integer bigcode, Integer smallcode){
        service.createNewCategorySmall(bigcode, smallcode, categorySmall);
    }

    @RequestMapping(value = "/changebig")
    @ResponseBody
    public void changeCategoreBig(Integer bigcode, String bigCategore){
        service.changeCategoryBig(bigcode,bigCategore);
    }

    @RequestMapping(value = "/changesmall")
    @ResponseBody
    public void changeCategoreSmall(Integer smallcode, String smallCategore){
        service.changeCategorySmall(smallcode,smallCategore);
    }

    @RequestMapping(value = "/deletebig")
    @ResponseBody
    public void deleteCategoreBig(Integer bigId){
        service.deleteCategoryBig(bigId);
    }

    @RequestMapping(value = "/deletesmall")
    @ResponseBody
    public void deleteCategoreSmall(Integer smallId){
        service.deleteCategorySmall(smallId);
    }

    @RequestMapping(value = "/selectAllbig")
    @ResponseBody
    public List<CategoryBig> selectAllBig(){
        List<CategoryBig> categoryBigs = service.selectAllCategoryBig();
        return categoryBigs;
    }

    @RequestMapping(value = "/selectAllsmall")
    @ResponseBody
    public List<CategorySmall> selectAllSmall(Integer id){
        List<CategorySmall> categorySmalls = service.selectAllCategorySmall(id);
        return categorySmalls;
    }

}
