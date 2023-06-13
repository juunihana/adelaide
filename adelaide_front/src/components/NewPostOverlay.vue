<template>
  <div class="overlay flex-row" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container flex-col gap-50" @click.stop>
      <header>What's on your mind?</header>
      <div class="block error-block flex-col gap-50" v-if="localState.error">
        <div v-for="field in  localState.errorMessage">{{ field }}</div>
      </div>
      <input type="text" placeholder="Title (not required)" v-model="localState.newPost.title"/>
      <textarea v-model="localState.newPost.content"></textarea>
      <div class="align-right flex-row gap-100">
        <button @click.prevent="close">Close</button>
        <button @click.prevent="addPost">Create</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "./common/form/Button.vue";
import {generalStore} from "@/stores/generalStore";
import {reactive} from "vue";
import UserService from "../service/UserService.js";
import {useRoute, useRouter} from "vue-router";

const generalStorage = generalStore()
const route = useRoute()
const router = useRouter()

const localState = reactive({
  newPost: {
    title: null,
    content: null,
    username: route.params.username
  },
  loading: false,
  error: false,
  errorMessage: null
})

function addPost() {
  localState.error = false
  localState.loading = true
  UserService.addPost(localState.newPost)
  .then(data => {
    localState.loading = false
    generalStorage.showNewPostOverlay = false;

    router.push("/" + localState.username)
  })
  .catch(err => {
    console.log(err)
    localState.loading = false
    localState.error = true
    localState.errorMessage = err.response.data.errorFields
  })
}

function close() {
  generalStorage.showNewPostOverlay = false;
}
</script>

<style scoped>
.overlay-container {
  width: 40vw;
  min-height: 25vh;
  /*  display: grid;*/
  /*  grid-template-columns: 1fr 5fr;*/
  /*  gap: 1rem;*/
}

header {
  text-align: center;
  font-size: 1.6rem;
  grid-column-start: 1;
  grid-column-end: 3;
}
</style>