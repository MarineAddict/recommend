from datetime import datetime
from datetime import timedelta

from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall
from recommend.com.yihuisoft.util.CalculateData import calculateYieidRatio, calculateRisk

"""
Description : 获取数据源数据
Author : lixiaosong
Create : 14:59 2018/1/17
Params :  * @param null
"""
def getResource(cursor):
    sql = "SELECT pm.* FROM PM_NET_VALUE pm order by navlatestdate DESC"
    try:
        cursor.execute(sql)
        return cursor.fetchall()
    except Exception as e:
        print("sql语句 %s 执行出错 %s" % (sql, e))



"""
Description : 计算风险率 逻辑：先从参数配置表中取出对应的天数，然后取对应时间段的风险率进行计算得到风险率
Author : lixiaosong
Create : 15:00 2018/1/17
Params :  ctype 计算类型  daytime 要计算的当天的时间
"""
def caculate_riskRatio(ctype, daytime):
    # sql_select="select number from system_config where type="+type
    # number=cursor.execute(sql_select)  # 相隔时间段
    if ctype=='Day': # 相隔时间段应从数据库中取，暂时写死
        number=220
    elif ctype == 'Week':
        number=52
    elif ctype == 'Month':
        number=12
    elif ctype == 'Year':
        number=3
    else:
        number=0
    end = daytime.date()
    start =end-timedelta(days = number)   # 获取number天前的事件
    if ctype == 'Day':
        sql = "select YIELD_RATIO from PM_DATA_DAY where NAV_DATE <= to_date('%s','yyyy-mm-dd') AND rownum <= %d" % (end,number)
    elif ctype == 'Week':
        sql = "select YIELD_RATIO from PM_DATA_WEEK where NAV_DATE <= to_date('%s','yyyy-mm-dd') AND rownum <= %d" % (end,number)
    elif ctype == 'Month':
        sql = "select YIELD_RATIO from PM_DATA_MONTH where NAV_DATE <= to_date('%s','yyyy-mm-dd') AND rownum <= %d" % (end,number)
    elif ctype == 'Year':
        sql = "select YIELD_RATIO from PM_DATA_YEAR where NAV_DATE <= to_date('%s','yyyy-mm-dd') AND rownum <= %d" % (end,number)
    try:
        cursor.execute(sql)  # 收益率集合
        risk_list=cursor.fetchall()
        RISK_RATIO=calculateRisk(risk_list)
        return RISK_RATIO
    except Exception as e:
        print("sql语句 %s 执行出错 %s" % (sql,e))


"""
Description : 将风险率更新到数据库中
Author : lixiaosong
Create : 15:01 2018/1/17
Params : ctype 类型 id 唯一标示   risk_ratio 风险率
"""
def insert_riskRatio(ctype, id, risk_ratio):
    if ctype == 'Day':
        sql="update PM_DATA_DAY set RISK_RATIO= %lf where id = %d" % (risk_ratio,id)
    elif ctype == 'Week':
        sql="update PM_DATA_WEEK set RISK_RATIO= %lf where id = %d" % (risk_ratio,id)
    elif ctype == 'Month':
        sql="update PM_DATA_MONTH set RISK_RATIO= %lf where id = %d" % (risk_ratio,id)
    elif ctype =='Year':
        sql="update PM_DATA_YEAR set RISK_RATIO= %lf where id = %d" % (risk_ratio,id)
    else:
        sql=""
    try:
        cursor.execute(sql)
        conn.commit()
    except Exception as e:
        print("sql语句 %s 执行出错 " % (sql, e))


"""
Description : 插入收益率
Author : lixiaosong
Create : 15:02 2018/1/17
Params :  * @param null
"""
def insert_yieldRatio(ctype, CODE, YIELD_RATIO, RISK_RATIO, NAV_DATE, NAVADJ):
    if ctype == 'Day':
        sql_insert = "insert into PM_DATA_DAY(ID,CODE,YIELD_RATIO,RISK_RATIO,NAV_DATE,NAVADJ) VALUES(SEQ_PM_DATA_DAY.NEXTVAL, '%s', '%lf', '%lf', to_date('%s','yyyy-mm-dd'),'%lf')" % (CODE, YIELD_RATIO, RISK_RATIO, NAV_DATE, NAVADJ)
    elif ctype == 'Week':
        sql_insert = "insert into PM_DATA_WEEK(ID,CODE,YIELD_RATIO,RISK_RATIO,NAV_DATE,NAVADJ) VALUES(SEQ_PM_DATA_WEEK.NEXTVAL, '%s', '%lf', '%lf', to_date('%s','yyyy-mm-dd'),'%lf')" % (CODE, YIELD_RATIO, RISK_RATIO, NAV_DATE, NAVADJ)
    elif ctype == 'Month':
        sql_insert = "insert into PM_DATA_MONTH(ID,CODE,YIELD_RATIO,RISK_RATIO,NAV_DATE,NAVADJ) VALUES(SEQ_PM_DATA_MONTH.NEXTVAL, '%s', '%lf', '%lf', to_date('%s','yyyy-mm-dd'),'%lf')" % (CODE, YIELD_RATIO, RISK_RATIO, NAV_DATE, NAVADJ)
    elif ctype =='Year':
        sql_insert = "insert into PM_DATA_YEAR(ID,CODE,YIELD_RATIO,RISK_RATIO,NAV_DATE,NAVADJ) VALUES(SEQ_PM_DATA_YEAR.NEXTVAL, '%s', '%lf', '%lf', to_date('%s','yyyy-mm-dd'),'%lf')" % (CODE, YIELD_RATIO, RISK_RATIO, NAV_DATE, NAVADJ)
    else:
        sql_insert = ""
    try:
        cursor.execute(sql_insert)
        conn.commit()
    except Exception as e:
        print("sql语句 %s 执行出错 " % (sql_insert, e))

