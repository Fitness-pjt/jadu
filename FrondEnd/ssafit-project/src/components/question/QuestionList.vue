<template>
  <div class="container mt-4">
    <div class="">
      <div class="card-header text-black text-center">
        <h3 class="mb-4">질문 게시판</h3>
      </div>
      <div class="d-flex justify-content-center">
        <div class="card-body">
          <!-- 질문 리스트 테이블 -->
          <table
            class="table table-hover align-middle"
            v-if="questionStore.questionList.length !== 0"
          >
            <thead class="table-light">
              <tr>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">등록일</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="question in questionStore.questionList"
                :key="question.questionId"
                class="question-row"
                @click="goToQuestionDetail(question)"
              >
                <td>
                  <a @click.prevent>
                    {{ question.title }}
                  </a>
                </td>
                <td>{{ question.writer }}</td>
                <td>{{ formattedDate(question.createdAt) }}</td>
              </tr>
            </tbody>
          </table>
          <!-- 질문이 없을 때 메시지 -->
          <div
            v-if="questionStore.questionList.length === 0"
            class="empty-state"
          >
            등록된 질문이 없습니다.
          </div>
          <!-- 질문 등록 버튼 -->
          <div class="create-actions">
            <button @click="handleQuestionCreate" class="submit-btn">
              <i class="bi bi-send"></i>
              질문 등록하기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useQuestionStore } from "@/stores/question";
import { computed, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import { formattedDate } from "@/utils/formattedDate";

const route = useRoute();
const programId = route.params.programId;

const questionStore = useQuestionStore();
const questionList = computed(() => questionStore.questionList);
watch(
  () => {
    questionList.value;
  },
  { deep: true }
);

onMounted(() => {
  questionStore.getQuestionList(programId);
});

const token = computed(() => sessionStorage.getItem("access-token"));

// 질문 등록 버튼 클릭 핸들러
const handleQuestionCreate = () => {
  if (!token.value) {
    const confirm = window.confirm(
      "로그인을 해야 질문 작성이 가능합니다. 로그인 페이지로 이동하시겠습니까?"
    );
    if (confirm) {
      router.push({ name: "login" }); // 질문 등록 페이지로 이동
    }
  } else {
    router.push({ name: "questionCreate" }); // 질문 등록 페이지로 이동
  }
};

const goToQuestionDetail = (question) => {
  router.push({
    name: "questionDetail",
    params: { questionId: question.questionId },
  });
};
</script>

<style scoped>
/* 필요할 경우 커스텀 스타일 추가 */

.empty-state {
  text-align: center;
  padding: 2rem;
  color: #666;
  font-size: 1.2rem;
}

.create-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
  padding-top: 0.5rem;
}

/* 질문 테이블 줄 스타일 */
.question-row:hover {
  background-color: #fefefe;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

/* 링크 스타일 */
a {
  transition: color 0.2s ease;
  color: black;
  text-decoration: none;
}

/* 제목 강조 */
.card-header {
  font-size: 1rem;
  font-weight: 700;
}

.card-body {
  max-width: 1000px;
}
</style>
