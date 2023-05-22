<template>
  <div v-if="false">Loading</div>
  <div v-if="false">Error</div>
  <div v-else class="user-profile-side-block general-block">
    <slot></slot>
  </div>
</template>

<script setup>
</script>

<style scoped>
.user-profile-side-block {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 0.75rem;
}
</style>