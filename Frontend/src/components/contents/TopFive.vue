<template>
  <v-card class="ma-auto pa-1 mt-5" outlined>
    <v-row>
      <v-col cols="12" sm="6" class="my-2 py-1">
        <p class="toptitle" style="text-align:center">성별</p>
        <v-row class="ma-auto">
          <v-col cols="12" sm="6" class="ma-0 pa-0">
            <h5 style="text-align:center">남</h5>
            <v-list two-line>
              <v-list-item-group multiple>
                <template v-for="(item, index) in menlist">
                  <v-list-item :key="item.title">
                    <template>
                      <v-list-item-content>
                        <v-row>
                          <v-col cols="1">
                            <span>{{ranking[index]}}</span>
                          </v-col>
                          <v-col cols="10">
                            <v-list-item-title
                              class="meetingtitle"
                              v-text="item.title"
                              @click="goDetail(item.meetingId)"
                            ></v-list-item-title>
                          </v-col>
                        </v-row>
                      </v-list-item-content>
                    </template>
                  </v-list-item>

                  <v-divider v-if="index + 1 < menlist.length" :key="index"></v-divider>
                </template>
              </v-list-item-group>
            </v-list>
          </v-col>
          <v-col cols="12" sm="6" class="ma-0 pa-0">
            <h5 style="text-align:center">여</h5>
            <v-list two-line>
              <v-list-item-group multiple>
                <template v-for="(item, index) in womenlist">
                  <v-list-item :key="item.title">
                    <template>
                      <v-list-item-content>
                        <v-row>
                          <v-col cols="1">
                            <span>{{ranking[index]}}</span>
                          </v-col>
                          <v-col cols="10">
                            <v-list-item-title
                              class="meetingtitle"
                              v-text="item.title"
                              @click="goDetail(item.meetingId)"
                            ></v-list-item-title>
                          </v-col>
                        </v-row>
                      </v-list-item-content>
                    </template>
                  </v-list-item>

                  <v-divider v-if="index + 1 < womenlist.length" :key="index"></v-divider>
                </template>
              </v-list-item-group>
            </v-list>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" sm="3">
        <div class="mx-auto">
          <p class="toptitle" style="text-align:center">연령대</p>
          <h5 style="text-align:center">{{ ages }} 대</h5>
          <v-list two-line>
            <v-list-item-group multiple>
              <template v-for="(item, index) in ageslist">
                <v-list-item :key="item.title">
                  <template>
                    <v-list-item-content>
                      <v-row>
                        <v-col cols="1">
                          <span>{{ranking[index]}}</span>
                        </v-col>
                        <v-col cols="10">
                          <v-list-item-title
                            class="meetingtitle"
                            v-text="item.title"
                            @click="goDetail(item.meetingId)"
                          ></v-list-item-title>
                        </v-col>
                      </v-row>
                    </v-list-item-content>
                  </template>
                </v-list-item>

                <v-divider v-if="index + 1 < ageslist.length" :key="index"></v-divider>
              </template>
            </v-list-item-group>
          </v-list>
        </div>
      </v-col>
      <v-col cols="12" sm="3">
        <div class="mx-auto">
          <p class="toptitle" style="text-align:center">지역</p>
          <h5 style="text-align:center">{{ area }}</h5>
          <v-list two-line>
            <v-list-item-group multiple>
              <template v-for="(item, index) in arealist">
                <v-list-item :key="item.title">
                  <template>
                    <v-list-item-content>
                      <v-row>
                        <v-col cols="1">
                          <span>{{ranking[index]}}</span>
                        </v-col>
                        <v-col cols="10">
                          <v-list-item-title
                            class="meetingtitle"
                            v-text="item.title"
                            @click="goDetail(item.meetingId)"
                          ></v-list-item-title>
                        </v-col>
                      </v-row>
                    </v-list-item-content>
                  </template>
                </v-list-item>

                <v-divider v-if="index + 1 < arealist.length" :key="index"></v-divider>
              </template>
            </v-list-item-group>
          </v-list>
        </div>
      </v-col>
    </v-row>
  </v-card>
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
      ranking: ["🥇", "🥈", "🥉", " ", " "],
      ages: "",
      area: ""
    };
  },
  mounted() {
    this.getTopFive();
    this.getUserInfo();
  },
  methods: {
    goDetail(seq) {
      if (this.$route.name == "myPage") {
        window.open(
          "http://k02b1051.p.ssafy.io/contentdetail/" + seq,
          "_blank"
        );
      } else {
        this.$router.push("/contentdetail/" + seq);
      }
    },
    getUserInfo() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .get(`user/info/`, config)
        .then(response => {
          this.ages = response.data.info.userinfo.ages;
          this.area =
            response.data.info.userinfo.area1 +
            " " +
            response.data.info.userinfo.area2;
        })
        .catch(() => {
          alert("토큰 만료! 다시 로그인 해주세요!");
          localStorage.clear();
          this.$router.go();
        });
    },
    getTopFive() {
      let config = {
        headers: { access_token: localStorage.getItem("token") }
      };
      http
        .get(`toplank`, config)
        .then(response => {
          this.menlist = response.data.성별.남;
          this.womenlist = response.data.성별.여;
          this.ageslist = response.data.연령;
          this.arealist = response.data.지역;
        })
        .catch(() => {
          alert("토큰 만료! 다시 로그인 해주세요!");
          localStorage.clear();
          this.$router.go();
        });
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
.toptitle {
  font-size: 13pt;
  font-family: "Jua", sans-serif;
}
.theme--light.v-card.v-card--outlined {
  border: thick solid #aed581;
  border-radius: 12px;
}
.meetingtitle {
  font-size: 9pt;
}
</style>
