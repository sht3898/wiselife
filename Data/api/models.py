from django.db import models
from django.contrib.auth.models import AbstractBaseUser
from django.contrib.auth.models import UserManager
from .managers import CustomUserManager
from django.utils import timezone

now = timezone.localtime()


# Create your models here.
class User(AbstractBaseUser):
    uid = models.BigIntegerField(primary_key=True)
    ages = models.IntegerField(blank=True, null=True)
    area1 = models.TextField(blank=True, null=True)
    area2 = models.TextField(blank=True, null=True)
    gender = models.IntegerField(blank=True, null=True)
    is_inst = models.IntegerField(default=0)
    profile_image = models.TextField(blank=True, null=True)
    username = models.CharField(max_length=255, unique=True)
    year = models.IntegerField(blank=True, null=True)

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

    class Meta:
        managed = False
        db_table = 'user'


class Category(models.Model):
    category_id = models.AutoField(primary_key=True)
    category_description = models.TextField()
    category_name = models.TextField()

    def __str__(self):
            return self.category_name or ''

    class Meta:
        managed = False
        db_table = 'category'

class InterestCategory(models.Model):
    category = models.ForeignKey(Category, on_delete=models.CASCADE, related_name='interestCategory')
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='interestCategory')

    class Meta:
        managed = False
        db_table = 'interest_category'
        unique_together = (('category', 'uid'),)

class Meeting(models.Model):
    meeting_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='meeting')
    writer = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    address = models.TextField(blank=True, null=True)
    is_period = models.IntegerField()
    meeting_date = models.DateTimeField()
    period_date = models.TextField()
    is_class = models.IntegerField()
    max_person = models.IntegerField()
    now_person = models.IntegerField()
    content = models.TextField()
    ref_url = models.TextField(blank=True, null=True)
    fee = models.IntegerField()
    unit = models.TextField()
    is_active = models.IntegerField()
    like_cnt = models.IntegerField()
    view_cnt = models.IntegerField()
    score = models.FloatField()
    main_category = models.ForeignKey(Category, on_delete=models.CASCADE, related_name='meeting')
    tags = models.TextField(blank=True, null=True)
    title = models.TextField()
    area1 = models.TextField(blank=True, null=True)
    area2 = models.TextField(blank=True, null=True)

    def __str__(self):
            return self.title or ''
    
    class Meta:
        managed = False
        db_table = 'meeting'

class MeetingImages(models.Model):
    meeting_images_id = models.AutoField(primary_key=True)
    image_url = models.TextField(blank=True, null=True)
    meeting = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='meetingImages')

    class Meta:
        managed = False
        db_table = 'meeting_images'

class Review(models.Model):
    review_id = models.AutoField(primary_key=True)
    content = models.TextField(blank=True, null=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='review')
    meeting = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='review')
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    score = models.FloatField()
    image_url = models.TextField(blank=True, null=True)
    writer = models.TextField(blank=True, null=True)

    def __str__(self):
            return str(self.review_id)
        
    class Meta:
        managed = False
        db_table = 'review'


class Chatting(models.Model):
    chatting_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='chatting')
    meeting_id = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='chatting')
    writer = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    content = models.TextField()

    def __str__(self):
            return self.chatting or ''

    class Meta:
        managed = False
        db_table = 'chatting'

class UserMeeting(models.Model):
    meeting = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='userMeeting')
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='userMeeting')
    is_active = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'user_meeting'
        unique_together = (('meeting', 'uid'),)


class Survey(models.Model):
    survey_id = models.AutoField(primary_key=True)
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='survey')
    openness = models.IntegerField()
    conscientiousness = models.IntegerField()
    extraversion = models.IntegerField()
    agreeableness = models.IntegerField()
    neuroticism = models.IntegerField()

    def __str__(self):
            return str(self.survey_id)

    class Meta:
        managed = False
        db_table = 'survey'


class Area(models.Model):
    area_id = models.AutoField(primary_key=True)
    first_area = models.TextField(blank=True, null=True)
    second_area = models.TextField(blank=True, null=True)

    def __str__(self):
            return str(self.area_id)

    class Meta:
        managed = False
        db_table = 'area'


class LikeMeeting(models.Model):
    meeting = models.ForeignKey(Meeting, on_delete=models.CASCADE, related_name='likeMeeting')
    uid = models.ForeignKey(User, on_delete=models.CASCADE, related_name='likeMeeting')

    class Meta:
        managed = False
        db_table = 'like_meeting'
        unique_together = (('meeting', 'uid'),)
