<template>
  <v-container v-if="!complete" class="ma-auto">
    <v-row class="my-5 py-5">
      <v-col cols="12" sm="6">
        <v-flex class="float-lg-right" lg10>
          <h3 style="text-align:center">내 정보 보기</h3>
          <v-row>
            <v-col cols="2" class="mt-5">
              <span style="color:dimgray;">
                <strong>Gender</strong>
              </span>
            </v-col>
            <v-radio-group v-model="gender" row class="ml-3">
              <v-col>
                <v-radio color="green" label=" 남" value="1"></v-radio>
              </v-col>
              <v-col style="padding-left:90px">
                <v-radio color="green" label=" 여" value="2"></v-radio>
              </v-col>
            </v-radio-group>
          </v-row>
          <v-row>
            <v-col cols="2" class="mt-5">
              <span style="color:dimgray;">
                <strong>Birth</strong>
              </span>
            </v-col>
            <v-col class="px-0 py-0">
              <v-container id="dropdown-example-2">
                <v-overflow-btn v-model="birth" :items="years" label="연도" dense></v-overflow-btn>
              </v-container>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="2" class="mt-5">
              <span style="color:dimgray;">
                <strong>Area</strong>
              </span>
            </v-col>
            <v-col class="px-0 py-0">
              <v-container id="dropdown-example-2" class="py-0">
                <v-overflow-btn v-model="area1" :items="first_area" label="도/시" dense></v-overflow-btn>
              </v-container>
            </v-col>
            <v-col class="px-0 py-0">
              <v-container id="dropdown-example-2" class="py-0">
                <v-overflow-btn v-model="area2" :items="second_area" label="시/군/구" dense></v-overflow-btn>
              </v-container>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="2" class="mt-5">
              <span style="color:dimgray;">
                <strong>Instructor</strong>
              </span>
            </v-col>
            <v-radio-group v-model="instructor" row class="ml-3">
              <v-col>
                <v-radio color="green" label=" 강사" value="1"></v-radio>
              </v-col>
              <v-col style="padding-left:60px">
                <v-radio color="green" label=" 일반 회원" value="0"></v-radio>
              </v-col>
            </v-radio-group>
          </v-row>

          <h3 style="text-align:center">관심 카테고리</h3>
          <v-row justify="space-around">
            <v-col class="mt-5" v-for="category in categories" :key="category.name">
              <v-img
                :src="getImgUrl(category.url)"
                width="70px"
                @click="category.clicked = !category.clicked"
                :class="{green: category.clicked}"
                style="border-radius:12px; margin:auto"
              ></v-img>
              <div style="font-size:10pt; text-align:center;">{{category.name}}</div>
            </v-col>
          </v-row>
            <div class="mt-5" style="text-align:center">
              <v-btn rounded class="myinfobtn mr-5" color="orange lighten-2">
                수정하기
              </v-btn>
              <v-btn rounded class="myinfobtn" color="orange lighten-2">
                탈퇴하기
              </v-btn>
            </div>
            </v-flex>

        
      </v-col>
      <v-col cols="12" sm="6">
          <h3 style="text-align:center">성향 검사 결과</h3>
          <survey-result />
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import http from "../http-common";
import SurveyResult from "@/components/survey/SurveyResult";

export default {
  name: "MyInfoPage",
  props: {
    token: { type: String }
  },
  components: {
    SurveyResult
  },
  data() {
    return {
      gender: "",
      birth: "",
      area1: "",
      area2: "",
      instructor: "",
      categories: [
        {
          url: "레저,스포츠.png",
          name: "레저/스포츠",
          clicked: false
        },
        {
          url: "요리.png",
          name: "요리",
          clicked: false
        },
        {
          url: "수공예,공방.png",
          name: "수공예/공방",
          clicked: false
        },
        {
          url: "놀이,게임.png",
          name: "놀이/게임",
          clicked: false
        },
        {
          url: "문화.png",
          name: "문화",
          clicked: false
        },
        {
          url: "예술.png",
          name: "예술",
          clicked: false
        },
        {
          url: "축제,행사.png",
          name: "축제/행사",
          clicked: false
        },
        {
          url: "기타.png",
          name: "기타",
          clicked: false
        }
      ],
      years: [],
      valid: true,
      complete: false,
      first_area: [
        "서울특별시",
        "부산광역시",
        "대구광역시",
        "인천광역시",
        "광주광역시",
        "대전광역시",
        "울산광역시",
        "세종특별자치시",
        "경기도",
        "강원도",
        "충청북도",
        "충청남도",
        "전라북도",
        "전라남도",
        "경상북도",
        "경상남도",
        "제주특별자치도"
      ],
      second_area: []
    };
  },
  watch: {
    area1: "getSecondArea"
  },
  mounted() {
    this.getUserInfo();
    this.getYears();
  },
  methods: {
    getYears() {
      const now = new Date().getUTCFullYear();
      this.years = Array(now - (now - 70))
        .fill("")
        .map((v, idx) => now - idx);
    },
    getImgUrl(img) {
      return require("../assets/categories/" + img);
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    reset() {
      this.$refs.form.reset();
    },
    getSecondArea() {
      if (this.area1.charAt(this.area1.length - 1) == "시") {
        this.second_area.push("전체");
      }
      http
        .get(`area/${this.area1}`)
        .then(response => {
          console.log(response.data);
          for (var i = 0; i < response.data.length; i++) {
            this.second_area.push(response.data[i]);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },   
    pass() {
      sessionStorage.setItem("token", this.token);
      this.$router.push("/");
      location.reload();
    },
    getUserInfo(){
       let config = {
        headers: {
          access_token: sessionStorage.getItem("token")
        }
      };
      http.get(`user/info`, config).then(response => {
        if (response.data.status == "success") {
          console.log(response);
          let userinfo = response.data.info.userinfo;
          this.birth=userinfo.year;
          this.gender=userinfo.gender.toString();
          this.area1=userinfo.area1;
          this.area2=userinfo.area2;
          this.instructor=userinfo.isInst.toString();

          for(var i = 0; i < response.data.info.interest_category.length; i++){
            var now_interest_category = response.data.info.interest_category[i];
            for(var j = 0; j < this.categories.length; j++){
              if(now_interest_category.categoryName == this.categories[j].name){
                this.categories[j].clicked = true;
              }
            }
          }
        }
      });
    }
  },
  
};
</script>
<style scoped>
.green {
  background-color: green;
}
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.myinfobtn {
  font-size:13pt;
  font-family: "Nanum Pen Script", cursive;
}
</style>