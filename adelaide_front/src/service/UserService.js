import axios from "axios";
import {generalStore} from "../stores/generalStore";

class UserService {
  constructor() {
    this.restService = axios.create({
      baseURL: "http://localhost:8080/api/v1"
    })
  }
}

export default new UserService()
