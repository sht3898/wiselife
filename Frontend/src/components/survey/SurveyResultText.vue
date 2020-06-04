<template>
  <v-container>
    <v-flex class="ma-auto my-10">
      <v-row v-if="chk">
        <v-col cols="12" sm="6">
          <h3 class="infotext ma-auto" style="text-align:center; color:purple;">당신은 {{ name }}!</h3>
          <v-img class="personality ma-auto mt-1" :src="getImgUrl(url)" />
        </v-col>
        <v-col cols="12" sm="6">
          <h4 v-for="content in text" :key="content" class="contents my-1" style="text-align:left;">
            - {{ content }}
            <br />
          </h4>
        </v-col>
      </v-row>
      <div class="tags my-10" style="text-align:center;">
        <h2 class="infotext ma-auto" style="text-align:center; color:orange;">당신을 위한 여가 추천!</h2>
        <v-chip
          v-for="act in activity"
          :key="act"
          class="mt-2 my-2 mx-1"
          style="background-color:lightblue;"
          label
        >#{{ act }}</v-chip>
      </div>
    </v-flex>
  </v-container>
</template>

<script>
export default {
  name: "SurveyResultText",
  props: {
    mbti: { type: String, default: "" }
  },
  data() {
    return {
      chk: false,
      url: "",
      name: "",
      text: [],
      activity: [],
      activities: [
        [],
        ["프라모델", "바둑", "장기", "체스"],
        ["승마", "반려동물과 산책하기"],
        ["요트", "패러글라이딩", "육체적인 운동"],
        ["그림", "글쓰기", "악기 연주"],
        ["명상", "산책", "글쓰기"],
        ["낚시", "IT 기기 다루기"],
        ["레포츠", "항공 레포츠", "여행"],
        ["산악자전거", "여행"],
        ["암벽등반"]
      ],
      results: [
        {
          key: "ISTJ",
          name: "논리주의자",
          url: "ISTJ.png",
          index: [1, 5, 6],
          text: [
            "실질적이고 까다로운 사람",
            "조직적으로 일하며 시간을 잘 지킵니다.",
            "자기 자신과 남에 대한 기대 수준이 높습니다.",
            "걱정이 많고 실수하지 않으려고 노력합니다.",
            "객관적이며 자신의 감정을 잘 다스립니다.",
            "충실하고 건실한 편이나 때로 타협을 잘 하지 않는 사람으로 보이기도 합니다.",
            "쾌활한 사람에게 서로 끌리는 경우가 많습니다."
          ],
          activity: ["컴퓨터 게임", "에어로빅", "골프"]
        },
        {
          key: "ISFJ",
          name: "수호자",
          url: "ISFJ.png",
          index: [1, 2, 6],
          text: [
            "내성적이고 참을성이 많은 사람",
            "부드럽고 충직하며 신뢰할 만합니다.",
            "때론 자기 비판적이고 걱정이 많습니다.",
            "남을 위해 자신의 것을 많이 내어줄 수 있지만 도움을 받거나, 부정적인 감정을 표현하는 것은 힘들어합니다.",
            "의무에 극단적일 정도로 충실하고 책임감 때문에 과도한 부담을 느끼는 경우가 많습니다."
          ],
          activity: ["소풍"]
        },
        {
          key: "INFJ",
          name: "변호사",
          url: "INFJ.png",
          index: [1, 4],
          text: [
            "굳은 의지와 강한 책임감, 참신한 창의력이 돋보이는 사람",
            "비교적 세련되고 품위 있습니다.",
            "충직하면서 말이 없고 고독해 보이기도 합니다.",
            "화합에 대한 욕구가 강해서 자기주장을 힘들어하고, 경계를 짓는 것을 어려워하며, 힘들어도 혼자 감당합니다.",
            "다른 사람의 느낌을 잘 헤아리며, 이해받지 못한다고 생각할 때는 깊은 상처를 받기도 합니다."
          ],
          activity: ["독서", "작곡"]
        },
        {
          key: "INTJ",
          name: "건축가",
          url: "INTJ.png",
          index: [1, 5, 6],
          text: [
            "논리적이고 이론적인 사람",
            "독립적인 동시에 책임감이 있습니다.",
            "매사에 충직하며 자신에 대한 기대치가 높습니다.",
            "걱정이 많고 실수하지 않으려고 노력합니다. ",
            "객관적이며 자신의 감정을 잘 다스립니다.",
            "거절당하는 것에 민감하지만 마음이 상했을 대는 혼자 감당하는 편입니다.",
            "심사숙고하고 이성적으로 판단하여 선택하는 편입니다."
          ],
          activity: ["전략게임", "독서", "마라톤", "수영"]
        },
        {
          key: "ISTP",
          name: "만능재주꾼",
          url: "ISTP.png",
          index: [5, 7],
          text: [
            "독립적이고 현실적인 사람",
            "구속받는 것과 의무감, 반복되는 일상을 피하고 싶어합니다.",
            "모험과 도전을 좋아하고 남에게 별다른 관심이 없으며 말이 별로 없습니다.",
            "도구나 기계 등 자신의 손으로 조작하는 것에 열정적으로 집중합니다."
          ],
          activity: ["양궁", "마술", "스쿠버다이빙", "레펠", "스카이다이빙"]
        },
        {
          key: "ISFP",
          name: "예술가",
          url: "ISFP.png",
          index: [9],
          text: [
            "부드러우며 겸손한 사람",
            "말이 없는 편이고 꾸밈이 없습니다.",
            "남을 잘 돕고 잘 믿으며, 남을 위해 조용히 무언가를 해줌으로써 사랑을 표현합니다.",
            "수용적이고 다른 사람의 삶의 방식을 간섭하지 않으며, 존중 받지 못하면 쉽게 위축되기도 합니다."
          ],
          activity: ["춤", "스키", "수영", "수공예 배우기", "파티"]
        },
        {
          key: "INFP",
          name: "중재자",
          url: "INFP.png",
          index: [4, 9],
          text: [
            "충직하고 연민이 많으며 생각이 깊고 부드러운 사람",
            "창의적이고 자기 비판적이며 온화하고 혼자 있는 시간이 필요합니다.",
            "거부나 경멸에 무척 민감하며 자기감정을 나누는 일을 힘들어합니다."
          ],
          activity: ["자연감상", "영화/공연 감상", "사진 촬영"]
        },
        {
          key: "INTP",
          name: "논리 학자",
          url: "INTP.png",
          index: [5],
          text: [
            "지식, 생각의 세계에 심취한 사람",
            "자신의 독립성을 소중하게 생각합니다.",
            "기념일이나 생일 같은 것에 별로 관심이 없습니다.",
            "사람을 만나는 것에 큰 흥미를 못 느끼지만 가끔 열띤 토론에 참여하기도 합니다.",
            "부정적이고 냉소적이며 논쟁을 좋아합니다.",
            "자신의 내향성을 보완하기 위해 남을 잘 돌보는 활발한 사람에게 끌리는 편입니다."
          ],
          activity: ["배낭 여행", "하이킹"]
        },
        {
          key: "ESTP",
          name: "사업가",
          url: "ESTP.png",
          index: [7],
          text: [
            "매력적이고 사람들과 잘 어울리며 재치 있는 사람",
            "사람들에게 주목받는 것을 좋아하며 권위주의에 저항하고 도전을 즐깁니다.",
            "배우자가 느끼기에는 충동적이고 산만하며 직선적이고 둔감해 보일 것입니다.",
            "자신이 좋아하는 모험을 중요하게 생각합니다."
          ],
          activity: ["자동차 경주", "소프트볼", "배구", "복싱"]
        },
        {
          key: "ESFP",
          name: "연예인",
          url: "ESFP.png",
          index: [2, 7],
          text: [
            "따뜻하고 열정이 많으며 생기가 넘치는 사람",
            "남을 잘 돌보고 즉각적으로 애정을 표현하며 다른 사람을 집에 초대하는 것을 좋아합니다.",
            "앞일을 생각하기보다는 현재의 즐거움을 찾고, 한 번 시작한 일을 끝가지 마무리 하는 것을 힘들어 합니다.",
            "화합과 긍정적인 것을 중시합니다. 사람들이 보기에는 산만하고 지나치게 활동적일 수 있습니다."
          ],
          activity: ["보드게임"]
        },
        {
          key: "ENFP",
          name: "자유로운 사고의 소유자",
          url: "ENFP.png",
          index: [2, 7],
          text: [
            "역동적이고 즉흥적이며 재미를 추구하는 사람",
            "따뜻하고 남을 잘 지지해 주며 연민이 많습니다.",
            "지나치게 긍적적으로 보이기도 하며, 남들의 인정을 바라고 다른 사람과의 관계에 지나친 신경을 쓰다가 자신을 놓치기도 합니다.",
            "애정이 많아서 끊임없이 새로운 사람, 새로운 것과 사랑에 빠집니다.",
            "열정의 대상이 바뀌면 변덕을 보일 수도 있습니다."
          ],
          activity: ["소설 읽기", "스포츠 활동"]
        },
        {
          key: "ENTP",
          name: "경쟁적인 비판자",
          url: "ENTP.png",
          index: [6, 7],
          text: [
            "즉흥적이며 활기찬 사람",
            "경쟁적이고 사람들과 어울리는 것을 좋아하며 자유와 독립성을 중시합니다.",
            "모험을 좋아하고 심리적인 도전을 즐깁니다.",
            "정리정돈을 잘 못하고 시작한 일을 마무리 못하는 경우가 많습니다.",
            "따뜻하고 자신을 지지하며 섬세한 사람에게 끌리는 편입니다."
          ],
          activity: ["해외 여행", "탐험", "방탈출"]
        },
        {
          key: "ESTJ",
          name: "CEO",
          url: "ESTJ.png",
          index: [1, 8],
          text: [
            "자기주장이 강하고 직선적인 사람",
            "무뚝뚝하면서도 논리적이고 활동적이며 결단력이 있습니다.",
            "사교적인 반면 다른 사람의 관점의 결함을 빨리 찾아내는 경향이 있습니다.",
            "중요한 책임을 떠맡는 것을 좋아해서 동등한 관계를 원하는 배우자와는 갈등을 일으키기도 합니다.",
            "따뜻하고 남을 보살펴 주는 것을 좋아하며 감정적 성향의 사람에게 끌립니다."
          ],
          activity: ["골프", "인테리어", "봉사활동", "등산"]
        },
        {
          key: "ESFJ",
          name: "인플루언서",
          url: "ESFJ.png",
          index: [2, 6],
          text: [
            "따뜻하고 부드러우며 동정심이 많은 사람",
            "남에게 실질적인 도움을 줌으로써 사랑을 표현합니다. ",
            "때론 자기 비판적이고 걱정이 많습니다.",
            "말이 많은 편이고 사람들과 어울리는 것을 좋아합니다.",
            "상대가 자신을 필요로 하거나 자기가 준 도움에 고마워하는 것을 좋아하며, 그런 사람을 위해서라면 자기가 원하는 것도 포기할 수 있습니다."
          ],
          activity: ["합창", "요리수업", "파티"]
        },
        {
          key: "ENFJ",
          name: "사회운동가",
          url: "ENFJ.png",
          index: [1, 2, 3, 7, 8],
          text: [
            "책임감이 강하고 열정이 넘치는 사람",
            "남을 돕는 것을 좋아합니다.",
            "탁월한 소통능력으로 사교성이 뛰어나며 사람들과 함께 있는 것을 좋아합니다.",
            "인정과 칭찬, 찬사를 받고 싶어합니다.",
            "일을 효율적으로 처리하여 빨리 마무리하고 싶어하는 경향이 있으며 남에게 잘 맡기지 않습니다.",
            "항상 사랑과 인정을 갈망하며 충족되지 않을 때는 실망합니다."
          ],
          activity: ["박물관 견학", "사교모임 참석", "특선요리"]
        },
        {
          key: "ENTJ",
          name: "통솔자",
          url: "ENTJ.png",
          index: [1, 3, 6, 8],
          text: [
            "자신감 넘치고 야망있는 사람",
            "직선적이고 주도권을 갖기를 원합니다.",
            "일을 효율적으로 처리하여 빨리 마무리하고 싶어하는 경향이 있으며 남에게 잘 맡기지 않습니다.",
            "정면 대결을 좋아하고 성격이 급하며 비판적이기 때문에 갈등을 겪는 경우가 많습니다.",
            "사람이나 상황을 지배하려는 성격을 보완하기 위해 남을 잘 보살피고 잘 맞춰주는 따뜻한 사람에게 끌립니다. "
          ],
          activity: ["골프", "파티", "축구", "복싱"]
        }
      ]
    };
  },
  mounted() {
    this.setResultText();
  },
  methods: {
    getImgUrl(img) {
      return require("../../assets/personality/" + img);
    },
    setResultText() {
      for (var i = 0; i < this.results.length; i++) {
        if (this.results[i].key == this.mbti) {
          this.text = this.results[i].text;
          this.url = this.results[i].url;
          this.name = this.results[i].name;
          let activity_list = [];
          for (var j = 0; j < this.results[i].index.length; j++) {
            activity_list = activity_list.concat(
              this.activities[this.results[i].index[j]]
            );
          }
          activity_list = Array.from(new Set(activity_list));
          for (var k = 0; k < 5; k++) {
            var index = Math.floor(Math.random() * (activity_list.length - 1));
            if (!this.activity.includes(activity_list[index])) {
              this.activity[k] = activity_list[index];
            } else {
              i--;
            }
          }
          this.chk = true;
          break;
        }
      }
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.infotext {
  font-family: "Nanum Pen Script", cursive;
  font-size: 20px;
}
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.tags {
  font-family: "Do Hyeon", sans-serif;
  font-size: 23px;
}

@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic+Coding:wght@700&display=swap");
.contents {
  font-family: "Nanum Gothic Coding", monospace;
  font-size: 15px;
  text-indent: -1em;
  margin-left: 1em;
}
.personality {
  margin: -1px;
  border-radius: 30px;
  box-shadow: 3px 3px 5px grey;
}
</style>