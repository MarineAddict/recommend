package com.yihuisoft.product.deposit.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.deposit.entity.Deposit;
import com.yihuisoft.product.deposit.entity.DepositBasic;
import com.yihuisoft.product.deposit.mapper.DepositBasicMapper;
import com.yihuisoft.product.deposit.service.DepositBasicService;
import com.yihuisoft.product.deposit.util.DepositCalculate;
import com.yihuisoft.product.util.CommonCal;
import com.yihuisoft.product.util.CategoryData.CategoryData;

/**
 *  @Description：DepositBasicServiceImpl.java
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/2/1-10:33
 *  @version    ：V_1.0.0
 */
@Service
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
    
    /**
     * 查询存款产品
     * @author zhaodc
     * @return
     */
    @SuppressWarnings("finally")
	public List<DepositBasic> qryDepositList(Integer start,Integer end,String productCode, String productName){
    	Map map=new HashMap();
    	map.put("start", start);
    	map.put("end", end);
    	map.put("productCode", productCode);
    	map.put("productName", productName);
    	List<DepositBasic> list=null;
    	try {
			list=depositBasicMapper.getDepositList(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return list;
		}
    }
    /**
     * 查询存款记录数
     * @author zhaodc
     * @return
     */
    @SuppressWarnings("finally")
    public int qryDepositListRows(int start,int end,String productCode, String productName){
    	Map map=new HashMap();
    	map.put("start", start);
    	map.put("end", end);
    	map.put("productCode", productCode);
    	map.put("productName", productName);
    	int rows=0;
    	try {
    		rows=depositBasicMapper.getDepositListRows(map);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}finally{
    		return rows;
    	}
    }
    
    
    
    public Map getDailyIncreaseList(String depositCode){
    	Map resultMap=new HashMap<>();
    	
    	DepositBasic deposit=null;
    	try{
    	deposit= qryDepositList(null,null,depositCode,null).get(0);
    	}catch (NullPointerException e) {
    		logger.error("没有找到对应 code="+depositCode+"的产品信息,无法获取涨幅信息");
			// TODO: handle exception
		}
    	List<Double> depositIncreaseData=null;
    	List<Double> bidIncreaseData=null;
    	List<String> timeData=null; 
    	
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Date today=new Date();
    	Date intEndDay=deposit.getIntEndDay();
    	Date intStartDay=deposit.getIntStartDay();
    	Double startMoney=deposit.getStartMoney();//最低存款额
    	Float interest=deposit.getInterest();//利率
    	
    	Integer bidCode=deposit.getCategoryBigCode();//标的号
    	Integer termDays=CommonCal.calDateBetween(intStartDay, intEndDay);
    	
        depositIncreaseData=DepositCalculate.calculateDailyIncrease(startMoney, intStartDay, intEndDay, interest, termDays);//产品的日增长率
        
        
    	List<Map<String,Object>> categoryData=CategoryData.getAllRequiredCategoryData(bidCode, sdf.format(intStartDay), sdf.format(intEndDay));
    	if(categoryData.size()>0){
    		List<Double> bidData=new ArrayList<Double>();
    		for(Map dataMap:categoryData){
    			bidData.add((Double) dataMap.get("data"));			
    		}
    		bidIncreaseData=CommonCal.IncreaseAmount(bidData); //标的对应的涨幅计算好
    	}
   
    	timeData=CommonCal.StringDateListInALength(intStartDay, intEndDay);//所有的时间
    	resultMap.put("depositIncreaseData", depositIncreaseData);
    	resultMap.put("bidIncreaseData", bidIncreaseData);
    	resultMap.put("timeData", timeData);
    	resultMap.put("prdName", deposit.getName());
    	resultMap.put("bidName", deposit.getTraceName());
    	return resultMap;
    	
    }
    
    
    
    
    
    
}
