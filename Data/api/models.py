from django.db import models
from django.contrib.auth.models import AbstractBaseUser


# Create your models here.
class User(AbstractBaseUser):
    uid = models.AutoField(primary_key=True)
    username = models.TextField()
    profile_image = models.TextField()
    is_inst = models.IntegerField(default=0)
    gender = models.IntegerField()
    year = models.IntegerField()
    ages = models.IntegerField()
    area1 = models.TextField()
    area2 = models.TextField()
    
    USERNAME_FIELD = 'uid'
    REQUIRED_FIELDS = []

    def __str__(self):
        return self.uid

class Category(models.Model):
    category_id = models.AutoField(primary_key=True)
    category_name = models.TextField()
    category_description = models.TextField()

class InterestCategory(models.Model):
    category_id = models.ForeignKey(Category, on_delete=models.CASCADE)
    uid = models.ForeignKey(User, on_delete=models.CASCADE)

class Meeting(models.Model):
    meeting_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE)
    writer = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    is_period = models.IntegerField()
    meeting_data = models.DateTimeField()
    period_date = models.TextField()
    is_class = models.IntegerField()
    max_person = models.IntegerField()
    now_person = models.IntegerField()
    content = models.TextField()
    ref_url = models.TextField()
    address = models.TextField()
    fee = models.IntegerField()
    unit = models.TextField()
    is_active = models.IntegerField()
    like_cnt = models.IntegerField()
    view_cnt = models.IntegerField()
    score = models.FloatField()
    main_category = models.ForeignKey(Category, on_delete=models.CASCADE)
    tags = models.TextField()
    area1 = models.TextField()
    area2 = models.TextField()

class MeetingImages(models.Model):
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE)
    image_url = models.TextField()

class Review(models.Model):
    review_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE)
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    score = models.IntegerField()
    image_url = models.TextField()

class Chatting(models.Model):
    chatting_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE)
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE)
    writer = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    content = models.TextField()

class UserMeeting(models.Model):
    uid = models.ForeignKey(User, on_delete=models.CASCADE)
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE)
    is_active = models.IntegerField()

class Survey(models.Model):
    survey_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE)
    openness = models.IntegerField()
    conscientiousness = models.IntegerField()
    extraversion = models.IntegerField()
    agreeableness = models.IntegerField()
    neuroticism = models.IntegerField()

class LikeMeeting(models.Model):
    uid = models.ForeignKey(User, on_delete=models.CASCADE)
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE)
