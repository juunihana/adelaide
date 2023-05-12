<template>
  <div class="user-profile-groups-block general-block">
    <BlockHeader link="/">Groups</BlockHeader>
    <main>
      <div class="user-profile-group">
        group
      </div>
      <div class="user-profile-group">
        group
      </div>
      <div class="user-profile-group">
        group
      </div>
    </main>
  </div>
</template>

<script>
import BlockHeader from "@/components/common/BlockHeader.vue";

export default {
  name: "UserProfileGroups",
  components: {BlockHeader}
}
</script>

<style scoped>
.user-profile-groups-block {
  display: flex;
  flex-direction: column;
}
</style>