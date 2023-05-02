<template>
  <button type="submit" :id="id" :disabled="loading">
    {{ title }}
  </button>
</template>

<script>
export default {
  name: "SignFormButton",
  props: {
    title: null,
    id: null,
    loading: false
  }
}
</script>

<style scoped>
button {
  margin-left: auto;
  padding: var(--padding-button-general);
  outline: none;
  border-radius: 5px;
  background: var(--background-button);
  border: var(--border-general);
  font-family: var(--text-font-general);
  font-size: 1.2rem;
  color: var(--text-color-general);
}

button:hover {
  cursor: pointer;
  background: var(--background-button-hover);
  //text-shadow: 0 0 2px #666666;
}
</style>