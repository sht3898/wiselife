import random
from django.shortcuts import render, get_object_or_404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework.authtoken.models import Token
from . import models, recommend
from .serializers import MeetingSerializer, UserSerializer, MeetingSerializer


# Create your views here.
# 유저 기반 추천 시스템
# recommend(유저 아이디, 추천 받고 싶은 추천 개수(기본값=12))
@api_view(["GET"])
def Recommend(request, uid):
    meeting_count = models.Meeting.objects.count()
    review_count = models.Review.objects.filter(uid=uid).count()
    results = []
    if review_count >= 3 and meeting_count >= 30:
        user = get_object_or_404(models.User, uid=uid)
        meetings = recommend.recommend(user.uid)
        for meeting in meetings.meeting_id.values:
            results.append(MeetingSerializer(models.Meeting.objects.get(meeting_id=meeting)).data)
            # results.append(MeetingSerializer(models.Meeting.objects.get(meeting_id=uid)).data)
    else:
        meetings = models.Meeting.objects.order_by('like_cnt')[:12]
        for meeting in meetings:
            results.append(MeetingSerializer(models.Meeting.objects.get(meeting_id=meeting.meeting_id)).data)
    return Response(results, status=status.HTTP_200_OK)


@api_view(["GET"])
def RandomRecommend(request, uid):
    meeting_images_list = models.MeetingImages.objects.all()
    index_list = []
    for m in meeting_images_list:
        index_list.append(m.meeting_images_id)
    meeting_list = random.sample(index_list, 12)
    results = []

    likes = models.LikeMeeting.objects.filter(uid=uid)
    like_list = []
    for like in likes:
        like_list.append(like.meeting_id)

    for meeting in meeting_list:
        idx = models.MeetingImages.objects.get(pk=meeting).meeting_id
        temp = MeetingSerializer(models.Meeting.objects.get(meeting_id=idx)).data
        
        images = models.MeetingImages.objects.filter(meeting_id=idx)
        image_list = []
        for image in images:
            image_list.append(image.image_url) 
        temp['image_url'] = image_list

        if idx in like_list:
            is_like = 1
        else:
            is_like = 0

        temp['is_like'] = is_like
        # results.append(MeetingSerializer(models.Meeting.objects.get(meeting_id=idx)).data)
        results.append(temp)
    return Response(results, status=status.HTTP_200_OK)


@api_view(["GET"])
def UserDetail(request, uid):
    user = models.User.objects.get(uid=uid)
    print(user)
    return Response()


@api_view(["GET"])
def Users(request):
    users = models.User.objects.values()
    results = []
    for user in users:
        results.append(UserSerializer(models.User.objects.get(uid=user['uid'])).data)
    return Response(results, status=status.HTTP_200_OK)


@api_view(["GET"])
def Meetings(request):
    meetings = models.Meeting.objects.values()
    results = []
    print(meetings)
    for meeting in meetings:
        results.append(MeetingSerializer(models.Meeting.objects.get(meeting_id=meeting['meeting_id'])).data)
    return Response(results, status=status.HTTP_200_OK)

@api_view(["GET"])
def MeetingDetail(request, meeting_id):
    meeting = models.Meeting.objects.get(meeting_id=meeting_id)
    print(meeting)
    return Response()