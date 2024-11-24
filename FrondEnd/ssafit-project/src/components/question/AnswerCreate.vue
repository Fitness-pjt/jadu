<template>
  <div class="answer-form-container p-4 bg-light rounded shadow-sm">
    <div class="answer-header d-flex align-items-center mb-3">
      <!-- User Avatar -->
      <img :src="profileImg" alt="User Avatar" class="user-avatar me-3" />
      <h4 class="answer-form-title mb-0">답변 작성하기</h4>
    </div>
    <fieldset class="answer-fieldset border-0 p-0">
      <div class="answer-form-group mb-3">
        <textarea
          id="content"
          rows="4"
          v-model="answer.content"
          class="answer-form-textarea form-control"
          placeholder="답변을 작성해주세요..."
        ></textarea>
      </div>
      <div class="answer-form-actions d-flex">
        <button
          @click="createAnswer"
          class="answer-form-submit-button btn btn-primary"
        >
          <i class="bi bi-check-circle me-2"></i>등록
        </button>
      </div>
    </fieldset>
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
.answer-form-container {
  max-width: 600px;
  margin: 20px auto;
}

.answer-header {
  font-size: 18px;
}

.user-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.answer-form-title {
  font-weight: bold;
}

.answer-form-textarea {
  resize: none;
}

.answer-form-actions button {
  font-size: 14px;
  padding: 8px 16px;
}

.answer-form-submit-button {
  background-color: #007bff;
}

.answer-form-cancel-button {
  background-color: #f44336;
}

.answer-form-submit-button:hover {
  background-color: #0056b3;
}

.answer-form-cancel-button:hover {
  background-color: #d32f2f;
}
</style>
