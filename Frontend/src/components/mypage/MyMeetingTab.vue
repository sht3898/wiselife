<template>
  
    <v-flex class="ma-auto mt-5" lg11 xs12>
      <v-container fluid>
        <p class="menu">
          참여한 강좌/모임
          <span class="blinking" style="float:right">
            <v-btn rounded class="reviewbtn orange lighten-1" @click="insertReview">리뷰 작성 ✒️</v-btn>
          </span>
        </p>
        <attend-meeting-list :attendlist="this.attendmeetinglist"/>
      </v-container>
      <!-- 리뷰 modal -->
      <v-dialog v-model="dialog" max-width="800">
        <v-card>
          <v-toolbar width="800" style="position:absolute; z-index:2">
              
            <v-row class="menu py-4 px-3">
                <v-col>
              강좌/모임 Review
                </v-col>
                <v-col>
              <span style="text-align:right; float:right">
                <v-btn color="green darken-1" text @click="dialog = false">Close</v-btn>
              </span>
                </v-col>
            </v-row>
          </v-toolbar>
          <review-modal style="padding-top:70px;" />
        </v-card>
      </v-dialog>

      <v-container fluid mb-12>
        <p class="menu">등록한 강좌/모임</p>
        <create-meeting-list :createlist="this.createmeetinglist"/>
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
      createmeetinglist: []
    };
  },
  methods: {
    insertReview() {
      this.dialog = true;
    },
    getMyMeeting(){
       let config = {
        headers: {
          access_token: sessionStorage.getItem("token")
        }
      };
      http
      .get(`meeting/list`, config)
      .then(response => {
        console.log(response);

        
        this.attendmeetinglist = response.data.참여;
         for (var i = 0; i < this.attendmeetinglist.length; i++) {
            this.attendmeetinglist[i].tags = this.attendmeetinglist[i].tags.split(" ");
          }
        this.createmeetinglist = response.data.등록;
         for (var i = 0; i < this.createmeetinglist.length; i++) {
            this.createmeetinglist[i].tags = this.createmeetinglist[i].tags.split(" ");
          }

        console.log(this.attendmeetinglist);
        console.log(this.createmeetinglist);
      })
    }
  },
  mounted(){
    this.getMyMeeting();
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.menu {
  font-family: "Do Hyeon", sans-serif;
  font-size: 23px;
  padding-left: 10px;
}
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
.reviewbtn {
  font-family: 'Jua', sans-serif;
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
</style>