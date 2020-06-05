<template>
  <v-container>
    <v-flex class="ma-auto my-5" lg9>
      <v-card outlined>
        <p class="infotext ml-5 mt-3">필수 항목 - 강좌/모임에 대한 정보를 입력해주세요!</p>
        <v-row class="ma-0 pa-0">
          <v-col class="my-0 mr-0 pr-0 py-0" cols="2">
            <v-overflow-btn
              v-model="meeting.mainCategory"
              :items="categories"
              style="font-size:10pt"
              label="카테고리"
              dense
            ></v-overflow-btn>
          </v-col>
          <v-col>
            <v-text-field v-model="meeting.title" placeholder="제목" outlined dense></v-text-field>
          </v-col>
          <v-col class="ma-0 pa-0" cols="2">
            <v-overflow-btn
              v-if="userinst==1"
              v-model="meeting.isClass"
              :items="classform"
              style="font-size:10pt"
              label="형태"
              dense
            ></v-overflow-btn>
            <v-overflow-btn
              v-else
              v-model="meeting.isClass"
              :items="meetingform"
              style="font-size:10pt"
              label="형태"
              dense
            ></v-overflow-btn>
          </v-col>
          <v-col class="my-0 py-0" cols="2">
            <v-overflow-btn
              v-model="meeting.isPeriod"
              :items="periodform"
              style="font-size:10pt"
              label="기간"
              dense
            ></v-overflow-btn>
          </v-col>
          <v-col v-if="meeting.isPeriod=='정기'">
            <v-text-field
              v-model="meeting.periodDate"
              dense
              outlined
              style="font-size:10pt"
              placeholder="주/월 n회"
            ></v-text-field>
          </v-col>
          <v-col v-if="meeting.isPeriod=='비정기'">
            <v-menu
              v-model="menu2"
              :close-on-content-click="false"
              :nudge-right="40"
              transition="scale-transition"
              offset-y
              min-width="290px"
            >
              <template v-slot:activator="{ on }">
                <v-text-field
                  dense
                  outlined
                  v-model="meeting.meetingDate"
                  style="font-size:10pt"
                  label="모임 날짜"
                  prepend-icon="mdi-calendar"
                  readonly
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="meeting.meetingDate" @input="menu2 = false"></v-date-picker>
            </v-menu>
          </v-col>
        </v-row>

        <v-row>
          <v-col class="my-0 py-0 ml-2">
            <v-text-field
              v-model="meeting.maxPerson"
              label="모집 인원"
              style="font-size:10pt"
              type="number"
              :min="meeting.nowPerson"
              outlined
              dense
            ></v-text-field>
          </v-col>
          <v-col class="my-0 py-0">
            <v-overflow-btn
              class="my-0 py-0"
              v-model="meeting.unit"
              :items="unitform"
              style="font-size:10pt"
              label="모임비"
              dense
            ></v-overflow-btn>
          </v-col>
          <v-col class="my-0 py-0 ml-0 pl-0" v-if="meeting.unit=='회비'">
            <v-text-field
              v-model="meeting.fee"
              type="number"
              style="font-size:9pt"
              suffix="원"
              outlined
              dense
            ></v-text-field>
          </v-col>

          <v-col class="my-0 mr-0 pr-0 py-0">
            <v-container id="dropdown-example-2" class="py-0">
              <v-overflow-btn
                class="my-0 py-0"
                v-model="area1"
                :items="first_area"
                style="font-size:10pt"
                label="도/시"
                dense
              ></v-overflow-btn>
            </v-container>
          </v-col>
          <v-col class="ma-0 pa-0 mr-3">
            <v-container id="dropdown-example-2" class="py-0">
              <v-overflow-btn
                class="my-0 py-0"
                v-model="meeting.area2"
                :items="second_area"
                style="font-size:10pt"
                label="시/군/구"
                dense
              ></v-overflow-btn>
            </v-container>
          </v-col>
        </v-row>
      </v-card>
      <v-alert class="mt-3" outlined type="warning" dense border="left" style="font-size:10pt;">등록되어 있던 이미지는 삭제됩니다. 새로 등록해주세요!</v-alert>
      <v-row>
        <v-col cols="5">
          <v-file-input
            :rules="rules"
            accept="image/png, images/PNG, image/jpg, image/jpeg, image/bmp"
            placeholder="이미지 첨부"
            prepend-icon="mdi-camera-enhance"
            style="font-size:10pt"
            outlined
            multiple
            show-size
            dense
            id="files"
            ref="files"
            v-on:change="handleFilesUploads()"
          ></v-file-input>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="meeting.address"
            class="my-0 py-0"
            id="sample6_address"
            prepend-icon="mdi-map-marker"
            style="font-size:10pt"
            outlined
            dense
            disabled
            placeholder="위치"
          />
        </v-col>

        <v-col cols="1" class="my-1 py-2 mx-0 px-0">
          <v-btn @click="sample6_execDaumPostcode()" outlined color="green lighten-1">검색</v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-combobox
          class="my-0 py-0"
          v-model="hashtag"
          :filter="filter"
          :hide-no-data="!search"
          :items="items"
          :search-input.sync="search"
          hide-selected
          label="해시태그를 등록해주세요! (최대 5개 / 메인에는 3개만 보입니다)"
          multiple
          small-chips
          solo
        >
          <template v-slot:no-data>
            <v-list-item>
              <span class="subheading">Create</span>
              <v-chip :color="`${colors[nonce - 1]} lighten-3`" label small>{{ search }}</v-chip>
            </v-list-item>
          </template>
          <template v-slot:selection="{ attrs, item, parent, selected }">
            <v-chip
              v-if="item === Object(item)"
              v-bind="attrs"
              :color="`${item.color} lighten-3`"
              :input-value="selected"
              label
              small
            >
              <span class="pr-2">{{ item.text }}</span>
              <v-icon small @click="parent.selectItem(item)">mdi-close</v-icon>
            </v-chip>
          </template>
          <template v-slot:item="{ index, item }">
            <v-text-field
              class="my-0 py-0"
              v-if="editing === item"
              v-model="editing.text"
              autofocus
              flat
              background-color="transparent"
              hide-details
              solo
              @keyup.enter="edit(index, item)"
            ></v-text-field>
            <v-chip v-else :color="`${item.color} lighten-3`" dark label small>{{ item.text }}</v-chip>
            <v-spacer></v-spacer>
            <v-list-item-action @click.stop>
              <v-btn icon @click.stop.prevent="edit(index, item)">
                <v-icon>{{ editing !== item ? 'mdi-pencil' : 'mdi-check' }}</v-icon>
              </v-btn>
            </v-list-item-action>
          </template>
        </v-combobox>
      </v-row>
      <v-row style="heigth:300px">
        <vue-editor
          v-model="meeting.content"
          :editorToolbar="customToolbar"
          style="width:100%; height:300px"
        ></vue-editor>
      </v-row>

      <v-row class="mt-12">
        <v-col>
          <v-text-field
            class="my-0 py-0"
            v-model="meeting.phone"
            label="문의 연락처) 전화번호 or 카카오톡id or 이메일"
            filled
            dense
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
            class="my-0 py-0"
            v-model="meeting.refUrl"
            label="참고 URL) http://k02b105.p.ssafy.io"
            filled
            dense
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row class="mb-5" style="text-align:right; float:right">
        <v-btn rounded class="mr-2 submitbtn" @click="goback">취소</v-btn>
        <v-btn rounded class="mr-3 submitbtn" color="orange lighten-1" @click="validate()">수정</v-btn>
      </v-row>
    </v-flex>
  </v-container>
