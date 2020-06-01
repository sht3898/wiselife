<template>
  <div class="body">
    <div class="wrap_personality">
      <div class="group_cover">
        <strong class="tit_cover">
          성향 설문 조사
          <em class="tit_refer">(30문항)</em>
        </strong>
        <p class="desc_cover">
          당신의 성향을 기반으로 여가생활을 추천해드립니다!
          <br />성향 검사는 BIG 5 성격 검사를 기반으로 한 검사입니다.
          <br />
          <br />총 30문항으로 순서대로 답변을 해주시면 됩니다.
          <br />질문을 읽은 후 너무 많은 생각을 하기보다는 바로 선택하세요.
          <br />중간 답변 의미를 선택하면 약 50 % 정확도가 있습니다.
        </p>
      </div>
      <v-divider></v-divider>
      <div class="group_question">
        <ol class="list_question">
          <li v-for="question in questions" :key="question.number">
            <strong class="tit_question">{{question.number}}. {{question.question}}</strong>
            <div class="area_auescase">
              <span class="indicate_standard standard_negative">전혀 그렇지 않다.</span>
              <span class="indicate_standard standard_positive">매우 그렇다.</span>
              <div class="list_quescase">
                <span class="screen_out">텍스트 선택지</span>
                <v-btn
                  text
                   min-width="0"
                  class="btn_dot btn_dot1"
                  :class="{btn_active: (question.answer==1)}"
                  @click="clickDot1(question)"
                  freezebutton
                >
                  <span class="screen_out">가장 부정</span>
                  <span class="ico_together2 ico_dot"></span>
                </v-btn>
                <v-btn
                  text
                  min-width="0"
                  class="btn_dot btn_dot2"
                  :class="{btn_active: (question.answer==2)}"
                  @click="clickDot2(question)"
                  freezebutton
                >
                  <span class="screen_out">약간 부정</span>
                  <span class="ico_together2 ico_dot"></span>
                </v-btn>
                <v-btn
                  min-width="0"
                  text
                  class="btn_dot btn_dot3"
                  :class="{btn_active: (question.answer==3)}"
                  @click="clickDot3(question)"
                  freezebutton
                >
                  <span class="screen_out">보통</span>
                  <span class="ico_together2 ico_dot"></span>
                </v-btn>
                <v-btn
                  text
                  min-width="0"
                  class="btn_dot btn_dot4"
                  :class="{btn_active: (question.answer==4)}"
                  @click="clickDot4(question)"
                  freezebutton
                >
                  <span class="screen_out">약간 긍정</span>
                  <span class="ico_together2 ico_dot"></span>
                </v-btn>
                <v-btn
                  text
                   min-width="0"
                  class="btn_dot btn_dot5"
                  :class="{btn_active: (question.answer==5)}"
                  @click="clickDot5(question)"
                  freezebutton
                >
                  <span class="screen_out">강한 긍정</span>
                  <span class="ico_together2 ico_dot"></span>
                </v-btn>
              </div>
            </div>
          </li>
        </ol>
      </div>
    </div>
    <div class="btn pb-5" style="float:right;">
      <v-btn color="success" class="mr-4" @click="validate()">결과 확인</v-btn>
    </div>
  </div>
</template>

<script>
import http from "../../http-common.js";

