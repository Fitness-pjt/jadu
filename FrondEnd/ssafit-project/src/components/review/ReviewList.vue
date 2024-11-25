<template>
  <div class="d-flex col justify-content-center">
    <div class="review-section">
      <!-- 리뷰 리스트 -->
      <div class="review-create-section">
        <ReviewCreate />
      </div>
      <div v-if="reviewList.length !== 0" class="review-list">
        <div
          v-for="review in reviewList"
          :key="review.reviewId"
          class="review-item"
        >
          <!-- 유저 프로필 -->
          <div class="review-avatar"></div>

          <div class="review-main">
            <div class="review-header">
              <div class="user-info">
                <UserNameTag :user-id="review.userId" />
                <span class="review-date">{{
                  formattedDateTime(review.createdAt)
                }}</span>
              </div>
            </div>
            <div class="review-body">
              <!-- 리뷰 내용 -->
              <div>
                <p
                  v-if="!editingStates[review.reviewId]"
                  class="review-content"
                >
                  {{ review.content }}
                </p>
                <div
                  v-if="editingStates[review.reviewId]"
                  class="edit-container"
                >
                  <textarea
                    v-model="review.content"
                    class="edit-textarea"
                    rows="3"
                  ></textarea>
                  <div class="edit-actions">
                    <button
                      @click="toggleEditReview(review)"
                      class="cancel-btn"
                    >
                      취소
                    </button>
                    <button @click="saveUpdateReview(review)" class="save-btn">
                      저장
                    </button>
                  </div>
                </div>
              </div>

              <!-- 액션 버튼 -->
              <div
                v-if="
                  review.userId === loginUserId &&
                  !editingStates[review.reviewId]
                "
                class="review-actions"
              >
                <button @click="toggleEditReview(review)" class="action-btn">
                  수정
                </button>
                <button @click="deleteReview(review)" class="action-btn">
                  삭제
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="empty-state">첫 번째 리뷰를 작성해보세요.</div>
    </div>
  </div>
</template>

<script setup>
import { useProgramStore } from "@/stores/program";
import { useReviewStore } from "@/stores/review";
import { formattedDateTime } from "@/utils/formattedDate";
import { computed, onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import ReviewCreate from "./ReviewCreate.vue";
import UserNameTag from "../common/UserNameTag.vue";
import { useLoginStore } from "@/stores/login";

const route = useRoute();
const programId = route.params.programId;

const loginUserStore = useLoginStore();
const loginUserId = computed(() => loginUserStore.loginUserId);

const reviewStore = useReviewStore();
const reviewList = computed(() => reviewStore.reviewList);
const editingStates = ref({}); // 현재 수정 상태인지 아닌지 확인

watch(
  () => reviewList.value,
  (newList) => {
    // console.log("Updated review list:", newList);
  },
  { deep: true }
);

// 리뷰 삭제하기
const deleteReview = (review) => {
  const confirm = window.confirm("리뷰를 삭제하시겠습니까?");
  if (confirm) {
    reviewStore.deleteReview(programId, review.reviewId);
  }
};

// 리뷰 수정하기
const toggleEditReview = (review) => {
  editingStates.value[review.reviewId] = !editingStates.value[review.reviewId];
};

// 리뷰 수정 저장하기
const saveUpdateReview = async (review) => {
  if (!review.content.trim()) {
    return; // 내용이 없으면 반환
  }

  // 서버에 수정된 내용 업데이트
  await reviewStore.updateReview(programId, review.reviewId, review.content);
  editingStates.value[review.reviewId] = false; // 수정 모드 종료
};

onMounted(() => {
  reviewStore.getReviewList(programId);
});
</script>
<style scoped>
.review-section {
  width: 1000px;
  padding-bottom: 2rem;
  margin-left: 0;
}

.review-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.review-item {
  display: flex;
  gap: 1.5rem;
}

.review-avatar {
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

.review-main {
  flex: 1;
  min-width: 0;
}

.review-header {
  margin-bottom: 0.5rem;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.review-date {
  font-size: 0.9rem;
  color: #666;
}

.review-content {
  font-size: 1.1rem;
  line-height: 1.6;
  color: #2c3e50;
  margin: 0.75rem 0;
  white-space: pre-wrap;
}

.review-actions {
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

.review-body {
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
  .review-section {
    padding: 1rem;
  }

  .review-item {
    gap: 1rem;
  }

  .avatar-circle {
    width: 40px;
    height: 40px;
  }

  .review-content {
    font-size: 1rem;
  }
}
</style>
