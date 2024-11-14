<template>
  <div class="header">
    <h2>LOGO</h2>
    <div>
      <RouterLink :to="{ name: 'login' }">로그인</RouterLink> <span> | </span>
      <RouterLink :to="{ name: 'signup' }">회원가입</RouterLink>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
</style>
