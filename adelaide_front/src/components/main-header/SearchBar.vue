<template>
  <div class="search-bar">
    <TextInput placeholder="Search"/>
  </div>
</template>

<script>
import TextInput from "@/components/common/form/TextInput.vue";

export default {
  name: "SearchBar",
  components: {TextInput}
}
</script>

<style scoped>
.search-bar {
  margin-right: 1.5em;
}
</style>