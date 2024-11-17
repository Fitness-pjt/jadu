<template>
  <div class="app-layout">
    <Header v-if="showLayout" />
    <div class="content">
      <RouterView />
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";

export default {
  components: {
    Header,
    Footer,
  },
  computed: {
    // 현재 라우트의 meta 필드를 기준으로 헤더와 푸터 표시 여부 결정
    showLayout() {
      return this.$route.meta.requiresLayout !== false;
    },
  },
};
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
