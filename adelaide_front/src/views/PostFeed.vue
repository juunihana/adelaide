<template>
  <main class="flex-col gap-100">
    <post-list :username="route.params.username"/>
  </main>
</template>

<script setup>
import {reactive} from "vue";
import {generalStore} from "@/stores/generalStore.js";
import PostList from "@/components/PostList.vue";
import {useRoute} from "vue-router";

const generalStorage = generalStore()

const route = useRoute()
const localState = reactive({
  currentUser: false,
  signedInUser: null
})

generalStorage.$subscribe((mutation, state) => {
  if (state.signedIn) {
    localState.currentUser = state.signedIn.username === route.params.username
  }

  localState.signedInUser = state.signedIn
})
</script>

<style scoped>

</style>
