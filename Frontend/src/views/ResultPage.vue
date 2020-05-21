<template>
  <v-container>
    <v-flex class="ma-auto mt-5" lg10>
      <h3 class="mt-5">'{{keyword}}'에 대한 검색 결과입니다.</h3>
      <v-row>
        <v-col>
          <v-row class="filtering my-2">
            <v-col v-for="category in categories" :key="category.name">
              <v-btn x-small text style="text-align:center; font-size:10pt">{{category.name}}</v-btn>
            </v-col>
          </v-row>
        </v-col>
        <v-col class="pl-7" cols="2">
          <v-checkbox :label="'내 지역 보기'" color="success"></v-checkbox>
        </v-col>
      </v-row>

      <contents-list :content="this.content" />
    </v-flex>
  </v-container>
</template>
<script>
import ContentsList from "@/components/contents/ContentsList";
export default {
  name: "resultPage",
  components: {
    ContentsList
  },
  data() {
    return {
      content: "",
      categories: [
        {
          key: 0,
          name: "전체",
          clicked: false
        },
        {
          key: 1,
          name: "레저/스포츠",
          clicked: false
        },
        {
          key: 2,
          name: "요리",
          clicked: false
        },
        {
          key: 3,
          name: "수공예/공방",
          clicked: false
        },
        {
          key: 4,
          name: "놀이/게임",
          clicked: false
        },
        {
          key: 5,
          name: "문화",
          clicked: false
        },
        {
          key: 6,
          name: "예술",
          clicked: false
        },
        {
          key: 7,
          name: "축제/행사",
          clicked: false
        },
        {
          key: 8,
          name: "기타",
          clicked: false
        }
      ],
      selctedCategory: 0
    };
  },
  methods: {
    search() {}
  },
  computed: {
    keyword: function() {
      this.content = this.$route.params.keyword;
      return this.content;
    }
  }
};
</script>
<style scoped>
.filtering {
  background-color: lightgray;
  border-radius: 12px;
}
</style>
