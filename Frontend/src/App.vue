<template>
  <v-app id="app">
    <toolbar v-if="isLogin"/>
    <router-view class="mt-12 pt-12" />
    <go-top v-if="isLogin"/>    
    <Footer v-if="isLogin"/>
  </v-app>
</template>

<script>
import Toolbar from "@/components/layouts/Toolbar";
import GoTop from "@/components/layouts/GoTop";
import Footer from "@/components/layouts/Footer";

export default {
  components: {
    Toolbar,
    GoTop,
    Footer,
  },
  data() {
    return {
      isLogin: "",
    };
  },
  created(){
    this.isLogin = sessionStorage.getItem("token");
    if(!this.isLogin){
      this.$router.push('/login');
    }
  }
};
</script>
