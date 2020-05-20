<template>
  <v-container v-if="!complete" class="signUp mt-15">
    <h3>회원정보 입력</h3>
    <v-row>
      <v-col cols="2" class="mt-5">
        <span style="color:dimgray;">
          <strong>gender</strong>
        </span>
      </v-col>
      <v-radio-group v-model="gender" row class="ml-3">
        <v-col>
          <v-radio label=" 남" value="1"></v-radio>
        </v-col>
        <v-col style="padding-left:90px">
          <v-radio label=" 여" value="2"></v-radio>
        </v-col>
      </v-radio-group>
    </v-row>
    <v-row>
      <v-col cols="2" class="mt-5">
        <span style="color:dimgray;">
          <strong>birth</strong>
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
          <strong>instructor</strong>
        </span>
      </v-col>
      <v-radio-group v-model="instructor" row class="ml-3">
        <v-col>
          <v-radio label=" 강사" value="1"></v-radio>
        </v-col>
        <v-col style="padding-left:60px">
          <v-radio label=" 일반 회원" value="0"></v-radio>
        </v-col>
      </v-radio-group>
    </v-row>

    <h3>관심 카테고리</h3>
    <v-row justify="space-around">
      <v-col class="mt-5" v-for="category in categories" :key="category.name">
        <v-img
          :src="getImgUrl(category.url)"
          width="90px"
          @click="category.clicked = !category.clicked"
          :class="{green: category.clicked}"
          style="border-radius:17px; margin:auto"
        ></v-img>
        <div class="subheading" style="text-align:center;">{{category.name}}</div>
      </v-col>
    </v-row>

    <div class="btn py-3" style="float:right">
      <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">가입!</v-btn>
    </div>
  </v-container>
  <v-container v-else>
    <survey :token="token"/>
    <div class="btn py-3" style="float:right">
      <v-btn color="grey lighten-3" class="mr-4" @click="pass()">건너뛰기</v-btn>
    </div>
  </v-container>
</template>
<script>
import http from "../../http-common";
import Survey from "@/components/survey/Survey";

export default {
  name: "signUp",
  props: {
    token: { type: String },

  },
  components:{
    Survey,
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
      complete : false,
      first_area:[
        '서울특별시',
        '부산광역시',
        '대구광역시',
        '인천광역시',
        '광주광역시',
        '대전광역시',
        '울산광역시',
        '세종특별자치시',
        '경기도',
        '강원도',
        '충청북도',
        '충청남도',
        '전라북도',
        '전라남도',
        '경상북도',
        '경상남도',
        '제주특별자치도'
      ],
      second_area:[],
    };
  },
  methods: {
    getYears() {
      const now = new Date().getUTCFullYear();
      this.years = Array(now - (now - 70))
        .fill("")
        .map((v, idx) => now - idx);
    },
    getImgUrl(img) {
      return require("../../assets/categories/" + img);
    },
    clickCategory() {
      this.clicked = true;
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    reset() {
      this.$refs.form.reset();
    },
    getSecondArea(){
      if(this.area1.charAt(this.area1.length-1) == "시"){
        this.second_area.push("전체");
      }
      // http
      //   .get(`api/area/${this.first_area}`)
      //   .then(response => {
      //     this.second_area.push(response.data.second_area);
      //   })
      //   .catch(err => {
      //     console.log(err);
      //   });
    },
    validate() {
      if(this.gender == "" || this.birth == "" || this.area1 == "" || this.area2 == "" || this.instructor == ""){
        alert("입력하지 않은 값이 있습니다!");
        return;
      }
      var picked = [];
      for(var i = 0; i < this.categories.length; i++){
        if(this.categories[i].clicked){
          picked.push(i+1);
        }
      }
      let params = {
        username : sessionStorage.getItem("username"),
        gender: this.gender,
        year : this.birth,
        area1: this.area1,
        area2: this.area2,
        is_inst : this.instructor,
        interest_category : picked,
        headers: {
          Authorization: "JWT " + this.token
        }
      }
      console.log(params);
      this.complete = true;

      // http
      // .post(`api/signup/`,params)
      // .then(response =>{
      //   if(respons.data.state == 200){
          // this.complete = true;
          // sessionStorage.setItem("token",this.token);
      //   }
      // })

    },
    pass(){
      sessionStorage.setItem('token', this.token);
      this.$router.push('/');
      location.reload();
    }
  },
  watch: {
    area1: "getSecondArea"
  },
  mounted() {
    this.getYears();
  }
};
</script>
<style scoped>
.signUp {
  margin: auto;
}
.green {
  background-color: green;
}
</style>