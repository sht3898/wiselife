<template>
  <v-container>
    <v-flex class="ma-auto mb-10">
      <apexchart v-if="chk" type="radar" :options="options" :series="series" height="400px" />
      <div class="resultcomment" style="text-align:center; float:center">
        <h3 style="color:Green;">
          {{ username }}
          <span style="color:dimgrey">님은 [</span>
          <span style="color:orange"> {{ myKeyword }} </span>
          <span style="color:dimgrey">] 사람입니다.</span>
        </h3>
        <h4 class="mt-2">결과분석</h4>
        <v-chip
          v-for="keyword in random_keywords"
          :key="keyword"
          :color="`green lighten-4 mr-1`"
          class="black--text mt-2"
          label
        >#{{ keyword }}</v-chip>
      </div>
    </v-flex>
  </v-container>
</template>

<script>
import http from "../../http-common.js";
import VueApexCharts from "vue-apexcharts";

export default {
  name: "SurveyResult",
  components: {
    apexchart: VueApexCharts
  },
  data() {
    return {
      chk: false,
      username: sessionStorage.getItem("username"),
      keywords: [],
      myKeyword: "",
      random_keywords: [],
      openness_keywords: [
        "전통적인",
        "보수적인",
        "익숙함 선호",
        "혁신적인",
        "진보적인",
        "새로움 선호"
      ],
      conscientiousness_keywords: [
        "즉흥적인",
        "덤벙거리는",
        "방만한",
        "계획적인",
        "꼼꼼한",
        "절제된"
      ],
      extraversion_keywords: [
        "과묵한",
        "혼자가 좋아",
        "진중한",
        "수다스러운",
        "함께가 좋아",
        "명랑한"
      ],
      agreeableness_keywords: [
        "까칠한",
        "무뚝뚝한",
        "이기적인",
        "배려하는",
        "친절한",
        "이타적인"
      ],
      neuroticism_keywords: [
        "안정적인",
        "편안한",
        "무던한",
        "변덕스러운",
        "불안한",
        "예민한"
      ],

      survey: {
        openness: 73.7,
        conscientiousness: 53.3,
        extraversion: 60,
        agreeableness: 63,
        neuroticism: 13.3
      },
      avg: {
        openness: 66,
        conscientiousness: 59,
        extraversion: 51,
        agreeableness: 55,
        neuroticism: 55
      },
      series: [
        {
          name: "",
          data: []
        }
      ],
      options: {
        labels: ["개방성", "성실성", "외향성", "우호성", "신경증"],
        stroke: {
          show: true,
          width: 2,
          colors: ["orange"],
          dashArray: 0
        },
        colors: ["orange"],
        markers: {
          size: 5,
          hover: {
            size: 10
          }
        },
        xaxis: {
          categories: ["개방성", "성실성", "외향성", "우호성", "신경증"],
          labels: {
            show: true,
            style: {
              colors: ["#a8a8a8"],
              fontSize: "11px",
              fontFamily: "Arial"
            }
          }
        }
      }
    };
  },
  mounted() {
    this.getSurveyResult();
  },
  methods: {
    getSurveyResult() {
      let config = {
        headers: {
          access_token: sessionStorage.getItem("token")
        }
      };
      http.get(`user/info`, config).then(response => {
        if (response.data.status == "success") {
          this.survey = response.data.info.survey;
          this.series[0].data = [
            this.survey.openness,
            this.survey.conscientiousness,
            this.survey.extraversion,
            this.survey.agreeableness,
            this.survey.neuroticism
          ];
          let key_index = this.series[0].data.indexOf(
            Math.max(...this.series[0].data)
          );
          switch (key_index) {
            case 0:
              this.myKeyword = "개방적인";
              break;
            case 1:
              this.myKeyword = "부지런한";
              break;
            case 2:
              this.myKeyword = "활동적인";
              break;
            case 3:
              this.myKeyword = "신사적인";
              break;
            case 4:
              this.myKeyword = "날카로운";
              break;
          }
          if (this.survey.openness < this.avg.openness) {
            this.keywords.push(
              this.openness_keywords[0],
              this.openness_keywords[1],
              this.openness_keywords[2]
            );
          } else if (this.survey.openness > this.avg.openness) {
            this.keywords.push(
              this.openness_keywords[3],
              this.openness_keywords[4],
              this.openness_keywords[5]
            );
          }
          if (this.survey.conscientiousness < this.avg.conscientiousness) {
            this.keywords.push(
              this.conscientiousness_keywords[0],
              this.conscientiousness_keywords[1],
              this.conscientiousness_keywords[2]
            );
          } else if (
            this.survey.conscientiousness > this.avg.conscientiousness
          ) {
            this.keywords.push(
              this.conscientiousness_keywords[3],
              this.conscientiousness_keywords[4],
              this.conscientiousness_keywords[5]
            );
          }
          if (this.survey.extraversion < this.avg.extraversion) {
            this.keywords.push(
              this.extraversion_keywords[0],
              this.extraversion_keywords[1],
              this.extraversion_keywords[2]
            );
          } else if (this.survey.extraversion > this.avg.extraversion) {
            this.keywords.push(
              this.extraversion_keywords[3],
              this.extraversion_keywords[4],
              this.extraversion_keywords[5]
            );
          }
          if (this.survey.agreeableness < this.avg.agreeableness) {
            this.keywords.push(
              this.agreeableness_keywords[0],
              this.agreeableness_keywords[1],
              this.agreeableness_keywords[2]
            );
          } else if (this.survey.agreeableness > this.avg.agreeableness) {
            this.keywords.push(
              this.agreeableness_keywords[3],
              this.agreeableness_keywords[4],
              this.agreeableness_keywords[5]
            );
          }
          if (this.survey.neuroticism < this.avg.neuroticism) {
            this.keywords.push(
              this.neuroticism_keywords[0],
              this.neuroticism_keywords[1],
              this.neuroticism_keywords[2]
            );
          } else if (this.survey.neuroticism > this.avg.neuroticism) {
            this.keywords.push(
              this.neuroticism_keywords[3],
              this.neuroticism_keywords[4],
              this.neuroticism_keywords[5]
            );
          }
          for (var i = 0; i < 3; i++) {
            var index = Math.floor(Math.random() * 15);
            if (!this.random_keywords.includes(this.keywords[index])) {
              this.random_keywords[i] = this.keywords[index];
            } else {
              i--;
            }
          }
          this.chk = true;
        }
      });
    }
  }
};
</script>

<style>
</style>