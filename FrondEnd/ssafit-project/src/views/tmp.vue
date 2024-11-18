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
                @input="resetNicknameCheck"
                :class="{ 'is-valid': isNicknameChecked && isNicknameAvailable, 
                         'is-invalid': isNicknameChecked && !isNicknameAvailable }"
              />
              <button 
                class="check-button"
                @click="checkNickname"
                :disabled="!user.userNickname || isNicknameChecked"
              >
                중복 확인
              </button>
            </div>
            <div class="feedback-message" v-if="isNicknameChecked">
              <span :class="{ 'text-success': isNicknameAvailable, 'text-danger': !isNicknameAvailable }">
                {{ isNicknameAvailable ? '사용 가능한 닉네임입니다.' : '이미 사용 중인 닉네임입니다.' }}
              </span>
            </div>
          </div>
  
          <div class="input-group">
            <div class="input-label">아이디</div>
            <div class="input-with-button">
              <input
                type="email"
                v-model="user.userEmail"
                placeholder="아이디를 입력해주세요. ex) hong@gmail.com"
                @input="resetEmailCheck"
                :class="{ 'is-valid': isEmailChecked && isEmailAvailable, 
                         'is-invalid': (!isEmailValid && user.userEmail) || (isEmailChecked && !isEmailAvailable) }"
              />
              <button 
                class="check-button"
                @click="checkEmail"
                :disabled="!isEmailValid || isEmailChecked"
              >
                중복 확인
              </button>
            </div>
            <div class="feedback-message">
              <span v-if="!isEmailValid && user.userEmail" class="text-danger">
                유효한 이메일 형식이 아닙니다.
              </span>
              <span v-else-if="isEmailChecked" :class="{ 'text-success': isEmailAvailable, 'text-danger': !isEmailAvailable }">
                {{ isEmailAvailable ? '사용 가능한 이메일입니다.' : '이미 사용 중인 이메일입니다.' }}
              </span>
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
  
          <div class="input-group">
            <div class="input-label">비밀번호 확인</div>
            <input
              type="password"
              v-model="passwordConfirm"
              placeholder="비밀번호를 다시 입력해주세요."
              :class="{ 'is-invalid': !isPasswordMatch && passwordConfirm }"
            />
            <div class="feedback-message">
              <span v-if="!isPasswordMatch && passwordConfirm" class="text-danger">
                비밀번호가 일치하지 않습니다.
              </span>
            </div>
          </div>
  
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
        <button 
          class="signup-button" 
          @click="handleSignup"
          :disabled="!isFormValid"
        >
          회원가입하기
        </button>
  
        <!-- 로그인 링크 -->
        <div class="login-link">
          이미 회원이신가요? <a @click="moveToLogin">로그인하기</a>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useUserStore } from "@/stores/user";
  import { ref, computed } from "vue";
  import { useRouter } from "vue-router";
  import axios from 'axios';
  
  const router = useRouter();
  const userStore = useUserStore();
  const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
  
  const user = ref({
    userNickname: "",
    userEmail: "",
    userPassword: "",
    userName: "",
  });
  
  const passwordConfirm = ref("");
  
  // 유효성 검사 상태
  const isEmailChecked = ref(false);
  const isEmailAvailable = ref(false);
  const isNicknameChecked = ref(false);
  const isNicknameAvailable = ref(false);
  
  // 이메일 유효성 검사
  const isEmailValid = computed(() => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(user.value.userEmail);
  });
  
  // 비밀번호 일치 검사
  const isPasswordMatch = computed(() => {
    return user.value.userPassword === passwordConfirm.value;
  });
  
  // 폼 전체 유효성 검사
  const isFormValid = computed(() => {
    return isEmailChecked.value && 
           isEmailAvailable.value && 
           isNicknameChecked.value && 
           isNicknameAvailable.value && 
           isPasswordMatch.value && 
           user.value.userPassword && 
           user.value.userName;
  });
  
  // 이메일 중복 체크
  const checkEmail = async () => {
    if (!isEmailValid.value) return;
  
    try {
      const response = await axios.get(`${API_BASE_URL}/api/users/check-email/${user.value.userEmail}`);
      isEmailChecked.value = true;
      isEmailAvailable.value = response.data.available;
    } catch (error) {
      console.error('이메일 중복 체크 중 오류 발생:', error);
      isEmailChecked.value = true;
      isEmailAvailable.value = false;
    }
  };
  
  // 닉네임 중복 체크
  const checkNickname = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/users/check-nickname/${user.value.userNickname}`);
      isNicknameChecked.value = true;
      isNicknameAvailable.value = response.data.available;
    } catch (error) {
      console.error('닉네임 중복 체크 중 오류 발생:', error);
      isNicknameChecked.value = true;
      isNicknameAvailable.value = false;
    }
  };
  
  // 재입력 시 체크 상태 초기화
  const resetEmailCheck = () => {
    isEmailChecked.value = false;
    isEmailAvailable.value = false;
  };
  
  const resetNicknameCheck = () => {
    isNicknameChecked.value = false;
    isNicknameAvailable.value = false;
  };
  
  const handleSignup = () => {
    if (isFormValid.value) {
      userStore.userSignUp(user.value);
    }
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
  
  .check-button:disabled {
    background-color: #f8f9fa;
    cursor: not-allowed;
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
  
  .signup-button:disabled {
    background-color: #aaa;
    cursor: not-allowed;
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
  
  .feedback-message {
    font-size: 0.875rem;
    margin-top: 0.25rem;
  }
  
  .text-danger {
    color: #dc3545;
  }
  
  .text-success {
    color: #198754;
  }
  
  .is-invalid {
    border-color: #dc3545 !important;
  }
  
  .is-valid {
    border-color: #198754 !important;
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