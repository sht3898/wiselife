<template>
  <v-container class="my-5">
    <v-flex class="ma-auto" lg9 xs11>
      <h3 class="mt-5">'{{keyword}}'에 대한 검색 결과입니다. 선택된 카테고리는 '{{category}}'</h3>
      <v-row>
        <v-col>
          <v-row class="filtering">
            <v-col v-for="category in categories" :key="category.name">
              <v-btn  :class="{bold: category.clicked}" @click="clickCategory(category)" x-small text style="text-align:center; font-size:10pt">{{category.name}}</v-btn>
            </v-col>
          </v-row>
        </v-col>
        <v-col class="pl-5" cols="1">
          <span style="font-size:9pt; color:dimgrey">내 지역</span>
          <span class="ma-auto" style="float:center; text-align:center">
          <v-switch class="my-0 py-0" dense v-model="myarea" inset color="success"></v-switch>      
          </span>
        </v-col>
      </v-row>

    </v-flex>
    <v-flex class="ma-auto" lg10 xs12>
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
      selctedCategory: 0,
      myarea: 0,
    };
  },
  methods: {
    search() {},
    clickCategory(category) {
      for(var i = 0; i < this.categories.length; i++){
        if(this.categories[i] != category){
          this.categories[i].clicked = false;
        }
      }
      category.clicked = true;
      this.$router.push("/result/"+category.key+"/"+this.$route.params.keyword)
    },
    checkURL(){
      for(var i = 0; i < this.categories.length; i++){
        if(this.categories[i].key == this.$route.params.category){
          this.categories[i].clicked =true;
        } 
      }
    }
  },
  mounted(){
    this.checkURL();
  },
  computed: {
    keyword: function() {
      this.content = this.$route.params.keyword;
      return this.content;
    },
    category: function(){
      this.selctedCategory=this.$route.params.category;
      return this.selctedCategory;
    }
  }
};
</script>
<style scoped>
.filtering {
  background-color: lightgray;
  border-radius: 12px;
}
.bold{
  font-weight: bold;
}
</style>
