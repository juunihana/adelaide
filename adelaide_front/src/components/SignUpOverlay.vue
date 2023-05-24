<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" @click.stop>
      <header>Sign up</header>
      <label>Email</label>
      <TextInput></TextInput>

      <label>Username</label>
      <TextInput></TextInput>

      <label>Password</label>
      <TextInput></TextInput>

      <label>Repeat password</label>
      <TextInput></TextInput>

      <label>Phone</label>
      <TextInput></TextInput>

      <label>First name</label>
      <TextInput></TextInput>

      <label>Middle name</label>
      <TextInput></TextInput>

      <label>Last name</label>
      <TextInput></TextInput>

      <label>Maiden surname</label>
      <TextInput></TextInput>

      <label>Place</label>
      <TextInput></TextInput>

      <label>Bio</label>
      <TextInput></TextInput>

      <label>Date of birth</label>
      <TextInput></TextInput>

      <label></label>
      <Button>Sign up</Button>
    </div>
  </div>
</template>

<script setup>
import TextInput from "@/components/common/form/TextInput.vue";
import Button from "@/components/common/form/Button.vue";
import {generalStore} from "@/stores/generalStore";

const generalStorage = generalStore()

function close() {
  generalStorage.showSignUpOverlay = false
}
</script>

<style scoped>
.overlay-container {
  width: 40vw;
  min-height: 75vh;
  padding: 1rem;
  display: grid;
  grid-template-columns: 2fr 5fr;
  gap: 1rem;
  align-items: baseline;
}

header {
  grid-column-start: 1;
  grid-column-end: 3;
  font-size: 1.6rem;
  text-align: center;
}
</style>