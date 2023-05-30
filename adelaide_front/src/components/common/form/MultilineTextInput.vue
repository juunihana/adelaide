<template>
  <textarea :value="modelValue" :placeholder="placeholder"
         @input="$emit('update:modelValue', $event.target.value)" rows="6">
  </textarea>
</template>

<script setup>
defineProps(['placeholder', 'modelValue'])
defineEmits(['update:modelValue'])
</script>

<style scoped>
textarea {
  outline: none;
  border: none;
  border-radius: var(--border-radius);
  padding: 0.25rem 0.5rem 0.25rem 0.5rem;
  font-size: 1.1rem;
  font-family: var(--font-family);
  cursor: text;
  color: var(--input-color);
  background: var(--input-background);
  resize: none;
}
</style>