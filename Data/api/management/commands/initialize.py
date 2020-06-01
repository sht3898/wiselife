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
        print('[*] Delete ...................................')
        print(meetings.dtypes)
        meetings.meeting_id = meetings.meeting_id.astype('int')
        meetings_bulk = [
            models.Meeting(
                meeting_id = meetings.meeting_id,
                uid = models.User.objects.get(uid=1),
                writer = meetings.writer,
                created_at = meetings.created_at,
                updated_at = meetings.updated_at,
                is_period = meetings.is_period,
                meeting_date = meetings.meeting_date,
                period_date = meetings.period_date,
                is_class = meetings.is_class,
                max_person = meetings.max_person,
                now_person = 0,
                content = meetings.content,
                ref_url = meetings.ref_url,
                address = meetings.address,
                fee = meetings.fee,
                unit = meetings.unit,
                is_active = meetings.is_active,
                like_cnt = 0,
                view_cnt = 0,
                score = 0,
                main_category = models.Category.objects.get(category_id=1),
                tags = meetings.tags,
                title = meetings.title,
                area1 = "기타",
                area2 = "기타"
            )
            for meeting in meetings.itertuples()
        ]
        print('[*] Bulk ...................................')
        models.Meeting.objects.bulk_create(meetings_bulk)

        print("[+] Done")

# load images dataframe
# images
        print("[*] Initializing meeting images...")
        models.MeetingImages.objects.all().delete()
        images_bulk = [
            models.MeetingImages(
                meeting_id = images.meeting_id,
                images_url = images.image_url
            )
            for meeting in images.itertuples()
        ]
        models.MeetingImages.objects.bulk_create(images_bulk)

        print("[+] Done")


    def handle(self, *args, **kwargs):
        self._initialize()
