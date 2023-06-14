<template>
  <button class="bg-hover">{{loading ? 'Loading...' : caption}}</button>
</template>

<script setup>
defineProps(['caption', 'loading'])
</script>

<style scoped>
</style>