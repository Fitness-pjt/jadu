<template>
  <div class="container py-5" style="width: 500px">
    <!-- <h2 class="text-center mb-4">회원 정보 수정</h2> -->

    <!-- 프로필 이미지 섹션 -->
    <div class="d-flex flex-column align-items-center mb-4">
      <img
        v-if="!userStore.userProfileImg"
        src="../../assets/image/default_profile.png"
        alt="프로필 이미지"
        class="rounded-circle img-thumbnail mb-3"
        style="width: 8rem; height: 8rem; object-fit: cover"
      />
      <img
        v-else
        :src="userStore.userProfileImg"
        alt="프로필 이미지"
        class="rounded-circle img-thumbnail mb-3"
        style="width: 8rem; height: 8rem; object-fit: cover"
      />
      <label class="btn btn-secondary">
        사진 변경
        <input
          type="file"
          accept="image/*"
          @change="handleImageChange"
          class="d-none"
        />
      </label>
    </div>

    <!-- 회원 정보 수정 폼 -->
    <form @submit.prevent="handleSubmit">
      <div class="mb-3">
        <label class="form-label">닉네임</label>
        <div class="input-group">
          <input
            v-model="formData.nickname"
            type="text"
            class="form-control"
            :placeholder="userStore.userNickname"
            @input="resetNicknameCheck"
            :class="{
              'is-valid': isNicknameChecked && isNicknameAvailable,
              'is-invalid': isNicknameChecked && !isNicknameAvailable,
            }"
          />
          <button
            @click.prevent="checkNickname"
            class="btn btn-outline-secondary"
            type="button"
            :disabled="!formData.nickname || isNicknameChecked"
          >
            중복확인
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

      <div class="mb-3">
        <label class="form-label">이름</label>
        <input
          v-model="formData.name"
          type="text"
          class="form-control"
          :placeholder="userStore.userName"
          readonly
        />
      </div>

      <!-- 투두 공개 여부 -->
      <div class="mb-3 mt-4 d-flex align-items-center justify-content-between">
        <label class="form-label">To Do 공개 여부</label>
        <div class="form-check form-switch">
          <input
            type="checkbox"
            v-model="formData.status"
            class="form-check-input"
            id="status-toggle"
          />
          <label for="status-toggle" class="form-check-label">
            {{ formData.status ? "공개" : "비공개" }}
          </label>
        </div>
      </div>

      <div class="d-flex gap-2 mt-4">
        <button type="submit" class="btn btn-navy flex-grow-1">수정하기</button>
        <button
          type="button"
          @click="cancelUpdateProfile"
          class="btn btn-gray flex-grow-1"
        >
          취소
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted, watchEffect } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useLoginStore } from "@/stores/login";

const router = useRouter();
const userStore = useUserStore();
const loginStore = useLoginStore();

const formData = ref({
  nickname: "",
  name: "",
  status: true,
});

// 초기 데이터 로드
onMounted(() => {
  userStore.getUserProfileInfo(loginStore.loginUserId);
});

watchEffect(() => {
  formData.value.nickname = userStore.userNickname;
  formData.value.name = userStore.userName;
  formData.value.status = userStore.userStatus; // userStore에서 상태 가져오기
});

// 이미지 업로드 처리
const handleImageChange = async (event) => {
  const file = event.target.files[0];
  if (!file) return;

  if (!file.type.startsWith("image/")) {
    alert("이미지 파일만 업로드 가능합니다.");
    return;
  }

  if (file.size > 5 * 1024 * 1024) {
    alert("파일 크기는 5MB를 초과할 수 없습니다.");
    return;
  }

  const formData = new FormData();
  formData.append("file", file);

  try {
    const imagePath = await userStore.uploadProfileImage(formData);
  } catch (error) {
    console.error("이미지 업로드 에러:", error);
    alert("이미지 업로드에 실패했습니다.");
    event.target.value = "";
  }
};

// 닉네임 중복 체크

const isNicknameChecked = ref(false);
const isNicknameAvailable = ref(false);
const isNicknameSize = ref(false);

