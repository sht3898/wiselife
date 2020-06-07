<template>
  <v-container class="ma-auto">
    <v-card outlined class="pa-1 mb-2" width="100%" height="100%">
      <v-row>
        <v-col class="mx-0 px-0" cols="12" sm="6">
          <v-row class="ma-auto">
            <v-col cols="4">
              <span class="toptitle mt-1" style="text-align:right; float:right">평균 평점</span>
            </v-col>
            <v-col v-if="score==0" class="toptitle mt-1">
              <span style="font-size:10pt; color:dimgrey">아직 등록된 리뷰가 없습니다</span>
            </v-col>
            <v-col v-else class="toptitle ma-0 pa-0">
              <v-row class="mx-0 px-0">
                <v-col class="mx-0 px-0 mt-1">
                  <v-rating
                    :value="score"
                    background-color="white"
                    color="amber"
                    dense
                    half-increments
                    readonly
                    medium
                  />
                </v-col>
                <v-col class="mx-0 px-0 mt-1">
                  <span class="topscore">{{ score }}</span>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="12" sm="6" class="mt-4 text-center">
          <span class="toptitle">참여자 평균 연령</span>
          <span class="topscore ml-2">{{ avg_age }} 세</span>
        </v-col>
      </v-row>
    </v-card>
    <p>
      * 리뷰는 참여자만 등록 가능합니다! 리뷰 작성은
      <router-link to="/mypage">My Page > My Meeting</router-link>에서 진행해주세요!
    </p>
    <div v-if="(reviews.length > 0)">
      <review-content v-for="review in reviews" :key="review.key" :review="review" />
    </div>
  </v-container>
</template>
<script>
import http from "../../http-common.js";
import ReviewContent from "./ReviewContent";

export default {
  name: "Review",
  components: {
    ReviewContent
  },
  props: {
    seq: { type: String, default: "" },
    score: { type: Number, default: 0.0 }
  },
  data() {
    return {
      avg_age: "",
      reviews: []
    };
  },
  mounted() {
    this.getAttendant();
    this.getReviews();
  },
  methods: {
    getAttendant() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`meeting/${this.seq}/attendant`, config)
        .then(response => {
          let attendants = response.data.length;
          var sum_ages = 0;
          for (var i = 0; i < attendants; i++) {
            var date = new Date();
            var year = date.getFullYear();
            sum_ages += year - response.data[i].year + 1;
          }
          let avg_ages = sum_ages / attendants;
          this.avg_age = Math.floor(avg_ages);
        })
        .catch(() => {
          alert("토큰 만료! 다시 로그인 해주세요!");
          localStorage.clear();
          this.$router.go();
        });
    },
    getReviews() {
      http.get(`review/list?meeting_id=` + this.seq).then(response => {
        this.reviews = response.data;
      });
    }
  }
};
</script>
<style scoped>
.toptitle {
  font-size: 12pt;
}
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.topscore {
  font-size: 13pt;
  font-family: "Jua", sans-serif;
}
</style>