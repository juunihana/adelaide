import axios from "axios";
import {authStore} from "../stores/authStore";

class UserService {
  constructor() {
    this.restService = axios.create({
      baseURL: "http://localhost:8080/api/v1"
    })
  }
}

export default new UserService()
