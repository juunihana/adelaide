<template>
  <div class="block flex-col gap-100" :style="{'height': expandNew }" id="newPostPanel">
    <div class="flex-row gap-50 align-center">
      <div class="search-bar flex-row gap-50">
        <input type="text"/>
        <Button caption="Search" class="bg-hover"/>
      </div>
      <div>Sort by</div>
      <Button caption="Time"/>
      <Button caption="Rating"/>
      <Button caption="New post" class="align-right" @click="toggleNew" v-if="localState.signedIn"/>
    </div>

    <div class="flex-col gap-50">
      <textarea v-model="localState.newPost.content" placeholder="What's on your mind?"></textarea>
      <div class="align-right flex-row gap-100">
        <Button @click.prevent="addPost" caption="Create" :loading="localState.loading"/>
      </div>
      <!--      <div class="block error-block flex-col gap-50" v-if="localState.error">-->
      <!--        <div v-for="field in  localState.errorMessage">{{ field }}</div>-->
      <!--      </div>-->
    </div>
  </div>
  <div class="flex-col gap-100">
    <div class="block" v-if="localState.loading">Loading</div>
    <div class="block" v-if="localState.error">Error</div>
    <user-post v-else v-for="post in localState.posts" :post="post"/>
    <footer></footer>
  </div>
</template>

<script setup>
import UserPost from "./user-posts/UserPost.vue";
import {computed, onMounted, reactive} from "vue";
import UserService from "../service/UserService.js";
import {useRouter} from "vue-router";
import {generalStore} from "../stores/generalStore.js";
import Button from "./common/form/Button.vue";

const router = useRouter()
const generalStorage = generalStore()

const props = defineProps({
  username: null
})

const localState = reactive({
  signedIn: generalStorage.signedIn,
  loading: false,
  error: false,
  errorMessage: null,
  posts: [],
  expandNewPost: false,
  newPost: {
    title: null,
    content: null,
    username: props.username
  }
})

const expandNew = computed(() => {
  return localState.expandNewPost ? "32.5vh" : "60px"
})
onMounted(() => {
  loadUserPosts()
})

function loadUserPosts() {
  localState.loading = true
  UserService.getUserPosts(props.username)
  .then(data => {
    localState.posts = []
    localState.loading = false
    localState.posts = data.data
  })
  .catch(error => {
    localState.loading = false;
    localState.error = true;
    localState.errorMessage = error.data
  })
}

function addPost() {
  if (!localState.loading) {
    localState.error = false
    localState.loading = true
    UserService.addPost(localState.newPost)
    .then(data => {
      localState.loading = false
      toggleNew()

      // router.push("/" + props.username)
      loadUserPosts()
    })
    .catch(err => {
      console.log(err)
      localState.loading = false
      localState.error = true
      localState.errorMessage = err.response.data.errorFields
    })
  }
}

function toggleNew() {
  localState.expandNewPost = !localState.expandNewPost
}

generalStorage.$subscribe((mutation, state) => {
  console.log(state.signedIn)
  localState.signedIn = state.signedIn
})
</script>

<style scoped>
#newPostPanel {
  /*padding: 0;*/
  transition: 0.3s;
  overflow: hidden;
}

textarea {
  height: 20vh;
}
</style>