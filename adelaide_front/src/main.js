import {createApp} from 'vue'
import App from './App.vue'
import './assets/main.css'
import axios from "axios"
import VueAxios from "vue-axios"
import router from "./router/router"
import {createPinia} from "pinia"
import VueCookies from 'vue-cookies'

const pinia = createPinia()

createApp(App)
.use(router)
.use(pinia)
.use(VueAxios, axios)
.use(VueCookies)
.mount('#app')
