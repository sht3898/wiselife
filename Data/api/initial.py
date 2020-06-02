import sqlite3
# import scipy.io
import csv
import pymysql
import pandas as pd

# # load data
# df = pd.read_csv('meetings.csv')

# # strip whitespace from headers
# print(df[0])
# df.columns = df.columns.str.strip()
# con = sqlite3.connect("db.sqlite3")
# curs = con.cursor()

    
# # drop data into database
# df.to_sql("api_meeting", con)

# con.close()


# conn = sqlite3.connect("db.sqlite3")

# curs = conn.cursor()

# conn.commit()



# f = open('meetings.csv','r', encoding='utf-8')

# csvReader = csv.reader(f)


# temp = 0
# for row in csvReader:
#     if temp == 0:
#         temp += 1
#         continue
#     else:

#         meeting_id = row[0]
#         uid = row[1]
#         writer = row[2]
#         created_at = row[3]
#         updated_at = row[4]
#         is_period = row[5]
#         meeting_date = row[6]
#         period_date = row[7]
#         is_class = row[8]
#         max_person = row[9]
#         now_person = row[10]
#         content = row[11]
#         ref_url = row[12]
#         address = row[13]
#         fee = row[14]
#         unit = row[15]
#         is_active = row[16]
#         like_cnt = row[17]
#         view_cnt = row[18]
#         score = row[19]
#         main_category = row[20]
#         tags = row[21]
#         title = row[22]
#         area1 = row[23]
#         area2 = row[24]

#         sql = """insert into api_meeting (meeting_id, uid, writer, updated_at, is_period, meeting_date, period_date, is_class, max_person, now_person, content, ref_url, address, fee, unit, is_active, like_cnt, view_cnt, score, main_category, tags, title, area1, area2
#         values (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"""
#         curs.execute(sql, (meeting_id, uid, writer, updated_at, is_period, meeting_date, period_date, is_class, max_person, now_person, content, ref_url, address, fee, unit, is_active, like_cnt, view_cnt, score, main_category, tags, title, area1, area2))



# #db의 변화 저장

# conn.commit()

    

# f.close()

# conn.close()

meeting_db = sqlite3.connect("db.sqlite3")
cursor = meeting_db.cursor()

sql = "select * from `api_category`;"
cursor.execute(sql)
result = cursor.fetchall()
result = pd.DataFrame(result)
print(result)