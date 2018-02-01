# !/usr/bin/python3
# -*-coding:utf-8-*-

import cx_Oracle

from recommend.com.yihuisoft.product.datautil.utilforexception.exceptionFactory import CreateConnException
from recommend.com.yihuisoft.product.datautil.utilforlogging.loggerFactory import getlogging

"""
    Description :数据库连接工厂
    Author      :Machengxin
    Create      :2018/1/17-13:32
"""
logger = getlogging()


def getconn():
    """
    创建数据源连接对象
    :return: 连接对象
    """
    try:
        conn = cx_Oracle.connect('uwp_recommend/123456@192.168.1.92/orcl')
        logger.info("Create connection object Success!")
    except Exception:
        logger.error("Create connection object Success!")
        raise CreateConnException("Create connection object Error")
    return conn


def getaimconn():
    """
    创建数据源连接对象
    :return: 连接对象
    """
    try:
        conn = cx_Oracle.connect('uwp_cfgh_root/123456@192.168.1.202/fdms2')
        logger.info("Create connection object Success!")
    except Exception:
        logger.error("Create connection object Success!")
        raise CreateConnException("Create connection object Error")
    return conn


def closeconn(conn):
    """
    关闭连接对象
    :param conn:连接对象 或 参数接受对象
    :return:
    """
    try:
        conn.close()
        logger.info("Close connection object Success!")
    except Exception:
        logger.error("Close connection object Error!")
        raise CreateConnException("Close connection object Error")


def closeall(conn, cursor):
    """
    同时关闭两个连接对象
    :param conn:连接对象
    :param cursor:参数接受对象
    :return:
    """
    try:
        cursor.close()              # 关闭接收返回值对象
        conn.close()                # 关闭数据源连接
        logger.info("Close connection object Success!")
    except Exception:
        logger.error("Close connection object Error!")
        raise CreateConnException("Close connection-conn object Error")



