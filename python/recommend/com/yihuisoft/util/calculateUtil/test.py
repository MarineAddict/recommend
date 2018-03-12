# -*- coding: UTF-8 -*-
from recommend.com.yihuisoft.util.calculateUtil.CalculateEfficientFrontier import EffcientFrontier
from recommend.com.yihuisoft.util.calculateUtil.CalculateGroupData import calculateGroupRiskAndYield
from recommend.com.yihuisoft.util.calculateUtil.CalculateGroupParams import calculateGroupCov
from recommend.com.yihuisoft.util.calculateUtil.CalculateGroupParams import calculateGeometricMean
from recommend.com.yihuisoft.util.calculateUtil.CalculatePortvrisk import calculatePortvrisk
from recommend.com.yihuisoft.util.calculateUtil.CalculateMaxdrawdown import calculateMaxdrawdown
from recommend.com.yihuisoft.util.calculateUtil.CalculateOptimum import CalculateOptimum

# 测试 有效前沿线
# eff=EffcientFrontier()
# result=eff.efficientFrontier([ 0.184834,0.041218,0.023062,0.463256,0.784507,0.031819,0.220497],
#                       [[8.833939e-03,7.651558e-07,-0.002226,0.003731,-0.019216,-0.000003,-0.001368],[7.651558e-07,1.419560e-06,-0.000008,-0.000013,-0.000007,0.000005, 0.000013],[-2.225520e-03,-7.980835e-06,0.006014,-0.006066,0.006555,0.000035,0.001051],
#                        [3.730521e-03,-1.263160e-05,-0.006066,0.270428,-0.007033,0.000257,0.003435],[-1.921642e-02,-6.514642e-06,0.006555,-0.007033,0.532037,-0.000350,0.023181],[-3.045455e-06,4.834472e-06,0.000035,0.000257,-0.000350,0.000172,0.000063],[-1.367698e-03,1.278405e-05,0.001051,0.003435,0.023181,0.000063,0.008574]],100)
#
eff=EffcientFrontier()
result=eff.efficientFrontier([ 0.0054, 0.0531,0.0779,0.0934,0.0130],
                             [[0.0569,  0.0092,  0.0039,  0.0070,  0.0022],[0.0092,  0.0380,  0.0035,  0.0197,  0.0028],[0.0039,  0.0035,  0.0997,  0.0100,  0.0070],
                              [0.0070,  0.0197,  0.0100,  0.0461,  0.0050],[0.0022,  0.0028,  0.0070,  0.0050,  0.0573]],5)

print('组合风险率：',result[0]);
print('组合收益率：',result[1]);
print('产品权重：',result[2]);

#测试 计算产品组合的 收益率 以及 风险率（波动率）
# result=calculateGroupRiskAndYield([0.20789, 0.27423, 0.11609, 0.16215, 0.23964],
#                                   [ 0.0054, 0.0531,0.0779,0.0934,0.0130],
#                                   [[0.0569,  0.0092,  0.0039,  0.0070,  0.0022],[0.0092,  0.0380,  0.0035,  0.0197,  0.0028],[0.0039,  0.0035,  0.0997,  0.0100,  0.0070],
#                                    [0.0070,  0.0197,  0.0100,  0.0461,  0.0050],[0.0022,  0.0028,  0.0070,  0.0050,  0.0573]]);
# print(result)



#测试 返回 协方差矩阵 方法
result=calculateGroupCov([1,0,1,1],['a','b','c','d'],{'a':[2,4,6],'b':[],'c':[3,5,7],'d':[2,3,4]});
print(result)
#测试 计算几何平均收益率
# result=calculateGeometricMean([1,-0.5,-0.5]);
# print(result);
#测试 组合最大亏损
# result=calculatePortvrisk(0.073,0.0527,0.975,10000)
# print(result)
#
# #测试 最大回撤
# result=calculateMaxdrawdown([1,2,3,1,2,6,1,9,1]);
# print(result);

#测试 最优解（求最小风险率）
# calculateOptimum =CalculateOptimum();
# result=calculateOptimum.calculateOptimumByYield([ 0.0054, 0.0531,0.0779,0.0934,0.0130],
#                                                 [[0.0569,  0.0092,  0.0039,  0.0070,  0.0022],[0.0092,  0.0380,  0.0035,  0.0197,  0.0028],[0.0039,  0.0035,  0.0997,  0.0100,  0.0070],
#                                                  [0.0070,  0.0197,  0.0100,  0.0461,  0.0050],[0.0022,  0.0028,  0.0070,  0.0050,  0.0573]],0.062);   #  0.05581206030150753
# print(result);
#
# # 测试 最优解（求最大收益率）
# calculateOptimum =CalculateOptimum();
# result=calculateOptimum.calculateOptimumByRisk([ 0.0054, 0.0531,0.0779,0.0934,0.0130],
#                                                 [[0.0569,  0.0092,  0.0039,  0.0070,  0.0022],[0.0092,  0.0380,  0.0035,  0.0197,  0.0028],[0.0039,  0.0035,  0.0997,  0.0100,  0.0070],
#                                                  [0.0070,  0.0197,  0.0100,  0.0461,  0.0050],[0.0022,  0.0028,  0.0070,  0.0050,  0.0573]],0.1401);  #0.1340974687763909
# print(result);


