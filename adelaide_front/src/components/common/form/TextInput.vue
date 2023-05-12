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
  border-radius: 3px;
  padding: 0.1rem 0.3rem 0.1rem 0.3rem;
  font-size: 1.1rem;
  font-family: Ysabeau, Arial, sans-serif;
  vertical-align: center;
  cursor: text;
  color: var(--text-color-general);
  background: var(--background-color-root);
}
</style>