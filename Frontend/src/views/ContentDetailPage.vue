<template>
  <v-container>
    <v-flex class="ma-auto my-5" lg9>
      <v-row>
        <v-col cols="4">
          <v-chip :color="`green lighten-4 mr-1`" class="black--text" label small>모임</v-chip>
          <v-chip :color="`green lighten-4 mr-1`" class="black--text" label small>놀이/게임</v-chip>
          <v-chip :color="`green lighten-4 mr-1`" class="black--text" label small>비정기</v-chip>
          <v-chip :color="`green lighten-4`" class="black--text" label small>인원 2/4</v-chip>
        </v-col>
        <v-col cols="3">
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>모임날짜</v-chip>2020-05-22
        </v-col>
        <v-col cols="3">
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>모임비</v-chip>16,000원
        </v-col>
        <v-col cols="2" style="text-align:right">
          <v-chip :color="`blue lighten-4`" class="black--text" label small>모집중</v-chip>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <p class="contentstitle">제목</p>
        </v-col>
        <v-col style="text-align:right">
          <v-btn icon>
            <span style="font-size:20pt" class="mdi mdi-heart-outline"></span>
          </v-btn>
        </v-col>
        <v-col>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>작성자</v-chip>안지연
        </v-col>
        <v-col>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>작성일</v-chip>2020-05-21 13:20
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-img height="150" src="https://cdn.vuetifyjs.com/images/cards/cooking.png" />
        </v-col>
        <v-col>
          <div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <div>
            <v-chip :color="`orange lighten-4`" class="black--text mr-2" label small>#슬기로운</v-chip>#방탈출 #둔산동 #주말
          </div>
        </v-col>
        <v-col>
          <v-chip :color="`grey lighten-4`" class="black--text mr-2" label small>참고 URL</v-chip>
          <a href="http://edu.ssafy.com">link</a>
        </v-col>
      </v-row>
      <v-row>
        <v-card outlined class="mx-4 pa-3" width="100%" height="100%">내용</v-card>
      </v-row>
    </v-flex>

    <v-flex class="ma-auto my-5" lg9>
      <contents-detail-tab></contents-detail-tab>
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
      mapContainer: "",
      geocoder: "",
      map: "",
      marker: "",
      is_writer: false,
      meeting: {
        uid: 0,
        meeting_id: 0,
        writer: "",
        main_category: "",
        title: "",
        tags: "", // 해시태그 띄어쓰기로 구분 (#붙여서 보내기!)
        is_period: 0,
        meeting_date: new Date().toISOString().substr(0, 10),
        period_date: "",
        is_class: 0,
        max_person: 0,
        content: "",
        ref_url: "",
        area1: "",
        area2: "",
        address: "",
        phone: "",
        fee: 0, // 회비 또는 금액을 작성시 숫자를 입력하도록
        unit: "", // 단위 : 원/미정/회비,
        is_active: 0,
        like_cnt: 0,
        view_cnt: 0,
        score: 0.0
      }
    };
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

      http
        .get(`meeting/detail/${this.$route.params.seq}?uid=`+1363992434)
        .then(response => {
          console.log(response);
          const seq = this.$route.params.seq;
          alert(response.data.seq);
          if (response.data.status) {
            let this_component = this;
            response.data.meeting = this.meeting;
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
            }
            Kakao.API.request({
              success: function(response) {
                console.log(response);
                if (this_component.meeting.uid == response.id) {
                  this_component.is_writer = true;
                }
              },
              fail: function(error) {
                console.log(error);
                alert("회원 정보를 가져오는데 실패했습니다.");
              }
            });
          }
        });
      this.mapContainer = mapContainer;
      this.geocoder = geocoder;
      this.map = map;
      this.marker = marker;
    }
  },
  mounted() {
    this.init();
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