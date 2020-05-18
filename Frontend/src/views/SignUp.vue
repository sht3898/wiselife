<template>
  <v-container class="signUp mt-15">
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
    <v-row>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('레저,스포츠.png')" width="90px" @click="clickCategory" :class="{ green : clicked }" style="border-radius:17px" ></v-img>
      </v-col>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('요리.png')" width="90px" style="cursor:pointer" />
      </v-col>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('수공예,공방.png')" width="90px" />
      </v-col>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('놀이,게임.png')" width="90px" />
      </v-col>
    </v-row>
    <v-row>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('문화.png')" width="90px" />
      </v-col>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('예술.png')" width="90px" />
      </v-col>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('축제,행사.png')" width="90px" />
      </v-col>
      <v-col class="mt-5">
        <v-img :src="getImgUrl('기타.png')" width="90px" />
      </v-col>
    </v-row>

    <div class="btn py-3" style="float:right">
      <v-btn color="error" class="mr-4" @click="reset">Reset</v-btn>
      <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">가입!</v-btn>
    </div>
  </v-container>
</template>
<script>
export default {
  name: "SignUp",
  data() {
    return {
      gender: "",
      birth: "",
      area1: "",
      area2: "",
      instructor: "",
      interest_category: [],
      years: [],
      clicked: false,
      valid: true,
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
      return require("../assets/categories/" + img);
    },
    clickCategory(){
      this.clicked = true;
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    reset() {
      this.$refs.form.reset();
    },
    validate() {}
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
.green{
  background-color:green;
}
</style>