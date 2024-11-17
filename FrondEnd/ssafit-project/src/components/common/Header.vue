<template>
  <header class="header">
    <!-- Logo Section -->
    <h1 class="logo">
      <RouterLink :to="{ name: 'home' }">SSAFIT</RouterLink>
    </h1>

    <!-- Navigation Links -->
    <nav class="nav">
      <RouterLink :to="{ name: 'home' }" class="nav-link">홈</RouterLink>
      <RouterLink to="/program" class="nav-link">프로그램</RouterLink>
      <RouterLink :to="{ name: 'todo' }" class="nav-link"
        >헬스 투두리스트</RouterLink
      >
    </nav>

    <!-- Authentication Links -->
    <div class="auth">
      <div v-if="!token">
        <RouterLink :to="{ name: 'login' }" class="auth-link"
          >로그인</RouterLink
        >
        <RouterLink :to="{ name: 'signup' }" class="auth-link"
          >회원가입</RouterLink
        >
      </div>
      <div v-else>
        <p class="welcome">
          <span class="nickname">{{ loginStore.loginUserNickname }}</span> 님,
          반갑습니다!
        </p>
        <button @click="logout" class="logout-btn">로그아웃</button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";

const loginStore = useLoginStore();

console.log("loginStore.loginUserNickname", loginStore.loginUserNickname);

const token = sessionStorage.getItem("access-token");

const logout = () => {
  loginStore.logout();
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: #1b1b1b; /* Matches the black background */
  color: white;
  font-size: 1rem;

  height: 4rem;
}

.logo {
  font-size: 2rem;
  font-weight: bold;
}

.logo a {
  text-decoration: none;
  color: white;
}

.nav {
  display: flex;
  gap: 1rem;
}

nav a.router-link-exact-active {
  color: #42b983;
  font-weight: bold;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: normal;
}

.nav-link:hover {
  font-weight: bold;
  /* text-decoration: underline; */
}

.auth {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.auth > div {
  display: flex;
  gap: 0.5rem;
}

.auth-link {
  color: white;
  text-decoration: none;
}

.auth-link:hover {
  font-weight: bold;
  color: #42b983;
}

.welcome {
  margin: 0;
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

.nickname {
  color: white;
}
</style>
