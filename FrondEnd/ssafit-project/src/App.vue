<template>
  <div>
    <Header v-if="showLayout" />
    <RouterView />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "./components/common/Footer.vue";

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

<style scoped></style>
