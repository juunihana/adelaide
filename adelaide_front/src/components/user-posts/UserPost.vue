<template>
  <div class="block flex-col gap-50">
    <router-link :to="'/' + localState.post.author.username" class="block-header bg-hover flex-row gap-50 align-center">
      <img src="../../assets/sample_image_48.png"/>
      <div class="font-title">
        {{ localState.post.author.firstName }}
        {{ localState.post.author.lastName }}
      </div>
      <div class="align-right">
        {{ localState.post.timeCreated }}
      </div>
    </router-link>
    <div class="flex-col gap-25">
      <div class="font-title text-regular">
        {{ localState.post.title }}
      </div>
      <div class="text-regular text-justify post-text">
        {{ localState.post.content }}
      </div>
      <div class="flex-row gap-50">
        <a class="font-dimmed font-smaller">tag</a>
      </div>
    </div>
    <div class="flex-row gap-50">
      <div class="flex-row">
        <button class="upvote" @click="vote(true)">Upvote {{ localState.post.upVotes }}</button>
        <button class="downvote" @click="vote(false)">Downvote {{ localState.post.downVotes }}</button>
      </div>
      <button>Comment 0</button>
      <div class="align-right flex-row gap-50">
        <button class="font-dimmed">Edit</button>
        <button class="font-dimmed" @click="removePost">Delete</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "../common/form/Button.vue";
import {generalStore} from "../../stores/generalStore.js";
import {reactive} from "vue";
import UserService from "../../service/UserService.js";
import {useRoute} from "vue-router";
import router from "../../router/router.js";

const generalStorage = generalStore()
const route = useRoute()

const props = defineProps({
  post: {}
})

const localState = reactive({
  currentUser: generalStorage.signedIn && generalStorage.signedIn.username === props.post.author.username,
  post: props.post
})

async function vote(upVote) {
  if (localState.post.vote && localState.post.vote.upVote === upVote) {
    await UserService.removeVote(localState.post.vote.id)
  } else {
    await UserService.addVote({
      targetType: "post",
      targetId: localState.post.id,
      upVote: upVote
    })
  }

  await UserService.getUserVoteForPost(localState.post.id)
  .then((data) => {
    localState.post.upVotes = data.data.upVotes
    localState.post.downVotes = data.data.downVotes
    localState.post.vote = data.data.vote
  })
}

function removePost() {
  UserService.removePost(localState.post.id)
  router.push("/" + route.params.username)
}
</script>

<style scoped>

</style>