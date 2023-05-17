<template>
  <div v-if="loading">Loading</div>
  <div v-if="error">Error</div>
  <div v-if="userProfile" class="user-profile-info-block general-block">
    <div class="user-profile-avatar">
      <img :src="userProfile.avatarLink" alt="user avatar"/>
    </div>
    <div class="user-profile-name">
      <h1>{{ userProfile.firstName }} {{ userProfile.lastName }}</h1>
    </div>
    <div class="user-profile-age">
      <h2>{{ userProfile.age }} years</h2>
    </div>
    <div class="user-profile-place">
      <h3>{{ userProfile.place }}</h3>
    </div>
  </div>
</template>

<script>
import {generalStore} from "../../stores/generalStore.js";
import UserService from "@/service/UserService";

export default {
  name: "UserProfileInfo",
  data() {
    return {
      generalStore: generalStore(),
      userProfile: null,
      loading: false,
      error: false
    }
  },
  created() {
    this.$watch(
        () => this.$route.params,
        () => this.getUserProfile(),
        {immediate: true})
  },
  methods: {
    getUserProfile() {
      this.loading = true
      UserService.getUserProfile(this.$route.params.username)
      .then((data) => {
        this.loading = false
        this.userProfile = data.data
      })
      .catch((error) => {
        this.loading = false;
        this.error = true;
      })
    }
  }
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