# !/usr/bin/python3
# -*-coding:utf-8-*-

import logging
import logging.config

"""
    Description :日志创建工厂
    Author      :Machengxin
    Create      :2018/1/17-11:35
"""

# logging.config.fileConfig("logger.conf")
# logger = logging.getLogger("example01")


def getlogging():
    '''''
        创建日志对象
    '''
    logging.basicConfig(level=logging.INFO,
                        format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                        datefmt='%a, %d %b %Y %H:%M:%S',
                        filename='../../datalog/logger.log',
                        filemode='a')

    console = logging.StreamHandler()
    console.setLevel(logging.DEBUG)
    formatter = logging.Formatter('%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s')
    console.setFormatter(formatter)
    logging.getLogger('').addHandler(console)
    return logging


