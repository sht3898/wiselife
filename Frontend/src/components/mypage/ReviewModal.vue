<template>
  <v-data-table
    :headers="headers"
    :items="attendmeetinglist"
    single-expand
    :expanded.sync="expanded"
    item-key="title"
    show-expand
  >
    <template v-slot:expanded-item="{ headers }">
      <td :colspan="headers.length">        
        
        <v-container class="pa-3 mt-5">
          <v-file-input
            :rules="rules"
            accept="image/png, image/jpeg, image/bmp"
            placeholder="이미지 첨부"
            prepend-icon="mdi-camera-enhance"
            outlined            
            dense
            id="files"
            ref="files"
            v-on:change="handleFilesUploads()"
          ></v-file-input>
          <p>* 리뷰 이미지는 1장만 등록 가능합니다!</p>

          <v-rating
          v-model="rating"
          color="yellow darken-3"
          background-color="grey darken-1"
          empty-icon="$ratingFull"
          half-increments
          hover
        ></v-rating>

          <v-textarea
            v-model="reviewContent"
            background-color="amber lighten-4"
            color="orange orange-darken-4"
            outlined
            placeholder="리뷰를 작성해주세요!"
          ></v-textarea>
        

        <div class="pb-5" style="text-align:right">
          <v-btn class="reviewbtn mr-3" color="green lighten-2"  rounded small @click="writeReview()" style="font-size: 12pt;">등록</v-btn>
          <v-btn class="reviewbtn"  rounded small style="font-size: 12pt;">삭제</v-btn>
        </div>
        </v-container>
      </td>
    </template>
  </v-data-table>
</template>

<script>
import http from "../../http-common"
export default {
  name: "ReviewModal",
  data() {
    return {
      expanded: [],
      singleExpand: false,
      headers: [
        { text: "카테고리", value: "mainCategory" },
        {
          text: "강좌/모임명",
          align: "start",
          value: "title"
        },
        { text: "호스트", value: "writer" },
        { text: "모임 유형", value: "isPeriod" },
        { text: "모임 날짜", value: "meetingDate" },

        { text: "", value: "data-table-expand" }
      ],
      attendmeetinglist:[],
      reviewContent:"",
      files: "",
      rules: [
        value =>
        !value.length ||
        value.reduce((size, file) => size + file.size, 0) < 3000000 ||
        "이미지는 3 MB 이하로 등록해주세요!"
      ],
      categories: [
        "전체",
        "레저/스포츠",
        "요리",
        "수공예/공방",
        "놀이/게임",
        "문화",
        "예술",
        "축제/행사",
        "기타"
      ],
    };
  },
  mounted(){
    this.getMymeeting();
  },
  methods: {
    getMymeeting(){
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
      .get(`review/check`, config)
      .then(response => {
        console.log(response);

        
        this.attendmeetinglist = response.data.참여;
         for (var i = 0; i < this.attendmeetinglist.length; i++) {
            this.attendmeetinglist[i].tags = this.attendmeetinglist[i].tags.split(" ");
            
            if(this.attendmeetinglist[i].isPeriod == "0") {
              this.attendmeetinglist[i].isPeriod="비정기";
            }else{
              this.attendmeetinglist[i].isPeriod="정기";
            }
            this.attendmeetinglist[i].mainCategory = this.categories[this.attendmeetinglist[i].mainCategory];
            this.attendmeetinglist[i].meetingDate = this.dateParsing(this.attendmeetinglist[i].meetingDate);
          }  

        console.log(this.attendmeetinglist);
      })
    
    },
    dateParsing(beforeParsing) {
      const t = beforeParsing.indexOf("T");
      const afterParsing = beforeParsing.substring(0, t);
      const realdate =
        afterParsing.substring(0, 4) +
        "년 " +
        afterParsing.substring(5, 7) +
        "월 " +
        afterParsing.substring(8, 10) +
        "일";
      return realdate;
    },
    handleFilesUploads() {
      this.files = this.$refs.files.files;
    },
    writeReview() {
          if (this.reviewContent == "") {
            alert("내용을 입력해주세요.");
            return;
          }
          if(this.files == ""){
            alert("이미지를 등록해주세요.");
            return;
          }
          let formData = new FormData();
          for (var i = 0; i < this.files.length; i++) {
            let file = this.files[i];
            formData.append("files", file);
          }
          formData.append("budget_num", this.budgetInfo.budget_num);
          // alert("budget_num: " + this.budgetInfo.budget_num);
          formData.append("reviewContent", this.reviewContent);
          // alert("reviewContent: " + this.reviewContent);
          http
            .post("/review", formData)
            .then(response => {
              // console.log("SUCCESS!!");
              this.$router.push({name:'review'});
              // console.log(response);
              // this.result = response.;
            })
            .catch(ex => {
              // console.log("FAILURE!!");
            });
         
    }
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.reviewbtn {
  font-family: "Nanum Pen Script", cursive;
}
</style>