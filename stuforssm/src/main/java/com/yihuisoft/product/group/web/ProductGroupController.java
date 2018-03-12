package com.yihuisoft.product.group.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihuisoft.product.group.entity.ProductGroup;
import com.yihuisoft.product.group.entity.ProductGroupDetails;
import com.yihuisoft.product.group.entity.dto.ProductGroupDTO;
import com.yihuisoft.product.group.service.ProductGroupService;
import com.yihuisoft.product.group.service.ProductGroupTimeService;


/**
 * Created by wangyinyuo on 2018/1/17.
 */
@RestController
@RequestMapping("/productGroup/")
public class ProductGroupController {
	
	private Logger logger=LoggerFactory.getLogger(ProductGroupController.class);

    @Autowired
    ProductGroupService productGroupService;
    
    @Autowired
    ProductGroupTimeService productGroupTimeService;
    
    /**
     * wangyinuo	2018-01-23
     * 获取组合的风险率和收益率
     * @param productGroupId
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/yieldRatioLine")
    public List<ProductGroup> getYieldRatioLine(String productCode, String startTime, String endTime){
        return productGroupService.getProductGroupYieldRatioLine(productCode, startTime, endTime);
    }

    /**
     * wangyinuo	2018-01-23
     * 获取单个产品组合的详细信息信息
     * @param productGroupId
     * @return
     */
    @RequestMapping("/productGroupId/{productGroupId}/getProductGroupDetailsInfo")
    @ResponseBody
    private ProductGroupDTO getProductGroupDetailsInfo(@PathVariable String productGroupId) {
    	return productGroupService.getProductGroupDetailsInfo(productGroupId);
    }

    /**
     * 返回所有产品组合的详细信息
     * @return
     */
    @RequestMapping("/getProductGroupAllInfo")
    public List<ProductGroupDTO> getProductGroupAllInfo(String productCode, String startTime, String endTime) {
    	return productGroupService.getProductGroupAllInfo(productCode, startTime, endTime);
    }

    /**
     * 组合信息插入
     * @param name  产品组合名称
     * @param createSource  创建来源（1是后台管理，2是客户配置）
     * @return
     */
    @RequestMapping(value = "/insertProductGroup/createSource/{createSource}/add-productGroup",method = RequestMethod.GET)
    public int insertProductGroup(String Groupname, @PathVariable int createSource,ProductGroupDetails list){
    	int num = 0;
    	try {
			String str=new String(Groupname.getBytes("ISO-8859-1"),"utf-8");
	    	num = productGroupService.insertProductGroup(str,createSource,list);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.info("组合名称字符转码错误");
		}
        return num ;
    }

    /**
     * 组合实现软删除，在数据库basic表中status置为0
     * @param product_group_id
     * @return
     */
    @RequestMapping(value = "/productGroupId/{product_group_id}/delete-productGroup")
    public int deleteProductGroup(@PathVariable String product_group_id) {
        int num = productGroupService.deleteProductGroup(product_group_id);
        return num ;
    }

    /**
     * 计算单个产品组合的每天净值
     * @param product_group_id
     */
    @RequestMapping(value = "/productGroupId/{product_group_id}/get-single-product-group-navadj")
    public void getProductGroupNavadj(@PathVariable String product_group_id) {
    	productGroupTimeService.getSingleProductGroupNavadj(product_group_id);
    }

    /**
     * 计算所有产品组合的每天净值
     */
    @RequestMapping(value = "/get-all-product-group-navadj")
    public void getAllProductGroupNavadj() {
    	productGroupTimeService.getAllProductGroupNavadj();
    }
}
