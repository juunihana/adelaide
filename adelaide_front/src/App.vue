<template>
  <MainHeader/>
  <SignInOverlay v-if="localState.showSignInOverlay"/>
  <SignUpOverlay v-if="localState.showSignUpOverlay"/>
  <NewPostOverlay v-if="localState.showNewPostOverlay"/>
  <MediaOverlay v-if="false"/>
  <main class="root-container">
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
generalStorage.checkSignIn();

const localState = reactive({
  showSignUpOverlay: false,
  showSignInOverlay: false,
  showNewPostOverlay: false
})

generalStorage.$subscribe((mutation, state) => {
  localState.showSignUpOverlay = state.showSignUpOverlay
  localState.showSignInOverlay = state.showSignInOverlay
  localState.showNewPostOverlay = state.showNewPostOverlay
})
</script>

<style scoped>
.root-container {
  width: 85%;
  max-width: 85%;
  margin: 4rem auto;
  overflow: hidden;
}
</style>
