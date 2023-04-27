import {createApp} from 'vue'
import App from './App.vue'
import './assets/main.css'
import axios from "axios";
import VueAxios from "vue-axios";
import router from "./router/router"

createApp(App)
.use(router)
.use(VueAxios, axios)
.mount('#app')
