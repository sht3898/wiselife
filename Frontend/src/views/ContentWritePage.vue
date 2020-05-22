<template>
  <v-container>
    <v-flex class="ma-auto my-5" lg9>
      <v-row class="ma-0 pa-0">
        <v-col class="my-0 py-0" cols="2">
          <v-overflow-btn
            v-model="meeting.main_category"
            :items="categories"
            style="font-size:10pt"
            label="카테고리"
            dense
          ></v-overflow-btn>
        </v-col>
        <v-col>
          <v-text-field placeholder="제목" outlined dense></v-text-field>
        </v-col>
        <v-col class="my-0 py-0" cols="2">
          <v-overflow-btn
            v-model="meeting.is_class"
            :items="classform"
            style="font-size:10pt"
            label="형태"
            dense
          ></v-overflow-btn>
        </v-col>
        <v-col class="my-0 py-0" cols="2">
          <v-overflow-btn
            v-model="meeting.is_period"
            :items="periodform"
            style="font-size:10pt"
            label="기간"
            dense
          ></v-overflow-btn>
        </v-col>
        <v-col v-if="meeting.is_period=='정기'">
          <v-text-field
            v-model="meeting.period_date"
            dense
            outlined
            style="font-size:10pt"
            placeholder="주/월 n회"
          ></v-text-field>
        </v-col>
        <v-col v-if="meeting.is_period=='비정기'">
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
                v-model="meeting.meeting_date"
                style="font-size:10pt"
                label="모임 시간"
                prepend-icon="mdi-calendar"
                readonly
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="meeting.meeting_date" @input="menu2 = false"></v-date-picker>
          </v-menu>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
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
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="6">
          <v-file-input
            :rules="rules"
            accept="image/png, image/jpeg, image/bmp"
            placeholder="이미지 첨부"
            prepend-icon="mdi-camera-enhance"
            outlined
            dense
            v-model="image_url"
          ></v-file-input>
        </v-col>
        <v-col>
          <v-text-field
            v-model="meeting.max_person"
            label="모집 인원"
            style="font-size:10pt"
            type="number"
            outlined
            dense
          ></v-text-field>
        </v-col>
        <v-col>
          <v-overflow-btn
            class="my-0 py-0"
            v-model="meeting.unit"
            :items="unitform"
            style="font-size:10pt"
            label="모임비"
            dense
          ></v-overflow-btn>
        </v-col>
        <v-col v-if="meeting.unit=='회비'">
          <v-text-field
            v-model="meeting.fee"
            type="number"
            style="font-size:9pt"
            suffix="원"
            outlined
            dense
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row style="heigth:400px">
        <v-card class="pa-2" outlined tile style="width:100%; height:400px">
          <textarea
            v-model="meeting.content"
            auto-grow
            style="width:100%; height:100%"
            placeholder="내용을 입력해주세요."
          ></textarea>
        </v-card>
      </v-row>

      <v-row>
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
            v-model="meeting.ref_url"
            label="참고URL ex) http://k02b105.p.ssafy.io"
            filled
            dense
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col class="px-0 py-0">
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
        <v-col class="px-0 py-0">
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
        <v-col cols="6" class="my-0 py-0">
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
        <v-col cols="1" style="padding:2px">
          <v-btn @click="sample6_execDaumPostcode()" outlined color="green lighten-1">검색</v-btn>
        </v-col>
      </v-row>
      <v-row class="mb-5" style="text-align:right; float:right">
        <v-btn rounded class="mr-2">취소</v-btn>
        <v-btn rounded class="mr-3" color="orange lighten-1" @click="validate()">등록</v-btn>
      </v-row>
    </v-flex>
  </v-container>
</template>
<script>
import http from "../http-common";
export default {
  name: "contentWritePage",
  data() {
    return {
      meeting: {
        writer: "",
        main_category: "",
        title: "",
        tags: "", // 해시태그 띄어쓰기로 구분 (#붙여서 입력!)
        is_period: 0,
        meeting_date: new Date().toISOString().substr(0, 10),
        period_date: "",
        is_class: 0,
        max_person: 0,
        content: "",
        ref_url: "",
        area1: "",
        area2: "",
        address: "",
        phone: "",
        fee: 0, // 회비 또는 금액을 작성시 숫자를 입력하도록
        unit: "" // 단위 : 원/미정/회비
      },
      area1: "",

      image_url: "",

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
        v => !v || v.size < 2000000 || "이미지는 2 MB 이하로 등록해주세요!"
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
      second_area: []
    };
  },

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
  methods: {
    validate() {
      alert(this.category_key[this.meeting.main_category]);
      alert(this.class_key[this.meeting.is_class]);
      alert(this.period_key[this.meeting.is_period]);
      alert(document.getElementById("sample6_address").value);

      for(var i=0; i<this.hashtag.length; i++){
          this.meeting.tags += "#"+this.hashtag[i].text+","
      }

      http
        .post(`meeting/create`, {
          uid: "사용자",
          meeting: this.meeting
        })
        .then(response => {
          if (respons.data.state == 200) {
            sessionStorage.setItem("token", this.token);
          }
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
    handleFilesUploads() {
      this.files = this.$refs.files.files;
    },
    getSecondArea() {
      this.meeting.area1 = this.area1;
      if (this.meeting.area1.charAt(this.meeting.area1.length - 1) == "시") {
        this.second_area.push("전체");
      }
      // http
      //   .get(`api/area/${this.first_area}`)
      //   .then(response => {
      //     this.second_area.push(response.data.second_area);
      //   })
      //   .catch(err => {
      //     console.log(err);
      //   });
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
</style>