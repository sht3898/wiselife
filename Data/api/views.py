from django.shortcuts import render, get_object_or_404
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework.authtoken.models import Token
from . import recommend
from . import models
from .serializers



# Create your views here.
@api_view(["GET"])
def Recommend(request, uid):
    user = get_object_or_404(models.User, uid=uid)
    
