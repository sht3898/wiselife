<template>
  <v-container fluid>
    <div v-if="!clickBtn">
      <div class="mx-auto">
        <img-banner :imgSrc="bannerImg" />
        <v-row>
          <v-col>
            <div style="padding:60px;">
              <v-img src="../assets/wiselife.png" style="margin:auto;" height="100%" width="350px"></v-img>
            </div>
          </v-col>
          <v-col>
            <div style="padding:45px;">
              <p class="maintext" style="text-align:center;">
                빅데이터 기반으로
                <br />당신의 맞춤 여가생활을
                <br />추천해드립니다!
              </p>
            </div>

            <div style="padding:30px;">
              <v-img
                style="cursor:pointer; margin:auto"
                max-width="450px"
                src="../assets/kakao_login_btn_large_wide.png"
                @click="login()"
              ></v-img>
            </div>
          </v-col>
        </v-row>
      </div>
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
  name: "LoginPage",
  components: {
    SignUp,
    ImgBanner
  },
  data() {
    return {
      isMember: true,
      clickBtn: false,
      token: "",
      bannerImg: [
        this.getImgUrl("wiselifeimgbanner1.png"),
        this.getImgUrl("wiselifeimgbanner2.png"),
        this.getImgUrl("wiselifeimgbanner3.png"),
        this.getImgUrl("wiselifeimgbanner4.png")
      ]
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
            access_token: authObj.access_token
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
    },
    getImgUrl(img) {
      return require("../assets/" + img);
    }
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.maintext {
  font-size: 16pt;
  font-family: "Jua", sans-serif;
}
</style>