import LoginView from "@/views/LoginView.vue";
import MyPageView from "@/views/MyPageView.vue";
import SignUpView from "@/views/SignUpView.vue";
import UserProfileView from "@/views/UserProfileView.vue";
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
      meta: { requiresLayout: true }, // 헤더와 푸터를 포함
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
      meta: { requiresLayout: false }, // 헤더와 푸터를 제외
    },
    {
      path: "/signup",
      name: "signup",
      component: SignUpView,
      meta: { requiresLayout: false },
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPageView,
    },
    {
      path: "/profile/:userId",
      name: "profile",
      component: UserProfileView,
    },
  ],
});

export default router;
