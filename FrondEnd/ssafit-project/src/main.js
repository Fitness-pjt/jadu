import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

// VCalendar 사용
import VCalendar from "v-calendar";
import "v-calendar/style.css";

// Bootstrap 사용
import 'bootstrap'

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import 'bootstrap-icons/font/bootstrap-icons.css'
import 'animate.css'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { 
  faDumbbell, 
  faPencilAlt, 
  faRobot, 
  faArrowRight, 
  faMagic,
  faFolderPlus,
  faRunning,
  faExclamationCircle,
  faFolderOpen,
  faPlus,
  faSearch 
} from '@fortawesome/free-solid-svg-icons'

library.add(
    faDumbbell, 
    faPencilAlt, 
    faRobot, 
    faArrowRight, 
    faMagic,
    faFolderPlus,
    faRunning,
    faExclamationCircle,
    faFolderOpen,
    faPlus,
    faSearch
  )
const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(VCalendar, {});

app.mount("#app");
