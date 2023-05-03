import axios from "axios";
import {authStore} from "../stores/authStore";

class UserService {
  constructor() {
    this.restService = axios.create({
      baseURL: "http://localhost:8080/api/v1"
    })
  }

  getSignedUser() {
    return this.restService.get("/user-signed", {withCredentials: true})
  }
  getUserProfile(username) {
    return this.restService.get(`/user/${username}`, {withCredentials: true})
  }

  newUser(newUser) {
    return this.restService.post("/user/new", newUser)
  }

  signIn(userCredentials) {
    return this.restService.post("/user/sign-in", userCredentials)
  }
}

export default new UserService()
