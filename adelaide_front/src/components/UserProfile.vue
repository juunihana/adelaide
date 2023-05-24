<template>
  <div class="user-profile-container">
    <div v-if="state.loading">Loading</div>
    <div v-if="state.error">Error</div>
    <div v-if="state.user" class="user-profile-info-block general-block">
      <div class="user-profile-avatar">
        <img :src="state.user.avatar" alt="user avatar"/>
      </div>
      <div class="user-profile-name">
        <h1>{{ state.user.firstName }} {{ state.user.lastName }}</h1>
      </div>
      <div class="user-profile-age">
        <h3>{{ state.user.age }} years</h3>
      </div>
      <div class="user-profile-place">
        <h3>{{ state.user.place }}</h3>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, watch} from "vue"
import UserService from "../service/UserService.js"
import {useRoute} from "vue-router"

const route = useRoute()

const state = reactive({
  loading: false,
  error: false,
  errorMessage: [],
  user: {}
})

watch(() => route.params,
    () => {
      state.loading = true
      UserService.getUserProfile(route.params.username)
      .then((data) => {
        state.loading = false
        state.user = data.data
      })
      .catch((error) => {
        state.loading = false;
        state.error = true;
        state.errorMessage = error.data
      })
    },
    {immediate: true})

// let userProfile = reactive({
//   avatarLink: null,
//   firstName: null,
//   lastName: null,
//   age: null,
//   place: null,
//   friends: [],
//   groups: [],
//   photos: []
// })
</script>

<style scoped>
.user-profile-container {
  display: flex;
  flex-direction: column;
}
</style>