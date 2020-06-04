<template>
  <div class="ma-auto pa-1 mt-5" style="background-color:#e9ffe0; border-radius: 10px;">
    <v-row>
      <v-col cols="12" sm="6">
        <v-card class="mx-auto">
          <v-row>
            <v-col cols="12" sm="6">
              <h5>성별</h5>
              <h5>남</h5>
              <v-list two-line>
                <v-list-item-group multiple>
                  <template v-for="(item, index) in menlist">
                    <v-list-item :key="item.title">
                      <template>
                        <v-list-item-content>
                          <span>{{ranking[index]}}</span>
                          <v-list-item-title v-text="item.title"></v-list-item-title>
                        </v-list-item-content>
                      </template>
                    </v-list-item>

                    <v-divider v-if="index + 1 < menlist.length" :key="index"></v-divider>
                  </template>
                </v-list-item-group>
              </v-list>
            </v-col>
            <v-col cols="12" sm="6">
              <h5>성별</h5>
              <h5>여</h5>
              <v-list two-line>
                <v-list-item-group multiple>
                  <template v-for="(item, index) in womenlist">
                    <v-list-item :key="item.title">
                      <template>
                        <v-list-item-content>
                          <span>{{ranking[index]}}</span>
                          <v-list-item-title v-text="item.title"></v-list-item-title>
                        </v-list-item-content>
                      </template>
                    </v-list-item>

                    <v-divider v-if="index + 1 < womenlist.length" :key="index"></v-divider>
                  </template>
                </v-list-item-group>
              </v-list>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
      <v-col cols="12" sm="3">
        <v-card class="mx-auto">
          <h5>연령대</h5>
          <h5>{{ ages }} 대</h5>
          <v-list two-line>
            <v-list-item-group multiple>
              <template v-for="(item, index) in ageslist">
                <v-list-item :key="item.title">
                  <template>
                    <v-list-item-content>
                      <span>{{ranking[index]}}</span>
                      <v-list-item-title v-text="item.title"></v-list-item-title>
                    </v-list-item-content>
                  </template>
                </v-list-item>

                <v-divider v-if="index + 1 < ageslist.length" :key="index"></v-divider>
              </template>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-col>
      <v-col cols="12" sm="3">
        <v-card class="mx-auto">
          <h5>지역</h5>
          <h5>{{ area }}</h5>
          <v-list two-line>
            <v-list-item-group multiple>
              <template v-for="(item, index) in arealist">
                <v-list-item :key="item.title">
                  <template>
                    <v-list-item-content>
                      <span>{{ranking[index]}}</span>
                      <v-list-item-title v-text="item.title"></v-list-item-title>
                    </v-list-item-content>
                  </template>
                </v-list-item>

                <v-divider v-if="index + 1 < arealist.length" :key="index"></v-divider>
              </template>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import http from "../../http-common";
export default {
  name: "TopFive",
  data() {
    return {
      menlist: [],
      womenlist: [],
      ageslist: [],
      arealist: [],
      ranking: ["🥇", "🥈", "🥉", "4️", "5"],
      ages: "",
      area: ""
    };
  },
  mounted() {
    this.getTopFive();
    this.getUserInfo();
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
          this.ages = response.data.info.userinfo.ages;
          this.area =
            response.data.info.userinfo.area1 +
            " " +
            response.data.info.userinfo.area2;
        })
        .catch(error => {
          alert(error);
          this.$router.push("/");
        });
    },
    getTopFive() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http.get(`toplank`, config).then(response => {
        console.log(response);
        this.menlist = response.data.성별.남;
        this.womenlist = response.data.성별.여;
        this.ageslist = response.data.연령;
        this.arealist = response.data.지역;
      });
    }
  }
};
</script>

<style>
</style>
