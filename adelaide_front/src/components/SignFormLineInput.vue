<template>
  <div>
    <input type="text"/>
  </div>
</template>

<script>
export default {
  name: "SignFormLineInput",
  props: {
    title: ""
  }
}
</script>

<style scoped>
div {
}
input {
  padding: 10px;
  outline: none;
  border-radius: 5px;
  background: #fafafa;
  border: solid 1px #999999;
  font-family: Verdana, Arial, sans-serif;
  font-size: 1rem;
  color: #111111;
}
</style>