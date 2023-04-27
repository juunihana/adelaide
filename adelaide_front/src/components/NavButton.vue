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
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-family: Verdana, Arial, sans-serif;
  font-size: 1rem;
}
.side-block-button:hover {
  background: #e0e0e0;
}
</style>