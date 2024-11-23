<template>
  <div class="profile-container">
    <div class="image-wrapper">
      <img
        v-if="userData?.profileImgPath"
        :src="userData?.profileImgPath"
        :alt="userData?.userNickname"
        class="profile-image"
      />
      <div v-else class="profile-placeholder">
        <img
          src="../../assets/image/default_profile.png"
          alt="기본 프로필 이미지"
          class="profile-image"
        />
      </div>
    </div>

    <span class="nickname">
      {{ userData?.userNickname }}
    </span>
  </div>
</template>

<script setup>
import { watch, onMounted, computed } from "vue";
import { useUserStore } from "@/stores/user";

const props = defineProps({
  userId: Number,
});

const userStore = useUserStore();

const userData = computed(() => userStore.getUserById(props.userId));
const userNickName = computed(() => userStore.userNickname);
let userProfileImg = computed(() => userStore.userProfileImg);

// console.log("userProfileImg :>> ", userProfileImg.value);

// 사용자 데이터를 가져오는 함수
const fetchUserProfile = (userId) => {
  if (userId) {
    userStore.getUserListProfileInfo(userId);
  }
};

// props.userId 변경 감지
watch(
  [() => props.userId], // 배열로 감시할 값들을 지정
  ([newUserId]) => {
    // 새로운 값들도 배열로 받음
    fetchUserProfile(newUserId);
  }
);

// 초기 데이터 로드
onMounted(() => {
  fetchUserProfile(props.userId);
});
</script>

<style scoped>
.profile-container {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px;

  background-color: transparent;
}

.image-wrapper {
  flex-shrink: 0; /* 이미지 크기 고정 */
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  /* background-color: transparent; /* Facebook 스타일 배경색 */
  /* border: 1px solid #e4e6eb; // 미세한 테두리 */
  /* box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);  */
}

.profile-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.2s; /* 호버 효과를 위한 트랜지션 */
}

.image-wrapper:hover .profile-image {
  transform: scale(1.05); /* 호버시 살짝 확대 */
}

/* 이미지가 없을 때의 플레이스홀더 */
.profile-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #e4e6eb;
  color: #65676b;
  font-weight: 500;
  font-size: 1.1rem;
  text-transform: uppercase;
}

.nickname {
  font-size: 0.9rem;
  font-weight: 600;
  color: #1877f2; /* Facebook 스타일 텍스트 컬러 */
  transition: color 0.2s;
}

.nickname:hover {
  color: #1877f2; /* Facebook 브랜드 컬러 */
  cursor: pointer;
}

/* 반응형 크기 조정 */
@media (min-width: 768px) {
  .image-wrapper {
    width: 44px;
    height: 44px;
  }

  .nickname {
    font-size: 1rem;
  }
}
</style>
