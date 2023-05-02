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
  padding: 10px;
  border-radius: 5px;
  border: solid 1px #bababa;
  cursor: pointer;
  font-family: Ysabeau, Arial, sans-serif;
  font-size: 1.2rem;
  color: #222222;
  background: #fafafa;
}
.side-block-button:hover {
  background: #eaeaea;
  text-shadow: 0 0 2px #666666;
}
</style>