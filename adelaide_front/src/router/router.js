import {createRouter, createWebHistory} from "vue-router"
import UserPage from "../views/UserPage.vue";
import MainPage from "../views/MainPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: MainPage
    },
    {
      path: "/:username",
      name: "my-page",
      component: UserPage
    },
    {
      path: "/feed",
      name: "my-feed",
      component: UserPage
    },
    {
      path: "/messages",
      name: "my-messages",
      component: UserPage
    },
    {
      path: "/friends",
      name: "my-friends",
      component: UserPage
    },
    {
      path: "/groups",
      name: "my-groups",
      component: UserPage
    },
    {
      path: "/photos",
      name: "my-photos",
      component: UserPage
    },
    {
      path: "/music",
      name: "my-music",
      component: UserPage
    },
    {
      path: "/videos",
      name: "my-videos",
      component: UserPage
    }
  ]
})

export default router
