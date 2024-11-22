<!-- ProgramListItem.vue -->
<template>
  <div class="card h-100">
    <!-- 썸네일 공간 -->
    <div class="card-img-top d-flex align-items-center justify-content-center bg-light" style="height: 200px;">
      <img v-if="program?.programImgPath" :src="program?.programImgPath" :alt="program?.title"
        class="profile-image" />
      <div v-else class="profile-placeholder">
        {{ userData?.userNickname?.[0] }}
      </div>
    </div>

    <div class="card-body">
      <!-- 헤더: 제목 및 작성자 -->
      <h5 class="card-title">{{ program.title }}</h5>
      <p class="card-subtitle mb-2 text-muted small">
        <UserNameTag :user-id="program.userId" />
      </p>

      <!-- 설명 -->
      <p class="card-text text-truncate mb-3">
        {{ program.description || '프로그램 설명이 없습니다.' }}
      </p>

      <!-- 프로그램 정보 -->
      <div class="mb-3">
        <!-- 난이도 -->
        <span class="badge me-2" :class="{
          'bg-success': program.level === 'BEGINNER',
          'bg-warning': program.level === 'INTERMEDIATE',
          'bg-danger': program.level === 'ADVANCED'
        }">
          {{ getLevelText(program.level) }}
        </span>

        <!-- 기간 -->
        <span class="badge bg-primary">
          {{ program.durationWeeks }}주 프로그램
        </span>
      </div>
    </div>

    <!-- 하단 정보 -->
    <div class="card-footer bg-transparent">
      <div class="d-flex justify-content-between align-items-center text-muted small">
        <span>{{ formatDate(program.createdAt) }}</span>
        <span>
          <i class="bi bi-camera-video me-1"></i>
          {{ program.videoCnt || 0 }} videos
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import UserNameTag from '../common/UserNameTag.vue';


const props = defineProps({
  program: {
    type: Object,
    required: true
  }
})

const getLevelText = (level) => {
  const levelMap = {
    'BEGINNER': '입문',
    'INTERMEDIATE': '중급',
    'ADVANCED': '고급'
  }
  return levelMap[level] || level
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return new Intl.DateTimeFormat('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  }).format(date)
}
</script>

<style scoped>
.card {
  transition: transform 0.2s ease-in-out;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-text {
  height: 48px;
  overflow: hidden;
}
</style>