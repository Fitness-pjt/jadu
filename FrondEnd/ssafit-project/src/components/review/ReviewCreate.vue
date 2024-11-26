<template>
  <div class="review-create-section">
    <div class="create-form">
      <div class="create-header">
        <img :src="profileImg" alt="User Avatar" class="user-avatar" />
        <textarea
          v-model="review.content"
          class="create-textarea"
          placeholder="리뷰를 작성하세요..."
          rows="3"
        ></textarea>
      </div>
      <div class="create-actions">
        <button @click="createReview" class="submit-btn">
          <i class="bi bi-send"></i>
          등록하기
        </button>
      </div>
    </div>
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
.review-create-section {
  max-width: 1000px;
  margin: 2rem 0;
  margin-left: 0;
}

.create-form {
  background: white;
  border-radius: 12px;
  padding-bottom: 1.5rem;
  /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); */
}

.create-header {
  display: flex;
  gap: 1.5rem;
  margin-bottom: 1rem;
}

.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  flex-shrink: 0;
}

.create-textarea {
  flex: 1;
  width: 80%;
  padding: 1rem;
  border: 1px solid #eee;
  border-radius: 8px;
  font-size: 1.1rem;
  resize: none;
  min-height: 100px;
}

.create-textarea:focus {
  outline: none;
  border-color: #c6e7ff;
}

.create-textarea::placeholder {
  color: #666;
}

.create-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
  padding-top: 0.5rem;
}

@media (max-width: 768px) {
  .review-create-section {
    padding: 1rem;
    margin: 1rem 0;
  }

  .create-header {
    gap: 1rem;
  }

  .user-avatar {
    width: 40px;
    height: 40px;
  }

  .create-textarea {
    font-size: 1rem;
    min-height: 80px;
  }

  .submit-btn {
    width: 100%;
    justify-content: center;
  }
}
</style>
