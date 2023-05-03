<template>
  <div>
    <div v-if="authorized">
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
      <div class="user-profile-container" v-for="post in user.posts">
        {{ post.title }}
        {{ post.content }}
      </div>
    </div>
    <InfoFrameStatic type="error" v-else>
      Access denied
    </InfoFrameStatic>
  </div>
</template>

<script>
import UserService from "@/service/UserService.js"
import InfoFrameStatic from "@/components/InfoFrameStatic.vue";

export default {
  name: "UserProfile",
  components: {InfoFrameStatic},
  props: {
    username: String
  },
  data() {
    return {
      user: {},
      authorized: false
    }
  },
  mounted() {
    UserService.getUserProfile(this.$route.params.username)
    .then((data) => {
          this.user = data.data
          this.authorized = true
        },
        (error) => {
          this.authorized = false
        })
  },
  computed: {},
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