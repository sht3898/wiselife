<template>
  <v-container>
    <v-flex class="ma-auto mt-5" lg10>
      <v-container fluid>
        <p>
          <span class="menu">추천 강좌/모임 👍</span>
          <v-chip
            class="mt-1"
            outlined
            :color="`grey lighten-2`"
            label
            style="float:right; text-align:right"
          >
            <weather />
          </v-chip>
        </p>
        <recommends-list />
      </v-container>

      <v-container fluid>
        <p class="menu">슬기로운 집콕 생활 🏡</p>
        <youtube />
      </v-container>

      <v-container fluid mb-8>
        <p class="menu">
          전체 카테고리
          <v-btn class="gobtn" @click="searchByCategory(0)" rounded small color="green lighten-2">이동</v-btn>
        </p>
        <v-row justify="space-around">
          <v-col class="mt-3" v-for="category in categories" :key="category.key">
            <v-img
              :src="getImgUrl(category.url)"
              width="70px"
              @click="searchByCategory(category.key)"
              style="border-radius:12px; margin:auto"
            ></v-img>
            <div class="subheading" style="text-align:center; font-size:10pt">{{category.name}}</div>
          </v-col>
        </v-row>
      </v-container>
      <v-container fluid mb-12>
        <p class="menu">인기 컨텐츠 TOP 5 🏆</p>
        <top-five style="width:97%" />
      </v-container>
    </v-flex>
  </v-container>
</template>

<script>
import RecommendsList from "@/components/contents/RecommendsList";
import TopFive from "@/components/contents/TopFive";
import Youtube from "@/components/contents/Youtube";
import Weather from "@/components/contents/Weather";
export default {
  name: "Home",
  components: {
    RecommendsList,
    TopFive,
    Youtube,
    Weather
  },
  data() {
    return {
      categories: [
        {
          key: 1,
          url: "레저,스포츠.png",
          name: "레저/스포츠",
          clicked: false
        },
        {
          key: 2,
          url: "요리.png",
          name: "요리",
          clicked: false
        },
        {
          key: 3,
          url: "수공예,공방.png",
          name: "수공예/공방",
          clicked: false
        },
        {
          key: 4,
          url: "놀이,게임.png",
          name: "놀이/게임",
          clicked: false
        },
        {
          key: 5,
          url: "문화.png",
          name: "문화",
          clicked: false
        },
        {
          key: 6,
          url: "예술.png",
          name: "예술",
          clicked: false
        },
        {
          key: 7,
          url: "축제,행사.png",
          name: "축제/행사",
          clicked: false
        },
        {
          key: 8,
          url: "기타.png",
          name: "기타",
          clicked: false
        }
      ]
    };
  },
  mounted() {
    this.checkLogin();
  },
  methods: {
    getImgUrl(img) {
      return require("../assets/categories/" + img);
    },
    searchByCategory(category) {
      var keyword = "";
      this.$router.push("/result/" + category + "/" + keyword);
    },
    checkLogin() {
      if (!localStorage.getItem("token")) {
        this.$router.push("/login");
        return;
      }
    }
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.menu {
  font-family: "Do Hyeon", sans-serif;
  font-size: 23px;
  padding-left: 10px;
}
.gobtn {
  font-size: 12pt;
  font-family: "Nanum Pen Script", cursive;
}
</style>
