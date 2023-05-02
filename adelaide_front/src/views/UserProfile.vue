<template>
  <div>
    <div class="user-profile-container">
      <div class="user-avatar">

      </div>
      <div class="user-info-block">
        <div class="user-name">
          {{ user.firstName }}
          {{ user.middleName ? user.middleName : "" }}
          {{ user.lastName }}
          {{ user.maidenSurname ? "(" + user.maidenSurname + ")" : "" }}
        </div>
        <div class="user-status" v-if="user.status">
          {{ user.status }}
        </div>
        <div class="user-age">
          {{ user.age }} years old
        </div>
        <div class="user-info" v-if="user.bio">
          {{ user.bio }}
        </div>
        <div class="user-place" v-if="user.place">
          From {{ user.place }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "../service/UserService"

export default {
  name: "UserProfile",
  props: {
    username: String
  },
  data() {
    return {
      user: {}
    }
  },
  mounted() {
    UserService.getUserProfile(this.$route.params.username)
    .then((response) => {
      this.user = response.data
    })
  },
  methods: {}
}
</script>

<style scoped>
.user-profile-container {
  display: flex;
  flex-direction: row;
}

.user-avatar {
  flex: 1;
}

.user-avatar img {
  border-radius: var(--border-radius-general);
}

.user-info-block {
  flex: 4;

  display: flex;
  flex-direction: column;
}

.user-name {
  font-size: 1.5rem;
  color: var(--text-color-general);
}

.user-profile-container {
  font-family: var(--text-font-general);
  font-size: 1.2rem;
  color: var(--text-color-general);
}
</style>