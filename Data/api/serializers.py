from .models import Area, Category, Chatting, InterestCategory, LikeMeeting, Meeting, MeetingImages, Review, Survey, User, UserMeeting
from rest_framework import serializers
from django.contrib.auth import get_user_model, authenticate
from rest_framework_jwt.serializers import VerificationBaseSerializer
from rest_framework_jwt.compat import get_username_field, PasswordField
from rest_framework.serializers import ValidationError

class MeetingSerializer(serializers.ModelSerializer):
    class Meta:
        model = Meeting
        fields = '__all__'

