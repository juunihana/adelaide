<template>
  <main class="user-page-container">
    <div class="user-profile-container">
      <div class="user-profile-side-container general-block">
        <div class="user-profile-avatar">
          <img src="@/assets/photo_96.png" alt="avatar"/>
        </div>
        <div class="user-profile-name">
          Name Surname
        </div>
        <div class="user-profile-age">
          25 years
        </div>
        <div class="user-profile-place">
          Moscow
        </div>
        <div class="user-profile-bio">
          Software Engineer
        </div>
      </div>
      <div class="user-profile-side-container general-block">
        <router-link to="/">
          <div class="side-block-header">Friends</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_96.png" alt="avatar"/></div>
          <div class="side-block-text">Name Surname</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_96.png" alt="avatar"/></div>
          <div class="side-block-text">Name Surname</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_96.png" alt="avatar"/></div>
          <div class="side-block-text">Name Surname</div>
        </router-link>
      </div>
      <div class="user-profile-side-container general-block">
        <router-link to="/">
          <div class="side-block-header">Groups</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_96.png" alt="avatar"/></div>
          <div class="side-block-text">Group</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_96.png" alt="avatar"/></div>
          <div class="side-block-text">Group</div>
        </router-link>
        <router-link class="side-block-element" to="/">
          <div class="side-block-image"><img src="@/assets/photo_96.png" alt="avatar"/></div>
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
        <Button class="new-post-button" v-if="signedInUser">New post</Button>
      </MenuStripe>
      <div class="loading-block" v-if="loading">Loading</div>
      <div class="error-block" v-else-if="error">Error</div>
      <UserPost v-else v-for="post in posts" :post="post"/>
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
import {ref} from "vue";

const posts = ref([
  {
    title: "Title",
    content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    author: {
      username: "username",
      firstName: "Name",
      lastName: "Surname"
    }
  }
])

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
  max-height: 100%;
  cursor: pointer;
}

.side-block-text {
  flex: 6;
  font-size: 1rem;
  color: var(--input-color);
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
