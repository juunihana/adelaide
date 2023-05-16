<template>
  <div class="user-post-block general-block">
    <header class="block-header">
      <h3>{{ title }} by
        <router-link :to="'/'+author">{{ author }}</router-link>
      </h3>
    </header>
    {{ content }}
  </div>
</template>

<script>
export default {
  name: "UserPost",
  props: ["title", "content", "author"]
}
</script>

<style scoped>

</style>