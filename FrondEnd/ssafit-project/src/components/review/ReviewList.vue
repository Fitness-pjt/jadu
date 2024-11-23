<template>
  <div>
    <div v-if="store.selectVideo">
      <h4>영상 상세보기</h4>
      <iframe
        width="560"
        height="315"
        :src="videoURL"
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        referrerpolicy="strict-origin-when-cross-origin"
        allowfullscreen
      ></iframe>
    </div>

    <h2 class="review-list-title">리뷰 리스트</h2>
    <table class="review-table" v-if="reviewStore.reviewList.length !== 0">
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
          v-for="review in reviewStore.reviewList"
          :key="review.id"
          class="review-row"
        >
          <td>{{ review.id }}</td>
          <td>
            <RouterLink
              :to="{ name: 'reviewDetail', params: { reviewId: review.id } }"
              class="review-link"
            >
              {{ review.title }}
            </RouterLink>
          </td>
          <td>{{ review.writer }}</td>
          <td>{{ review.viewCnt }}</td>
          <td>{{ review.regDate }}</td>
        </tr>
      </tbody>
    </table>
    <h3 v-if="reviewStore.reviewList.length === 0">등록된 리뷰가 없습니다.</h3>
    <div>
      <ReviewCreate />
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useReviewStore } from "@/stores/review";
import { useVideoStore } from "@/stores/video";
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import ReviewCreate from "./ReviewCreate.vue";

const route = useRoute();
const videoId = route.params.videoId;

const store = useVideoStore();
const reviewStore = useReviewStore();

const videoURL = computed(() => {
  const videoSrc = store.selectVideo.id.videoId;
  return `https://www.youtube.com/embed/${videoSrc}`;
});

onMounted(() => {
  reviewStore.getReviewList(videoId);
});

const token = computed(() => sessionStorage.getItem("access-token"));
// 리뷰 등록 버튼 클릭 핸들러
const handleReviewCreate = () => {
  if (!token.value) {
    alert("로그인을 해야 리뷰를 등록할 수 있습니다.");
  } else {
    router.push({ name: "reviewCreate" }); // 리뷰 등록 페이지로 이동
  }
};
</script>

<style scoped>
.review-list-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 16px;
}

.review-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 24px;
}

.review-table th,
.review-table td {
  padding: 12px;
  border: 1px solid #ddd;
}

.review-table thead tr {
  background-color: #f9f9f9;
}

.review-row {
  background-color: #fff;
  transition: background-color 0.2s;
}

.review-row:hover {
  background-color: #f1f1f1;
}

.review-link {
  color: #007bff;
  text-decoration: none;
}

.review-link:hover {
  text-decoration: underline;
}

.review-create-button-container {
  text-align: right;
}

.review-create-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 16px;
  font-size: 14px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.review-create-button:hover {
  background-color: #0056b3;
}
</style>
