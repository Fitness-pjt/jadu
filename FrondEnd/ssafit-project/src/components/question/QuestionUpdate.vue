<template>
  <div class="container mt-4">
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

    <div class="question-form-card">
      <div class="card-body">
        <h3 class="form-title">질문 수정</h3>
        <form @submit.prevent="updateQuestion">
          <!-- 제목 -->
          <div class="form-group">
            <label for="title" class="form-label">제목:</label>
            <input
              type="text"
              id="title"
              class="form-input"
              placeholder="수정할 제목을 입력하세요"
              v-model="questionStore.singleQuestion.title"
            />
          </div>

          <!-- 내용 -->
          <div class="form-group">
            <label for="content" class="form-label">내용:</label>
            <textarea
              id="content"
              class="form-textarea"
              rows="8"
              placeholder="수정할 내용을 입력하세요"
              v-model="questionStore.singleQuestion.content"
            ></textarea>
          </div>

          <!-- 버튼 그룹 -->
          <div class="button-group">
            <button type="button" class="cancel-btn" @click="cancelEdit">
              취소
            </button>
            <button type="submit" class="submit-btn">
              <i class="bi bi-check-circle"></i>
              수정 완료
            </button>
          </div>
        </form>
      </div>
    </div>
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
.question-form-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  max-width: 800px;
  margin: 0 auto;
}

.form-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 2rem;
  text-align: center;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-label {
  display: block;
  font-weight: 500;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

.form-input,
.form-textarea {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #eee;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s ease;
}

.form-textarea {
  min-height: 200px;
  resize: vertical;
}

.form-input:focus,
.form-textarea:focus {
  outline: none;
  border-color: #c6e7ff;
  box-shadow: 0 0 0 2px rgba(198, 231, 255, 0.2);
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
  padding-top: 1.5rem;
  border-top: 1px solid #eee;
}

.cancel-btn,
.submit-btn {
  padding: 0.75rem 1.25rem;
  border-radius: 10px;
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.cancel-btn {
  background: none;
  border: 1px solid #eee;
  color: #666;
}

.submit-btn {
  background: #c6e7ff;
  border: none;
  color: #2c3e50;
}

.cancel-btn:hover {
  background: #f8f9fa;
}

.submit-btn:hover {
  opacity: 0.9;
}

@media (max-width: 768px) {
  .question-form-card {
    padding: 1.5rem;
    margin: 1rem;
  }

  .button-group {
    flex-direction: column-reverse;
  }

  .cancel-btn,
  .submit-btn {
    width: 100%;
    justify-content: center;
  }
}
</style>
