<template>
  <v-container>
    <v-flex class="ma-auto my-5" lg9>
      <v-row>
        <v-col>
          <v-chip :color="`green lighten-4`" class="black--text" label small>모임</v-chip>
        </v-col>
        <v-col>
          <v-chip :color="`green lighten-4`" class="black--text" label small>놀이/게임</v-chip>
        </v-col>
        <v-col>
          <v-chip :color="`green lighten-4`" class="black--text" label small>비정기</v-chip>
        </v-col>
        <v-col>모임날짜 2020-05-22</v-col>
        <v-col>
          <v-chip :color="`green lighten-4`" class="black--text" label small>인원 2/4</v-chip>
        </v-col>
        <v-col>모임비 16,000원</v-col>
        <v-col>
          <v-chip :color="`blue lighten-4`" class="black--text" label small>모집중</v-chip>
        </v-col>
      </v-row>
      <v-row>
        <v-col>제목</v-col>
        <v-col>
          <v-btn icon>
            <span style="font-size:20pt" class="mdi mdi-heart-outline"></span>
          </v-btn>
        </v-col>
        <v-col>모임장 안지연</v-col>
        <v-col>작성일 2020-05-21 13:20</v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-img height="150" src="https://cdn.vuetifyjs.com/images/cards/cooking.png" />
        </v-col>
        <v-col>지도~</v-col>
      </v-row>
      <v-row>
        <v-col>
          <div>#방탈출 #둔산동 #주말</div>
        </v-col>
        <v-col>참고 URL</v-col>
      </v-row>
      <v-row>
        <v-text-field readonly outlined></v-text-field>
      </v-row>
    </v-flex>

    <v-flex>
        <contents-detail-tab></contents-detail-tab>
    </v-flex>
  </v-container>
  
</template>
<script>
import ContentsDetailTab from "@/components/tabs/ContentsDetailTab"
export default {
  name: "contentDetailPage",
  components:{
      ContentsDetailTab
  }
};
</script>
<style scoped>
</style>