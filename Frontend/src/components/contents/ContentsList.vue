<template>
  <div>
    <v-layout mt-1 wrap>
      <v-flex v-for="content in contentslist" :key="content.meetingId" xs12 sm4 lg3>
        <contents-card :content="content" />
      </v-flex>
    </v-layout>
  </div>
</template>
<script>
import ContentsCard from "./ContentsCard";
export default {
  name: "ContentsList",
  props: {
    contentslist: {
      type: Array,
      default: () => []
    }
  },
  components: {
    ContentsCard
  },
  data() {
    return {};
  }
};
</script>
<style scoped>
</style>