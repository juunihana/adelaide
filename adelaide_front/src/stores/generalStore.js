import {defineStore} from 'pinia'

export const generalStore = defineStore('generalStore', {
  state: () => ({
    showSignInOverlay: false,
    showSignUpOverlay: false
  }),
  actions: {}
})