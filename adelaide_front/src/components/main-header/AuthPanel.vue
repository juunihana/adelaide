<template>
  <div class="auth-panel">
    <div class="auth-panel-element">
      Sign in
    </div>
    <div class="auth-panel-element">
      Sign up
    </div>
  </div>
</template>

<script>
export default {
  name: "AuthPanel"
}
</script>

<style scoped>
.auth-panel {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-left: auto;
}

.auth-panel-element {
  margin-right: 1.5em;
}
</style>