# !/usr/bin/python3
# -*-coding:utf-8-*-

"""
    Description :参数检验工具
    Author      :Machengxin
    Create      :2017/12/28-11:26
"""

import time

from recommend.com.yihuisoft.product.datautil.utilforexception.exceptionFactory import ParameterException


def ParameterInspection(a, b, c, d, class_name):    # 利息计算参数检验

    time_type = ['day', 'month', 'year']

    if type(a) != int and type(a) != float:
        execution_time = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        raise ParameterException("计算参数【初始值/终止值】有误", class_name, execution_time)
    if type(b) != int and type(b) != float:
        execution_time = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        raise ParameterException("计算参数【年化利率】有误", class_name, execution_time)
    if type(c) != int and type(c) != float:
        execution_time = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        raise ParameterException("计算参数【时间段】有误", class_name, execution_time)
    if d not in time_type:
        execution_time = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        raise ParameterException("时间段类型有有误", class_name, execution_time)

