<template>
  <MainHeader/>
  <Overlay v-if="false" />
  <UserPage/>
</template>

<script>
import MainHeader from "@/components/MainHeader";
import Overlay from "./components/Overlay";
import UserPage from "./views/UserPage";
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
