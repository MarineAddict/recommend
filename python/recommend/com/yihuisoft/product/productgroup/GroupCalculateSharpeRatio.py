import datetime
import sys

from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging
from recommend.com.yihuisoft.util.calculateUtil.CalculateGroupData import calculateGroupRiskAndYield
from recommend.com.yihuisoft.util.calculateUtil.CalculateGroupParams import calculateGroupCov, calculateGeometricMean

logger = getlogging()
conn = getconn()
cr = conn.cursor()
weights = []
codeList = []
typeList = []
covList = []
yieldListMap = {}
yieldList = []
exception_yieldList=[]
'''
获取组合产品权重序列
'''
def getGroupInfo(productId) :
    sql = 'select PRODUCT_CODE,PROPORTION,PRODUCT_TYPE from PRODUCT_GROUP_DETAILS where PRODUCT_GROUP_ID = '+productId;
    cr.execute(sql)
    rs=cr.fetchall()  #--一次返回所有结果集
    for i in range(0,len(rs)):
        codeList.append(rs[i][0])
        weights.append(rs[i][1])
        typeList.append(rs[i][2])
'''
获取外来参数
'''
a = []
for i in range(1,len(sys.argv)):
    a.append(sys.argv[i])
getGroupInfo(a[0])
'''
处理组合内各产品收益率序列
'''
def getProductYieldRatio():
    num = 0
    now = datetime.datetime.now()
    if int(now.month)>10 and int(now.day)>10:
        time = str(int(now.year) -1) + '-' + str(now.month) + '-' + str(now.day)
    elif int(now.month)<10 and int(now.day)>10:
        time = str(int(now.year) -1) + '-0' + str(now.month) + '-' + str(now.day)
    elif int(now.month)>10 and int(now.day)<10:
        time = str(int(now.year) -1) + '-' + str(now.month) + '-0' + str(now.day)
    elif int(now.month)<10 and int(now.day)<10:
        time = str(int(now.year) -1) + '-0' + str(now.month) + '-' + str(now.day)
    for index,items in enumerate(typeList):
        if items == '1' :#  1代表理财产品
            covList.append(0)
            yieldListMap[codeList[index]] = []
            sql = "SELECT EXP_YIELD_MAX FROM FINANCE_BASIC_DATA  WHERE CODE ='"+ codeList[index]+"'"
            cr.execute(sql)
            rs=cr.fetchall()  #--一次返回所有结果集
            exception_yieldList.append(round(rs[0][0],4))
        elif items == '2':  #   2代表基金
            sql = "select calculate_covariance from fund_basic_data where code = '"+ codeList[index]+"'"
            cr.execute(sql)
            rs=cr.fetchall()  #--一次返回所有结果集
            covList.append(rs[0][0])
            if rs[0][0]== 1:
                sql = "SELECT yield_ratio FROM fund_data_day where CODE='"+ codeList[index]+"' AND to_char(NAV_DATE,'yyyy-MM-dd') >='"+ time+"'  ORDER BY NAV_DATE ASC"
                cr.execute(sql)
                rs=cr.fetchall()  #--一次返回所有结果集
                for i in range(0,len(rs)):
                    yieldList.append(rs[i][0])
                yieldListMap[codeList[index]] = yieldList
                num = len(yieldList)
                exception_yieldList.append(calculateGeometricMean(yieldList))
            else:
                yieldListMap[codeList[index]] = []
        elif items == '3':  #3代表贵金属
            covList.append(1)
            sql = "SELECT yield_ratio FROM pm_data_day where CODE='"+ codeList[index]+"' AND to_char(NAV_DATE,'yyyy-MM-dd') >='"+ time+"'  ORDER BY NAV_DATE ASC"
            cr.execute(sql)
            rs=cr.fetchall()  #--一次返回所有结果集
            for i in range(0,len(rs)):
                yieldList.append(rs[i][0])
            yieldListMap[codeList[index]] = yieldList
            exception_yieldList.append(calculateGeometricMean(yieldList))
        elif items == '4':  #4代表存单
            covList.append(0)
            yieldListMap[codeList[index]] = []
            sql = "select INTEREST from deposit_basic_data where code ='"+ codeList[index]+"'"
            cr.execute(sql)
            rs=cr.fetchall()  #--一次返回所有结果集
            exception_yieldList.append(round(rs[0][0],4))
        yieldList = []
    for key,value in yieldListMap.items():
        if len(value)<num and len(value) != 0:
            num = len(value)
    for key,value in yieldListMap.items():
        if len(value)>num :
            value = value[len(value)-num:]
            yieldListMap[key] = value
getProductYieldRatio()

cov = calculateGroupCov(covList,codeList,yieldListMap)
sharpRatio = calculateGroupRiskAndYield(weights,exception_yieldList,cov)
sql = "update product_group_basic set expected_annualized_return = "+str(sharpRatio[1])+",EXPECTED_RISK_RATIO = "+str(sharpRatio[0])+" where id = "+str(a[0])
logger.info(sql)
cr.execute(sql)
conn.commit()
closeall(conn,cr)
print(str(sharpRatio[0])+","+str(sharpRatio[1]))