<template>
  <div>
    <BlockHeader link="/">Friends</BlockHeader>
    <main>
      <UserFriend name="Name" surname="Surname"/>
      <UserFriend name="Name" surname="Surname"/>
      <UserFriend name="Name" surname="Surname"/>
    </main>
  </div>
</template>

<script setup>
import UserFriend from "./user-friends/UserFriend.vue";
import BlockHeader from "../common/BlockHeader.vue";
</script>

<style scoped>
main {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3, 4vh);
  grid-gap: 1rem;
}
</style>