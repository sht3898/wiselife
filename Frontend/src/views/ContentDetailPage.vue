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
            small
          >강좌</v-chip>
          <v-chip v-else :color="`green lighten-4 mr-1`" class="black--text" label small>모임</v-chip>
          <v-chip
            :color="`green lighten-4 mr-1`"
            class="black--text"
            label
            small
          >{{ categories[meeting.mainCategory] }}</v-chip>
          <v-chip
            v-if="meeting.isPeriod == 0"
            :color="`green lighten-4 mr-1`"
            class="black--text"
            label
            small
          >비정기</v-chip>
          <v-chip v-else :color="`green lighten-4 mr-1`" class="black--text" label small>정기</v-chip>
          <v-chip
            :color="`green lighten-4`"
            class="black--text"
            label
            small
          >인원 {{ meeting.nowPerson }}/{{ meeting.maxPerson }}</v-chip>
        </v-col>
        <v-col cols="3" v-if="meeting.isPeriod == 0">
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>모임날짜</v-chip>
          {{ meeting.meetingDate }}
        </v-col>
        <v-col cols="3" v-else>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>모임주기</v-chip>
          {{ meeting.periodDate }}
        </v-col>
        <v-col cols="3">
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>모임비</v-chip>
          {{ meeting.fee }} {{ meeting.unit }}
        </v-col>
        <v-col cols="2" style="text-align:right">
          <v-chip
            v-if="meeting.isActive == 1"
            :color="`blue lighten-4`"
            class="black--text"
            label
            small
          >모집중</v-chip>
          <v-chip
            v-else-if="meeting.isActive == 2"
            :color="`green lighten-4`"
            class="black--text"
            label
            small
          >진행중</v-chip>
          <v-chip v-else :color="`red lighten-4`" class="black--text" label small>마감</v-chip>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <p class="contentstitle">{{ meeting.title }}</p>
        </v-col>
        <v-col cols="2" style="text-align:right">
          <v-btn icon v-if="meeting.checkUser != 0">
            <span v-if="meeting.isLike == 0" style="font-size:20pt" class="mdi mdi-heart-outline"></span>
            <span v-else class="mdi mdi-heart" style="font-size:20pt; color:red;"></span>
          </v-btn>
        </v-col>
        <v-col>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>작성자</v-chip>
          {{ meeting.writer }}
        </v-col>
        <v-col>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>작성일</v-chip>
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
      chk: false,
      seq: this.$route.params.seq,
      colors: ["lime", "amber", "orange", "teal", "indigo"],
      mapContainer: "",
      geocoder: "",
      map: "",
      marker: "",
      is_writer: false,
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
      meeting: {
        title: "",
        writer: "",
        updatedAt: "",
        isPeriod: 0,
        meetingDate: "",
        periodDate: "",
        isClass: 0,
        maxPerson: 10,
        nowPerson: 5,
        content: "",
        refUrl: "",
        address: "",
        fee: 0,
        unit: "",
        isActive: 0,
        likeCnt: 0,
        viewCnt: 0,
        mainCategory: 0,
        tags: "",
        score: 0,
        phone: "",
        isLike: 0,
        checkUser: 0,
        area1: "",
        area2: ""
      }
    };
  },
  mounted() {
    this.init();
  },
  methods: {
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
      console.log("realdate: " + realdate);
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
      http
        .get(`meeting/detail/${this.$route.params.seq}`, config)
        .then(response => {
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
            this.meeting.meetingDate = this.meeting.meetingDate.substring(
              0,
              10
            );
          }
          this.meeting.createdAt = "2020-05-27T04:43:07.024+00:00";
          if (this.meeting.createdAt != null) {
            let date = this.meeting.createdAt.substring(0, 10);
            let time = this.meeting.createdAt.substring(11, 16);
            this.meeting.createdAt = date + " " + time;
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