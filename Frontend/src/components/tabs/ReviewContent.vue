<template>
  <v-card class="my-4" width="100%" flat>
    <v-row class="mt-5 ml-2">
      
        <v-avatar size="30px">
                    <img src="https://cdn.vuetifyjs.com/images/cards/cooking.png"/>
                  </v-avatar>                  
        <span class="px-3" style="padding-top:5px; font-size:11pt; color:dimgrey">이름</span>
        <v-rating
          :value="5"
          background-color="white"
          color="amber"
          dense
          half-increments
          readonly
          small
          style="padding-top:4px;"
        ></v-rating>           
    </v-row>
    <v-row height="100px"> 
        <v-col><v-card width="180" outlined><v-img src="https://cdn.vuetifyjs.com/images/cards/cooking.png" /></v-card></v-col>
        <v-col cols="9"> <v-card outlined class="pa-3" width="100%" height="100%"> 내용 </v-card></v-col>
    </v-row>
  </v-card>
</template>
<script>
export default {
  name: "reviewContent"
};
</script>