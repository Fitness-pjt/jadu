<!-- ProgramListItem.vue -->
<template>
  <div class="card">
    <!-- 썸네일 공간 -->
    <div class="thumbnail-container">
      <img
        v-if="program?.programImgPath"
        :src="program?.programImgPath"
        :alt="program?.title"
        class="thumbnail-image"
      />
      <div v-else class="profile-placeholder">
        {{ userData?.userNickname?.[0] }}
      </div>
    </div>

    <!-- 하드 설명 공간 -->
    <div class="card-body">
      <!-- 헤더: 제목 및 작성자 -->
      <div
        class="card-subtitle mb-1 text-muted small d-flex justify-content-between align-items-center"
      >
        <UserNameTag :user-id="program.userId" />
        <span>{{ formattedDate(program.createdAt) }}</span>
      </div>
      <h5 class="card-title">{{ program.title }}</h5>

     

      <!-- 프로그램 정보 -->
      <div class="mb-3 mt-1 d-flex justify-content-between align-items-center">
        <div>
          <!-- 난이도 -->
          <span
            class="badge me-2"
            :class="{
              'bg-green': program.level === 'BEGINNER',
              'bg-orange': program.level === 'INTERMEDIATE',
              'bg-red': program.level === 'ADVANCED',
            }"
          >
            {{ getLevelText(program.level) }}
          </span>

          <!-- 기간 -->
          <span class="badge bg-week">
            {{ program.durationWeeks }}주 프로그램
          </span>
        </div>
        <span>
          <i class="bi bi-camera-video me-1"></i>
          {{ program.videoCnt || 0 }} videos
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { formattedDate } from "@/utils/formattedDate";
import UserNameTag from "../common/UserNameTag.vue";

const props = defineProps({
  program: {
    type: Object,
    required: true,
  },
});

const getLevelText = (level) => {
  const levelMap = {
    BEGINNER: "입문",
    INTERMEDIATE: "중급",
    ADVANCED: "고급",
  };
  return levelMap[level] || level;
};
</script>

<style scoped>
.card {
  height: 400px; /* 카드 전체 높이 고정 */
  transition: transform 0.2s ease-in-out;
  border-radius: 1rem !important;
  border: none;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-body {
  padding: 1rem;
  display: flex;
  flex-direction: column;
  height: 200px; /* 카드 본문 높이 고정 */
}

.card:hover {
  transform: translateY(-5px);
}

/* media query */
/* 화면 크기가 768px 이하일 때 카드 크기 축소 */
@media (max-width: 768px) {
  .card {
    height: 350px;
  }
  
 
  .card-body {
    height: 200px;
  }
  /* 썸네일 크기 조정 */
  
.thumbnail-container {
  height: 150px; /* 썸네일 높이 고정 */
  border-top-left-radius: 1rem !important;
  border-top-right-radius: 1rem !important;
  position: relative;
  width: 100%;
  overflow: hidden;
  background-color: #f8f9fa;
}

.thumbnail-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

  .card-text {
    -webkit-line-clamp: 1; /* 텍스트가 1줄만 보이도록 수정 */
  }
}

/* 화면 크기가 576px 이하일 때 카드 크기 더욱 축소 */
@media (max-width: 576px) {
  .card {
    /* width: 90%; */
    margin: 0.25rem; /* 카드 간의 여백 줄이기 */
  }

  /* 썸네일 크기 추가 축소 */
  .thumbnail-container {
    height: auto; /* 썸네일 크기 더 축소 */
  }

  .card-text {
    -webkit-line-clamp: 1; /* 텍스트가 1줄만 보이도록 수정 */
  }
}

/* 썸네일 관련 스타일 수정 */
.thumbnail-container {
  border-top-left-radius: 1rem !important;
  border-top-right-radius: 1rem !important;
  position: relative;
  width: 100%;
  height: auto;
  overflow: hidden;
  background-color: #f8f9fa;
}

.thumbnail-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  /* 이미지 비율 유지하면서 컨테이너 채우기 */
  object-position: center;
  /* 이미지 중앙 정렬 */
}

.profile-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  color: #6c757d;
  background-color: #e9ecef;
}


.card-text {
  flex: 1;
  margin-bottom: 1rem;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 설명 2줄로 제한 */
  -webkit-box-orient: vertical;
  line-height: 1.5;
}
.card-title {
  font-size: 1.25rem;
  margin-bottom: 0.5rem;
  line-height: 1.4;
  height: 2.8rem; /* 제목 높이 제한 (2줄) */
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
