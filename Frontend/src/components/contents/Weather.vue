<template>
  <span class="px-3" style="color:dimgrey; font-size:10pt;">
    <v-row>
      <span class="px-1 city">{{area1}} {{area2}}</span>
      <span class="px-1">
        <v-img v-if="ok" :src="getIcon()" width="15px" />
      </span>
      <span class="px-1" style="color:darkred">{{temp_max}} °</span> /
      <span class="pl-1" style="color:darkblue">{{temp_min}} °</span>
    </v-row>
  </span>
</template>

<script>
import http from "../../http-common";

export default {
  name: "Weather",
  data() {
    return {
      city: "",
      ok: false,
      weather: "",
      humidity: "",
      area1: "",
      area2: "",
      temp_min: 0.0,
      temp_max: 0.0,
      latitude: 0.0,
      longitude: 0.0,
      result: []
    };
  },
  created() {
    this.getLocation();
  },
  methods: {
    searchWeather(latitude, longitude) {
      http
        .get(
          `http://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=585008f316bb47798b7db47bef017803&units=metric`
        )
        .then(result => {
          this.city = result.data.name;
          this.weather = result.data.weather[0].main;
          this.temp_min = result.data.main.temp_min;
          this.temp_max = result.data.main.temp_max;
          this.ok = true;
        })
        .catch(() => {});
    },
    getLocation() {
      let config = {
        headers: {
          access_token: localStorage.getItem("token")
        }
      };
      http
        .get(`user/info`, config)
        .then(response => {
          if (response.data.status == "success") {
            this.area1 = response.data.info.userinfo.area1;
            this.area2 = response.data.info.userinfo.area2;

            if (this.area2 == "전체") {
              this.area2 = "";
            }
            var keyword = this.area1 + " " + this.area2;

            var geocoder = new kakao.maps.services.Geocoder();
            const page = this;

            geocoder.addressSearch(keyword, function(result, status) {
              if (status === kakao.maps.services.Status.OK) {
                var latitude = result[0].y;
                var longitude = result[0].x;
                page.searchWeather(latitude, longitude);
              }
            });
          }
        })
        .catch(() => {
          alert("토큰 만료! 다시 로그인 해주세요!");
          localStorage.clear();
          this.$router.go();
        });
    },
    getIcon() {
      return require("../../assets/weather/" + this.weather + ".png");
    }
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.city {
  font-family: "Nanum Pen Script", cursive;
  font-size: 15px;
}
</style>