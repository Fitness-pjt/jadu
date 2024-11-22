<template>
  <header class="header container-fluid shadow-sm py-3">
    <div class="container d-flex justify-content-between align-items-center">
      <!-- Logo Section -->
      <h1 class="logo fs-2 fw-bold m-0">
        <RouterLink
          :to="{ name: 'home' }"
          class="text-decoration-none logo-text"
        >
          <i class="bi bi-heart-pulse-fill me-2"></i>SSAFIT
        </RouterLink>
      </h1>

      <!-- Navigation Links -->
      <nav class="nav gap-4">
        <RouterLink :to="{ name: 'home' }" class="nav-link px-3 rounded-pill">
          <i class="bi bi-house-door me-1"></i>홈
        </RouterLink>
        <RouterLink
          :to="{ name: 'program' }"
          class="nav-link px-3 rounded-pill"
        >
          <i class="bi bi-play-circle me-1"></i>프로그램
        </RouterLink>
        <RouterLink
          :to="{ name: 'mypage' }"
          class="nav-link px-3 rounded-pill"
          v-if="token"
        >
          <i class="bi bi-person me-1"></i>마이페이지
        </RouterLink>
      </nav>

      <!-- Authentication Links -->
      <div class="auth d-flex align-items-center gap-3">
        <div v-if="!token" class="d-flex gap-3">
          <RouterLink
            :to="{ name: 'login' }"
            class="btn btn-outline-primary rounded-pill px-4"
          >
            <i class="bi bi-box-arrow-in-right me-1"></i>로그인
          </RouterLink>
          <RouterLink
            :to="{ name: 'signup' }"
            class="btn btn-primary rounded-pill px-4"
          >
            <i class="bi bi-person-plus me-1"></i>회원가입
          </RouterLink>
        </div>
        <div v-else class="d-flex align-items-center gap-3">
          <div class="welcome">
            <RouterLink
              :to="getRoute(loginStore.loginUserId)"
              class="btn btn-light"
            >
              <UserNameTag :user-id="loginStore.loginUserId" />
            </RouterLink>
          </div>
          <button @click="logout" class="btn btn-outline-danger">
            <i class="bi bi-box-arrow-right me-1"></i>로그아웃
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import UserNameTag from "./UserNameTag.vue";

const loginStore = useLoginStore();
const token = sessionStorage.getItem("access-token");

const logout = () => {
  loginStore.logout();
};

const getRoute = (userId) => {
  return loginStore.loginUserId === userId
    ? { name: "mypage", params: { userId } }
    : { name: "profile", params: { userId } };
};
</script>

<style scoped>
.header {
  height: 6rem;
  background-color: #fbfbfb;
  border-bottom: 1px solid rgba(198, 231, 255, 0.3);
}

.logo-text {
  color: #133e87;
}

.nav-link {
  color: #133e87;
  font-size: 1.2rem;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.nav-link:hover,
.nav-link.router-link-active {
  background-color: #d4f6ff;
  color: #133e87;
  border-color: #c6e7ff;
}

.btn-primary {
  background-color: #c6e7ff;
  border-color: #c6e7ff;
  color: #133e87;
}

.btn-primary:hover {
  background-color: #d4f6ff;
  border-color: #c6e7ff;
  color: #133e87;
}

.btn-outline-primary {
  color: #133e87;
  border-color: #c6e7ff;
}

.btn-outline-primary:hover {
  background-color: #d4f6ff;
  border-color: #c6e7ff;
  color: #133e87;
}

.btn-light {
  background-color: #fbfbfb;
  /* border-color: #c6e7ff; */
  color: #133e87;
}

.btn-light:hover {
  background-color: #d4f6ff;
  color: #133e87;
}

.btn-outline-danger {
  color: #ff6b6b;
  /* border-color: #ff6b6b; */
}

.btn-outline-danger:hover {
  background-color: #ff6b6b;
  color: white;
}
</style>
