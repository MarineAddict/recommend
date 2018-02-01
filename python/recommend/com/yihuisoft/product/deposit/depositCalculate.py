# !/usr/bin/python3
# -*-coding:utf-8-*-

from datetime import timedelta, datetime
import time

from dateutil.relativedelta import relativedelta
from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getaimconn, closeall, getconn
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging

"""
    Description :存单数据计算
    Author      :Machengxin
    Create      :2018/1/18-10:06
"""
logger = getlogging()


def SynchronousData():
    """
    数据库存单数据同步
    :return:
    """
    aimconn = getaimconn()
    aimcursor = aimconn.cursor()
    datalist = GetAllDataFromCFGH(aimcursor)    # 调用方法获取数据
    closeall(aimconn, aimcursor)

    conn = getconn()
    cursor = conn.cursor()
    SetAllDataToRECOM(cursor, datalist)         # 调用方法插入数据
    conn.commit()                               # 提交处理
    closeall(conn, cursor)


def CalculateData():
    """
    存单数据进行处理
    :return:
    """
    conn = getconn()
    cursor = conn.cursor()
    datalist = GetAllDataFromRECOM(cursor)      # 获取数据
    SetAllDataToTable(cursor, datalist)         # 处理并导入数据
    conn.commit()
    closeall(conn, cursor)


###################################################################################


def GetAllDataFromCFGH(cursor):
    """
    获取产品数据：从财富E站通库
    :param cursor: 连接对象
    :return:  从财富E展通获得的存单产品数据
    """
    sql_select = "SELECT deposit.code,deposit.intstartday,deposit.intendday,deposit.interest,deposit.theterm  FROM DEPOSIT_CALCULATE deposit"
    # 数据格式 ： 120170010100030	2017/1/1	2017/1/31	1.53	1
    try:
        cursor.execute(sql_select)
        logger.debug("get CFGH data Succcess")
        return cursor.fetchall()
    except Exception as e:
        logger.error("get CFGH data Error")


def GetAllDataFromRECOM(cursor):
    """
    获取产品数据：从推荐引擎库
    :param cursor: 连接对象
    :return: 本地所有产品的（产品代码，收益率）
    """
    sql_select = "SELECT D.CODE,D.INTEREST,D.INTSTARTDAY,D.INTENDDAY FROM DEPOSIT_BASIC_DATA D"
    # 数据格式 ：  120170010100030   1.53000   2017/1/1    2017/2/28
    try:
        cursor.execute(sql_select)
        logger.debug("get localData data Success")
        return cursor.fetchall()
    except Exception as e:
        logger.error("get localData data Error")


########################################################################################################################


def SetAllDataToRECOM(cursor, datalist):
    """
    插入产品数据：产品推荐引擎库
    :param cursor: 连接对象
    :param datalist: 从财富E站通获得产品数据
    :return:
    """
    for i in range(0, len(datalist)):
        # 源 ：120170010100030	    2017/1/1	2017/1/31	                        1.53	   1
        # 目 ：120170010100030	    2017/1/1	2017/1/31	2017/1/1	2017/2/28	1.53000	   1	   1

        deopsitcode = datalist[i][0]
        # 发售开始日期
        startdata = str(datalist[i][1])
        # 发售结束日期
        enddata = str(datalist[i][2])
        # 计算结束日期
        outenddata = str(datalist[i][2] + relativedelta(months=+ datalist[i][4]))

        sql_insert = "INSERT INTO DEPOSIT_BASIC_DATA " \
                     "VALUES(SEQ_DEPOSIT_BASIC_DATA.NEXTVAL,'%s',to_date('%s','yyyy-mm-dd,hh24:mi:ss'),to_date('%s','yyyy-mm-dd,hh24:mi:ss')," \
                     "to_date('%s','yyyy-mm-dd,hh24:mi:ss'),to_date('%s','yyyy-mm-dd,hh24:mi:ss'),'%lf','%lf','%lf')" \
                     % (deopsitcode, startdata, enddata, startdata, outenddata, datalist[i][3], datalist[i][4], 1.0)
        try:
            cursor.execute(sql_insert)
            logger.debug("set recommend localData Success")
        except Exception as e:
            logger.error("set recommend localData Error")


def SetAllDataToTable(cursor, datalist):
    """
    将存单本地基本表中数据处理并插入对应数据表中
    :param cursor: 连接对象
    :param datalist: 本地存单基本数据集合
    :return:
    """
    for i in range(0, len(datalist)):

        starttime = time.time()
        onedata = datalist[i]

        code = onedata[0]
        navadj = onedata[1]
        start_date = onedata[2]
        end_date = onedata[3]
        definenavadj = 1
        yieldratio = 0.0

        SetOneLineDataToTable(cursor, code, start_date, end_date, navadj, yieldratio, definenavadj)

        endtimd = time.time()
        spendTime = endtimd - starttime
        logger.info("Insert Deposit Data :" + code + "Spend Time :" + str(spendTime))


def SetOneLineDataToTable(cursor, code, start_date, end_date, navadj, yieldratio, definenavadj):
    """
    将产品信息处理插入
    :param cursor:
    :param code:
    :param start_date:
    :param end_date:
    :param navadj:
    :param yieldratio:
    :param definenavadj:
    :return:
    """
    day_count = (end_date - start_date).days + 1
    for single_date in [d for d in (start_date + timedelta(n) for n in range(day_count)) if d <= end_date]:
        date = str(single_date)
        week = single_date.weekday()

        if single_date == end_date:
            definenavadj = navadj
            yieldratio = float(navadj * 1)

        # 插入日的表
        sql_insert = "INSERT INTO DEPOSIT_DATA_DAY (ID,CODE,YIELD_RATIO,NAV_DATE,NAVADJ) " \
                     "VALUES(SEQ_DEPOSIT_DATA_DAY.NEXTVAL,'%s','%lf',to_date('%s','yyyy-mm-dd,hh24:mi:ss'),'%lf')" \
                     % (code, yieldratio, date, definenavadj)
        try:
            cursor.execute(sql_insert)
            logger.debug("set recommend day/week/month/yearData Success")
        except Exception as e:
            logger.error("set recommend day/week/month/yearData Error")

        # 插入周的表
        if week == 4 or single_date == end_date:
            sql_insert = "INSERT INTO DEPOSIT_DATA_WEEK (ID,CODE,YIELD_RATIO,NAV_DATE,NAVADJ) " \
                         "VALUES(SEQ_DEPOSIT_DATA_DAY.NEXTVAL,'%s','%lf',to_date('%s','yyyy-mm-dd,hh24:mi:ss'),'%lf')" \
                         % (code, yieldratio, date, definenavadj)
            try:
                cursor.execute(sql_insert)
                logger.debug("set recommend day/week/month/yearData Success")
            except Exception as e:
                logger.error("set recommend day/week/month/yearData Error")

        # 插入月的表

####################################################################################


# 导入处理基本数据
# SynchronousData()

# 基本数据处理分流
# CalculateData()
