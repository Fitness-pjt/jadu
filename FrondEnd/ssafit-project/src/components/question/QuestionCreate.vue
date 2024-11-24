<template>
  <div class="container mt-4">
    <div class="card shadow-sm">
      <div class="card-body">
        <h4 class="card-title text-center mb-4">질문 작성</h4>
        <form>
          <!-- 작성자 -->
          <div class="mb-3">
            <label for="writer" class="form-label">작성자:</label>
            <input
              type="text"
              id="writer"
              class="form-control"
              v-model="question.writer"
              readonly
            />
          </div>
          <!-- 제목 -->
          <div class="mb-3">
            <label for="title" class="form-label">제목:</label>
            <input
              type="text"
              id="title"
              class="form-control"
              v-model="question.title"
              placeholder="질문의 제목을 입력하세요."
            />
          </div>
          <!-- 내용 -->
          <div class="mb-3">
            <label for="content" class="form-label">내용:</label>
            <textarea
              id="content"
              class="form-control"
              rows="6"
              v-model="question.content"
              placeholder="질문의 내용을 입력하세요."
            ></textarea>
          </div>
          <!-- 등록 버튼 -->
          <div class="d-grid">
            <button
              type="button"
              class="btn btn-primary"
              @click="createQuestion"
            >
              등록
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useQuestionStore } from "@/stores/question";
import { useRoute } from "vue-router";
import { computed, onMounted, ref, watch } from "vue";
import { useUserStore } from "@/stores/user";
import { useLoginStore } from "@/stores/login";
import router from "@/router";

const route = useRoute();
const programId = route.params.programId;

const questionStore = useQuestionStore();
const loginStore = useLoginStore();

const userNickname = computed(() => loginStore.loginUserNickname);

watch(
  () => loginStore.value,
  (newList, oldList) => {
    console.log("newList", newList);
  },
  {
    deep: true,
  }
);

// console.log("userNickname :>> ", userNickname.value);

const question = ref({
  writer: userNickname,
  title: "",
  content: "",
});

const createQuestion = async () => {
  // 제목과 내용이 없으면 등록 되지 않음
  const { title, content } = question.value;

  if (!title.trim()) {
    alert("제목을 입력해 주세요.");
    return;
  }

  if (!content.trim()) {
    alert("내용을 입력해 주세요.");
    return;
  }

  await questionStore.createQuestion(question.value, programId);
  alert("질문 등록이 완료되었습니다.");
  router.replace({ name: "question" });
};

onMounted(() => {});
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
