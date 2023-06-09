<template>
  <div class="overlay flex-row" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container flex-col gap-100" @click.stop>
      <header class="font-header text-center">Sign in</header>
      <div class="block error-block flex-col gap-50" v-if="localState.error">
        <div v-for="field in localState.errorMessage">{{ field }}</div>
      </div>
      <div class="form">
        <label>Username</label>
        <input type="text" placeholder="Username" v-model="localState.username"/>

        <label>Password</label>
        <input type="password" placeholder="Password" v-model="localState.password"/>
      </div>

      <Button @click.prevent="signIn" class="align-right" caption="Sign in" :loading="localState.loading"/>
    </div>
  </div>
</template>

<script setup>
import {generalStore} from "@/stores/generalStore";
import {reactive} from "vue";
import UserService from "@/service/UserService";
import VueCookies from "vue-cookies";
import Button from "./common/form/Button.vue";
import {useRoute, useRouter} from "vue-router";

const generalStorage = generalStore()
const route = useRoute()
const router = useRouter()

const localState = reactive({
  username: null,
  password: null,
  loading: false,
  error: false,
  errorMessage: []
})

function signIn() {
  localState.loading = true
  UserService.signIn(localState.username, localState.password)
  .then(data => {
    localState.loading = false
    VueCookies.set('auth', 'Bearer ' + data.data.token, '1d')

    generalStorage.showSignInOverlay = false
    generalStorage.checkSignIn()

    router.push("/" + localState.username)
  })
  .catch(err => {
    localState.errorMessage = []
    localState.loading = false
    localState.error = true
    if (err.response) {
      switch (err.response.data.result) {
        case "validationError":
          err.response.data.errorFields.forEach(field => localState.errorMessage.push(field))
          break
        case "userNotFoundError":
          localState.errorMessage.push(err.response.data.message)
          break
      }
    } else {
      localState.errorMessage.push(err.message)
    }
  })
}

function close() {
  generalStorage.showSignInOverlay = false;
}
</script>

<style scoped>
.overlay-container {
  width: 40vw;
  max-height: 40vh;
}

.form {
  display: grid;
  grid-template-columns: 1fr 5fr;
  gap: 1rem;
  align-items: baseline;
}
</style>