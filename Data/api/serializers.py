from .models import Area, Category, Chatting, InterestCategory, LikeMeeting, Meeting, MeetingImages, Review, Survey, User, UserMeeting
from rest_framework import serializers
from django.contrib.auth import get_user_model, authenticate
from rest_framework.serializers import ValidationError

class MeetingSerializer(serializers.ModelSerializer):
    class Meta:
        model = Meeting
        fields = '__all__'


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['uid', 'ages', 'area1', 'area2', 'gender', 'is_inst', 'profile_image', 'username', 'year']

class MeetingSerializer(serializers.ModelSerializer):
    class Meta:
        model = Meeting
        fields = '__all__'