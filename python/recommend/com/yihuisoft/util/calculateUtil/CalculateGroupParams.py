# -*- coding: UTF-8 -*-
from recommend.com.yihuisoft.util.calculateUtil.CalculateData import calculateCovariance;
import math;
import pandas as pd

'''
    @Desc: 计算组合所需协方差矩阵
    @Author: yongquan.xiong
    @Date: 2018-01-17
    @Param: typeList:组合中各产品类型   type:list  0：代表不需要计算协方差；  1：需要计算协方差
            codeList：组合中各产品代码   type:list
            yieldListMap：组合中各产品收益率  type:dict<code,yieldlist>
'''
def calculateGroupCov(typelist,codelist,yieldmap):
    groupCovariance=[];     #组合的协方差矩阵
    for index1,item1 in enumerate(typelist):
        tempCovarianceList=[];
        for index2,item2 in enumerate(typelist):
            if item1 <= 0 or item2 <= 0 :
                cov=0;
            else:
                yieldlist1= yieldmap[codelist[index1]];    # 取出对应code的数据
                yieldlist2= yieldmap[codelist[index2]];    # 取出对应code的数据
                cov=calculateCovariance(yieldlist1,yieldlist2);
            tempCovarianceList.append(cov)
        groupCovariance.append(tempCovarianceList);
    return groupCovariance;


'''
    @Desc: 计算几何平均数
    @Author: yongquan.xiong
    @Date: 2018-01-22
    @Param: value_list: 变量序列 （如 收益率序列 or 风险率序列）
'''
def calculateGeometricMean(value_list):
    length=len(value_list);
    if length > 0:
        sum=1;
        for x in value_list:
            sum*=(x + 1);
        return math.pow(sum,1/length) -1;
    else:
        return ;    #None

def calculate_group_params(data):
    temp=[value for key,value in data.groupby(data['code'])]
    r=[]
    for value in temp:
        code=str(value.iloc[0]['code'])
        value.fillna(method='ffill',inplace=True)
        value.fillna(method='bfill',inplace=True)
        value['ret']=value['close'].pct_change()
        value.dropna(inplace=True)
        value.index=value['date']
        value=value['ret']
        value.rename(code,inplace=True)
        r.append(value)
    r=pd.concat(r,axis=1)
    r.dropna(inplace=True)

    mean=r.mean()
    mean=(1+mean)**252-1
    cov=r.cov()*252

    return mean,cov