const checkNickname = async () => {
  if (!formData.value.nickname) {
    alert("닉네임을 입력해주세요.");
    return;
  }

  if (formData.value.nickname.length > 20) {
    alert("닉네임은 최대 20자까지 입력 가능합니다. ");
    return;
  }

  try {
    // 기존 닉네임과 동일한 경우, 중복 확인 없이 사용 가능하다고 처리
    if (formData.value.nickname === userStore.userNickname) {
      isNicknameChecked.value = true;
      isNicknameAvailable.value = true;
      return;
    }

    const response = await userStore.checkNicknameDuplicate(
      formData.value.nickname
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
const resetNicknameCheck = () => {
  isNicknameChecked.value = false;
  isNicknameAvailable.value = false;
};

// 폼 제출 처리
const handleSubmit = async () => {
  if (formData.value.nickname && !isNicknameChecked.value) {
    alert("닉네임 중복 확인이 필요합니다.");
    return;
  }

  try {
    await userStore.updateUser({
      userId: loginStore.loginUserId,
      nickname: formData.value.nickname,
      name: formData.value.name,
      status: formData.value.status,
    });
    alert("회원정보가 수정되었습니다.");
    window.location.href = "/mypage";
    // router.push("/mypage");
  } catch (error) {
    alert("회원정보 수정에 실패했습니다.");
  }
};

// 취소하기 버튼
const cancelUpdateProfile = (event) => {
  event.preventDefault();
  const confirm = window.confirm("정말로 취소하시겠습니까?");
  if (confirm) {
    router.replace({ name: "mypage" });
  }
  return;
};
</script>
<style scoped>
/* 기본 스타일 */
.container {
  transition: all 0.3s ease;
}

.profile-image {
  transition: all 0.3s ease;
  object-fit: cover;
}

.form-control {
  border-radius: 5px;
  border: 1px solid #dee2e6;
  padding: 0.75rem;
  transition: all 0.3s;
}

.form-control:focus {
  border-color: #c6e7ff;
  box-shadow: 0 0 0 0.2rem rgba(198, 231, 255, 0.25);
  outline: none;
}

/* 폼 스위치 커스텀 스타일 */
.form-switch .form-check-input {
  width: 3em;
}

.form-check-input:checked {
  background-color: #133e87;
  border-color: #133e87;
}

/* 피드백 메시지 스타일 */
.feedback-message {
  margin-top: 0.5rem;
  font-size: 0.9rem;
}

/* Large Desktop (1200px 이상) */
@media (min-width: 1200px) {
  .container {
    width: 500px;
    padding: 2rem;
  }

  .profile-image {
    width: 8rem;
    height: 8rem;
  }
}

/* Desktop & Tablet (992px ~ 1199px) */
@media (max-width: 1199px) {
  .container {
    width: 450px;
    padding: 1.8rem;
  }

  .profile-image {
    width: 7rem;
    height: 7rem;
  }
}

/* Tablet (768px ~ 991px) */
@media (max-width: 991px) {
  .container {
    width: 400px;
    padding: 1.5rem;
  }

  .profile-image {
    width: 6.5rem;
    height: 6.5rem;
  }

  .form-label {
    font-size: 0.9rem;
  }

  .form-control {
    padding: 0.6rem;
  }

  .btn {
    padding: 0.6rem !important;
  }
}

/* Mobile (576px ~ 767px) */
@media (max-width: 767px) {
  .container {
    width: 90%;
    max-width: 350px;
    padding: 1.2rem;
  }

  .profile-image {
    width: 6rem;
    height: 6rem;
  }

  .mb-3 {
    margin-bottom: 1rem !important;
  }

  .form-label {
    font-size: 0.85rem;
    margin-bottom: 0.5rem;
  }

  .form-control {
    padding: 0.5rem;
    font-size: 0.9rem;
  }

  .input-group {
    flex-direction: column;
  }

  .input-group .btn {
    width: 100%;
    margin-top: 0.5rem;
  }

  .btn {
    padding: 0.5rem !important;
    font-size: 0.9rem;
  }

  .feedback-message {
    font-size: 0.8rem;
  }

  .d-flex.gap-2 {
    gap: 0.5rem !important;
  }
}

/* Small Mobile (375px 이하) */
@media (max-width: 375px) {
  .container {
    width: 95%;
    padding: 1rem;
  }

  .profile-image {
    width: 5rem;
    height: 5rem;
  }

  .form-label {
    font-size: 0.8rem;
  }

  .form-control {
    padding: 0.4rem;
    font-size: 0.85rem;
  }

  .btn {
    padding: 0.4rem !important;
    font-size: 0.85rem;
  }

  .d-flex.gap-2 {
    gap: 0.3rem !important;
  }
}

/* 접근성 개선 */
@media (prefers-reduced-motion: reduce) {
  * {
    transition: none !important;
  }
}
</style>
