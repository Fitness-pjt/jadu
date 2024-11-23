<template>
  <div>
    <h2 class="review-list-title">리뷰 리스트</h2>
    <!-- 리뷰 리스트 -->
    <div v-if="reviewList.length !== 0" class="review-list">
      <div
        v-for="review in reviewList"
        :key="review.reviewId"
        class="card review-card mb-3"
      >
        <!-- 하나의 Review -->
        <div class="card-body d-flex justify-content-between">
          <!-- 작성자 -->
          <div class="user-avatar me-3">
            <UserNameTag :user-id="review.userId" />
          </div>
          <!-- 내용 -->
          <div class="review-content-container">
            <p
              v-if="!editingStates[review.reviewId]"
              class="mb-1 review-content"
            >
              {{ review.content }}
            </p>
            <textarea
              v-if="editingStates[review.reviewId]"
              v-model="review.content"
              class="form-control mb-1 review-content-input"
              rows="3"
            ></textarea>
            <small class="text-muted review-date">{{
              formattedDateTime(review.createdAt)
            }}</small>
          </div>
          <div v-if="review.userId === loginUserId" class="review-actions">
            <button
              v-if="!editingStates[review.reviewId]"
              @click="toggleEditReview(review)"
              class="action-button"
            >
              <i class="bi bi-pencil"></i>
            </button>
            <button
              v-if="editingStates[review.reviewId]"
              @click="saveUpdateReview(review)"
              class="action-button"
            >
              <i class="bi bi-save"></i>
            </button>
            <button @click="deleteReview(review)" class="action-button">
              <i class="bi bi-trash"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <h3 v-else class="text-muted">등록된 리뷰가 없습니다.</h3>

    <!-- 리뷰 등록 -->
    <div class="review-create-container mt-4">
      <ReviewCreate />
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
.review-list-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 2px solid #e1e1e1;
}

.review-card {
  background-color: #fff;
  border: 1px solid #e1e1e1;
  border-radius: 8px;
  padding: 12px;
  display: flex;
  align-items: flex-start;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.review-content-container {
  flex: 1;
}

.review-content {
  font-size: 14px;
  color: #333;
  margin-bottom: 8px;
}

.review-date {
  font-size: 12px;
  color: #777;
}

.review-actions {
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

.review-create-container {
  text-align: center;
}
</style>
