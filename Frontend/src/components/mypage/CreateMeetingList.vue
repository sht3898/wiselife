<template>
  <v-flex class="ma-auto">
    <div class="card-carousel-wrapper">
      <div class="card-carousel--nav__left" :disabled="atHeadOfList" @click="moveCarousel(-1)" />
      <div class="card-carousel">
        <div class="card-carousel--overflow-container">
          <div
            class="card-carousel-cards"
            :style="{ transform: 'translateX' + '(' + currentOffset + 'px' + ')'}"
          >
            <div v-for="item in createlist" :key="item.name" class="card-carousel--card">
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
import ContentsCard from "../contents/ContentsCard";
export default {
  name: "CreateMeetingList",
  components: {
    ContentsCard
  },
  data() {
    return {
      currentOffset: 0,
      windowSize: 4,
      paginationFactor: 220
    };
  },
  props: {
    createlist: { type: Array }
  },
  computed: {
    atEndOfList() {
      return (
        this.currentOffset <=
        this.paginationFactor *
          -1 *
          (this.createlist.length - this.windowSize + 2)
      );
    },
    atHeadOfList() {
      return this.currentOffset === 0;
    }
  },
  methods: {
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
  width: 42vw;
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
  margin: 0 10px;
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