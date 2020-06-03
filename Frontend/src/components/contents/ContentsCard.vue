<template>
  <v-card :loading="loading" class="mx-auto my-3" width="200" height="350">
    <v-img height="100" @click="goDetail" src="https://cdn.vuetifyjs.com/images/cards/cooking.png">
      <div style="z-index:5; position: relative;">
        <v-chip
          :color="`green lighten-4`"
          class="black--text"
          label
          small
          style="top: 0px; left: 5px;"
        >{{ content.area1 }} {{ content.area2 }}</v-chip>
      </div>
      <v-btn icon style="top: 110px; left: 80px;" @click="btnLike(content)">
        <v-icon v-if="content.isLike == 0">mdi-heart-outline</v-icon>
        <v-icon v-else color="red">mdi-heart</v-icon>
      </v-btn>
    </v-img>

    <v-card-title
      class="contenttitle"
      style="font-weight:bold; height:75px"
      @click="goDetail"
    >{{ content.title }}</v-card-title>

    <v-card-text style="height:175px">
      <v-row class="mx-0 mt-2" style="height:110px;">
        <v-chip
          v-for="(tag, index) in content.tags"
          :key="tag"
          :color="`${colors[index]} lighten-3`"
          class="black--text mr-2"
          label
          small
          @click="tagSearch(tag)"
        >#{{ tag }}</v-chip>
      </v-row>
      <v-row align="center" class="mx-0" style="height:20px;">
        <v-rating
          v-if="content.score != 0"
          :value="content.score"
          color="amber"
          background-color="white"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating>
        <div v-if="content.score != 0" class="grey--text ml-4">{{ content.score }}</div>
      </v-row>
      <v-row class="mx-0" style="height:20px">
        <v-col>
          <span class="mdi mdi-eye-outline" style="color:#e9c04c"></span>
          <span class="grey--text ml-4">{{ content.viewCnt }}</span>
        </v-col>
        <v-col>
          <span class="mdi mdi-heart" style="color:red"></span>
          <span class="grey--text ml-4">{{ content.likeCnt }}</span>
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "ContentsCard",
  props: {
    content: {
      type: Object,
      default: () => ({
        area1: "",
        area2: "",
        isLike: 0,
        likeCnt: "",
        meetingId: "",
        score: 0,
        tags: "",
        title: "",
        viewCnt: ""
      })
    }
  },
  data: () => ({
    loading: false,
    colors: ["lime", "amber", "orange", "teal", "indigo"]
  }),
  methods: {
    goDetail() {
      this.$router.push("/contentdetail/" + this.content.meetingId);
    },
    tagSearch(tagname) {
      this.$router.push("/result/0/" + tagname);
    },
    btnLike() {
      let params = new URLSearchParams();
      params.append("meeting_id", this.content.meetingId);
      let config = {
        headers: { access_token: sessionStorage.getItem("token") }
      };
      http
        .post(`meeting/like`, params, config)
        .then(response => {
          if (response.data.OK == "좋아요 취소") {
            this.content.isLike = 0;
            this.content.likeCnt--;
          } else {
            this.content.isLike = 1;
            this.content.likeCnt++;
          }
        })
        .catch(error => {
          alert(error);
        });
    }
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.contenttitle {
  font-size: 15pt;
  font-family: "Jua", sans-serif;
}
</style>