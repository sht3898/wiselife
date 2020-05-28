<template>
  <v-container class="ma-auto">
    <v-card outlined class="pa-3 mb-2" width="100%" height="100%">
      <v-row>
        <v-col style="text-align:right">
          <span class="toptitle">평균 평점</span>
          <span class="topscore ml-2">{{ score }}</span>
        </v-col>
        <v-col>
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
        <v-col cols="5">
          <span class="toptitle">참여자 평균 연령</span>
          <span class="topscore ml-2">{{ avg_age }} 세</span>
        </v-col>
      </v-row>
    </v-card>
    <review-content v-for="review in reviews" :key="review.key" :review="review" />
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
      reviews:[],
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
          access_token: sessionStorage.getItem("token")
        }
      };
      http.get(`meeting/${this.seq}/attendant`, config).then(response => {
        let attendants = response.data.length;
        var sum_ages = 0;
        for (var i = 0; i < attendants; i++) {
          var date = new Date();
          var year = date.getFullYear();
          sum_ages += year - response.data[i].year + 1;
        }
        let avg_ages = sum_ages / attendants;
        this.avg_age = Math.floor(avg_ages);
      });
    },
    getReviews(){
      http
      .get(`review/list?meeting_id=`+this.seq)
      .then(response=>{
        this.reviews = response.data;
      })
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
  font-size: 15pt;
  font-family: "Jua", sans-serif;
}
</style>