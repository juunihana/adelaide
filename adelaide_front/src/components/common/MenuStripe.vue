<template>
  <nav>
    <slot></slot>
  </nav>
</template>

<script>
export default {
  name: "MenuStripe"
}
</script>

<style scoped>
nav {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>