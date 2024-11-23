<template>
  <div class="question-form-container">
    <h4 class="question-form-title">질문 작성</h4>
    <fieldset class="question-fieldset">
      <legend>질문 등록</legend>
      <div class="question-form-group">
        <label for="title" class="question-form-label">제목 : </label>
        <input
          type="text"
          id="title"
          v-model="question.title"
          class="question-form-input"
        />
      </div>
      <div class="question-form-group">
        <label for="content" class="question-form-label">내용 : </label>
        <textarea
          id="content"
          cols="30"
          rows="10"
          v-model="question.content"
          class="question-form-textarea"
        ></textarea>
      </div>
      <div class="question-form-group">
        <button @click="createQuestion" class="question-form-button">
          등록
        </button>
      </div>
    </fieldset>
  </div>
</template>

<script setup>
import { useQuestionStore } from "@/stores/question";
import { useRoute } from "vue-router";
import { ref } from "vue";

const route = useRoute();
const programId = route.params.programId;

const store = useQuestionStore();

const question = ref({
  title: "",
  content: "",
});

const createQuestion = () => {
  store.createQuestion(question.value, programId);
};
</script>

<style scoped>
.question-form-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.question-form-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 16px;
  text-align: center;
}

.question-fieldset {
  border: none;
  padding: 20px;
}

.question-form-group {
  margin-bottom: 16px;
}

.question-form-label {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
  display: block;
}

.question-form-input,
.question-form-textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.question-form-input:focus,
.question-form-textarea:focus {
  border-color: #007bff;
  outline: none;
}

.question-form-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s;
}

.question-form-button:hover {
  background-color: #0056b3;
}
</style>
