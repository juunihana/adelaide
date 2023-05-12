<template>
  <header class="main-header">
    <SearchBar/>
    <MainMenu/>
    <AuthPanel/>
  </header>
</template>

<script>
import SearchBar from "./main-header/SearchBar.vue";
import MainMenu from "./main-header/MainMenu.vue";
import AuthPanel from "./main-header/AuthPanel.vue";
export default {
  name: "MainHeader",
  components: {AuthPanel, MainMenu, SearchBar}
}
</script>

<style scoped>
.main-header {
  position: fixed;
  top: 0;
  width: 100%;
  height: 2.5em;
  display: flex;
  flex-direction: row;
  align-items: center;
  background: #eaeaea;
  padding: 1em;
}
</style>