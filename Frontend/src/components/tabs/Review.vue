<template>
  <v-container class="ma-auto">
    <v-card outlined class="pa-3 mb-2" width="100%" height="100%">
      <v-row>
        <v-col style="text-align:right">
          <span class="toptitle">평균 평점</span>
          <span class="topscore ml-2">3.2</span>
        </v-col>
        <v-col>
          <v-rating
            :value="3.2"
            background-color="white"
            color="amber"
            dense
            half-increments
            readonly
            medium
          />
        </v-col>
        <v-col cols="5">
          <span class="toptitle">참여자 평균 연령대</span>
          <span class="topscore ml-2">20대</span>
        </v-col>
      </v-row>
    </v-card>
    <review-content />
  </v-container>
</template>
<script>
import ReviewContent from "./ReviewContent";
export default {
  name: "Review",
  components: {
    ReviewContent
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