<template>
  <div class="container py-5" style="width: 500px">
    <h2 class="text-center mb-4">회원 정보 수정</h2>

    <!-- 프로필 이미지 섹션 -->
    <div class="text-center mb-4">
      <img
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
          />
          <button
            @click.prevent="checkNickname"
            class="btn btn-outline-secondary"
            type="button"
          >
            중복확인
          </button>
        </div>
      </div>

      <div class="mb-3">
        <label class="form-label">새 비밀번호</label>
        <input
          v-model="formData.newPassword"
          type="password"
          class="form-control"
        />
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

      <div class="mb-3 d-flex align-items-center justify-content-between">
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
        <button type="submit" class="btn btn-primary flex-grow-1">
          수정하기
        </button>
        <button
          type="button"
          @click="router.go(-1)"
          class="btn btn-secondary flex-grow-1"
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
  currentPassword: "",
  newPassword: "",
  name: "",
  status: true,
});

const isNicknameChecked = ref(false);

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
const checkNickname = async () => {
  if (!formData.value.nickname) {
    alert("닉네임을 입력해주세요.");
    return;
  }

  try {
    const isAvailable = await userStore.checkNicknameDuplicate(
      formData.value.nickname
    );
    if (isAvailable) {
      isNicknameChecked.value = true;
      alert("사용 가능한 닉네임입니다.");
    } else {
      alert("이미 사용중인 닉네임입니다.");
    }
  } catch (error) {
    alert("중복 확인 중 오류가 발생했습니다.");
  }
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
      newPassword: formData.value.newPassword,
      name: formData.value.name,
      status: formData.value.status,
    });
    alert("회원정보가 수정되었습니다.");
    router.push("/profile");
  } catch (error) {
    alert("회원정보 수정에 실패했습니다.");
  }
  window.location.href = "/mypage";
};
</script>

<style scoped>
.edit-container {
  max-width: 28rem;
  margin: 0 auto;
  padding: 2rem;
}

.title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 2rem;
  text-align: center;
}

.profile-image-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2rem;
}

.profile-image {
  width: 8rem;
  height: 8rem;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 1rem;
}

.upload-button {
  padding: 0.5rem 1rem;
  background-color: #4b5563;
  color: white;
  border-radius: 0.375rem;
  cursor: pointer;
}

.upload-button:hover {
  background-color: #374151;
}

.hidden {
  display: none;
}

.form-container {
  width: 100%;
}

.input-group {
  margin-bottom: 1rem;
}

.input-label {
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.input-field {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #e5e7eb;
  border-radius: 0.375rem;
}

.nickname-input-group {
  display: flex;
  gap: 0.5rem;
}

.nickname-input-group .input-field {
  flex: 1;
}

.check-button {
  padding: 0.5rem 1rem;
  background-color: #4b5563;
  color: white;
  border-radius: 0.375rem;
  white-space: nowrap;
}

.button-group {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.submit-button {
  flex: 1;
  padding: 0.75rem;
  background-color: #3b82f6;
  color: white;
  border-radius: 0.375rem;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #2563eb;
}

.cancel-button {
  flex: 1;
  padding: 0.75rem;
  background-color: #e5e7eb;
  border-radius: 0.375rem;
  cursor: pointer;
}

.cancel-button:hover {
  background-color: #d1d5db;
}
.status-toggle-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.5rem 0;
}

.toggle-wrapper {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.toggle-input {
  display: none;
}

.toggle-label {
  position: relative;
  display: inline-block;
  width: 3rem;
  height: 1.5rem;
  background-color: #e5e7eb;
  border-radius: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.toggle-input:checked + .toggle-label {
  background-color: #3b82f6;
}

.toggle-button {
  position: absolute;
  top: 0.125rem;
  left: 0.125rem;
  width: 1.25rem;
  height: 1.25rem;
  background-color: white;
  border-radius: 50%;
  transition: transform 0.2s;
}

.toggle-input:checked + .toggle-label .toggle-button {
  transform: translateX(1.5rem);
}

.toggle-text {
  font-size: 0.875rem;
  color: #4b5563;
}
</style>
