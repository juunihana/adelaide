class ValidationService {
  validateSignUp(newUser) {
    let errors = []

    if (!newUser.username) {
      errors.push("Username cannot be empty")
    }

    if (!newUser.email) {
      errors.push("Email cannot be empty")
    }

    if (!newUser.firstName) {
      errors.push("First name cannot be empty")
    }

    if (!newUser.lastName) {
      errors.push("Last name cannot be empty")
    }

    if (!newUser.dateOfBirth) {
      errors.push("Please, specify your age")
    }

    if (!newUser.agreement) {
      errors.push("You must accept EULA terms")
    }

    if (newUser.passwordFirst !== newUser.passwordSecond) {
      errors.push("Passwords are not matching")
    }

    return errors
  }
}

export default new ValidationService()
