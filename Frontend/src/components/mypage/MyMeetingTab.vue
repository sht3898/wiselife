<template>
  <v-flex class="ma-auto mt-5" lg11 xs12>
    <v-container fluid>
      <p class="menu">
        참여한 강좌/모임
        <span class="blinking" style="float:right">
          <v-btn rounded class="reviewbtn orange lighten-1" @click="insertReview">리뷰 작성 ✒️</v-btn>
        </span>
      </p>

      <v-row v-if="!ok" justify="center" align="center">
        <v-content class="text-center progress">
          <v-progress-circular
            indeterminate
            :rotate="0"
            :size="50"
            :value="0"
            :width="7"
            color="orange"
            class="ma-auto"
          />
        </v-content>
      </v-row>

      <div v-if="ok">
        <v-content
          v-if="notattend"
          class="my-10"
          style="text-align:center; color:grey; font-weight:bold"
        >참여한 강좌/모임이 없습니다!</v-content>

        <attend-meeting-list v-else :attendlist="this.attendmeetinglist" />
      </div>
    </v-container>
    <!-- 리뷰 modal -->
    <v-dialog v-model="dialog" scrollable max-width="800">
      <v-card>
        <v-card-title>
          <v-row class="menu">
            <v-col cols="9">강좌/모임 Review</v-col>
            <v-col cols="3">
              <span style="text-align:right; float:right">
                <v-btn color="green darken-1" text @click="dialog = false">Close</v-btn>
              </span>
            </v-col>
          </v-row>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text style="max-height: 550px;">
          <review-modal />
        </v-card-text>
      </v-card>
    </v-dialog>

    <v-container fluid mb-12>
      <p class="menu">등록한 강좌/모임</p>
      <v-row v-if="!ok" justify="center" align="center">
        <v-content class="text-center progress">
          <v-progress-circular
            indeterminate
            :rotate="0"
            :size="50"
            :value="0"
            :width="7"
            color="orange"
            class="ma-auto"
          />
        </v-content>
      </v-row>

      <div v-if="ok">
        <v-content
          v-if="notcreate"
          class="my-10"
          style="text-align:center; color:grey; font-weight:bold"
        >등록한 강좌/모임이 없습니다!</v-content>

        <create-meeting-list v-else :createlist="this.createmeetinglist" />
      </div>
    </v-container>
  </v-flex>
</template>
<script>
import http from "../../http-common";
import ReviewModal from "./ReviewModal";
import AttendMeetingList from "./AttendMeetingList";
import CreateMeetingList from "./CreateMeetingList";
export default {
  name: "MyPage",
  components: {
    ReviewModal,
    AttendMeetingList,
    CreateMeetingList
  },
  data() {
    return {
      dialog: false,
      attendmeetinglist: [],
      createmeetinglist: [],
      notattend: false,
      notcreate: false,
      ok: false
    };
  },
  methods: {
    insertReview() {
      this.dialog = true;
    },
    getMyMeeting() {
      this.ok = false;
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`meeting/list`, config)
        .then(response => {
          this.attendmeetinglist = response.data.참여;
          if (this.attendmeetinglist.length == 0) {
            this.notattend = true;
          }

          this.createmeetinglist = response.data.등록;
          if (this.createmeetinglist.length == 0) {
            this.notcreate = true;
          }

          for (var i = 0; i < this.attendmeetinglist.length; i++) {
            if (this.attendmeetinglist[i].tags != null) {
              let split_tags = this.attendmeetinglist[i].tags.split(" ");
              let tags = [];
              for (var j in split_tags) {
                tags.push(split_tags[j]);
              }
              tags = Array.from(new Set(tags));
              this.attendmeetinglist[i].tags = tags;
            }
            if (this.attendmeetinglist[i].meetingImages.length != 0) {
              if (
                this.attendmeetinglist[i].meetingImages[0].substring(2, 3) ==
                "t"
              ) {
                if (
                  this.attendmeetinglist[i].meetingImages[0].substring(0, 1) ==
                  '"'
                ) {
                  let length = this.attendmeetinglist[i].meetingImages[0]
                    .length;
                  this.attendmeetinglist[
                    i
                  ].meetingImages[0] = this.attendmeetinglist[
                    i
                  ].meetingImages[0].substring(1, length - 1);
                }
                this.attendmeetinglist[
                  i
                ].meetingImages = this.attendmeetinglist[
                  i
                ].meetingImages[0].split(" ")[0];
                if (
                  this.attendmeetinglist[i].meetingImages[0].substring(0, 1) ==
                  "h"
                ) {
                  this.attendmeetinglist[i].isUrl = true;
                } else {
                  this.attendmeetinglist[i].isUrl = false;
                }
              } else {
                this.attendmeetinglist[
                  i
                ].meetingImages = this.attendmeetinglist[i].meetingImages[0];
              }
            } else {
              this.attendmeetinglist[i].meetingImages = null;
            }
          }

          for (var m = 0; m < this.createmeetinglist.length; m++) {
            if (this.createmeetinglist[m].tags != null) {
              let split_tags = this.createmeetinglist[m].tags.split(" ");
              let tags = [];
              for (var n in split_tags) {
                tags.push(split_tags[n]);
              }
              tags = Array.from(new Set(tags));
              this.createmeetinglist[m].tags = tags;
            }
            if (this.createmeetinglist[m].meetingImages.length != 0) {
              if (
                this.createmeetinglist[m].meetingImages[0].substring(2, 3) ==
                "t"
              ) {
                if (
                  this.createmeetinglist[m].meetingImages[0].substring(0, 1) ==
                  '"'
                ) {
                  let length = this.createmeetinglist[m].meetingImages[0]
                    .length;
                  this.createmeetinglist[
                    m
                  ].meetingImages[0] = this.createmeetinglist[
                    m
                  ].meetingImages[0].substring(1, length - 1);
                }
                this.createmeetinglist[
                  m
                ].meetingImages = this.createmeetinglist[
                  m
                ].meetingImages[0].split(" ")[0];
                if (
                  this.createmeetinglist[m].meetingImages[0].substring(0, 1) ==
                  "h"
                ) {
                  this.createmeetinglist[m].isUrl = true;
                } else {
                  this.createmeetinglist[m].isUrl = false;
                }
              } else {
                this.createmeetinglist[
                  m
                ].meetingImages = this.createmeetinglist[m].meetingImages[0];
              }
            } else {
              this.createmeetinglist[m].meetingImages = null;
            }
          }
          this.ok = true;
        })
        .catch(() => {});
    }
  },
  mounted() {
    this.getMyMeeting();
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.menu {
  font-family: "Do Hyeon", sans-serif;
  font-size: 21px;
  padding-left: 10px;
}
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.reviewbtn {
  font-family: "Jua", sans-serif;
}
.blinking {
  -webkit-animation: blink 1s ease-in-out infinite alternate;
  -moz-animation: blink 1s ease-in-out infinite alternate;
  animation: blink 1s ease-in-out infinite alternate;
}
@-webkit-keyframes blink {
  50% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
@-moz-keyframes blink {
  50% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
@keyframes blink {
  50% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
.progress {
  margin: 50px;
  padding: 50px;
}
</style>