<template>
  <div>
    <v-app-bar class="toolbar" id="app-toolbar" flat color="white">
      <v-toolbar-title>
        <v-img :src="getImgUrl('wiselife.png')" height="60px" width="100px" @click="gohome()" />
      </v-toolbar-title>
      <v-spacer />
      <v-toolbar-items>
        <div class="searchbar mt-3 mr-3 pr-2" style="width:500px">
          <v-text-field
          prefix="🔎"
            placeholder="강좌/모임 검색"
            @keyup.enter="goSearch(researchValue)"
            filled
            rounded
            dense
            v-model="researchValue"
          ></v-text-field>
        </div>
        <div >
        <v-btn rounded class="infotext mt-3 orange lighten-2" @click="insertContent">강좌/모임 만들기 ✒️</v-btn>
        </div>
        <v-btn text v-if="isLogin" class="text-center" style="padding-top:20px;">
          <v-menu offset-y open-on-hover>
            <template v-slot:activator="{ on }">
              <span class="infotext" text v-on="on" style="padding-bottom:15px; font-size:18pt; color:dimgrey">
                <span class="mdi mdi-account-circle"></span>
                {{name}} 님
              </span>
            </template>

            <v-list dense shaped width="150px">
              <v-list-item @click="entermypage">
                <v-list-item-action>
                  <v-list-item-content
                    style="font-weight:bold; font-size:15px; color:dimgrey"
                  >My Page</v-list-item-content>
                </v-list-item-action>
              </v-list-item>
              <v-list-item @click="logout">
                <v-list-item-action>
                  <v-list-item-content
                    style="font-weight:bold; font-size:15px; color:dimgrey"
                  >Logout</v-list-item-content>
                </v-list-item-action>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-btn>
        <v-btn v-else text width="15%" color="black">Login</v-btn>
      </v-toolbar-items>
    </v-app-bar>
  </div>
</template>

<script>
import http from "../../http-common";
import jwtDecode from "jwt-decode";

export default {
  name:'toolbar',
  components: {},
  data: () => ({
    responsive: false,
    id: "",
    isLogin: false,
    email: "",
    name: "",
    researchValue: ""
  }),
  computed: {},
  mounted() {
    if (sessionStorage.getItem("token") != null) {
      this.isLogin = true;
      const token = sessionStorage.getItem("token");
      const requestHeader = {
        headers: {
          Authorization: "JWT " + token
        }
      };
      const user_pk = jwtDecode(token).user_id;
      sessionStorage.setItem("user_pk", user_pk);
      http.get(`accounts/userdetail/${user_pk}/`, requestHeader).then(res => {
        this.email = res.data.email;
        this.name = res.data.nickname;
        sessionStorage.setItem("user_name", res.data.nickname);
        sessionStorage.setItem("busi_exp", res.data.business_exp);
        sessionStorage.setItem("interest_area", res.data.interest_area);
      });
    }
    this.onResponsiveInverted();
    window.addEventListener("resize", this.onResponsiveInverted);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.onResponsiveInverted);
  },
  methods: {
    onResponsiveInverted() {
      if (window.innerWidth < 900) {
        this.responsive = true;
      } else {
        this.responsive = false;
      }
    },
    getImgUrl(img) {
      return require("../../assets/" + img);
    },
    gohome() {
      this.$router.push("/");
    },
    goSearch(keyword) {
      this.researchValue = ''
      this.$router.push("/result/0/"+keyword)
    },
    insertContent(){
      this.$router.push("/contentwrite")
    },
    /////////////////// 회원 ////////////////////////
    getUserName() {
      this.id = sessionStorage.getItem("id");
    },
    
    logout() {
      let conf = confirm("로그아웃 하시겠습니까?");
      if (conf == true) {
        sessionStorage.clear();
        this.$router.push("/");
        this.$router.go();
      }
    },
    /////////////////////////////////////////////////////
    entermypage() {
      this.$router.push("/mypage");
    }
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.infotext {
  font-family: "Nanum Pen Script", cursive;
}
.toolbar {  
  min-height: 100px;
  max-width: 1000px;
  margin: auto;
  padding-top: 20px;
  position: absolute;
}
</style>
