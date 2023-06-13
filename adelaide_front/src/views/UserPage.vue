<template>
  <div class="flex-col side-container gap-100">
    <div class="block" v-if="localState.loadingProfile">loading</div>
    <div class="flex-col gap-25 block" v-else>
      <div class="text-center">
        <img class="photo-link" src="../assets/sample_image_200.png"/>
      </div>
      <div class="font-header text-regular">
        {{ localState.user.firstName }} {{ localState.user.lastName }}
      </div>
      <div class="text-regular" v-if="localState.user.age">
        {{ localState.user.age }} years old
      </div>
      <div class="font-dimmed text-regular" v-if="localState.user.birthday">
        Birthday {{ localState.user.birthday }}
      </div>
      <div class="font-dimmed text-regular" v-if="localState.user.place">
        From {{ localState.user.place }}
      </div>
      <div class="text-regular bg-button" v-if="localState.user.bio">
        {{ localState.user.bio }}
      </div>
      <div class="flex-row gap-25">
        <button class="font-dimmed" v-if="localState.currentUser">Edit info</button>
        <button class="font-dimmed" v-if="localState.signedInUser && !localState.currentUser">Send friends request</button>
        <!-- IF USER IS CURRENT SHOW EDIT INFO BUTTON INSTEAD -->
        <!--        <a class="font-dimmed">Accept request</a>-->
        <!--        <a class="font-dimmed">Decline request</a>-->
        <!--        <a class="font-dimmed">Remove friend</a>-->
      </div>
    </div>
    <div class="block" v-if="localState.loading">loading</div>
    <div class="block flex-col gap-25" v-else>
      <a class="bg-hover block-header font-header">
        Friends {{ localState.user.friends ? localState.user.friends.length : '0' }}
      </a>
      <router-link :to="'/' + friend.username" class="card bg-hover flex-row align-center gap-50"
                   v-for="friend in localState.user.friends">
        <user-profile-card :cardInfo="{name: friend.firstName + ' ' + friend.lastName}"/>
      </router-link>
    </div>
    <div class="block" v-if="localState.loading">loading</div>
    <div class="block flex-col gap-25" v-else>
      <a class="block-header bg-hover font-header">
        Groups {{ localState.user.friends ? localState.user.friends.length : '0' }}
      </a>
      <router-link :to="'/' + group.username" class="card bg-hover flex-row align-center gap-50"
                   v-for="group in localState.user.groups">
        <user-profile-card :cardInfo="{name: group.name }"/>
      </router-link>
    </div>
  </div>
  <div class="block" v-if="localState.loading">loading</div>
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
      <div class="search-bar flex-row gap-50">
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
      <button class="align-right" @click="showNewPostOverlay" v-if="localState.signedInUser">
        New post
      </button>
    </div>
    <post-list :username="route.params.username"/>
  </div>
</template>

<script setup>
import Button from "@/components/common/form/Button.vue";
import {reactive, watch} from "vue";
import UserService from "@/service/UserService.js";
import {generalStore} from "@/stores/generalStore.js";
import UserProfileCard from "@/components/user-profile/UserProfileCard.vue";
import PostList from "@/components/PostList.vue";
import {useRoute} from "vue-router";

const generalStorage = generalStore()

const route = useRoute()
const localState = reactive({
  loading: false,
  error: false,
  errorMessage: [],
  user: {},
  currentUser: false,
  signedInUser: null
})

watch(() => route.params,
    () => {
      localState.loading = true
      UserService.getUserProfile(route.params.username)
      .then(data => {
        localState.loading = false
        localState.user = data.data
      })
      .catch(error => {
        localState.loading = false;
        localState.error = true;
        localState.errorMessage = error.data
      })
    },
    {immediate: true})

generalStorage.$subscribe((mutation, state) => {
  localState.currentUser = state.signedIn.username === route.params.username
  localState.signedInUser = state.signedIn
})

function sendFriendsRequest() {
  UserService.sendFriendRequest(route.params.username)
}

function showNewPostOverlay() {
  generalStorage.showNewPostOverlay = true
}
</script>

<style scoped>

</style>
