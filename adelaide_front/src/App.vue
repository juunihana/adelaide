<template>
  <MainHeader/>
  <SignInOverlay v-if="false"/>
  <main class="root-container">
    <router-view/>
  </main>
</template>

<script setup>
import MainHeader from "./components/MainHeader.vue";
import SignInOverlay from "./components/SignInOverlay.vue";
import {generalStore} from "./stores/generalStore.js";

const generalStorage = generalStore()
generalStorage.checkSignIn();
</script>

<style scoped>
.root-container {
  width: 95%;
  max-width: 95%;
  margin: 4rem auto;
  overflow: hidden;
}
</style>
