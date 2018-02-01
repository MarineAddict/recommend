# !/usr/bin/python3
# -*-coding:utf-8-*-

"""
    Description :计算测试类
    Author      :Machengxin
    Create      :2018/1/17-17:59
"""


# 计算Future  Value(单利)给定初始值(PV),年化利率(R),时间段(T),时间段类型(TP = year/mouth/day)。
from recommend.com.yihuisoft.product.calculation.interestCalculate import getSimpleInterestForInitialValue, \
    getSimpleInterestForTerminationValue, getCompoundInterestForInitialValue, getCompundInteresForTerminationValue

a = getSimpleInterestForInitialValue(10, 10, 10, 'year')
print("a:")
print(a)

# 计算Present Value(单利)给定终止值(FV),年化利率(R),时间段(T),时间段类型(TP = year/mouth/day)。
b = getSimpleInterestForTerminationValue(10, 10, 10, 'month')
print("b:")
print(b)

# 计算Future Value(复利)给定初始值(PV),利率(R), 时间段(T),时间段类型
c = getCompoundInterestForInitialValue(10, 10, 10, 'year')
print("c:")
print(c)

# 计算Present Value(复利)给定终止值(FV),利率(R), 时间段(T),时间段类型
d = getCompundInteresForTerminationValue(10, 10, 10, 'year')
d = getCompundInteresForTerminationValue(10, 10, 10, 'year')
d = getCompundInteresForTerminationValue(10, 10, 10, 'year')
print("d:")
print(d)