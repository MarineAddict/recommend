# -*- coding: UTF-8 -*-
import numpy as np


'''
    @Desc: 依据给定的组合各产品的权重 以及 对应收益率、协方差矩阵
           计算产品组合的 收益率 以及 风险率（波动率）
    @Author: yongquan.xiong
    @Date: 2018-01-18
    @Param: weights:组合中各产品权重   type:list
            yieldlist：组合中各产品预期收益率   type:list
            covlist：组合中各产品协方差矩阵  type:Double_list
'''
def calculateGroupRiskAndYield(weights,yieldlist,covlist):
    try:
        weights_T=transpose([weights]); #  转置

        target_risk=np.sqrt(np.dot(weights,np.dot(covlist,weights_T))); #组合的波动率！
        target_yield=np.dot(yieldlist,weights_T); #组合的收益！

        target_return=[];   #返回值
        target_return.append(round(target_risk[0],4));   #组合风险率 (保留 4 位)
        target_return.append(round(target_yield[0],4));  #组合收益率
    except:
        return ;    #  None

    return target_return;

# 数据转置方法
def transpose(data):
    return [list(row) for row in np.array(data).T]