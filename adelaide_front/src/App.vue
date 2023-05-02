<template>
  <div class="root-container">
    <div class="main-block">
      <RouterView/>
    </div>
    <div class="side-block">
      <nav v-if="signedIn">
        <RouterLink :to="'/user/' + getSignedUsername">
          <NavButton button-text="My profile"/>
        </RouterLink>
        <NavButton button-text="Logout" @click="signOut"/>
      </nav>
      <nav v-else>
        <RouterLink to="/sign-in">
          <NavButton button-text="Sign in"/>
        </RouterLink>
        <RouterLink to="/sign-up">
          <NavButton button-text="Sign up"/>
        </RouterLink>
      </nav>
    </div>
  </div>
</template>

<script>
import {authStore} from "@/stores/authStore";
import NavButton from "@/components/NavButton.vue";

export default {
  name: "App",
  components: {NavButton},
  props: {},
  data() {
    return {
      auth: authStore(),
      username: null
    }
  },
  mounted() {
  },
  computed: {
    signedIn() {
      return this.auth.signedIn
    },
    getSignedUsername() {
      if (this.signedIn) {
        return this.auth.username
      }
    }
  },
  methods: {
    signOut() {
      this.auth.signOut()
      this.$router.push("/")
    }
  }
}
</script>

<style scoped>
.root-container {
  width: 70%;
  max-width: 70%;
  margin: 10px auto;
  display: flex;
  flex-direction: row;
}

nav a {
  color: black;
  text-decoration: none;
}

.main-block {
  flex: 75;
  margin-right: 5px;
}

.side-block {
  flex: 25;
  margin-left: 5px;
}

.main-block, .side-block {
  background: var(--background-block);
  padding: 10px;
  border-radius: var(--border-radius-general);
  box-shadow: 1px 1px 3px #c0c0c0;
}
</style>
