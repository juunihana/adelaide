<template>
  <main class="root-container">
    <UserProfile/>
    <UserPosts/>
  </main>
</template>

<script>
import UserProfile from "../components/UserProfile";
import UserPosts from "../components/UserPosts";

export default {
  name: "UserPage",
  components: {
    UserPosts,
    UserProfile
  }
}
</script>

<style scoped>

</style>