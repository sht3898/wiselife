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
          <v-hover v-slot:default="{ hover }">
            <v-card
              v-for="meeting in attendmeetinglist"
              :key="meeting.meetingId"
              outlined
              :elevation="hover ? 5 : 0"
              class="my-1"
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
          </v-hover>
        </v-tab-item>
        <v-tab-item>
          <v-content
            v-if="pick == 0"
            class="my-10"
            style="text-align:center; color:grey; font-weight:bold"
          >작성할 강좌/모임을 선택해주세요!</v-content>
          <v-container v-else-if="pick != 0 && !wrote" class="ma-auto">
            <v-card outlined>
              <v-row>
                <v-col class="mt-2 text-center" cols="12" sm="6">
                  <span class="picktitle">{{ picktitle }}</span>
                </v-col>
                <v-col class="text-center" cols="12" sm="6">
                  <v-rating
                    v-model="rating"
                    color="yellow darken-3"
                    background-color="grey lighten-1"
                    empty-icon="$ratingFull"
                    half-increments
                    hover
                  ></v-rating>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <v-row>
                <v-col class="mt-3 pl-5" cols="12" sm="6">
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
                </v-col>
                <v-col class="pr-5" cols="12" sm="6">
                  <v-alert
                    class="mt-3"
                    outlined
                    type="warning"
                    dense
                    border="left"
                    style="font-size:9pt;"
                  >리뷰 이미지는 1장만 등록 가능합니다!</v-alert>
                </v-col>
              </v-row>
            </v-card>
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
          <v-container>
            <v-row>
              <v-col class="mt-2 pl-5" cols="12" sm="9">
                <span class="picktitle">{{ picktitle }}</span>
              </v-col>
              <v-col class="pl-5" cols="12" sm="3">
                <v-row>
                  <v-col  style="padding-top:10px" >
                    <v-rating
                      :value="picked.score"
                      background-color="white"
                      color="amber"
                      dense
                      half-increments
                      readonly
                      small                                           
                    ></v-rating>
                  </v-col>
                  <v-col>
                    <span class="topscore ml-1">{{ picked.score }}</span>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
            <v-row height="100px">
              <v-col cols="3">
                <v-card width="180" outlined>
                  <v-img :src="`http://k02b1051.p.ssafy.io`+ picked.imageUrl" />
                </v-card>
              </v-col>
              <v-col cols="9">
                <v-card outlined class="pa-3" width="100%" height="100%">{{ picked.content }}</v-card>
              </v-col>
            </v-row>
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
      picktitle: "",
      rating: 0,
      wrote: false,
      attendmeetinglist: [],
      reviewlist: [],
      picked: {},
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
    this.getMyMeeting();
    this.getMyReview();
  },
  methods: {
    pickMeeting(meeting) {
      this.pick = meeting.meetingId;
      this.picktitle = meeting.title;
      this.reviewContent = "";
      this.rating = 0;
      this.files = "";
      this.wrote = false;
      for (var i = 0; i < this.reviewlist.length; i++) {
        if (this.reviewlist[i].meetingId == this.pick) {
          this.picked = this.reviewlist[i];
          this.wrote = true;
          break;
        }
      }
      this.tab = 1;
    },
    getMyReview() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`review/user`, config)
        .then(response => {
          if (response.data.OK != null) {
            this.reviewlist = [];
          } else {
            this.reviewlist = response.data;
            console.log(response.data);
          }
        })
        .catch(() => {
          // alert("토큰 만료! 다시 로그인 해주세요!");
          // localStorage.clear();
          // this.$router.go();
        });
    },
    getMyMeeting() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`review/check`, config)
        .then(response => {
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
        })
        .catch(() => {
          alert("토큰 만료! 다시 로그인 해주세요!");
          localStorage.clear();
          this.$router.go();
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
      console.log(this.$refs);
      this.files = this.$refs.files.$refs.input.files[0];
    },
    writeReview() {
      if (this.reviewContent == "") {
        alert("내용을 입력해주세요.");
        return;
      }
      if (this.rating == 0) {
        alert("평점을 입력해주세요!");
        return;
      }
      if (this.files == "") {
        alert("이미지를 등록해주세요.");
        return;
      }
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      let formData = new FormData();
      // for (var i = 0; i < this.files.length; i++) {
      //   let file = this.files[i];
      // }
      formData.append("meetingId", this.pick);
      formData.append("content", this.reviewContent);
      formData.append("score", this.rating);
      formData.append("imageFile", this.files);

      for (var key of formData.keys()) {
        console.log(key);
      }

      for (var value of formData.values()) {
        console.log(value);
      }

      http
        .post("/review/write", formData, config)
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.log(error);
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
.picktitle {
  font-family: "Jua", sans-serif;
  font-size: 23px;
}
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.topscore {
  font-size: 15pt;
  font-family: "Jua", sans-serif;
}
</style>