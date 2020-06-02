from django.contrib.auth.base_user import BaseUserManager
from django.utils.translation import ugettext_lazy as _


class CustomUserManager(BaseUserManager):
    def create_user(self, password, username, profile_image, is_inst, gender, year, ages, area1, area2):
        user = self.model(
            username = username,
            profile_image = profile_image,
            is_inst = is_inst,
            gender = gender,
            year = year,
            ages = ages,
            area1 = area1,
            area2 = area2
        )
        user.set_password(password)
        user.save()
        return user

    def create_superuser(self, password, username):
        user = self.model(
            username = username,
        )
        user.is_admin = True        
        user.is_superuser = True        
        user.is_staff = True
        user.set_password(password)
        user.save()
        return user
