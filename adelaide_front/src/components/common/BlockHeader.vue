<template>
  <header class="block-header"><h3>{{ caption }}</h3></header>
</template>

<script>
export default {
  name: "BlockHeader",
  props: {
    caption: null
  }
}
</script>

<style scoped>
.block-header {
  margin-bottom: 1em;
  border-bottom: solid 1px #222;
}
</style>