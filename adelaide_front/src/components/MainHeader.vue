<template>
  <header class="main-header">
    <SearchBar/>
    <MenuStripe>
      <MenuLink link="/">
        My profile
      </MenuLink>
      <MenuLink link="/">
        Messages
      </MenuLink>
      <MenuLink link="/">
        Feed
      </MenuLink>
      <MenuLink link="/">
        Friends
      </MenuLink>
      <MenuLink link="/">
        Groups
      </MenuLink>
      <MenuLink link="/">
        Photos
      </MenuLink>
      <MenuLink link="/">
        Music
      </MenuLink>
      <MenuLink link="/">
        Videos
      </MenuLink>
      <MenuLink link="/">
        Settings
      </MenuLink>
    </MenuStripe>
    <MenuStripe class="auth-panel" v-if="!signedIdUser">
      <Button @click="signIn">
        Sign in
      </Button>
      <Button @click="showSignUp">
        Sign up
      </Button>
    </MenuStripe>
    <MenuStripe class="auth-panel" v-else>
      <MenuLink link="/">
        {{ signedIdUser.username }}
      </MenuLink>
    </MenuStripe>
  </header>
</template>

<script>
import SearchBar from "@/components/main-header/SearchBar.vue";
import MenuStripe from "@/components/common/MenuStripe.vue";
import MenuLink from "@/components/common/menu-stripe/MenuLink.vue";
import Button from "@/components/common/form/Button.vue";
import {generalStore} from "@/stores/generalStore.js";

export default {
  name: "MainHeader",
  components: {Button, MenuStripe, MenuLink, SearchBar},
  data() {
    return {
      generalStore: generalStore(),
      signedIdUser: null
    }
  },
  mounted() {
    this.signedIdUser = this.generalStore.signedIn
  },
  methods: {
    signIn(e) {
      e.preventDefault()
      this.generalStore.signIn("alice", "password")
      // this.generalStore.showSignInOverlay = true
    },
    showSignUp(e) {
      e.preventDefault()
      // this.generalStore.showSignUpOverlay = true
    }
  }
}
</script>

<style scoped>
.main-header {
  position: fixed;
  top: 0;
  width: 100%;
  height: 2.5rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  background: var(--background-color-block);
  padding: 1rem;
  box-shadow: 0 0 10px #111111;
}
.auth-panel {
  margin-left: auto;
}
</style>