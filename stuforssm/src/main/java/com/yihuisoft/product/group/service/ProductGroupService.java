package com.yihuisoft.product.group.service;

import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.group.entity.ProductGroup;
import com.yihuisoft.product.group.entity.ProductGroupBasic;
import com.yihuisoft.product.group.entity.ProductGroupDetails;
import com.yihuisoft.product.group.entity.dto.ProductGroupDTO;
import com.yihuisoft.product.group.mapper.ProductGroupMapper;
import com.yihuisoft.product.util.CommonCal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangyinyuo on 2018/1/17.
 */
@Service
public class ProductGroupService {
	private Logger logger=LoggerFactory.getLogger(ProductGroupService.class);

    @Autowired
    ProductGroupMapper productGroupMapper;
    
    /**
     * wangyinuo	2018-01-23
     * 获取组合的风险率和收益率
     * @param productGroupId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<ProductGroup> getProductGroupYieldRatioLine(String productGroupId, String startTime, String endTime){
        Map<String,String> map=new HashMap<String,String>();
        map.put("productGroupCode", productGroupId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return productGroupMapper.getProductGroupYieldRatioLine(map);
    }
    
    /**
     * wangyinuo	2018-01-23
     * 获取单个产品组合的详细信息信息
     * @param productGroupId
     * @return
     */
    public ProductGroupDTO getProductGroupDetailsInfo(String productGroupId) {
        Double Maxdrawdown = 0D;//计算最大回撤
        List<Double> doubleList = new ArrayList<Double>();
    	ProductGroupDTO productGroupDTO = new ProductGroupDTO();
    	ProductGroupBasic pgbList = productGroupMapper.getProductGroupDetailsBasic(productGroupId);
    	List<ProductGroupDetails> pgdList = productGroupMapper.getProductGroupDetailsInfo(productGroupId);
        Map<String,String> map=new HashMap<String,String>();
        map.put("productGroupCode", productGroupId);
        Calendar c = Calendar.getInstance();c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String startTime = new SimpleDateFormat("yyyy-MM-dd").format(y);
        String endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<ProductGroup> yieldList = productGroupMapper.getProductGroupYieldRatioLine(map);
        for (ProductGroup productGroup : yieldList) {
        	doubleList.add(productGroup.getNavaDj());
        }
        Maxdrawdown = CommonCal.calculateMaxdrawdown(doubleList);
        productGroupDTO.setMaxdrawdown(Maxdrawdown);
    	productGroupDTO.setId(pgbList.getId());
    	productGroupDTO.setStatus(pgbList.getStatus());
    	productGroupDTO.setCreate_source(pgbList.getCreate_source());
    	productGroupDTO.setSharpeRatio(pgbList.getSharpe_ratio());
    	productGroupDTO.setProduct_group_name(pgbList.getProduct_group_name());
    	productGroupDTO.setCreate_time(pgbList.getCreate_time());
    	productGroupDTO.setPgdList(pgdList);
    	return productGroupDTO;
	}
    
    /**
     * 返回所有产品组合的详细信息
     * @return
     */
    public List<ProductGroupDTO> getProductGroupAllInfo(String productCode, String startTime, String endTime){
    	Map<String,String> map=new HashMap<String,String>();
        map.put("productGroupCode", productCode);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("type", "select");
    	List<String> list = productGroupMapper.getProductGroup(map);
    	List<ProductGroupDTO> productGroupDTOList = new ArrayList<ProductGroupDTO>();
    	ProductGroupDTO productGroupDTO = null;
    	for (String id : list) {
    		productGroupDTO = getProductGroupDetailsInfo(id);
    		productGroupDTOList.add(productGroupDTO);
    	}
    	return productGroupDTOList;
    }

    /**
     * wangyinuo	2018-01-23
     * 组合信息插入
     * @param name  产品组合名称
     * @param createSource  创建来源（1是后台管理，2是客户配置）
     * @return
     */
    public int insertProductGroup(String name,int createSource,ProductGroupDetails pgd){
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",0);
        map.put("product_group_name",name);
        map.put("CREATE_SOURCE",createSource);
        map.put("start_time",time);
        map.put("create_time",time);
        productGroupMapper.insertProductGroup(map);
        int productGroupId = Integer.parseInt(map.get("id").toString())+1;
        String[] codeArray = pgd.getProduct_code().split(",");
        String[] proportionArray = pgd.getProportion().split(",");
        String[] product_typeArray = pgd.getProduct_type().split(",");
        List<ProductGroupDetails> list = new ArrayList<ProductGroupDetails>();
        for (int i = 0 ;i < codeArray.length;i++){
            pgd = new ProductGroupDetails();
            pgd.setProduct_code(codeArray[i]);
            pgd.setProportion(proportionArray[i]);
            pgd.setLast_mod_time(time);
            pgd.setProduct_type(product_typeArray[i]);
            list.add(pgd);
        }
        productGroupMapper.insertProductGroupDetails(productGroupId,list);
        //计算生成的产品组合的夏普比率
        double sharpRatio = ProductGroupCalculate.calculateGroupSharpRatio(productGroupId);
        productGroupMapper.updateProductGroupSharpRatio(productGroupId, sharpRatio);
        return Integer.parseInt(map.get("id").toString())+1;
    }
    
    /**
     * wangyinuo	2018-01-23
     * 删除/禁用组合（软删除，将基本表中的STATUS字段置为0）
     * @param product_group_id
     * @return
     */
    public int deleteProductGroup(String product_group_id) {
    	int i = productGroupMapper.deleteProductGroup(product_group_id, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    	return i;
    }

}
