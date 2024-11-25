<template>
  <div class="signup-container">
    <!-- 로고 -->
    <div class="logo">
      <RouterLink :to="{ name: 'home' }"
        ><img src="@/assets/image/logo.png" style="width: 100px"
      /></RouterLink>
    </div>

    <!-- 회원가입 폼 -->
    <div class="signup-form">
      <h2>SIGN UP</h2>

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
              :class="{
                'is-valid': isNicknameChecked && isNicknameAvailable,
                'is-invalid': isNicknameChecked && !isNicknameAvailable,
              }"
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
            <span
              :class="{
                'text-success': isNicknameAvailable,
                'text-danger': !isNicknameAvailable,
              }"
            >
              {{
                isNicknameAvailable
                  ? "사용 가능한 닉네임입니다."
                  : "이미 사용 중인 닉네임입니다."
              }}
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
              :class="{
                'is-valid': isEmailChecked && isEmailAvailable,
                'is-invalid':
                  (!isEmailValid && user.userEmail) ||
                  (isEmailChecked && !isEmailAvailable),
              }"
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
            <span
              v-else-if="isEmailChecked"
              :class="{
                'text-success': isEmailAvailable,
                'text-danger': !isEmailAvailable,
              }"
            >
              {{
                isEmailAvailable
                  ? "사용 가능한 이메일입니다."
                  : "이미 사용 중인 이메일입니다."
              }}
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
            <span
              v-if="!isPasswordMatch && passwordConfirm"
              class="text-danger"
            >
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

const router = useRouter();
const userStore = useUserStore();

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

const isPasswordMatch = computed(() => {
  return user.value.userPassword === passwordConfirm.value;
});
// 폼 전체 유효성 검사
const isFormValid = computed(() => {
  return (
    isEmailChecked.value &&
    isEmailAvailable.value &&
    isNicknameChecked.value &&
    isNicknameAvailable.value &&
    isPasswordMatch.value &&
    user.value.userPassword &&
    user.value.userName
  );
});

// 이메일 중복 체크
const checkEmail = async () => {
  if (!isEmailValid.value) return;

  try {
    const response = await userStore.checkEmailDuplicate(user.value.userEmail);
    isEmailChecked.value = true;
    isEmailAvailable.value = response;
  } catch (error) {
    console.error("이메일 중복 체크 중 오류 발생:", error);
    isEmailChecked.value = true;
    isEmailAvailable.value = false;
  }
};
// 닉네임 중복 체크
const checkNickname = async () => {
  try {
    const response = await userStore.checkNicknameDuplicate(
      user.value.userNickname
    );
    isNicknameChecked.value = true;
    isNicknameAvailable.value = response;
  } catch (error) {
    console.error("닉네임 중복 체크 중 오류 발생:", error);
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
  text-align: center; /* 수평 가운데 정렬 */
  margin-bottom: 1.5rem; /* 아래 간격 추가 */
  font-size: 1.8rem; /* 글씨 크기 조절 */
  font-weight: bold; /* 강조 */
}

.input-wrapper {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
}

.input-group {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.input-label {
  margin-bottom: 0.5rem;
  font-weight: 500;
}

.input-with-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px !important;
  font-size: 0.9rem;
}

.check-button {
  padding: 0.75rem;
  background-color: #e9ecef;
  border: 1px solid #ddd;
  border-radius: 5px;
  white-space: nowrap;
  cursor: pointer;
}

.signup-button {
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

.signup-button:hover {
  background-color: white;
  border-color: #133e87;
  color: #133e87;
}

.login-link {
  text-align: center;
  margin: 1rem 0;
  color: #666;
}

.login-link a {
  color: #414141;
  text-decoration: underline !important;
  cursor: pointer;
}

.feedback-message {
  font-size: 0.9rem;
  margin-top: 0.5rem;
}

.is-valid {
  border-color: #28a745;
}

.is-invalid {
  border-color: #dc3545;
}

.text-success {
  color: #28a745;
}

.text-danger {
  color: #dc3545;
}
</style>
