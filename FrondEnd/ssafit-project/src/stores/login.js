import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { Base64 } from "js-base64";

const REST_API_URL = "http://localhost:8080/auth/login";

export const useLoginStore = defineStore("login", () => {
  const loginUserNickname = ref(null);
  const loginUserId = ref(null);

  // 로그인
  const login = (loginUser) => {
    axios.post(REST_API_URL, loginUser).then((res) => {
      // console.log("res.data :>> ", res.data);

      // sessionStorage에 access-token 넣기

      sessionStorage.setItem("access-token", res.data.accessToken);

      const token = res.data.accessToken.split(".");

      const payload = JSON.parse(Base64.decode(token[1]));
      loginUserNickname.value = payload["userNickname"];
      // console.log("loginUserNickname.value", loginUserNickname.value);
      loginUserId.value = payload["userId"];

      router.replace({ name: "home" });
    });
  };

  const googleLogin = function(){
    
  };

  // 로그아웃
  const logout = () => {
    sessionStorage.removeItem("access-token");
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

  return { login, loginUserId, loginUserNickname, logout, initialize };
});
