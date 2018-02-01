# !/usr/bin/python3
# -*-coding:utf-8-*-

'''
    Description :存款计算
    Author      :Machengxin
    Create      :2017/12/27-15:44
'''


def getDemandDeposits(p, r, s, e):
    """
        Description :活期存款
        Create      :2017/12/27-15:48
        :param p:初始值
        :param r:年化利率
        :param s:开始时间
        :param e:结束时间
    """
    return float(p * (r/360) * (e-s))       # F=P*(R/360)*(E-S)


def getEntireDepositAndWithdrawal(p, t, r):
    """
        Description :整存整取
        Create      :2017/12/28-14:01
        :param p: 存款金额
        :param t: 存款时间(统一按月)
        :param r: 利率
    """
    return float(p * (t / 12) * r)          # 利率=存款金额*(整存整取时间/12)*利率


def getCompleteInFragmentedOut(p, r, ta, tt):
    """
        Description :DepositCalculation.py
        Create      :2017/12/28-14:12
        :param p: 初始金额
        :param r: 年利率
        :param ta:存期    （月）
        :param tt:支取频数(输入月)
    """
    every_out = p / tt      # 每期支取的钱数=初始金额/支取频次
    frequency = ta / tt     # 存期/支取频次

    for i in range(1, frequency):
        interest = float(interest + ((p - (i - 1) * every_out) * (r / tt)))

    return interest


def getFragmentedInCompleteOut(p, r, ta, tt):
    """
        Description :DepositCalculation.py
        Create      :2017/12/28-15:25
        :param p: 每期存入金额
        :param r: 年利率
        :param ta:存期    （月）
    """
    every_out = p 