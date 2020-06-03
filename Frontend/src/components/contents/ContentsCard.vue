<template>
  <v-card :loading="loading" class="mx-auto my-3" width="200">
    <v-img height="150" @click="goDetail" src="https://cdn.vuetifyjs.com/images/cards/cooking.png">
    <div style="z-index:5; position: relative;">
    <v-chip :color="`green lighten-4`" class="black--text" label small style="top: 0px; left: 5px;">
        {{content.area1}} {{content.area2}}
      </v-chip>
      <v-btn icon style="top: 110px; left: 80px;">
        <v-icon color="white" style="opacity:0.7">mdi-heart</v-icon>
      </v-btn>
    </div>
    </v-img>

    <v-card-title class="contenttitle" style="font-weight:bold" @click="goDetail"> {{content.title}} </v-card-title>

    <v-card-text>
      <!-- <div class=" subtitle-1" v-for="tagname in content.tags" :key="tagname"> -->
      <!-- <div class=" subtitle-1" >
        <v-chip :color="`orange lighten-4`" class="black--text mr-2" label small @click="tagSearch(tag)">{{content.tags}}</v-chip></div> -->
     
     <v-chip
                  v-for="(tag, index) in tagname"
                  :key="tag"
                  :color="`${colors[index]} lighten-3`"
                  class="black--text mr-2 my-1"
                  label
                  small
                >#{{ tag }}</v-chip>
      <v-row align="center" class="mx-0 my-0">
        <v-rating :value="content.score" color="amber"  background-color="white" dense half-increments readonly size="14"></v-rating>

        <div class="grey--text ml-4">{{content.score}}</div>
        
      </v-row>
      <v-row class="mx-0 my-0">
        <v-col>
          <span class="mdi mdi-eye-outline" style="color:#e9c04c"></span>
          <span class="grey--text ml-4">{{content.viewCnt}}</span>
        </v-col>
        <v-col>
           <span class="mdi mdi-heart" style="color:red"></span>
          <span class="grey--text ml-4">{{content.likeCnt}}</span>
        </v-col>
      </v-row>
    

    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name:'ContentsCard',
  data: () => ({
    loading: false,
    selection: 1,
    colors: ["red", "amber", "lime", "teal", "indigo"],
    tagname:[]
  }),
  props:{
    content:{ type:Object }
  },
  methods: {
    tagparsing(){
      // console.log(this.content.tags);
      let split_tags = this.content.tags.split(" ");
            let tags = [];
            for (var i in split_tags) {
              tags.push(split_tags[i]);
            }
            this.tagname = tags;
            console.log(this.tagname);
    },
    reserve() {
      this.loading = true;

      setTimeout(() => (this.loading = false), 2000);
    },
    goDetail(){
      this.$router.push("/contentdetail/"+1)
    },
    tagSearch(tag){
      this.$router.push("/result/0/"+ tag)
    }
  },
  mounted(){
    this.tagparsing();
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.contenttitle {
  font-size: 15pt;
  font-family: "Jua", sans-serif;
}
</style>