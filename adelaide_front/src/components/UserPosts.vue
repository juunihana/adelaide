<template>
  <div class="user-posts-container">
    <MenuStripe class="general-block">
      <SearchBar class="post-search-bar"/>
      <MenuLabel>Sort by</MenuLabel>
      <Button link="/">Recent</Button>
      <Button link="/">Top rated</Button>
      <Button class="new-post-button">New post</Button>
    </MenuStripe>
    <UserPost v-for="post in posts" :title="post.title" :content="post.content"/>
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

export default {
  name: "UserPosts",
  components: {Button, MenuLabel, MenuLink, SearchBar, MenuStripe, UserPost},
  data() {
    return {
      posts: []
    }
  },
  mounted() {
    UserService.getUserPosts("alice")
    .then(data => {
      this.posts = data.data
    })
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
</style>