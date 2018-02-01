# -*- coding: UTF-8 -*-
from scipy import special
import math

'''
     @Desc: 计算组合最大可能亏损
     @Author: yongquan.xiong
     @Date: 2018-01-20
     @Param: port_return 组合预期收益率
             port_risk  组合预期风险率（波动率）
             confidence_interval 置信区间
             port_value 组合的总价值（投资金额）
 '''
def calculatePortvrisk(port_return,port_risk,confidence_interval,port_value):
    try:
        risk_threshold=1-confidence_interval;    # 损失概率= 1 - 置信区间
        temp0 =special.erfcinv(2*risk_threshold);
        temp1 = -math.sqrt(2) * temp0;
        temp2 = port_risk * temp1 + port_return;
        max_loss=(-min(temp2,0) * port_value);  #最大亏损
    except:
        return  #   None
    return round(max_loss,2);   #   (保留 2 位)

