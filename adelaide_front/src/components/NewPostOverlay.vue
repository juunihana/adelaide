<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" @click.stop>
      <header>Create new post</header>
      <label>Title</label>
      <TextInput placeholder="Title" v-model="localState.newPost.title"/>

      <label>Content</label>
      <TextInput placeholder="Content" v-model="localState.newPost.content"/>

      <label></label>
      <Button @click.prevent="addPost">
        Create
      </Button>
    </div>
  </div>
</template>

<script setup>
import TextInput from "./common/form/TextInput.vue";
import Button from "./common/form/Button.vue";
import {generalStore} from "@/stores/generalStore";
import {reactive} from "vue";
import UserService from "../service/UserService.js";
import {useRoute} from "vue-router";

const generalStorage = generalStore()
const route = useRoute()

const localState = reactive({
  newPost: {
    title: null,
    content: null,
    username: route.params.username
  }
})

function addPost() {
  console.log(localState.newPost)
  UserService.addPost(localState.newPost)
  .then(() => {
    generalStorage.showNewPostOverlay = false;
  })
  .catch((err) => {
    console.log(err)
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