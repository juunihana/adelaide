import {createRouter, createWebHistory} from "vue-router"
import UserProfile from "@/views/UserProfile.vue";
import PostFeed from "@/views/PostFeed.vue";
import SignIn from "@/views/SignIn.vue";
import SignUp from "@/views/SignUp.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: PostFeed
    },
    {
      path: "/sign-in",
      name: "sign-in",
      component: SignIn
    },
    {
      path: "/sign-up",
      name: "sign-up",
      component: SignUp
    },
    {
      path: "/user/:username",
      name: "user-profile",
      component: UserProfile
    }
  ]
})

export default router
