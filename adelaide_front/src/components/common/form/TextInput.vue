<template>
  <input type="text" :placeholder="placeholder">
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "TextInput",
  props: {
    placeholder: null
  }
})
</script>

<style scoped>
input {
  outline: none;
  border: none;
  border-radius: var(--border-radius);
  padding: 0.1rem 0.3rem 0.1rem 0.3rem;
  font-size: 1rem;
  font-family: var(--font-family);
  vertical-align: center;
  cursor: text;
  color: var(--input-color);
  background: var(--input-background);
}
</style>