<template>
  <div class="root-container">
    <div class="main-block">
      <RouterView/>
    </div>
    <div class="side-block">
      <nav>
        <RouterLink to="/sign-in">
          <NavButton button-text="Sign in"/>
        </RouterLink>
        <RouterLink to="/sign-up">
          <NavButton button-text="Sign up"/>
        </RouterLink>
      </nav>
    </div>
  </div>
</template>

<script>
import NavButton from "@/components/NavButton.vue";

export default {
  name: "App",
  components: {NavButton},
  props: {},
  data() {
    return {}
  },
  mounted() {

  },
  methods: {}
}
</script>

<style scoped>
.root-container {
  width: 70%;
  max-width: 70%;
  margin: 10px auto;
  display: flex;
  flex-direction: row;
}

nav a {
  color: black;
  text-decoration: none;
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
