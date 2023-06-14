<template>
  <MainHeader/>
  <SignInOverlay v-if="localState.showSignInOverlay"/>
  <SignUpOverlay v-if="localState.showSignUpOverlay"/>
  <NewPostOverlay v-if="localState.showNewPostOverlay"/>
  <MediaOverlay v-if="false"/>

  <main-header/>
  <main class="root-container flex-row">
    <router-view/>
  </main>
</template>

<script setup>
import MainHeader from "./components/MainHeader.vue";
import SignInOverlay from "./components/SignInOverlay.vue";
import {generalStore} from "./stores/generalStore.js";
import SignUpOverlay from "./components/SignUpOverlay.vue";
import MediaOverlay from "./components/MediaOverlay.vue";
import {reactive} from "vue";
import NewPostOverlay from "./components/NewPostOverlay.vue";

const generalStorage = generalStore()
generalStorage.checkSignIn()

const localState = reactive({
  showSignUpOverlay: false,
  showSignInOverlay: false
})

generalStorage.$subscribe((mutation, state) => {
  localState.showSignUpOverlay = state.showSignUpOverlay
  localState.showSignInOverlay = state.showSignInOverlay
})
</script>

<style scoped>
</style>
