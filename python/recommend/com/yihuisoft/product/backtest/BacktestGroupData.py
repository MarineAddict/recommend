# -*- coding: UTF-8 -*-
# import datetime
import sys
import pandas as pd
from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging
from recommend.com.yihuisoft.util.backtestUtil.backtest import getdata
from recommend.com.yihuisoft.util.calculateUtil.CalculateMaxdrawdown import calculateMaxdrawdownByMoney


logger = getlogging()
conn = getconn()
cr = conn.cursor()

codeList = []   # code
dataList = []   # data
dateList = []   # date

indexes ={}     # 指标数据

'''
获取组合数据
'''
def getGroupMetrics(param):
    try:
        params=param.split(",")
        groupId=params[0];      #组合Id
        startDate=params[1];     #开始时间
        endDate=params[2];       #结束时间
        initial_amount=int(params[3]);       #初始投资金额
        rebalance=params[4];         #再平衡
        benchmark=params[5];    #基准code
        groupType=params[6];    #组合类别

        # groupId='71';
        # startDate='2015-01-01';
        # endDate='2017-12-31';
        # initial_amount=10000;
        # rebalance='rebalance_semi-annually';

        startYear=int(startDate.split("-")[0]);
        endYear=int(endDate.split("-")[0]);

        #查询基准数据
        rs_benchmark=[];
        if(benchmark !='null'):
            sql_benchmark = "select CODE,DATA,to_char(DAY,'yyyyMM') from CATEGORY_DATA_SH where CODE = '" + benchmark + "' AND DATA IS NOT NULL AND to_char(DAY,'yyyy-MM-dd') >='"+ startDate+"' AND to_char(DAY,'yyyy-MM-dd') <='"+ endDate+"' ORDER BY DAY DESC"
            cr.execute(sql_benchmark)
            rs_benchmark=cr.fetchall()  #--一次返回所有结果集result
        benchmark_dataList=[]   #基准数据集合
        benchmark_dateList=[]   #基准日期集合

        benchmark_money=[]   #基准资金集合(元素类型为 str)
        benchmark_money_temp=[]   #基准资金集合（元素类型为 number）
        benchmark_yield=[]   #基准收益率集合
        benchmark_date=[]   #基准日期集合
        benchmark_drawdown=[]   #基准回撤集合

        if(len(rs_benchmark) >0):
            benchmark_dataList.append(rs_benchmark[0][1]);
            benchmark_dateList.append(rs_benchmark[0][2]);
            for i in range(0,len(rs_benchmark)):
                #取出基准每月底收盘价
                if(benchmark_dateList[-1] != rs_benchmark[i][2]):
                    benchmark_dataList.append(rs_benchmark[i][1])
                    benchmark_dateList.append(rs_benchmark[i][2])
            benchmark_dataList.reverse();   #按时间升序
            benchmark_dateList.reverse();
            #计算基准收益率以及资产
            for i in range(0,len(benchmark_dataList)-1):
                temp=(benchmark_dataList[i+1]-benchmark_dataList[i])/benchmark_dataList[i];
                temp=round(temp,4);
                benchmark_yield.append(str(temp));
                benchmark_date.append(str(benchmark_dateList[i+1]))
                if(i==0):
                    benchmark_money.append(str(initial_amount*(1+temp)));
                    benchmark_money_temp.append(initial_amount*(1+temp));
                    benchmark_drawdown.append(str(0));
                else:
                    benchmark_money.append(str(round(float(benchmark_money[-1])*(1+temp),2)))
                    benchmark_money_temp.append(round(float(benchmark_money_temp[-1])*(1+temp),2))
                    # benchmark_drawdown_params=benchmark_money_temp.copy();
                    benchmark_drawdown.append(str(calculateMaxdrawdownByMoney(benchmark_money_temp.copy())))
        #根据组合类别查询组合code,weight 等数据
        if(groupType=='mvo'):     #mvo
            sql_code = 'select cgd.CODE,cgd.WEIGHT,cb.NAME from CATEGORY_GROUP_DETAILS cgd' \
                       ' left join CATEGORY_BID cb on cgd.code=cb.code ' \
                       ' where cgd.WEIGHT > 0 and cgd.GROUP_ID = '+groupId;
        elif(groupType=='financial'):     #金融产品
            sql_code = 'select bgd.CODE,bgd.WEIGHT,fbd.NAME from BACKTEST_GROUP_DETAILS bgd' \
                       ' left join FUND_BASIC_DATA fbd on bgd.code=fbd.code ' \
                       ' where bgd.WEIGHT > 0 and bgd.GROUP_ID = '+groupId;
        elif(groupType=='asset'):     # 资产小类
            sql_code = 'select bgd.CODE,bgd.WEIGHT,cb.NAME from BACKTEST_GROUP_DETAILS bgd' \
                       ' left join CATEGORY_BID cb on bgd.code=cb.code ' \
                       ' where bgd.WEIGHT > 0 and bgd.GROUP_ID = '+groupId;

        #执行sql
        cr.execute(sql_code)
        rs_code=cr.fetchall()  #--一次返回所有结果集result

        group_code=[];  #用到组合code
        weights={};  #组合中产品 weight
        code_name=[];  #产品名称 name
        for i in range(0,len(rs_code)):
            weights[rs_code[i][0]]=rs_code[i][1]
            group_code.append(str(rs_code[i][0]))
            code_name.append(str(rs_code[i][2]))
            #根据组合类别查询组合产品data等数据
            if(groupType=='asset' or groupType=='mvo'):     #资产小类 or  MVO
                sql_data = "select CODE,DATA,to_char(DAY,'yyyy-MM-dd') from CATEGORY_DATA where CODE" \
                           " = '"+ str(rs_code[i][0])+"' AND to_char(DAY,'yyyy-MM-dd') >='"+ startDate+"'" \
                           " AND to_char(DAY,'yyyy-MM-dd') <='"+ endDate+"' ORDER BY DAY ASC";
            elif(groupType=='financial'):     #金融产品
                sql_data = "select CODE,NAVADJ,to_char(NAVLATESTDATE,'yyyy-MM-dd') from FUND_NET_VALUE1 where CODE" \
                           "  = '"+ str(rs_code[i][0])+"' AND to_char(NAVLATESTDATE,'yyyy-MM-dd') >='"+ startDate+"' " \
                           " AND to_char(NAVLATESTDATE,'yyyy-MM-dd') <='"+ endDate+"' ORDER BY NAVLATESTDATE ASC";
            #查询组合中产品的数据
            cr.execute(sql_data)
            rs=cr.fetchall()  #--一次返回所有结果集

            for i in range(0,len(rs)):
                codeList.append(rs[i][0])
                dataList.append(rs[i][1])
                dateList.append(rs[i][2])
        group_code.append('000001.SH');     #指数 code
        group_code=','.join(group_code)

        code_name.append('上证指数');
        code_name=','.join(code_name)

        #获取上证指数  数据
        sql_sh = "select CODE,DATA,to_char(DAY,'yyyy-MM-dd') from CATEGORY_DATA_SH where CODE  = '000001.SH'  AND to_char(DAY,'yyyy-MM-dd') >='"+ startDate+"' AND to_char(DAY,'yyyy-MM-dd') <='"+ endDate+"' ORDER BY DAY ASC"
        cr.execute(sql_sh)
        rs_sh=cr.fetchall()  #--一次返回所有结果集
        shCodeList = []
        shDataList = []
        shDateList = []
        for i in range(0,len(rs_sh)):
            codeList.append(rs_sh[i][0])
            dataList.append(rs_sh[i][1])
            dateList.append(rs_sh[i][2])

        #关闭数据库资源
        closeall(conn,cr)

        # 封装成 dict 格式
        data_dict = {'code' : codeList, 'close' : dataList,'date': dateList}
        #转换格式
        data_pd=pd.DataFrame(data_dict)
        # weights01={3: 0.25, 4: 0.25, 5: 0.25,7: 0.25}
        #计算回归测试指标数据
        result=getdata(data_pd,startYear,endYear,initial_amount,rebalance,weights)
        #取出返回值
        indexes_temp=result[0]
        corr_temp=result[1]
        asset_index_temp=result[2]
        asset_temp=result[3]

        '''
        处理第 2 个返回值 corr_temp
        '''
        # 一 ：相关系数矩阵
        corr=[];
        for indexs in corr_temp.index:
            row = corr_temp.loc[indexs].values[0:]
            row_temp=[]
            for x in row:
                row_temp.append(round(x,4))
            corr.append(row_temp)


        '''
       处理第 3 个返回值 asset_index_temp( 含 year_ret,此处暂未用到)
    
       '''
        # 标题
        asset_index_title=asset_index_temp.columns.values.tolist()[0:-1];
        asset_index_title.reverse();
        asset_index_title=','.join(asset_index_title);
        # 数据
        asset_index_value=[];
        for indexs in asset_index_temp.index:
            row = asset_index_temp.loc[indexs].values[0:-1]
            row_temp=[]
            for x in row:
                row_temp.append(str(x))
            row_temp.reverse();
            row_temp_value=','.join(row_temp);
            asset_index_value.append((row_temp_value+'#'))  #添加 分隔符 #
        asset_index_value=','.join(asset_index_value);

        asset_index={'title':asset_index_title,'values':asset_index_value}


        '''
        处理第 4 个返回值 asset_temp
        '''
        # 标题
        asset_temp_title=asset_temp.columns.values.tolist()[0:];
        asset_temp_title=','.join(asset_temp_title);
        # 数据
        asset_temp_value=[];
        for indexs in asset_temp.index:
            row = asset_temp.loc[indexs].values[0:]
            row_temp=[]
            for x in row:
                row_temp.append(str(x))
            row_temp_value=','.join(row_temp);
            asset_temp_value.append((row_temp_value+'#'))
        asset_temp_value=','.join(asset_temp_value);
        asset={'title':asset_temp_title,'values':asset_temp_value}


        '''
            处理第 1 个返回值 indexes_temp
       '''
        # 回撤序列
        drawdowns=indexes_temp['drawdowns'];
        drawdowns_temp=[]
        for x in drawdowns:
            drawdowns_temp.append(str(round(x,4)))
        drawdowns=','.join(drawdowns_temp);

        # risk_contribution(各产品风险贡献)
        risk_contribution=indexes_temp['risk_contribution'].values;
        risk_contribution_temp=[];
        for x in risk_contribution:
            risk_contribution_temp.append(str(round(x,4)))
        risk_contribution=','.join(risk_contribution_temp);

        # month_end_money 每月资产金额
        #每月资产金额数值
        month_end_money_value=indexes_temp['month_end_money'].values;
        month_end_money_value_temp=[];
        for x in month_end_money_value:
            month_end_money_value_temp.append(str(round(x,4)))
        # 转 str
        month_end_money_value_str = ','.join(month_end_money_value_temp);

        #每月资产金额对应日期
        month_end_money_date=indexes_temp['month_end_money'].index;
        month_end_money_date_temp=[];
        for x in month_end_money_date:
            month_end_money_date_temp.append(str(x))
        # 转 str
        month_end_money_date_str = ','.join(month_end_money_date_temp);

        month_end_money={'month_end_money_date':month_end_money_date_str,'month_end_money_value':month_end_money_value_str}

        # month_ret 每月资产收益
        #每月资产收益数值
        month_ret_value=indexes_temp['month_ret'].values;
        month_ret_value_temp=[];
        for x in month_ret_value:
            month_ret_value_temp.append(str(round(x,4)))
        # 转 str
        month_ret_value_str = ','.join(month_ret_value_temp);

        #每月资产收益对应日期
        month_ret_date=indexes_temp['month_ret'].index;
        month_ret_date_temp=[];
        for x in month_ret_date:
            month_ret_date_temp.append(str(x))
        # 转 str
        month_ret_date_str = ','.join(month_ret_date_temp);

        month_ret={'month_ret_date':month_ret_date_str,'month_ret_value':month_ret_value_str}

        #基准数据 与 组合数据 对齐,对应日期无数据的用 null 填充
        benchmark_money_tar=[];     #基准资金集合
        benchmark_yield_tar=[]   #基准收益率集合
        benchmark_drawdown_tar=[]   #基准回撤集合
        for index,date in enumerate(month_end_money_date_temp):
            if(date in benchmark_date):
                benchmark_money_tar.append(benchmark_money[benchmark_date.index(date)])
                benchmark_yield_tar.append(benchmark_yield[benchmark_date.index(date)])
                benchmark_drawdown_tar.append(benchmark_drawdown[benchmark_date.index(date)])
            else:
                benchmark_money_tar.append('null')
                benchmark_yield_tar.append('null')
                benchmark_drawdown_tar.append('null')
        benchmark_money_str=','.join(benchmark_money_tar)   #基准资金集合
        benchmark_yield_str=','.join(benchmark_yield_tar)   #基准收益率集合
        benchmark_drawdown_str=','.join(benchmark_drawdown_tar)  #基准回撤集合

        #  指标赋值
        indexes={'BestYear':indexes_temp['Best Year'],'CAGR':indexes_temp['CAGR'],'MarketCorrelation': indexes_temp['Market Correlation'],'MaxDrawdown':indexes_temp['Max.Drawdown'],
                 'SharpeRatio':indexes_temp['Sharpe Ratio'],'SortinoRatio':indexes_temp['Sortino Ratio'],'Stdev':indexes_temp['Stdev'],'WorstYear':indexes_temp['Worst Year'],
                 'Alpha(annualized)':indexes_temp['Alpha(annualized)'],'Beta':indexes_temp['Beta'],'CompoundReturn(annualized)':indexes_temp['Compound Return(annualized)'],
                 'CompoundReturn(monthly)':indexes_temp['Compound Return(monthly)'],'ConditionalValue':indexes_temp['Conditional Value-at-Risk(95.0%)'],
                 'DeltaNormalValue':indexes_temp['Delta Normal Value-at-Risk(95.0%)'],'DownsideDeviation(monthly)':indexes_temp['Downside Deviation(monthly)'],
                 'ExcessKurtosis':indexes_temp['Excess Kurtosis'],'Financial_Balance':indexes_temp['Financial_Balance'],'Gain_LossRatio':indexes_temp['Gain/Loss Ratio'],
                 'HistoricalValue':indexes_temp['Historical Value-at-Risk(95.0%)'],'MeanReturn(annualized)':indexes_temp['Mean Return(annualized)'],
                 'MeanReturn(monthly)':indexes_temp['Mean Return(monthly)'],'PositivePeriods':indexes_temp['Positive Periods'],'R2':indexes_temp['R^2'],'Skewness':indexes_temp['Skewness'],
                 'TreynorRatio':indexes_temp['Treynor Ratio(%)'],'Volatility(monthly)':indexes_temp['Volatility (monthly)'],'diversification_ratio':indexes_temp['diversification_ratio'],
                 'group_code':group_code,
                 'code_name':code_name,
                 'drawdowns':drawdowns,'risk_contribution':risk_contribution,
                 'month_end_money':month_end_money,
                 'month_ret':month_ret,
                 'corr':corr,
                 'asset_index':asset_index,
                 'asset':asset,
                 'benchmark_money':benchmark_money_str,
                 'benchmark_yield':benchmark_yield_str,
                 'benchmark_drawdown':benchmark_drawdown_str
                  }
        return indexes;
        # return 'haha';
    except:
        return 'Failed to call python';

'''
    获取外来参数
'''
params = []
for i in range(1,len(sys.argv)):
    params.append(sys.argv[i])
params=','.join(params);
# print(params)
# param='71,2015-01-01,2017-12-31,10000,rebalance_semi-annually,000001.SH';
# print(param)
results=getGroupMetrics(params);
print(results)


# result=getGroupMetrics('551,2016-03-01,2018-03-15,1000,rebalance_monthly,000001.SH,mvo');
# print(result)
