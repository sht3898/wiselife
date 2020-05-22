<template>
  <v-container fluid>
    <div v-if="!clickBtn">
      <v-card class="mx-auto" max-width="75%">
        <v-row>
          <v-col class="hidden-sm-and-down">
            <img-banner :imgSrc="bannerImg" />
          </v-col>
          <v-col>
            <div style="padding:50px;">
              <v-img src="../assets/wiselife.png" style="margin:auto;" height="110px" width="170px"></v-img>
            </div>
            <v-spacer></v-spacer>
            <div style="padding:50px;">
              <h2 style="text-align:center;">
                빅데이터 기반으로
                <br />당신의 맞춤 여가생활을
                <br />추천해드립니다!
              </h2>
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
  </v-container>
</template>

<script>
import http from "../http-common";
import SignUp from "@/components/login/SignUp";
import ImgBanner from "@/components/login/ImgBanner";

Kakao.init("c2d9f09a902e77b8550b754cdb90d407");

export default {
  name: "loginPage",
  components: {
    SignUp,
    ImgBanner
  },
  data() {
    return {
      isMember: true,
      clickBtn: false,
      token: "",
      bannerImg: [this.getImgUrl("banner1.jpg"), this.getImgUrl("banner2.jpg")]
    };
  },
  mounted() {
    if (sessionStorage.getItem("token")) {
      this.$router.push("/");
    }
  },
  methods: {
    login() {
      this.clickBtn = true;
      let this_component = this;
      Kakao.Auth.login({
        success: function(authObj) {
          let headers = {
            access_token: authObj.access_token,
          };
          http
            .get(`user/login`, {
              headers
            })
            .then(response => {
              if (response.data.status) {
                var username = "";
                Kakao.API.request({
                  url: "/v2/user/me",
                  success: function(response) {
                    username = response.properties.nickname;
                  },
                  fail: function(error) {
                    alert("회원 정보를 가져오는데 실패했습니다!");
                  }
                });
                sessionStorage.setItem("token", authObj.access_token);
                sessionStorage.setItem("username", username);
                this_component.$router.go();
              } else {
                this_component.isMember = false;
                this_component.token = authObj.access_token;
              }
            });
        },
        fail: function(err) {
          console.log(err);
          alert("로그인에 실패하였습니다! 다시 시도해주세요!");
          this.clickBtn = false;
          return;
        }
      });
      // this.clickBtn = true;
      // let token = "asdfasdfasdfasdfasdf";
      // let username = "대래";
      // let status = 2;
      // // status { 1 : login ok, member ok / 2 : login ok, member : no / 3 : error }
      // if (status == 1) {
      //   sessionStorage.setItem("token", token);
      //   sessionStorage.setItem("username", username);
      //   this.$router.go();
      // } else if (status == 2) {
      //   sessionStorage.setItem("username", username);
      //   this.token = token;
      //   this.isMember = false;
      // } else {
      //   this.clickBtn = false;
      // }
      //   http.get(`kakaoLogin/`).then(res => {
      //     if (res.data.state == 200) {

      //     }
      //   });
    },
    getImgUrl(img) {
      return require("../assets/" + img);
    }
  }
};
</script>

<style>
</style>