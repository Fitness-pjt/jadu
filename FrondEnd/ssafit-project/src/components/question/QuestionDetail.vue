<template>
  <div class="container my-4">
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

    <!-- 카드 섹션 -->
    <div class="">
      <div class="card-body">
        <!-- 상단 정보 (제목 및 유저 프로필) -->
        <div class="d-flex justify-content-between align-items-center">
          <p class="title">
            {{ questionStore.singleQuestion.title }}
          </p>
          <UserNameTag :user-id="questionStore.singleQuestion.userId" />
        </div>

        <!-- 질문 정보 -->
        <div class="d-flex justify-content-start align-items-center">
          <p class="mb-2" style="color: #666">
            {{ formattedDate(questionStore.singleQuestion.createdAt) }}
          </p>
        </div>

        <!-- 사진 -->
        <div v-if="questionStore.singleQuestion.questionFileName">
          <img :src="questionImgUrl" alt="등록한 사진" />
        </div>
        <!-- 질문 내용 -->
        <div class="mb-4">
          <p class="p-3 rounded">
            {{ questionStore.singleQuestion.content }}
          </p>
        </div>
        <hr />
        <!-- 버튼 액션 -->
        <div
          class="d-flex justify-content-end gap-2"
          v-if="loginUserId === questionStore.singleQuestion.userId"
        >
          <button
            class="btn btn-outline-navy d-flex align-items-center gap-1"
            @click="updateQuestion"
          >
            <i class="bi bi-pencil"></i> 수정
          </button>
          <button
            class="btn btn-outline-danger d-flex align-items-center gap-1"
            @click="deleteQuestion"
          >
            <i class="bi bi-trash"></i> 삭제
          </button>
        </div>
      </div>
    </div>
    <div class="my-4">
      <!-- 답변 -->
      <AnswerCreate />
      <AnswerList />
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useQuestionStore } from "@/stores/question";
import { formattedDate } from "@/utils/formattedDate";
import { computed, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import UserNameTag from "../common/UserNameTag.vue";
import { useLoginStore } from "@/stores/login";
import AnswerCreate from "./AnswerCreate.vue";
import AnswerList from "./AnswerList.vue";

const route = useRoute();
const questionId = route.params.questionId;
const programId = route.params.programId;

const questionStore = useQuestionStore();
const loginStore = useLoginStore();
const loginUserId = computed(() => loginStore.loginUserId);

const singleQuestion = computed(() => questionStore.singleQuestion);
watch(() => singleQuestion.value, { deep: true });

const baseURL = "https://attnnskybucket.s3.ap-northeast-2.amazonaws.com/";
const questionImgUrl = baseURL + singleQuestion.value.questionFileName;

onMounted(() => {
  questionStore.getQuestion(programId, questionId);
});

const deleteQuestion = () => {
  const confirm = window.confirm("질문을 삭제하시겠습니까?");
  if (confirm) {
    questionStore.deleteQuestion(programId, questionId);
  }
};

const updateQuestion = () => {
  router.push({
    name: "questionUpdate",
    params: { programId: programId, questionId: questionId },
  });
};

const goBack = () => {
  router.back();
};
</script>

<style scoped>
/* 컨테이너 크기 조정 */
.container {
  max-width: 800px;
}

/* 아이콘 크기 조정 */
.bi {
  font-size: 1.2rem;
}

.title {
  font-size: x-large;
}

.btn-outline-danger,
.btn-outline-navy {
  border: none;
}
</style>
