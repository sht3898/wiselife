<template>
  <v-container>
    <v-card-text class="text-center py-10 mt-10">
    </v-card-text>

 
    
  </v-container>
</template>

<script>
export default {
  components: {
  },
  data() {
    return {
     
    };
  },
  methods: {
    getImgUrl(img) {
      return require("../assets/" + img);
    },
   
  }
};
</script>
<style scoped>

</style>
