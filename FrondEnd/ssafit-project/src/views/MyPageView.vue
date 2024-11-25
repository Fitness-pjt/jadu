<template>
  <div class="container mt-4">
    <div class="navigation-buttons">
      <RouterLink
        :to="{ name: 'mypage' }"
        class="nav-btn"
        exact-active-class="active"
      >
        <i class="bi bi-house-door-fill"></i> 마이 투두리스트
      </RouterLink>

      <RouterLink
        :to="{ name: 'updateProfile' }"
        class="nav-btn"
        exact-active-class="active"
      >
        <i class="bi bi-pencil-fill"></i> 프로필 정보 수정
      </RouterLink>

      <RouterLink
        :to="{ name: 'detailUserInfo' }"
        class="nav-btn"
        exact-active-class="active"
      >
        <i class="bi bi-eye-fill"></i> 나의 운동 정보
      </RouterLink>
    </div>

    <RouterView />
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";

const loginStore = useLoginStore();
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

.navigation-buttons {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  padding: 1rem 0;
}

.nav-btn {
  display: flex;
  align-items: center;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  text-decoration: none;
  color: #2c3e50;
  background-color: #fff;
  font-weight: 500;
}

.nav-btn:hover {
  color: #133e87;
}

.nav-btn.active {
  color: #133e87;
  font-weight: 600;
}

.nav-btn i {
  margin-right: 8px;
  font-size: 1.1rem;
}

@media (max-width: 768px) {
  .container {
    padding: 0 1rem;
  }

  .navigation-buttons {
    flex-wrap: wrap;
    gap: 0.5rem;
    padding: 0.5rem 0;
  }

  .nav-btn {
    padding: 0.5rem 1rem;
    font-size: 0.9rem;
  }
}
</style>
