<template>
  <header class="main-header">
    <MenuLink link="/">
      Adelaide Social Network
    </MenuLink>
    <SearchBar/>
    <MenuStripe v-if="state.signedInUser">
      <MenuLink :link="'/' + state.signedInUser">
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
    <MenuStripe class="auth-panel" v-if="!state.signedInUser">
      <Button @click.prevent="signIn">
        Sign in
      </Button>
      <Button @click.prevent="showSignUp">
        Sign up
      </Button>
    </MenuStripe>
    <MenuStripe class="auth-panel" v-else @mouseover="state.menuColumnShown = true"
                @mouseleave="state.menuColumnShown = false">
      <MenuLink link="/settings"
                :class="{'user-menu-element-active': state.menuColumnShown, 'user-menu-element': !state.menuColumnShown}">
        Settings
      </MenuLink>
      <Button @click.prevent="signOut"
              :class="{'user-menu-element-active': state.menuColumnShown, 'user-menu-element': !state.menuColumnShown}">
        Sign out
      </Button>
      <Button>
        {{ signedInUser }}
      </Button>
    </MenuStripe>
  </header>
</template>

<script setup>
import SearchBar from "@/components/main-header/SearchBar.vue";
import MenuStripe from "@/components/common/MenuStripe.vue";
import MenuLink from "@/components/common/menu-stripe/MenuLink.vue";
import Button from "@/components/common/form/Button.vue";
import {reactive, watch} from "vue";
import {generalStore} from "../stores/generalStore.js";
import {storeToRefs} from "pinia"

const generalStorage = generalStore()
const {signedIn} = storeToRefs(generalStore())

let state = reactive({
  menuColumnShown: false,
  signedInUser: null
})

watch(() => signedIn,
    () => {
      state.signedInUser = generalStorage.signedIn
      console.log("here")
    })

function signIn() {
  generalStorage.signIn("username", "password")
}

function signOut() {
  generalStorage.signOut()
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