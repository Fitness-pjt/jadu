<template>
  <div>
    <p class="p-5">{{ userStore.userNickname }}님의 프로필 페이지</p>
    <div v-if="userStore.userStatus">
      <TodoView :userId="userId" />
    </div>

    <div v-else class="private-account">
      <i class="fas fa-lock lock-icon"></i>
      <h2 class="private-title">비공개 계정입니다</h2>
      <div class="private-divider"></div>
      <p class="private-message">
        이 사용자의 프로필은 비공개로 설정되어 있습니다.<br />
        투두 리스트를 보려면 사용자의 승인이 필요합니다.
      </p>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { onMounted } from "vue";
import TodoView from "./TodoView.vue";
import { useRoute } from "vue-router";
import UserNameTag from "@/components/common/UserNameTag.vue";

const userStore = useUserStore();
const route = useRoute();
const userId = route.params.userId;

onMounted(() => [userStore.getUserProfileInfo(userId)]);
</script>
<style scoped>
p {
  font-size: x-large;
  font-weight: bold;
  color: #133e87;
  margin-bottom: 2rem;
}

/* 비공개 계정 안내 스타일 */
.private-account {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 3rem;
  margin: 2rem auto;
  max-width: 500px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
  text-align: center;
}

.lock-icon {
  font-size: 4rem;
  color: #c6e7ff;
  margin-bottom: 1.5rem;
  animation: float 3s ease-in-out infinite;
}

.private-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #133e87;
  margin-bottom: 1rem;
}

.private-message {
  color: #666;
  line-height: 1.5;
  margin-bottom: 2rem;
  font-size: 1rem;
}

.private-divider {
  width: 50px;
  height: 4px;
  background: linear-gradient(to right, #c6e7ff, #ffddae);
  border-radius: 2px;
  margin: 1rem auto;
}

@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-10px);
  }
  100% {
    transform: translateY(0px);
  }
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .private-account {
    margin: 1rem;
    padding: 2rem;
  }

  .lock-icon {
    font-size: 3rem;
  }

  .private-title {
    font-size: 1.3rem;
  }
}

@media (max-width: 480px) {
  .private-account {
    padding: 1.5rem;
  }

  .lock-icon {
    font-size: 2.5rem;
  }

  .private-title {
    font-size: 1.1rem;
  }

  .private-message {
    font-size: 0.9rem;
  }
}
</style>
