import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router.js";
import VModal from 'vue-js-modal';


Vue.config.productionTip = false;
Vue.use(VModal);


new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount("#app");
