<template>
  <div>
    <v-carousel
      v-if="imgSrc.length"
      height="auto"
      :hide-delimiters="true"
      :show-arrows="false"
      :cycle="true"
      interval="3000"
    >
      <v-carousel-item v-for="k in 2" :key="k">
        <v-img :src="imgSrc[k-1]" height="600px" :aspect-ratio="0.8"></v-img>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>



<script>
export default {
  name: "ImgBanner",
  props: {
    imgSrc: { type: Array },
    text: { type: String }
  },
  methods: {}
};
</script>
<style>
.text-shadow {
  text-shadow: 0 0 15px rgb(255, 255, 255);
}
#desk {
  display: block;
}
#mobile {
  display: none;
}
@media only screen and (max-width: 600px) {
  #desk {
    display: none;
  }
  #mobile {
    display: block;
  }
}
</style>