<template>
  <div>
    <p></p>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import axios from "axios";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const loginStore = useLoginStore();

onMounted(() => {
  const code = new URL(window.location.href).searchParams.get("code");
  // console.log("인증 코드 확인:", code); // 코드가 제대로 들어오는지 확인

  if (!code) {
    console.error("인증 코드가 없습니다.");
    router.push("/login");
    return;
  }

  loginStore.kakaoLogin(code);
});
</script>

<style scoped></style>
