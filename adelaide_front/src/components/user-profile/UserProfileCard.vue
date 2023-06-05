<template>
  <img src="src/assets/sample_image_48.png"/>
  <div class="user-page-friend-name">
    {{ localState.cardInfo.name }}
  </div>
</template>

<script setup>
import {reactive} from "vue";

const props = defineProps({
  cardInfo: null
})

const localState = reactive({
  cardInfo: props.cardInfo
})
</script>

<style scoped>
</style>