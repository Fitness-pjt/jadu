<template>
  <div>
    <h2 class="question-list-title">질문 리스트</h2>
    <table
      class="question-table"
      v-if="questionStore.questionList.length !== 0"
    >
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>조회수</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="question in questionStore.questionList"
          :key="question.id"
          class="question-row"
        >
          <td>{{ question.id }}</td>
          <td>
            <RouterLink
              :to="{
                name: 'questionDetail',
                params: { questionId: question.id },
              }"
              class="question-link"
            >
              {{ question.title }}
            </RouterLink>
          </td>
          <td>{{ question.writer }}</td>
          <td>{{ question.viewCnt }}</td>
          <td>{{ question.regDate }}</td>
        </tr>
      </tbody>
    </table>
    <h3 v-if="questionStore.questionList.length === 0">
      등록된 질문이 없습니다.
    </h3>
    <!-- 질문 등록 버튼 -->
    <div class="question-create-button-container">
      <button class="question-create-button" @click="handleQuestionCreate">
        질문 등록하기
      </button>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useQuestionStore } from "@/stores/question";
import { useProgramStore } from "@/stores/program";
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const programId = route.params.programId;

const store = useProgramStore();
const questionStore = useQuestionStore();

const programURL = computed(() => {
  const programSrc = store.selectProgram.id.programId;
  return `https://www.youtube.com/embed/${programSrc}`;
});

onMounted(() => {
  questionStore.getQuestionList(programId);
});

const token = computed(() => sessionStorage.getItem("access-token"));
// 리뷰 등록 버튼 클릭 핸들러
const handleQuestionCreate = () => {
  if (!token.value) {
    alert("로그인을 해야 리뷰를 등록할 수 있습니다.");
  } else {
    router.push({ name: "questionCreate" }); // 리뷰 등록 페이지로 이동
  }
};
</script>

<style scoped>
.question-list-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 16px;
}

.question-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 24px;
}

.question-table th,
.question-table td {
  padding: 12px;
  border: 1px solid #ddd;
}

.question-table thead tr {
  background-color: #f9f9f9;
}

.question-row {
  background-color: #fff;
  transition: background-color 0.2s;
}

.question-row:hover {
  background-color: #f1f1f1;
}

.question-link {
  color: #007bff;
  text-decoration: none;
}

.question-link:hover {
  text-decoration: underline;
}

.question-create-button-container {
  text-align: right;
}

.question-create-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 16px;
  font-size: 14px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.question-create-button:hover {
  background-color: #0056b3;
}
</style>
