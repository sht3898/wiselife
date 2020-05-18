<template>
  <v-app id="app">
    <toolbar />
    <router-view/>
    <go-top />
    <Footer />
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
    Footer
  },
};
</script>
