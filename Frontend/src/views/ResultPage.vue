<template>
  <v-container class="my-5">
    <v-flex class="ma-auto" lg9 xs11>
      <h3 v-if="keyword" class="mt-5">'{{keyword}}'에 대한 검색 결과입니다.</h3>
      <v-row class="hidden-xs-only justify-content-around pa-0">
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
      <span
        class="hidden-xs-only justify-content-around ma-0 pa-0"
        v-for="sorting in sortingFilter"
        :key="sorting.key"
      >
        <v-chip
          :color="`yellow lighten-4`"
          :class="{sort: sorting.clicked}"
          @click="clickSorting(sorting)"
          class="black--text mr-2"
          label
          small
        >{{sorting.name}}</v-chip>
      </span>
      <v-card outlined class="hidden-sm-and-up">
        <v-row>
          <v-col cols="12" class="pl-5">
            <span style="font-size:9pt; color:dimgrey">내 지역</span>
            <span class="ma-auto" style="float:center; text-align:center">
              <v-switch class="ml-1 my-0 py-0" dense v-model="myarea" inset color="success"></v-switch>
            </span>
          </v-col>
          <v-col cols="6" class="pl-5">
            <v-overflow-btn
              v-model="mainCategory"
              :items="mainCategories"
              item-value="value"
              item-text="text"
              label="카테고리"
              dense
            ></v-overflow-btn>
          </v-col>
          <v-col cols="6" class="pr-5">
            <v-overflow-btn
              v-model="smsort"
              :items="sortingFilter"
              item-value="key"
              item-text="name"
              label="정렬방식"
              dense
            ></v-overflow-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-flex>
    <v-flex class="ma-auto" lg10 xs12>
      <v-row v-if="!ok" justify="center" align="center">
        <v-content class="text-center progress">
          <v-progress-circular
            indeterminate
            :rotate="0"
            :size="50"
            :value="0"
            :width="7"
            color="orange"
            class="ma-auto"
          />
        </v-content>
      </v-row>
      <div v-else>
        <v-content
          v-if="contentslist.length == 0"
          class="my-10"
          style="text-align:center; color:grey; font-weight:bold"
        >검색된 강좌/모임이 없습니다!</v-content>

        <contents-list v-else :contentslist="page_list" />
        <div v-if="contentslist.length != 0" class="text-center">
          <v-pagination
            v-model="page"
            :total-visible="7"
            :length="pagelength"
            circle
            color="success"
          ></v-pagination>
        </div>
      </div>
    </v-flex>
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
      mainCategory: Number(this.$route.params.category),
      mainCategories: [
        { text: "전체", value: 0 },
        { text: "레저/스포츠", value: 1 },
        { text: "요리", value: 2 },
        { text: "수공예/공방", value: 3 },
        { text: "놀이/게임", value: 4 },
        { text: "문화", value: 5 },
        { text: "예술", value: 6 },
        { text: "축제/행사", value: 7 },
        { text: "기타", value: 8 }
      ],
      contentslist: [],
      page_list: [],
      smsort: 0,
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
  computed: {
    keyword: function() {
      this.search();
      return this.$route.params.keyword;
    },
    category: function() {
      this.search();
      return this.$route.params.category;
    }
  },
  watch: {
    myarea: "getMyArea",
    page: "pagination",
    mainCategory: "smClickCategory",
    smsort: "smSort"
  },
  methods: {
    smSort() {
      switch (this.smsort) {
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
      this.page = 1;
      this.pagination();
      this.ok = true;
    },
    smClickCategory() {
      let keyword = "";
      if (
        this.$route.params.keyword != null &&
        this.$route.params.keyword != "undefined"
      ) {
        keyword = this.$route.params.keyword;
      }
      this.$router.push("/result/" + this.mainCategory + "/" + keyword);
    },
    pagination() {
      this.ok = false;
      var page_list = [];
      for (var i = (this.page - 1) * 12; i < (this.page - 1) * 12 + 12; i++) {
        if (i == this.contentslist.length) {
          break;
        }
        page_list.push(this.contentslist[i]);
      }
      this.page_list = page_list;
      this.ok = true;
    },
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
          for (var j = 0; j < this.contentslist.length; j++) {
            if (
              this.contentslist[j].area1 == this.myarea1 &&
              this.contentslist[j].area2 == this.myarea2
            ) {
              contents.push(this.contentslist[j]);
            }
          }
        }
        this.contentslist = contents;
        this.pagelength = Math.floor(this.contentslist.length / 12) + 1;
        this.page = 1;
        this.pagination();
      } else {
        for (var k = 0; k < this.sortingFilter.length; k++) {
          this.sortingFilter[k].clicked = false;
        }
        this.page = 1;
        this.search();
      }
    },
    userInfo() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .get(`user/info/`, config)
        .then(response => {
          this.myarea1 = response.data.info.userinfo.area1;
          this.myarea2 = response.data.info.userinfo.area2;
        })
        .catch(error => {
          alert(error);
          this.$router.push("/");
        });
    },
    search() {
      for (var i = 0; i < this.categories.length; i++) {
        if (this.categories[i].key == this.$route.params.category) {
          this.categories[i].clicked = true;
        } else {
          this.categories[i].clicked = false;
        }
      }
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      let keyword = "";
      if (
        this.$route.params.keyword != null &&
        this.$route.params.keyword != "undefined"
      ) {
        keyword = this.$route.params.keyword;
      }
      this.ok = false;
      http
        .get(`search/${this.$route.params.category}?keyword=` + keyword, config)
        .then(response => {
          if (response.data.OK != null) {
            this.contentslist = [];
            this.ok = true;
          } else {
            this.contentslist = response.data;

            for (var i = 0; i < this.contentslist.length; i++) {
              if (this.contentslist[i].tags != null) {
                let split_tags = this.contentslist[i].tags.split(" ");
                let tags = [];
                for (var j in split_tags) {
                  tags.push(split_tags[j]);
                }
                tags = Array.from(new Set(tags));
                this.contentslist[i].tags = tags;
              }
              if (this.contentslist[i].meetingImages.length != 0) {
                if (
                  this.contentslist[i].meetingImages[0].substring(2, 3) == "t"
                ) {
                  if (
                    this.contentslist[i].meetingImages[0].substring(0, 1) == '"'
                  ) {
                    let length = this.contentslist[i].meetingImages[0].length;
                    this.contentslist[i].meetingImages[0] = this.contentslist[
                      i
                    ].meetingImages[0].substring(1, length - 1);
                  }
                  this.contentslist[i].meetingImages = this.contentslist[
                    i
                  ].meetingImages[0].split(" ")[0];
                  if (
                    this.contentslist[i].meetingImages[0].substring(0, 1) == "h"
                  ) {
                    this.contentslist[i].isUrl = true;
                  } else {
                    this.contentslist[i].isUrl = false;
                  }
                } else {
                  this.contentslist[i].meetingImages = this.contentslist[
                    i
                  ].meetingImages[0];
                }
              } else {
                this.contentslist[i].meetingImages = null;
              }
            }
            this.pagelength = Math.floor(this.contentslist.length / 12) + 1;
            if (this.myarea == 1) {
              this.getMyArea();
            } else {
              this.pagination();
            }
          }
        })
        .catch(() => {});
    },
    clickCategory(category) {
      for (var i = 0; i < this.categories.length; i++) {
        if (this.categories[i] != category) {
          this.categories[i].clicked = false;
        }
      }
      category.clicked = true;
      let keyword = "";
      if (
        this.$route.params.keyword != null &&
        this.$route.params.keyword != "undefined"
      ) {
        keyword = this.$route.params.keyword;
      }
      this.$router.push("/result/" + category.key + "/" + keyword);
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
      this.page = 1;
      this.pagination();
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
.progress {
  margin: 100px;
  padding: 100px;
}
</style>
