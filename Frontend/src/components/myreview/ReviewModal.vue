<template>
  <v-data-table
    :headers="headers"
    :items="desserts"
    single-expand
    :expanded.sync="expanded"
    item-key="name"
    show-expand
  >
    <template v-slot:expanded-item="{ headers, item }">
      <td :colspan="headers.length">
        {{ item.name }}
        
        <v-container class="pa-3">
          <v-file-input
            :rules="rules"
            accept="image/png, image/jpeg, image/bmp"
            placeholder="이미지 첨부"
            prepend-icon="mdi-camera-enhance"
            outlined
            multiple
            dense
            id="files"
            ref="files"
            v-on:change="handleFilesUploads()"
          ></v-file-input>

          <v-textarea
            v-model="content"
            background-color="amber lighten-4"
            color="orange orange-darken-4"
            outlined
            placeholder="리뷰를 작성해주세요!"
          ></v-textarea>
        

        <div class="pb-5" style="text-align:right">
          <v-btn class="reviewbtn mr-3" color="green lighten-2"  rounded small @click="writeReview(true)" style="font-size: 12pt;">등록</v-btn>
          <v-btn class="reviewbtn"  rounded small @click="writeReview(false)" style="font-size: 12pt;">삭제</v-btn>
        </div>
        </v-container>
      </td>
    </template>
  </v-data-table>
</template>

<script>
export default {
  name: "ReviewModal",
  data() {
    return {
      expanded: [],
      singleExpand: false,
      headers: [
        { text: "카테고리", value: "category" },
        {
          text: "강좌/모임명",
          align: "start",
          value: "name"
        },
        { text: "호스트", value: "writer" },
        { text: "모임 유형", value: "is_period" },
        { text: "모임 날짜", value: "meeting_date" },

        { text: "", value: "data-table-expand" }
      ],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          iron: "1%"
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          iron: "1%"
        },
        {
          name: "Eclair",
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          iron: "7%"
        },
        {
          name: "Cupcake",
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          iron: "8%"
        },
        {
          name: "Gingerbread",
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          iron: "16%"
        },
        {
          name: "Jelly bean",
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          iron: "0%"
        },
        {
          name: "Lollipop",
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          iron: "2%"
        },
        {
          name: "Honeycomb",
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          iron: "45%"
        },
        {
          name: "Donut",
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          iron: "22%"
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%"
        }
      ],
      files: "",
      rules: [
        value =>
          !value ||
          value.size > 100000000 ||
          "이미지는 10 MB 이하로 등록해주세요!"
      ]
    };
  },
  methods: {
    handleFilesUploads() {
      this.files = this.$refs.files.files;
    },
    writeReview(bool) {
      //   if (bool === true) {
      //     if (this.content == "") {
      //       alert("내용을 입력해주세요.");
      //       return;
      //     }
      //     if(this.files == ""){
      //       alert("이미지를 등록해주세요.");
      //       return;
      //     }
      //     let formData = new FormData();
      //     for (var i = 0; i < this.files.length; i++) {
      //       let file = this.files[i];
      //       formData.append("files", file);
      //     }
      //     formData.append("budget_num", this.budgetInfo.budget_num);
      //     // alert("budget_num: " + this.budgetInfo.budget_num);
      //     formData.append("review_content", this.content);
      //     // alert("review_content: " + this.content);
      //     http
      //       .post("/review", formData)
      //       .then(response => {
      //         // console.log("SUCCESS!!");
      //         this.$router.push({name:'review'});
      //         // console.log(response);
      //         // this.result = response.;
      //       })
      //       .catch(ex => {
      //         // console.log("FAILURE!!");
      //       });
      //   } else {
      //     this.dialog = false;
      //   }
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