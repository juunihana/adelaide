<template>
  <div>
    <div class="sign-up-container">
      <form method="post" class="sign-up-form" v-on:submit="signUp">
        <header>
          Sign up
        </header>
        <InfoFrameStatic type="success" v-if="success">
          You have successfully signed up!<br/>
          Welcome, {{ this.user.username }}! You can use your credentials to sign in now
        </InfoFrameStatic>
        <InfoFrameStatic type="error" v-if="hasErrors">
          Following errors occurred during sign up:
          <ul>
            <li v-for="error in errors">{{ error }}</li>
          </ul>
        </InfoFrameStatic>
        <main>
          <SignFormLineInput v-model="user.username" :id="'username'" :title="'Username*'"/>
          <SignFormLineInput v-model="user.email" :id="'email'" :title="'Email*'"/>
          <SignFormLineInput v-model="user.passwordFirst" :id="'passwordFirst'" :title="'Password*'" :password="true"/>
          <SignFormLineInput v-model="user.passwordSecond" :id="'passwordSecond'" :title="'Password again*'" :password="true"/>
          <SignFormLineInput v-model="user.firstName" :id="'firstName'" :title="'First name*'"/>
          <SignFormLineInput v-model="user.lastName" :id="'lastName'" :title="'Last name*'"/>
          <SignFormLineInput v-model="user.middleName" :id="'middleName'" :title="'Middle name'"/>
          <SignFormLineInput v-model="user.maidenSurname" :id="'maidenSurname'" :title="'Maiden surname'"/>
<!--          <SignFormLineInput v-model="user.phone" :id="'phone'" :title="'Phone'"/>-->
          <SignFormLineInput v-model="user.place" :id="'place'" :title="'Place'"/>
          <SignFormLineInput v-model="user.dateOfBirth" :id="'dateOfBirth'" :title="'Date of birth*'"/>
          <SignFormTextInput class="user-bio" v-model="user.bio" :id="'bio'" :title="'Bio'"/>
        </main>
        <footer>
          <input type="checkbox" id="agreement" v-model="user.agreement"/>
          <label for="agreement">I agree to EULA terms*</label>
          <SignFormButton id="btnSignUp" :loading="loading" title="Sign up"/>
        </footer>
      </form>
    </div>
  </div>
</template>

<script>
import SignFormLineInput from "@/components/SignFormLineInput.vue";
import UserService from "@/service/UserService";
import ValidationService from "../service/ValidationService";
import InfoFrameStatic from "@/components/InfoFrameStatic.vue";
import SignFormButton from "@/components/SignFormButton.vue";
import SignFormTextInput from "@/components/SignFormTextInput.vue";

export default {
  name: "SignUp",
  components: {SignFormTextInput, SignFormButton, InfoFrameStatic, SignFormLineInput},
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
  margin-bottom: 10px;
  padding: 10px;
  text-align: center;
  font-family: var(--text-font-general);
  font-size: 1.5rem;
  color: var(--text-color-general);
}

main {
  display: grid;
  grid-template-columns: 1fr 1fr;
  row-gap: 10px;
  column-gap: 10px;
}

label {
  padding: 10px 10px 10px 0;
  font-family: var(--text-font-general);
  font-size: var(--text-size-form-label);
  color: var(--text-color-general);
}

footer {
  flex: 1;
  margin-top: 10px;
}

#agreement {
  margin-right: 10px;
  cursor: pointer;
}

.user-bio {
  grid-column-start: 1;
  grid-column-end: 3;
}
</style>