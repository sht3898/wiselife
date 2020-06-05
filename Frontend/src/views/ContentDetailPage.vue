<template>
  <v-container>
    <v-flex class="ma-auto my-5" lg9>
      <v-row v-if="chk">
        <v-col>
          <v-chip
            v-if="meeting.isClass == 1"
            :color="`green lighten-4 mr-1`"
            class="black--text"
            label
          >강좌</v-chip>
          <v-chip v-else :color="`green lighten-4 mr-1`" class="black--text" label>모임</v-chip>
          <v-chip
            :color="`green lighten-4 mr-1`"
            class="black--text"
            label
          >{{ categories[meeting.mainCategory] }}</v-chip>
        </v-col>
        <v-col style="text-align:right">
          <div v-if="meeting.checkUser == 0">
            <v-tooltip top>
              <template v-slot:activator="{ on }">
                <v-chip
                  v-if="meeting.isActive == 1"
                  :color="`blue lighten-4`"
                  class="blinking black--text mr-3"
                  label
                  v-on="on"
                  @click="btnActive(2)"
                >모집중</v-chip>
                <v-chip
                  v-else-if="meeting.isActive == 2"
                  :color="`green lighten-4`"
                  class="blinking black--text mr-3"
                  label
                  v-on="on"
                  @click="btnActive(0)"
                >진행중</v-chip>
                <v-chip
                  v-else
                  :color="`red lighten-4`"
                  class="blinking black--text mr-3"
                  label
                  v-on="on"
                  @click="btnActive(1)"
                >마감</v-chip>
              </template>
              <span style="font-size:8pt;">상태를 변경하려면 클릭하세요!</span>
            </v-tooltip>
          </div>
          <div v-else>
            <v-chip
              v-if="meeting.isActive == 1"
              :color="`blue lighten-4`"
              class="black--text mr-3"
              label
            >모집중</v-chip>
            <v-chip
              v-else-if="meeting.isActive == 2"
              :color="`green lighten-4`"
              class="black--text mr-3"
              label
            >진행중</v-chip>
            <v-chip v-else :color="`red lighten-4`" class="black--text mr-3" label>마감</v-chip>
            <v-chip
              v-if="meeting.checkUser==1 && meeting.nowPerson != meeting.maxPerson"
              :color="`teal lighten-4`"
              class="black--text"
              label
              @click="attendMeeting()"
            >신청하기</v-chip>
            <v-chip
              v-if="meeting.checkUser==1 && meeting.nowPerson == meeting.maxPerson"
              :color="`teal lighten-4`"
              class="black--text"
              label
              disabled
              @click="attendMeeting()"
            >신청하기</v-chip>
            <v-chip
              v-if="meeting.checkUser==2"
              color="warning"
              class="black--text"
              label
              @click="attendMeeting()"
            >탈퇴하기</v-chip>
          </div>
        </v-col>
      </v-row>
      <v-divider></v-divider>
      <v-row>
        <v-col cols="12">
          <v-card outlined class="ma-auto">
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title class="contentstitle">{{ meeting.title }}</v-list-item-title>
              </v-list-item-content>
              <v-btn icon @click="btnLike()">
                <v-icon v-if="meeting.isLike == 0">mdi-heart-outline</v-icon>
                <v-icon v-else color="red">mdi-heart</v-icon>
              </v-btn>
              <span class="topscore ma-auto">{{ meeting.likeCnt }}</span>
              <span class="mdi mdi-eye-outline ml-4" style="color:grey"></span>
              <span class="grey--text ml-2">{{ meeting.viewCnt }}</span>
              <!-- 카톡링크전송 -->
              <span class="mx-4 mt-2">
                <a
                  href="javascript:;"
                  @click="kakaotalklink"
                  id="kakao-link-btn"
                  style="width:30px; height:auto"
                >
                  <!-- 버튼이 생기는 부분, id는 맘대로 쓰시되 아래 js 코드도 동일하게 적용해주셔야 합니다. -->
                  <img
                    src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
                    style="width:30px; height:auto"
                  />
                </a>
              </span>

              <div v-if="meeting.checkUser==0">
                <v-btn
                  class="contentbtn mr-3"
                  color="green lighten-2"
                  rounded
                  small
                  @click="updateContent"
                  style="font-size: 12pt;"
                >수정</v-btn>
                <v-btn
                  class="contentbtn"
                  rounded
                  small
                  style="font-size: 12pt;"
                  @click="deleteContent"
                >삭제</v-btn>
              </div>
            </v-list-item>

            <div v-if="chk && meeting.meetingImages.length != 0">
              <v-carousel cycle height="400" hide-delimiter-background show-arrows-on-hover>
                <v-carousel-item v-for="(image, i) in meeting.meetingImages" :key="i">
                  <v-img v-if="isUrl" :src="meeting.meetingImages[i]" class="imgs" />
                  <v-img
                    v-else
                    :src="`http://k02b1051.p.ssafy.io`+ meeting.meetingImages[i]"
                    class="imgs"
                  />
                </v-carousel-item>
              </v-carousel>
            </div>
            <v-card-text class="text--primary">
              <v-row class="ml-2">
                <v-col cols="12" sm="8">
                  <v-chip
                    v-for="(tag, index) in meeting.tags"
                    :key="tag"
                    :color="`${colors[index]} lighten-3`"
                    class="black--text mr-2 my-1"
                    label
                    small
                  >#{{ tag }}</v-chip>
                </v-col>
                <v-col style="text-align:right; color:LightGrey" cols="12" sm="4">
                  <span class="mdi mdi-pencil ml-4" style="color:Grey">작성일</span>
                  <span class="grey--text ml-2">{{ meeting.createdAt }}</span>
                </v-col>
              </v-row>
              <v-row class="ml-2">
                <v-col cols="12" sm="6">
                  <v-row class="my-3">
                    <v-chip :color="`grey lighten-4`" class="black--text mr-3" label small>
                      <span class="mdi mdi-dark mdi-clock mr-1" style="font-size:15pt;" />
                      날짜/주기
                    </v-chip>
                    <span v-if="meeting.isPeriod == 1">
                      <span style="color:grey;">(정기)</span>
                      <span class="ml-2" style="font-weight:bold;">{{ meeting.periodDate }}</span>
                    </span>
                    <span v-else>
                      <span style="color:grey;">(비정기)</span>
                      <span class="ml-2" style="font-weight:bold;">{{ meeting.meetingDate }}</span>
                    </span>
                  </v-row>
                  <v-row class="my-3">
                    <v-chip :color="`grey lighten-4`" class="black--text mr-3" label small>
                      <span class="mdi mdi-dark mdi-cash-100 mr-1" style="font-size:15pt;" />
                      &ensp;모임비&ensp;
                    </v-chip>
                    <span v-if="meeting.unit == '원'" style="font-weight:bold;">{{ meeting.fee }}</span>
                    <span class="ml-1" style="font-weight:bold;">{{ meeting.unit }}</span>
                  </v-row>
                  <v-row class="my-3">
                    <v-chip :color="`grey lighten-4`" class="black--text mr-3" label small>
                      <span class="mdi mdi-dark mdi-face mr-1" style="font-size:15pt;" />
                      모임 정원
                    </v-chip>
                    <span class="ml-2" style="font-weight:bold;">총</span>
                    <span
                      class="ml-1"
                      style="font-weight:bold; color:CornflowerBlue;"
                    >{{ meeting.maxPerson }}</span>
                    <span style="font-weight:bold;">명 |</span>
                    <span
                      class="ml-1"
                      style="font-weight:bold; color:CornflowerBlue;"
                    >{{ (meeting.maxPerson - meeting.nowPerson) }}</span>
                    <span v-if="meeting.isActive == 1" style="font-weight:bold;">명 신청가능</span>
                    <span v-else-if="meeting.isActive == 2" style="font-weight:bold;">명 진행 중</span>
                    <span v-else style="font-weight:bold;">명 참여 중</span>
                    <v-menu
                      v-model="menu"
                      bottom
                      right
                      transition="scale-transition"
                      origin="top left"
                    >
                      <template v-slot:activator="{ on }">
                        <v-chip
                          :color="`green lighten-4`"
                          class="black--text ml-3"
                          label
                          small
                          pill
                          v-on="on"
                        >참여자 보기</v-chip>
                      </template>
                      <v-card width="300">
                        <v-list>
                          <v-list-item>
                            <v-list-item-avatar>
                              <v-img :src="writer.profileImage"></v-img>
                            </v-list-item-avatar>
                            <v-list-item-content>
                              <v-list-item-title>호스트 : {{ writer.username }}</v-list-item-title>
                              <v-list-item-subtitle>문의 : {{ meeting.phone }}</v-list-item-subtitle>
                            </v-list-item-content>
                            <v-list-item-action>
                              <v-btn icon @click="menu = false">
                                <v-icon>mdi-close-circle</v-icon>
                              </v-btn>
                            </v-list-item-action>
                          </v-list-item>
                        </v-list>
                        <v-divider />
                        <v-list>
                          <v-list-item-subtitle class="ml-3">참여자 목록</v-list-item-subtitle>
                          <v-list-item v-for="attendant in attendants" :key="attendant.uid">
                            <v-list-item-avatar>
                              <v-img :src="attendant.profileImage"></v-img>
                            </v-list-item-avatar>
                            <v-list-item-subtitle>{{ attendant.username }} ({{ attendant.gender }} / {{ attendant.ages }}대)</v-list-item-subtitle>
                          </v-list-item>
                        </v-list>
                      </v-card>
                    </v-menu>
                  </v-row>
                  <v-row>
                    <v-chip :color="`grey lighten-4`" class="black--text mr-3" label small>
                      <span class="mdi mdi-dark mdi-link mr-1" style="font-size:15pt;" />참고 URL
                    </v-chip>
                    <a :href="meeting.refUrl" target="_blank">link</a>
                  </v-row>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-row class="my-1">
                    <v-chip :color="`grey lighten-4`" class="black--text mx-2" label small>지역</v-chip>
                    <span
                      style="font-weight:bold; font-size:1em;"
                    >{{ meeting.area1 }} {{ meeting.area2 }}</span>
                  </v-row>
                  <v-row class="my-1">
                    <v-chip :color="`grey lighten-4`" class="black--text mx-2" label small>상세 주소</v-chip>
                    <span style="font-weight:bold; font-size:1em;">{{ meeting.address }}</span>
                  </v-row>
                  <div
                    id="map"
                    style="max-width:400px; max-height:200px; width:100vw; height:30vw;margin-top:10px;display:none"
                  ></div>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-card outlined class="mx-4 pa-3" width="100%" height="100%">
          <span class="vhtml" v-html="meeting.content" />
        </v-card>
      </v-row>
    </v-flex>

    <v-flex class="ma-auto my-5" lg9>
      <contents-detail-tab :seq="seq" :score="meeting.score"></contents-detail-tab>
    </v-flex>
  </v-container>
