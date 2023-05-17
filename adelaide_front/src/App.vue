<template>
  <MainHeader/>
  <SignInOverlay v-if="overlayShown"/>
  <main class="user-page-container">
    <router-view />
  </main>
</template>

<script>
import MainHeader from "@/components/MainHeader.vue";
import UserPage from "@/views/UserPage.vue";
import UserPosts from "@/components/UserPosts.vue";
import {generalStore} from "@/stores/generalStore";
import {watch} from "vue";
import {storeToRefs} from "pinia";
import MenuStripe from "@/components/common/MenuStripe.vue";
import Button from "@/components/common/form/Button.vue";
import SignInOverlay from "./components/SignInOverlay.vue";

export default {
  name: "App",
  components: {SignInOverlay, Button, MenuStripe, UserPosts, UserPage, MainHeader},
  props: {},
  data() {
    return {
      generalStore: generalStore(),
      overlayShown: false
    }
  },
  mounted() {
    const {showSignInOverlay, showSignUpOverlay} = storeToRefs(this.generalStore)
    watch(showSignInOverlay, () => {
      this.overlayShown = !!this.generalStore.showSignInOverlay;
    })
    watch(showSignUpOverlay, () => {
      this.overlayShown = !!this.generalStore.showSignUpOverlay;
    })
  },
  computed: {
    overlayType() {
      if (this.generalStore.showSignInOverlay) {
        return "signIn"
      }
      if (this.generalStore.showSignUpOverlay) {
        return "media"
      }
    }
  },
  methods: {
    closeSignIn() {
      if (this.generalStore.showSignInOverlay)
        this.generalStore.showSignInOverlay = false
      if (this.generalStore.showSignUpOverlay)
        this.generalStore.showSignUpOverlay = false
    }
  }
}
</script>

<style scoped>
.user-page-container {
  width: 95%;
  max-width: 95%;
  margin: 4em auto;
  overflow: hidden;
}

.sign-in-menu {
  margin-top: 1rem;
}

.sign-in-menu button {
  margin-left: auto;
}
</style>
