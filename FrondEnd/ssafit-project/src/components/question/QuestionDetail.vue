<template>
  <div class="container my-4">
    <!-- 뒤로가기 버튼 -->
    <div class="d-flex align-items-center mb-4">
      <button
        class="btn btn-link text-decoration-none d-flex align-items-center gap-1"
        @click="goBack"
      >
        <i class="bi bi-arrow-left"></i>
        <span>뒤로가기</span>
      </button>
    </div>

    <!-- 카드 섹션 -->
    <div class="card shadow">
      <div class="card-body">
        <!-- 상단 정보 (제목 및 유저 프로필) -->
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2 class="card-title mb-0">질문 상세보기</h2>
          <UserNameTag :user-id="questionStore.singleQuestion.userId" />
        </div>

        <!-- 질문 정보 -->
        <div class="mb-4">
          <p class="mb-2">
            <strong>제목:</strong> {{ questionStore.singleQuestion.title }}
          </p>
          <p class="mb-2">
            <strong>등록일:</strong>
            {{ formattedDate(questionStore.singleQuestion.createdAt) }}
          </p>
        </div>

        <!-- 질문 내용 -->
        <div class="mb-4">
          <h3 class="h5 mb-3">내용</h3>
          <p class="border p-3 rounded bg-light">
            {{ questionStore.singleQuestion.content }}
          </p>
        </div>

        <!-- 버튼 액션 -->
        <div
          class="d-flex justify-content-end gap-2"
          v-if="loginUserId === questionStore.singleQuestion.userId"
        >
          <button
            class="btn btn-danger d-flex align-items-center gap-1"
            @click="deleteQuestion"
          >
            <i class="bi bi-trash"></i> 삭제
          </button>
          <button
            class="btn btn-primary d-flex align-items-center gap-1"
            @click="updateQuestion"
          >
            <i class="bi bi-pencil"></i> 수정
          </button>
        </div>
      </div>
    </div>
    <div>
      <!-- 답변 -->
      <AnswerCreate />
      <AnswerList />
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useQuestionStore } from "@/stores/question";
import { formattedDate } from "@/utils/formattedDate";
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import UserNameTag from "../common/UserNameTag.vue";
import { useLoginStore } from "@/stores/login";
import AnswerCreate from "./AnswerCreate.vue";
import AnswerList from "./AnswerList.vue";

const route = useRoute();
const questionId = route.params.questionId;
const programId = route.params.programId;

const questionStore = useQuestionStore();
const loginStore = useLoginStore();
const loginUserId = computed(() => loginStore.loginUserId);

onMounted(() => {
  questionStore.getQuestion(programId, questionId);
});

const deleteQuestion = () => {
  const confirm = window.confirm("질문을 삭제하시겠습니까?");
  if (confirm) {
    questionStore.deleteQuestion(programId, questionId);
  }
};

const updateQuestion = () => {
  router.push({
    name: "questionUpdate",
    params: { programId: programId, questionId: questionId },
  });
};

const goBack = () => {
  router.back();
};
</script>

<style scoped>
/* 컨테이너 크기 조정 */
.container {
  max-width: 800px;
}

/* 아이콘 크기 조정 */
.bi {
  font-size: 1.2rem;
}
</style>
