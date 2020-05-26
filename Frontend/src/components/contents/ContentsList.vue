<template>
  <div>
    {{content}}
    <v-layout mt-1 wrap>
    <v-flex v-for="item in items" :key="item.name" xs6 sm4 lg3 >
       <contents-card />
    </v-flex>
  </v-layout>
    
  </div>
</template>
<script>
import ContentsCard from "./ContentsCard";
export default {
  name: "ContentsList",
  props: {
    content: {
      type: String
    }
  },
  components: {
    ContentsCard
  },
  data() {
    return {
      items: [
        { name: "Kin Khao", tag: ["Thai"] },
        { name: "Jū-Ni", tag: ["Sushi", "Japanese", "$$$$"] },
        { name: "Delfina", tag: ["Pizza", "Casual"] },
        { name: "San Tung", tag: ["Chinese", "$$"] },
        { name: "Anchor Oyster Bar", tag: ["Seafood", "Cioppino"] },
        { name: "Locanda", tag: ["Italian"] },
        { name: "Garden Creamery", tag: ["Ice cream"] }
      ]
    };
  }
};
</script>
<style scoped>
</style>