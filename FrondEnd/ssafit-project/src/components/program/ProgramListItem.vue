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
        class="card-subtitle mb-4 text-muted small d-flex justify-content-between align-items-center"
      >
        <UserNameTag :user-id="program.userId" />
        <span>{{ formattedDate(program.createdAt) }}</span>
      </div>
      <h5 class="card-title">{{ program.title }}</h5>

      <!-- 설명 -->
      <p class="card-text mb-3">
        {{ program.description || "프로그램 설명이 없습니다." }}
      </p>

      <!-- 프로그램 정보 -->
      <div class="mb-3 d-flex justify-content-between align-items-center">
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
  transition: transform 0.2s ease-in-out;
  border-radius: 1rem !important;
  border: none;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  display: flex;
  flex-direction: column;
  justify-content: space-between; /* 내용 간격 균일하게 유지 */
  height: 100%; /* 모든 카드가 같은 높이 유지 */
}

.card-body {
  flex: 1; /* 본문 높이를 유연하게 조정 */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card:hover {
  transform: translateY(-5px);
}

/* media query */
/* 화면 크기가 768px 이하일 때 카드 크기 축소 */
@media (max-width: 768px) {
  .card {
    /* width: 90%; 카드 너비를 90%로 설정 */
    margin: 0.5rem; /* 여백을 줄여서 카드 크기 축소 */
  }

  /* 썸네일 크기 조정 */
  .thumbnail-container {
    width: 100%;
    height: auto; /* 썸네일 높이를 줄여서 작은 화면에 맞게 조정 */
    overflow: hidden;
  }

  .thumbnail-image {
    width: 100%;
    height: 100%; /* 썸네일 이미지 높이 조정 */
    object-fit: cover;
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
  /* height: 60px; */
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.5; /* 필요에 따라 라인 높이 설정 */
  max-height: 3rem; /* 두 줄의 높이에 맞는 최대 높이 설정 (line-height * 2) */
}

.card-title {
  padding-bottom: 0.5rem;
  font-weight: 600;
}
</style>
