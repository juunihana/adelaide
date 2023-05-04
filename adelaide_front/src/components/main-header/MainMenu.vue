<template>
  <nav>
    <div class="main-menu-element">
      My profile
    </div>
    <div class="main-menu-element">
      Friends
    </div>
    <div class="main-menu-element">
      Groups
    </div>
    <div class="main-menu-element">
      Photos
    </div>
    <div class="main-menu-element">
      Music
    </div>
    <div class="main-menu-element">
      Videos
    </div>
    <div class="main-menu-element">
      Settings
    </div>
  </nav>
</template>

<script>
export default {
  name: "MainMenu"
}
</script>

<style scoped>
nav {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.main-menu-element {
  margin-right: 1.5em;
}
</style>