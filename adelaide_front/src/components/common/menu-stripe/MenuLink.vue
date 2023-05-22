<template>
  <div class="main-menu-element">
    <router-link :to="link"><slot></slot></router-link>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "MenuLink",
  props: {
    caption: null,
    link: null
  }
})
</script>

<style scoped>
.main-menu-element {
  /*margin-right: 1.2rem;*/
}
</style>