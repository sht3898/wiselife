<template>
  <v-container class="ma-auto mt-5">
    <v-flex class="ma-auto" lg10>
      <p class="infotitle">내 정보 보기</p>
      <v-row>
        <v-col cols="3" class="mt-5">
          <span style="color:dimgray;">
            <strong>Gender</strong>
          </span>
        </v-col>
        <v-radio-group v-model="gender" row class="ml-3">
          <v-col>
            <v-radio color="green" label=" 남" value="1"></v-radio>
          </v-col>
          <v-col style="padding-left:90px">
            <v-radio color="green" label=" 여" value="2"></v-radio>
          </v-col>
        </v-radio-group>
      </v-row>
      <v-row>
        <v-col cols="3" class="mt-5">
          <span style="color:dimgray;">
            <strong>Birth</strong>
          </span>
        </v-col>
        <v-col class="px-0 py-0">
          <v-container id="dropdown-example-2">
            <v-overflow-btn v-model="birth" :items="years" label="연도" dense></v-overflow-btn>
          </v-container>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="3" class="mt-5">
          <span style="color:dimgray;">
            <strong>Area</strong>
          </span>
        </v-col>
        <v-col class="px-0 py-0">
          <v-container id="dropdown-example-2" class="py-0">
            <v-overflow-btn v-model="area1" :items="first_area" label="도/시" dense></v-overflow-btn>
          </v-container>
        </v-col>
        <v-col class="px-0 py-0">
          <v-container id="dropdown-example-2" class="py-0">
            <v-overflow-btn v-model="area2" :items="second_area" hide-no-data label="시/군/구" dense></v-overflow-btn>
          </v-container>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="3" class="mt-5">
          <span style="color:dimgray;">
            <strong>Instructor</strong>
          </span>
        </v-col>
        <v-radio-group v-model="instructor" row class="ml-3">
          <v-col>
            <v-radio color="green" label=" 강사" value="1"></v-radio>
          </v-col>
          <v-col style="padding-left:60px">
            <v-radio color="green" label=" 일반 회원" value="0"></v-radio>
          </v-col>
        </v-radio-group>
      </v-row>

      <p class="infotitle">관심 카테고리</p>
      <v-row justify="space-around">
        <v-col class="mt-5" v-for="category in categories" :key="category.name">
          <v-img
            :src="getImgUrl(category.url)"
            width="70px"
            @click="category.clicked = !category.clicked"
            :class="{green: category.clicked}"
            style="border-radius:12px; margin:auto"
          ></v-img>
          <div style="font-size:10pt; text-align:center;">{{category.name}}</div>
        </v-col>
      </v-row>
      <div class="my-10" style="text-align:right">
        <v-btn rounded class="mr-5" color="green" @click="modify" style="color:white">수정하기</v-btn>
        <v-btn rounded color="red" @click="withdraw" style="color:white">탈퇴하기</v-btn>
      </div>
    </v-flex>
  </v-container>
</template>
<script>
import http from "../../http-common";

