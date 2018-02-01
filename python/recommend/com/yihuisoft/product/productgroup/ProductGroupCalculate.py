from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging
from recommend.com.yihuisoft.util.calculateUtil.CalculateData import calculateRisk, calculateYieidRatio

logger = getlogging()
conn = getconn()
cr = conn.cursor()
'''
    计算组合的收益率和风险率
    :type day   计算组合的收益率和风险率
'''
def calculateProductGroup(product_group_id):
    args=[]
    yieid=[]
    product_groupData = []
    queryNum = 220
    startTime = ''
    sql="select * from (select to_char(nav_date,'yyyy-MM-dd') from product_group_data_day where product_group_id = '"+product_group_id+"' order by nav_date desc) where rownum=1"
    cr.execute(sql)  #--执行sql 语句
    rs=cr.fetchall()  #--一次返回所有结果集
    if rs :
        startTime = rs[0][0]
    else:
        sql="select to_char(CREATE_TIME,'yyyy-MM-dd') CREATE_TIME from product_group_basic where id ='"+product_group_id+"'"
        cr.execute(sql)  #--执行sql 语句
        rs=cr.fetchall()  #--一次返回所有结果集
        startTime = rs[0][0]
    sql="select PRODUCT_GROUP_ID,navadj,to_char(navlatestdate,'yyyy-MM-dd') from product_group_net_value where product_group_id = '"+product_group_id+"' order by navlatestdate"
    cr.execute(sql)  #--执行sql 语句
    rs=cr.fetchall()  #--一次返回所有结果集
    for i in range(0,len(rs)):
           product_groupData.append(rs[i])
    sql = "insert into product_group_data_day values(SEQ_product_group_DATA_DAY.NEXTVAL,:1,:2,:3,to_date(:4,'yyyy-MM-dd'),:5)"
    for i in range(0,len(product_groupData)):
        if i==0:
            yieidRatio = 0.0
            risk = 0.0
            yieid.append(0)
        elif len(yieid) == queryNum:
            del yieid[0]
            yieidRatio = calculateYieidRatio(product_groupData[i][1],product_groupData[i-1][1])
            yieid.append(yieidRatio)
            risk = calculateRisk(yieid)
        else:
            yieidRatio = calculateYieidRatio(product_groupData[i][1],product_groupData[i-1][1])
            yieid.append(yieidRatio)
            risk = calculateRisk(yieid)
        prar = (product_groupData[i][0],yieidRatio,risk,product_groupData[i][2],product_groupData[i][1])
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
            logger.info("table product_group_data_day insert rows = {}".format(cr.rowcount))
        else:
            logger.info("table product_group_net_value No updated data!")
    except Exception as e:
        logger.info("执行Oracle: %s 时出错：%s" % (sql, e))
    finally:
        conn.commit()
sql="select id from product_group_basic"
cr.execute(sql)  #--执行sql 语句
rs=cr.fetchall()  #--一次返回所有结果集
for key,value in enumerate(rs):
    logger.info("table product_group_data_day insert product_group_id = {}".format(value[0]))
    calculateProductGroup(str(value[0]))
closeall(conn,cr)