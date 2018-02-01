import time
import datetime

"""
     Description : 按zhou计算收益率和风险率
     Author : tangjian
     Create : 15:02 2018/1/18
     Params :  expYieldMax预期最大收益率, expYieldMin预期最小收益率, realYield 真实收益率,
              expYieldMaxAvg同风险等级下最大收益率的均值, expYieldMinAvg同风险等级下最小收益率的均值
     return : 返回理财产品的收益率和风险率         
"""
def calWeekIncomeAndRisk(expYieldMax, expYieldMin, realYield, expYieldMaxAvg, expYieldMinAvg):
    expRatioDay = 0.0
    riskYield = 0.0
    if expYieldMax != 0.0 and expYieldMax != "" and expYieldMin != 0.0 and expYieldMin != "":
        expRatioDay = (expYieldMax + expYieldMin) / (2 * 52)
    elif expYieldMax != 0.0 and expYieldMax != "":
        expRatioDay = expYieldMax / 52
    elif realYield != 0.0 and realYield != "":
        expRatioDay = realYield / 52
    elif expYieldMaxAvg != 0.0 and expYieldMaxAvg != "" and expYieldMinAvg != 0.0 and expYieldMinAvg != "":
        expRatioDay = (expYieldMaxAvg + expYieldMinAvg) / (2 * 52)
    else:
        expRatioDay = expYieldMaxAvg / 52
    return expRatioDay, riskYield

"""
     Description : 按月计算收益率和风险率
     Author : tangjian
     Create : 15:02 2018/1/18
     Params :  expYieldMax预期最大收益率, expYieldMin预期最小收益率, realYield 真实收益率,
              expYieldMaxAvg同风险等级下最大收益率的均值, expYieldMinAvg同风险等级下最小收益率的均值
     return : 返回理财产品的收益率和风险率         
"""
def calMonthIncomeAndRisk(expYieldMax, expYieldMin, realYield, expYieldMaxAvg, expYieldMinAvg):
    expRatioDay = 0.0
    riskYield = 0.0
    if expYieldMax != 0.0 and expYieldMax != "" and expYieldMin != 0.0 and expYieldMin != "":
        expRatioDay = (expYieldMax + expYieldMin) / (2 * 12)
    elif expYieldMax != 0.0 and expYieldMax != "":
        expRatioDay = expYieldMax / 12
    elif realYield != 0.0 and realYield != "":
        expRatioDay = realYield / 12
    elif expYieldMaxAvg != 0.0 and expYieldMaxAvg != "" and expYieldMinAvg != 0.0 and expYieldMinAvg != "":
        expRatioDay = (expYieldMaxAvg + expYieldMinAvg) / (2 * 12)
    else:
        expRatioDay = expYieldMaxAvg / 12
    return expRatioDay, riskYield

"""
     Description : 按天计算收益率和风险率
     Author : tangjian
     Create : 15:02 2018/1/18
     Params :  expYieldMax预期最大收益率, expYieldMin预期最小收益率, realYield 真实收益率,
              expYieldMaxAvg同风险等级下最大收益率的均值, expYieldMinAvg同风险等级下最小收益率的均值
     return : 返回理财产品的收益率和风险率         
"""
def calDayIncomeAndRisk(expYieldMax, expYieldMin, realYield, expYieldMaxAvg, expYieldMinAvg):
    expRatioDay = 0.0
    riskYield = 0.0
    if expYieldMax != 0.0 and expYieldMax != "" and expYieldMin != 0.0 and expYieldMin != "":
        expRatioDay = (expYieldMax + expYieldMin) / (2 * 365)
    elif expYieldMax != 0.0 and expYieldMax != "":
        expRatioDay = expYieldMax / 365
    elif realYield != 0.0 and realYield != "":
        expRatioDay = realYield / 365
    elif expYieldMaxAvg != 0.0 and expYieldMaxAvg != "" and expYieldMinAvg != 0.0 and expYieldMinAvg != "":
        expRatioDay = (expYieldMaxAvg + expYieldMinAvg) / (2 * 365)
    else:
        expRatioDay = expYieldMaxAvg / 365
    return expRatioDay, riskYield

"""
     Description : 按年计算收益率和风险率
     Author : tangjian
     Create : 15:02 2018/1/18
     Params :  expYieldMax预期最大收益率, expYieldMin预期最小收益率, realYield 真实收益率,
              expYieldMaxAvg同风险等级下最大收益率的均值, expYieldMinAvg同风险等级下最小收益率的均值
     return : 返回理财产品的收益率和风险率         
"""
def calYearIncomeAndRisk(expYieldMax, expYieldMin, realYield, expYieldMaxAvg, expYieldMinAvg):
    expRatioDay = 0.0
    riskYield = 0.0
    if expYieldMax != 0.0 and expYieldMax != "" and expYieldMin != 0.0 and expYieldMin != "":
        expRatioDay = (expYieldMax + expYieldMin) / 2
    elif expYieldMax != 0.0 and expYieldMax != "":
        expRatioDay = expYieldMax
    elif realYield != 0.0 and realYield != "":
        expRatioDay = realYield
    elif expYieldMaxAvg != 0.0 and expYieldMaxAvg != "" and expYieldMinAvg != 0.0 and expYieldMinAvg != "":
        expRatioDay = (expYieldMaxAvg + expYieldMinAvg) / 2
    else:
        expRatioDay = expYieldMaxAvg
    return expRatioDay, riskYield

"""
    Description : 计算两个时间的相差天数
    Author : tangjian
    Create : 15:02 2018/1/18
    Params :  startDate起始日期，endDate终止日期
"""
def calTimeBwteen(startDate, endDate):
    startDate = time.strptime(startDate, "%Y-%m-%d %H:%M:%S")
    endDate = time.strptime(endDate, "%Y-%m-%d %H:%M:%S")
    date1 = datetime.datetime(startDate[0], startDate[1], startDate[2])
    date2 = datetime.datetime(endDate[0], endDate[1], endDate[2])
    betweenDate = (date2 - date1).days + 1
    return betweenDate


if __name__ == '__main__':
    print(calTimeBwteen("2017-03-09","2019-03-09"))