</template>
<script>
import http from "../http-common";
import { VueEditor } from "vue2-editor";
export default {
  name: "ContentUpdatePage",
  components: {
    VueEditor
  },
  data: () => ({
    customToolbar: [
      ["bold", "italic", "underline"],
      [{ list: "ordered" }, { list: "bullet" }],
      [
        { align: "" },
        { align: "center" },
        { align: "right" },
        { align: "justify" }
      ]
    ],
    meeting: {
      writer: "",
      mainCategory: "",
      title: "",
      tags: "", // 해시태그 띄어쓰기로 구분 (#붙여서 보내기!)
      isPeriod: 0,
      meetingDate: new Date().toISOString().substr(0, 10),
      periodDate: "",
      isClass: 0,
      maxPerson: 0,
      content: "",
      refUrl: "",
      area1: "",
      area2: "",
      address: "",
      phone: "",
      fee: 0, // 회비 또는 금액을 작성시 숫자를 입력하도록
      unit: "" // 단위 : 원/미정/회비
    },
    area1: "",

    image_url: "",
    files: "",

    categories: [
      "레저/스포츠",
      "요리",
      "수공예/공방",
      "놀이/게임",
      "문화",
      "예술",
      "축제/행사",
      "기타"
    ],
    category_key: {
      "레저/스포츠": 1,
      요리: 2,
      "수공예/공방": 3,
      "놀이/게임": 4,
      문화: 5,
      예술: 6,
      "축제/행사": 7,
      기타: 8
    },
    classform: ["강좌", "모임"],
    meetingform: ["모임"],
    class_key: {
      강좌: 1,
      모임: 0
    },
    periodform: ["정기", "비정기"],
    period_key: {
      정기: 1,
      비정기: 0
    },
    modal: false,
    menu2: false,

    activator: null,
    attach: null,
    colors: ["red", "amber", "lime", "teal", "indigo"],
    editing: null,
    index: -1,
    items: [
      { header: "해시태그를 등록해주세요!" },
      {
        text: "슬기로운",
        color: "green"
      },
      {
        text: "여가생활",
        color: "orange"
      }
    ],
    nonce: 1,
    menu: false,
    hashtag: [],
    x: 0,
    search: null,
    y: 0,
    rules: [
      value =>
        !value ||
        value.reduce((size, file) => size + file.size, 0) < 10000000 ||
        "이미지는 10 MB 이하로 등록해주세요!"
    ],
    unitform: ["미정", "회비"],
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
    second_area: [],
    userinst: ""
  }),
  watch: {
    area1: "getSecondArea",
    hashtag(val, prev) {
      if (val.length === prev.length) return;
      if (val.length > 5) {
        this.$nextTick(() => this.hashtag.pop());
      }

      this.hashtag = val.map(v => {
        if (typeof v === "string") {
          v = {
            text: v,
            color: this.colors[this.nonce - 1]
          };

          this.items.push(v);

          this.nonce++;
        }

        return v;
      });
    }
  },
  mounted() {
    this.getUserInfo();
    this.getContentDetail();
  },
  methods: {
    getUserInfo() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .get(`user/info/`, config)
        .then(response => {
          console.log(response.data);
          this.userinst = response.data.info.userinfo.isInst;
        })
        .catch(error => {
          alert(error);
        });
    },
    goback() {
      this.$router.push("/contentdetail/" + this.$route.params.seq);
    },
    handleFilesUploads() {
      this.files = this.$refs.files.$refs.input.files;
    },
    getContentDetail() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .get(`meeting/${this.$route.params.seq}`, config)
        .then(response => {

          this.meeting = response.data;

          /////////데이터 정제//////////
          this.meeting.mainCategory = this.categories[
            this.meeting.mainCategory - 1
          ];
          if (this.meeting.isClass == 0) {
            this.meeting.isClass = "모임";
          } else {
            this.meeting.isClass = "강좌";
          }
          if (this.meeting.isPeriod == 0) {
            this.meeting.isPeriod = "비정기";
            this.meeting.meetingDate = this.meeting.meetingDate.substring(
              0,
              10
            );
          } else {
            this.meeting.isPeriod = "정기";
          }
          if (this.meeting.tags != null || this.meeting.tags != "") {
            this.hashtag = this.meeting.tags.split(" ");
          }
          this.area1 = response.data.area1;
        })
        .catch(error => {
          alert(error);
        });
    },
    validate() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      this.meeting.address = document.getElementById("sample6_address").value;
      this.meeting.area1 = this.area1;
      this.meeting.tags = "";
      for (var i = 0; i < this.hashtag.length; i++) {
        if (i == 5) {
          break;
        }
        this.meeting.tags += "#" + this.hashtag[i].text + ",";
      }

      //////////// 이미지 업로드 //////////////
      let formData = new FormData();
      console.log("보자!:   ", this.$refs.files.$refs.input.files);
      console.log(this.files);

      formData.append("meeting_id", this.$route.params.seq);
      for (var j = 0; j < this.files.length; j++) {
        let file = this.files[j];
        formData.append("files", file);
      }
      formData.append("writer", localStorage.getItem("username"));
      formData.append("title", this.meeting.title);
      formData.append("isPeriod", this.period_key[this.meeting.isPeriod]);
      formData.append("meetingDate", this.meeting.meetingDate);
      formData.append("periodDate", this.meeting.periodDate);
      formData.append("isClass", this.class_key[this.meeting.isClass]);
      formData.append("maxPerson", this.meeting.maxPerson);
      formData.append("content", this.meeting.content);
      formData.append("refUrl", this.meeting.refUrl);
      formData.append("address", this.meeting.address);
      formData.append("fee", this.meeting.fee);
      formData.append("unit", this.meeting.unit);
      formData.append(
        "mainCategory",
        this.category_key[this.meeting.mainCategory]
      );
      formData.append("tags", this.meeting.tags);
      formData.append("area1", this.meeting.area1);
      formData.append("area2", this.meeting.area2);
      formData.append("phone", this.meeting.phone);
      formData.append("isActive", this.meeting.isActive);
      http
        .put("meeting/update", formData, config)
        .then(response => {
          console.log(response);
          if (response.status == 200) {
            alert("성공적으로 수정되었습니다!");
            alert(response.data.meeting_id);
            this.$router.push("/contentdetail/" + response.data.meeting_id);
          }
        })
        .catch(error => {
          console.log(error);
          alert("에러! 업데이트 실패!");
        });
    },
    edit(index, item) {
      if (!this.editing) {
        this.editing = item;
        this.index = index;
      } else {
        this.editing = null;
        this.index = -1;
      }
    },
    filter(item, queryText, itemText) {
      if (item.header) return false;

      const hasValue = val => (val != null ? val : "");

      const text = hasValue(itemText);
      const query = hasValue(queryText);

      return (
        text
          .toString()
          .toLowerCase()
          .indexOf(query.toString().toLowerCase()) > -1
      );
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
        .catch(err => {
          alert(err);
        });
    },

    sample6_execDaumPostcode() {
      new daum.Postcode({
        oncomplete: function(data) {
          var addr = ""; // 주소 변수
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            addr = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            addr = data.jibunAddress;
          }
          document.getElementById("sample6_address").value = addr;
        }
      }).open();
    }
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.submitbtn {
  font-size: 13pt;
  font-family: "Nanum Pen Script", cursive;
}
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.infotext {
  font-family: "Nanum Pen Script", cursive;
}
</style>