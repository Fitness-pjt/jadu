<template>
  <div>
    <p>{{ userStore.userNickname }}의 Profile Page</p>
    <TodoView :userId="userId" />
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { onMounted } from "vue";
import TodoView from "./TodoView.vue";
import { useRoute } from "vue-router";

const userStore = useUserStore();
const route = useRoute();
const userId = route.params.userId;

onMounted(() => [userStore.getUserProfileInfo(userId)]);
</script>

<style scoped>
p {
  font-size: x-large;
  font-weight: bold;
}
</style>
