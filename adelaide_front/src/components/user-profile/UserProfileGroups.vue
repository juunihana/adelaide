<template>
  <div>
    <BlockHeader link="/">Groups</BlockHeader>
    <main>
      <UserGroup name="Group"/>
      <UserGroup name="Group"/>
      <UserGroup name="Group"/>
    </main>
  </div>
</template>

<script>
import BlockHeader from "@/components/common/BlockHeader.vue";
import UserGroup from "./user-groups/UserGroup.vue";

export default {
  name: "UserProfileGroups",
  components: {UserGroup, BlockHeader}
}
</script>

<style scoped>
.user-profile-groups-block {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

main {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3, 4vh);
  grid-gap: 1rem;
}
</style>