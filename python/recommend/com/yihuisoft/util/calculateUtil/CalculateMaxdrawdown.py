# -*- coding: UTF-8 -*-
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging

logger=getlogging();    #获取日志对象

'''
     @Desc: 计算最大回撤
     @Author: yongquan.xiong
     @Date: 2018-01-20
     @Param: data(list) 收益价格序列 （如 基金的复权单位净值 序列）
 '''
def calculateMaxdrawdown(data):
    if len(data)>1:     #   len(data) > 1  and data cannot have values <= 0
        if(data[-1]>=max(data[0:len(data)-1])):
            return 0;
        drawdown=[];
        data.reverse();    #倒序
        for index,item in enumerate(data):
            if index != len(data)-1 :
                if item <= 0:
                    logger.info('Data cannot have values <= 0');
                    return ;     # return None ;
                max_value=max(data[index+1:])
                temp=item/max_value - 1;
                drawdown.append(temp);
    else:
        logger.info('The length of data must > 1');
        return ;    # return None ;

    return round(min(min(drawdown),0),4);   # 最大回撤  (保留 4 位)


def calculateMaxdrawdownByMoney(data):
    if len(data)>1:     #   len(data) > 1  and data cannot have values <= 0
        if(data[-1]>=max(data[0:len(data)-1])):
            return 0;
        else:
            return round((data[-1]-max(data[0:len(data)-1]))/max(data[0:len(data)-1]),4);
    else:
        logger.info('The length of data must > 1');
        return ;    # return None ;


