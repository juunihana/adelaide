<template>
  <button>
    <slot></slot>
  </button>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "Button"
})
</script>

<style scoped>
button {
  margin-right: 1.2rem;
  background: #444444;
  border-radius: 3px;
  border: none;
  font-size: 1.2rem;
  font-family: Ysabeau, Arial, sans-serif;
  color: var(--text-color-general);
  padding: 0.1rem 0.5rem 0.1rem 0.5rem;
}
button:hover {
  color: var(--hover-color);
  text-shadow: 0 0 2px var(--hover-color);
  cursor: pointer;
}
</style>