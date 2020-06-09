from pathlib import Path
import pandas as pd
import numpy as np
from django.core.management.base import BaseCommand
from wiselife import settings
from api import models

class Command(BaseCommand):
    help = "initialize database"
    DATA_DIR = Path(settings.BASE_DIR)
    DATA_FILE = str(DATA_DIR / "meetings.csv") # meetings 정보
    IMAGE_FILE = str(DATA_DIR / "images.csv") # images 정보

    def _load_dataframes(self):
        try:
            meetings = pd.read_csv(Command.DATA_FILE)
            images = pd.read_csv(Command.IMAGE_FILE)
            
        except:
            print(f"[-] Reading {Command.DATA_FILE} failed")
            exit(1)
        return meetings, images

    def _initialize(self):
        """
        DB를 초기화
        """
        print("[*] Loading data...")
        meetings, images = self._load_dataframes()
# load meetings dataframe
# meetings
        print("[*] Initializing meetings...")
        # print(meetings.head())
        models.Meeting.objects.all().delete()
        meetings_bulk = [
            models.Meeting(
                meeting_id = meeting.meeting_id,
                uid = models.User.objects.get(uid=1),
                writer = meeting.writer,
                created_at = meeting.created_at,
                updated_at = meeting.updated_at,
                is_period = meeting.is_period,
                meeting_date = meeting.meeting_date,
                period_date = meeting.period_date,
                is_class = meeting.is_class,
                max_person = meeting.max_person,
                now_person = 0,
                content = meeting.content,
                ref_url = meeting.ref_url,
                address = meeting.address,
                fee = meeting.fee,
                unit = meeting.unit,
                is_active = meeting.is_active,
                like_cnt = 0,
                view_cnt = 0,
                score = 0,
                main_category = models.Category.objects.get(category_id=1),
                tags = meeting.tags,
                title = meeting.title,
                area1 = "기타",
                area2 = "기타"
            )
            for meeting in meetings.itertuples()
        ]
        print('[*] Bulk ...................................')
        models.Meeting.objects.bulk_create(meetings_bulk)

        print("[+] Done")

# # load images dataframe
# # images
#         print("[*] Initializing meeting images...")
#         models.MeetingImages.objects.all().delete()
#         images_bulk = [
#             models.MeetingImages(
#                 meeting_id = images.meeting_id,
#                 images_url = images.image_url
#             )
#             for meeting in images.itertuples()
#         ]
#         models.MeetingImages.objects.bulk_create(images_bulk)

#         print("[+] Done")


    def handle(self, *args, **kwargs):
        self._initialize()