</template>
<script>
import http from "../http-common";
import ContentsDetailTab from "@/components/tabs/ContentsDetailTab";

export default {
  name: "ContentDetailPage",
  components: {
    ContentsDetailTab
  },
  data() {
    return {
      active_list: [{ title: "마감" }, { title: "모집" }, { title: "진행" }],
      menu: false,
      chk: false,
      isUrl: false,
      seq: this.$route.params.seq,
      colors: ["red", "amber", "lime", "teal", "indigo"],
      mapContainer: "",
      geocoder: "",
      map: "",
      marker: "",
      writer: {},
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
      ],
      meeting: {},
      attendants: []
    };
  },
  mounted() {
    this.init();
    this.getAttendant();
  },
  methods: {
    btnActive(active) {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      let params = new URLSearchParams();
      params.append("meeting_id", this.seq);
      params.append("isActive", active);
      http
        .put(`meeting/update/isActive`, params, config)
        .then(() => {
          switch (active) {
            case 0:
              this.meeting.isActive = 0;
              break;
            case 1:
              this.meeting.isActive = 1;
              break;
            case 2:
              this.meeting.isActive = 2;
              break;
          }
        })
        .catch(error => {
          alert("hi");
          console.log(error);
          alert(error);
        });
    },
    updateContent() {
      this.$router.push("/contentupdate/" + this.seq);
    },
    deleteContent() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      if (confirm("삭제하시겠습니까?") == true) {
        http
          .delete(`meeting/delete?meeting_id=` + this.seq, config)
          .then(response => {
            console.log(response);
            alert("삭제되었습니다.");
            this.$router.push("/");
          });
      }
    },
    kakaotalklink() {
      // // 사용할 앱의 JavaScript 키를 설정해 주세요.
      // Kakao.cleanup();
      // Kakao.init("c2d9f09a902e77b8550b754cdb90d407");
      // // 카카오링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.
      Kakao.Link.createDefaultButton({
        container: "#kakao-link-btn", // 컨테이너는 아까 위에 버튼이 쓰여진 부분 id
        objectType: "feed",
        content: {
          // 여기부터 실제 내용이 들어갑니다.
          title: this.meeting.title, // 본문 제목
          // description: this.meeting.tags, // 본문 바로 아래 들어가는 영역?
          imageUrl:
            "http://k02b1051.p.ssafy.io" + this.meeting.meetingImages[0], // 이미지
          link: {
            webUrl:
              "http://k02b1051.p.ssafy.io/contentdetail/" +
              this.$route.params.seq,
            mobileWebUrl:
              "http://k02b1051.p.ssafy.io/contentdetail/" +
              this.$route.params.seq,
            androidExecParams:
              "http://k02b1051.p.ssafy.io/contentdetail/" +
              this.$route.params.seq,
            iosExecParams:
              "http://k02b1051.p.ssafy.io/contentdetail/" +
              this.$route.params.seq
          }
        },
        social: {
          /* 공유하면 소셜 정보도 같이 줄 수 있는데, 이 부분은 기반 서비스마다 적용이 쉬울수도 어려울 수도 있을듯 합니다. 전 연구해보고 안되면 제거할 예정 (망할 google  blogger...) */
          likeCount: this.meeting.likeCnt
          // commentCount: 45,
          // sharedCount: 845
        },
        buttons: [
          {
            title: "슬기로운 여가생활로 이동",
            link: {
              webUrl:
                "http://k02b1051.p.ssafy.io/contentdetail/" +
                this.$route.params.seq,
              mobileWebUrl:
                "http://k02b1051.p.ssafy.io/contentdetail/" +
                this.$route.params.seq,
              androidExecParams:
                "http://k02b1051.p.ssafy.io/contentdetail/" +
                this.$route.params.seq,
              iosExecParams:
                "http://k02b1051.p.ssafy.io/contentdetail/" +
                this.$route.params.seq
            }
          }
        ]
      });
    },
    attendMeeting() {
      let params = new URLSearchParams();
      params.append("meeting_id", this.seq);
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .post(`meeting/attend`, params, config)
        .then(response => {
          if (response.data.OK == "참가 신청 완료") {
            this.meeting.checkUser = 2;
            this.getAttendant();
            this.meeting.nowPerson++;
          } else {
            this.meeting.checkUser = 1;
            this.getAttendant();
            this.meeting.nowPerson--;
          }
          // console.log(response);
        })
        .catch(error => {
          alert(error);
        });
    },
    btnLike() {
      let params = new URLSearchParams();
      params.append("meeting_id", this.seq);
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .post(`meeting/like`, params, config)
        .then(response => {
          if (response.data.OK == "좋아요 취소") {
            this.meeting.isLike = 0;
            this.meeting.likeCnt--;
          } else {
            this.meeting.isLike = 1;
            this.meeting.likeCnt++;
          }
          // console.log(response);
        })
        .catch(error => {
          alert(error);
        });
    },
    getAttendant() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      this.attendants = [];
      http
        .get(`meeting/${this.seq}/attendant`, config)
        .then(response => {
          for (var i = 0; i < response.data.length; i++) {
            if (response.data[i].checkUser == 0) {
              this.writer = response.data[i];
            }
            this.attendants.push(response.data[i]);
            if (this.attendants[i].gender == 1) {
              this.attendants[i].gender = "남";
            } else {
              this.attendants[i].gender = "여";
            }
          }
        })
        .catch(error => {
          alert(error);
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
    init() {
      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
          level: 5 // 지도의 확대 레벨
        };

      //지도를 미리 생성
      var map = new daum.maps.Map(mapContainer, mapOption);
      //주소-좌표 변환 객체를 생성
      var geocoder = new daum.maps.services.Geocoder();
      //마커를 미리 생성
      var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map
      });
      this.mapContainer = mapContainer;
      this.geocoder = geocoder;
      this.map = map;
      this.marker = marker;

      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`meeting/${this.$route.params.seq}`, config)
        .then(response => {
          console.log(response.data);
          let this_component = this;
          this.meeting = response.data;
          this.host = response.data.writer;
          this.chk = true;
          console.log(this.meeting);
          //주소 있으면 지도 찍자!
          if (this.meeting.address != null) {
            this.geocoder.addressSearch(this.meeting.address, function(
              results,
              status
            ) {
              // 정상적으로 검색이 완료됐으면
              if (status === daum.maps.services.Status.OK) {
                var result = results[0]; //첫번째 결과의 값을 활용

                // 해당 주소에 대한 좌표를 받아서
                var coords = new daum.maps.LatLng(result.y, result.x);
                // 지도를 보여준다.
                this_component.mapContainer.style.display = "block";
                this_component.map.relayout();
                // 지도 중심을 변경한다.
                this_component.map.setCenter(coords);
                // 마커를 결과값으로 받은 위치로 옮긴다.
                this_component.marker.setPosition(coords);
              }
            });
          } else {
            this.mapContainer = null;
          }

          ///////데이터 정제////////
          if (this.meeting.fee != null) {
            this.meeting.fee = this.meeting.fee.toLocaleString();
          }
          if (this.meeting.meetingDate != null) {
            this.meeting.meetingDate = this.dateParsing(
              this.meeting.meetingDate
            );
          }
          if (this.meeting.createdAt != null) {
            this.meeting.createdAt = this.dateParsing(this.meeting.createdAt);
          }
          if (this.meeting.tags != null) {
            let split_tags = this.meeting.tags.split(" ");
            let tags = [];
            for (var i in split_tags) {
              tags.push(split_tags[i]);
            }
            this.meeting.tags = tags;
          }
          if (this.meeting.meetingImages.length != 0) {
            if (this.meeting.meetingImages[0].substring(2, 3) == "t") {
              if (this.meeting.meetingImages[0].substring(0, 1) == '"') {
                let length = this.meeting.meetingImages[0].length;
                this.meeting.meetingImages[0] = this.meeting.meetingImages[0].substring(
                  1,
                  length - 1
                );
              }
              this.meeting.meetingImages = this.meeting.meetingImages[0].split(
                " "
              );
              if (this.meeting.meetingImages[0].substring(0, 1) == "h") {
                this.isUrl = true;
              }
            }
          }
        })
        .catch(error => {
          alert(error);
        });
    }
  }
};
</script>
<style lang="css" scoped>
.vhtml >>> .ql-align-center {
  text-align: center;
}
.vhtml >>> .ql-align-left {
  text-align: left;
}
.vhtml >>> .ql-align-right {
  text-align: right;
}

@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.contentstitle {
  font-size: 20pt;
  font-family: "Jua", sans-serif;
}
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.contentbtn {
  font-family: "Nanum Pen Script", cursive;
}

.imgs {
  width: auto !important;
  max-height: 100%;
}
.blinking {
  -webkit-animation: blink 3s ease-in-out infinite alternate;
  -moz-animation: blink 3s ease-in-out infinite alternate;
  animation: blink 3s ease-in-out infinite alternate;
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