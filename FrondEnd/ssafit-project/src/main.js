import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

// VCalendar 사용
import VCalendar from "v-calendar";
import "v-calendar/style.css";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(VCalendar, {});

app.mount("#app");
