<template>
  <div class="overlay" @click="close" @wheel.prevent @touchmove.prevent @scroll.prevent>
    <div class="overlay-container" @click.stop>
      <form method="post">
        <div class="label">
          Sign in
        </div>
        <TextInput placeholder="Username" v-model="username"/>
        <TextInput placeholder="Password" v-model="password"/>
        <Button @click.prevent="signIn">
          Sign in
        </Button>
      </form>
    </div>
  </div>
</template>

<script setup>
import TextInput from "./common/form/TextInput.vue";
import Button from "./common/form/Button.vue";
</script>

<style scoped>
.overlay-container {
  width: 25vw;
  height: 25vh;
  align-items: center;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  /*width: 10vw;*/
  margin: auto;
  align-items: center;
}

.label {
  /*text-align: center;*/
}
</style>