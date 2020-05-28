from django.contrib import admin
import api.models

# Register your models here.
admin.register(api.models.Category, api.models.Chatting, api.models.InterestCategory, api.models.LikeMeeting, api.models.Meeting, api.models.MeetingImages, api.models.Review, api.models.Survey, api.models.UserMeeting) 
