<template>
  <header class="root-header flex-row gap-50 align-center">
    <router-link to="/" class="root-header-logo">Adelaide</router-link>
    <div class="search-bar flex-row gap-50">
      <input type="text" placeholder="Search"/>
      <button>Search</button>
    </div>
    <nav class="flex-row gap-75" v-if="localState.signedInUser">
      <router-link :to="'/' + localState.signedInUser.username" class="main-menu-element">My page
      </router-link>
      <router-link to="/feed">Feed</router-link>
      <router-link to="/messages">Messages</router-link>
      <router-link to="/friends">Friends</router-link>
      <router-link to="/groups">Groups</router-link>
      <router-link to="/photos">Photos</router-link>
      <router-link to="/music">Music</router-link>
      <router-link to="/videos">Videos</router-link>
    </nav>
    <div class="flex-row gap-50 align-right" v-if="!localState.signedInUser">
      <button @click="showSignIn">Sign in</button>
      <button @click="showSignUp">Sign up</button>
    </div>
    <button class="flex-row gap-25 align-right bg-hover align-center user-menu-button" v-else
            @click="toggleUserMenu">
      <img
          :src="localState.signedInUser.avatar ? localState.signedInUser.avatar : '../assets/sample_image_48.png'"
          alt="avatar"/>
      {{ localState.signedInUser.firstName }}
      {{ localState.signedInUser.lastName }}
    </button>
    <div class="flex-col gap-25 align-center user-menu" v-if="localState.userMenu"
         @click="toggleUserMenu">
      <a>Settings</a>
      <button @click="signOut">Sign out</button>
    </div>
  </header>
</template>

<script setup>
import {reactive} from "vue"
import {generalStore} from "@/stores/generalStore"

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
.user-menu {
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

.root-header {
  background: rgb(255 255 255 / 0.1);
  backdrop-filter: blur(50px);
  position: fixed;
  top: 0;
  height: 40px;
  width: 100vw;
  padding: 0 2rem 0 1rem;
  z-index: 999;
}
</style>