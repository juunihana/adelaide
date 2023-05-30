<template>
  <main class="user-page-container">
    <div class="user-profile-container">
      <div class="user-profile-side-container general-block">
        <div class="user-profile-avatar" v-if="!localState.loadingProfile">
          <img :src="localState.user.avatar" alt="avatar" v-if="!localState.loadingProfile"/>
        </div>
        <div class="user-profile-name" v-if="!localState.loadingProfile">
          {{ localState.user.firstName }}
          {{ localState.user.middleName }}
          {{ localState.user.lastName }}
          {{ localState.user.maidenSurname ? '(' + localState.user.maidenSurname + ')' : '' }}
        </div>
        <div class="user-profile-age" v-if="!localState.loadingProfile">
          {{ localState.user.age }} years old
        </div>
        <div class="user-profile-place" v-if="!localState.loadingProfile">
          From {{ localState.user.place }}
        </div>
        <div class="user-profile-bio" v-if="!localState.loadingProfile">
          {{ localState.user.bio }}
        </div>
        <Button v-if="!localState.currentUser" @click="sendFriendRequest">
          Send friends request
        </Button>
      </div>
      <div class="user-profile-side-container general-block" v-if="localState.user.friends">
        <router-link to="/">
          <div class="side-block-header">Friends</div>
        </router-link>
        <router-link class="side-block-element" :to="'/' + friend.username"
                     v-for="friend in localState.user.friends">
          <div class="side-block-image"><img :src="friend.avatar" alt="avatar"/></div>
          <div class="side-block-text">{{ friend.firstName }} {{ friend.lastName }}</div>
        </router-link>
      </div>
      <div class="user-profile-side-container general-block">
        <router-link to="/">
          <div class="side-block-header">Groups</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_48.png" alt="avatar"/></div>
          <div class="side-block-text">Group</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_48.png" alt="avatar"/></div>
          <div class="side-block-text">Group</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_48.png" alt="avatar"/></div>
          <div class="side-block-text">Group</div>
        </router-link>
      </div>
      <div class="user-profile-side-container general-block">
        <router-link to="/">
          <div class="side-block-header">Photo</div>
        </router-link>
        <div class="user-profile-side-photo-container">
          <router-link class="side-block-photo-element-top" to="/">
            <img src="@/assets/photo_96.png" alt="photo"/>
          </router-link>
          <router-link class="side-block-photo-element" to="/">
            <img src="@/assets/photo_96.png" alt="photo"/>
          </router-link>
          <router-link class="side-block-photo-element" to="/">
            <img src="@/assets/photo_96.png" alt="photo"/>
          </router-link>
          <router-link class="side-block-photo-element" to="/">
            <img src="@/assets/photo_96.png" alt="photo"/>
          </router-link>
        </div>
      </div>
    </div>
    <div class="user-posts-container">
      <MenuStripe class="general-block">
        <SearchBar class="post-search-bar"/>
        <MenuLabel>Sort by</MenuLabel>
        <Button>Recent</Button>
        <Button>Top rated</Button>
        <Button class="new-post-button" v-if="localState.currentUser" @click="showNewPost">New post</Button>
      </MenuStripe>
      <div class="loading-block" v-if="localState.loadingPosts">Loading</div>
      <div class="error-block" v-else-if="localState.errorPosts">Error</div>
      <UserPost v-else v-for="post in localState.posts" :post="post"/>
      <footer>
        You have reached the end of the page. Congrats!
      </footer>
    </div>
  </main>
</template>

<script setup>
import MenuStripe from "../components/common/MenuStripe.vue";
import SearchBar from "../components/main-header/SearchBar.vue";
import MenuLabel from "../components/common/menu-stripe/MenuLabel.vue";
import UserPost from "../components/user-posts/UserPost.vue";
import Button from "../components/common/form/Button.vue";
import {reactive, ref, watch} from "vue";
import {useRoute} from "vue-router";
import UserService from "../service/UserService.js";
import {generalStore} from "../stores/generalStore.js";

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
.user-page-container {
  display: grid;
  grid-column-gap: 2rem;
  grid-template-columns: 1fr 3fr;
}

.user-profile-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.user-profile-side-container {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  padding: 0.75rem;
}

.user-profile-side-photo-container {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 0.25rem;
}

.user-profile-age {
  font-size: 0.1rem;
}

.side-block-element, .side-block-header {
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
  border-radius: var(--border-radius);
  font-family: var(--font-family);
  align-items: center;
  cursor: pointer;
}

.side-block-header {
  height: 3vh;
  font-size: 1.2rem;
  color: var(--color);
  padding: 0.25rem;
}

.side-block-element {
  height: 5vh;
  background: var(--background-root);
}

.side-block-header:hover {
  background: var(--background-hover);
}

.side-block-element:hover {
  background: var(--background-hover);
}

.side-block-photo-element-top {
  grid-column-start: 1;
  grid-column-end: 4;
}

.side-block-photo-element {

}

.side-block-image {
  flex: 1;
  /*cursor: pointer;*/
}

.side-block-image img {
  object-fit: cover;
  max-height: 40px;
  cursor: pointer;
}

.side-block-text {
  flex: 6;
  color: var(--color);
  cursor: pointer;
}

.user-posts-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.user-profile-avatar {
  text-align: center;
}

.user-profile-avatar img {
  max-width: 200px;
  max-height: 200px;
}

.user-profile-name {
  font-size: 1.6rem;
}

.user-profile-age {
  font-size: 1rem;
}

.user-profile-place {
  font-size: 1rem;
}

.user-profile-bio {
  font-size: 1rem;
}

.post-search-bar {
}

.new-post-button {
  margin-left: auto;
  margin-right: 0;
}

.loading-block {
  background: cornflowerblue;
  border-radius: 3px;
  padding: 1rem;
}

.error-block {
  background: lightpink;
  border-radius: 3px;
  padding: 1rem;
}
</style>
