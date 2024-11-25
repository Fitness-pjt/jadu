<template>
  <div class="row">
    <!-- 선택된 비디오 재생 영역 -->
    <div class="col-12 mb-4" v-if="selectedVideo">
      <div class="video-player-wrapper">
        <iframe
          :src="`https://www.youtube.com/embed/${selectedVideo.id.videoId}`"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
          class="video-player"
        ></iframe>
      </div>
      <div class="video-info mt-4">
        <h3>{{ selectedVideo.snippet.title }}</h3>
      </div>
    </div>

    <!-- 비디오 목록 -->
    <div class="col-12">
      <div class="row g-4">
        <!-- 비디오가 없는 경우 -->
        <div v-if="!videos.length" class="col-12">
          <div class="alert alert-info">등록된 비디오가 없습니다.</div>
        </div>

        <!-- 비디오 리스트 -->
        <div>
          <h5>프로그램 추천 운동 영상</h5>
        </div>
        <div
          v-for="video in videos"
          :key="video.id.videoId"
          class="col-md-6 col-lg-4"
          @click="selectVideo(video)"
          style="cursor: pointer"
        >
          <div
            class="card h-100"
            :class="{
              'border-primary': selectedVideo?.id.videoId === video.id.videoId,
            }"
          >
            <div class="thumbnail-wrapper">
              <img
                :src="video.snippet.thumbnails.medium.url"
                :alt="video.snippet.title"
                class="card-img-top"
              />
              <div class="play-overlay">
                <i class="bi bi-play-circle-fill"></i>
              </div>
            </div>
            <div class="card-body">
              <h5 class="card-title">{{ video.snippet.title }}</h5>
              <!-- <p class="card-text">{{ video.snippet.description }}</p> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  videos: Array,
  selectedVideo: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["update:selectedVideo"]);

const selectVideo = (video) => {
  emit("update:selectedVideo", video);
};
</script>

<style scoped>
.card {
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.thumbnail-wrapper {
  width: 100%;
  position: relative;
  padding-top: 56.25%; /* 16:9 비율 */
  overflow: hidden;
}

.card-img-top {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.card-title {
  font-size: 1rem;
  margin-bottom: 0.5rem;
  display: block;
  max-height: 2.4em;
  line-height: 1.2;
  overflow: hidden;
  position: relative;
}

.card-title::after {
  content: "...";
  position: absolute;
  right: 0;
  bottom: 0;
  background: white;
  padding-left: 4px;
}
.card-text {
  font-size: 0.9rem;
  color: #666;
  max-height: 3.6em;
  line-height: 1.2;
  overflow: hidden;
  position: relative;
}

.card-text::after {
  content: "...";
  position: absolute;
  right: 0;
  bottom: 0;
  background: white;
  padding-left: 4px;
}

.video-player-wrapper {
  position: relative;
  padding-top: 56.25%;
  width: 100%;
  background: #000;
}

.video-player {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  max-width: 100%;
  /* max-height: 60vh; */
}

.video-info h3 {
  font-size: 1.5rem;
  font-weight: bold;
}
.play-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.2s ease-in-out;
}

.play-overlay i {
  font-size: 3rem;
  color: white;
}

.card:hover .play-overlay {
  opacity: 1;
}
.btn-outline-primary:hover {
  transform: scale(1.05);
}

.bi-heart-fill {
  animation: pulse 0.3s ease-in-out;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}
</style>
