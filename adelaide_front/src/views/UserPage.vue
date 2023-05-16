<template>
  <main class="root-container">
    <UserProfile/>
    <UserPosts/>
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
.root-container {
  display: grid;
  grid-column-gap: 2em;
  grid-template-columns: 1fr 3fr;
}
</style>