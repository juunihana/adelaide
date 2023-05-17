<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" @click="clickOnModal">
      <form method="post">
        <TextInput placeholder="Username" v-model="username"/>
        <TextInput placeholder="Password" v-model="password"/>
        <Button @click="signIn">
          Sign in
        </Button>
      </form>
    </div>
  </div>
</template>

<script>
import {generalStore} from "@/stores/generalStore";
import TextInput from "./common/form/TextInput.vue";
import Button from "./common/form/Button.vue";

export default {
  name: "SignInOverlay",
  components: {Button, TextInput},
  props: {
  },
  computed: {
  },
  data() {
    return {
      generalStore: generalStore(),
      username: null,
      password: null
    }
  },
  methods: {
    signIn(e) {
      e.preventDefault()
      this.generalStore.signIn(this.username, this.password)
    },
    close() {
      this.generalStore.showSignInOverlay = false;
      this.generalStore.showSignUpOverlay = false;
    },
    clickOnModal(e) {
      e.stopPropagation()
    }
  }
}
</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(22, 22, 22, 0.9);
  display: flex;
}

.overlay-container {
  width: 25vw;
  height: 30vh;
  margin: auto;
  vertical-align: middle;
  background: #333333;
  border-radius: 3px;
}

form {
  display: flex;
  flex-direction: column;
}
</style>