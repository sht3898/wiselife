<template>
  <v-container>
    <v-flex class="ma-auto mt-5" lg10>
      <survey-result />
      <div class="ma-auto mb-10" style="text-align:center;">
        <v-btn rounded class="homebtn green lighten-1" style="color:white; font-size: 13pt;" @click="gobackhome">
        더 많은 여가생활 보러가기
        </v-btn>
      </div>
    </v-flex>
  </v-container>
</template>

<script>
import surveyResult from "../components/survey/SurveyResult";

export default {
  name: "SurveyResultPage",
  components: {
    surveyResult
  },
  data() {
    return {};
  },
  methods: {
    gobackhome() {
      this.$router.push("/");
      location.reload();
    }
  }
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
.homebtn {
  font-family: 'Jua', sans-serif;
}
</style>