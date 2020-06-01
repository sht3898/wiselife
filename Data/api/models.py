# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Area(models.Model):
    area_id = models.AutoField(primary_key=True)
    first_area = models.CharField(max_length=255, blank=True, null=True)
    second_area = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'area'


class Category(models.Model):
    category_id = models.AutoField(primary_key=True)
    category_description = models.TextField(blank=True, null=True)
    category_name = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'category'


class InterestCategory(models.Model):
    category = models.OneToOneField(Category, models.DO_NOTHING, primary_key=True)
    uid = models.ForeignKey('User', models.DO_NOTHING, db_column='uid')

    class Meta:
        managed = False
        db_table = 'interest_category'
        unique_together = (('category', 'uid'),)


class LikeMeeting(models.Model):
    meeting = models.OneToOneField('Meeting', models.DO_NOTHING, primary_key=True)
    uid = models.ForeignKey('User', models.DO_NOTHING, db_column='uid')

    class Meta:
        managed = False
        db_table = 'like_meeting'
        unique_together = (('meeting', 'uid'),)


class Meeting(models.Model):
    meeting_id = models.AutoField(primary_key=True)
    address = models.CharField(max_length=255, blank=True, null=True)
    area1 = models.CharField(max_length=255, blank=True, null=True)
    area2 = models.CharField(max_length=255, blank=True, null=True)
    content = models.TextField(blank=True, null=True)
    created_at = models.DateTimeField(blank=True, null=True)
    fee = models.IntegerField()
    is_active = models.IntegerField()
    is_class = models.IntegerField()
    is_period = models.IntegerField()
    like_cnt = models.IntegerField()
    max_person = models.IntegerField()
    meeting_date = models.DateTimeField(blank=True, null=True)
    now_person = models.IntegerField()
    period_date = models.CharField(max_length=255, blank=True, null=True)
    phone = models.CharField(max_length=255, blank=True, null=True)
    ref_url = models.CharField(max_length=255, blank=True, null=True)
    score = models.FloatField()
    tags = models.CharField(max_length=255, blank=True, null=True)
    title = models.CharField(max_length=255, blank=True, null=True)
    unit = models.CharField(max_length=255, blank=True, null=True)
    updated_at = models.DateTimeField(blank=True, null=True)
    view_cnt = models.IntegerField()
    writer = models.CharField(max_length=255, blank=True, null=True)
    main_category = models.ForeignKey(Category, models.DO_NOTHING, db_column='main_category', blank=True, null=True)
    uid = models.ForeignKey('User', models.DO_NOTHING, db_column='uid', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'meeting'


class MeetingImages(models.Model):
    meeting_images_id = models.AutoField(primary_key=True)
    image_url = models.CharField(max_length=255, blank=True, null=True)
    meeting = models.ForeignKey(Meeting, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'meeting_images'


class Review(models.Model):
    review_id = models.AutoField(primary_key=True)
    content = models.TextField(blank=True, null=True)
    created_at = models.DateTimeField(blank=True, null=True)
    image_url = models.CharField(max_length=255, blank=True, null=True)
    score = models.FloatField(blank=True, null=True)
    updated_at = models.DateTimeField(blank=True, null=True)
    writer = models.CharField(max_length=255, blank=True, null=True)
    meeting = models.ForeignKey(Meeting, models.DO_NOTHING, blank=True, null=True)
    uid = models.ForeignKey('User', models.DO_NOTHING, db_column='uid', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'review'


class Survey(models.Model):
    survey_id = models.AutoField(primary_key=True)
    agreeableness = models.IntegerField()
    conscientiousness = models.IntegerField()
    extraversion = models.IntegerField()
    neuroticism = models.IntegerField()
    openness = models.IntegerField()
    uid = models.ForeignKey('User', models.DO_NOTHING, db_column='uid', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'survey'


class User(models.Model):
    uid = models.BigIntegerField(primary_key=True)
    ages = models.IntegerField()
    area1 = models.CharField(max_length=255, blank=True, null=True)
    area2 = models.CharField(max_length=255, blank=True, null=True)
    gender = models.IntegerField()
    is_inst = models.IntegerField()
    profile_image = models.CharField(max_length=255, blank=True, null=True)
    username = models.CharField(max_length=255, blank=True, null=True)
    year = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'user'


class UserMeeting(models.Model):
    meeting = models.OneToOneField(Meeting, models.DO_NOTHING, primary_key=True)
    uid = models.ForeignKey(User, models.DO_NOTHING, db_column='uid')
    is_active = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'user_meeting'
        unique_together = (('meeting', 'uid'),)
