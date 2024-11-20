<template>
  <div>
    <p>홈 화면입니다.</p>
    <RouterLink :to="getRoute(1)">
      <button><UserNameTag :user-id="1" /></button
    ></RouterLink>

    <RouterLink :to="getRoute(2)">
      <button><UserNameTag :user-id="2" /></button
    ></RouterLink>

    <RouterLink :to="getRoute(5)">
      <button><UserNameTag :user-id="5" /></button
    ></RouterLink>
    <RouterLink :to="getRoute(3)">
      <button><UserNameTag :user-id="3" /></button
    ></RouterLink>
    <ProgramBanner />
  </div>
</template>

<script setup>
import UserNameTag from "@/components/user/UserNameTag.vue";
import ProgramBanner from "@/components/program/ProgramBanner.vue";
import { useLoginStore } from "@/stores/login";
const loginStore = useLoginStore();

const getRoute = (userId) => {
  return loginStore.loginUserId === userId
    ? { name: "mypage", params: { userId } }
    : { name: "profile", params: { userId } };
};
</script>

<style scoped>
p {
  padding: 1rem;
  font-size: large;
  font-weight: bold;
}
</style>
