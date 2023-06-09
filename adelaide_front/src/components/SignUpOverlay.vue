<template>
  <div class="overlay align-center flex-row" @click="close" @wheel.prevent @touchmove.prevent
       @scroll.prevent>
    <div class="overlay-container block flex-col gap-100" @click.stop>
      <header class="text-center font-header">Sign up</header>
      <div class="block error-block flex-col gap-50" v-if="localState.error">
        <div v-for="field in localState.errorMessage">{{ field }}</div>
      </div>
      <div class="step-form" v-if="localState.step === 0">
        <label>Email</label>
        <input type="text" v-model="localState.user.email"/>
        <label>Username</label>
        <input type="text" v-model="localState.user.username"/>
        <label>Password</label>
        <input type="password" v-model="localState.user.password"/>
        <label>Repeat password</label>
        <input type="password" v-model="localState.user.repeatPassword"/>
      </div>
      <div class="step-form" v-else-if="localState.step === 1">
        <label>First name</label>
        <input type="text" v-model="localState.user.firstName"/>
        <label>Last name</label>
        <input type="text" v-model="localState.user.lastName"/>
        <label>Date of birth</label>
        <div class="flex-row gap-50">
          <select v-model="localState.user.day">
            <option selected :value="day" v-for="day in days">{{ day }}</option>
          </select>
          <select v-model="localState.user.month">
            <option v-for="month in months" :value="months.indexOf(month)">{{ month }}</option>
          </select>
          <select v-model="localState.user.year">
            <option selected :value="year" v-for="year in years">{{ year }}</option>
          </select>
        </div>
      </div>
      <div class="align-right flex-row gap-50">
        <Button caption="Back" @click="localState.step--" v-if="localState.step > 0"/>
        <Button @click.prevent="stepForward" v-if="localState.step < 1" caption="Next"
                :loading="localState.loading"/>
        <Button @click.prevent="signUp" v-if="localState.step === 1" caption="Sign up"
                :loading="localState.loading"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "@/components/common/form/Button.vue";
import {generalStore} from "@/stores/generalStore";
import {computed, reactive} from "vue";
import UserService from "@/service/UserService";
import VueCookies from "vue-cookies";
import {useRouter} from "vue-router";
import ValidationService from "../service/ValidationService.js";

const generalStorage = generalStore()
const router = useRouter()

const localState = reactive({
  user: {
    email: null,
    username: null,
    password: null,
    repeatPassword: null,
    firstName: null,
    lastName: null,
    day: null,
    month: null,
    year: null
  },
  step: 0,
  loading: false,
  error: false,
  errorMessage: []
})

const months = ["January", "February", "March", "April", "May", "June", "July", "August",
  "September", "October", "November", "December"]

const days = computed(() => {
  switch (localState.user.month) {
    case "January":
    case "March":
    case "May":
    case "July":
    case "August":
    case "October":
    case "December":
      return Array.from({length: 31}, (_, i) => i + 1)
    case "February":
      return (localState.user.year && localState.user.year % 4 === 0) ?
          Array.from({length: 29}, (_, i) => i + 1) :
          Array.from({length: 28}, (_, i) => i + 1)
    default:
      return Array.from({length: 30}, (_, i) => i + 1)
  }
})

const years = Array.from({length: 101}, (_, i) => new Date().getFullYear() - 100 + i)

function signUp() {
  localState.loading = true
  UserService.signUp(localState.user)
  .then(() => {
    localState.loading = false
    UserService.signIn(localState.user.username, localState.user.password)
    .then(data => {
      VueCookies.set('auth', 'Bearer ' + data.data.token, '1d')

      generalStorage.checkSignIn()
      generalStorage.showSignUpOverlay = false

      router.push("/" + localState.user.username)
    })
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
      }
    } else {
      localState.errorMessage.push(err.message)
    }
  })
}

function close() {
  generalStorage.showSignUpOverlay = false
}

function stepForward() {
  console.log(localState.user.password)
  const errors = ValidationService.validateUsernameEmailPassword(
      localState.user.email,
      localState.user.username,
      localState.user.password,
      localState.user.repeatPassword)

  if (errors.length > 0) {
    localState.error = true
    localState.errorMessage = errors
  } else {
    localState.loading = true
    UserService.checkUserByUsernameAndEmail(localState.user.username, localState.user.email, localState.user.password)
    .then(() => {
      localState.loading = false
      localState.error = false
      localState.step++
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
        }
      } else {
        localState.errorMessage.push(err.message)
      }
    })
  }
}
</script>

<style scoped>
.overlay-container {
  width: 40vw;
  min-height: 30vh;
  padding: 1rem;
}

.step-form {
  display: grid;
  grid-template-columns: 2fr 7fr;
  gap: 1.5rem;
  align-items: baseline;
}
</style>