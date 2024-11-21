<template>
  <div>
    <p>MyPage 입니다.</p>

    <RouterLink :to="{ name: 'updateProfile' }">
      <button>프로필 수정하기</button></RouterLink
    >
    <RouterLink :to="{ name: 'updateUserInfo' }">
      <button>운동정보 수정하기</button></RouterLink
    >
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
p {
  font-size: larger;
  font-weight: bold;
  padding: 1rem;
}
</style>
