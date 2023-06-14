import {defineStore} from 'pinia'
import VueCookies from "vue-cookies";
import UserService from "../service/UserService.js";

export const generalStore = defineStore('generalStore', {
  state: () => ({
    showSignInOverlay: false,
    showSignUpOverlay: false,
    showNewPostOverlay: false,
    signedIn: null
  }),
  actions: {
    setSignedInUser(user) {
      this.signedIn = user
    },
    signUp(user) {
      UserService.signUp(user)
      .then(data => {
        this.showSignUpOverlay = false
      })
      .catch(err => {
        console.log(err)
      })
    },
    signOut() {
      this.signedIn = null
      VueCookies.remove('auth')
    },
    checkSignIn() {
      if (VueCookies.get('auth')) {
        UserService.getCurrentLoggedUser().then(data => {
          this.signedIn = data.data
        })
        .catch(err => {
          console.err(err)
        })
      }
    },
  }
})