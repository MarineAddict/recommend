# -*- coding: utf-8 -*-
"""
Created on Mon Feb  5 15:03:36 2018

@author: wangjian
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import scipy.stats as st
import scipy.integrate as sci
import datetime
from sklearn import linear_model

asset=[]

##将start~end之间的时间按rebalancing划分
def rebalance(start_year=2010,end_year=2017,rebalancing='rebalance monthly'):
    ##产生交易年份月份
    years=[]
    for temp_year in range(start_year,end_year+1):
        years+=[temp_year]*12
    months=list(range(1,13))*(end_year-start_year+1)
    trade_month=pd.DataFrame({'year':years,'month':months})
    trade_month['seq']=list(range(trade_month.shape[0]))

    if rebalancing=='rebalance_monthly':
        trade_month['seq']=trade_month['seq']+1
    elif rebalancing=='rebalance_quarterly':
        trade_month['seq']=trade_month['seq']//3+1
    elif rebalancing=='rebalance_semi-annually':
        trade_month['seq']=trade_month['seq']//6+1
    elif rebalancing=='rebalance_annually':
        trade_month['seq']=trade_month['seq']//12+1
    elif rebalancing=='None':
        trade_month['seq']=1
    else:
        pass
    
    return trade_month

##将资产日收盘价数据转换成月度收益率
def close2mret(close_data):
    if (not 'year' in close_data.columns) or (not 'month' in close_data.columns):
        close_data['date']=[datetime.datetime.strptime(date,'%Y-%m-%d') for date in close_data['date']]
        close_data['year']=[date.year for date in close_data['date']]
        close_data['month']=[date.month for date in close_data['date']]
    
    close_data['year_month']=close_data['year']*100+close_data['month']
    ret=close_data['close'].groupby(close_data['year_month']).agg(lambda x:x.tail(1))
    ret=pd.DataFrame(ret)
    ret['ret']=ret['close'].pct_change()
    ret['year']=ret.index//100
    ret['month']=ret.index-ret['year']*100
    ret['code']=close_data.iloc[0]['code']
    ret.index=list(range(ret.shape[0]))
    ret=ret.dropna()
    return ret[['code','year','month','ret']]

##回测初始化
def initialize(data,start_year=2010,end_year=2017,rebalancing='rebalance monthly'):
    data['date']=[datetime.datetime.strptime(str(date),'%Y-%m-%d') for date in data['date']]
    data['year']=[date.year for date in data['date']]
    data['month']=[date.month for date in data['date']]
    #temp=data.groupby(data['code']).agg(close2mret)
    
    temp=[]
    for key,value in data.groupby(data['code']):
        temp.append(close2mret(value))
    data=pd.concat(temp,ignore_index=True)
    
    trade_days=rebalance(start_year=start_year,end_year=end_year,rebalancing=rebalancing)
    data=pd.merge(data,trade_days)
    #asset['code']=asset.index
    #data=pd.merge(data,asset[['code','weight','money']])
    #del asset['code']
    
    return data
 
##计算一个周期内各资产类别每日的资金,并在周期最后一个交易日将各资产类别的资金按weight调整
def compute_money(index_data):
    #计算周期内某个资产每个交易日结束后账户的资金,
    #期间资产的盈利以及最后一个交易日账户中的资金
    def f(sub_index_data):
        sub_index_data.index=list(range(sub_index_data.shape[0]))
        code=sub_index_data['code'][0]
        # print('sub_index_data:',sub_index_data.shape)
        # print(sub_index_data.head())
        # print('asset:')
        # print(asset)
        # print('code type:',type(code))
        sub_index_data['money']=asset.loc[code]['money']*np.cumprod(sub_index_data['ret']+1)
        days=sub_index_data.shape[0]
        profit=sub_index_data['money'][days-1]-asset.loc[code]['money']  #计算周期内的盈利
        money=sub_index_data['money'][days-1]  #资产周期结束后的价值
        return sub_index_data,pd.DataFrame({'code':[code],'profit':[profit],'money':[money]})
    
    r=[]
    total_money=0    #账户中的总资金
    for key,value in index_data.groupby('code'):   #按资产代码进行分组
        temp_r=f(value)
        r.append(temp_r[0])
        asset.loc[temp_r[1]['code'][0],'profit']+=temp_r[1]['profit'][0] #计算资产目前总盈利
        total_money+=temp_r[1]['money'][0]
    r=pd.concat(r,ignore_index=True)
    r.index=r['code']
    
    asset['money']=total_money*asset['weight']  #对账户中各资产价值再平衡
    
    r1=[]
    weights=asset.iloc[np.where(asset['weight']>0)]['weight']
    for key,value in r.groupby([r['year'],r['month']]):
        value['ret1']=value['ret']*weights
        r1.append(value)
        weights=value['money']/value['money'].sum()
        
    r1=pd.concat(r1,ignore_index=True)
    
    return r1

#index_data=data.iloc[np.where(data['seq']==1)]
#r=compute_money(index_data)
#sub_index_data=index_data.iloc[np.where(index_data['code']=='sh000908')]

##对组合做回测:计算每月各资产类别的资金
def backtest(data):
    result=[]
    for key,value in data.groupby('seq'):
        #print(key)
        temp=compute_money(value)
        result.append(temp)
        
    result=pd.concat(result,ignore_index=True)
    
    return result

#组合月收益率的回撤序列以及最大回撤（ret是单月的对数收益率，非累积收益率）
#ret的类型为np.array
def maxdrawdowns(ret):
    cumret=np.cumsum(ret)
    cumret_max=[None]*len(ret)
    #计算序列的累积最大值
    cumret_max[0]=cumret[0]
    for i in range(1,ret.shape[0]):
        if cumret_max[i-1]<cumret[i]:
            cumret_max[i]=cumret[i]
        else:
            cumret_max[i]=cumret[i-1]
    #cumret_max=np.array([max(cumret[0:i]) for i in range(1,cumret.shape[0]+1)])
    diff_max=cumret-cumret_max
    drawdown=[diff_ret if diff_ret<0 else 0 for diff_ret in diff_max]
    
    return (drawdown,min(drawdown))

#组合月收益率的下行风险
#ret的类型为numpy.ndarray
def downside_risk(ret,target_mu=None):
    if target_mu is None:
        target_mu=np.mean(ret)
        
    ret0=ret-target_mu
    ret1=np.array([i if i<0 else 0 for i in ret0])
    return (ret1**2).sum()/len(ret)

#组合的夏普比
def sharpe_ratio(ret,rf=0,period='monthly'):
    if period=='monthly':
        return (np.mean(ret-rf))/np.std(ret-rf,ddof=1)*np.sqrt(12)
    elif period=='daily':
        return (np.mean(ret-rf))/np.std(ret-rf,ddfo=1)*np.sqrt(250)
    else:
        raise ValueError("period must be equal to daily or monthly")
        
#8.组合的索提诺比,mar为可接受最小收益率
def sortino_ratio(ret,mar=0,period='monthly'):
    ds_risk=downside_risk(ret,target_mu=mar)
    
    if period=='monthly':
        return (np.mean(ret)-mar)/ds_risk*np.sqrt(12)
    elif period=='daily':
        return (np.mean(ret)-mar)/ds_risk*np.sqrt(250)
    else:
        raise ValueError("period must be equal to monthly or daily")
        
#在险价值(VaR),分别利用历史模拟法与Delta-normal法,以及条件在险价值(利用历史模拟法计算)
#计算var(在险价值),cvar(条件在险价值)
def var_cvar(alpha,index_ret,method='hs'):
    mu=np.mean(index_ret)
    sigma=np.std(index_ret,ddof=1)
    c_alpha=st.norm().ppf(alpha)
    
    if method=='hs':
        var=-np.percentile(index_ret,alpha*100)
        cvar=-np.mean(index_ret[index_ret<=-var])
    elif method=='norm':
        q_alpha=st.norm(mu,sigma).ppf(alpha)
        var=-q_alpha
        #cvar=-sci.quad(lambda x:x*st.norm(mu,sigma).pdf(x),-np.inf,q_alpha)[0]/alpha
        cvar=-(mu-sigma*st.norm().pdf(c_alpha)/alpha)
    elif method=='cornish-fisher':
        index_ret0=(index_ret-mu)/sigma
        s=st.skew(index_ret0)
        k=st.kurtosis(index_ret0)
        
        var=-(mu+sigma*(c_alpha+(c_alpha**2-1)*s/6+(c_alpha**3-3*c_alpha)*k/24-(2*c_alpha**3-5*c_alpha)*s**2/36))
        
        m1=sci.quad(lambda x:x*st.norm().pdf(x),-np.inf,c_alpha)[0]/alpha
        m2=sci.quad(lambda x:x**2*st.norm().pdf(x),-np.inf,c_alpha)[0]/alpha
        m3=sci.quad(lambda x:x**3*st.norm().pdf(x),-np.inf,c_alpha)[0]/alpha
        
        cvar=-sigma*(m1+(m2-1)*s/6+(m3-3*m1)*k/24-(2*m3-5*m1)*s**2/36)-mu
    else:
        print("method must in ['hs','norm','conish-fisher']")
        return
        
    return (var,cvar)

##计算月度收益率序列的各种指标
##asset_mret,market_mret表示资产与市场指数月度收益率序列,为DataFrame结构,
##列名包括年份-year,月份-month,月度收益率-ret,资产代码-code
def calc_index(asset_mret,market_mret,alpha=0.05,rf=0):
    asset_mret.index=list(range(0,asset_mret.shape[0]))
    code=asset_mret.iloc[0]['code']
    m_periods=asset_mret.shape[0]    #月份数
    asset_mret['log_ret']=np.log(1+asset_mret['ret'])   #月度对数收益率
    asset_yret=asset_mret['log_ret'].groupby(asset_mret['year']).sum()
    asset_yret=pd.DataFrame(asset_yret)  #年度对数收益率
    asset_yret['ret']=np.exp(asset_yret['log_ret'])-1  #年度收益率
    
    market_mret.index=list(range(0,market_mret.shape[0]))
    market_mret['log_ret']=np.log(1+market_mret['ret']) #月度对数收益率
    
    #----------------------------计算指标-第一部分--------------------------------------
    cagr=np.exp(asset_mret['log_ret'].mean()*12)-1   #复合年化收益率
    stdev=np.std(asset_mret['ret'],ddof=1)  #月度收益率的标准差
    stdev_ann=stdev*np.sqrt(12)  #收益率的年化标准差
    yret_max,yret_min=asset_yret['ret'].max(),asset_yret['ret'].min()  #收益率最大最小值
    drawdowns=maxdrawdowns(asset_mret['log_ret'])
    maxdrawdown=np.exp(drawdowns[1])-1  #最大回撤
    drawdowns=np.exp(drawdowns[0])-1  #最大回撤序列
    SR=sharpe_ratio(asset_mret['ret'],rf)   #夏普比
    Sortino_R=sortino_ratio(asset_mret['ret'],mar=rf)   #索提诺比
    
    temp=pd.merge(asset_mret[['year','month','ret']],market_mret[['year','month','ret']],on=['year','month'])
    rho=temp[['ret_x','ret_y']].corr()['ret_y']['ret_x']   #计算asset_mret与market_mret的相关系数
    
    result1={'code':code,'CAGR':cagr,'Stdev':stdev_ann,'Best Year':yret_max,
            'Worst Year':yret_min,'Max.Drawdown':maxdrawdown,'Sharpe Ratio':SR,
            'Sortino Ratio':Sortino_R,'ret_peryear':asset_yret['ret'],
            'Market Correlation':rho}
    
    if not 'money' in asset_mret.columns:
        return pd.Series(result1)
    
    #---------------------第二部分指标--------------------------------------
    financial_balance=asset_mret.iloc[m_periods-1]['money']   #组合最终金额
    mret_mean=np.mean(asset_mret['ret'])  #月度收益率的均值
    yret_mean=np.mean(asset_yret['ret'])  #年度收益率的均值
    mret_gmean=np.exp(np.mean(asset_mret['log_ret']))-1  #复合月度收益率
    yret_gmean=np.exp(np.mean(asset_yret['log_ret']))-1  #复合年度收益率
    skew=st.skew(asset_mret['ret'])    #月度收益率的偏度
    kurtosis=st.kurtosis(asset_mret['ret'])   #月度收益率的的超额峰度
    downside_dev=downside_risk(asset_mret['ret'])  #月度收益率的半方差
    
    mret=asset_mret['ret'].values
    gain_loss_ratio=-mret[np.where(mret>0)].mean()/mret[np.where(mret<0)].mean()  #盈利损失比
    positive_periods=len(np.where(mret>0)[0])
    ratio=positive_periods/m_periods
    positive_periods=str(positive_periods)+' out of '+str(m_periods)+'('+str(ratio)+')'
    
    regr=linear_model.LinearRegression()
    x=(asset_mret['log_ret'].values-rf).reshape(-1,1)
    y=(market_mret['log_ret'].values-rf).reshape(-1,1)
    regr.fit(x,y)
    
    beta=regr.coef_[0][0]   #beta
    alpha_ann=np.exp(regr.intercept_[0]*12)-1   #年化alpha
    R_square=regr.score(x,y)   #线性回归R^2
    
    ret_mean_ann=np.exp(asset_mret['log_ret'].mean()*12)-1    #资产年化收益率
    treynor_ratio=(ret_mean_ann-rf)/beta   #特雷诺指数
    
    hs_var=var_cvar(alpha,asset_mret['ret'],method='hs')
    delta_var=var_cvar(alpha,asset_mret['ret'],method='norm')
    
    result2={'Beta':beta,'Alpha(annualized)':alpha_ann,'Treynor Ratio(%)':treynor_ratio*100,
            'R^2':R_square,'Financial_Balance':financial_balance,
            'Mean Return(monthly)':mret_mean,'Mean Return(annualized)':yret_mean,
            'Compound Return(monthly)':mret_gmean,'drawdowns':drawdowns,
            'Compound Return(annualized)':yret_gmean,'Volatility (monthly)':stdev,
            'Downside Deviation(monthly)':downside_dev,'Skewness':skew,
            'Excess Kurtosis':kurtosis,'Positive Periods':positive_periods,
            'Gain/Loss Ratio':ratio,
            'Historical Value-at-Risk('+str((1-alpha)*100)+'%)':-hs_var[0],
            'Delta Normal Value-at-Risk('+str((1-alpha)*100)+'%)':-delta_var[0],
            'Conditional Value-at-Risk('+str((1-alpha)*100)+'%)':-hs_var[1]}
    
    return pd.concat([pd.Series(result1),pd.Series(result2)])
    
##计算滚动收益率
def rolling_ret(asset_mret,num=3,period='year'):
    asset_mret['log_ret']=np.log(1+asset_mret['ret'])   #月度对数收益率
    if period=='month':
        if asset_mret.shape[0]<num:
            raise ValueError('data is not enough')
        asset_mret.index=asset_mret['year']*100+asset_mret['month']
        result=pd.rolling_sum(asset_mret['log_ret'],num)  #计算月度滚动对数收益率
        result=np.exp(result)-1   #计算月度滚动收益率
    elif period=='year':
        asset_yret=asset_mret['log_ret'].groupby(asset_mret['year']).sum()  #计算年度对数收益率
        if asset_yret.shape[0]<num:
            raise ValueError('data is not enough')
        result=pd.rolling_sum(asset_yret,num)   #计算年度滚动对数收益率
        result=np.exp(result)-1
        
    return result

##
def data_transform(index_mret,column='ret'):
    index_mret.index=index_mret['year']*100+index_mret['month']
    
    r=[]
    keys=[]
    for key,value in index_mret[column].groupby(index_mret['code']):
        r.append(value)
        keys.append(key)
    r=pd.concat(r,axis=1,keys=keys,join='inner')
    
    return r
    

##测试

def final_call(data,market_data,start_year,end_year,initial_amount,rebalancing,rf):
    data=initialize(data,start_year=start_year,end_year=end_year,rebalancing=rebalancing)
    market_ret=close2mret(market_data)
    market_ret=market_ret.iloc[np.where(market_ret['year']>=start_year)]
    market_ret=market_ret.iloc[np.where(market_ret['year']<=end_year)]
    market_ret['log_ret']=np.log(market_ret['ret']+1)
    
    portfolio_month=backtest(data)
    portfolio_month['year_month']=portfolio_month['year']*100+portfolio_month['month']
    asset_ret=data_transform(portfolio_month,column='ret1')
    asset_std=asset_ret.std()
    
    portfolio_month=portfolio_month['money'].groupby(portfolio_month['year_month']).sum()
    portfolio_month=pd.concat([pd.Series([initial_amount],index=[(start_year-1)*100+12]),portfolio_month])
    portfolio_month=pd.DataFrame(portfolio_month)
    portfolio_month['year']=portfolio_month.index//100
    portfolio_month['month']=portfolio_month.index-portfolio_month['year']*100
    portfolio_month.columns=['money','year','month']
    portfolio_month['code']='portfolio1'
    portfolio_month['ret']=portfolio_month['money'].pct_change()
    portfolio_month=portfolio_month.dropna()
    
    indexes=calc_index(portfolio_month,market_ret)
    
    portfolio_month.index=portfolio_month['year']*100+portfolio_month['month']
    diversification_ratio=asset_std.sum()/portfolio_month['ret'].std()    #多元化比
    risk_contribution=pd.concat([asset_ret,portfolio_month['ret']],axis=1)  #各资产对组合的风险贡献比
    risk_contribution=risk_contribution.cov()
    risk_contribution=risk_contribution.loc['ret'][asset.index]/risk_contribution['ret']['ret']
    
    indexes['diversification_ratio']=diversification_ratio
    indexes['risk_contribution']=risk_contribution
    indexes['month_end_money']=portfolio_month['money']
    indexes['month_ret']=portfolio_month['ret']
    corr=data_transform(pd.concat([data,market_ret]),column='ret').corr()
    
    if end_year-start_year>=5:
        rolling_3yret=rolling_ret(portfolio_month,num=3,period='year')
        rolling_5yret=rolling_ret(portfolio_month,num=5,period='year')
        indexes['rolling_3yret']=rolling_3yret
        indexes['rolling_5yret']=rolling_5yret
        
    asset_index=[]
    for code in asset.index:
        asset_index.append(calc_index(data.iloc[np.where(data['code']==code)],market_ret))
    
    asset_index=pd.concat(asset_index,axis=1).T
    
    return indexes,corr,asset_index,asset

def getdata(data,start_year,end_year,initial_amount,rebalancing,asset_weights):
    #检验数据
    # print('start_year:',type(start_year),str(start_year))
    # print('end_year:',type(end_year),str(end_year))
    # print('inittial_amount:',type(initial_amount),str(initial_amount))
    # print('rebalancing:',type(rebalancing))
    # print('data:',data.head())

    #-----------------------------------------------
    global asset

    asset=pd.DataFrame(pd.Series(asset_weights))
    asset.columns=['weight']
    asset['money']=initial_amount*asset['weight']
    asset['profit']=0
    asset.index=[str(i) for i in asset.index]

    data=pd.DataFrame(data)
    # print('data[date]:',type(data.iloc[0]['date']))
    data.sort_values(by=['code','date'],inplace=True)
    data['code']=[str(i) for i in data['code']]
    market_data=data.iloc[np.where(data['code']=='000001.SH')]
    asset_data=data.iloc[np.where(data['code']!='000001.SH')]
    temp=[value for key,value in asset_data.groupby(asset_data['code'])]
    r=[]
    for value in temp:
        value.fillna(method='ffill',inplace=True)
        value.fillna(method='bfill',inplace=True)
        r.append(value)
    asset_data=pd.concat(r)
    
    result=final_call(asset_data,market_data,start_year,end_year,initial_amount,rebalancing,0)
    
    return result

# data=pd.read_csv(r'C:\Users\Administrator\Desktop\0205\data_pd.txt',index_col=0)
# start_year=2015
# end_year=2017
# initial_amount=10000
# rebalancing='rebalance semi-annually'
# asset_weights={6:0.2,7:0.5,8:0.3}
#
# #print('data=',data.head())
# result=getdata(data,start_year,end_year,initial_amount,rebalancing,asset_weights)
# print(type(result))
# print(len(result))

#data=asset_data
