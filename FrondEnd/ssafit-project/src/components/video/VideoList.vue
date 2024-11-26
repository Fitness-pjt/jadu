<!-- VideoList.vue -->
<!-- VideoList.vue -->
<template>
  <div class="video-list-container">
    <!-- 선택된 비디오 리스트 -->
    <TransitionGroup
      name="list"
      tag="div"
      class="selected-videos-container"
      v-if="store.selectedVideos.length > 0"
    >
      <div class="selected-header">
        <h3>선택된 운동 영상</h3>
        <span class="video-count">{{ store.selectedVideos.length }}개</span>
      </div>

      <div
        v-for="(video, index) in store.selectedVideos"
        :key="video.id"
        class="selected-video-item"
      >
        <div class="video-number">{{ index + 1 }}</div>
        <div class="video-info">
          <h4>{{ decodeHTMLEntities(video.snippet.title) }}</h4>
          <p>{{ video.snippet.channelTitle }}</p>
        </div>
        <button
          class="remove-btn"
          @click="store.toggleVideoSelection(video)"
          aria-label="영상 제거"
        >
          <svg
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
          >
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>
    </TransitionGroup>

    <!-- 스와이퍼 비디오 리스트 -->
    <div class="swiper-section">
      <swiper
        :slides-per-view="1.2"
        :space-between="20"
        :navigation="true"
        :pagination="{ clickable: true }"
        :breakpoints="swiperBreakpoints"
        :modules="[Navigation, Pagination]"
        class="video-swiper"
      >
        <swiper-slide
          v-for="video in store.videoList"
          :key="video.id"
          class="video-slide"
        >
          <div
            class="video-card"
            :class="{ selected: isSelected(video) }"
            @click="store.toggleVideoSelection(video)"
          >
            <div class="selection-indicator">
              <svg
                v-if="isSelected(video)"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
              >
                <polyline points="20 6 9 17 4 12"></polyline>
              </svg>
            </div>
            <VideoListItem :video="video" />
          </div>
        </swiper-slide>
      </swiper>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useVideoStore } from "@/stores/video";
import VideoListItem from "./VideoListItem.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation, Pagination } from "swiper/modules";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

const props = defineProps({
  initialVideos: {
    type: Array,
    default: () => [],
  },
});

const store = useVideoStore();
const swiperBreakpoints = {
  // 모바일
  640: {
    slidesPerView: 1.2,
    spaceBetween: 20,
  },
  // 태블릿
  968: {
    slidesPerView: 1.8,
    spaceBetween: 30,
  },
  // 데스크탑
  1200: {
    slidesPerView: 2.2,
    spaceBetween: 30,
  },
};

onMounted(() => {
  if (props.initialVideos.length > 0) {
    store.setSelectedVideos(props.initialVideos);
  }
});

watch(
  () => props.initialVideos,
  (newVideos) => {
    if (newVideos.length > 0) {
      store.setSelectedVideos(newVideos);
    }
  },
  { deep: true }
);

const isSelected = (video) => {
  return store.selectedVideos.some((v) => v.id === video.id);
};

const decodeHTMLEntities = (text) => {
  const textArea = document.createElement("textarea");
  textArea.innerHTML = text;
  return textArea.value;
};

defineExpose({
  getCurrentVideos: () => store.selectedVideos,
});
</script>
<style scoped>
:root {
  --primary-blue: #c6e7ff;
  --secondary-blue: #d4f6ff;
  --neutral: #fbfbfb;
  --accent: #ffddae;
}

.video-list-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

/* Selected Videos Styles */
.selected-videos-container {
  background: white;
  border-radius: 16px;
  padding: 1.5rem;
  margin-bottom: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.selected-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid var(--secondary-blue);
}

.selected-header h3 {
  font-size: 1.25rem;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.video-count {
  background: var(--primary-blue);
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.9rem;
  color: #333;
}

.selected-video-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  background: var(--neutral);
  border-radius: 12px;
  margin-bottom: 0.5rem;
  transition: all 0.3s ease;
}

.selected-video-item:hover {
  transform: translateX(5px);
  background: var(--accent);
}

.video-number {
  width: 32px;
  height: 32px;
  background: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  color: #333;
}

.video-info {
  flex: 1;
  min-width: 0;
}

.video-info h4 {
  margin: 0 0 0.25rem 0;
  font-size: 1rem;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.video-info p {
  margin: 0;
  font-size: 0.85rem;
  color: #666;
}

.remove-btn {
  padding: 0.5rem;
  background: none;
  border: none;
  cursor: pointer;
  opacity: 0;
  transition: all 0.3s ease;
  color: #666;
}

.selected-video-item:hover .remove-btn {
  opacity: 1;
}

.remove-btn:hover {
  color: #ef4444;
  transform: rotate(90deg);
}

/* Swiper Styles */
.video-swiper {
  padding: 2rem 1rem; /* 좌우 패딩 감소 */
}

:deep(.swiper-button-next),
:deep(.swiper-button-prev) {
  background: white;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  color: #333;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

:deep(.swiper-button-next:hover),
:deep(.swiper-button-prev:hover) {
  background: var(--accent);
  transform: scale(1.1);
}

:deep(.swiper-button-next::after),
:deep(.swiper-button-prev::after) {
  font-size: 1.2rem;
  font-weight: bold;
}

:deep(.swiper-pagination-bullet) {
  background: var(--primary-blue);
  opacity: 0.5;
}

:deep(.swiper-pagination-bullet-active) {
  background: var(--accent);
  opacity: 1;
}

/* Video Card Styles */
.video-slide {
  height: auto;
  padding: 1rem; /* 패딩 추가 */
}

.video-card {
  position: relative;
  height: 100%;
  border-radius: 16px; /* 약간 더 둥글게 */
  transition: all 0.3s ease;
  cursor: pointer;
  background: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05); /* 그림자 추가 */
}

.video-card:hover {
  transform: translateY(-4px);
}

.video-card.selected {
  box-shadow: 0 0 0 2px var(--accent);
}

.selection-indicator {
  position: absolute;
  top: 1rem;
  right: 1rem;
  width: 24px;
  height: 24px;
  background: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 10;
  color: var(--accent);
  opacity: 0;
  transform: scale(0.8);
  transition: all 0.3s ease;
}

.video-card:hover .selection-indicator,
.video-card.selected .selection-indicator {
  opacity: 1;
  transform: scale(1);
}

/* Transitions */
.list-move,
.list-enter-active,
.list-leave-active {
  transition: all 0.5s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}

.list-leave-active {
  position: absolute;
}

/* Mobile Responsive */
@media (max-width: 768px) {
  .video-list-container {
    padding: 1rem;
  }

  .selected-videos-container {
    padding: 1rem;
  }

  .selected-header h3 {
    font-size: 1.1rem;
  }

  .video-count {
    font-size: 0.8rem;
    padding: 0.4rem 0.8rem;
  }

  .video-number {
    width: 28px;
    height: 28px;
    font-size: 0.9rem;
  }

  .video-info h4 {
    font-size: 0.9rem;
  }

  .video-info p {
    font-size: 0.8rem;
  }

  :deep(.swiper-button-next),
  :deep(.swiper-button-prev) {
    width: 32px;
    height: 32px;
  }

  :deep(.swiper-button-next::after),
  :deep(.swiper-button-prev::after) {
    font-size: 1rem;
  }
}

/* 애니메이션 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}
</style>
