import {defineStore} from 'pinia'
import VueCookies from "vue-cookies";
import UserService from "../service/UserService";

export const authStore = defineStore('authStore', {
  state: () =>
      ({
        signedIn: !!(VueCookies.get("auth") &&
            VueCookies.get("auth") !== ""),
        token: VueCookies.get("auth") || null
      }),
  actions: {
    isSignedIn() {
      return this.signedIn
    },
    signIn(username, password) {
      UserService.signIn({username: username, password: password})
      .then((data) => {
            this.token = data.data.token
            VueCookies.set("auth", this.token, "24h")
        this.signedIn = true;
          },
          (error) => {
          })
    },
    signOut() {
      VueCookies.remove("auth")
      this.signedIn = false
      this.username = null
    }
  }
})