<template>
  <v-container>
    <v-flex class="ma-auto mt-5 pt-5" lg10 sm12>
      <v-card>
        <v-toolbar flat color="green" dark>
          <v-toolbar-title>MY PAGE</v-toolbar-title>
        </v-toolbar>
        <v-tabs vertical color="green" class="hidden-xs-only justify-content-around">
          <v-tab>
            <v-icon left>mdi-timetable</v-icon>My meeting
          </v-tab>
          <v-tab>
            <v-icon left>mdi-pentagon-outline</v-icon>Big FIVE
          </v-tab>

          <v-tab>
            <v-icon left>mdi-account</v-icon>My Infomation
          </v-tab>
          <v-tab-item>
            <v-card flat>
              <my-meeting-tab />
            </v-card>
          </v-tab-item>

          <v-tab-item>
            <v-card flat>
              <my-survey-tab />
            </v-card>
          </v-tab-item>

          <v-tab-item>
            <v-card flat>
              <my-info-tab />
            </v-card>
          </v-tab-item>
        </v-tabs>

        <v-tabs grow color="green" class="hidden-sm-and-up">
          <v-tab>
            <v-icon left>mdi-timetable</v-icon>
          </v-tab>
          <v-tab>
            <v-icon left>mdi-pentagon-outline</v-icon>
          </v-tab>

          <v-tab>
            <v-icon left>mdi-account</v-icon>
          </v-tab>

          <v-tab-item>
            <v-card flat>
              <my-meeting-tab />
            </v-card>
          </v-tab-item>

          <v-tab-item>
            <v-card flat>
              <my-survey-tab />
            </v-card>
          </v-tab-item>
          <v-tab-item>
            <v-card flat>
              <my-info-tab />
            </v-card>
          </v-tab-item>
        </v-tabs>
      </v-card>
    </v-flex>
  </v-container>
</template>
<script>
import MyInfoTab from "@/components/mypage/MyInfoTab";
import MyMeetingTab from "@/components/mypage/MyMeetingTab";
import MySurveyTab from "@/components/mypage/MySurveyTab";
export default {
  name: "MyPage",
  components: {
    MyInfoTab,
    MyMeetingTab,
    MySurveyTab
  }
};
</script>
<style scoped>
</style>
