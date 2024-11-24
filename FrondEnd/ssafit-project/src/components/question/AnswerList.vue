<template>
  <div>
    <!-- 답변 등록 -->
    <h2 class="answer-list-title">답변 리스트</h2>
    <!-- 답변 리스트 -->
    <div v-if="answerList.length !== 0" class="answer-list">
      <div
        v-for="answer in answerList"
        :key="answer.answerId"
        class="card answer-card mb-3"
      >
        <!-- 하나의 Answer -->
        <div class="card-body d-flex justify-content-between">
          <!-- 작성자 -->
          <div class="user-avatar me-3">
            <UserNameTag :user-id="answer.userId" />
          </div>
          <!-- 내용 -->
          <div class="answer-content-container">
            <p
              v-if="!editingStates[answer.answerId]"
              class="mb-1 answer-content"
            >
              {{ answer.content }}
            </p>
            <textarea
              v-if="editingStates[answer.answerId]"
              v-model="answer.content"
              class="form-control mb-1 answer-content-input"
              rows="3"
            ></textarea>
            <small class="text-muted answer-date">{{
              formattedDateTime(answer.createdAt)
            }}</small>
          </div>
          <div v-if="answer.userId === loginUserId" class="answer-actions">
            <button
              v-if="!editingStates[answer.answerId]"
              @click="toggleEditAnswer(answer)"
              class="action-button"
            >
              <i class="bi bi-pencil"></i>
            </button>
            <button
              v-if="editingStates[answer.answerId]"
              @click="saveUpdateAnswer(answer)"
              class="action-button"
            >
              <i class="bi bi-save"></i>
            </button>
            <button @click="deleteAnswer(answer)" class="action-button">
              <i class="bi bi-trash"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <h3 v-else class="text-muted">등록된 답변이 없습니다.</h3>
  </div>
</template>

<script setup>
import { useAnswerStore } from "@/stores/answer";
import { useLoginStore } from "@/stores/login";
import { formattedDateTime } from "@/utils/formattedDate";
import { computed, onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import UserNameTag from "../common/UserNameTag.vue";

const route = useRoute();
const programId = route.params.programId;
const questionId = route.params.questionId;

const loginUserStore = useLoginStore();
const loginUserId = computed(() => loginUserStore.loginUserId);

const answerStore = useAnswerStore();
const answerList = computed(() => answerStore.answerList);
const editingStates = ref({}); // 현재 수정 상태인지 아닌지 확인

watch(
  () => answerList.value,
  (newList) => {
    // console.log("Updated answer list:", newList);
  },
  { deep: true }
);

// 답변 삭제하기
const deleteAnswer = (answer) => {
  const confirm = window.confirm("답변를 삭제하시겠습니까?");
  if (confirm) {
    answerStore.deleteAnswer(programId, questionId, answer.answerId);
  }
};

// 답변 수정하기
const toggleEditAnswer = (answer) => {
  editingStates.value[answer.answerId] = !editingStates.value[answer.answerId];
};

// 답변 수정 저장하기
const saveUpdateAnswer = async (answer) => {
  if (!answer.content.trim()) {
    return; // 내용이 없으면 반환
  }

  // 서버에 수정된 내용 업데이트
  await answerStore.updateAnswer(
    programId,
    questionId,
    answer.answerId,
    answer
  );
  editingStates.value[answer.answerId] = false; // 수정 모드 종료
};

onMounted(async () => {
  await answerStore.getAnswerList(programId, questionId);
});
</script>

<style scoped>
.answer-list-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 2px solid #e1e1e1;
}

.answer-card {
  background-color: #fff;
  border: 1px solid #e1e1e1;
  border-radius: 8px;
  padding: 12px;
  display: flex;
  align-items: flex-start;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.answer-content-container {
  flex: 1;
}

.answer-content {
  font-size: 14px;
  color: #333;
  margin-bottom: 8px;
}

.answer-date {
  font-size: 12px;
  color: #777;
}

.answer-actions {
  display: flex;
  align-items: center;
}

.action-button {
  background: none;
  border: none;
  color: #888;
  cursor: pointer;
  font-size: 18px;
  margin-left: 10px;
  transition: color 0.3s;
}

.action-button:hover {
  color: #333;
}

.answer-create-container {
  text-align: center;
}
</style>
