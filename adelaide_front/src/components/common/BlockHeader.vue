<template>
  <header class="block-header">
    <h3>
      <router-link :to="link">
        <slot></slot>
      </router-link>
    </h3>
  </header>
</template>

<script>
export default {
  name: "BlockHeader",
  props: {
    link: null
  }
}
</script>

<style scoped>
.block-header {
  /*border-bottom: var(--border);*/
}
</style>