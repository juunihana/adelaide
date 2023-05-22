<template>
  <div>


  </div>
</template>

<script setup>
import {reactive, watch} from "vue"
import UserService from "@/service/UserService.js"
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
</style>