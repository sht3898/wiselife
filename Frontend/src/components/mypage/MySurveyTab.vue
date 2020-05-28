<template>
 <v-container class="ma-auto mt-5">
      
        <v-flex v-if="!reSurvey" class="ma-auto" lg10>
        <p class="infotitle" >성향 검사 결과 <span class="blinking" style="float:right">
            <v-btn rounded class="replaybtn orange lighten-1" @click="reSurvey = true">다시 검사하기</v-btn>
          </span></p>
        <survey-result />
        </v-flex>

        <v-flex v-else>
            <survey/>
        </v-flex>
 </v-container>
</template>

<script>
import Survey from "@/components/survey/Survey"
import SurveyResult from "@/components/survey/SurveyResult";
export default {
    name: "MySurveyTab",
    components: {
        Survey,
    SurveyResult
  },
  data(){
      return{
          reSurvey:false
      }
  },
  methods:{
      
  }
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.infotitle {
  font-family: "Do Hyeon", sans-serif;
  font-size: 23px;
  padding-left: 10px;
}
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
.replaybtn {
  font-family: 'Jua', sans-serif;
}
.blinking {
  -webkit-animation: blink 1s ease-in-out infinite alternate;
  -moz-animation: blink 1s ease-in-out infinite alternate;
  animation: blink 1s ease-in-out infinite alternate;
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
