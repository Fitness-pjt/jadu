<template>
  <div class="review-detail">
    <h2 class="review-detail__title">리뷰 상세보기</h2>

    <div class="review-detail__info">
      <p class="review-detail__field">
        <strong>제목:</strong> {{ reviewStore.singleReview.title }}
      </p>
      <p class="review-detail__field">
        <strong>작성자:</strong> {{ reviewStore.singleReview.writer }}
      </p>
      <p class="review-detail__field">
        <strong>등록일:</strong> {{ reviewStore.singleReview.regDate }}
      </p>
      <p class="review-detail__field">
        <strong>조회수:</strong> {{ reviewStore.singleReview.viewCnt }}
      </p>
    </div>

    <div class="review-detail__content">
      <h3>내용</h3>
      <p>{{ reviewStore.singleReview.content }}</p>
    </div>

    <div class="review-detail__actions">
      <button class="button button--danger" @click="deleteReview">삭제</button>
      <button class="button button--primary" @click="updateReview">수정</button>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useReviewStore } from "@/stores/review";
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const reviewId = route.params.reviewId;
const videoId = route.params.videoId;

const reviewStore = useReviewStore();

onMounted(() => {
  reviewStore.getReview(videoId, reviewId);
});

const deleteReview = () => {
  reviewStore.deleteReview(videoId, reviewId);
};

const updateReview = () => {
  router.push({
    name: "reviewUpdate",
    params: { videoId: videoId, reviewId: reviewId },
  });
};
</script>

<style scoped>
/* 기본적인 스타일 */
.review-detail {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.review-detail__title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}

.review-detail__info {
  margin-bottom: 20px;
}

.review-detail__field {
  font-size: 16px;
  margin: 5px 0;
}

.review-detail__field strong {
  color: #555;
}

.review-detail__content {
  margin: 20px 0;
  padding: 15px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.review-detail__content h3 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #333;
}

.review-detail__content p {
  font-size: 16px;
  line-height: 1.5;
  color: #666;
}

.review-detail__actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.button {
  padding: 10px 20px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.button--danger {
  background-color: #e74c3c;
  color: #fff;
}

.button--danger:hover {
  background-color: #c0392b;
}

.button--primary {
  background-color: #3498db;
  color: #fff;
}

.button--primary:hover {
  background-color: #2980b9;
}
</style>
