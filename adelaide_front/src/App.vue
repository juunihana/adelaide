<template>
  <div class="root-container">
    <div class="main-block">
      <UserProfile username="alice"/>
    </div>
    <div class="side-block">
      <div class="side-block-button">
        Sign in
      </div>
      <div class="side-block-button">
        Sign up
      </div>
    </div>
  </div>
</template>

<script setup>
import UserProfile from "@/views/UserProfile.vue"
</script>

<style scoped>
.root-container {
  width: 80%;
  max-width: 80%;
  margin: 10px auto;
  display: flex;
  flex-direction: row;
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
  background: #f0f0f0;
  padding: 10px;
  border-radius: 5px;
}

.side-block-button {
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-family: Verdana, Arial, sans-serif;
  font-size: 1rem;
}
.side-block-button:hover {
  background: #e0e0e0;
}
</style>
