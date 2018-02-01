# !/usr/bin/python3
# -*-coding:utf-8-*-

"""
    Description :自定义异常工厂
    Author      :Machengxin
    Create      :2018/1/17-13:55
"""


class CreateConnException(Exception):
    def __init__(self):
        massage1 = str(self.massage)
        return repr(massage1)

    def __init__(self, massage):
        self.massage = massage


class ParameterException(Exception):    # 传入参数异常

    def __init__(self):
        massage = str(self.massage), str(self.defname), str(self.excutiontime)
        return repr(massage)

    def __init__(self, massage, defname, excutiontime):
        self.massage = massage
        self.defname = defname
        self.excutiontime = excutiontime


class CalculateException(Exception):    # 计算过程异常

    def __init__(self):
        massage1 = str(self.massage), str(self.defname)
        return repr(massage1)

    def __init__(self, massage, defname):
        self.massage = massage
        self.defname = defname

