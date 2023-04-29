import {defineStore} from 'pinia'

export const authStore = defineStore('authStore', {
  state: () =>
      ({
        signedIn: false,
        username: null
      }),
  actions: {
    signIn(username) {
      this.signedIn = true
      this.username = username
    },
    signOut() {
      this.signedIn = false
      this.username = null
    }
  }
})