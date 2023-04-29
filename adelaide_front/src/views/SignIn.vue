<template>
  <div>
    <div class="sign-up-container">
      <form method="post" class="sign-up-form" v-on:submit="submitSignUp">
        <header>
          Sign up
        </header>
        <div class="errors" v-if="hasErrors">
          Following errors occurred during sign up:
          <ul>
            <li v-for="error in errors">{{ error }}</li>
          </ul>
        </div>
        <main>
          <label for="username">Username</label>
          <input type="text" id="username" v-model="username"/>

          <label for="email">Email</label>
          <input type="text" id="email" v-model="email"/>
        </main>
        <footer>
          <button type="submit" id="btnSignUp" :disabled="loading">
            Sign up
          </button>
        </footer>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "SignIn",
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
.sign-in-container {
  width: 80%;
  max-width: 80%;
  margin: auto;
  display: flex;
  flex-direction: column;
}

header {
  padding: 10px;
  font-family: Ysabeau, "Courier New", monospace;
  font-size: 1.5rem;
  color: #555555;
  text-align: center;
  margin-bottom: 10px;
  background: #fafafa;
  border-radius: 5px;
}

main {
  display: grid;
  grid-template-columns: 2fr 8fr;
  row-gap: 10px;
}

.errors, .success {
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
  font-family: Ysabeau, Arial, sans-serif;
  font-size: 1.1rem;
}

.errors {
  background: #ffd1d1;
  border: solid 1px #ff9898;
  color: #730000;
}

.success {
  background: #e7ffd1;
  border: solid 1px #a3d772;
  color: #527300;
}

label {
  padding: 10px 10px 10px 0;
  font-family: Ysabeau, Arial, sans-serif;
  font-size: 1.1rem;
  color: #555555;
}

footer {
  flex: 1;
  margin-top: 10px;
}

input, footer button {
  padding: 10px;
  outline: none;
  border-radius: 5px;
  background: #fafafa;
  border: solid 1px #999999;
  font-family: Ysabeau, Arial, sans-serif;
  font-size: 1.2rem;
  color: #555555;
}

#agreement {
  margin-right: 10px;
}

footer button {
  margin-left: auto;
  padding: 10px 20px 10px 20px;
}

footer button:hover {
  cursor: pointer;
  background: #e0e0e0;
}
</style>