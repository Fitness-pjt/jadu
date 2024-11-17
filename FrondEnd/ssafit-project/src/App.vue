<template>
  <div class="app-layout">
    <Header v-if="showLayout" />
    <div class="content">
      <RouterView />
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { computed, onMounted } from "vue";
import { useLoginStore } from "./stores/login";
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";

// Header와 Footer 컴포넌트 가져오기
const route = useRoute(); // 현재 라우트 정보 가져오기
const loginStore = useLoginStore(); // Pinia store 가져오기

// 레이아웃 표시 여부 (reactive)
const showLayout = computed(() => route.meta.requiresLayout !== false);

// Login store 초기화
onMounted(() => {
  loginStore.initialize();
});
</script>

<style scoped>
/* 전체 레이아웃 */
.app-layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 화면 높이 전체를 채움 */
}

/* 메인 콘텐츠 영역 */
.content {
  flex: 1; /* 공간을 차지하면서 Footer를 아래로 밀어냄 */
  padding: 1rem; /* 기본적인 여백 */
}
</style>
