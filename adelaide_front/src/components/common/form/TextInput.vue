<template>
  <input type="text" :placeholder="placeholder">
</template>

<script setup>
defineProps({
  placeholder: null
})
</script>

<style scoped>
input {
  outline: none;
  border: none;
  border-radius: var(--border-radius);
  padding: 0.25rem 0.5rem 0.25rem 0.5rem;
  font-size: 1.1rem;
  font-family: var(--font-family);
  /*vertical-align: center;*/
  cursor: text;
  color: var(--input-color);
  background: var(--input-background);
}
</style>