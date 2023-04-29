<template>
  <div>
    <div class="sign-up-container">
      <form method="post" class="sign-up-form" v-on:submit="signUp">
        <header>
          Sign up
        </header>
        <div class="success" v-if="success">
          You have successfully signed up!<br/>
          Welcome, {{ username }}! You can use your credentials to sign in now
        </div>
        <div class="errors" v-if="hasErrors">
          Following errors occurred during sign up:
          <ul>
            <li v-for="error in errors">{{ error }}</li>
          </ul>
        </div>
        <main>
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username"/>

          <label for="email">Email</label>
          <input type="text" id="email" v-model="user.email"/>

          <label for="phone">Phone</label>
          <input type="text" id="phone" v-model="user.phone"/>

          <label for="passwordFirst">Password</label>
          <input type="password" id="passwordFirst" v-model="user.passwordFirst"/>

          <label for="passwordSecond">Password again</label>
          <input type="password" id="passwordSecond" v-model="user.passwordSecond"/>

          <label for="firstName">First name</label>
          <input type="text" id="firstName" v-model="user.firstName"/>

          <label for="middleName">Middle name</label>
          <input type="text" id="middleName" v-model="user.middleName"/>

          <label for="lastName">Last name</label>
          <input type="text" id="lastName" v-model="user.lastName"/>

          <label for="maidenSurname">Maiden surname</label>
          <input type="text" id="maidenSurname" v-model="user.maidenSurname"/>

          <label for="bio">Bio</label>
          <input type="text" id="bio" v-model="user.bio"/>

          <label for="place">Place</label>
          <input type="text" id="place" v-model="user.place"/>

          <label for="dateOfBirth">Date of birth</label>
          <input type="text" id="dateOfBirth" v-model="user.dateOfBirth"/>
        </main>
        <footer>
          <input type="checkbox" id="agreement" v-model="user.agreement"/>
          <label for="agreement">I agree to EULA terms</label>
          <button type="submit" id="btnSignUp" :disabled="loading">
            Sign up
          </button>
        </footer>
      </form>
    </div>
  </div>
</template>

<script>
import SignFormLineInput from "@/components/SignFormLineInput.vue";
import UserService from "@/service/UserService";
import ValidationService from "../service/ValidationService";

export default {
  name: "SignUp",
  components: {SignFormLineInput},
  props: {},
  data() {
    return {
      user: {
        username: null,
        email: null,
        phone: null,
        passwordFirst: null,
        passwordSecond: null,
        firstName: null,
        middleName: null,
        lastName: null,
        maidenSurname: null,
        bio: null,
        place: null,
        dateOfBirth: null,
        agreement: false
      },

      success: false,
      hasErrors: false,
      loading: false,
      errors: []
    }
  },
  mounted() {
  },
  methods: {
    signUp(e) {
      e.preventDefault()

      this.errors = ValidationService.validateSignUp(this.user)

      if (this.errors.length > 0) {
        this.success = false;
        this.hasErrors = true;
      } else {
        this.loading = true;
        const newUser = {
          username: this.user.username,
          email: this.user.email,
          phone: this.user.phone,
          password: this.user.passwordFirst,
          firstName: this.user.firstName,
          middleName: this.user.middleName,
          lastName: this.user.lastName,
          maidenSurname: this.user.maidenSurname,
          bio: this.user.bio,
          place: this.user.place,
          dateOfBirth: this.user.dateOfBirth
        };
        UserService.newUser(newUser)
        .then(
            (data) => {
              this.success = true
              this.hasErrors = false
              this.loading = false
            },
            (error) => {
              this.hasErrors = true
              this.success = false
              this.loading = false
              switch (error.response.data.result) {
                case "validationError":
                  this.errors = error.response.data.errorFields
                  break;
                case "userAlreadyExistsError":
                  this.errors.push(error.response.data.message)
                  break;
                case "internalServerError":
                  this.errors.push(error.response.data.message)
                  break;
              }
            })
      }
    }
  }
}
</script>

<style scoped>
.sign-up-container {
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