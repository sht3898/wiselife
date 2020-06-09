<template>
  <div @click="onVideoSelect()">
    <v-row>
      <v-col cols="12" sm="5">
        <img :src="imgUrl" style="height:80px" />
      </v-col>
      <v-col cols="12" sm="7">
        <p class="subtitle is-6" style="font-size:10pt">{{ videoTitle }}</p>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  props: {
    video: Object,
    imgUrl: String,
    videoTitle: String
  },
  methods: {
    onVideoSelect() {
      this.$emit("selectedVideo", this.video);
    }
  }
};
</script>

<style>
.tile.is-child:hover {
  cursor: pointer;
}
</style>