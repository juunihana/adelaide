<template>
  <MainHeader/>
  <Overlay v-if="false" />
  <UserPage/>
</template>

<script>
import MainHeader from "./components/MainHeader.vue";
import Overlay from "./components/Overlay.vue";
import UserPage from "./views/UserPage.vue";
export default {
  name: "App",
  components: {UserPage, Overlay, MainHeader},
  props: {},
  data() {
  },
  mounted() {

  },
  computed: {

  },
  methods: {

  }
}
</script>

<style scoped>

</style>
