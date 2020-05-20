<template>
  <v-card :loading="loading" class="mx-auto my-12" width="200">
    <v-img height="150" src="https://cdn.vuetifyjs.com/images/cards/cooking.png">
      <v-btn icon style="z-index:5; position: relative; top: 110px; left: 160px;">
        <v-icon color="white" style="opacity:0.7">mdi-heart</v-icon>
      </v-btn>
    </v-img>

    <v-card-title style="font-weight:bold">강좌명</v-card-title>

    <v-card-text>

      <div class="my-1 subtitle-1" style="color:green; font-weight:bold">#태그 #태그 #태그</div>
      <v-row align="center" class="mx-0 my-0">
        <v-rating :value="4.5" color="amber" dense half-increments readonly size="14"></v-rating>

        <div class="grey--text ml-4">4.5</div>
        
      </v-row>
      <v-row align="center" class="mx-1 my-0">
          <span class="mdi mdi-eye-outline" style="color:#e9c04c"></span>
          <div class="grey--text ml-4">36 views</div>
      </v-row>
    

    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name:'contentsCard',
  data: () => ({
    loading: false,
    selection: 1
  }),

  methods: {
    reserve() {
      this.loading = true;

      setTimeout(() => (this.loading = false), 2000);
    }
  }
};
</script>
<style scoped>

</style>