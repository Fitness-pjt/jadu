<template>
  <div>
    <!-- <img
      :src="video.snippet.thumbnails.medium.url"
      alt="썸네일"
      class="card-thumbnail"
    /> --><iframe
        width="320"
        height="180"
        :src="videoURL"
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        referrerpolicy="strict-origin-when-cross-origin"
        allowfullscreen
      ></iframe>
    <div class="card-content">
      <h3 class="video-title">{{ videoTitle }}</h3>
      <p class="channel-name">채널명: {{ video.snippet.channelTitle }}</p>
      <!-- <RouterLink
        :to="{ name: 'review', params: { videoId: video.id.videoId } }"
        ><button class="review-button" @click="clickVideo">
          리뷰 보러가기
        </button></RouterLink -->
      
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import _ from "lodash";
import { storeToRefs } from "pinia";
import { useVideoStore } from "@/stores/video";

const props = defineProps({
  video: Object,
});

const videoTitle = computed(() => {
  return _.unescape(props.video.snippet.title);
});

const videoURL = computed(() => {
  const videoSrc = props.video.id.videoId;
  return `https://www.youtube.com/embed/${videoSrc}`;
});

const store = useVideoStore();

const clickVideo = () => {
  store.clickVideo(props.video);
};
</script>

<style scoped>
.card-thumbnail {
  width: 100%;
  height: auto;
}

.card-content {
  padding: 16px;
  text-align: center;
}

.video-title {
  font-size: 18px;
  font-weight: 600;
  margin: 8px 0;
}

.channel-name,
.exercise-part {
  color: #666;
  font-size: 14px;
  margin: 4px 0;
}

.review-button {
  margin-top: 12px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.review-button:hover {
  background-color: #0056b3;
}
</style>
