<template>
  <div class="block flex-col gap-50">
    <div class="flex-row gap-50 align-center" style="padding-right: 1rem">
      <router-link :to="'/' + localState.post.author.username" class="flex-row gap-50 align-center">
        <img src="../../assets/sample_image_48.png"/>
        <div class="font-larger">
          {{ localState.post.author.firstName }}
          {{ localState.post.author.lastName }}
        </div>
      </router-link>
      <div class="align-right font-dimmed">{{ localState.post.timeCreated }}</div>
    </div>
    <div class="flex-row gap-50">
    <span>&#8627;</span>
    <a class="font-dimmed font-smaller">tag</a>
    <a class="font-dimmed font-smaller">tag</a>
    <a class="font-dimmed font-smaller">tag</a>
  </div>
    <div class="flex-col gap-25">
      <div class="font-title text-regular" v-if="localState.post.title">{{ localState.post.title }}</div>
      <div class="text-regular text-justify post-text">{{ localState.post.content }}</div>

    </div>
    <div class="flex-row gap-50">
      <div class="flex-row">
        <Button
            style="border-top-right-radius: 0; border-bottom-right-radius: 0; border-right: none"
            caption="&#9651;" class="font-success" @click="vote(true)"/>
        <div style="border: var(--border); padding: 0 1rem 0 1rem">{{
            localState.post.upVotes - localState.post.downVotes
          }}
        </div>
        <Button style="border-radius: 0; border-left: none"
                caption="&#9661;" class="font-danger" @click="vote(false)"/>
        <Button style="border-bottom-left-radius: 0; border-top-left-radius: 0; border-left: 0" :caption="'Comments ' + 0"/>
      </div>
      <div class="align-right flex-row gap-50" v-if="localState.currentUser">
        <button class="font-dimmed">Edit</button>
        <button class="font-danger" @click="removePost">Delete</button>
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
  currentUser: generalStorage.signedIn && generalStorage.signedIn.username
      === props.post.author.username,
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
img {
  max-height: 32px;
}
</style>