<template>
  <div class="user-profile-friends-block general-block">
    <BlockHeader link="/">Friends</BlockHeader>
    <main>
      <UserFriend name="Name" surname="Surname"/>
      <UserFriend name="Name" surname="Surname"/>
      <UserFriend name="Name" surname="Surname"/>
      <UserFriend name="Name" surname="Surname"/>
    </main>
  </div>
</template>

<script>
import UserFriend from "@/components/user-profile/user-friends/UserFriend.vue";
import BlockHeader from "@/components/common/BlockHeader.vue";

export default {
  name: "UserProfileFriends",
  components: {BlockHeader, UserFriend}
}
</script>

<style scoped>
.user-profile-friends-block {
  display: flex;
  flex-direction: column;
}

main {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(4, 6vh);
  grid-gap: 1rem;
}
</style>