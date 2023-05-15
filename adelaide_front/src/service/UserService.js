import axios from "axios";

class UserService {
  constructor() {
    this.restService = axios.create({
      baseURL: "http://localhost:8080/api/v1"
    })
  }

  signIn(username, password) {
    return this.restService.post("/users/auth/sign-in",
        {username: username, password: password})
  }

  getUserProfile(username) {
    return this.restService.get("/users/profile/" + username)
  }

  getUserPosts(username) {
    return this.restService.get("/posts/profile/" + username)
  }
}

export default new UserService()
