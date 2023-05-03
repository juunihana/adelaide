<template>
  <div>
    <div class="sign-up-container">
      <form method="post" class="sign-up-form" v-on:submit="submitSignIn">
        <header>
          Sign in
        </header>
        <div class="errors" v-if="hasErrors">
          Following errors occurred during sign in:
          <ul>
            <li v-for="error in errors">{{ error }}</li>
          </ul>
        </div>
        <main>
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username"/>

          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.password"/>
        </main>
        <footer>
          <button type="submit" id="btnSignIn" :disabled="loading">
            Sign in
          </button>
        </footer>
      </form>
    </div>
  </div>
</template>

<script>
import {authStore} from "@/stores/authStore";

export default {
  name: "SignIn",
  props: {},
  data() {
    return {
      auth: authStore(),
      user: {
        username: null,
        password: null
      },
      loading: false,
      hasErrors: false,
      errors: []
    }
  },
  mounted() {

  },
  methods: {
    submitSignIn(e) {
      e.preventDefault()
      this.auth.signIn(this.user.username, this.user.password)
      this.$router.push("/user/" + this.auth.username)
    }
  }
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

footer button {
  margin-left: auto;
  padding: 10px 20px 10px 20px;
}

footer button:hover {
  cursor: pointer;
  background: #e0e0e0;
}
</style>