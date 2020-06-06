<template>
  <div>
    <span>city : {{city}}</span><br/>
    <span>weather : {{weather}}</span><br/>
    <span>humidity : {{humidity}}</span><br/>
    <span>temp_min :{{temp_min}}</span><br/>
    <span>temp_max :{{temp_max}}</span><br/>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "weather",
  data() {
    return {
      city: "",
      weather: "",
      humidity: "",
      temp_min: "",
      temp_max: "",
      latitude: 0.0,
      longitude: 0.0
    };
  },
  mounted() {
    this.getLocation();
  },
  methods: {
    searchWeather(latitude, longitude) {
      http
        .get(
          `http://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=585008f316bb47798b7db47bef017803`
        )
        .then(result => {
          this.city = result.data.name;
          this.weather = result.data.weather[0].main;
          this.humidity = result.data.main.humidity;
          this.temp_min = result.data.main.temp_min;
          this.temp_max = result.data.main.temp_max;
        })
        .catch(error=>{
            console.log(error);
        });
    },
    getLocation() {
      const this_component = this;
      if (window.navigator.geolocation) {
        // GPS를 지원하면
        window.navigator.geolocation.getCurrentPosition(
          function(position) {
            this_component.searchWeather(
              position.coords.latitude,
              position.coords.longitude
            );
          },
          function(error) {
            console.error(error);
          },
          {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: Infinity
          }
        );
      } else {
        alert("GPS를 지원하지 않습니다");
      }
    }
  }
};
</script>

<style>
</style>