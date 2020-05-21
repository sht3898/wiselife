<template>
  <v-container fluid>
    <div v-if="!clickBtn">
      <v-card class="mx-auto" max-width="75%">
        <v-row>
          <v-col class="hidden-sm-and-down">
            <img-banner :imgSrc="bannerImg"/>
          </v-col>
          <v-col>
            <div style="padding:50px;">
            <v-img src="../assets/wiselife.png" style="margin:auto;" height="110px" width="170px"></v-img>
            </div>
            <v-spacer></v-spacer>
            <div style="padding:50px;">
            <h2 style="text-align:center;">빅데이터 기반으로<br/>당신의 맞춤 여가생활을<br/>추천해드립니다!</h2>
            </div>
            <v-spacer></v-spacer>
            <div style="padding:50px;">
            <v-img
              style="cursor:pointer; margin:auto"
              max-width="450px"
              src="../assets/kakao_login_btn_large_wide.png"
              @click="login()"
            ></v-img>
            </div>
          </v-col>
        </v-row>
      </v-card>
    </div>
    <sign-up v-if="!isMember" :token="token" />
    <!-- <KakaoLogin
      api-key="c2d9f09a902e77b8550b754cdb90d407"
      image="kakao_login_btn_large"
      :on-success=onSuccess
      :on-failure=onFailure
    /> -->
  </v-container>
  
</template>

<script>
import http from "../http-common";
import SignUp from "@/components/login/SignUp.vue";
import ImgBanner from "@/components/login/ImgBanner";
// import KakaoLogin from 'vue-kakao-login'

let onSuccess = (data) => {
  console.log(data)
  console.log("success")
}
let onFailure = (data) => {
  console.log(data)
  console.log("failure")
}

export default {
  name: "loginPage",
  components: {
    SignUp,
    ImgBanner,
    // KakaoLogin
  },
  data() {
    return {
      isMember: true,
      clickBtn: false,
      token: "",
      bannerImg: [this.getImgUrl("banner1.jpg"), this.getImgUrl("banner2.jpg")],
    };
  },
  mounted(){
    if(sessionStorage.getItem('token')){
      this.$router.push('/');
    }
  },
  methods: {
    onSuccess,
    onFailure,
    login() {
      this.clickBtn = true;
      let token = "asdfasdfasdfasdfasdf";
      let username="대래";
      let status = 2;
      // status { 1 : login ok, member ok / 2 : login ok, member : no / 3 : error }
      if (status == 1) {
        sessionStorage.setItem("token", token);
        sessionStorage.setItem("username", username);
        this.$router.go();
      } else if (status == 2) {
        sessionStorage.setItem("username", username);
        this.token = token;
        this.isMember = false;
      } else {
        this.clickBtn = false;
      }
      //   http.get(`kakaoLogin/`).then(res => {
      //     if (res.data.state == 200) {

      //     }
      //   });
    },
    getImgUrl(img) {
      return require("../assets/" + img);
    },
  }
};
</script>

<style>
</style>