<template>
  <div class="container mt-4">
    <div class="d-flex mb-3">
      <RouterLink :to="{ name: 'mypage' }" class="btn">
        <i class="bi bi-house-door-fill"></i> MyPage
      </RouterLink>

      <RouterLink :to="{ name: 'updateProfile' }" class="btn">
        <i class="bi bi-pencil-fill"></i> 프로필 수정하기
      </RouterLink>

      <RouterLink :to="{ name: 'detailUserInfo' }" class="btn">
        <i class="bi bi-eye-fill"></i> 운동 정보 보기
      </RouterLink>
    </div>

    <RouterView />
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import TodoView from "./TodoView.vue";

const loginStore = useLoginStore();
let loginUserId = loginStore.loginUserId;
// console.log("loginUserId", loginUserId);
</script>

<style scoped>
.container {
  max-width: 900px;
}

.card {
  border-radius: 10px;
}

.card-title {
  font-size: 1.25rem;
}

.btn {
  font-size: 1rem;
  padding: 0.5rem 1rem;
}

.btn i {
  margin-right: 8px;
}
</style>
