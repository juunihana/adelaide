<template>
  <div class="user-posts-container">
    <UserPost/>
    <footer>
      You have reached the end of the page. Congrats!
    </footer>
  </div>
</template>

<script>
import UserPost from "./user-posts/UserPost";
export default {
  name: "UserPosts",
  components: {UserPost}
}
</script>

<style scoped>

</style>