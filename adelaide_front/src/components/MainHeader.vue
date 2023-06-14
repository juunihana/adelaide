<template>
  <header class="root-header flex-row gap-50 align-center">
    <router-link to="/" class="root-header-logo">Adelaide</router-link>
    <div class="search-bar flex-row gap-50">
      <input type="text" placeholder="Search"/>
      <Button caption="Search"/>
    </div>
    <nav class="flex-row gap-75" v-if="localState.signedIn">
      <router-link :to="'/' + localState.signedIn.username" class="main-menu-element">My page
      </router-link>
      <router-link to="/feed">Feed</router-link>
      <router-link to="/messages">Messages</router-link>
      <router-link to="/friends">Friends</router-link>
      <router-link to="/groups">Groups</router-link>
      <router-link to="/photos">Photos</router-link>
      <router-link to="/music">Music</router-link>
      <router-link to="/videos">Videos</router-link>
      <router-link to="/settings">Settings</router-link>
    </nav>
    <div class="flex-row gap-50 align-right" v-if="!localState.signedIn">
      <button class="bg-hover" @click="showSignIn">Sign in</button>
      <button class="bg-hover" @click="showSignUp">Sign up</button>
    </div>
    <div class="flex-row gap-50 align-right" v-else>
      <router-link :to="'/' + localState.signedIn.username" id="userLink" class="flex-row gap-25 align-center bg-hover">
        <img :src="avatar" alt="avatar" width="32" height="32"/>
        <div>{{
            localState.signedIn.firstName + ' ' + localState.signedIn.lastName
          }}
        </div>
      </router-link>
      <button class="bg-hover" @click="signOut">Sign out</button>
    </div>
  </header>
</template>

<script setup>
import {computed, reactive} from "vue"
import {generalStore} from "@/stores/generalStore"
import Button from "./common/form/Button.vue";

const generalStorage = generalStore()

let localState = reactive({
  userMenu: false,
  signedIn: null
})

const avatar = computed(() => localState.signedIn.avatar || "src/assets/sample_image_48.png")

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
  localState.signedIn = state.signedIn
})
</script>

<style scoped>
button img {
  max-height: 32px;
}

.root-header {
  background: var(--bg-block);
  backdrop-filter: var(--blur);
  position: fixed;
  top: 0;
  height: 40px;
  width: 100vw;
  padding: 0 2rem 0 1rem;
  z-index: 999;
}

#userLink {
  padding: 0.2rem 0.7rem;
}
</style>