<template>
  <div class="login-container">
    <!-- 로고 -->
    <div class="logo">
      <RouterLink :to="{ name: 'home' }"
        ><img src="@/assets/image/logo.png" style="width: 100px" />
      </RouterLink>
    </div>

    <!-- 로그인 폼 -->
    <div class="login-form">
      <h2>LOGIN</h2>

      <!-- 입력 필드들 -->
      <div class="input-group">
        <div class="input-label">아이디</div>
        <input
          type="text"
          v-model="loginUser.userEmail"
          placeholder="이메일을 입력해주세요. ex) hong@gmail.com"
        />
        <!-- 아이디 입력이 비었을 경우 경고 메시지 -->
        <p v-if="emailError" class="error-message">아이디를 입력해주세요.</p>
        <!-- 아이디가 유효하지 않을 경우 경고 메시지 -->
        <!-- <p v-if="invalidEmailError" class="error-message">
          유효한 이메일 형식이 아닙니다.
        </p> -->
      </div>

      <div class="input-group">
        <div class="input-label">비밀번호</div>
        <input
          type="password"
          v-model="loginUser.userPassword"
          placeholder="비밀번호를 입력해주세요."
        />
        <!-- 비밀번호 입력이 비었을 경우 경고 메시지 -->
        <p v-if="passwordError" class="error-message">
          비밀번호를 입력해주세요.
        </p>
      </div>

      <!-- 로그인 버튼 -->
      <button class="login-button" @click="handleLogin">로그인하기</button>

      <!-- 회원가입 링크 -->
      <div class="signup-link">
        아직 회원이 아니신가요?
        <a @click="moveToSignup">회원가입하기</a>
      </div>

      <!-- 소셜 로그인 -->
      <div class="social-login">
        <button class="social-button google" @click="handleGoogleLogin()">
          <img src="@/assets/image/google-logo.svg" />
          Google
        </button>
        <button class="social-button kakao" @click="handleKakaoLogin()">
          <img src="@/assets/image/kakao-logo.svg" />Kakao
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import { ref } from "vue";
import { useRouter } from "vue-router";

const loginStore = useLoginStore();
const router = useRouter();

const loginUser = ref({
  userEmail: "",
  userPassword: "",
});

// 입력 필드 오류 상태
const emailError = ref(false);
const passwordError = ref(false);

// 이메일 유효성 검사
// const invalidEmailError = ref(false); // 유효하지 않은 이메일 형식 에러

// const isEmailValid = (email) => {
//   const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//   return emailRegex.test(email);
// };

const handleLogin = () => {
  // 유효성 검사
  emailError.value = loginUser.value.userEmail === "";
  passwordError.value = loginUser.value.userPassword === "";

  // invalidEmailError.value =
  //   !emailError.value && !isEmailValid(loginUser.value.userEmail);

  if (!emailError.value && !passwordError.value) {
    loginStore.login(loginUser.value);

    // 입력 후 필드 초기화
    loginUser.value.userEmail = "";
    loginUser.value.userPassword = "";
  } // 로그인하기
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
  align-items: center;
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
  max-width: 500px;
  margin-bottom: 2rem;
  text-align: center; /* 텍스트 정렬 추가 */
}

.login-form h2 {
  text-align: center; /* 수평 가운데 정렬 */
  margin-bottom: 1.5rem; /* 아래 간격 추가 */
  font-size: 1.8rem; /* 글씨 크기 조절 */
  font-weight: bold; /* 강조 */
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
  border-radius: 5px !important;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.login-button {
  transition: all 0.3s;
  width: 100%;
  padding: 0.75rem;
  background-color: #133e87;
  color: white;
  border: 1px solid #133e87;
  border-radius: 5px;
  cursor: pointer;
  margin: 1rem 0;
}

.login-button:hover {
  background-color: white;
  border-color: #133e87;
  color: #133e87;
}

.signup-link {
  text-align: center;
  margin: 1rem 0;
  color: #666;
}

.signup-link a {
  color: #414141;
  text-decoration: underline !important;
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

.social-button:hover {
  background: #f1f1f1;
  transform: scale(1.02);
  box-shadow: 0 2px 2px rgba(0, 0, 0, 0.1);
}

.social-button img {
  width: 20px;
  height: 20px;
}

.btn-outline-primary {
  color: #133e87;
  border-color: #133e87;
}

.btn-outline-primary:hover {
  background-color: #133e87;
  border-color: #133e87;
  color: white;
}

.error-message {
  color: red;
  font-size: 0.9rem;
  margin-top: 0.3rem;
}

@media (max-width: 768px) {
  .login-form {
    margin: 1rem;
    padding: 1rem;
  }
}
</style>
