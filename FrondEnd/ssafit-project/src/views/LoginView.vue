<template>
  <div class="login-container">
    <!-- 로고 -->
    <div class="logo">
      <RouterLink :to="{ name: 'home' }">LOGO</RouterLink>
    </div>

    <!-- 로그인 폼 -->
    <div class="login-form">
      <h2>Login</h2>

      <!-- 입력 필드들 -->
      <div class="input-group">
        <div class="input-label">아이디</div>
        <input type="text" v-model="loginUser.userEmail" placeholder="이메일" />
      </div>

      <div class="input-group">
        <div class="input-label">비밀번호</div>
        <input
          type="password"
          v-model="loginUser.userPassword"
          placeholder="비밀번호"
        />
      </div>

      <!-- 로그인 버튼 -->
      <button class="login-button" @click="handleLogin">로그인하기</button>

      <!-- 회원가입 링크 -->
      <div class="signup-link">
        아직 회원이 아니신가요? <a @click="moveToSignup">회원가입하기</a>
      </div>

      <!-- 소셜 로그인 -->
      <div class="social-login">
        <button class="social-button google" @click="handleGoogleLogin()">
          Google
        </button>
        <button class="social-button kakao" @click="handleKakaoLogin()">
          Kakao
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const loginStore = useLoginStore();
const router = useRouter();

const loginUser = ref({
  userEmail: "",
  userPassword: "",
});

const handleLogin = () => {
  loginStore.login(loginUser.value);
  // console.log("Login attempt:", loginUser.value);
};

const moveToSignup = () => {
  router.push("/signup");
};

const handleGoogleLogin = () => {
  //google auth 링크를 만드는 과정.
  const params = {
    client_id: import.meta.env.VITE_GOOGLE_CLIENT_ID,
    redirect_uri: import.meta.env.VITE_GOOGLE_REDIRECT_URI,
    response_type: "code",
    scope: "email profile",
  };

  const queryString = new URLSearchParams(params).toString();
  const googleAuthUrl = `https://accounts.google.com/o/oauth2/v2/auth?${queryString}`;

  window.location.href = googleAuthUrl;
};

const handleKakaoLogin = async () => {
  // Kakao oauth 링크 만들기

  const REST_API_KEY = import.meta.env.VITE_KAKAO_CLIENT_ID;
  const REDIRECT_URI = import.meta.env.VITE_KAKAO_REDIRECT_URI;

  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&scope=profile_image profile_nickname account_email`;

  window.location.href = kakaoAuthUrl;
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  align-items: center;
  background-color: #f8f9fa;
}

.logo {
  font-size: 2rem;
  font-weight: bold;
  margin: 2rem 0;
}

.login-form {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  margin-bottom: 2rem;
}

.input-group {
  margin-bottom: 1rem;
}

.input-label {
  margin-bottom: 0.5rem;
  font-weight: 500;
}

input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-bottom: 1rem;
}

.login-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #666;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin: 1rem 0;
}

.signup-link {
  text-align: center;
  margin: 1rem 0;
}

.signup-link a {
  color: #666;
  text-decoration: underline;
  cursor: pointer;
}

.social-login {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.social-button {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  background: white;
  cursor: pointer;
}

.social-button img {
  width: 20px;
  height: 20px;
}

@media (max-width: 768px) {
  .login-form {
    margin: 1rem;
    padding: 1rem;
  }
}
</style>
