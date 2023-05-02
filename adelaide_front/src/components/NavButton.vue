<template>
  <div class="side-block-button">{{ buttonText }}</div>
</template>

<script>
export default {
  name: "NavButton",
  props: {
    buttonText: ""
  }
}
</script>

<style scoped>
.side-block-button {
  margin-bottom: 5px;
  padding: var(--padding-button-general);
  border-radius: var(--border-radius-general);
  border: var(--border-general);
  cursor: pointer;
  font-family: var(--text-font-general);
  font-size: 1.15em;
  color: var(--text-color-general);
  background: var(--background-button);
}
.side-block-button:hover {
  background: var(--background-button-hover);
  text-shadow: 0 0 2px #666666;
}
</style>