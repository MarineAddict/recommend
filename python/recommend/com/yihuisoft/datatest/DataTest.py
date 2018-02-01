# !/usr/bin/python3
# -*-coding:utf-8-*-

"""
    Description :数据方法测试类
    Author      :Machengxin
    Create      :2018/1/16-16:52
"""
from recommend.com.yihuisoft.product.datautil.utilforconnection.connFactory import getconn, closeall

# 获取连接对象
conn = getconn()
# 获取返回参数接受对象
cursor = conn.cursor()

# 绑定参数
name_param = {'id': 3}
cursor.execute('select * from TEST_TABLE WHERE ID=:id', name_param)
row2 = cursor.fetchall()
print(row2)

# 关闭连接
closeall(conn, cursor)
