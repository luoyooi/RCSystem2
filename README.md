# RCSystem2

## 项目概要
本项目是一个基于哈工大ltp4j的电影推荐系统，通过调用哈工大ltp4j中文分词接口，在后端实现对电影评论进行分析，在前端实现数据的可视化展示。

## 技术栈
### 前端
javascript、html、css、jQuery、echarts
### 后端
J2EE、MySQL5.7、ltp4j、maven3.6.3

## 评论数据获取
编写Python爬虫，从豆瓣爬取热门电影的基本信息和短评，主要用到urllib、bs4、pymysql、re、random、ssl、time、requests等库。