<template>
  <div class="container my-4">
    <div class="card shadow-sm">
      <div class="card-body">
        <!-- 상단 정보 (제목 및 유저 프로필) -->
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h2 class="card-title">질문 상세보기</h2>
          <!-- UserNameTag 컴포넌트 -->
          <UserNameTag :user-id="questionStore.singleQuestion.userId" />
        </div>

        <!-- 질문 정보 -->
        <div class="mb-3">
          <p class="mb-1">
            <strong>제목:</strong> {{ questionStore.singleQuestion.title }}
          </p>
          <p class="mb-1">
            <strong>등록일:</strong>
            {{ formattedDate(questionStore.singleQuestion.createdAt) }}
          </p>
        </div>

        <!-- 질문 내용 -->
        <div class="mb-4">
          <h3 class="h5">내용</h3>
          <p class="border p-3 rounded bg-light">
            {{ questionStore.singleQuestion.content }}
          </p>
        </div>

        <!-- 버튼 액션 -->
        <div
          class="d-flex justify-content-end gap-2"
          v-if="loginUserId === questionStore.singleQuestion.userId"
        >
          <button class="btn btn-danger" @click="deleteQuestion">삭제</button>
          <button class="btn btn-primary" @click="updateQuestion">수정</button>
        </div>
      </div>
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
  questionStore.deleteQuestion(programId, questionId);
};

const updateQuestion = () => {
  router.push({
    name: "questionUpdate",
    params: { programId: programId, questionId: questionId },
  });
};
</script>

<style scoped>
/* 컨테이너 크기 조정 */
.container {
  max-width: 800px;
}
</style>
