<template>
  <v-container>
    <v-flex class="ma-auto my-5" lg9>
      <v-row v-if="chk">
        <v-col cols="4">
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
          <v-chip
            v-if="meeting.isPeriod == 0"
            :color="`green lighten-4 mr-1`"
            class="black--text"
            label
          >비정기</v-chip>
          <v-chip v-else :color="`green lighten-4 mr-1`" class="black--text" label>정기</v-chip>
        </v-col>
        <v-col cols="3" v-if="meeting.isPeriod == 0">
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label>모임날짜</v-chip>
          {{ meeting.meetingDate }}
        </v-col>
        <v-col cols="3" v-else>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label>모임주기</v-chip>
          {{ meeting.periodDate }}
        </v-col>
        <v-col cols="3">
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label>모임비</v-chip>
          <span v-if="meeting.unit == '원'">{{ meeting.fee }}</span>
          {{ meeting.unit }}
        </v-col>
        <v-col cols="2" style="text-align:right">
          <v-chip
            v-if="meeting.isActive == 1"
            :color="`blue lighten-4`"
            class="black--text"
            label
          >모집중</v-chip>
          <v-chip
            v-else-if="meeting.isActive == 2"
            :color="`green lighten-4`"
            class="black--text"
            label
          >진행중</v-chip>
          <v-chip v-else :color="`red lighten-4`" class="black--text" label>마감</v-chip>
        </v-col>
      </v-row>
      <v-divider></v-divider>
      <v-row>
        <v-col>
          <p class="contentstitle">{{ meeting.title }}</p>
        </v-col>
        <v-col cols="2" style="text-align:right">
          <v-btn icon v-if="meeting.checkUser != 0" @click="btnLike()">
            <v-icon v-if="meeting.isLike == 0">mdi-heart-outline</v-icon>
            <v-icon v-else color="red">mdi-heart</v-icon>
          </v-btn>
          <span class="topscore ma-auto">{{ meeting.likeCnt }}</span>
        </v-col>
        <v-col>
          <v-menu v-model="menu" bottom right transition="scale-transition" origin="top left">
            <template v-slot:activator="{ on }">
              <v-chip :color="`green lighten-4`" class="black--text" label pill v-on="on">
                <v-icon left color="orange">mdi-human-greeting</v-icon>
                인원 {{ meeting.nowPerson }}/{{ meeting.maxPerson }}
              </v-chip>
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
        </v-col>
        <v-col>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label>작성일</v-chip>
          {{ meeting.createdAt }}
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-img height="150" src="https://cdn.vuetifyjs.com/images/cards/cooking.png" />
        </v-col>
        <v-col>
          <v-row class="my-2">
            <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>지역</v-chip>
            {{ meeting.area1 }} {{ meeting.area2 }}
          </v-row>
          <v-row v-if="meeting.address" class="my-2">
            <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>상세 주소</v-chip>
            {{ meeting.address }}
          </v-row>
          <div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <div>
            <v-chip
              v-for="(tag, index) in meeting.tags"
              :key="tag"
              :color="colors[index]"
              class="black--text mr-2"
              label
              small
            >#{{ tag }}</v-chip>
          </div>
        </v-col>
        <v-col>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>참고 URL</v-chip>
          <a :href="meeting.refUrl">link</a>
        </v-col>
      </v-row>
      <v-row>
        <v-card outlined class="mx-4 pa-3" width="100%" height="100%">
          <span v-html="meeting.content" />
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
      menu: false,
      chk: false,
      seq: this.$route.params.seq,
      colors: ["lime", "amber", "orange", "teal", "indigo"],
      mapContainer: "",
      geocoder: "",
      map: "",
      marker: "",
      is_writer: false,
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
    btnLike() {
      let params = new URLSearchParams();
      params.append("meeting_id", this.seq);
      let config = {
        headers: { access_token: sessionStorage.getItem("token") }
      };
      http.post(`meeting/like`, params, config).then(response => {
        if (response.data.OK == "좋아요 취소") {
          this.meeting.isLike = 0;
          this.meeting.likeCnt--;
        } else {
          this.meeting.isLike = 1;
          this.meeting.likeCnt++;
        }
      });
    },
    getAttendant() {
      let config = {
        headers: {
          access_token: sessionStorage.getItem("token")
        }
      };
      http.get(`meeting/${this.seq}/attendant`, config).then(response => {
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
      });
    },
    dateParsing(beforeParsing) {
      const t = beforeParsing.indexOf("T");
      const afterParsing = beforeParsing.substring(0, t);
      // console.log(afterParsing)
      const realdate =
        afterParsing.substring(0, 4) +
        "년 " +
        afterParsing.substring(5, 7) +
        "월 " +
        (Number(afterParsing.substring(8, 11)) + 1) +
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
          access_token: sessionStorage.getItem("token")
        }
      };
      http.get(`meeting/${this.$route.params.seq}`, config).then(response => {
        let this_component = this;
        this.meeting = response.data;
        this.chk = true;
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
          this.meeting.meetingDate = this.dateParsing(this.meeting.meetingDate);
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
      });
    }
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.contentstitle {
  font-size: 20pt;
  font-family: "Jua", sans-serif;
}
</style>