from django.db import models
from django.contrib.auth.models import AbstractBaseUser
from django.contrib.auth.models import UserManager
from .managers import CustomUserManager
from django.utils import timezone

now = timezone.localtime()


# Create your models here.
class User(AbstractBaseUser):
    uid = models.AutoField(primary_key=True)
    username = models.TextField(unique=True)
    profile_image = models.TextField(null=True)
    is_inst = models.IntegerField(default=0)
    gender = models.IntegerField(null=True)
    year = models.IntegerField(null=True)
    ages = models.IntegerField(null=True)
    area1 = models.TextField(null=True)
    area2 = models.TextField(null=True)

    is_staff = models.BooleanField(default=False)
    is_superuser = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)

    objects = CustomUserManager()
    
    USERNAME_FIELD = 'username'
    REQUIRED_FIELDS = []

    def __str__(self):
        return self.username or ''

    def has_perm(self, perm, obj=None):
        return self.is_superuser

    def has_module_perms(self, app_label):
        return self.is_superuser



class Category(models.Model):
    category_id = models.AutoField(primary_key=True)
    category_name = models.TextField()
    category_description = models.TextField()

    def __str__(self):
            return self.category_name or ''

class InterestCategory(models.Model):
    category_id = models.ForeignKey(Category, on_delete=models.CASCADE, related_name='interestCategory')
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='interestCategory')

class Meeting(models.Model):
    meeting_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='meeting')
    writer = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    is_period = models.IntegerField()
    meeting_date = models.DateTimeField()
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
    main_category = models.ForeignKey(Category, on_delete=models.CASCADE, related_name='meeting')
    tags = models.TextField()
    title = models.TextField()
    area1 = models.TextField()
    area2 = models.TextField()

    def __str__(self):
            return self.title or ''

class MeetingImages(models.Model):
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='meetingImages')
    image_url = models.TextField()

class Review(models.Model):
    review_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='review')
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='review')
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    score = models.IntegerField()
    image_url = models.TextField()

    def __str__(self):
            return self.score or ''

class Chatting(models.Model):
    chatting_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='chatting')
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='chatting')
    writer = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    content = models.TextField()

    def __str__(self):
            return self.chatting or ''

class UserMeeting(models.Model):
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='userMeeting')
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='userMeeting')
    is_active = models.IntegerField()

class Survey(models.Model):
    survey_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='survey')
    openness = models.IntegerField()
    conscientiousness = models.IntegerField()
    extraversion = models.IntegerField()
    agreeableness = models.IntegerField()
    neuroticism = models.IntegerField()

    def __str__(self):
            return self.survey_id or ''

class Area(models.Model):
    area_id = models.AutoField(primary_key=True)
    first_area = models.TextField()
    second_area = models.TextField(null=True)

    def __str__(self):
            return self.first_area or ''

class LikeMeeting(models.Model):
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='likeMeeting')
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='likeMeeting')