export default {
  name: "Survey",
  props: {
    token: { type: String }
  },
  data() {
    return {
      questions: [
        {
          number: 1,
          question: "나는 상상력이 풍부하고, 아이디어 떠올리는 일을 즐긴다",
          answer: 0
        },
        {
          number: 2,
          question: "나는 예술에 대해 거의 관심이 없다",
          answer: 0
        },
        {
          number: 3,
          question:
            "나는 자신이나 타인의 감정이나 기분에 쉽게 영향을 받지 않는다",
          answer: 0
        },
        {
          number: 4,
          question: "나는 여러 방면에 호기심이 많고 도전하기를 즐긴다",
          answer: 0
        },
        {
          number: 5,
          question: "나는 아이디어나 이론적 이야기를 하는 것에 관심이 없다",
          answer: 0
        },
        {
          number: 6,
          question: "나는 변화에 두려움이 없다",
          answer: 0
        },
        {
          number: 7,
          question: "나는 일을 능숙하게 처리하며, 끈기를 갖고 일을 잘 끝낸다",
          answer: 0
        },
        {
          number: 8,
          question: "나는 체계적이며, 계획을 세우고 그것을 따른다",
          answer: 0
        },
        {
          number: 9,
          question: "나는 약속을 잘 어긴다",
          answer: 0
        },
        {
          number: 10,
          question: "나는 성공하고 싶은 욕구가 강하다",
          answer: 0
        },
        {
          number: 11,
          question: "나는 쉽게 산만해진다",
          answer: 0
        },
        {
          number: 12,
          question: "나는 조심성이 없는 편이다",
          answer: 0
        },
        {
          number: 13,
          question: "나는 친구를 쉽게 사귄다",
          answer: 0
        },
        {
          number: 14,
          question: "나는 모임에서 많은 사람들과 이야기를 나누는 것을 좋아한다",
          answer: 0
        },
        {
          number: 15,
          question:
            "나는 모임에서 먼저 나서거나, 다른 사람들을 리드하기 위해 노력한다",
          answer: 0
        },
        {
          number: 16,
          question: "나는 뭐든지 행동하는 것을 좋아한다",
          answer: 0
        },
        {
          number: 17,
          question: "나는 흥이 많으며 흥분되는 일을 매우 좋아한다",
          answer: 0
        },
        {
          number: 18,
          question: "나는 활력이 가득하고 기쁨이 넘치는 사람이다",
          answer: 0
        },
        {
          number: 19,
          question: "나는 사람들이 선한 의도를 갖고 있다고 믿는다",
          answer: 0
        },
        {
          number: 20,
          question: "나는 성공하기 위해 속임수를 쓸 수 있다",
          answer: 0
        },
        {
          number: 21,
          question: "나는 바라는 것 없이 남들을 돕는다",
          answer: 0
        },
        {
          number: 22,
          question:
            "나는 종종 남들에게 무례할 때가 있거나 남의 흠을 지적할 때가 있다",
          answer: 0
        },
        {
          number: 23,
          question: "나는 내가 다른 사람들보다 더 낫다고 믿는다",
          answer: 0
        },
        {
          number: 24,
          question: "나는 다른 사람의 슬픔을 함께 괴로워한다",
          answer: 0
        },
        {
          number: 25,
          question: "나는 걱정이 많고, 쉽게 스트레스를 받는 편이다",
          answer: 0
        },
        {
          number: 26,
          question: "나는 화가나면 잘 참지 못하는 편이다",
          answer: 0
        },
        {
          number: 27,
          question: "나는 잘 우울해지는 편이다",
          answer: 0
        },
        {
          number: 28,
          question: "나는 쉽게 창피해하지 않는다",
          answer: 0
        },
        {
          number: 29,
          question: "나는 스스로 욕구를 자제할 수 있다",
          answer: 0
        },
        {
          number: 30,
          question: "나는 스트레스를 잘 다스린다",
          answer: 0
        }
      ],
      ticksLabels: ["전혀 그렇지 않다", " ", " ", " ", "매우 그렇다"]
    };
  },
  mounted: {},
  methods: {
    validate() {
      var answers = [];
      let minus = [1, 2, 4, 8, 10, 11, 19, 21, 22, 27, 28, 29];
      for (var i = 0; i < this.questions.length; i++) {
        if (this.questions[i].answer == 0) {
          alert(i + 1 + "번 문항을 체크해주세요!");
          return;
        }
        if (minus.includes(i)) {
          answers.push(6 - this.questions[i].answer);
        } else {
          answers.push(this.questions[i].answer);
        }
      }
      let openness = 0;
      let conscientiousness = 0;
      let extraversion = 0;
      let agreeableness = 0;
      let neuroticism = 0;

      for (var j = 0; j < answers.length; ++j) {
        var div = Math.floor(j / 6);
        if (div == 0) {
          openness += answers[j];
        } else if (div == 1) {
          conscientiousness += answers[j];
        } else if (div == 2) {
          extraversion += answers[j];
        } else if (div == 3) {
          agreeableness += answers[j];
        } else if (div == 4) {
          neuroticism += answers[j];
        }
      }
      openness = ((openness / 30) * 100).toFixed(0);
      conscientiousness = ((conscientiousness / 30) * 100).toFixed(0);
      extraversion = ((extraversion / 30) * 100).toFixed(0);
      agreeableness = ((agreeableness / 30) * 100).toFixed(0);
      neuroticism = ((neuroticism / 30) * 100).toFixed(0);

      let data = {
        openness: openness,
        conscientiousness: conscientiousness,
        extraversion: extraversion,
        agreeableness: agreeableness,
        neuroticism: neuroticism
      };

      let config = {
        headers: {
          access_token: this.token
        }
      };

      console.log(data);
      http.post(`user/survey`, data, config).then(response => {
        if (response.data.status) {
          this.$router.push("/surveyresult");
        }
      });
    },
    clickDot1(question) {
      question.answer = 1;
    },
    clickDot2(question) {
      question.answer = 2;
    },
    clickDot3(question) {
      question.answer = 3;
    },
    clickDot4(question) {
      question.answer = 4;
    },
    clickDot5(question) {
      question.answer = 5;
    }
  }
};
</script>

