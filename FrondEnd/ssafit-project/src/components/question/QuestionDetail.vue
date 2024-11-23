<template>
  <div class="question-detail">
    <h2 class="question-detail__title">리뷰 상세보기</h2>

    <div class="question-detail__info">
      <p class="question-detail__field">
        <strong>제목:</strong> {{ questionStore.singleQuestion.title }}
      </p>
      <p class="question-detail__field">
        <strong>작성자:</strong> {{ questionStore.singleQuestion.writer }}
      </p>
      <p class="question-detail__field">
        <strong>등록일:</strong> {{ questionStore.singleQuestion.regDate }}
      </p>
      <p class="question-detail__field">
        <strong>조회수:</strong> {{ questionStore.singleQuestion.viewCnt }}
      </p>
    </div>

    <div class="question-detail__content">
      <h3>내용</h3>
      <p>{{ questionStore.singleQuestion.content }}</p>
    </div>

    <div class="question-detail__actions">
      <button class="button button--danger" @click="deleteQuestion">
        삭제
      </button>
      <button class="button button--primary" @click="updateQuestion">
        수정
      </button>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useQuestionStore } from "@/stores/question";
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const questionId = route.params.questionId;
const programId = route.params.programId;

const questionStore = useQuestionStore();

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
/* 기본적인 스타일 */
.question-detail {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.question-detail__title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}

.question-detail__info {
  margin-bottom: 20px;
}

.question-detail__field {
  font-size: 16px;
  margin: 5px 0;
}

.question-detail__field strong {
  color: #555;
}

.question-detail__content {
  margin: 20px 0;
  padding: 15px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.question-detail__content h3 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #333;
}

.question-detail__content p {
  font-size: 16px;
  line-height: 1.5;
  color: #666;
}

.question-detail__actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.button {
  padding: 10px 20px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.button--danger {
  background-color: #e74c3c;
  color: #fff;
}

.button--danger:hover {
  background-color: #c0392b;
}

.button--primary {
  background-color: #3498db;
  color: #fff;
}

.button--primary:hover {
  background-color: #2980b9;
}
</style>
