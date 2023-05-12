<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" :style="dialogSize" @click="clickOnModal">
      <slot></slot>
    </div>
  </div>
</template>

<script>
import {generalStore} from "@/stores/generalStore";

export default {
  name: "Overlay",
  props: {
    type: null
  },
  computed: {
    dialogSize() {
      switch (this.type) {
        case "signIn":
          return "width: 25vw; height: 30vh;"
        case "signUp":
          return "width: 25vw; height: 90vh;"
        case "media":
          return "width: 90vw; height: 90vh;"
      }
    }
  },
  data() {
    return {
      generalStore: generalStore()
    }
  },
  methods: {
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
</style>