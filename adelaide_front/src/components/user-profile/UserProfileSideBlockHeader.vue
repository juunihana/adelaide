<template>
  <div class="side-block-element">
    <div class="side-block-text">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.side-block-element {
  border-radius: var(--border-radius);
  align-items: center;
  height: 5vh;
  display: flex;
}

.side-block-text {
  font-family: var(--font-family);
  font-size: 1.2rem;
  color: var(--color);
}
</style>