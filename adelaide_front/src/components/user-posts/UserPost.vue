<template>
  <div class="user-post-block general-block">
    <header class="block-header"><h3>{{ title }}</h3></header>
    {{ content }}
  </div>
</template>

<script>
export default {
  name: "UserPost",
  props: ["title", "content"]
}
</script>

<style scoped>

</style>