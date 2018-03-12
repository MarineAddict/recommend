import recommend.com.yihuisoft.product.finance.financeService as financeService
from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall
import datetime
import numpy as np

""" 
     Description : 按天计算收益率和风险率
     Author : tangjian
     Create : 17:02 2018/1/18
     Params : financeCode理财产品代码
     return : 返回理财产品的天收益率和风险率         
"""
def calculateIncomeAndRisk(financeCode):
    # 从基本表查询理财产品的收益率按照规则进行计算收益率
    sql = "SELECT  EXP_YIELD_MAX, EXP_YIELD_MIN, REAL_YIELD,(SELECT AVG(EXP_YIELD_MAX) FROM FINANCE_BASIC_DATA WHERE " \
              "RISK_LEVEL IN (SELECT RISK_LEVEL FROM FINANCE_BASIC_DATA WHERE CODE = "+financeCode+")),(SELECT AVG(EXP_YIELD_MIN) FROM " \
              "FINANCE_BASIC_DATA WHERE RISK_LEVEL IN (SELECT RISK_LEVEL FROM FINANCE_BASIC_DATA WHERE CODE = "+financeCode+"))FROM " \
              "FINANCE_BASIC_DATA WHERE  CODE = "+financeCode+""
    conn = getconn()
    cursor = conn.cursor()
    cursor.execute(sql)
    result = cursor.fetchall()
    closeall(conn, cursor)
    for i in range(0, len(result)):
        ro = result[i]
    expYieldMax, expYieldMin, realYield, expYieldMaxAvg, expYieldMinAvg  = ro[0], ro[1], ro[2], ro[3], ro[4]
    incomeYield, riskYield = financeService.calDayIncomeAndRisk(expYieldMax, expYieldMin, realYield, expYieldMaxAvg, expYieldMinAvg)
    return incomeYield,riskYield

"""
     Description : 通过产品代码计算产品到期日和起息日的相差天数
     Author : tangjian
     Create : 17:02 2018/1/18
     Params : financeCode理财产品代码
     return : 相差天数         
"""
def getProductDate(financeCode):
    sql = "select VALUE_DATE ,EXPIRY_DATE  from finance_basic_data WHERE CODE = "+financeCode+""
    conn = getconn()
    cursor = conn.cursor()
    cursor.execute(sql)
    result = cursor.fetchall()
    closeall(conn, cursor)
    for i in range(0, len(result)):
        ro = result[i]
    valueDate, expipyDate = ro[0], ro[1]
    days = financeService.calTimeBwteen(str(valueDate), str(expipyDate))
    return days,valueDate,expipyDate

"""
     Description : 向理财表中插入数据（第一种处理理财产品数据的方式）
                    每天的净值默认为1直到产品到期时数据是对应周期的收益率
     Author : tangjian
     Create : 17:02 2018/1/18
     Params : financeCode理财产品代码
"""
def insertFinanceDataDay1(financeCode):
    days, valueDate, expipyDate = getProductDate(financeCode)
    incomeYield, riskYield = calculateIncomeAndRisk(financeCode)
    navadj = 1 + incomeYield * days
    financeCode = str(financeCode)
    startDate = str(valueDate.date())#起息日
    expipyDate = str(valueDate.date()) #到息日
    print(startDate)
    conn = getconn()
    cursor = conn.cursor()
    for i in range(1,days):
     # sql = "INSERT INTO FINANCE_DATA_DAY (ID,FINANCE_CODE,YIELD_RATIO,RISK_RATIO,NAV_DATE,NAVADJ)VALUES (SEQ_FINANCE_DATA_DAY.NEXTVAL,financeCode,incomeYield,riskYield,to_date('%s','yyyy-mm-dd'),navadj) % (valueDate)"
        sql = "INSERT INTO FINANCE_DATA_DAY (ID,CODE,NAV_DATE)VALUES (SEQ_FINANCE_DATA_DAY.NEXTVAL,"+financeCode+",to_date('%s','yyyy-mm-dd'))" %(startDate)
        cursor.execute(sql)
        conn.commit()
        startDate = valueDate + datetime.timedelta(days=i)
        startDate = str(startDate.date())
        print(startDate)
    sql = "INSERT INTO FINANCE_DATA_MONTH (ID,CODE,NAV_DATE,YIELD_RATIO,RISK_RATIO,NAVADJ)VALUES " \
          "(SEQ_FINANCE_DATA_DAY.NEXTVAL,"+financeCode+",to_date('%s','yyyy-mm-dd'),)" %(expipyDate)
    cursor.execute(sql)
    conn.commit()
    closeall(conn, cursor)

