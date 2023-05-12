<template>
  <main class="root-container">
    <UserProfile/>
    <UserPosts/>
  </main>
</template>

<script>

import UserProfile from "../components/UserProfile.vue";
import UserPosts from "../components/UserPosts.vue";
export default {
  name: "UserPage",
  components: {UserPosts, UserProfile}
}
</script>

<style scoped>

</style>