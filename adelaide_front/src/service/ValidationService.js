class ValidationService {
  validateUsernameEmailPassword(email, username, password, passwordRepeat) {
    let errors = []
    if (!email || email === "") {
      errors.push("Email cannot be empty")
    }
    if (!username || username === "") {
      errors.push("Username cannot be empty")
    }
    if (!password || password === "") {
      errors.push("Password cannot be empty")
    }
    if (!passwordRepeat || passwordRepeat !== password) {
      errors.push("Passwords do not match")
    }
     return errors
  }
}

export default new ValidationService()