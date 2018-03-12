package com.yihuisoft.product.deposit.service;

import java.util.List;
import java.util.Map;

import com.yihuisoft.product.deposit.entity.Deposit;
import com.yihuisoft.product.deposit.entity.DepositBasic;

/**
 *  @Description：存单业务处理
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/2/1-10:26
 *  @version    ：V_1.0.0
 */
public interface DepositBasicService {

    /**
     * 查询询单信息列表(没有code、name默认查全部）
     * @param code  存单代码
     * @param name  存单名称
     * @return  存单信息集合
     */
    List<DepositBasic> getDeposit(String code, String name);

    /**
     * 获取收益率曲线
     * @param productCode   存单代码
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return  存单数据集合
     */
    List<Deposit> getYieldRatioLine(String productCode, String startTime, String endTime);

    /**
     * 获取收益率值
     * @param productCode   存单代码
     * @return  存单收益率
     */
    float getImcome(String productCode);

    /**
     * 获取风险率值
     * @param productCode   存单代码
     * @return  存单风险率
     */
    float getRisk(String productCode);
    
    /**
     * 分页查询存款产品
     * @author zhaodc
     * @param start
     * @param end
     * @param productCode
     * @param productName
     * @return
     */
    List<DepositBasic> qryDepositList(Integer start,Integer end,String productCode, String productName);
    int qryDepositListRows(int start,int end,String productCode, String productName);
    /**
     * 根据产品号获取涨幅（数据跟随起息日到今天）
     * @param depositCode
     * @return
     */
    Map getDailyIncreaseList(String depositCode);
}
