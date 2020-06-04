from django.shortcuts import render, get_object_or_404
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework.authtoken.models import Token
from . import recommend
from . import models
from .serializers import MeetingSerializer



# Create your views here.
# 유저 기반 추천 시스템
@api_view(["GET"])
def Recommend(request, uid):
    user = get_object_or_404(models.User, uid=uid)

# 미팅 기반 추천 시스템
@api_view(["GET"])
def MeetingRecommend(request, meeting_id):
    meeting = get_object_or_404(models.Meeting, meeting_id=meeting_id)
    