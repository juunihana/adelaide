<template>
  <div class="search-bar">
    <TextInput class="search-bar-input" placeholder="Search"/>
    <Button class="search-bar-button">Search</Button>
  </div>
</template>

<script>
import TextInput from "@/components/common/form/TextInput.vue";
import Button from "../common/form/Button.vue";

export default {
  name: "SearchBar",
  components: {Button, TextInput}
}
</script>

<style scoped>
.search-bar {
  display: flex;
  flex-direction: row;
}

.search-bar-input {
  border-radius: var(--border-radius) 0 0 var(--border-radius);
  border-right: var(--border);
}

.search-bar-button {
  border-radius: 0 var(--border-radius) var(--border-radius) 0;
}
</style>