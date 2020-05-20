<template>
  <v-app v-if="isLogin" id="app">
    <toolbar />
    <v-divider></v-divider>
    <router-view class="mt-12 pt-12" />
    <go-top />
    <v-divider></v-divider>
    <Footer />
  </v-app>
  <v-app v-else id="app">
    <router-view />
  </v-app>
</template>

<script>
import Toolbar from "@/components/Toolbar";
import GoTop from "@/components/GoTop";
import Footer from "@/components/Footer";

export default {
  components: {
    Toolbar,
    GoTop,
    Footer,
  },
  data() {
    return {
      isLogin: sessionStorage.getItem("token")
    };
  },
  created(){
    if(!this.isLogin){
      this.$router.push('/login');
    }
  }
};
</script>
