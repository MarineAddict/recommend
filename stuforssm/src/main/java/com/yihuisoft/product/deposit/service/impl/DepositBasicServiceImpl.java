package com.yihuisoft.product.deposit.service.impl;

import com.yihuisoft.product.deposit.entity.Deposit;
import com.yihuisoft.product.deposit.entity.DepositBasic;
import com.yihuisoft.product.deposit.mapper.DepositBasicMapper;
import com.yihuisoft.product.deposit.service.DepositBasicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @Description：DepositBasicServiceImpl.java
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/2/1-10:33
 *  @version    ：V_1.0.0
 */
/*@Service*/
public class DepositBasicServiceImpl implements DepositBasicService {

    private Logger logger= LoggerFactory.getLogger(DepositBasicService.class);

    @Autowired
    private DepositBasicMapper depositBasicMapper;

    /**
     * 根据CODE查询存单信息，为空默认查询全部
     * @param code  存单代码
     * @param name  存单名称
     * @return
     */
    @Override
    public List<DepositBasic> getDeposit(String code, String name) {
        Map<String, Object> map = new HashMap();
        List<DepositBasic> depositBasicList = new ArrayList<>();
        map.put("depositcode", code);
        map.put("depositname", name);
        try{
            depositBasicList  = depositBasicMapper.getDepositBasicList(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取存单产品列表失败："+e.getMessage());
        }
        return depositBasicList;
    }

    /**
     * 获取收益曲线
     * @param productCode   存单代码
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return
     */
    @Override
    public List<Deposit> getYieldRatioLine(String productCode, String startTime, String endTime) {
        Map<String,String> map=new HashMap<String,String>();
        List<Deposit> list=new ArrayList<Deposit>();
        map.put("productCode", productCode);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        try {
            list= depositBasicMapper.getYieldRatioLine(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取存单信息列表失败："+e.getMessage());
        }
        return list;
    }

    /**
     * 获取收益率
     * @param productCode   存单代码
     * @return
     */
    @Override
    public float getImcome(String productCode) {
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("productCode", productCode);
        float imcome = 0.0f;
        try{
            imcome = depositBasicMapper.getIncome(map);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取存单收益率失败："+e.getMessage());
        }
        return imcome;
    }

    /**
     * 获取风险率
     * @param productCode   存单代码
     * @return
     */
    @Override
    public float getRisk(String productCode) {
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("productCode", productCode);
        float risk = 0.0f;
        try{
            risk = depositBasicMapper.getRisk(map);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取存单风险率失败："+e.getMessage());
        }
        return risk;
    }
}