<style>
@keyframes metronome-example {
  from {
    transform: scale(0.5);
  }

  to {
    transform: scale(1);
  }
}
.wrap_personality {
  font-size: 14px;
  line-height: 1.5;
  font-family: "Kakao Light", "Malgun Gothic", "맑은 고딕",
    "Apple SD Gothic Neo", dotum, "돋움", sans-serif;
  color: #444;
  margin: 0;
  padding: 0;
  margin-bottom: 92px;
}
.wrap_personality .group_question {
  width: 700px;
  margin: 0 auto;
  padding: 50px 0 0 0;
  text-align: center;
}

.wrap_personality .group_cover {
  width: 700px;
  margin: 0 auto;
  text-align: center;
}
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.infotitle {
  font-family: "Do Hyeon", sans-serif;
  font-size: 23px;
  padding-left: 10px;
}
.group_cover .tit_cover {
  display: inline-block;
  font-size: 40px;
  font-weight: normal;
  font-family: "Do Hyeon", sans-serif;
  vertical-align: top;
}
.group_cover .tit_cover .tit_refer {
  display: block;
  font-size: 16px;
}
.group_cover .desc_cover {
  margin-top: 19px;
  font-size: 15px;
  line-height: 25px;
  word-break: keep-all;
}
.screen_out {
  overflow: hidden;
  position: absolute;
  width: 0;
  height: 0;
  line-height: 0;
  text-indent: -9999px;
}

ul,
ol,
menu,
li {
  list-style: none;
}

div {
  display: block;
}
ol {
  display: block;
  list-style-type: decimal;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  padding-inline-start: 40px;
  font-size: 14px;
  line-height: 1.5;
  font-family: "Kakao Light", "Malgun Gothic", "맑은 고딕",
    "Apple SD Gothic Neo", dotum, "돋움", sans-serif;
  color: #444;
  text-align: center;
  margin: 0;
  padding: 0;
  list-style: none;
}

.group_question .list_question .tit_question {
  font-size: 19px;
  font-weight: normal;
  font-family: "Kakao Bold", sans-serif;
}

.group_question .area_auescase {
  position: relative;
  height: 74px;
  margin-top: 22px;
}
.group_question .area_auescase .indicate_standard {
  position: absolute;
  top: 50%;
  margin-top: -11px;
  font-size: 15px;
}
.group_question .area_auescase .standard_negative {
  overflow: hidden;
  width: 145px;
  text-align: right;
  padding-right: 15px;
  left: 0;
  white-space: nowrap;
  -ms-text-overflow: ellipsis;
  text-overflow: ellipsis;
}

