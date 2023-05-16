import {defineStore} from 'pinia'
import VueCookies from "vue-cookies";
import UserService from "../service/UserService.js";
import router from "../router/router.js";

export const generalStore = defineStore('generalStore', {
  state: () => ({
    showSignInOverlay: false,
    showSignUpOverlay: false,
    token: null,
    signedIn: null
  }),
  actions: {
    signIn(username, password) {
      UserService.signIn(username, password)
      .then(data => {
        this.signedIn = username
        this.token = "Bearer " + data.data.token;
        VueCookies.set('auth', this.token, '1d')
        // router.push("/" + username)
      })
    },
    checkSignedUser() {
      if (VueCookies.get('auth')) {
        UserService.getCurrentLoggedUser()
            .then((data) => {
              this.signedIn = data.response.username
            })
      } else {
        return null;
      }
    }
  }
})