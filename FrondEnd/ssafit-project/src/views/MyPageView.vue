<template>
  <div>
    <p>MyPage 입니다.</p>
    <TodoView :userId="loginUserId" />
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