.group_question .area_auescase .standard_positive {
  width: 139px;
  text-align: left;
  padding-left: 15px;
  right: 0;
}
.group_question .list_quescase {
  position: relative;
  width: 314px;
  height: 74px;
  margin: 0 auto;
}
.group_question .list_quescase .btn_dot {
  position: absolute;
  z-index: 10;
  top: 50%;
  margin-top: -20px;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
  border-radius: 50%;
  border: 1px solid #ccc;
}
.group_question .list_quescase .btn_dot1 {
  width: 74px;
  height: 74px;
  left: -38px;
  margin-top: -38px;
}
.group_question .list_quescase .btn_dot2 {
  width: 60px;
  height: 60px;
  left: 25%;
  margin: -30px 0 0 -24px;
}
.group_question .list_quescase .btn_dot3 {
  width: 52px;
  height: 52px;
  left: 50%;
  margin: -26px 0 0 -27px;
}
.group_question .list_quescase .btn_dot4 {
  width: 60px;
  height: 60px;
  left: 75%;
  margin: -30px 0 0 -35px;
}
.group_question .list_quescase .btn_dot5 {
  width: 74px;
  height: 74px;
  right: -36px;
  margin-top: -38px;
}
.list_quescase .btn_dot.btn_active {
  background: #84dc1d;
  border: 1px solid #84dc1d;
}
.group_question .list_quescase .ico_dot {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 10px;
  height: 10px;
  margin-left: -5px;
  margin-top: -5px;
  background-position: -463px -885px;
}
.group_question .list_quescase .btn_dot.btn_active .ico_dot {
  width: 21px;
  height: 17px;
  background-position: -440px -885px;
  margin-top: -8px;
  margin-left: -10px;
}
.ico_together2 {
  display: block;
  overflow: hidden;
  font-size: 0;
  line-height: 0;
  background: url(//t1.kakaocdn.net/together_image/common/ico_together2_191106.png)
    no-repeat 0 0;
  text-indent: -9999px;
}
.group_question .list_question li + li {
  margin-top: 87px;
}
.group_question .after_question .tit_question {
  opacity: 0.3;
}
.group_question .list_question .tit_question {
  font-size: 19px;
  font-weight: normal;
  font-family: "Kakao Bold", sans-serif;
}
.group_question .after_question .area_auescase {
  opacity: 0.3;
}

@media (max-width: 767px) {
  .wrap_personality {
    margin-bottom: 51px;
  }
  .wrap_personality .group_cover {
    width: auto;
    padding-top: 38px;
  }
  .wrap_personality .group_question {
    width: auto;
    margin: 60px auto 100px;
    padding: 0 25px;
  }
  .group_question .list_question .tit_question {
    font-size: 16px;
  }
  .group_question .area_auescase {
    width: 232px;
    height: 54px;
    margin: 17px auto 0;
  }
  .group_question .area_auescase .standard_negative {
    left: -38px;
  }
  .group_question .area_auescase .standard_positive {
    right: -41px;
  }
  .group_question .area_auescase .indicate_standard {
    width: auto;
    top: auto;
    bottom: -26px;
    font-size: 13px;
  }
  .group_question .list_quescase {
    width: 232px;
    height: 54px;
  }

  .group_question .list_quescase .ico_dot {
    width: 8px;
    height: 8px;
    margin-top: -4px;
    margin-left: -4px;
    background-position: -130px -690px;
  }

  .group_question .list_quescase .btn_dot.btn_active .ico_dot {
    width: 16px;
    height: 13px;
    background-position: -130px -708px;
    margin-top: -6px;
    margin-left: -8px;
  }

  .group_question .list_quescase .btn_dot1 {
    width: 54px;
    height: 54px;
    margin-top: -27px;
    left: -27px;
  }

  .group_question .list_quescase .btn_dot2 {
    width: 44px;
    height: 44px;
    margin-left: -17px;
    margin-top: -22px;
  }
  .group_question .list_quescase .btn_dot3 {
    width: 38px;
    height: 38px;
    margin-left: -18px;
    margin-top: -19px;
  }
  .group_question .list_quescase .btn_dot4 {
    width: 44px;
    height: 44px;
    margin-left: -25px;
    margin-top: -22px;
  }
  .group_question .list_quescase .btn_dot5 {
    width: 54px;
    height: 54px;
    margin-top: -27px;
    right: -27px;
  }
  .ico_together2 {
    display: block;
    overflow: hidden;
    font-size: 0;
    line-height: 0;
    background: url(//t1.kakaocdn.net/together_image/common/ico_together2_191106.png)
      no-repeat 0 0;
    text-indent: -9999px;
  }
  .group_question .list_question li + li {
    margin-top: 87px;
  }
  .group_question .after_question .tit_question {
    opacity: 0.3;
  }
  .group_question .list_question .tit_question {
    font-size: 19px;
    font-weight: normal;
    font-family: "Kakao Bold", sans-serif;
  }
  .group_question .after_question .area_auescase {
    opacity: 0.3;
  }
}
</style>