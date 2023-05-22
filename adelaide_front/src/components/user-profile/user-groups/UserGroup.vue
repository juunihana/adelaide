<template>
  <div class="user-group">
    <div class="user-group-avatar">
      <img src="@/assets/photo_96.png" alt="avatar"/>
    </div>
    <div class="user-group-name">
      {{ name }}
    </div>
  </div>
</template>

<script>
export default {
  name: "UserGroup",
  props: {
    name: null
  }
}
</script>

<style scoped>
.user-group {
  display: flex;
  flex-direction: row;
  background: var(--background-root);
  border-radius: var(--border-radius);
  font-size: 1rem;
  color: var(--input-color);
  align-items: center;
}

.user-group-avatar {
}

img {
  object-fit: cover;
  max-height: 100%;
}

.user-group-name {
  margin: 0 0.5rem;
}
</style>