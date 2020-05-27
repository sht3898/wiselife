<template>
  <v-container>
    <v-flex class="ma-auto mt-5" lg10>
      <survey-result />
      <div class="ma-auto" style="text-align:center;">
        <v-btn rounded class="infotext green lighten-2" @click="gobackhome">
          <strong>더 많은 여가생활 보러가기</strong>
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
</style>