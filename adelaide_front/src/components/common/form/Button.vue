<template>
  <button class="align-right">{{loading ? 'Loading...' : caption}}</button>
</template>

<script setup>
defineProps(['caption', 'loading'])
</script>

<style scoped>
</style>