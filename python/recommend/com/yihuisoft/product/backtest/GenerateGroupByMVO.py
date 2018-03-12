# -*- coding: UTF-8 -*-

import sys
import pandas as pd
import time;
from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging
from recommend.com.yihuisoft.util.calculateUtil.CalculateGroupParams import calculate_group_params
from recommend.com.yihuisoft.util.calculateUtil.CalculateEfficientFrontier import EffcientFrontier
'''
@Desc:通过MVO 产生组合数据
    @Author: yongquan.xiong
    @Date: 2018-02-10
    @Param: 
'''

logger = getlogging()
conn = getconn()
cr = conn.cursor()

codeList = []   # code
dataList = []   # data
dateList = []   # date


def generateGroupByMVO(params):
    try:
        # startDate='2015-01-01';
        # endDate='2017-12-31';
        params=params.split(",")
        startDate=str(params[0]);
        endDate=str(params[1]);
        ub=params[2];
        lb=params[3];

        sql_code = 'select distinct CODE from CATEGORY_DATA order by CODE';
        cr.execute(sql_code)
        rs_code=cr.fetchall()  #--一次返回所有结果集

        group_code=[];  #用于组合的code
        # print(rs_code)
        for i in range(0,len(rs_code)):
            group_code.append(str(rs_code[i][0]))
            tempCodeList = []
            tempDataList = []
            tempDateList = []
            sql_data = "select CODE,DATA,to_char(DAY,'yyyy-MM-dd') from CATEGORY_DATA where CODE  = '"+ str(rs_code[i][0])+"' AND to_char(DAY,'yyyy-MM-dd') >='"+ startDate+"' AND to_char(DAY,'yyyy-MM-dd') <='"+ endDate+"' ORDER BY DAY ASC"
            cr.execute(sql_data)
            rs=cr.fetchall()  #--一次返回所有结果集

            for i in range(0,len(rs)):
                codeList.append(rs[i][0])
                dataList.append(rs[i][1])
                dateList.append(rs[i][2])
        #封装数据
        data_dict = {'code' : codeList, 'close' : dataList,'date': dateList};
        #转换格式
        data_pd=pd.DataFrame(data_dict)
        #计算 MVO 所需参数
        group_params=calculate_group_params(data_pd);
        #组合中各产品预期收益
        yield_list=group_params[0].values;
        # 协方差矩阵
        cov_matrix=group_params[1];

        #调用 MVO 方法
        eff=EffcientFrontier()
        result=eff.efficientFrontier(yield_list,cov_matrix,50,ub,lb)
        # 组合风险
        group_risk=result[0];
        #组合收益
        group_yield=result[1];
        #各个组合中各产品对应权重
        group_weights=result[2];

        #对数据去重
        new_group_risk = []
        new_group_yield = []
        new_group_weights = []
        for index,risk in enumerate(group_risk):
            if risk not in new_group_risk:
                new_group_risk.append(risk)
                new_group_yield.append(group_yield[index])
                new_group_weights.append(group_weights[index])

    #对 数据进行 等分筛选
        data_len=len(new_group_risk)//5;
        #组合风险
        group_risk1=new_group_risk[:data_len]
        group_risk2=new_group_risk[data_len:(data_len*2)]
        group_risk3=new_group_risk[(data_len*2):(data_len*3)]
        group_risk4=new_group_risk[(data_len*3):(data_len*4)]
        group_risk5=new_group_risk[(data_len*4):]
        group_risk_temp=[];
        group_risk_temp.append(group_risk1);
        group_risk_temp.append(group_risk2);
        group_risk_temp.append(group_risk3);
        group_risk_temp.append(group_risk4);
        group_risk_temp.append(group_risk5);

        #组合收益
        group_yield1=new_group_yield[:data_len]
        group_yield2=new_group_yield[data_len:(data_len*2)]
        group_yield3=new_group_yield[(data_len*2):(data_len*3)]
        group_yield4=new_group_yield[(data_len*3):(data_len*4)]
        group_yield5=new_group_yield[(data_len*4):]
        group_yield_temp=[];
        group_yield_temp.append(group_yield1)
        group_yield_temp.append(group_yield2)
        group_yield_temp.append(group_yield3)
        group_yield_temp.append(group_yield4)
        group_yield_temp.append(group_yield5)

        #组合各产品权重
        group_weights1=new_group_weights[:data_len]
        group_weights2=new_group_weights[data_len:(data_len*2)]
        group_weights3=new_group_weights[(data_len*2):(data_len*3)]
        group_weights4=new_group_weights[(data_len*3):(data_len*4)]
        group_weights5=new_group_weights[(data_len*4):]
        group_weights_temp=[];
        group_weights_temp.append(group_weights1)
        group_weights_temp.append(group_weights2)
        group_weights_temp.append(group_weights3)
        group_weights_temp.append(group_weights4)
        group_weights_temp.append(group_weights5)

        result_group_risk=[];   #目标组合风险
        result_group_yield=[];  #目标组合收益
        result_group_weights=[];    #目标组合各产品权重
        #计算最大夏普比率
        for i in range(0,len(group_risk_temp)):
            group_risk_value=group_risk_temp[i];
            group_yield_value=group_yield_temp[i];
            group_weights_value=group_weights_temp[i];
            sharp_list=[];
            for j in range(0,len(group_risk_value)):
                sharp_list.append(group_yield_value[j]/group_risk_value[j])
            index=sharp_list.index(max(sharp_list)) # 返回最大值 所在位置
            #取最大夏普比率的组合数据
            result_group_risk.append(group_risk_value[index])
            result_group_yield.append(group_yield_value[index])
            result_group_weights.append(group_weights_value[index])

        # 当前时间
        create_date= time.strftime("%Y/%m/%d %H:%M:%S",time.localtime());

        #数据入库
        for i in range(0,len(result_group_risk)):
            result_group_risk_value=result_group_risk[i];
            result_group_yield_value=result_group_yield[i];
            # 组合收益、风险 等数据 插入 CATEGORY_GROUP_BASIC
            sql_insert = "insert into CATEGORY_GROUP_BASIC(ID,GROUP_ID,GROUP_YIELD,GROUP_RISK,CREATE_TIME,IS_VALID) values(SEQ_CATEGORY_GROUP_BASIC.Nextval,SEQ_CATEGORY_GROUP_BASIC.Nextval,'%s', '%s',to_date('%s','yyyy-MM-dd HH24:mi:ss'), 1)";
            cr.execute(sql_insert % (result_group_yield_value,result_group_risk_value,create_date))

            #取出刚插入记录的group_id
            sql_group_id="select MAX(GROUP_ID) from CATEGORY_GROUP_BASIC where IS_VALID=1"
            cr.execute(sql_group_id)
            rs=cr.fetchone()  #从数据库中取单个元祖，如果没有有效数据返回none

            if rs!=None:
                group_id=rs[0];
                group_id_list=[];   #组合 id
                create_date_list=[];    #创建时间
                args=[];
                for j in range(0,len(group_code)):
                    param=(group_id,group_code[j],result_group_weights[i][j],create_date);

                    args.append(param);

                # 组合各产品权重等数据 批量插入 CATEGORY_GROUP_DETAILS
                sql_insert_many = "insert into CATEGORY_GROUP_DETAILS values(SEQ_CATEGORY_GROUP_DETAILS.NEXTVAL,:1,:2,:3,to_date(:4,'yyyy-MM-dd HH24:mi:ss'))"

                if len(args) != 0 :
                    cr.executemany(sql_insert_many,args)  # 一次插入全部数据
                    logger.info("Table CATEGORY_GROUP_DETAILS insert rows = %d" % ( cr.rowcount))
                else:
                    logger.info("Table CATEGORY_GROUP_DETAILS No updated data!");
                    print ("ID of last record is ") #最后插入行的主键ID

        conn.commit()

        #关闭数据库资源
        closeall(conn,cr)

        return str(cr.rowcount);
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

result=generateGroupByMVO(params);
print(result)


# result=generateGroupByMVO(str('2015-03-01,2017-03-01,0.9,0.05'));
# print(result)
