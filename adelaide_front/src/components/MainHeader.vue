<template>
  <header class="main-header">
    <SearchBar/>
    <MainMenu/>
    <AuthPanel/>
  </header>
</template>

<script>
import SearchBar from "./main-header/SearchBar";
import MainMenu from "./main-header/MainMenu";
import AuthPanel from "./main-header/AuthPanel";
export default {
  name: "MainHeader",
  components: {AuthPanel, MainMenu, SearchBar}
}
</script>

<style scoped>

</style>