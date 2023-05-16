<template>
    <header class="main-header">
        <SearchBar/>
        <MenuStripe>
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
            <MenuLink link="/settings">
                Settings
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
        <MenuStripe class="auth-panel" v-else>
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

export default {
    name: "MainHeader",
    components: {Button, MenuStripe, MenuLink, SearchBar},
    data() {
        return {
            generalStore: generalStore(),
            signedInUser: null
        }
    },
    mounted() {
        this.generalStore.signIn("alice", "password")
        const {signedIn} = storeToRefs(this.generalStore)
        watch(signedIn, () => {
            this.signedInUser = this.generalStore.signedIn
        })
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