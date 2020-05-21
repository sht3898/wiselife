<template>
  <v-container>
    <v-flex class="ma-auto my-5" lg9>
      <v-row>
        <v-col cols="2">
          <v-overflow-btn v-model="main_category" :items="categories" label="카테고리" dense></v-overflow-btn>
        </v-col>
        <v-col cols="4"  class="mt-3">
          <v-text-field placeholder="제목" outlined dense></v-text-field>
        </v-col>
        <v-col cols="2">
          <v-overflow-btn v-model="is_class" :items="classform" label="형태" dense></v-overflow-btn>
        </v-col>
        <v-col cols="2">
          <v-overflow-btn v-model="is_period" :items="periodform" label="기간" dense></v-overflow-btn>
        </v-col>
        <v-col v-if="is_period=='정기'">
          <v-text-field placeholder="주/월 n회"></v-text-field>
        </v-col>
        <v-col v-if="is_period=='비정기'">
          <v-menu
            v-model="menu2"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="date"
                label="모임 시간"
                prepend-icon="mdi-calendar"
                readonly
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="date" @input="menu2 = false"></v-date-picker>
          </v-menu>
        </v-col>
      </v-row>
      <v-row style="heigth:400px">
        <v-card class="pa-2" outlined tile style="width:100%; height:400px">
          <textarea auto-grow style="width:100%; height:100%" placeholder="내용을 입력해주세요."></textarea>
        </v-card>
      </v-row>
      <v-row>
        <v-col>해시태그</v-col>
      </v-row>
      <v-row>
        <v-col>연락처</v-col>
        <v-col>참고URL</v-col>
      </v-row>
      <v-row>
        <v-col>이미지 업로더</v-col>
        <v-col>모집 인원</v-col>
        <v-col>모임비</v-col>
        <v-col>단위</v-col>
      </v-row>
      <v-row>
        <v-col>지역 (도시/시군구)</v-col>
        <v-col>상세 주소</v-col>
      </v-row>
      <v-row style="text-align:right; float:right">
        <v-col>
          <v-btn>취소</v-btn>
        </v-col>
        <v-col>
          <v-btn @click="validate()">등록</v-btn>
        </v-col>
      </v-row>
    </v-flex>
  </v-container>
</template>
<script>
export default {
  name: "contentWritePage",
  data() {
    return {
      writer: "",
      main_category: "",
      title: "",
      tags: "", // 해시태그 띄어쓰기로 구분
      is_period: 0,
      meeting_date: "",
      is_class: 0,
      max_person: 0,
      content: "",
      ref_url: "",
      area1: "",
      area2: "",
      address: "",
      fee: 0, // 회비 또는 금액을 작성시 숫자를 입력하도록
      unit: "", // 단위 : 원/미정/회비

      image_url: "",

      categories: [
        "레저/스포츠",
        "요리",
        "수공예/공방",
        "놀이/게임",
        "문화",
        "예술",
        "축제/행사",
        "기타"
      ],
      category_key: {
        "레저/스포츠": 1,
        요리: 2,
        "수공예/공방": 3,
        "놀이/게임": 4,
        문화: 5,
        예술: 6,
        "축제/행사": 7,
        기타: 8
      },
      classform: ["강좌", "모임"],
      class_key: {
        강좌: 1,
        모임: 0
      },
      periodform: ["정기", "비정기"],
      period_key: {
        정기: 1,
        비정기: 0
      },
      date: new Date().toISOString().substr(0, 10),
      modal: false,
      menu2: false
    };
  },
  methods: {
    validate() {
      alert(this.category_key[this.main_category]);
      alert(this.class_key[this.is_class]);
      alert(this.period_key[this.is_period]);
    }
  }
};
</script>
<style scoped>

</style>