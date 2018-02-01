# -*- coding: UTF-8 -*-
import math
import numpy

"""
    @Desc:计算收益率
    @Author: yongquan.xiong
    @Date: 2018-01-10
    @Param: 公式：Ln(val1/val2)
     val1：例如基金，为本周期的复权单位净值
     val2：例如基金，为上一周期的复权单位净值
 """
def calculateYieidRatio(val1, val2):
    if val1<=0 or val2<=0:
        return  # return None
    else:
         return round(math.log(val1/val2),5);   #保留 5 位

"""
    @Desc:计算风险率，即标准差
    @Author: yongquan.xiong
    @Date: 2018-01-10
    @Param: 
           val：收益率list or tuple
 """
def calculateRisk(val):
    if len(val)>0:
        return round(numpy.std(val),5);    #保留 5 位
    else:
        return  # return None

"""
    @Desc:计算协方差
    @Author: yongquan.xiong
    @Date: 2018-01-10
    @Param: 
          val1：例如基金，为 A 基金收益率list or tuple
          val2：例如基金，为 B 基金收益率list or tuple
 """
def calculateCovariance(val1,val2):
    if len(val1)==len(val2) and len(val1)>1:
        return numpy.cov(val1,val2)[0][1]
    else:
        return  # return None

"""
    @Desc:返回协方差矩阵
    @Author: yongquan.xiong
    @Date: 2018-01-10
    @Param: 
           args：不定长参数，内容为收益率list or tuple
                 需保证传入的参数长度一致 且 长度大于 1
                 如：calculateCovarianceMatrix([1,2,3],[2,4,6],[1,3,5])
 """
def calculateCovarianceMatrix(*args):
    try:
        return numpy.cov(args)
    except:
         return  # return None

"""
    @Desc:计算半方差
    @Author: yongquan.xiong
    @Date: 2018-01-26
    @Param: val：收益率list or tuple 
 """
def calculateSemiVariance(val):
    if len(val) > 1:
        avg = sum(val)/(len(val)*1.0) #求平均收益率
        sum_val=0;
        for x in val:
            sum_val+=pow(min(0,(x - avg)),2);  #
        return round(math.sqrt(sum_val/(len(val)-1)),5);    #保留 5 位
    else:
        return  # return None

"""
    @Desc:计算左偏动差
    @Author: yongquan.xiong
    @Date: 2018-01-26
    @Param: yield_list：收益率list or tuple 
            tar_return: 目标收益率
 """
def calculateLeftVariance(yield_list,tar_return):
    if len(yield_list) > 1:
        sum_val=0;
        for x in yield_list:
            sum_val+=pow(max(0,(tar_return - x)),2);  #
        return round(math.sqrt(sum_val/(len(yield_list)-1)),5);    #保留 5 位
    else:
        return  # return None











