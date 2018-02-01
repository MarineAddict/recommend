# -*- coding: UTF-8 -*-
import pandas as pd
import numpy as np
from scipy import optimize
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging

'''
    @Desc: 计算组合最优解
    @Author: yongquan.xiong
    @Date: 2018-01-17
'''
class CalculateOptimum:

    logger=getlogging();    #获取日志对象

    '''
        @Desc: 由 组合收益率 计算 组合最小风险率 以及 各产品权重
        @Author: yongquan.xiong
        @Date: 2018-01-17
        @Param: yieldlist: 组合中各产品预期收益率   type:list
                covlist: 组合中各产品协方差矩阵  type:list[list]
                yield_ratio: 组合收益率
    '''
    def calculateOptimumByYield(self,yieldlist,covlist,yield_ratio):
        cal_op=CalculateOptimum()
        cal_op.yieldlist=yieldlist;
        cal_op.covlist=covlist;
        noa=len(yieldlist);

        temp_weights=[];    #循环时 临时权重
        cal_op.temp_weights=temp_weights;
        #参数值(权重)上下限。这些值以多个元组组成的一个元组形式提供给最小化函数
        bnds=tuple((0.0,1.0) for x in range(noa))
        # 给定组合收益率 ，求组合最小波动率及其权重
        cons = ({'type':'eq','fun':lambda weights:cal_op.statistics(weights)[0]-yield_ratio},{'type':'eq','fun':lambda weights:np.sum(weights)-1})
        res =optimize.minimize(cal_op.min_variance, noa*[1./noa,],method = 'SLSQP', bounds = bnds, constraints = cons)

        result=[];  # 返回 结果
        if res['status'] == 0 :    # 计算成功
            result.append(round(res['fun'],4)); #组合风险 (保留 4 位)
            result.append(round(yield_ratio,4));  #组合收益
            result.append(np.around(temp_weights[-1],4));  #目标 权重
        return result;


    '''
       @Desc: 由 组合风险率 计算 最大收益率 以及 各产品权重
       @Author: yongquan.xiong
       @Date: 2018-01-17
       @Param: yieldlist: 组合中各产品预期收益率   type:list
               covlist: 组合中各产品协方差矩阵  type:list[list]
               risk_ratio: 组合风险率
   '''
    def calculateOptimumByRisk(self,yieldlist,covlist,risk_ratio):
        cal_op=CalculateOptimum()
        cal_op.yieldlist=yieldlist;
        cal_op.covlist=covlist;
        noa=len(yieldlist);

        temp_weights=[];    #循环时 临时权重
        cal_op.temp_weights=temp_weights;
        #参数值(权重)上下限。这些值以多个元组组成的一个元组形式提供给最小化函数
        bnds=tuple((0.0,1.0) for x in range(noa))
        # 给定组合收益率 ，求组合最小波动率及其权重
        cons = ({'type':'eq','fun':lambda weights:cal_op.statistics(weights)[1]-risk_ratio},{'type':'eq','fun':lambda weights:np.sum(weights)-1})
        res =optimize.minimize(cal_op.max_yield, noa*[1./noa,],method = 'SLSQP', bounds = bnds, constraints = cons)

        result=[];  # 返回 结果
        if res['status'] == 0 :    # 计算成功
            result.append(round(risk_ratio,4)); #组合风险   (保留 4 位)
            result.append(round(-res['fun'],4));  #组合收益
            result.append(np.around(temp_weights[-1],4));  #目标 权重
        return result;

    #建立statistics函数来记录组合统计数据（收益，风险和夏普比）
    def statistics(self,weights):
        weights=np.array(weights)
        pret=np.dot(self.yieldlist,weights.T)
        pvol=np.sqrt(np.dot(weights,np.dot(self.covlist,weights.T)))
        return np.array([pret,pvol,pret/pvol])  #组合收益率 风险率  夏普比率

    #目标函数 ：风险率 最小
    def min_variance(self,weights):
        self.temp_weights.append(weights);
        return self.statistics(weights)[1];

    #目标函数 ：收益率 最大
    def max_yield(self,weights):
        self.temp_weights.append(weights);
        return -self.statistics(weights)[0];

