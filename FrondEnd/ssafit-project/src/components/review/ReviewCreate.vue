<template>
  <div class="review-form-container">
    <h4 class="review-form-title">리뷰 작성</h4>
    <fieldset class="review-fieldset">
      <legend>리뷰 등록</legend>

      <div class="review-form-group">
        <label for="content" class="review-form-label">내용 : </label>
        <textarea
          id="content"
          cols="100"
          rows="10"
          v-model="review.content"
          class="review-form-textarea"
        ></textarea>
      </div>
      <div class="review-form-group">
        <button @click="createReview" class="review-form-button">등록</button>
      </div>
    </fieldset>
  </div>
</template>

<script setup>
import { useReviewStore } from "@/stores/review";
import { useRoute } from "vue-router";
import { ref } from "vue";

const route = useRoute();
const videoId = route.params.videoId;

const store = useReviewStore();

const review = ref({
  title: "",
  content: "",
});

const createReview = () => {
  store.createReview(review.value, videoId);
};
</script>

<style scoped>
.review-form-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.review-form-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 16px;
  text-align: center;
}

.review-fieldset {
  border: none;
  padding: 20px;
}

.review-form-group {
  margin-bottom: 16px;
}

.review-form-label {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
  display: block;
}

.review-form-input,
.review-form-textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.review-form-input:focus,
.review-form-textarea:focus {
  border-color: #007bff;
  outline: none;
}

.review-form-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s;
}

.review-form-button:hover {
  background-color: #0056b3;
}
</style>
