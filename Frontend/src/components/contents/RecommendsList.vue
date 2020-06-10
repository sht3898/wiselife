<template>
  <v-flex class="ma-auto">
    <div v-if="ok" class="card-carousel-wrapper">
      <div class="card-carousel--nav__left" :disabled="atHeadOfList" @click="moveCarousel(-1)" />
      <div class="card-carousel">
        <div class="card-carousel--overflow-container">
          <div
            class="card-carousel-cards"
            :style="{ transform: 'translateX' + '(' + currentOffset + 'px' + ')'}"
          >
            <div v-for="item in items" :key="item.name" class="card-carousel--card">
              <contents-card :content="item" />
            </div>
          </div>
        </div>
      </div>
      <div class="card-carousel--nav__right" :disabled="atEndOfList" @click="moveCarousel(1)" />
    </div>
  </v-flex>
</template>
<script>
import http from "../../http-common.js";
import axios from "axios";
import ContentsCard from "./ContentsCard";
export default {
  name: "RecommendsList",
  components: {
    ContentsCard
  },
  data() {
    return {
      ok: false,
      uid: 0,
      currentOffset: 0,
      windowSize: 4,
      paginationFactor: 220,
      items: []
    };
  },
  computed: {
    atEndOfList() {
      return (
        this.currentOffset <
        this.paginationFactor * -1 * (this.items.length - this.windowSize)
      );
    },
    atHeadOfList() {
      return this.currentOffset === 0;
    }
  },
  mounted() {
    this.getUserInfo();
    if (window.innerWidth < 960) {
      this.windowSize = 2;
    } else if (window.innerWidth < 1264) {
      this.windowSize = 3;
    } else {
      this.windowSize = 4;
    }
  },
  methods: {
    getUserInfo() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .get(`user/info/`, config)
        .then(response => {
          this.uid = response.data.info.userinfo.uid;
          this.getList();
        })
        .catch(() => {
          location.reload();
        });
    },
    getList() {
      // let config = {
      //   headers: { access_token: localStorage.getItem("token") }
      // };
      this.ok = false;
      axios
        .get(`http://13.125.114.122:8000/api/randomrecommend/` + this.uid + "/")
        .then(response => {
          this.items = response.data;
          for (var i = 0; i < this.items.length; i++) {
            this.items[i]["meetingId"] = response.data[i].meeting_id;
            this.items[i]["likeCnt"] = response.data[i].like_cnt;
            this.items[i]["viewCnt"] = response.data[i].view_cnt;
            this.items[i]["meetingImages"] = response.data[i].image_url;
            this.items[i]["isLike"] = response.data[i].is_like;
            let split_tags = this.items[i].tags.split(" ");
            let tags = [];
            for (var j in split_tags) {
              tags.push(split_tags[j]);
            }
            tags = Array.from(new Set(tags));
            this.items[i].tags = tags;
            if (this.items[i].meetingImages.length != 0) {
              if (this.items[i].meetingImages[0].substring(2, 3) == "t") {
                if (this.items[i].meetingImages[0].substring(0, 1) == '"') {
                  let length = this.items[i].meetingImages[0].length;
                  this.items[i].meetingImages[0] = this.items[
                    i
                  ].meetingImages[0].substring(1, length - 1);
                }
                this.items[i].meetingImages = this.items[
                  i
                ].meetingImages[0].split(" ")[0];
                if (this.items[i].meetingImages[0].substring(0, 1) == "h") {
                  this.items[i].isUrl = true;
                } else {
                  this.items[i].isUrl = false;
                }
              } else {
                this.items[i].meetingImages = this.items[i].meetingImages[0];
              }
            } else {
              this.items[i].meetingImages = null;
            }
          }

          this.ok = true;
        })
        .catch(() => {
          alert("추천 리스트 가져오기 실패..");
        });
    },
    moveCarousel(direction) {
      // Find a more elegant way to express the :style. consider using props to make it truly generic
      if (direction === 1 && !this.atEndOfList) {
        this.currentOffset -= this.paginationFactor;
      } else if (direction === -1 && !this.atHeadOfList) {
        this.currentOffset += this.paginationFactor;
      }
    }
  }
};
</script>
<style scoped>
body {
  background: #f8f8f8;
  color: #2c3e50;
  font-family: "Source Sans Pro", sans-serif;
}

.card-carousel-wrapper {
  height: 360px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0 20px;
  color: #666a73;
}

.card-carousel {
  display: flex;
  justify-content: center;
  width: 60vw;
}
.card-carousel--overflow-container {
  overflow: hidden;
}
.card-carousel--nav__left,
.card-carousel--nav__right {
  display: inline-block;
  width: 15px;
  height: 15px;
  padding: 10px;
  box-sizing: border-box;
  border-top: 2px solid #42b883;
  border-right: 2px solid #42b883;
  cursor: pointer;
  margin: 0 20px;
  transition: transform 150ms linear;
}
.card-carousel--nav__left[disabled],
.card-carousel--nav__right[disabled] {
  opacity: 0.2;
  border-color: black;
}
.card-carousel--nav__left {
  transform: rotate(-135deg);
}
.card-carousel--nav__left:active {
  transform: rotate(-135deg) scale(0.9);
}
.card-carousel--nav__right {
  transform: rotate(45deg);
}
.card-carousel--nav__right:active {
  transform: rotate(45deg) scale(0.9);
}

.card-carousel-cards {
  display: flex;
  transition: transform 150ms ease-out;
  transform: translatex(0px);
}
.card-carousel-cards .card-carousel--card {
  padding-left: 1px;
  margin: 0 10px;
  cursor: pointer;
  background-color: #fff;
  border-radius: 4px;
  z-index: 3;
}
.card-carousel-cards .card-carousel--card:first-child {
  margin-left: 0;
}
.card-carousel-cards .card-carousel--card:last-child {
  margin-right: 0;
}
.card-carousel-cards .card-carousel--card img {
  vertical-align: bottom;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  transition: opacity 150ms linear;
  user-select: none;
}
.card-carousel-cards .card-carousel--card img:hover {
  opacity: 0.5;
}

h1 {
  font-size: 3.6em;
  font-weight: 100;
  text-align: center;
  margin-bottom: 0;
  color: #42b883;
}
</style>