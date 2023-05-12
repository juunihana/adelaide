<template>
  <div class="user-friend">
    <div class="user-friend-avatar">
      <img src="@/assets/photo_96.png" alt="avatar"/>
    </div>
    <div class="user-friend-name">
      {{ name }} {{ surname }}
    </div>
  </div>
</template>

<script>
export default {
  name: "UserFriend",
  props: {
    name: null,
    surname: null
  }
}
</script>

<style scoped>
.user-friend {
  display: flex;
  flex-direction: row;
  background: #444444;
  border-radius: 3px;
  font-size: 1.3rem;
}

.user-friend-avatar {
}

img {
  object-fit: cover;
  max-height: 100%;
}

.user-friend-name {
  margin: 0 0.5rem;
}
</style>