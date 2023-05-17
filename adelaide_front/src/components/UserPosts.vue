<template>
  <div class="user-posts-container">
    <MenuStripe class="general-block">
      <SearchBar class="post-search-bar"/>
      <MenuLabel>Sort by</MenuLabel>
      <Button link="/">Recent</Button>
      <Button link="/">Top rated</Button>
      <Button class="new-post-button" v-if="signedInUser">New post</Button>
    </MenuStripe>
    <div class="loading-block" v-if="loading">Loading</div>
    <div class="error-block" v-else-if="error">Error</div>
    <UserPost v-else v-for="post in posts" :post="post"/>
    <footer>
      You have reached the end of the page. Congrats!
    </footer>
  </div>
</template>

<script>
import UserPost from "@/components/user-posts/UserPost.vue";
import MenuStripe from "@/components/common/MenuStripe.vue";
import SearchBar from "@/components/main-header/SearchBar.vue";
import MenuLink from "@/components/common/menu-stripe/MenuLink.vue";
import MenuLabel from "@/components/common/menu-stripe/MenuLabel.vue";
import Button from "@/components/common/form/Button.vue";
import UserService from "@/service/UserService.js";
import {generalStore} from "@/stores/generalStore";

export default {
  name: "UserPosts",
  components: {Button, MenuLabel, MenuLink, SearchBar, MenuStripe, UserPost},
  props: {
    username: null
  },
  data() {
    return {
      posts: [],
      generalStore: generalStore(),
      signedInUser: null,
      loading: false,
      error: false,
      errorStatus: null
    }
  },
  created() {
    this.$watch(
        () => this.$route.params,
        () => this.getUserPosts(),
        {immediate: true})
    this.$watch(
        () => this.generalStore.signedIn,
        () => this.signedInUser = this.generalStore.signedIn,
        {immediate: true})
  },
  methods: {
    getUserPosts() {
      this.loading = true;
      UserService.getUserPosts(this.$route.params.username)
      .then((data) => {
        this.loading = false
        this.posts = data.data
      }).catch((error) => {
        this.loading = false
        this.error = true
      })
    }
  }
}
</script>

<style scoped>
.user-posts-container {
  display: flex;
  flex-direction: column;
  height: 1vh;
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
  margin-bottom: 1rem;
}

.error-block {
  background: lightpink;
  border-radius: 3px;
  padding: 1rem;
  margin-bottom: 1rem;
}
</style>