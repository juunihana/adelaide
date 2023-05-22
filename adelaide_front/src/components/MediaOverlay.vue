<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" @click.stop>

    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.overlay-container {
  width: 90vw;
}

form {
  display: flex;
  flex-direction: column;
}
</style>