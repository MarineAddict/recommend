#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/6 16:40
# @Author  : xuq
# @Site    : 
# @File    : prac.py
# @Software: PyCharm


from practice.module1 import thisis
import urllib.request


def download_img(url):
    urllib.request.urlretrieve(url,'dow.jpg');

download_img('https://www.sololearn.com/Icons/Courses/1073.png')










