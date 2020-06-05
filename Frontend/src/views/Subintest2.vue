<template>
    <section class="section">
      <div class="container">
        <div class="tile is-ancestor">
          <div class="tile is-parent is-9">
            <article class="tile is-child">
              <div v-if="show">
                <VideoDetail :selectedVideo="selectedVideo" :videos="videos" />
              </div>
              <div v-else>
                <h4>...Loading ⟳</h4>
              </div>
            </article>
          </div>
          <div class="tile is-parent is-3 is-vertical">
            <article v-for="video in videos" :key="video.etag">
              <VideoList
                @selectedVideo="videoSelect($event)"
                :video="video"
                :imgUrl="video.snippet.thumbnails.medium.url"
                :videoTitle="video.snippet.title" />
            </article>
          </div>
        </div>
      </div> <!-- /.container -->
    </section>
</template>

<script>
import YouTubeSearch from 'youtube-api-search';
import VideoList from './VideoList';
import VideoDetail from './VideoDetail';
export default {
  name: 'Subintest2',
  data() {
    return {
      show: false,
      video: null,
      videos: [],
      selectedVideo: null,
    }
  },
  components: {
    VideoList,
    VideoDetail,
  },
  created() {
    // init search
    this.videoSearch('집에서 즐기는 취미생활');
  },
  methods: {
    videoSearch(searchTerm) {
      YouTubeSearch( {key: "AIzaSyD2ipx6PqlX5qy6cAWXfwg265izg7YPe2Y", term: searchTerm}, (videos) => {
        this.videos = videos;
        // get the first video (before one is selected --this is the default)
        this.selectedVideo = videos[0];
        this.show = true;
      });
    },
    videoSelect(video) {
      this.selectedVideo = video;
    }
  },
};
</script>

<style>
  .container p {
    font-family: 'Open Sans', sans-serif;
  }
</style>