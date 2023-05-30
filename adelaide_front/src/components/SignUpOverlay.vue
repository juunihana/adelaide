<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" @click.stop>
      <header>Sign up</header>
      <label>Email</label>
      <TextInput placeholder="Email" v-model="localState.user.email"/>

      <label>Username</label>
      <TextInput placeholder="Username" v-model="localState.user.username"/>

      <label>Password</label>
      <TextInput placeholder="Password" v-model="localState.user.password"/>

      <label>Repeat password</label>
      <TextInput placeholder="Repeat" v-model="localState.user.repeatPassword"/>

      <label>Phone</label>
      <TextInput placeholder="Phone" v-model="localState.user.phone"/>

      <label>First name</label>
      <TextInput placeholder="First name" v-model="localState.user.firstName"/>

      <label>Middle name</label>
      <TextInput placeholder="Middle name" v-model="localState.user.middleName"/>

      <label>Last name</label>
      <TextInput placeholder="Last name" v-model="localState.user.lastName"/>

      <label>Maiden surname</label>
      <TextInput placeholder="Maiden surname" v-model="localState.user.maidenSurname"/>

      <label>Place</label>
      <TextInput placeholder="Place" v-model="localState.user.place"/>

      <label>Bio</label>
      <MultilineTextInput placeholder="Bio" v-model="localState.user.bio"/>

      <label>Date of birth</label>
      <TextInput placeholder="Date of birth (yyyy-mm-dd)" v-model="localState.user.dateOfBirth"/>

      <label></label>
      <Button @click="signUp">Sign up</Button>
    </div>
  </div>
</template>

<script setup>
import TextInput from "@/components/common/form/TextInput.vue";
import Button from "@/components/common/form/Button.vue";
import {generalStore} from "@/stores/generalStore";
import {reactive} from "vue";
import MultilineTextInput from "./common/form/MultilineTextInput.vue";

const generalStorage = generalStore()

const localState = reactive({
  user: {
    email: null,
    username: null,
    password: null,
    passwordRepeat: null,
    phone: null,
    firstName: null,
    middleName: null,
    lastName: null,
    maidenSurname: null,
    place: null,
    bio: null,
    dateOfBirth: null
  }
})

function signUp() {
  generalStorage.signUp(localState.user)
}

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