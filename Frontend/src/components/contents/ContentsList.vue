<template>
  <div>
    {{content}}
    <v-layout mt-1 wrap>
    <v-flex v-for="content in contents" :key="content.name" xs12 sm4 lg3 >
       <contents-card />
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
      contents: [
        
      ]
    };
  },
  
};
</script>
<style scoped>
</style>