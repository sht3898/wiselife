<template>
  <section class="section">
    <div class="container">
      <div v-if="ok">
        <v-row>
          <v-col cols="12" sm="7">
            <VideoDetail :selectedVideo="selectedVideo" :videos="videos" />
          </v-col>
          <v-col cols="12" sm="5">
            <div class="tile" style="padding-right:10px; overflow-y:scroll; height:340px;">
              <article v-for="video in videos" :key="video.etag">
                <VideoList
                  @selectedVideo="videoSelect($event)"
                  :video="video"
                  :imgUrl="video.snippet.thumbnails.medium.url"
                  :videoTitle="video.snippet.title"
                />
              </article>
            </div>
          </v-col>
        </v-row>
      </div>
    </div>
    <!-- /.container -->
  </section>
</template>

<script>
import YouTubeSearch from "youtube-api-search";
import VideoList from "./VideoList";
import VideoDetail from "./VideoDetail";
export default {
  name: "Youtube",
  data() {
    return {
      ok:false,
      video: "",
      videos: [],
      selectedVideo: "",
      searchkeyword: [
        "집에서 즐기는 취미",
        "집에서 즐기는 홈",
        "홈코노미",
        "홈카페",
        "홈트레이닝",
        "홈캠핑",
        "방구석캠핑",
        "방구석취미",
        "방구석콘서트",
        "집에서 시간보내기",
        "취미 미술",
        "홈쿠킹",
        "홈베이킹",
        "슬기로운 집콕생활",
        "집콕 여가"
      ]
    };
  },
  components: {
    VideoList,
    VideoDetail
  },
  created() {
    // init search
    let index = Math.floor(Math.random() * (this.searchkeyword.length - 1));
    let keyword = this.searchkeyword[index];
    this.videoSearch(keyword);
  },
  methods: {
    videoSearch(searchTerm) {
      YouTubeSearch(
        { key: "AIzaSyD2ipx6PqlX5qy6cAWXfwg265izg7YPe2Y", term: searchTerm },
        videos => {
          this.videos = videos;
          // get the first video (before one is selected --this is the default)
          this.selectedVideo = videos[0];
          this.ok=true;
        }
      );
    },
    videoSelect(video) {
      this.selectedVideo = video;
    }
  }
};
</script>
<style scoped>
::-webkit-scrollbar {
  width: 5px;  /* 세로축 스크롤바 길이 */
  height: 0px; /* 가로축 스크롤바 길이 */
}
::-webkit-scrollbar-track {
  background-color: lightgray;
}
::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: grey;
}
</style>

