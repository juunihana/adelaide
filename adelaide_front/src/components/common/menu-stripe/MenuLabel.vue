<template>
  <div class="main-menu-element">
    <slot></slot>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "MenuLabel"
})
</script>

<style scoped>
.main-menu-element {
  /*margin-right: 1.2rem;*/
}
</style>