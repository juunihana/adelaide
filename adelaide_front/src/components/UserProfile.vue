<template>
  <div class="user-profile-container">
    <UserProfileInfo/>
    <UserProfileFriends/>
    <UserProfileGroups/>
    <UserProfilePhotos/>
  </div>
</template>

<script>

import UserProfileInfo from "./user-profile/UserProfileInfo.vue";
import UserProfileFriends from "./user-profile/UserProfileFriends.vue";
import UserProfileGroups from "./user-profile/UserProfileGroups.vue";
import UserProfilePhotos from "./user-profile/UserProfilePhotos.vue";
export default {
  name: "UserProfile",
  components: {UserProfilePhotos, UserProfileGroups, UserProfileFriends, UserProfileInfo}
}
</script>

<style scoped>

</style>