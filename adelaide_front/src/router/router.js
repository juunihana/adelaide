import {createRouter, createWebHistory} from "vue-router"
import UserPage from "../views/UserPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: UserPage
    },
    {
      path: "/:username",
      name: "user-page",
      component: UserPage
    }
    // {
    //   path: "/sign-in",
    //   name: "sign-in",
    //   component: SignIn
    // },
    // {
    //   path: "/sign-up",
    //   name: "sign-up",
    //   component: SignUp
    // },
    // {
    //   path: "/user/:username",
    //   name: "user-profile",
    //   component: UserProfile
    // }
  ]
})

export default router
