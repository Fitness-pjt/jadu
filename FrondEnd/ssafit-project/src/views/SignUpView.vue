<!-- src/views/SignUpPage.vue -->
<template>
  <div class="signup-container">
    <!-- 로고 -->
    <div class="logo"><RouterLink :to="{ name: 'home' }">LOGO</RouterLink></div>

    <!-- 회원가입 폼 -->
    <div class="signup-form">
      <h2>Sign Up</h2>

      <!-- 입력 필드들 -->
      <div class="input-wrapper">
        <div class="input-group">
          <div class="input-label">닉네임</div>
          <div class="input-with-button">
            <input
              type="text"
              v-model="user.userNickname"
              placeholder="닉네임을 작성해주세요."
            />
            <button class="check-button">중복 확인</button>
          </div>
        </div>

        <div class="input-group">
          <div class="input-label">아이디</div>
          <div class="input-with-button">
            <input
              type="text"
              v-model="user.userEmail"
              placeholder="아이디를 입력해주세요. ex) hong@gmail.com"
            />
            <button class="check-button">중복 확인</button>
          </div>
        </div>

        <div class="input-group">
          <div class="input-label">비밀번호</div>
          <input
            type="password"
            v-model="user.userPassword"
            placeholder="비밀번호를 입력해주세요."
          />
        </div>

        <!-- <div class="input-group">
          <div class="input-label">비밀번호 확인</div>
          <input
            type="password"
            v-model="passwordConfirm"
            placeholder="비밀번호를 다시 입력해주세요."
          />
        </div> -->

        <div class="input-group">
          <div class="input-label">이름</div>
          <input
            type="text"
            v-model="user.userName"
            placeholder="이름을 작성해주세요."
          />
        </div>
      </div>

      <!-- 회원가입 버튼 -->
      <button class="signup-button" @click="handleSignup">회원가입하기</button>

      <!-- 로그인 링크 -->
      <div class="login-link">
        이미 회원이신가요? <a @click="moveToLogin">로그인하기</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const userStore = useUserStore();

const user = ref({
  userNickname: "",
  userEmail: "",
  userPassword: "",
  userName: "",
});

const handleSignup = () => {
  // 회원가입 로직 구현
  userStore.userSignUp(user.value);
};

const moveToLogin = () => {
  router.push({ name: "login" });
};
</script>

<style scoped>
.signup-container {
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

.signup-form {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 500px;
  margin-bottom: 2rem;
}

h2 {
  text-align: center;
  margin-bottom: 2rem;
}

.input-wrapper {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.input-group {
  width: 100%;
}

.input-label {
  margin-bottom: 0.5rem;
  font-weight: 500;
}

.input-with-button {
  display: flex;
  gap: 0.5rem;
}

input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 0.9rem;
}

.check-button {
  padding: 0 1rem;
  background-color: #e9ecef;
  border: 1px solid #ddd;
  border-radius: 5px;
  white-space: nowrap;
  cursor: pointer;
}

.signup-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #666;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 2rem;
}

.login-link {
  text-align: center;
  margin: 1rem 0;
  color: #666;
}

.login-link a {
  color: #666;
  text-decoration: underline;
  cursor: pointer;
}

@media (max-width: 768px) {
  .signup-form {
    margin: 1rem;
    padding: 1rem;
  }

  .input-with-button {
    flex-direction: column;
  }

  .check-button {
    padding: 0.5rem;
  }
}
</style>
