<template>
  <div class="container mt-4">
    <h2 class="mb-4">질문 리스트</h2>
    <!-- 질문 리스트 테이블 -->
    <table
      class="table table-striped table-hover"
      v-if="questionStore.questionList.length !== 0"
    >
      <thead class="table-light">
        <tr>
          <th scope="col">번호</th>
          <th scope="col">제목</th>
          <th scope="col">작성자</th>
          <th scope="col">등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="question in questionStore.questionList"
          :key="question.questionId"
        >
          <td>{{ question.questionId }}</td>
          <td>
            <a
              href="#"
              class="text-primary text-decoration-none"
              @click.prevent="goToQuestionDetail(question)"
            >
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
      class="alert alert-info"
    >
      등록된 질문이 없습니다.
    </div>
    <!-- 질문 등록 버튼 -->
    <div class="text-end mt-3">
      <button class="btn btn-primary" @click="handleQuestionCreate">
        질문 등록하기
      </button>
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
a.text-primary {
  cursor: pointer;
}
</style>
