import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import 'quill/dist/quill.snow.css';

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue-3/dist/bootstrap-vue-3.css";
import 'bootstrap-icons/font/bootstrap-icons.css';

const app = createApp(App);
app.use(store);
app.use(router);
app.mount("#app");