export default {
  name: "MyInfoPage",
  props: {
    token: { type: String }
  },

  data() {
    return {
      gender: "",
      birth: "",
      area1: "",
      area2: "",
      instructor: "",
      categories: [
        {
          url: "레저,스포츠.png",
          name: "레저/스포츠",
          clicked: false
        },
        {
          url: "요리.png",
          name: "요리",
          clicked: false
        },
        {
          url: "수공예,공방.png",
          name: "수공예/공방",
          clicked: false
        },
        {
          url: "놀이,게임.png",
          name: "놀이/게임",
          clicked: false
        },
        {
          url: "문화.png",
          name: "문화",
          clicked: false
        },
        {
          url: "예술.png",
          name: "예술",
          clicked: false
        },
        {
          url: "축제,행사.png",
          name: "축제/행사",
          clicked: false
        },
        {
          url: "기타.png",
          name: "기타",
          clicked: false
        }
      ],
      years: [],
      valid: true,
      first_area: [
        "서울특별시",
        "부산광역시",
        "대구광역시",
        "인천광역시",
        "광주광역시",
        "대전광역시",
        "울산광역시",
        "세종특별자치시",
        "경기도",
        "강원도",
        "충청북도",
        "충청남도",
        "전라북도",
        "전라남도",
        "경상북도",
        "경상남도",
        "제주특별자치도"
      ],
      second_area: []
    };
  },
  watch: {
    area1: "getSecondArea"
  },
  mounted() {
    this.getUserInfo();
    this.getYears();
  },
  methods: {
    getYears() {
      const now = new Date().getUTCFullYear();
      this.years = Array(now - (now - 70))
        .fill("")
        .map((v, idx) => now - idx);
    },
    getImgUrl(img) {
      return require("../../assets/categories/" + img);
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    reset() {
      this.$refs.form.reset();
    },
    getSecondArea() {
      this.second_area = [];
      if (this.area1.charAt(this.area1.length - 1) == "시") {
        this.second_area.push("전체");
      }
      http
        .get(`area/${this.area1}`)
        .then(response => {
          for (var i = 0; i < response.data.length; i++) {
            this.second_area.push(response.data[i]);
          }
        })
        .catch(() => {});
    },
    pass() {
      localStorage.setItem("token", this.token);
      this.$router.push("/");
      location.reload();
    },
    getUserInfo() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`user/info`, config)
        .then(response => {
          if (response.data.status == "success") {
            let userinfo = response.data.info.userinfo;
            this.birth = userinfo.year;
            this.gender = userinfo.gender.toString();
            this.area1 = userinfo.area1;
            this.area2 = userinfo.area2;
            this.instructor = userinfo.isInst.toString();

            for (
              var i = 0;
              i < response.data.info.interest_category.length;
              i++
            ) {
              var now_interest_category =
                response.data.info.interest_category[i];
              for (var j = 0; j < this.categories.length; j++) {
                if (
                  now_interest_category.categoryName == this.categories[j].name
                ) {
                  this.categories[j].clicked = true;
                }
              }
            }
          }
        })
        .catch(() => {
          alert("토큰 만료! 다시 로그인 해주세요!");
          localStorage.clear();
          this.$router.go();
        });
    },
    modify() {
      if (
        this.gender == "" ||
        this.birth == "" ||
        this.area1 == "" ||
        this.area2 == "" ||
        this.instructor == ""
      ) {
        alert("입력하지 않은 값이 있습니다!");
        return;
      }
      var picked = [];
      for (var i = 0; i < this.categories.length; i++) {
        if (this.categories[i].clicked) {
          picked.push(i + 1);
        }
      }
      let params = {
        gender: this.gender,
        year: this.birth,
        area1: this.area1,
        area2: this.area2,
        isInst: this.instructor,
        interestCategory: picked
      };
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .put(`user/update`, params, config)
        .then(response => {
          if (response.data.status == "success") {
            alert("회원 정보가 수정되었습니다!");
            this.getUserInfo();
          }
        })
        .catch(() => {
          alert("토큰 만료! 다시 로그인 해주세요!");
          localStorage.clear();
          this.$router.go();
        });
    },
    withdraw() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      if (confirm("탈퇴하시겠습니까?") == true) {
        http
          .delete(`user`, config)
          .then(response => {
            if (response.data.status == "success") {
              alert("탈퇴 하였습니다.");
              localStorage.clear();
              this.$router.push("/");
              location.reload();
            } else {
              alert("오류가 발생했습니다. 다시 시도해 주세요.");
              this.$router.push("/mypage");
            }
          })
          .catch(() => {
            alert("토큰 만료! 다시 로그인 해주세요!");
            localStorage.clear();
            this.$router.go();
          })
          .finally(() => (this.loading = false));
      } else {
        return false;
      }
    }
  }
};
</script>
<style scoped>
.green {
  background-color: green;
}
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.myinfobtn {
  font-size: 13pt;
  font-family: "Nanum Pen Script", cursive;
}
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.infotitle {
  font-family: "Do Hyeon", sans-serif;
  font-size: 23px;
}
</style>