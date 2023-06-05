<template>
  <div class="flex-col side-container gap-100">
    <div class="block" v-if="localState.loadingProfile">loading</div>
    <div class="flex-col gap-25 block" v-else>
      <div class="text-center">
        <img class="photo-link" src="../assets/sample_image_200.png"/>
      </div>
      <div class="font-title text-regular">
        {{ localState.user.firstName }}
        {{ localState.user.lastName }}
      </div>
      <div class="font-dimmed text-regular">
        {{ localState.user.age }} years old
      </div>
      <div class="font-dimmed text-regular" v-if="localState.user.place">
        From {{ localState.user.place }}
      </div>
      <div class="text-regular bg-light" v-if="localState.user.bio">
        {{ localState.user.bio }}
      </div>
      <div class="flex-row gap-25">
        <button class="font-dimmed">Edit info</button>
        <!--        <a class="font-dimmed">Send friends request</a> &lt;!&ndash; IF USER IS CURRENT SHOW EDIT INFO BUTTON INSTEAD &ndash;&gt;-->
        <!--        <a class="font-dimmed">Accept request</a>-->
        <!--        <a class="font-dimmed">Decline request</a>-->
        <!--        <a class="font-dimmed">Remove friend</a>-->
      </div>
    </div>
    <div class="block" v-if="localState.loadingProfile">loading</div>
    <div class="block flex-col gap-25" v-else>
      <a class="bg-hover block-header font-header">Friends {{ localState.user.friends.length }}</a>
      <router-link :to="'/' + friend.username" class="card bg-hover flex-row align-center gap-50" v-for="friend in localState.user.friends">
        <user-profile-card :cardInfo="{name: friend.firstName + ' ' + friend.lastName}"/>
      </router-link>
    </div>
    <div class="block" v-if="localState.loadingProfile">loading</div>
    <div class="block flex-col gap-25" v-else>
      <a class="block-header bg-hover font-header">Groups</a>
      <router-link :to="'/' + group.username" class="card bg-hover flex-row align-center gap-50" v-for="group in localState.user.groups">
        <user-profile-card :cardInfo="{name: group.name }"/>
      </router-link>
    </div>
  </div>
  <div class="block" v-if="localState.loadingProfile">loading</div>
  <div class="flex-col main-container gap-100" v-else>
    <div class="block flex-col gap-25">
      <a class="block-header bg-hover font-header">Photos</a>
      <div class="flex-row gap-50">
        <img src="../assets/sample_image_200.png"/>
        <img src="../assets/sample_image_200.png"/>
        <img src="../assets/sample_image_200.png"/>
        <img src="../assets/sample_image_200.png"/>
      </div>
    </div>
    <div class="block flex-row gap-50">
      <div class="search-bar">
        <input type="text"/>
        <button>Search</button>
      </div>
      <div class="">
        Sort by
      </div>
      <a>
        Time
      </a>
      <a>
        Rating
      </a>
      <a class="align-right">
        New post
      </a>
    </div>
    <div class="block" v-if="localState.loadingPosts">loading</div>
    <user-post v-else v-for="post in localState.posts" :post="post"/>
  </div>
</template>

<script setup>
import UserPost from "../components/user-posts/UserPost.vue";
import Button from "../components/common/form/Button.vue";
import {reactive, ref, watch} from "vue";
import {useRoute} from "vue-router";
import UserService from "../service/UserService.js";
import {generalStore} from "../stores/generalStore.js";
import UserProfileCard from "../components/user-profile/UserProfileCard.vue";

const route = useRoute()
const generalStorage = generalStore()

const localState = reactive({
  loadingProfile: false,
  errorProfile: false,
  errorMessageProfile: [],
  loadingPosts: false,
  errorPosts: false,
  errorMessagePosts: [],
  user: {},
  posts: [],
  currentUser: false
})

watch(() => route.params,
    () => {
      localState.loadingProfile = true
      UserService.getUserProfile(route.params.username)
      .then((data) => {
        localState.loadingProfile = false
        localState.user = data.data
      })
      .catch((error) => {
        localState.loadingProfile = false;
        localState.errorProfile = true;
        localState.errorMessageProfile = error.data
      })

      localState.loadingPosts = true
      UserService.getUserPosts(route.params.username)
      .then((data) => {
        localState.loadingPosts = false
        localState.posts = data.data
      })
      .catch((error) => {
        localState.loadingPosts = false;
        localState.errorPosts = true;
        localState.errorMessagePosts = error.data
      })
    },
    {immediate: true})

generalStorage.$subscribe((mutation, state) => {
  localState.currentUser = state.signedIn.username === route.params.username
})

function sendFriendRequest() {
  UserService.sendFriendRequest(route.params.username)
}

function showNewPost() {
  generalStorage.showNewPostOverlay = true
}
</script>

<style scoped>

</style>
