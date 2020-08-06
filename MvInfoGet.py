from urllib import request
from bs4 import BeautifulSoup
import pymysql
import re
import random
import ssl
import time
import requests

# 使用https访问时用ssl的该方法
context = ssl._create_unverified_context()

# headers = [
#         "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 OPR/26.0.1656.60",
#         "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10",
#         "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)",
#         "Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11",
#         "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;360SE)"
#     ]
headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0'}

# # 随机useragent
# def randomOpener():
#     '''代理ip'''
#     proxy = [{'http': '27.152.90.4:9999'}]
#     headers = [
#         "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 OPR/26.0.1656.60",
#         "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10",
#         "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)",
#         "Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11",
#         "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;360SE)"
#     ]
#     RP = random.choice(proxy)
#     RH = random.choice(headers)
#     # 创建ProxyHandler
#     proxy_support = request.ProxyHandler(RP)
#     # 创建opener
#     opener = request.build_opener(proxy_support)
#     # 添加User-Agent
#     opener.addheaders = [("User-Agent", RH)]
#
#     return opener



# 获取所有电影对应的详情页网址
def getUrls(url):
    req = request.Request(url=url, headers=headers)
    response = request.urlopen(req, context=context)
    HTML = response.read().decode("utf-8")
    soup = BeautifulSoup(HTML, "html.parser")
    info = soup.find_all("a", onclick="moreurl(this, {from:'mv_a_pst'})")
    pat = r'href="(.*?)" onclick'
    urls = re.compile(pat).findall(str(info))
    return urls

# 获取url对应电影的详细信息
def getInfo(url):
    print("正在获取电影详细信息。。。")
    req = request.Request(url=url, headers=headers)
    response = request.urlopen(req, context=context)
    HTML = response.read().decode("utf-8")
    soup = BeautifulSoup(HTML, "html.parser")
    pat1 = r'<div class="subjectwrap clearfix">(.*?)<div class="gtleft">'
    mInfo_HTML = str(re.compile(pat1, re.S).findall(str(HTML)))
    dic = {}

    dic["片名"] = ""
    mname = soup.find("span", property="v:itemreviewed")
    if mname != None:
        dic["片名"] = mname.get_text()

    directors = soup.find_all("a", rel="v:directedBy")
    dic["导演"] = []
    for item in directors:
        dic["导演"].append(item.string)

    dic["编剧"] = ""
    pat_screenwriter = r'/">(.*?)</a>'
    dic["编剧"] = re.compile(pat_screenwriter).findall(mInfo_HTML)

    dic["主演"] = ""
    pat_Tostar = r'rel="v:starring">(.*?)</a>'
    dic["主演"] = re.compile(pat_Tostar).findall(mInfo_HTML)

    dic["类型"] = ""
    pat_type = r'property="v:genre">(.*?)</span>'
    dic["类型"] = re.compile(pat_type).findall(mInfo_HTML)

    dic[r"制片国家\地区"] = ""
    pat_conutry = r'制片国家/地区:</span>(.*?)<br/>'
    dic[r"制片国家\地区"] = re.compile(pat_conutry).findall(mInfo_HTML)

    dic["语言"] = ""
    pat_language = r'语言:</span>(.*?)<br/>'
    dic["语言"] = re.compile(pat_language).findall(mInfo_HTML)

    dic["上映日期"] = ""
    datetime = soup.find("span", property="v:initialReleaseDate")
    if datetime != None:
        dic["上映日期"] = datetime.get_text()

    dic["片长"] = ""
    movieLong = soup.find("span", property="v:runtime")
    if movieLong != None:
        dic["片长"] = movieLong.get_text()

    dic["又名"] = ""
    pat_othername = r'又名:</span>(.*?)<br/>'
    dic["又名"] = re.compile(pat_othername).findall(mInfo_HTML)

    dic["豆瓣评分"] = ""
    score = soup.find("strong", class_="ll rating_num")
    if score != None:
        dic["豆瓣评分"] = score.get_text()

    dic["评价人数"] = ""
    people_num = soup.find("span", property="v:votes")
    if people_num != None:
        dic["评价人数"] = people_num.get_text()

    # 星级比例
    stars = soup.find_all("span", class_="rating_per")
    if len(stars) == 5:
        i = 5
        for s in stars:
            dic[str(i) + "星"] = s.string
            i = i - 1
    else:
        for i in range(0, 5):
            dic[str(5 - i) + "星"] = ""

    dic["海报地址"] = ""
    pat_poster = r'<img src="(.*?)" title="点击看更多海报"'
    dic["海报地址"] = re.compile(pat_poster).findall(mInfo_HTML)
    response = request.urlopen(dic["海报地址"][0], context=context)

    result = re.search(re.compile("p(\d+)"),dic["海报地址"][0])

    # 本地存储海报图片到web项目img目录下
    RCSystem2="D:/xunlianying/workspace/RcSystem2/src/main/webapp/img/posters"

    filepath = RCSystem2 + r"\{0}.jpg".format(result.group(0))
    with open(filepath, 'wb') as f:
        f.write(response.read())
    print("完成！")
    return dic

