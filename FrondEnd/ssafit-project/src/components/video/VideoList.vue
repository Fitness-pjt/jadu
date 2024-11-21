<template>
  <div class="container-fluid">
    <!-- 메인 비디오 캐러셀 -->
    <swiper :slidesPerView="1" :spaceBetween="20" :navigation="true" :pagination="{ clickable: true }" :breakpoints="{
      '640': {
        slidesPerView: 2,
        spaceBetween: 20,
      },
      '968': {
        slidesPerView: 3,
        spaceBetween: 30,
      }
    }" :modules="[Navigation, Pagination]" class="mySwiper mb-5">
      <swiper-slide v-for="video in store.videoList" :key="video.id.videoId">
        <div class="video-card" :class="{ 'selected': isSelected(video) }">
          <div class="form-check">
            <input type="checkbox" class="form-check-input" :checked="isSelected(video)"
              @change="store.toggleVideoSelection(video)">
            <VideoListItem :video="video" />
          </div>
        </div>
      </swiper-slide>
    </swiper>

    <!-- 선택된 프로그램 영상 리스트 -->
    <div v-if="store.selectedVideos.length > 0" class="program-list">
      <div class="program-header">
        <h5 class="program-title">프로그램 영상 리스트</h5>
        <span class="video-count">{{ store.selectedVideos.length }}개의 영상</span>
      </div>
      <div class="selected-videos">
        <div v-for="(video, index) in store.selectedVideos" :key="video.id.videoId" class="selected-video-item">
          <span class="video-number">{{ index + 1 }}</span>
          <div class="video-info">
            <span class="video-title">{{ decodeHTMLEntities(video.snippet.title) }}</span>
          </div>
          <button type="button" class="btn btn-outline-danger btn-sm rounded-circle btn-remove"
            @click="store.toggleVideoSelection(video)" aria-label="영상 제거">
            <i class="bi bi-x"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import VideoListItem from "./VideoListItem.vue";
import { Swiper, SwiperSlide } from 'swiper/vue';
import { Navigation, Pagination } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';

const store = useVideoStore();

const isSelected = (video) => {
  return store.selectedVideos.some(v => v.id.videoId === video.id.videoId);
};
const decodeHTMLEntities = (text) => {
  const textArea = document.createElement('textarea');
  textArea.innerHTML = text;
  return textArea.value;
};
</script>

<style scoped>
/* 캐러셀 스타일 */
:deep(.swiper) {
  padding: 20px 50px;
}

:deep(.swiper-button-next),
:deep(.swiper-button-prev) {
  color: #0d6efd;
  background-color: white;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

:deep(.swiper-button-next)::after,
:deep(.swiper-button-prev)::after {
  font-size: 20px;
}

:deep(.swiper-pagination-bullet) {
  background: #0d6efd;
}

:deep(.swiper-button-disabled) {
  opacity: 0.35;
}

/* 비디오 카드 스타일 */
.video-card {
  background: white;
  border-radius: 12px;
  padding: 15px;
  transition: all 0.3s ease;
  border: 2px solid transparent;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  height: 100%;
}

.video-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.video-card.selected {
  background-color: #f8f9fe;
  border-color: #0d6efd;
}

.form-check {
  padding: 0;
  margin: 0;
  display: flex;
  gap: 15px;
}

.form-check-input {
  width: 24px;
  height: 24px;
  margin-top: 8px;
  cursor: pointer;
  border: 2px solid #dee2e6;
}

.form-check-input:checked {
  background-color: #0d6efd;
  border-color: #0d6efd;
}

/* 선택된 프로그램 영상 리스트 스타일 */
.program-list {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 40px;
}

.program-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 2px solid #e9ecef;
}

.program-title {
  color: #212529;
  margin: 0;
  font-weight: 600;
}

.video-count {
  color: #6c757d;
  font-size: 0.9rem;
}

.selected-videos {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.selected-video-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 12px 15px;
  background: #f8f9fa;
  border-radius: 8px;
  transition: background-color 0.2s ease;
}

.selected-video-item:hover {
  background: #e9ecef;
}

.video-number {
  background: #0d6efd;
  color: white;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.9rem;
  flex-shrink: 0;
}

.video-title {
  color: #495057;
  font-size: 0.95rem;
}

@media (max-width: 768px) {
  :deep(.swiper) {
    padding: 20px 30px;
  }

  .program-list {
    padding: 15px;
  }

  .selected-video-item {
    padding: 10px;
  }

  .video-title {
    font-size: 0.9rem;
  }
}

.selected-video-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 12px 15px;
  background: #f8f9fa;
  border-radius: 8px;
  transition: all 0.2s ease;
}
.video-info {
  flex: 1;
  min-width: 0;
}

.video-title {
  color: #495057;
  font-size: 0.95rem;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.btn-remove {
  width: 28px;
  height: 28px;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  transition: all 0.2s ease;
}

.btn-remove:hover {
  background-color: #dc3545;
  color: white;
}

.btn-remove i {
  line-height: 1;
}

/* 호버 시 배경색 변경 */
.selected-video-item:hover {
  background: #e9ecef;
}


@media (max-width: 768px) {
  .btn-remove {
    width: 24px;
    height: 24px;
    font-size: 1rem;
  }
}
</style>