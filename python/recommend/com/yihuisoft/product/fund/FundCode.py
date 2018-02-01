from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall
from recommend.com.yihuisoft.product.fund.FundCalculate import calculateFund

conn = getconn()
cr = conn.cursor()
sql="select DISTINCT code from fund_net_value"
cr.execute(sql)  #--执行sql 语句
rs=cr.fetchall()  #--一次返回所有结果集
for key,value in enumerate(rs):
    calculateFund('day',value[0],conn,cr)
    print("day affected rows = {}".format(cr.rowcount)) # 按天插入几条数据
    calculateFund('week',value[0],conn,cr)
    print("week affected rows = {}".format(cr.rowcount)) # 按周插入几条数据
    calculateFund('month',value[0],conn,cr)
    print("month affected rows = {}".format(cr.rowcount)) # 按月插入几条数据
    calculateFund('year',value[0],conn,cr)
    print("year affected rows = {}".format(cr.rowcount)) # 按年插入几条数据
closeall(conn,cr)
