<template>
  <div>
    <!-- 답변 리스트 -->
    <div v-if="answerList.length !== 0" class="answer-list">
      <div
        v-for="answer in answerList"
        :key="answer.answerId"
        class="answer-item"
      >
        <!-- 유저 프로필 -->
        <div class="answer-avatar"></div>

        <div class="answer-main">
          <div class="answer-header">
            <div class="user-info">
              <UserNameTag :user-id="answer.userId" />
              <span class="answer-date">{{
                formattedDateTime(answer.createdAt)
              }}</span>
            </div>
          </div>
          <div class="answer-body">
            <!-- 답변 내용 -->
            <div>
              <p v-if="!editingStates[answer.answerId]" class="answer-content">
                {{ answer.content }}
              </p>
              <div v-if="editingStates[answer.answerId]" class="edit-container">
                <textarea
                  v-model="answer.content"
                  class="edit-textarea"
                  rows="3"
                ></textarea>
                <div class="edit-actions">
                  <button @click="toggleEditAnswer(answer)" class="cancel-btn">
                    취소
                  </button>
                  <button @click="saveUpdateAnswer(answer)" class="save-btn">
                    저장
                  </button>
                </div>
              </div>
            </div>

            <!-- 액션 버튼 -->
            <div
              v-if="
                answer.userId === loginUserId && !editingStates[answer.answerId]
              "
              class="answer-actions"
            >
              <button @click="toggleEditAnswer(answer)" class="action-btn">
                수정
              </button>
              <button @click="deleteAnswer(answer)" class="action-btn">
                삭제
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="empty-state">첫 번째 답변을 달아주세요.</div>
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
  (newList) => {},
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
.answer-section {
  width: 1000px;
  padding-bottom: 2rem;
  margin-left: 0;
}

.answer-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.answer-item {
  display: flex;
  gap: 1.5rem;
}

.answer-avatar {
  flex-shrink: 0;
  width: 48px;
  height: 48px;
}

.avatar-circle {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background-color: #eee;
}

.answer-main {
  flex: 1;
  min-width: 0;
}

.answer-header {
  margin-bottom: 0.5rem;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.answer-date {
  font-size: 0.9rem;
  color: #666;
}

.answer-content {
  font-size: 1.1rem;
  line-height: 1.6;
  color: #2c3e50;
  margin: 0.75rem 0;
  white-space: pre-wrap;
}

.answer-actions {
  margin-top: 0.75rem;
  display: flex;
  gap: 1rem;

  justify-content: flex-end;
}

.action-btn {
  background: none;
  border: none;
  color: #666;
  font-size: 0.95rem;
  font-weight: 500;
  padding: 0;
  cursor: pointer;
}

.action-btn:hover {
  color: #2c3e50;
  font-weight: 600;
}

.answer-body {
  width: 100%;
  padding-left: 4rem;
  padding-right: 4rem;
}

/* 수정 모드 스타일 */
.edit-container {
  width: 100%;
}

.edit-textarea {
  width: 100%;
  padding: 1rem;
  border: 1px solid #eee;
  border-radius: 8px;
  font-size: 1.1rem;
  margin-bottom: 0.75rem;
  resize: none;
}

.edit-textarea:focus {
  outline: none;
  border-color: #c6e7ff;
}

.edit-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
}

.cancel-btn,
.save-btn {
  padding: 0.75rem 1.25rem;
  border-radius: 10px;
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
}

.cancel-btn {
  background: none;
  border: none;
  color: #666;
}

.save-btn {
  background: #c6e7ff;
  border: none;
  color: #2c3e50;
}

.cancel-btn:hover {
  background: #f8f9fa;
}

.save-btn:hover {
  opacity: 0.9;
}

.empty-state {
  text-align: center;
  padding: 2rem;
  color: #666;
  font-size: 1.2rem;
}

@media (max-width: 768px) {
  .answer-section {
    padding: 1rem;
  }

  .answer-item {
    gap: 1rem;
  }

  .avatar-circle {
    width: 40px;
    height: 40px;
  }

  .answer-content {
    font-size: 1rem;
  }
}
</style>
