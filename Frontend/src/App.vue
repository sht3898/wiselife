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
    <Login />
  </v-app>
</template>

<script>
import Toolbar from "@/components/Toolbar";
import GoTop from "@/components/GoTop";
import Footer from "@/components/Footer";
import Login from "@/components/Login";

export default {
  components: {
    Toolbar,
    GoTop,
    Footer,
    Login
  },
  data() {
    return {
      isLogin: sessionStorage.getItem("token")
    };
  }
};
</script>
