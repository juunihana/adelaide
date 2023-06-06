import {defineStore} from 'pinia'
import VueCookies from "vue-cookies";
import UserService from "../service/UserService.js";
import router from "../router/router.js";

export const generalStore = defineStore('generalStore', {
  state: () => ({
    showSignInOverlay: false,
    showSignUpOverlay: false,
    showNewPostOverlay: false,
    token: null,
    signedIn: null
  }),
  actions: {
    signUp(user) {
      UserService.signUp(user)
      .then(data => {
        this.showSignUpOverlay = false
      })
      .catch(err => {
        console.log(err)
      })
    },
    signIn(username, password) {
      UserService.signIn(username, password)
      .then(data => {
        this.showSignInOverlay = false
        this.token = "Bearer " + data.data.token
        VueCookies.set('auth', this.token, '1d')
        this.checkSignIn()
        router.push("/" + username)
      })
      .catch(err => {
        console.log(err)
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
            lastName: data.data.lastName,
            avatar: data.data.avatar
          }
        })
      }
    },
  }
})