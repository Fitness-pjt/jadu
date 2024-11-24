<template>
  <div class="review-form-container p-4 bg-light rounded shadow-sm">
    <div class="review-header d-flex align-items-center mb-3">
      <!-- User Avatar -->
      <img :src="profileImg" alt="User Avatar" class="user-avatar me-3" />
      <h4 class="review-form-title mb-0">리뷰 작성하기</h4>
    </div>
    <fieldset class="review-fieldset border-0 p-0">
      <div class="review-form-group mb-3">
        <textarea
          id="content"
          rows="4"
          v-model="review.content"
          class="review-form-textarea form-control"
          placeholder="댓글을 작성하세요..."
        ></textarea>
      </div>
      <div class="review-form-actions d-flex">
        <button
          @click="createReview"
          class="review-form-submit-button btn btn-primary"
        >
          <i class="bi bi-check-circle me-2"></i>등록
        </button>
      </div>
    </fieldset>
  </div>
</template>

<script setup>
import { useReviewStore } from "@/stores/review";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { useUserStore } from "@/stores/user";
import { useLoginStore } from "@/stores/login";
import defaultProfileImg from "@/assets/image/default_profile.png";

const route = useRoute();
const router = useRouter();
const programId = route.params.programId;
const userStore = useUserStore();
const profileImg = computed(() => {
  return userStore.userProfileImg || defaultProfileImg;
});

console.log("profileImg", profileImg.value);

const loginStore = useLoginStore();
const loginUserId = computed(() => loginStore.loginUserId);

const reviewStore = useReviewStore();
const review = ref({
  content: "",
});

const token = computed(() => sessionStorage.getItem("access-token"));

const createReview = async () => {
  if (!review.value.content.trim()) {
    alert("리뷰를 작성해주세요.");
    return;
  }

  if (!token.value) {
    alert("로그인을 해야 리뷰를 등록할 수 있습니다.");
  } else {
    await reviewStore.createReview(review.value, programId);
    review.value.content = "";
    // alert("리뷰가 등록되었습니다.");
    // router.push({ name: "review" });
    reviewStore.getReviewList(programId);
  }
};

onMounted(() => {
  userStore.getUserProfileInfo(loginUserId.value);
});
</script>

<style scoped>
.review-form-container {
  max-width: 600px;
  margin: 20px auto;
}

.review-header {
  font-size: 18px;
}

.user-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.review-form-title {
  font-weight: bold;
}

.review-form-textarea {
  resize: none;
}

.review-form-actions button {
  font-size: 14px;
  padding: 8px 16px;
}

.review-form-submit-button {
  background-color: #007bff;
}

.review-form-cancel-button {
  background-color: #f44336;
}

.review-form-submit-button:hover {
  background-color: #0056b3;
}

.review-form-cancel-button:hover {
  background-color: #d32f2f;
}
</style>
