# !/usr/bin/python3
# -*-coding:utf-8-*-

"""
    Description :资产计算
    Author      :Machengxin
    Create      :2018/1/17-17:52
"""
import sys

from recommend.com.yihuisoft.product.calculation.parameterUtil import ParameterInspection
from recommend.com.yihuisoft.product.datautil.utilforexception.exceptionFactory import CalculateException


def getSimpleInterestForInitialValue(pv, t, r, tp):
    """
        Description :给定初始值计算单利
        Create      :2017/12/27-14:20
        :param pv   :初始值
        :param t    :年化利率
        :param r    :时间段
        :param tp   :时间段类型
    """
    class_name = sys._getframe().f_code.co_name     # 获取当前类
    ParameterInspection(pv, t, r, tp, class_name)   # 参数检验

    try:
        if tp == "year":
            return float(pv * (1 + r * t))          # 年化利率,时间段为年  : FV=PV+T*R*PV=PV(1+R*T)
        elif tp == "month":
            return float(pv * (1 + r * (t/12)))     # 年化利率,时间段为月  : FV=PV+T*R*PV=PV(1+R*(T/12))
        elif tp == "day":
            return float(pv * (1 + r * (t/360)))    # 年化利率,时间段为日  : FV=PV+T*R*PV=PV(1+R*(T/360))
        else:
            return 0.00
    except Exception:
        raise CalculateException("计算过程错误", class_name)


def getSimpleInterestForTerminationValue(fv, r, t, tp):
    """
        Description :给定终止值计算单利
        Create      :2017/12/27-14:21
        :param fv   :终止值
        :param r    :年化利率
        :param t    :时间段
        :param tp   :时间段类型
    """
    class_name = sys._getframe().f_code.co_name     # 获取当前类
    ParameterInspection(fv, t, r, tp, class_name)   # 参数检验

    try:
        if tp == "year":
            return float(fv / (1 + r * t))           # 年化利率,时间段为年  : PV=FV/(1+R*T)
        elif tp == "month":
            return float(fv / (1 + r * (t/12)))      # 年化利率,时间段为月  : PV=FV/(1+R*(T/12))
        elif tp == "day":
            return float(fv / (1 + r * (t/360)))     # 年化利率,时间段为日  : PV=FV/ (1+R*(T/360))
        else:
            return 0.00
    except Exception:
        raise CalculateException("计算过程错误", class_name)


def getCompoundInterestForInitialValue(pv, r, t, tp):
    """
        Description :给定初始值计算复利
        Create      :2017/12/27-14:54
        :param pv   :初始值
        :param t    :年化利率
        :param r    :时间段
        :param tp   :时间段类型
    """
    class_name = sys._getframe().f_code.co_name     # 获取当前类
    ParameterInspection(pv, t, r, tp, class_name)   # 参数检验

    try:
        if tp == "year":
            return float(pv * ((1 + r) ** t))           # 年化利率,时间段为年  : FV=PV((1+R)^T)
        elif tp == "month":
            return float(0)                             # 年化利率,时间段为月  : 暂不进行计算实现
        elif tp == "day":
            return float(0)                             # 年化利率,时间段为日  : 暂不进行计算实现
        else:
            return 0.00
    except Exception:
        raise CalculateException("计算过程错误", class_name)


def getCompundInteresForTerminationValue(fv, r, t, tp):
    """
        Description :给定终止值计算复利
        Create      :2017/12/27-15:04
        :param fv   :终止值
        :param r    :年化利率
        :param t    :时间段
        :param tp   :时间段类型
    """
    class_name = sys._getframe().f_code.co_name         # 获取当前类
    ParameterInspection(fv, t, r, tp, class_name)       # 参数检验

    try:
        if tp == "year":
            return float(fv / (1 + r * t))              # 年化利率,时间段为年  : PV=FV/((1+R)^T)
        elif tp == "month":
            return float(0)                             # 年化利率,时间段为月  : 暂不进行计算实现
        elif tp == "day":
            return float(0)                             # 年化利率,时间段为日  : 暂不进行计算实现
        else:
            return 0.00
    except Exception:
        raise CalculateException("计算过程错误", class_name)

