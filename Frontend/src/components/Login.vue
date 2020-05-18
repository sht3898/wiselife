<template>
  <v-container fluid>
    <div v-if="!clickBtn">
      <v-card class="mx-auto" max-width="80%">
          <v-img src="../assets/wisedoctor.jpg" height="400px"></v-img>
        <v-row>
        <v-col>
          <v-img src="../assets/wiselife.png" style="margin:30px" height="110px" width="170px"></v-img>
        </v-col>
        <v-spacer></v-spacer>
        <v-col>
          <v-img
            style="cursor:pointer; margin:30px;"
            max-width="500px"
            src="../assets/kakao_login_btn_large_wide.png"
            @click="login()"
          ></v-img>
        </v-col>
        </v-row>
        <v-spacer></v-spacer>

        <v-card-title>빅데이터 기반으로 당신의 맞춤 여가생활을 추천해드립니다!</v-card-title>
      </v-card>
    </div>
    <!-- <SignUp v-if="!isMember" /> -->
  </v-container>
</template>

<script>
import http from "../http-common";
// import SignUp from "./SignUp.vue";

export default {
  name: "Login",
  components: {
    // SignUp
  },
  data() {
    return {
      isMember: true,
      clickBtn: false
    };
  },
  methods: {
    login() {
      this.clickBtn = true;
      let token = "asdfasdfasdfasdfasdf";
      let status = 1;
      // status { 1 : login ok, member ok / 2 : login ok, member : no / 3 : error }
      if (status == 1) {
        sessionStorage.setItem("token", token);
        this.$router.go();
      } else if (status == 2) {
        this.isMember = false;
      } else {
        this.clickBtn = false;
      }
      //   http.get(`kakaoLogin/`).then(res => {
      //     if (res.data.state == 200) {

      //     }
      //   });
    }
  }
};
</script>

<style>
</style>