<template>
  <v-card class="my-4" width="100%" flat>
    <v-row class="mt-5 ml-2">
      <v-avatar size="30px">
        <img :src="review.profileImage" />
      </v-avatar>
      <span
        class="px-3"
        style="padding-top:5px; font-size:11pt; color:dimgrey"
      >{{ review.username }}</span>
      <v-rating
        :value="review.score"
        background-color="white"
        color="amber"
        dense
        half-increments
        readonly
        small
        style="padding-top:4px;"
      ></v-rating>
      <span class="topscore ml-1">{{ review.score }}</span>
    </v-row>
    <v-row height="100px">
      <v-col>
        <v-card width="180" outlined>
          <v-img :src="review.imageUrl" />
        </v-card>
      </v-col>
      <v-col cols="9">
        <v-card outlined class="pa-3" width="100%" height="100%">{{ review.content }}</v-card>
      </v-col>
    </v-row>
  </v-card>
</template>
<script>
export default {
  name: "ReviewContent",
  props: {
    review: { type: Object, default: {} }
    // - review_id (int) : 리뷰 아이디 pk
    // - uid(Long) : 유저 id fk
    // - meeting_id : 미팅 id fk
    // - writer_profile
    // - writer(String) : 닉네임
    // - created_at (timestamp) : default now () on created at
    // - updated_at (timestamp) : defalut now() on update cascade
    // - score(int) : 평점
    // - image_url(String) : 이미지 url
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.topscore {
  font-size: 15pt;
  font-family: "Jua", sans-serif;
}
</style>