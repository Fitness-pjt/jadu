<template>
  <div class="question-update">
    <h2 class="page-title">리뷰 수정</h2>

    <form class="question-form">
      <div class="form-group">
        <label for="title" class="form-label">제목</label>
        <input
          type="text"
          id="title"
          class="form-input"
          v-model="questionStore.singleQuestion.title"
        />
      </div>

      <div class="form-group">
        <label for="content" class="form-label">내용</label>
        <textarea
          id="content"
          class="form-input"
          rows="5"
          v-model="questionStore.singleQuestion.content"
        ></textarea>
      </div>

      <div class="form-actions">
        <button type="submit" class="submit-button" @click="updateQuestion">
          수정 완료
        </button>
        <button type="button" class="cancel-button">취소</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useQuestionStore } from "@/stores/question";
import { useRoute } from "vue-router";
const route = useRoute();
const programId = route.params.programId;

const questionStore = useQuestionStore();

const updateQuestion = () => {
  questionStore.updateQuestion(programId);
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.question-update {
  max-width: 600px;
  margin: 30px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}

/* 폼 그룹 스타일 */
.form-group {
  margin-bottom: 15px;
}

.form-label {
  font-size: 16px;
  color: #555;
  margin-bottom: 5px;
  display: block;
}

.form-input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

textarea.form-input {
  resize: vertical;
  min-height: 100px;
}

/* 버튼 스타일 */
.form-actions {
  display: flex;
  gap: 15px;
  justify-content: center;
}

.submit-button,
.cancel-button {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button {
  background-color: #3498db;
  color: white;
}

.submit-button:hover {
  background-color: #2980b9;
}

.cancel-button {
  background-color: #e0e0e0;
  color: #333;
}

.cancel-button:hover {
  background-color: #d5d5d5;
}
</style>
