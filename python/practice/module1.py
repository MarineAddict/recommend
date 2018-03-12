#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/6 16:45
# @Author  : xuq
# @Site    : 
# @File    : module1.py
# @Software: PyCharm


class thisis:
    '这是文档字符串'
    def __init__(self):
        self.name='sb';
        self.age=38;




    def print_it(name):
      print("hello",name);
      return;



data={
    "c":"ccccc",
    "b":"bbbb",
    "a":"aaaa",
    "d":"dddd"
}


print(min(zip(data.values(),data.keys())))