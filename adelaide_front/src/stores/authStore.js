import {defineStore} from 'pinia'
import VueCookies from "vue-cookies";
import UserService from "../service/UserService";
import router from "@/router/router";

export const authStore = defineStore('authStore', {
  state: () =>
      ({
        signedIn: !!(VueCookies.get("auth") && VueCookies.get("auth") !== ""),
        token: VueCookies.get("auth") || null,
        username: VueCookies.get("username") || null
      }),
  actions: {
    isSignedIn() {
      return this.signedIn
    },
    getUsername() {
      return this.username
    },
    signIn(username, password) {
      UserService.signIn({username: username, password: password})
      .then((data) => {
            this.token = data.data.token
            this.username = username
            VueCookies.remove("auth")
            VueCookies.remove("username")
            VueCookies.set("auth", this.token)
            VueCookies.set("username", this.username)
            router.push("/user/" + this.username)
            this.signedIn = true
          },
          (error) => {
          })
    },
    signOut() {
      VueCookies.remove("auth")
      VueCookies.remove("username")
      this.signedIn = false
      this.username = null
    }
  }
})