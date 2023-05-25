import axios from "axios";
import VueCookies from "vue-cookies";

class UserService {
  constructor() {
    this.restService = axios.create({
      baseURL: "http://localhost:8080/api/v1"
    })
  }

  signUp(user) {
    return this.restService.post("/users/new", user)
  }

  signIn(username, password) {
    return this.restService.post("/users/auth/sign-in", {username: username, password: password})
  }

  getCurrentLoggedUser() {
    return this.restService.get("/users/auth/signed", this.setRequestConfig())
  }

  getUserProfile(username) {
    return this.restService.get("/users/profile/" + username, this.setRequestConfig())
  }

  getUserPosts(username) {
    return this.restService.get("/posts/profile/" + username, this.setRequestConfig())
  }

  addNewPost(post) {
    return this.restService.post("/posts/new", this.setRequestConfig())
  }

  setRequestConfig() {
    let config = {};
    const authCookie = VueCookies.get("auth");
    if (authCookie && authCookie !== "") {
      config.headers = {
        Authorization: authCookie
      }
    }
    return config;
  }
}

export default new UserService()
