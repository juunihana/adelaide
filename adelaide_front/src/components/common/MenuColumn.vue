<template>
  <nav class="general-block">
    <slot></slot>
  </nav>
</template>

<script>
export default {
  name: "MenuColumn"
}
</script>

<style scoped>
nav {
  display: flex;
  flex-direction: column;
  align-items: normal;
  padding: 1rem 2rem 1rem 1rem
}
</style>