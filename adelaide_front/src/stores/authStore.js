import {defineStore} from 'pinia'
import VueCookies from "vue-cookies";
import UserService from "../service/UserService";

export const authStore = defineStore('authStore', {
  state: () =>
      ({
        signedIn: !!(VueCookies.get("username") &&
            VueCookies.get("username") !== ""),
        username: VueCookies.get("username"),
        token: VueCookies.get("auth-token") || null
      }),
  actions: {
    signIn(username, password) {
      // for test only
      this.username = username
      VueCookies.set("username", "alice", "1h")

      this.signedIn = true
      this.username = username
      UserService.signIn({username: username, password: password})
      .then((data) => {
            this.token = data.data.token
            VueCookies.set("auth-token", this.token, "1h")
          },
          (error) => {
          })
    },
    signOut() {
      VueCookies.remove("username")
      this.signedIn = false
      this.username = null
    }
  }
})