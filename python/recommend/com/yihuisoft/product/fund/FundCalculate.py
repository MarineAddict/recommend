from datetime import datetime, timedelta

from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging
from recommend.com.yihuisoft.util.calculateUtil.CalculateData import calculateRisk, calculateYieidRatio

logger = getlogging()
'''
    计算基金的日周月年的收益率和风险率
    :type day   计算基金日的收益率和风险率
    :type weed  计算基金周的收益率和风险率
    :type month 计算基金月的收益率和风险率
    :type year  计算基金年的收益率和风险率
'''
def calculateFund(type,code,conn,cr):
    args=[]
    yieid=[]
    fundData = []
    friTiem = '';
    num = 0
    queryNum = 0
    startTime = ''
    sql="select code,navadj,to_char(navlatestdate,'yyyy-MM-dd') from fund_net_value where code = '"+code+"' order by navlatestdate"
    cr.execute(sql)  #--执行sql 语句
    rs=cr.fetchall()  #--一次返回所有结果集
    if type == 'day' :
        queryNum = 220
        for i in range(0,len(rs)):
            fundData.append(rs[i])
        sql="select * from (select to_char(NAV_DATE,'yyyy-MM-dd') from fund_data_day where code = '"+code+"' order by NAV_DATE desc) where rownum=1"
        cr.execute(sql)  #--执行sql 语句
        rs=cr.fetchall()  #--一次返回所有结果集
        if rs :
            startTime = rs[0][0]
        sql = "insert into fund_data_day values(SEQ_FUND_DATA_DAY.NEXTVAL,:1,:2,:3,to_date(:4,'yyyy-MM-dd'),:5)"
    elif type == 'week' :
        queryNum = 52
        for i in range(0,len(rs)):
            week = datetime.strptime(rs[i][2],"%Y-%m-%d").weekday()
            if num == 0 :
                if week == 4 :
                    num = 1
                    fundData.append(rs[i])
                    friTiem = datetime.strptime(rs[i][2],"%Y-%m-%d") + timedelta(days=7)
            else:
                if datetime.strptime(rs[i][2],"%Y-%m-%d")>=friTiem :
                    if week == 4 :
                        fundData.append(rs[i])
                        friTiem = friTiem + timedelta(days=7)
                    else:
                        rs[i-1] = (rs[i-1][0],rs[i-1][1],friTiem.strftime("%Y-%m-%d"))
                        fundData.append(rs[i-1])
                        friTiem = friTiem + timedelta(days=7)
        sql="select * from (select to_char(NAV_DATE,'yyyy-MM-dd') from fund_data_week where code = '"+code+"' order by NAV_DATE desc) where rownum=1"
        cr.execute(sql)  #--执行sql 语句
        rs=cr.fetchall()  #--一次返回所有结果集
        if rs :
            startTime = rs[0][0]
        sql = "insert into fund_data_week values(SEQ_FUND_DATA_WEEK.NEXTVAL,:1,:2,:3,to_date(:4,'yyyy-MM-dd'),:5)"
    elif type == 'month' :
        queryNum = 12
        for i in range(0,len(rs)) :
            if i == 0 :
                friTiem = datetime.strptime(rs[i][2],"%Y-%m-%d").month
            if datetime.strptime(rs[i][2],"%Y-%m-%d").month != friTiem :
                fundData.append(rs[i-1])
                friTiem = datetime.strptime(rs[i][2],"%Y-%m-%d").month
        sql="select * from (select to_char(NAV_DATE,'yyyy-MM-dd') from fund_data_month where code = '"+code+"' order by NAV_DATE desc) where rownum=1"
        cr.execute(sql)  #--执行sql 语句
        rs=cr.fetchall()  #--一次返回所有结果集
        if rs :
            startTime = rs[0][0]
        sql = "insert into fund_data_month values(SEQ_FUND_DATA_MONTH.NEXTVAL,:1,:2,:3,to_date(:4,'yyyy-MM-dd'),:5)"
    elif type == 'year':
        queryNum = 10
        for i in range(0,len(rs)) :
            if i == 0 :
                friTiem = datetime.strptime(rs[i][2],"%Y-%m-%d").year
            if datetime.strptime(rs[i][2],"%Y-%m-%d").year != friTiem :
                fundData.append(rs[i-1])
                friTiem = datetime.strptime(rs[i][2],"%Y-%m-%d").year
        sql="select * from (select to_char(NAV_DATE,'yyyy-MM-dd') from fund_data_year where code = '"+code+"' order by NAV_DATE desc) where rownum=1"
        cr.execute(sql)  #--执行sql 语句
        rs=cr.fetchall()  #--一次返回所有结果集
        if rs :
            startTime = rs[0][0]
        sql = "insert into fund_data_year values(SEQ_FUND_DATA_YEAR.NEXTVAL,:1,:2,:3,to_date(:4,'yyyy-MM-dd'),:5)"
    for i in range(0,len(fundData)):
        if i==0:
            yieidRatio = 0.0
            risk = 0.0
            yieid.append(0)
        elif len(yieid) == queryNum:
            del yieid[0]
            yieidRatio = calculateYieidRatio(fundData[i][1],fundData[i-1][1])
            yieid.append(yieidRatio)
            risk = calculateRisk(yieid)
        else:
            yieidRatio = calculateYieidRatio(fundData[i][1],fundData[i-1][1])
            yieid.append(yieidRatio)
            risk = calculateRisk(yieid)
        prar = (fundData[i][0],yieidRatio,risk,fundData[i][2],fundData[i][1])
        args.append(prar)
    try:
        for i in range(0,len(args)):
            if args[0][3] == startTime:
                del args[0]
                break
            else:
                del args[0]
        if len(args) != 0 :
            cr.executemany(sql,args)  # 一次插入全部数据
            logger.info("table fund_data_%s insert rows = %d" % (type, cr.rowcount))
        else:
            logger.info("table fund_data_%s No updated data!" % (type))
    except Exception as e:
        logger.info("执行Oracle: %s 时出错：%s" % (sql, e))
    finally:
        conn.commit()