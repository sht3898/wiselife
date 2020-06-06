<template>
  <v-container>
    <v-flex class="ma-auto">
      <v-tabs v-model="tab" grow color="green">
        <v-tab>
          <v-icon left>mdi-playlist-star</v-icon>작성 가능한 참여 리스트
        </v-tab>
        <v-tab>
          <v-icon left>mdi-pencil-circle-outline</v-icon>리뷰
        </v-tab>
      </v-tabs>
      <v-tabs-items v-model="tab">
        <v-tab-item style="font-size:9pt;">
          <v-row class="ma-auto" style="text-align:center;">
            <v-col cols="1.5">카테고리</v-col>
            <v-col cols="3.5">강좌/모임명</v-col>
            <v-col cols="1.5">호스트</v-col>
            <v-col cols="2.5">지역</v-col>
            <v-col cols="1.5">모임 유형</v-col>
            <v-col cols="2.5">모임 날짜</v-col>
          </v-row>
          <v-card
            v-for="meeting in attendmeetinglist"
            :key="meeting.meetingId"
            @click="pickMeeting(meeting)"
          >
            <v-row class="ma-auto" style="text-align:center;">
              <v-col cols="1.5">{{ meeting.mainCategory }}</v-col>
              <v-col cols="3.5">{{ meeting.title }}</v-col>
              <v-col cols="1.5">{{ meeting.writer }}</v-col>
              <v-col cols="2.5">{{ meeting.area1 }} {{ meeting.area2 }}</v-col>
              <v-col cols="1.5">{{ meeting.isClass }}</v-col>
              <v-col v-if="meeting.isPeriod == '정기'" cols="2.5">{{ meeting.periodDate }}</v-col>
              <v-col v-else cols="2.5">{{ meeting.meetingDate }}</v-col>
            </v-row>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-content
            v-if="pick == 0"
            class="my-10"
            style="text-align:center; color:grey; font-weight:bold"
          >작성할 강좌/모임을 선택해주세요!</v-content>
          <v-container v-else-if="pick != 0 && !wrote" class="pa-3 mt-5">
            <v-file-input
              :rules="rules"
              accept="image/png, image/jpeg, image/bmp"
              placeholder="이미지 첨부"
              prepend-icon="mdi-camera-enhance"
              outlined
              dense
              id="files"
              ref="files"
              v-on:change="handleFilesUploads()"
            ></v-file-input>
            <p>* 리뷰 이미지는 1장만 등록 가능합니다!</p>

            <v-rating
              v-model="attendmeetinglist.rating"
              color="yellow darken-3"
              background-color="grey darken-1"
              empty-icon="$ratingFull"
              half-increments
              hover
            ></v-rating>

            <v-textarea
              v-model="reviewContent"
              background-color="amber lighten-4"
              color="orange orange-darken-4"
              outlined
              placeholder="리뷰를 작성해주세요!"
            ></v-textarea>

            <div class="pb-5" style="text-align:right">
              <v-btn
                class="reviewbtn mr-3"
                color="green lighten-2"
                rounded
                small
                @click="writeReview()"
                style="font-size: 12pt;"
              >등록</v-btn>
              <!-- <v-btn class="reviewbtn" rounded small style="font-size: 12pt;">삭제</v-btn> -->
            </div>
          </v-container>
        </v-tab-item>
      </v-tabs-items>
    </v-flex>
  </v-container>
</template>

<script>
import http from "../../http-common";
export default {
  name: "ReviewModal",
  data() {
    return {
      tab: null,
      pick: 0,
      wrote: false,
      expanded: [],
      singleExpand: false,
      headers: [
        { text: "카테고리", value: "mainCategory" },
        {
          text: "강좌/모임명",
          align: "start",
          value: "title"
        },
        { text: "호스트", value: "writer" },
        { text: "모임 유형", value: "isPeriod" },
        { text: "모임 날짜", value: "meetingDate" },

        { text: "", value: "data-table-expand" }
      ],
      attendmeetinglist: [],
      reviewContent: "",
      files: "",
      rules: [
        value =>
          !value || value.size < 3000000 || "이미지는 3 MB 이하로 등록해주세요!"
      ],
      categories: [
        "전체",
        "레저/스포츠",
        "요리",
        "수공예/공방",
        "놀이/게임",
        "문화",
        "예술",
        "축제/행사",
        "기타"
      ]
    };
  },
  mounted() {
    this.getMymeeting();
  },
  methods: {
    pickMeeting(meeting) {
      this.pick = meeting.meetingId;
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`review/list/meeting_id=` + meeting.meetingId, config)
        .then(response => {
          console.log(response);
          if(response.data.OK != null){
            this.wrote = false;
          }else{
            this.wrote = true;
          }
          this.tab = 1;
        })
        .catch(() => {});
    },
    getMymeeting() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http.get(`review/check`, config).then(response => {
        console.log(response);

        this.attendmeetinglist = response.data;
        for (var i = 0; i < this.attendmeetinglist.length; i++) {
          if (this.attendmeetinglist[i].isPeriod == "0") {
            this.attendmeetinglist[i].isPeriod = "비정기";
          } else {
            this.attendmeetinglist[i].isPeriod = "정기";
          }
          if (this.attendmeetinglist[i].isClass == "0") {
            this.attendmeetinglist[i].isClass = "모임";
          } else {
            this.attendmeetinglist[i].isClass = "강좌";
          }
          this.attendmeetinglist[i].mainCategory = this.categories[
            this.attendmeetinglist[i].mainCategory
          ];
          if (this.attendmeetinglist[i].meetingDate != "") {
            this.attendmeetinglist[i].meetingDate = this.dateParsing(
              this.attendmeetinglist[i].meetingDate
            );
          }
        }
      });
    },
    dateParsing(beforeParsing) {
      const t = beforeParsing.indexOf("T");
      const afterParsing = beforeParsing.substring(0, t);
      const realdate =
        afterParsing.substring(0, 4) +
        "년 " +
        afterParsing.substring(5, 7) +
        "월 " +
        afterParsing.substring(8, 10) +
        "일";
      return realdate;
    },
    handleFilesUploads() {
      this.files = this.$refs.files.files;
    },
    writeReview() {
      if (this.reviewContent == "") {
        alert("내용을 입력해주세요.");
        return;
      }
      if (this.files == "") {
        alert("이미지를 등록해주세요.");
        return;
      }
      let formData = new FormData();
      for (var i = 0; i < this.files.length; i++) {
        let file = this.files[i];
        formData.append("files", file);
      }
      formData.append("budget_num", this.budgetInfo.budget_num);
      // alert("budget_num: " + this.budgetInfo.budget_num);
      formData.append("reviewContent", this.reviewContent);
      // alert("reviewContent: " + this.reviewContent);
      http
        .post("/review", formData)
        .then(response => {
          console.log(response);
          // console.log("SUCCESS!!");
          this.$router.push({ name: "review" });
          // console.log(response);
          // this.result = response.;
        })
        .catch(error => {
          console.log(error);
          // console.log("FAILURE!!");
        });
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.reviewbtn {
  font-family: "Nanum Pen Script", cursive;
}
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.contenttitle {
  font-size: 15pt;
  font-family: "Jua", sans-serif;
  /* 한 줄 자르기 */
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 여러 줄 자르기 추가 스타일 */
  white-space: normal;
  line-height: 1.2;
  height: 3.6em;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
</style>