<template>
  <div>
    <p>홈 화면입니다.</p>
    <RouterLink :to="{ name: 'profile', params: { userId: 1 } }">
      <button><UserNameTag :user-id="1"/></button></RouterLink
    >

    <RouterLink :to="{ name: 'profile', params: { userId: 2 } }">
      <button><UserNameTag :user-id="2"/></button></RouterLink
    >
  </div>
</template>

<script setup>
import UserNameTag from "@/components/user/UserNameTag.vue";
</script>


<style scoped>
p {
  padding: 1rem;
  font-size: large;
  font-weight: bold;
}
</style>