# 返回一个list，结构如[[时间1,评论1],[时间2,评论2],...,[时间20,评论20]]
def getComments(urlPre, times):
    i = 0
    Commener = []
    print("正在获取评论信息。。。")
    while i < times:
        # 线程睡眠
        time.sleep(random.random() * 3)
        fullUrl = urlPre + "comments?start="+str(i*20)+"&limit=20&sort=new_score&status=P"
        req = request.Request(url=fullUrl, headers=headers)
        response = request.urlopen(req, context=context)
        HTML = response.read().decode("utf-8")
        soup = BeautifulSoup(HTML, "html.parser")
        commTimes = soup.find_all("span", class_="comment-time")
        comments = soup.find_all("span", class_="short")
        for tim in commTimes:
            lis = []
            date = tim.string
            date = date.replace(" ", "")
            date = date.replace("\n", "")
            lis.append(date)
            Commener.append(lis)

        j = 0
        for com in comments:
            Commener[len(Commener)-len(commTimes)+j].append(str(com.string))
            j = j + 1


        i = i + 1
    print("完成！")
    return Commener

# 保存电影详细信息
def saveInfo(dic):
    sql = r'replace into 最新电影信息 (片名, 导演, 编剧, 主演, 类型, 制片国家or地区, 语言, 上映日期, 片长, 又名, ' \
          '豆瓣评分, 评价人数, 5星, 4星, 3星, 2星, 1星, 海报地址) ' \
          'values (%s, %s ,%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'

    values = [dic["片名"],dic["导演"].__str__(),dic["编剧"].__str__(),dic["主演"].__str__(),
              dic["类型"].__str__(),dic[r"制片国家\地区"].__str__(),dic["语言"].__str__(),
              dic["上映日期"].__str__(),dic["片长"].__str__(),dic["又名"].__str__(),dic["豆瓣评分"].__str__(),
              dic["评价人数"].__str__(),dic["5星"].__str__(),dic["4星"].__str__(),dic["3星"].__str__(),
              dic["2星"].__str__(),dic["1星"].__str__(),dic["海报地址"].__str__()]
    print("正在保存电影信息。。。")
    cursor.execute(sql, values)
    conn.commit()
    print("电影信息已保存")

# 保存电影评论
def saveComments(movName, commList):
    sqlCTable = "CREATE TABLE IF NOT EXISTS `"+str(movName)+"` (id int not null primary key auto_increment,评论日期  VARCHAR(30),评论  TEXT)"
    cursor.execute(sqlCTable)
    conn.commit()

    sqlComm = "insert into `" +str(movName)+ "` (评论日期, 评论) values (%s, %s)"
    print("正在保存评论信息。。。")
    for cmner in commList:
        try:
            cursor.execute(sqlComm, cmner)
            conn.commit()
        except Exception as e:
            print("--以下评论无法存入数据库--")
            print(cmner)

    print("评论信息已保存\n")


# 建立数据库连接
conn = pymysql.connect(user='root', password='root', database='python')
cursor = conn.cursor()
url = "https://movie.douban.com/"

# 创建电影信息数据表
sqlMovie = 'CREATE TABLE IF NOT EXISTS 最新电影信息(片名 varchar(255) not null primary key, 导演 varchar(255), 编剧 varchar(255), 主演 TEXT, 类型 varchar(255), 制片国家or地区 varchar(255), 语言 varchar(255), 上映日期 varchar(255), 片长 varchar(255), 又名 varchar(255), 豆瓣评分 varchar(255), 评价人数 varchar(255), 5星 varchar(255), 4星 varchar(255), 3星 varchar(255), 2星 varchar(255), 1星 varchar(255), 海报地址 varchar(255))'
cursor.execute(sqlMovie)
conn.commit()

i = 0
for ur in getUrls(url):
    # 获取每个链接对应的影片详情
    info_Dict = getInfo(ur)
    info_Dict["片名"] = info_Dict["片名"][0:30]
    i = i + 1
    print("第" + str(i) + "部电影：" + info_Dict["片名"])
    # 保存电影详细信息
    saveInfo(info_Dict)
    # 评论起始页
    urlPre = ur.split("?")[0]
    # 获取评论
    commList = getComments(urlPre, 10)
    # 保存评论
    saveComments(info_Dict["片名"], commList)
    # 线程睡眠
    time.sleep(random.random() * 3)

print("恭喜哦！爬取最新电影信息任务，已完成！")
# 关闭数据库连接
conn.close