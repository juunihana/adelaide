<template>
  <div class="user-profile-info-block general-block">
    <div class="user-profile-avatar">
      <img src="@/assets/avatar_200.png" alt="user avatar"/>
    </div>
    <div class="user-profile-name">
      <h1>Name Surname</h1>
    </div>
    <div class="user-profile-age">
      <h2>25 years</h2>
    </div>
    <div class="user-profile-place">
      <h3>Moscow</h3>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserProfileInfo"
}
</script>

<style scoped>
.user-profile-info-block {
  display: flex;
  flex-direction: column;
}

.user-profile-avatar {
  text-align: center;
}

.user-profile-name {
}

.user-profile-age {
}

.user-profile-place {
}
</style>