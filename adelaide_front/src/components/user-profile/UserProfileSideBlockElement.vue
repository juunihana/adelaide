<template>
  <div class="side-block-element">
    <div class="side-block-image">
      <slot name="image"></slot>
    </div>
    <div class="side-block-text">
      <slot name="text"></slot>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.side-block-element {
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
  background: var(--background-root);
  border-radius: var(--border-radius);
  align-items: center;
  /*height: 5vh;*/
}

.side-block-image {
  flex: 1
}

.side-block-image img {
  object-fit: cover;
  max-height: 100%;
}

.side-block-text {
  flex: 6;
  font-size: 1rem;
  color: var(--input-color);
}
</style>