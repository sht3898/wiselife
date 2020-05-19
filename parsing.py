import requests
from bs4 import BeautifulSoup


headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36'}
url = "https://www.frip.co.kr/products/108923"
webpage = requests.get(url, headers=headers)
soup = BeautifulSoup(webpage.content, "html.parser")

print(soup)