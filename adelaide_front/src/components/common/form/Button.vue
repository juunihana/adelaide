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
  /*margin-right: 1.2rem;*/
  background: var(--button-background);
  border-radius: var(--border-radius);
  border: none;
  font-size: 1.1rem;
  font-family: var(--font-family);
  color: var(--button-color);
  padding: 0.25rem 0.75rem 0.25rem 0.75rem;
}
button:hover {
  //color: var(--color-hover);
  //text-shadow: var(--text-shadow);
  cursor: pointer;
  background: var(--background-hover)
}
</style>