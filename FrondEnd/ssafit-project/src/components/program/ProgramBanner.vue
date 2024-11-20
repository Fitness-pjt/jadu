<template>
  <div class="banner-container text-center text-white">
    <div class="container py-5">
      <h1 class="display-4 fw-bold">맞춤형 프로그램 추천</h1>
      <p class="lead mb-4">
        당신의 목표와 취향에 맞는 최적의 프로그램을 찾아보세요.
      </p>
      <RouterLink :to="{ name: 'userInfo' }">
        <button class="btn btn-primary btn-lg banner-btn">
          프로그램 추천 받기
        </button>
      </RouterLink>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.banner-container {
  background-color: #42b983; /* 배경색을 포인트 컬러로 설정 */
  border-radius: 8px;
  margin: 20px auto;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.banner-btn {
  background-color: #ffffff; /* 버튼 배경을 흰색으로 설정 */
  color: #42b983; /* 버튼 텍스트 색상을 포인트 컬러로 */
  border: 2px solid #ffffff;
  transition: all 0.3s ease;
}
.banner-btn:hover {
  background-color: #42b983;
  color: #ffffff;
  border-color: #42b983;
}
</style>
