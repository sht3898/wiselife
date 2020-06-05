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
    # sqlite3 연결
    con = sqlite3.connect("db.sqlite3")
    cur = con.cursor()
    df_meetings = pd.read_sql('SELECT * FROM api_meeting', con=con)
    df_reviews = pd.read_sql('SELECT * FROM api_review', con=con)
    df_reviews.rename(columns={'meeting_id_id':'meeting_id', 'uid_id':'uid'}, inplace=True)
    con.close()

    # # mysql 연결
    # conn = pymysql.connect(host='13.125.114.122', user='root', password='wiselife5', db='wiselife', charset='utf8')
    # curs = conn.cursor()
    # df_meetings = pd.read_sql('select * from meeting', con=conn)
    # df_reviews = pd.read_sql('select * from review', con=conn)
    # print(df_meetings)
    # print(df_reviews)
    # conn.close()

    meetings = df_meetings[['meeting_id', 'title']]
    reviews = df_reviews[['uid', 'meeting_id', 'score']]
    ratings = pd.merge(meetings, reviews, on='meeting_id')
    return meetings, reviews, ratings

def make_list(num_k=3):
    meetings, reviews, ratings = loaddata()
    df_user_meeting_ratings = ratings.pivot(
        index='uid',
        columns='meeting_id',
        values='score'
    ).fillna(0)
    matrix = np.matrix(df_user_meeting_ratings)
    user_ratings_mean = np.mean(matrix, axis=1)
    matrix_user_mean = matrix - user_ratings_mean.reshape(-1, 1)
    U, sigma, Vt = svds(matrix_user_mean, k=num_k)
    sigma = np.diag(sigma)
    svd_user_predicted_ratings = np.dot(np.dot(U, sigma), Vt) + user_ratings_mean.reshape(-1, 1)
    df_svd_preds = pd.DataFrame(svd_user_predicted_ratings, columns=df_user_meeting_ratings.columns)
    return df_svd_preds, meetings, reviews



def recommend(uid, num_recommendations=12):
    df_svd_preds, meetings, reviews = make_list()
    sorted_user_predictions = df_svd_preds.iloc[uid].sort_values(ascending=False)
    user_data = reviews[reviews.uid == uid]
    user_history = user_data.merge(meetings, on='meeting_id').sort_values(['score'], ascending=False)
    recommendations = meetings[~meetings['meeting_id'].isin(user_history['meeting_id'])]
    recommendations = recommendations.merge(pd.DataFrame(sorted_user_predictions).reset_index(), on='meeting_id')
    recommendations = recommendations.rename(columns={uid: 'Predictions'}).sort_values('Predictions', ascending=False).iloc[:num_recommendations, :]

    return recommendations

if __name__ == "__main__":
    predictions = recommend(1, 10)
    print(predictions)