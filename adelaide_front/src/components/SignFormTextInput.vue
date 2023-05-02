<template>
  <div class="input-container">
    <label :for="id">{{ title }}</label>
    <textarea :id="id" :value="modelProp"
           @input="$emit('update:modelProp', $event.target.value)">
    </textarea>
  </div>
</template>

<script>
export default {
  name: "SignFormTextInput",
  props: {
    title: null,
    modelProp: null,
    id: null
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
  border-radius: var(--border-radius-general) var(--border-radius-general) 0 0;
  background: var(--background-form-label);
  border: var(--border-general);
  font-family: var(--text-font-general);
  font-size: var(--text-size-form-label);
  color: var(--text-color-general);
}
.input-container textarea {
  height: 10em;
  padding: 7px;
  outline: none;
  resize: none;
  border-radius: 0 0 var(--border-radius-general) var(--border-radius-general);
  background: var(--background-form-input);
  border: var(--border-general);
  border-top: none;
  font-family: var(--text-font-general);
  font-size: var(--text-size-form-input);
  color: var(--text-color-general);
}
</style>