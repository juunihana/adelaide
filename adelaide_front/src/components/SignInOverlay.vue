<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" @click.stop>
      <header>Sign in</header>
      <label>Username</label>
      <TextInput placeholder="Username" v-model="username"/>

      <label>Password</label>
      <TextInput placeholder="Password" v-model="password"/>

      <label></label>
      <Button @click.prevent="signIn">
        Sign in
      </Button>
    </div>
  </div>
</template>

<script setup>
import TextInput from "./common/form/TextInput.vue";
import Button from "./common/form/Button.vue";
import {generalStore} from "@/stores/generalStore";

const generalStorage = generalStore()

function close() {
  generalStorage.showSignInOverlay = false;
}
</script>

<style scoped>
.overlay-container {
  width: 40vw;
  min-height: 25vh;
  padding: 1rem;
  display: grid;
  grid-template-columns: 1fr 5fr;
  gap: 1rem;
  align-items: baseline;
}

header {
  text-align: center;
  font-size: 1.6rem;
  grid-column-start: 1;
  grid-column-end: 3;
}
</style>