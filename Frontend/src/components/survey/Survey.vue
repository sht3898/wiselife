<template>
  <div>
    <v-row v-for="question in questions" :key="question.number">
      <v-col cols="12">
        <div class="mb-5">
        <h3>{{question.number}}. {{question.question}}</h3>
        </div>
        <v-card-text class="mt-5 mb-5">
          <v-slider
            v-model="question.answer"
            color="green"
            track-color="grey"
            always-dirty
            :tick-labels="ticksLabels"
            :max="4"
            step="1"
          ></v-slider>
        </v-card-text>
      </v-col>
    </v-row>
    <div class="btn py-3" style="float:right">
      <v-btn color="success" class="mr-4" @click="validate()">결과 확인</v-btn>
    </div>
  </div>
</template>

<script>
import http from "../../http-common.js";

export default {
  name: "survey",
  props: {
    token: { type: String }
  },
  data() {
    return {
      questions: [
        {
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },{
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },{
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },{
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },{
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },{
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },{
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },{
          number: 1,
          question: "나는 평소1 하다.",
          answer: 2
        },
        {
          number: 2,
          question: "나는 평소2 asdfasdfasdfasdfasdfasdf하다.",
          answer: 2
        },
      ],
      ticksLabels: [
        "전혀 그렇지 않다",
        "그렇지 않다",
        "보통이다",
        "그렇다",
        "매우 그렇다"
      ]
    };
  },
  mounted: {},
  methods: {
    validate() {
        var answers = [];
        for( var i = 0; i < this.questions.length; i++){
            answers.push(this.questions[i].answer);
        }
        let data = {
            answers : answers,
            headers: {
                Authorization: "JWT " + this.token
            }
        }
        http
        .post(``, data)
        .then(response =>{
            if(response.data.state == 200){
                this.$router.push('/surveyResult');
            }
        })
    }
  }
};
</script>

<style>
@keyframes metronome-example {
    from {
      transform: scale(.5);
    }

    to {
      transform: scale(1);
    }
  }
</style>