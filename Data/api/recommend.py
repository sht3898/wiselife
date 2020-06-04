from sklearn.decomposition import TruncatedSVD
from scipy.sparse.linalg import svds

import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
import warnings
import sqlite3
import surprise
import pymysql
warnings.filterwarnings('ignore')

def loaddata():
    # # sqlite3 연결
    # con = sqlite3.connect("db.sqlite3")
    # cur = con.cursor()
    # df_meetings = pd.read_sql('SELECT * FROM api_meeting', con=con)
    # df_reviews = pd.read_sql('SELECT * FROM api_review', con=con)
    # print(df_meetings)
    # print(df_reviews)
    # con.close()

    # mysql 연결
    conn = pymysql.connect(host='13.125.114.122', user='root', password='wiselife5', db='wiselife', charset='utf8')
    curs = conn.cursor()
    df_meetings = pd.read_sql('select * from meeting', con=conn)
    df_reviews = pd.read_sql('select * from review', con=conn)
    conn.close()

    print(df_meetings)
    print(df_reviews)
    return df_meetings, df_reviews

def recommend(uid):
    df_meetings, df_reviews = loaddata()


if __name__ == "__main__":
    loaddata()