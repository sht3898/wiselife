from django.shortcuts import render, get_object_or_404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework.authtoken.models import Token
from . import models, recommend
from .serializers import MeetingSerializer



# Create your views here.
# 유저 기반 추천 시스템
# recommend(유저 아이디, 추천 받고 싶은 추천 개수(기본값=12))
@api_view(["GET"])
def Recommend(request, uid):
    user = get_object_or_404(models.User, uid=uid)
    meetings = recommend.recommend(user.uid)
    results = []
    for meeting in meetings.meeting_id.values:
        results.append(MeetingSerializer(models.Meeting.objects.get(meeting_id=meeting)).data)
    return Response(results, status=status.HTTP_200_OK)