"""
Description : 挑选出周，月，年的数据
Author : lixiaosong
Create : 15:03 2018/1/17
Params :  ctype 类型 data 全部数据集合
"""
def typeData(ctype, data):
    month=0
    year=0
    listMonth=[]
    listYear=[]
    listWeek=[]
    for i in range(0,len(data)):
        pm=data[i]
        week=datetime.strptime(str(pm[2].date()),"%Y-%m-%d").weekday()

        if pm[2].date().month != month:
            month=pm[2].date().month
            listMonth.append(pm)  # 月的数据

        if pm[2].date().year != year:
            year=pm[2].date().year
            listYear.append(pm)   # 年的数据

        if week==4: # 如果是周五数据则添加
            countWeek=0
            listWeek.append(pm)
        else:
            if countWeek < 7:
                countWeek=countWeek+1
            else:
                countWeek=0
                listWeek.append(pm)  # 周的数据

    if ctype=='Day':
        return data
    elif ctype=='Week':
        return listWeek
    elif ctype=='Month':
        return listMonth
    elif ctype=='Year':
        return listYear
    else:
        return None


"""
Description : 计算收益率
Author : lixiaosong
Create : 15:04 2018/1/17
Params :  * @param null
"""
def insertYieldRatio(ctype,data):
    for i in range(0, len(data)):
        pm_day=data[i]
        pm_pre_day=data[i-1]
        # round(pm[1],2) 当天的复权单位净值  -- 价格
        # pm[2] 净值日期
        # round(pm_pre[1],2) 前一天的复权单位净值     --价格
        YIELD_RATIO_DAY = round(calculateYieidRatio(round(pm_pre_day[1],2),round(pm_day[1],2)),5)
        CODE_DAY = pm_day[0]
        RISK_RATIO_DAY = -1
        NAV_DATE_DAY = str(pm_day[2].date())
        NAVADJ_DAY = pm_day[1]
        # 插入天收益率到数据库
        insert_yieldRatio(ctype,CODE_DAY, YIELD_RATIO_DAY, RISK_RATIO_DAY, NAV_DATE_DAY, NAVADJ_DAY)

"""
Description : 计算风险率
Author : lixiaosong
Create : 15:04 2018/1/17
Params :  ctype 类型  data 数据集
"""
def insertRiskRatio(ctype,data):
    for i in range(0,len(data)):
        pm=data[i]
        NAV_DATE = str(pm[2].date())
        if ctype == 'Day':
            sql="select id from PM_DATA_DAY where CODE='%s' and NAV_DATE= to_date('%s','yyyy-mm-dd')" % (pm[0], NAV_DATE)
        elif ctype == 'Week':
            sql="select id from PM_DATA_WEEK where CODE='%s' and NAV_DATE= to_date('%s','yyyy-mm-dd')" % (pm[0], NAV_DATE)
        elif ctype == 'Month':
            sql="select id from PM_DATA_MONTH where CODE='%s' and NAV_DATE= to_date('%s','yyyy-mm-dd')" % (pm[0], NAV_DATE)
        elif ctype == 'Year':
            sql="select id from PM_DATA_YEAR where CODE='%s' and NAV_DATE= to_date('%s','yyyy-mm-dd')" % (pm[0], NAV_DATE)
        try:
            cursor.execute(sql)
            ids=cursor.fetchall()
            if len(ids) > 0:
                id=ids[0][0]  # 表中的唯一标识
                riskRatio=caculate_riskRatio(ctype,pm[2])   # 风险率
                insert_riskRatio(ctype,id,riskRatio)  # 将风险率更新到表中
        except Exception as e:
            print("sql语句：%s出错，错误信息：%s" % (sql, e))




"""
Description : 连接数据库
Author : lixiaosong
Create : 15:04 2018/1/17
Params :  * @param null
"""
conn = getconn()
cursor = conn.cursor()
data=getResource(cursor)


countWeek=0
weekData=typeData('Week', data)
monthData=typeData('Month', data)
yearData=typeData('Year', data)
insertYieldRatio('Day',data)
insertYieldRatio('Week',weekData)
insertYieldRatio('Month',monthData)
insertYieldRatio('Year',yearData)
insertRiskRatio('Day',data)
insertRiskRatio('Week',weekData)
insertRiskRatio('Month',monthData)
insertRiskRatio('Year',yearData)


conn.commit()
# 关闭连接
closeall(conn,cursor)



