<template>
  <div class="user-profile-container">
    <UserProfileInfo/>
    <UserProfileFriends/>
    <UserProfileGroups/>
    <UserProfilePhotos/>
  </div>
</template>

<script>
import UserProfileInfo from "@/components/user-profile/UserProfileInfo.vue";
import UserProfileFriends from "@/components/user-profile/UserProfileFriends.vue";
import UserProfileGroups from "@/components/user-profile/UserProfileGroups.vue";
import UserProfilePhotos from "@/components/user-profile/UserProfilePhotos.vue";

export default {
  name: "UserProfile",
  components: {UserProfileInfo, UserProfileFriends, UserProfileGroups, UserProfilePhotos}
}
</script>

<style scoped>
.user-profile-container {
  display: flex;
  flex-direction: column;
  height: 1vh;
}
</style>