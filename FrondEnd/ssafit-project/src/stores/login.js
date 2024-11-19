import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { Base64 } from "js-base64";

const REST_API_URL = "http://localhost:8080/auth/login";

export const useLoginStore = defineStore("login", () => {
  let loginUserNickname = ref(null);
  let loginUserId = ref(null);

  // 로그인
  const login = (loginUser) => {
    axios.post(REST_API_URL, loginUser).then((res) => {
      console.log("res.data :>> ", res.data);

      // sessionStorage에 access-token 넣기
      sessionStorage.setItem("access-token", res.data.accessToken);

      const token = res.data.accessToken.split(".");

      const payload = JSON.parse(Base64.decode(token[1]));

      // console.log('payload :>> ', payload);
      loginUserNickname.value = payload["userNickname"];
      // console.log("loginUserNickname.value", loginUserNickname.value);
      loginUserId.value = payload["userId"];

      router.replace({ name: "home" });
    });
  };

  const kakaoLogin =  (code) =>{
    axios
    .get(`http://localhost:8080/login/oauth2/code/kakao?code=${code}`, {
      headers: {
        "Content-Type": "application/json",
        // CORS 관련 헤더 추가
        "Access-Control-Allow-Origin": "*",
      },
      withCredentials: true, // 인증 권한 추가
    })
    .then((response) => {
      // console.log("백엔드 응답 확인:", response.data);

      if (!response.data || !response.data.accessToken) {
        console.error("토큰 데이터가 유효하지 않습니다:", response.data);
        return;
      }

      // TokenInfo 형식에 맞춰 저장
      sessionStorage.setItem("access-token", response.data.accessToken);
      sessionStorage.setItem("refresh-token", response.data.refreshToken);
      //   console.log("토큰 저장 완료");


      // 홈페이지로 이동
      router
        .push({ name: "home" })
        .then(() => {
          //   console.log("라우팅 완료, 새로고침 시작");
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
        console.log("인증 실패");
        router.push("/login");
      } else if (error.response?.status === 400) {
        console.log("잘못된 요청");
        router.push("/login");
      } else {
        console.log("기타 에러");
        router.push("/login");
      }
    });
  }

  // 로그아웃
  const logout = () => {
    sessionStorage.removeItem("access-token");
    sessionStorage.removeItem("refresh-token");
    loginUserNickname.value = null;
    loginUserId.value = null;
    router.replace({ name: "home" });
    window.location.reload();
  };

  // 초기화: sessionStorage에 토큰이 있다면 상태 복구
  const initialize = () => {
    const token = sessionStorage.getItem("access-token");
    if (token) {
      const payload = JSON.parse(Base64.decode(token.split(".")[1]));
      loginUserNickname.value = payload["userNickname"];
      loginUserId.value = payload["userId"];
    }
  };

  return { login, loginUserId, loginUserNickname, logout, initialize, kakaoLogin };
});
