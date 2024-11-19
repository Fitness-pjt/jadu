<template>
    <div class="user-profile-container" :class="size">
      <!-- 프로필 이미지 -->
      <div class="profile-image-wrapper">
        <img 
          v-if="profileImage"
          :src="profileImage"
          :alt="userName"
          class="profile-image"
          @error="handleImageError"
        />
        <div v-else class="profile-fallback">
          {{ userInitial }}
        </div>
      </div>
      
      <!-- 유저 이름 -->
      <span class="user-name" v-if="showName">
        {{ userName }}
      </span>
    </div>
  </template>
  
  <script setup>
  import { computed } from 'vue'
  import { useUserStore } from '@/stores/user'
  
  const props = defineProps({
    // 유저 정보를 props로 받거나, store에서 가져오는 두 가지 방식 지원
    userId: {
      type: Number,
      default: null
    },
    // 크기 설정 (sm, md, lg)
    size: {
      type: String,
      default: 'md'
    },
    // 이름 표시 여부
    showName: {
      type: Boolean,
      default: true
    }
  })
  
  const userStore = useUserStore()
  
  // 프로필 이미지 에러 처리
  const handleImageError = (e) => {
    e.target.src = '/default-profile.png'  // 기본 이미지로 대체
  }
  
  // 이미지가 없을 때 표시할 이니셜
  const userInitial = computed(() => {
    return userName.value?.charAt(0) || '?'
  })
  
  // 프로필 이미지 URL
  const profileImage = computed(() => {
    if (props.userId) {
      // props로 받은 userId의 이미지
      const userInfo = userStore.getUserInfo(props.userId)
      return userInfo?.profileImage
    }
    // 현재 로그인한 유저의 이미지
    return userStore.profileImage
  })
  
  // 유저 이름
  const userName = computed(() => {
    if (props.userId) {
      const userInfo = userStore.getUserInfo(props.userId)
      return userInfo?.nickname
    }
    return userStore.nickname
  })
  </script>
  
  <style scoped>
  .user-profile-container {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
  
  .profile-image-wrapper {
    border-radius: 50%;
    overflow: hidden;
  }
  
  .profile-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .profile-fallback {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #e5e7eb;
    color: #6b7280;
    font-weight: 600;
  }
  
  /* 크기 변형 */
  .sm .profile-image-wrapper,
  .sm .profile-fallback {
    width: 32px;
    height: 32px;
  }
  
  .md .profile-image-wrapper,
  .md .profile-fallback {
    width: 48px;
    height: 48px;
  }
  
  .lg .profile-image-wrapper,
  .lg .profile-fallback {
    width: 64px;
    height: 64px;
  }
  
  .user-name {
    font-weight: 500;
  }
  
  .sm .user-name {
    font-size: 0.875rem;
  }
  
  .md .user-name {
    font-size: 1rem;
  }
  
  .lg .user-name {
    font-size: 1.125rem;
  }
  </style>