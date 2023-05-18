<template>
  <header class="main-header">
    <MenuLink link="/">
      Adelaide Social Network
    </MenuLink>
    <SearchBar/>
    <MenuStripe v-if="signedInUser">
      <MenuLink :link="'/' + signedInUser">
        My profile
      </MenuLink>
      <MenuLink link="/messages">
        Messages
      </MenuLink>
      <MenuLink link="/feed">
        Feed
      </MenuLink>
      <MenuLink link="/friends">
        Friends
      </MenuLink>
      <MenuLink link="/groups">
        Groups
      </MenuLink>
      <MenuLink link="/photos">
        Photos
      </MenuLink>
      <MenuLink link="/music">
        Music
      </MenuLink>
      <MenuLink link="/videos">
        Videos
      </MenuLink>
    </MenuStripe>
    <MenuStripe class="auth-panel" v-if="!signedInUser">
      <Button @click="signIn">
        Sign in
      </Button>
      <Button @click="showSignUp">
        Sign up
      </Button>
    </MenuStripe>
    <MenuStripe class="auth-panel" v-else @mouseover="menuColumnShown = true"
                @mouseleave="menuColumnShown = false">
      <MenuLink link="/settings"
                :class="{'user-menu-element-active': menuColumnShown, 'user-menu-element': !menuColumnShown}">
        Settings
      </MenuLink>
      <Button @click="signOut"
          :class="{'user-menu-element-active': menuColumnShown, 'user-menu-element': !menuColumnShown}">
        Sign out
      </Button>
      <Button>
        {{ signedInUser }}
      </Button>
    </MenuStripe>
  </header>
</template>

<script>
import SearchBar from "@/components/main-header/SearchBar.vue";
import MenuStripe from "@/components/common/MenuStripe.vue";
import MenuLink from "@/components/common/menu-stripe/MenuLink.vue";
import Button from "@/components/common/form/Button.vue";
import {generalStore} from "@/stores/generalStore.js";
import {storeToRefs} from "pinia";
import {watch} from "vue";
import MenuColumn from "./common/MenuColumn.vue";
import MenuLabel from "./common/menu-stripe/MenuLabel.vue";

export default {
  name: "MainHeader",
  components: {MenuLabel, MenuColumn, Button, MenuStripe, MenuLink, SearchBar},
  data() {
    return {
      generalStore: generalStore(),
      signedInUser: null,
      menuColumnShown: false
    }
  },
  mounted() {
    const {signedIn} = storeToRefs(this.generalStore)
    watch(signedIn, () => {
      console.log(this.signedInUser)
      this.signedInUser = this.generalStore.signedIn
    })
  },
  methods: {
    signIn(e) {
      e.preventDefault()
      this.generalStore.signIn("username", "password")
      // this.generalStore.showSignInOverlay = true
    },
    signOut(e) {
      e.preventDefault()
      this.generalStore.signOut()
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
  background: var(--background-block);
  padding: 1rem;
  box-shadow: 0 0 10px #111111;
}

.user-menu-element {
  display: none;
}

.user-menu-element-active {
  display: block;
  transition: width 2s;
}

.auth-panel {
  margin-left: auto;
}
</style>