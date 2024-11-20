<template>
  <header
    class="header d-flex justify-content-between align-items-center p-3 bg-dark text-white"
  >
    <!-- Logo Section -->
    <h1 class="logo fs-2 fw-bold">
      <RouterLink :to="{ name: 'home' }" class="text-white text-decoration-none"
        >SSAFIT</RouterLink
      >
    </h1>

    <!-- Navigation Links -->
    <nav class="nav">
      <RouterLink :to="{ name: 'home' }" class="nav-link text-white"
        >홈</RouterLink
      >
      <RouterLink :to="{ name: 'program' }" class="nav-link text-white"
        >프로그램</RouterLink
      >
      <RouterLink
        :to="{ name: 'mypage' }"
        class="nav-link text-white"
        v-if="token"
        >마이 투두리스트</RouterLink
      >
    </nav>

    <!-- Authentication Links -->
    <div class="auth d-flex align-items-center gap-3">
      <div v-if="!token" class="d-flex gap-3">
        <RouterLink :to="{ name: 'login' }" class="auth-link text-white"
          >로그인</RouterLink
        >
        <RouterLink :to="{ name: 'signup' }" class="auth-link text-white"
          >회원가입</RouterLink
        >
      </div>
      <div v-else class="d-flex">
        <div class="welcome">
          <RouterLink :to="getRoute(loginStore.loginUserId)">
            <button class="btn btn-link text-white">
              <UserNameTag :user-id="loginStore.loginUserId" />
            </button>
          </RouterLink>
        </div>
        <button @click="logout" class="btn btn-link text-white">
          로그아웃
        </button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import UserNameTag from "../user/UserNameTag.vue";

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
  height: 4rem;
}

.nav .nav-link {
  font-weight: normal;
}

.nav .nav-link:hover {
  font-weight: bold;
}

.auth-link {
  font-weight: normal;
}

.auth-link:hover {
  font-weight: bold;
  color: #42b983;
}

.logout-btn {
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  text-decoration: underline;
}

.logout-btn:hover {
  font-weight: bold;
}
</style>
