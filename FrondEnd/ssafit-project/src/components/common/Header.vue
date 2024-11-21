<template>
  <header class="header container-fluid shadow-sm py-3">
    <div class="container d-flex justify-content-between align-items-center">
      <!-- Logo Section -->
      <h1 class="logo fs-2 fw-bold m-0">
        <RouterLink :to="{ name: 'home' }" class="text-decoration-none logo-text">
          <i class="bi bi-heart-pulse-fill me-2"></i>SSAFIT
        </RouterLink>
      </h1>

      <!-- Navigation Links -->
      <nav class="nav gap-4">
        <RouterLink :to="{ name: 'home' }" class="nav-link px-3 rounded-pill">
          <i class="bi bi-house-door me-1"></i>홈
        </RouterLink>
        <RouterLink :to="{ name: 'program' }" class="nav-link px-3 rounded-pill">
          <i class="bi bi-play-circle me-1"></i>프로그램
        </RouterLink>
        <RouterLink :to="{ name: 'mypage' }" class="nav-link px-3 rounded-pill" v-if="token">
          <i class="bi bi-person me-1"></i>마이페이지
        </RouterLink>
      </nav>

      <!-- Authentication Links -->
      <div class="auth d-flex align-items-center gap-3">
        <div v-if="!token" class="d-flex gap-3">
          <RouterLink :to="{ name: 'login' }" class="btn btn-outline-primary rounded-pill px-4">
            <i class="bi bi-box-arrow-in-right me-1"></i>로그인
          </RouterLink>
          <RouterLink :to="{ name: 'signup' }" class="btn btn-primary rounded-pill px-4">
            <i class="bi bi-person-plus me-1"></i>회원가입
          </RouterLink>
        </div>
        <div v-else class="d-flex align-items-center gap-3">
          <div class="welcome">
            <RouterLink :to="getRoute(loginStore.loginUserId)" class="btn btn-light rounded-pill">
              <i class="bi bi-person-circle me-2"></i>
              <UserNameTag :user-id="loginStore.loginUserId" />
            </RouterLink>
          </div>
          <button @click="logout" class="btn btn-outline-danger rounded-pill">
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
  background-color: #FBFBFB;
  border-bottom: 1px solid rgba(198, 231, 255, 0.3);
}

.logo-text {
  color: #133E87;
}

.nav-link {
  color: #133E87;
  font-size: 1.2rem;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.nav-link:hover, .nav-link.router-link-active {
  background-color: #D4F6FF;
  color: #133E87;
  border-color: #C6E7FF;
}

.btn-primary {
  background-color: #C6E7FF;
  border-color: #C6E7FF;
  color: #133E87;
}

.btn-primary:hover {
  background-color: #D4F6FF;
  border-color: #C6E7FF;
  color: #133E87;
}

.btn-outline-primary {
  color: #133E87;
  border-color: #C6E7FF;
}

.btn-outline-primary:hover {
  background-color: #D4F6FF;
  border-color: #C6E7FF;
  color: #133E87;
}

.btn-light {
  background-color: #FBFBFB;
  border-color: #C6E7FF;
  color: #133E87;
}

.btn-light:hover {
  background-color: #D4F6FF;
  color: #133E87;
}

.btn-outline-danger {
  color: #ff6b6b;
  border-color: #ff6b6b;
}

.btn-outline-danger:hover {
  background-color: #ff6b6b;
  color: white;
}
</style>