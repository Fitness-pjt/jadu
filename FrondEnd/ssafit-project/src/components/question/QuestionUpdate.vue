<template>
  <div class="container my-5">
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

    <!-- 페이지 타이틀 -->
    <h2 class="text-center mb-4">질문 수정</h2>

    <!-- 폼 시작 -->
    <form
      class="p-4 border rounded bg-light shadow-sm"
      @submit.prevent="updateQuestion"
    >
      <!-- 제목 입력 -->
      <div class="mb-3">
        <label for="title" class="form-label fw-bold">제목</label>
        <input
          type="text"
          id="title"
          class="form-control"
          placeholder="수정할 제목을 입력하세요"
          v-model="questionStore.singleQuestion.title"
        />
      </div>

      <!-- 내용 입력 -->
      <div class="mb-4">
        <label for="content" class="form-label fw-bold">내용</label>
        <textarea
          id="content"
          class="form-control"
          rows="5"
          placeholder="수정할 내용을 입력하세요"
          v-model="questionStore.singleQuestion.content"
        ></textarea>
      </div>

      <!-- 액션 버튼 -->
      <div class="d-flex justify-content-center gap-3">
        <button
          type="submit"
          class="btn btn-primary d-flex align-items-center gap-1"
        >
          <i class="bi bi-check-circle"></i>
          수정 완료
        </button>
        <button
          type="button"
          class="btn btn-secondary d-flex align-items-center gap-1"
          @click="cancelEdit"
        >
          <i class="bi bi-x-circle"></i>
          취소
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import router from "@/router";
import { useQuestionStore } from "@/stores/question";
import { computed, watch } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const programId = route.params.programId;

const questionStore = useQuestionStore();
const questionList = computed(() => questionStore.singleQuestion);

watch(() => questionList.value, { deep: true });

const updateQuestion = () => {
  questionStore.updateQuestion(programId);
  alert("질문 수정이 완료되었습니다.");
  router.replace({
    name: "questionDetail",
    params: {
      programId: programId,
      questionId: questionList.value.questionId,
    },
  });
};

const cancelEdit = () => {
  router.replace({
    name: "questionDetail",
    params: {
      programId: programId,
      questionId: questionList.value.questionId,
    },
  });
};

const goBack = () => {
  router.back();
};
</script>

<style scoped>
/* 페이지 컨테이너 */
.container {
  max-width: 700px;
  background-color: #fff;
  border-radius: 8px;
}

/* 폼 스타일 */
.form-control {
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

textarea.form-control {
  resize: vertical;
}

button {
  font-size: 16px;
}

/* 버튼 hover 효과 */
.btn-primary:hover {
  background-color: #2874a6;
  border-color: #2874a6;
}

.btn-secondary:hover {
  background-color: #bbb;
  border-color: #bbb;
}
</style>
