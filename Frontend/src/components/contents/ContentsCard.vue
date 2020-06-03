<template>
  <v-card :loading="loading" class="mx-auto my-3" width="200" height="350">
    <v-img
      height="100px"
      @click="goDetail"
      :src="`http://k02b1051.p.ssafy.io/`+ content.meetingImages"
    >
      <div style="z-index:5; position: relative;">
        <v-chip
          :color="`green lighten-4`"
          class="black--text"
          label
          small
          style="top: 0px; left: 5px;"
        >{{ content.area1 }} {{ content.area2 }}</v-chip>
      </div>
      <v-btn icon style="top: 42px; left: 160px;" @click="btnLike(content)">
        <v-icon v-if="content.isLike == 0">mdi-heart-outline</v-icon>
        <v-icon v-else color="red">mdi-heart</v-icon>
      </v-btn>
    </v-img>

    <v-card-title
      class="contenttitle"
      style="font-weight:bold; height:95px"
      @click="goDetail"
    >{{ content.title }}</v-card-title>

    <v-card-text style="height:175px">
      <div class="mx-0 mt-2" style="height:80px;">
        <v-chip
          v-for="(tag, index) in content.tags"
          :key="tag"
          :color="`${colors[index]} lighten-3`"
          class="mr-2"
          label
          x-small
          @click="tagSearch(tag)"
        >#{{ tag }}</v-chip>
      </div>
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