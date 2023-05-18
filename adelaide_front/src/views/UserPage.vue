<template>
  <main class="user-page-container">
    <UserProfile/>
<!--    <UserPosts/>-->
  </main>
</template>

<script>
import UserProfile from "@/components/UserProfile.vue"
import UserPosts from "@/components/UserPosts.vue"

export default {
  name: "UserPage",
  components: {
    UserPosts,
    UserProfile
  },
  data() {
    return {
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.user-page-container {
  display: grid;
  grid-column-gap: 2rem;
  grid-template-columns: 1fr 3fr;
}
</style>