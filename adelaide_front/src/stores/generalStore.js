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
        this.token = "Bearer " + data.data.token
        VueCookies.set('auth', this.token, '1d')
        this.checkSignIn()
        router.push("/" + username)
      })
    },
    signOut() {
      this.token = null
      this.signedIn = null
      VueCookies.remove('auth')
    },
    checkSignIn() {
      if (VueCookies.get('auth')) {
        UserService.getCurrentLoggedUser()
            .then((data) => {
              this.signedIn = {
                username: data.data.username,
                firstName: data.data.firstName,
                lastName: data.data.lastName
              }
            })
      }
    },
  }
})