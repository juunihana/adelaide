<template>
  <div class="flex-col gap-100">
    <div class="block" v-if="localState.loading">Loading</div>
    <div class="block" v-if="localState.error">Error</div>
    <user-post v-else v-for="post in localState.posts" :post="post"/>
    <footer></footer>
  </div>
</template>

<script setup>
import UserPost from "./user-posts/UserPost.vue";
import {reactive} from "vue";
import UserService from "../service/UserService.js";

const localState = reactive({
  loading: false,
  error: null,
  errorMessage: null,
  posts: []
})

const props = defineProps({
  username: null
})

localState.loading = true
UserService.getUserPosts(props.username)
.then(data => {
  localState.loading = false
  localState.posts = data.data
})
.catch(error => {
  localState.loading = false;
  localState.error = true;
  localState.errorMessage = error.data
})
</script>

<style scoped>

</style>