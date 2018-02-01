# -*- coding: UTF-8 -*-
from scipy import optimize
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging

'''
     @Desc: 返回有效前沿线数据
     @Author: yongquan.xiong
     @Date: 2018-01-17
 '''
class EffcientFrontier:

    logger=getlogging();    #获取日志对象

    '''
        @Desc: 返回有效前沿线数据
        @Author: yongquan.xiong
        @Date: 2018-01-17
        @Param: yieldlist：组合中各产品预期收益率   type:list
                covlist：组合中各产品协方差矩阵  type:list[list]
                point_count: 点 数量
    '''
    def efficientFrontier(self,yieldlist,covlist,point_count):
        eff=EffcientFrontier()
        eff.yieldlist=yieldlist;
        eff.covlist=covlist;
        noa=len(yieldlist);
        #参数值(权重)上下限。这些值以多个元组组成的一个元组形式提供给最小化函数
        bnds=tuple((0.0,1.0) for x in range(noa))
        min_yield=min(yieldlist);
        max_yield=max(yieldlist);
        target_returns = np.linspace(min_yield,max_yield,point_count*2) # min_yield  与 max_yield 之间 取样本数 point_count*2
        target_variance = []    #预期波动率
        target_weights = []    #权重
        for tar in target_returns:
            cons = ({'type':'eq','fun':lambda weights:eff.statistics(weights)[0]-tar},{'type':'eq','fun':lambda weights:np.sum(weights)-1})
            res =optimize.minimize(eff.min_variance, noa*[1./noa,],method = 'SLSQP', bounds = bnds, constraints = cons)
            target_variance.append(res['fun'])
            target_weights.append(np.around(res['x'],5))    #(保留 5 位)

        #取 数据 递增部分（有效前沿部分）
        min_variance=min(target_variance);  #最小风险率
        index_min_variance=target_variance.index(min_variance); #最小值的位置
        efficient_returns=target_returns[index_min_variance:]  #截取target_returns
        efficient_variance=target_variance[index_min_variance:]  #截取 target_variance
        efficient_weights=target_weights[index_min_variance:]   #截取 target_weights

        #绘制 有效前沿线
        eff.draw_pic(efficient_returns,efficient_variance);
        #绘制 全图
        eff.draw_pic(target_returns,target_variance);

        result=[];  # 返回 结果
        result.append(np.around(efficient_variance,5)); #组合风险   (保留 5 位)
        result.append(np.around(efficient_returns,5));  #组合收益
        result.append(efficient_weights);  #产品权重
        return result;

    #建立statistics函数来记录组合统计数据（收益，风险和夏普比）
    def statistics(self,weights):
        weights=np.array(weights)
        pret=np.dot(self.yieldlist,weights.T)
        pvol=np.sqrt(np.dot(weights,np.dot(self.covlist,weights.T)))
        return np.array([pret,pvol,pret/pvol])  #组合收益率 风险率  夏普比率

    # 投资组合优化：组合的有效前沿
    def min_variance(self,weights):
        return self.statistics(weights)[1]

    # 数据转置方法
    def transpose(self,data):
        return [list(row) for row in np.array(data).T]

    #绘制图形
    def draw_pic(self,target_returns,target_variance):
        plt.show()
        plt.figure(figsize=(8,4))
        #绘图
        plt.scatter(target_variance,target_returns, c = target_returns/target_variance, marker = '.')
        plt.grid(True)
        plt.xlabel('expected volatility')   #预期波动率
        plt.ylabel('expected return')   #预期收益率
        plt.colorbar(label='Sharp ratio')   #夏普比率


