<template>
  <div>
    <div class="sign-up-container">
      <form method="post" class="sign-up-form" v-on:submit="submitSignUp">
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
          <input type="text" id="username" v-model="username"/>

          <label for="email">Email</label>
          <input type="text" id="email" v-model="email"/>

          <label for="phone">Phone</label>
          <input type="text" id="phone" v-model="phone"/>

          <label for="passwordFirst">Password</label>
          <input type="text" id="passwordFirst" v-model="passwordFirst"/>

          <label for="passwordSecond">Password again</label>
          <input type="text" id="passwordSecond" v-model="passwordSecond"/>

          <label for="firstName">First name</label>
          <input type="text" id="firstName" v-model="firstName"/>

          <label for="middleName">Middle name</label>
          <input type="text" id="middleName" v-model="middleName"/>

          <label for="lastName">Last name</label>
          <input type="text" id="lastName" v-model="lastName"/>

          <label for="maidenSurname">Maiden surname</label>
          <input type="text" id="maidenSurname" v-model="maidenSurname"/>

          <label for="bio">Bio</label>
          <input type="text" id="bio" v-model="bio"/>

          <label for="place">Place</label>
          <input type="text" id="place" v-model="place"/>

          <label for="dateOfBirth">Date of birth</label>
          <input type="text" id="dateOfBirth" v-model="dateOfBirth"/>
        </main>
        <footer>
          <input type="checkbox" id="agreement" v-model="agreement"/>
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

export default {
  name: "SignUp",
  components: {SignFormLineInput},
  props: {},
  data() {
    return {
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
      agreement: false,

      success: false,
      hasErrors: false,
      loading: false,
      errors: []
    }
  },
  mounted() {
  },
  methods: {
    submitSignUp(e) {
      e.preventDefault()

      this.errors = []

      if (!this.username) {
        this.errors.push("Username cannot be empty")
      }

      if (!this.email) {
        this.errors.push("Email cannot be empty")
      }

      if (!this.firstName) {
        this.errors.push("First name cannot be empty")
      }

      if (!this.lastName) {
        this.errors.push("Last name cannot be empty")
      }

      if (!this.dateOfBirth) {
        this.errors.push("Please, specify your age")
      }

      if (!this.agreement) {
        this.errors.push("You must accept EULA terms")
      }

      if (this.passwordFirst !== this.passwordSecond) {
        this.errors.push("Passwords are not matching")
      }

      if (this.errors.length > 0) {
        this.success = false;
        this.hasErrors = true;
      } else {
        this.loading = true;
        UserService.newUser({
              username: this.username,
              email: this.email,
              phone: this.phone,
              password: this.passwordFirst,
              firstName: this.firstName,
              middleName: this.middleName,
              lastName: this.lastName,
              maidenSurname: this.maidenSurname,
              bio: this.bio,
              place: this.place,
              dateOfBirth: this.dateOfBirth,
              agreement: this.agreement
            }
        )
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
  font-family: Verdana, Arial, sans-serif;
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
  font-family: Verdana, Arial, sans-serif;
  font-size: 0.95rem;
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
  font-family: Verdana, Arial, sans-serif;
  font-size: 0.95rem;
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
  font-family: Verdana, Arial, sans-serif;
  font-size: 1rem;
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