package com.yihuisoft.product.group.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.group.entity.ProductGroupBasic;
import com.yihuisoft.product.group.entity.dao.ProductNavadjDAO;
import com.yihuisoft.product.group.mapper.ProductGroupMapper;

@Service
public class ProductGroupTimeService {
    @Autowired
    ProductGroupMapper productGroupMapper;
    
    /**
     * 计算单个产品组合的每天净值
     * @param product_group_id
     */
    public void getSingleProductGroupNavadj(String product_group_id) {
    	int flag = 0;
    	String startTime = productGroupMapper.getNavadjStartTime(product_group_id);
    	if (startTime == null){
    		flag = 1;
    		ProductGroupBasic groupBasic = productGroupMapper.getProductGroupDetailsBasic(product_group_id);
    		startTime = groupBasic.getCreate_time().split(" ")[0];
    	}
    	List<ProductNavadjDAO> fundNavadjList = productGroupMapper.getProductNavadj(product_group_id,"fund",startTime);
    	List<ProductNavadjDAO> financeNavadjList = productGroupMapper.getProductNavadj(product_group_id,"finance",startTime);
    	List<ProductNavadjDAO> pmNavadjList = productGroupMapper.getProductNavadj(product_group_id,"pm",startTime);
    	List<ProductNavadjDAO> depositNavadjList = productGroupMapper.getProductNavadj(product_group_id,"deposit",startTime);
    	List<ProductNavadjDAO> productGroupList = new ArrayList<ProductNavadjDAO>();
    	if (fundNavadjList.size()>0) {
    		for (ProductNavadjDAO productNavadjDAO : fundNavadjList) {
    			productGroupList.add(productNavadjDAO);
    		}
    	}
    	int status = 0;
    	if (productGroupList.size()>0) {
    		if (financeNavadjList.size()>0) {
	    		for (int i = 0 ; i< financeNavadjList.size();i++) {
	    			for (int j = 0; j <productGroupList.size();j++) {
		    			if (financeNavadjList.get(i).getNavlatestdate().equals(productGroupList.get(j).getNavlatestdate())) {
		    				double navadj=productGroupList.get(j).getNavadj() + financeNavadjList.get(i).getNavadj();
		    				productGroupList.get(j).setNavadj(navadj);
		    				status = j;
		    			}
	    			}
	    			if (status == (productGroupList.size()-1) && i != (financeNavadjList.size()-1)) {
	    				productGroupList.add(financeNavadjList.get(i));
	    			}
	    		}
    		}
    	}else {
    		for (ProductNavadjDAO productNavadjDAO : financeNavadjList) {
    			productGroupList.add(productNavadjDAO);
    		}
    	}
    	if (productGroupList.size()>0) {
    		if (pmNavadjList.size()>0) {
	    		for (int i = 0 ; i< pmNavadjList.size();i++) {
	    			for (int j = 0; j <pmNavadjList.size();j++) {
		    			if (pmNavadjList.get(i).getNavlatestdate().equals(productGroupList.get(j).getNavlatestdate())) {
		    				double navadj=productGroupList.get(j).getNavadj() + pmNavadjList.get(i).getNavadj();
		    				productGroupList.get(j).setNavadj(navadj);
		    				status = j;
		    			}
	    			}
	    			if (status == (productGroupList.size()-1) && i != (pmNavadjList.size()-1)) {
	    				productGroupList.add(pmNavadjList.get(i));
	    			}
	    		}
    		}
    	}else {
    		for (ProductNavadjDAO productNavadjDAO : pmNavadjList) {
    			productGroupList.add(productNavadjDAO);
    		}
    	}
    	if (productGroupList.size()>0) {
    		if (depositNavadjList.size()>0) {
	    		for (int i = 0 ; i< depositNavadjList.size();i++) {
	    			for (int j = 0; j <depositNavadjList.size();j++) {
		    			if (depositNavadjList.get(i).getNavlatestdate().equals(productGroupList.get(j).getNavlatestdate())) {
		    				double navadj=productGroupList.get(j).getNavadj() + depositNavadjList.get(i).getNavadj();
		    				productGroupList.get(j).setNavadj(navadj);
		    				status = j;
		    			}
	    			}
	    			if (status == (productGroupList.size()-1) && i != (depositNavadjList.size()-1)) {
	    				productGroupList.add(depositNavadjList.get(i));
	    			}
	    		}
    		}
    	}else {
    		for (ProductNavadjDAO productNavadjDAO : depositNavadjList) {
    			productGroupList.add(productNavadjDAO);
    		}
    	}
    	for (ProductNavadjDAO productNavadjDAO : productGroupList) {
			productNavadjDAO.setCode(product_group_id);
		}
    	if (flag == 0) {
    		productGroupList.remove(0);
    	}
    	if (productGroupList.size()!= 0) {
    		productGroupMapper.insertProductGroupNavadj(productGroupList);
    	}
    }
    
    /**
     * 查询出所有的产品组合信息，把产品组合id传进getProductGroupNavadj方法中计算每天净值数据
     */
    public void getAllProductGroupNavadj(){
        List<String> productGroupList = productGroupMapper.getProductGroup(new HashMap<String,String>());
        for (String id : productGroupList) {
        	getSingleProductGroupNavadj(id);
        }
    }
}