"""
     Description : 向理财表中插入数据（第二种处理理财产品数据的方式）
                    每天的净值默认为(1+incomeYield)的i次方(i是存的天数)
                    每天的收益率固定为incomeYield
                    风险率默认为0
     Author : tangjian
     Create : 17:02 2018/1/18
     Params : financeCode理财产品代码
"""
def insertFinanceDataDay2(financeCode):
    # 理财产品的周期时长，起息日，到息日
    days, valueDate, expipyDate = getProductDate(financeCode)
    # 理财产品每天的收益率和风险率
    incomeYield, riskYield = calculateIncomeAndRisk(financeCode)
    incomeYield = pow(1+incomeYield, 1 /365)
    yieldRatio = incomeYield - 1
    print(incomeYield)
    financeCode = str(financeCode)
    # 起息日
    startDate = str(valueDate.date())
    print(startDate)
    conn = getconn()
    cursor = conn.cursor()
    for i in range(1, days+1):
     # sql = "INSERT INTO FINANCE_DATA_DAY (ID,FINANCE_CODE,YIELD_RATIO,RISK_RATIO,NAV_DATE,NAVADJ)VALUES (SEQ_FINANCE_DATA_DAY.NEXTVAL,financeCode,incomeYield,riskYield,to_date('%s','yyyy-mm-dd'),navadj) % (valueDate)"
        # 单位净值
        navadj = np.power(incomeYield ,i)
        print(navadj)
        sql = "INSERT INTO FINANCE_DATA_DAY (ID,CODE,NAV_DATE,YIELD_RATIO,RISK_RATIO,NAVADJ)" \
              "VALUES (SEQ_FINANCE_DATA_DAY.NEXTVAL,"+financeCode+",to_date('%s','yyyy-mm-dd')，'%lf', '%lf','%lf')"\
              %(startDate,yieldRatio,riskYield,navadj)
        cursor.execute(sql)
        conn.commit()
        startDate = valueDate + datetime.timedelta(days=i)
        startDate = str(startDate.date())
        print(startDate)
    closeall(conn, cursor)

"""
     Description : 查询理财产品基本表中所有产品代码
     Author : tangjian
     Create : 17:02 2018/1/18
     Params : financeCode理财产品代码
"""
def getFinanceCode():
    sql = "select  CODE  from finance_basic_data "
    conn = getconn()
    cursor = conn.cursor()
    cursor.execute(sql)
    result = cursor.fetchall()
    closeall(conn, cursor)
    return result

"""
     Description : 查询理财每天净值表中所有产品代码
     Author : tangjian
     Create : 17:02 2018/1/18
     Params : financeCode理财产品代码
"""
def getFinanceCode1():
    sql = "SELECT distinct CODE FROM FINANCE_DATA_DAY "
    conn = getconn()
    cursor = conn.cursor()
    cursor.execute(sql)
    result1 = cursor.fetchall()
    closeall(conn, cursor)
    return result1

"""
     Description : 遍历理财产品代码并插入(插入第一种方式对应表中)
     Author : tangjian
     Create : 17:02 2018/1/18
"""
def insertFinanceCode0():
    result = getFinanceCode()
    result1 = getFinanceCode1()
    result1 = str(result1)
    for i in range(0,len(result)):
        financeCode = str(result[i])
        # 如果基本表中的代码在要插入表中存在则无需插入
        if(not(result1.__contains__(financeCode))):
            financeCode = str(result[i]).replace(",)","")
            financeCode = financeCode.replace("(","")
            print(financeCode)
            insertFinanceDataDay1(financeCode)

"""
     Description : 遍历理财产品代码并插入(插入第二种方式对应表中)
     Author : tangjian
     Create : 17:02 2018/1/18
"""
def insertFinanceCode():
    result = getFinanceCode()
    result1 = getFinanceCode1()
    result1 = str(result1)
    for i in range(0,len(result)):
        # 如果基本表中的代码在要插入表中存在则无需插入
        financeCode = str(result[i])
        if (not(result1.__contains__(financeCode))):
            financeCode = str(result[i]).replace(",)","")
            financeCode = financeCode.replace("(","")
            print(financeCode)
            insertFinanceDataDay2(financeCode)

# 测试方法用的
if __name__ == '__main__':
    insertFinanceCode()




