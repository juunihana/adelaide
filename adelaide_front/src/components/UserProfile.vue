<template>
  <div class="user-profile-container">
    <UserProfileInfo/>
    <UserProfileFriends/>
    <UserProfileGroups/>
    <UserProfilePhotos/>
  </div>
</template>

<script>
import UserProfileInfo from "./user-profile/UserProfileInfo";
import UserProfileFriends from "./user-profile/UserProfileFriends";
import UserProfileGroups from "./user-profile/UserProfileGroups";
import UserProfilePhotos from "./user-profile/UserProfilePhotos";

export default {
  name: "UserProfile",
  components: {UserProfileInfo, UserProfileFriends, UserProfileGroups, UserProfilePhotos}
}
</script>

<style scoped>

</style>