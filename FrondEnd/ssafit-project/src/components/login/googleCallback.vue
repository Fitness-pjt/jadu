<template>
  <div>
    <p></p>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { useLoginStore } from "@/stores/login";

const router = useRouter();

onMounted(() => {
  const code = new URL(window.location.href).searchParams.get("code");

  const userStore = useLoginStore();

  if (!code) {
    console.error("인증 코드가 없습니다.");
    router.push("/login");
    return;
  }

  axios
    .get(`http://localhost:8080/login/oauth2/code/google?code=${code}`, {
      headers: {
        "Content-Type": "application/json",
        // CORS 관련 헤더 추가
        "Access-Control-Allow-Origin": "*",
      },
    })
    .then((response) => {
      if (!response.data || !response.data.accessToken) {
        console.error("토큰 데이터가 유효하지 않습니다:", response.data);
        return;
      }

      // TokenInfo 형식에 맞춰 저장
      sessionStorage.setItem("access-token", response.data.accessToken);
      sessionStorage.setItem("refresh-token", response.data.refreshToken);

      // 홈페이지로 이동
      router
        .push({ name: "home" })
        .then(() => {
          window.location.reload();
        })
        .catch((routeError) => {
          console.error("라우팅 에러:", routeError);
        });
    })
    .catch((error) => {
      // 더 자세한 에러 정보 출력
      console.error("로그인 처리 실패:", error.response?.data || error.message);
      console.error("에러 상태 코드:", error.response?.status);
      console.error("전체 에러 객체:", error);

      // 에러 상황에 따른 처리
      if (error.response?.status === 401) {
        // console.log("인증 실패");
        router.push("/login");
      } else if (error.response?.status === 400) {
        // console.log("잘못된 요청");
        router.push("/login");
      } else {
        // console.log("기타 에러");
        router.push("/login");
      }
    });
});
</script>
