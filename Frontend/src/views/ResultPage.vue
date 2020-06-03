<template>
  <v-container class="my-5">
    <v-flex class="ma-auto" lg9 xs11>
      <h3 v-if="keyword" class="mt-5">'{{keyword}}'에 대한 검색 결과입니다.</h3>
      <v-row class="pa-0">
        <v-col>
          <v-row class="filtering">
            <v-col v-for="category in categories" :key="category.key">
              <v-btn
                :class="{bold: category.clicked}"
                @click="clickCategory(category)"
                x-small
                text
                style="text-align:center; font-size:10pt"
              >{{category.name}}</v-btn>
            </v-col>
          </v-row>
        </v-col>
        <v-col class="pl-5" cols="1">
          <span style="font-size:9pt; color:dimgrey">내 지역</span>
          <span class="ma-auto" style="float:center; text-align:center">
            <v-switch class="my-0 py-0" dense v-model="myarea" inset color="success"></v-switch>
          </span>
        </v-col>
      </v-row>
      <span class="ma-0 pa-0" v-for="sorting in sortingFilter" :key="sorting.key">
        <v-chip
          :color="`yellow lighten-4`"
          :class="{sort: sorting.clicked}"
          @click="clickSorting(sorting)"
          class="black--text mr-2"
          label
          small
        >{{sorting.name}}</v-chip>
      </span>
    </v-flex>
    <v-flex class="ma-auto" lg10 xs12>
      <contents-list v-if="ok" :contentslist="this.contentslist" />
    </v-flex>
    <div class="text-center">
      <v-pagination v-model="page" :length="pagelength" circle color="success"></v-pagination>
    </div>
  </v-container>
</template>
<script>
import http from "../http-common.js";
import ContentsList from "@/components/contents/ContentsList";
export default {
  name: "ResultPage",
  components: {
    ContentsList
  },
  data() {
    return {
      ok: false,
      page: 1,
      pagelength: 1,
      contentslist: [],

      categories: [
        {
          key: 0,
          name: "전체",
          clicked: false
        },
        {
          key: 1,
          name: "레저/스포츠",
          clicked: false
        },
        {
          key: 2,
          name: "요리",
          clicked: false
        },
        {
          key: 3,
          name: "수공예/공방",
          clicked: false
        },
        {
          key: 4,
          name: "놀이/게임",
          clicked: false
        },
        {
          key: 5,
          name: "문화",
          clicked: false
        },
        {
          key: 6,
          name: "예술",
          clicked: false
        },
        {
          key: 7,
          name: "축제/행사",
          clicked: false
        },
        {
          key: 8,
          name: "기타",
          clicked: false
        }
      ],
      selctedCategory: 0,
      myarea: 0,
      myarea1: "",
      myarea2: "",
      sortingFilter: [
        {
          key: 1,
          name: "최신순",
          clicked: false
        },
        {
          key: 2,
          name: "가나다순",
          clicked: false
        },
        {
          key: 3,
          name: "조회수순",
          clicked: false
        },
        {
          key: 4,
          name: "좋아요순",
          clicked: false
        },
        {
          key: 5,
          name: "평점순",
          clicked: false
        }
      ]
    };
  },
  mounted() {
    this.checkURL();
    this.search();
    this.userInfo();
  },
  watch: {
    myarea: "getMyArea"
  },
  computed: {
    keyword: function() {
      this.search();
      return this.$route.params.keyword;
    },
    category: function() {
      for (var i = 0; i < this.categories.length; i++) {
        if (this.categories[i].key == this.$route.params.category) {
          this.categories[i].clicked = true;
        } else {
          this.categories[i].clicked = false;
        }
      }
      this.search();
      return this.$route.params.category;
    }
  },
  methods: {
    getMyArea() {
      if (this.myarea == 1) {
        var contents = [];
        if (this.myarea2 == "전체") {
          for (var i = 0; i < this.contentslist.length; i++) {
            if (this.contentslist[i].area1 == this.myarea1) {
              contents.push(this.contentslist[i]);
            }
          }
        } else {
          for (var i = 0; i < this.contentslist.length; i++) {
            if (
              this.contentslist[i].area1 == this.myarea1 &&
              this.contentslist[i].area2 == this.myarea2
            ) {
              contents.push(this.contentslist[i]);
            }
          }
        }
        this.contentslist = contents;
      } else {
        for (var i = 0; i < this.sortingFilter.length; i++) {
          this.sortingFilter[i].clicked = false;
        }
        this.search();
      }
    },
    userInfo() {
      let config = {
        headers: { access_token: sessionStorage.getItem("token") }
      };
      http
        .get(`user/info/`, config)
        .then(response => {
          console.log(response.data);
          this.myarea1 = response.data.info.userinfo.area1;
          this.myarea2 = response.data.info.userinfo.area2;
        })
        .catch(error => {
          alert(error);
          this.$router.push("/");
        });
    },
    search() {
      let config = {
        headers: { access_token: sessionStorage.getItem("token") }
      };
      if (this.$route.params.keyword == null) {
        this.$route.params.keyword = "";
      }
      this.ok = false;
      http
        .get(
          `search/${this.$route.params.category}?keyword=` +
            this.$route.params.keyword,
          config
        )
        .then(response => {
          if(response.data.OK != null){
            
          }else{
            this.contentslist = response.data;
            for (var i = 0; i < this.contentslist.length; i++) {
              this.contentslist[i].tags = this.contentslist[i].tags.split(" ");
                if(this.contentslist[i].meetingImages != null){
                  this.contentslist[i].meetingImages = this.contentslist[i].meetingImages[0];
                }
            }
            this.ok = true;
            this.pagelength = Math.floor(this.contentslist.length / 12) + 1;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    clickCategory(category) {
      for (var i = 0; i < this.categories.length; i++) {
        if (this.categories[i] != category) {
          this.categories[i].clicked = false;
        }
      }
      category.clicked = true;
      this.$router.push(
        "/result/" + category.key + "/" + this.$route.params.keyword
      );
    },
    clickSorting(sorting) {
      this.ok = false;
      for (var i = 0; i < this.sortingFilter.length; i++) {
        if (this.sortingFilter[i] != sorting) {
          this.sortingFilter[i].clicked = false;
        }
      }
      sorting.clicked = true;
      switch (sorting.key) {
        case 1:
          this.contentslist.sort(function(a, b) {
            return b.meetingId - a.meetingId;
          });
          break;
        case 2:
          this.contentslist.sort(function(a, b) {
            return a.title < b.title ? -1 : a.title > b.title ? 1 : 0;
          });
          break;
        case 3:
          this.contentslist.sort(function(a, b) {
            return b.viewCnt - a.viewCnt;
          });
          break;
        case 4:
          this.contentslist.sort(function(a, b) {
            return b.likeCnt - a.likeCnt;
          });
          break;
        case 5:
          this.contentslist.sort(function(a, b) {
            return b.score - a.score;
          });
          break;
      }
      this.ok = true;
    },
    checkURL() {
      for (var i = 0; i < this.categories.length; i++) {
        if (this.categories[i].key == this.$route.params.category) {
          this.categories[i].clicked = true;
        }
      }
    }
  }
};
</script>
<style scoped>
.filtering {
  background-color: GhostWhite;
  border-radius: 12px;
}
.bold {
  font-weight: bold;
  color: darkgreen;
}
.sort {
  font-weight: bold;
  color: darkorange !important;
}
</style>
