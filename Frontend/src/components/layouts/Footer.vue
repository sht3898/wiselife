<template>
<div>
<v-divider></v-divider>
  <v-footer  padless  color="white">
    <v-card class="footer" flat tile>
      <v-card-text>
        <v-row>
          <v-col>
            <div style="text-align:right; float:right">
              <v-tooltip top>
                <template v-slot:activator="{ on }">
                  <v-img v-on="on" :src="getImgUrl('teamlogo.png')" @click="team" width="90px" />
                </template>
                <span>About Developer</span>
              </v-tooltip>
            </div>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-footer>
</div>
</template>
<script>
export default {
  name: "Footer",
  data() {
    return {};
  },

  components: {},
  methods: {
    team() {
      this.$router.push("/team");
    },
    getImgUrl(img) {
      return require("../../assets/" + img);
    }
  }
};
</script>
<style scoped>
.footer{
  max-width: 930px;
  margin: auto;
  width: 100%;
}
</style>


