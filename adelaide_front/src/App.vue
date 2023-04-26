<template>
  <div class="root-container">
    <div class="main-block">
      <UserProfile/>
    </div>
    <div class="side-block">

    </div>
  </div>
</template>

<script setup>

import UserProfile from "@/views/UserProfile.vue";
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
</style>
