<template>
  <div>
    <v-layout mt-1 wrap>
    <v-flex v-for="content in this.contents" :key="content.title" xs12 sm4 lg3 >
       <contents-card :content="content"/>
    </v-flex>
  </v-layout>
    
  </div>
</template>
<script>
import ContentsCard from "./ContentsCard";
import http from '../../http-common';
export default {
  name: "ContentsList",
  props: {
    contentslist: {
      type: Array
    }
  },
  components: {
    ContentsCard
  },
  data() {
    return {
      contents: []
    };
  },
  mounted(){   
      this.contents=this.contentslist;  
      }
};
</script>
<style scoped>
</style>