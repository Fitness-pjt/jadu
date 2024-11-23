import GoogleCallback from "@/components/login/googleCallback.vue";
import KakaoCallback from "@/components/login/KakaoCallback.vue";
import UpdateProfile from "@/components/mypage/UpdateProfile.vue";
import ProgramDetail from "@/components/program/ProgramDetail.vue";
import ProgramEdit from "@/components/program/ProgramEdit.vue";

import QuestionCreate from "@/components/question/QuestionCreate.vue";
import QuestionDetail from "@/components/question/QuestionDetail.vue";
import QuestionList from "@/components/question/QuestionList.vue";
import QuestionUpdate from "@/components/question/QuestionUpdate.vue";
import ReviewCreate from "@/components/review/ReviewCreate.vue";
import ReviewList from "@/components/review/ReviewList.vue";
import UserInfoCreate from "@/components/user/UserInfoCreate.vue";
import UserInfoDetail from "@/components/user/UserInfoDetail.vue";
import UserInfoUpdate from "@/components/user/UserInfoUpdate.vue";
import { useLoginStore } from "@/stores/login";
import LoginView from "@/views/LoginView.vue";
import MyPageView from "@/views/MyPageView.vue";
import QuestionView from "@/views/QuestionView.vue";
import ReviewView from "@/views/ReviewView.vue";
import SignUpView from "@/views/SignUpView.vue";
import TodoView from "@/views/TodoView.vue";
import UserInfoView from "@/views/UserInfoView.vue";
import UserProfileView from "@/views/UserProfileView.vue";
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import VideoListView from "@/views/VideoListView.vue";
import ProgramVideoList from "@/components/program/\bProgramVideoList.vue";

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
        {
          path: "updateUserInfo",
          name: "updateUserInfo",
          component: UserInfoUpdate,
        },
        {
          path: "detailUserInfo",
          name: "detailUserInfo",
          component: UserInfoDetail,
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
      path: "/program",
      name: "program",
      component: UserInfoView,
    },
    {
      path: "/program/createAi",
      name: "createAIProgram",
      component: UserInfoCreate,
      meta: { requiresAuth: true }, // 인증 필요
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
    {
      path: "/program/:programId",
      name: "programDetail",
      component: ProgramDetail,
      children: [
        {
          path: "question",
          name: "question",
          component: QuestionView,
          children: [
            {
              path: "",
              name: "question",
              component: QuestionList,
            },
            {
              path: ":questionId",
              name: "questionDetail",
              component: QuestionDetail,
            },
            {
              path: "create",
              name: "questionCreate",
              component: QuestionCreate,
            },
            {
              path: "update/:questionId",
              name: "questionUpdate",
              component: QuestionUpdate,
            },
          ],
        },
        {
          path: "review",
          name: "review",
          component: ReviewView,
          children: [
            {
              path: "",
              name: "review",
              component: ReviewList,
            },
            {
              path: "create",
              name: "reviewCreate",
              component: ReviewCreate,
            },
            // {
            //   path: "update/:reviewId",
            //   name: "reviewUpdate",
            //   component: QuestionUpdate,
            // },
          ],
        },
        {
          path: "video",
          component: VideoListView,
          children: [
            {
              path: "",
              name: "video",
              component: ProgramVideoList,
              // props: true, // URL의 params를 props로 넘김
            },
          ],
        },
      ],
    },
    {
      path: "/program/edit/:programId",
      name: "programEdit",
      component: ProgramEdit,
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
