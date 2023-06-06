<template>
  <input :type="isPassword ? 'password' : 'text'" :placeholder="placeholder" :value="modelValue"
         @input="$emit('update:modelValue', $event.target.value)">
</template>

<script setup>
defineProps(['placeholder', 'modelValue', 'isPassword'])
defineEmits(['update:modelValue'])
</script>

<style scoped>
/*input {*/
/*  outline: none;*/
/*  border: none;*/
/*  border-radius: var(--border-radius);*/
/*  padding: 0.25rem 0.5rem 0.25rem 0.5rem;*/
/*  font-size: 1.1rem;*/
/*  font-family: var(--font-family);*/
/*  cursor: text;*/
/*  color: var(--input-color);*/
/*  background: var(--input-background);*/
/*}*/
</style>