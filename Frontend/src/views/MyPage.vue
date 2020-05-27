<template>
  <v-container>
    <v-flex class="ma-auto mt-5" lg10>
      <v-container fluid>
        <p class="menu">
          참여한 강좌/모임
          <span style="float:right">
            <v-btn rounded class="reviewbtn green lighten-1" @click="insertReview">리뷰 작성 ✒️</v-btn>
          </span>
        </p>
      </v-container>
      <!-- 리뷰 modal -->
      <v-dialog v-model="dialog" max-width="800">
        <v-card>
          <v-toolbar width="800" style="position:absolute; z-index:2">
              
            <v-row class="menu py-4 px-3">
                <v-col>
              강좌/모임 Review
                </v-col>
                <v-col>
              <span style="text-align:right; float:right">
                <v-btn color="green darken-1" text @click="dialog = false">Close</v-btn>
              </span>
                </v-col>
            </v-row>
          </v-toolbar>
          <review-modal style="padding-top:70px;" />
        </v-card>
      </v-dialog>

      <v-container fluid mb-12>
        <p class="menu">등록한 강좌/모임</p>
      </v-container>
    </v-flex>
  </v-container>
</template>
<script>
import ReviewModal from "@/components/myreview/ReviewModal";
export default {
  name: "MyPage",
  components: {
    ReviewModal
  },
  data() {
    return {
      dialog: false
    };
  },
  methods: {
    insertReview() {
      this.dialog = true;
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
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.reviewbtn {
  font-family: "Nanum Pen Script", cursive;
}
</style>