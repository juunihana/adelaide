<template>
  <header class="root-header flex-row gap-100 align-center">
    <a class="root-header-logo">Adelaide</a>
    <div class="search-bar">
      <input type="text"/>
      <button>Search</button>
    </div>
    <nav class="flex-row gap-75">
      <router-link :to="'/' + this.$route.params.username" class="main-menu-element">My page</router-link>
      <a class="main-menu-element">Feed</a>
      <a class="main-menu-element">Messages</a>
      <a class="main-menu-element">Friends</a>
      <a class="main-menu-element">Groups</a>
      <a class="main-menu-element">Photos</a>
      <a class="main-menu-element">Music</a>
      <a class="main-menu-element">Videos</a>
    </nav>
    <div class="flex-row gap-100 align-right" v-if="!localState.signedInUser">
      <button @click="showSignIn">Sign in</button>
      <button>Sign up</button>
    </div>
    <button class="flex-row gap-100 align-right bg-light bg-hover align-center" v-else @click="toggleUserMenu">
      <img src="../assets/sample_image_48.png"/>
      {{ localState.signedInUser.firstName }}
      {{ localState.signedInUser.lastName }}
    </button>
    <div class="flex-col gap-25 block user-menu align-center" v-if="localState.userMenu" @click="toggleUserMenu">
      <a>Settings</a>
      <button @click="signOut">Sign out</button>
    </div>
  </header>
</template>

<script setup>
import Button from "@/components/common/form/Button.vue"
import {reactive} from "vue"
import {generalStore} from "@/stores/generalStore"

const generalStorage = generalStore()

let localState = reactive({
  userMenu: false,
  signedInUser: null
})

generalStorage.$subscribe((mutation, state) => {
  localState.signedInUser = state.signedIn
})

function showSignIn() {
  generalStorage.showSignInOverlay = true
  generalStorage.signIn("username", "password")
}

function signOut() {
  generalStorage.signOut()
}

function showSignUp() {
  generalStorage.showSignUpOverlay = true
}

function toggleUserMenu() {
  localState.userMenu = !localState.userMenu
}
</script>

<style scoped>
button img {
  max-height: 32px;
}
</style>