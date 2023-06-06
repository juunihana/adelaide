<template>
  <img src="src/assets/sample_image_48.png"/>
  <div>
    {{ cardInfo.name }}
  </div>
</template>

<script setup>
defineProps({
  cardInfo: null
})
</script>

<style scoped>
</style>