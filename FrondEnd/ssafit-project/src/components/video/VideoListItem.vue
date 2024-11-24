<!-- VideoListItem.vue -->
<template>
  <div class="video-item" :class="{ 'display-only': displayOnly }">
    <div class="thumbnail-wrapper">
      <iframe
        v-if="!displayOnly"
        class="video-frame"
        :src="videoURL"
        :title="videoTitle"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen
      />
      <img
        v-else
        :src="video.snippet.thumbnails.medium.url"
        :alt="videoTitle"
        class="thumbnail-img"
      />
      <!-- Duration Badge -->
      <div v-if="video.contentDetails?.duration" class="duration-badge">
        {{ formatDuration(video.contentDetails.duration) }}
      </div>
      <!-- Video Type Badge -->
      <div class="type-badge" :class="getDifficultyClass(videoTitle)">
        {{ getDifficultyText(videoTitle) }}
      </div>
    </div>

    <div class="content-wrapper">
      <div class="video-info">
        <h3 class="title">{{ videoTitle }}</h3>
        <p class="channel">
          <span class="channel-icon">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/>
            </svg>
          </span>
          {{ video.snippet.channelTitle }}
        </p>
      </div>
      
      <p v-if="!displayOnly" class="description">
        {{ truncatedDescription }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import _ from 'lodash';

const props = defineProps({
  video: {
    type: Object,
    required: true
  },
  displayOnly: {
    type: Boolean,
    default: false
  }
});

const videoTitle = computed(() => _.unescape(props.video.snippet.title));

const videoURL = computed(() => {
  return `https://www.youtube.com/embed/${props.video.id}`;
});

const truncatedDescription = computed(() => {
  const desc = props.video.snippet.description;
  return desc.length > 100 ? `${desc.slice(0, 100)}...` : desc;
});

const getDifficultyText = (title) => {
  if (title.toLowerCase().includes('초급')) return '초급';
  if (title.toLowerCase().includes('중급')) return '중급';
  if (title.toLowerCase().includes('고급')) return '고급';
  return '일반';
};

const getDifficultyClass = (title) => {
  if (title.toLowerCase().includes('초급')) return 'beginner';
  if (title.toLowerCase().includes('중급')) return 'intermediate';
  if (title.toLowerCase().includes('고급')) return 'advanced';
  return 'normal';
};

const formatDuration = (duration) => {
  if (!duration) return '';
  const match = duration.match(/PT(\d+H)?(\d+M)?(\d+S)?/);
  const hours = (match[1] || '').replace('H', '');
  const minutes = (match[2] || '').replace('M', '');
  const seconds = (match[3] || '').replace('S', '');
  
  if (hours) return `${hours}:${minutes.padStart(2, '0')}:${seconds.padStart(2, '0')}`;
  return `${minutes || '0'}:${seconds.padStart(2, '0')}`;
};
</script>

<style scoped>
.video-item {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
  height: 100%;
}

.thumbnail-wrapper {
  position: relative;
  padding-top: 56.25%;
  background: #f8f9fa;
  overflow: hidden;
}

.video-frame,
.thumbnail-img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.duration-badge,
.type-badge {
  position: absolute;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 500;
}

.duration-badge {
  bottom: 8px;
  right: 8px;
  background: rgba(0, 0, 0, 0.8);
  color: white;
}

.type-badge {
  top: 8px;
  left: 8px;
  background: var(--primary-blue);
  color: #333;
}

.type-badge.beginner { background: #4ade80; color: #064e3b; }
.type-badge.intermediate { background: #fbbf24; color: #92400e; }
.type-badge.advanced { background: #f87171; color: #991b1b; }
.type-badge.normal { background: #e5e7eb; color: #374151; }

.content-wrapper {
  padding: 1rem;
}

.video-info {
  margin-bottom: 0.5rem;
}

.title {
  font-size: 1rem;
  font-weight: 600;
  color: #333;
  margin: 0 0 0.5rem 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
}

.channel {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #666;
  font-size: 0.85rem;
  margin: 0;
}

.channel-icon {
  color: #666;
  display: flex;
  align-items: center;
}

.description {
  font-size: 0.85rem;
  color: #666;
  margin: 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
}

@media (max-width: 768px) {
  .content-wrapper {
    padding: 0.8rem;
  }

  .title {
    font-size: 0.9rem;
  }

  .channel {
    font-size: 0.8rem;
  }

  .description {
    display: none;
  }
}
</style>