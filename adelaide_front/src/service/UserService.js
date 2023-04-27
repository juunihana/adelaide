import axios from "axios";

class UserService {
  constructor() {
    this.restService = axios.create({
      baseURL: "http://localhost:8080/api/v1"
    })
  }

  getUserProfile(username) {
    return this.restService.get(`/user/${username}`)
  }

  newUser(newUser) {
    return this.restService.post("/user/new", newUser)
  }
}

export default new UserService()
