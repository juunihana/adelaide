<template>
  <div class="input-container">
    <label :for="id">{{ title }}</label>
    <input :type="password ? 'password' : 'text'" :id="id" :value="modelProp"
           @input="$emit('update:modelProp', $event.target.value)"/>
  </div>
</template>

<script>
export default {
  name: "SignFormLineInput",
  props: {
    title: null,
    modelProp: null,
    id: null,
    password: false
  }
}
</script>

<style scoped>
.input-container {
  display: flex;
  flex-direction: column;
}
.input-container label {
  padding: 1px 1px 1px 3px;
  border-radius: 5px 5px 0 0;
  background: #eaeaea;
  border: solid 1px #bababa;
  font-family: Ysabeau, Arial, sans-serif;
  font-size: 0.9rem;
  color: #222222;
}
.input-container input {
  padding: 7px;
  outline: none;
  border-radius: 0 0 5px 5px;
  background: #fafafa;
  border: solid 1px #bababa;
  border-top: none;
  font-family: Ysabeau, Arial, sans-serif;
  font-size: 1em;
  color: #222222;
}
</style>