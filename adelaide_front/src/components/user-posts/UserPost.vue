<template>
  <div class="user-post-block general-block">
    <header class="block-header">
      <h3>{{ post.title }}</h3>
      by
      <router-link :to="'/'+post.author.username">{{ post.author.firstName }}
        {{ post.author.lastName }}
      </router-link>
    </header>
    {{ post.content }}
  </div>
</template>

<script>
export default {
  name: "UserPost",
  props: ["post"]
}
</script>

<style scoped>

</style>