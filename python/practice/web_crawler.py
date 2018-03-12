#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/22 13:46
# @Author  : xuq
# @Site    : 
# @File    : web_crawler.py
# @Software: PyCharm

import requests
from bs4 import BeautifulSoup


def vid_spider(max_page):
    page=1;
    while page < max_page:
        url= 'http://fund.eastmoney.com/fund.html#os_0;isall_0;ft_;pt_1';
        source_code=requests.get(url);
        plain_text=source_code.text;
        soup=BeautifulSoup(plain_text,"html.parser");
        for link in soup.select('tr[id^=tr]'):
            title=link.find('td',{'class':'dwjz black'})
            print(title.string);
        page+=1;


vid_spider(2);