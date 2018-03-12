# !/usr/bin/python3
# -*-coding:utf-8-*-

"""
    Description :日志测试类
    Author      :Machengxin
    Create      :2018/1/17-10:00
"""
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging

# 获取日志对象
logger = getlogging()

# 不同级别的输出日志信息
logger.debug('debug message')
logger.info('info message')
logger.warn('warn message')
logger.critical('critical message')
logger.error('error message')

