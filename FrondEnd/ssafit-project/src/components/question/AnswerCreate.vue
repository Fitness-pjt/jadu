<template>
  <div class="anser-create-section">
    <div class="create-form">
      <div class="create-header">
        <img :src="profileImg" alt="User Avatar" class="user-avatar" />
        <textarea
          v-model="answer.content"
          class="create-textarea"
          placeholder="답변을 작성하세요..."
          rows="3"
        ></textarea>
      </div>
      <div class="create-actions">
        <button @click="createAnswer" class="submit-btn">
          <i class="bi bi-send"></i>
          등록하기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAnswerStore } from "@/stores/answer";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { useUserStore } from "@/stores/user";
import { useLoginStore } from "@/stores/login";
import defaultProfileImg from "@/assets/image/default_profile.png";

const route = useRoute();
const router = useRouter();
const programId = route.params.programId;
const questionId = route.params.questionId;
const userStore = useUserStore();
const profileImg = computed(() => {
  return userStore.userProfileImg || defaultProfileImg;
});
const loginStore = useLoginStore();
const loginUserId = computed(() => loginStore.loginUserId);

const answerStore = useAnswerStore();
const answer = ref({
  content: "",
});

const token = computed(() => sessionStorage.getItem("access-token"));

const createAnswer = async (event) => {
  event.preventDefault();

  // 빈 응답일 경우, 추가 X
  if (!answer.value.content.trim()) {
    alert("답변을 작성해주세요!");
    return;
  }

  if (!token.value) {
    alert("로그인을 해야 답변를 등록할 수 있습니다.");
  } else {
    await answerStore.createAnswer(answer.value, programId, questionId);

    answer.value.content = "";
    // alert("답변이 등록되었습니다.");
    // router.push({ name: "answer" });
  }
};

onMounted(() => {
  userStore.getUserProfileInfo(loginUserId.value);
});
</script>

<style scoped>
.answer-create-section {
  max-width: 1000px;
  margin: 2rem 0;
  margin-left: 0;
}

.create-form {
  background: white;
  border-radius: 12px;
  padding-bottom: 1.5rem;
  /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); */
}

.create-header {
  display: flex;
  gap: 1.5rem;
  margin-bottom: 1rem;
}

.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  flex-shrink: 0;
}

.create-textarea {
  flex: 1;
  width: 80%;
  padding: 1rem;
  border: 1px solid #eee;
  border-radius: 8px;
  font-size: 1.1rem;
  resize: none;
  min-height: 100px;
}

.create-textarea:focus {
  outline: none;
  border-color: #c6e7ff;
}

.create-textarea::placeholder {
  color: #666;
}

.create-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
  padding-top: 0.5rem;
}

@media (max-width: 768px) {
  .review-create-section {
    padding: 1rem;
    margin: 1rem 0;
  }

  .create-header {
    gap: 1rem;
  }

  .user-avatar {
    width: 40px;
    height: 40px;
  }

  .create-textarea {
    font-size: 1rem;
    min-height: 80px;
  }

  .submit-btn {
    width: 100%;
    justify-content: center;
  }
}
</style>
