<template>
  <header class="root-header flex-row gap-100 align-center">
    <a class="root-header-logo">Adelaide</a>
    <div class="search-bar flex-row gap-50">
      <TextInput placeholder="Search"/>
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
      <button @click="showSignUp">Sign up</button>
    </div>
    <button class="flex-row gap-25 align-right bg-hover align-center user-menu-button" v-else @click="toggleUserMenu">
      <img src="../assets/sample_image_48.png"/>
      {{ localState.signedInUser.firstName }}
      {{ localState.signedInUser.lastName }}
    </button>
    <div class="flex-col gap-25 align-center user-menu" v-if="localState.userMenu" @click="toggleUserMenu">
      <a>Settings</a>
      <button @click="signOut">Sign out</button>
    </div>
  </header>
</template>

<script setup>
import Button from "@/components/common/form/Button.vue"
import {reactive} from "vue"
import {generalStore} from "@/stores/generalStore"
import SearchBar from "./main-header/SearchBar.vue";
import TextInput from "./common/form/TextInput.vue";

const generalStorage = generalStore()

let localState = reactive({
  userMenu: false,
  signedInUser: null
})

function showSignIn() {
  generalStorage.showSignInOverlay = true
}

function showSignUp() {
  generalStorage.showSignUpOverlay = true
}

function toggleUserMenu() {
  localState.userMenu = !localState.userMenu
}

function signOut() {
  generalStorage.signOut()
}

generalStorage.$subscribe((mutation, state) => {
  localState.signedInUser = state.signedIn
})
</script>

<style scoped>
.user-menu{
  position: absolute;
  top: 2.5rem;
  right: 2rem;
  width: 10vw;
  border-radius: 0 0 5px 5px;
  background: rgb(255 255 255 / 0.1);
  backdrop-filter: blur(50px);
  padding: 1rem;
}

.user-menu-button {
  border-radius: 0;
  width: 10vw;
  height: 40px;
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  font-family: Ysabeau, Arial, sans-serif;
  font-size: 1.2rem;
}

button img {
  max-height: 32px;
}

.search-bar input[type=text] {
  border-radius: 0;
  height: 40px;
}

.search-bar button {
}
</style>