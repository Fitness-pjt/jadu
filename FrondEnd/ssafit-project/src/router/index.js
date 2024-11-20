import UserProfileView from "@/views/UserProfileView.vue";
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import SignUpView from "@/views/SignUpView.vue";
import MyPageView from "@/views/MyPageView.vue";
import GoogleCallback from "@/components/login/googleCallback.vue";
import KakaoCallback from "@/components/login/KakaoCallback.vue";
import TodoView from "@/views/TodoView.vue";
import { useUserStore } from "@/stores/user";
import { useLoginStore } from "@/stores/login";
import UpdateProfile from "@/components/mypage/UpdateProfile.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
      meta: { requiresLayout: true }, // 헤더와 푸터를 포함
      meta: { requiresAuth: false }, // 인증 필요
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
      component: MyPageView,
      children: [
        {
          path: "",
          name: "mypage",
          component: TodoView,
          props: () => ({ userId: getLoginIdFromStore() }), // userId를 전달
        },
        {
          path: "updateProfile",
          name: "updateProfile",
          component: UpdateProfile,
        },
      ],
      meta: { requiresAuth: true }, // 인증 필요
    },
    {
      path: "/profile/:userId",
      name: "profile",
      component: UserProfileView,
    },
    {
      path: "/login/oauth2/code/google",
      name: "GoogleCallback",
      component: GoogleCallback,
    },
    {
      path: "/login/oauth2/code/kakao",
      name: "KakaoCallback",
      component: KakaoCallback,
    },
  ],
});

// 네비게이션 가드 추가
router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem("access-token");

  if (to.meta.requiresAuth && !token) {
    // 인증 필요하고 토큰이 없으면
    alert("로그인이 필요합니다.");
    next("/login"); // 로그인 페이지로 리다이렉트
  } else {
    next(); // 페이지 이동 허용
  }
});

export default router;

function getLoginIdFromStore() {
  const loginStore = useLoginStore();
  return loginStore.loginUserId; // 또는 적절한 ID 필드
}
