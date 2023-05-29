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
    return this.restService.post("/users/auth/sign-in",
        {username: username, password: password})
  }

  getCurrentLoggedUser() {
    return this.restService.get("/users/auth/signed", this.setRequestConfig())
  }

  getUserProfile(username) {
    return this.restService.get("/users/profile/" + username,
        this.setRequestConfig())
  }

  getUserPosts(username) {
    return this.restService.get("/posts/profile/" + username,
        this.setRequestConfig())
  }

  sendFriendRequest(username) {
    return this.restService.post("/users/friends/" + username,
        this.setRequestConfig())
  }

  acceptFriendRequest(username) {
    return this.restService.put("/users/friends/" + username + "/accept",
        this.setRequestConfig())
  }

  declineFriendRequest(username) {
    return this.restService.put("/users/friends/" + username + "/decline",
        this.setRequestConfig())
  }

  removeFriend(username) {
    return this.restService.delete("/users/friends/" + username,
        this.setRequestConfig())
  }

  addPost(post) {
    return this.restService.post("/posts/new", post, this.setRequestConfig())
  }

  removePost(postId) {
    return this.restService.delete("/posts/" + postId, this.setRequestConfig())
  }

  addVote(vote) {
    return this.restService.post("/votes/new", vote, this.setRequestConfig())
  }

  removeVote(voteId) {
    return this.restService.delete("/votes/" + voteId, this.setRequestConfig())
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
