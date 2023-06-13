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
      this.token = null
      this.signedIn = null
      VueCookies.remove('auth')
    },
    async checkSignIn() {
      if (VueCookies.get('auth')) {
        const signedInUser = await UserService.getCurrentLoggedUser()
        this.signedIn = {
          username: signedInUser.data.username,
          firstName: signedInUser.data.firstName,
          lastName: signedInUser.data.lastName,
          avatar: signedInUser.data.avatar
        }
      }
    },
  }
})