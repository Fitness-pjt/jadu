<template>
  <div class="container mt-4">
    <div v-if="displayData.age == undefined" class="empty-state">
      <h2>등록된 운동 정보가 없습니다.</h2>
      <p style="color: #666">
        현재 운동 정보가 등록되지 않았습니다. 운동 정보를 추가하여 맞춤
        프로그램을 추천받아 보세요.
      </p>
      <RouterLink :to="{ name: 'createAIProgram' }">
        <button class="btn-navy">운동 정보 등록하기</button>
      </RouterLink>
    </div>

    <div v-else class="info-card">
      <h2>나의 운동 정보</h2>

      <div class="info-grid">
        <!-- 왼쪽 컬럼 -->
        <div class="info-column">
          <div class="info-item">
            <span class="label">성별</span>
            <span class="value">{{ displayData.gender }}</span>
          </div>
          <div class="info-item">
            <span class="label">연령대</span>
            <span class="value">{{ displayData.age }}</span>
          </div>
          <div class="info-item">
            <span class="label">체형</span>
            <span class="value">{{ displayData.shape }}</span>
          </div>
          <div class="info-item">
            <span class="label">운동 목표</span>
            <span class="value">{{ displayData.goal }}</span>
          </div>
        </div>

        <!-- 오른쪽 컬럼 -->
        <div class="info-column">
          <div class="info-item">
            <span class="label">운동 경험</span>
            <span class="value">{{
              formattedData(displayData.experience)
            }}</span>
          </div>
          <div class="info-item">
            <span class="label">운동 장소</span>
            <span class="value">{{ displayData.location }}</span>
          </div>
          <div class="info-item">
            <span class="label">운동 빈도</span>
            <span class="value">{{ displayData.frequency }}</span>
          </div>
          <div class="info-item">
            <span class="label">운동 기간</span>
            <span class="value">{{ displayData.duration }}</span>
          </div>
        </div>
      </div>

      <div class="keyword-section">
        <span class="label">관심 부위</span>
        <div class="keyword-chips">
          <span
            v-for="(keyword, index) in displayData.keyword"
            :key="index"
            class="keyword-chip"
          >
            {{ keyword }}
          </span>
        </div>
      </div>

      <div class="button-group">
        <button class="btn-point-blue" @click="goToUpdateUserInfo">
          <font-awesome-icon icon="fa-solid fa-pencil-alt" class="" />
          운동 정보 수정하기
        </button>
        <button class="btn-point-orange" @click="goToCreateNewProgram">
          <font-awesome-icon icon="fa-solid fa-robot" class="" />
          새로운 맞춤 프로그램 만들기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useUserInfoStore } from "@/stores/userInfo";
import { formatExerciseInfo } from "@/utils/formatDisplayAnswer";
import { computed, onMounted, watch } from "vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

const userInfoStore = useUserInfoStore();
const exerciseInfo = computed(() => userInfoStore.exerciseInfo);
const userInfoList = computed(() => userInfoStore.userInfoList);
// console.log("exerciseInfo.value :>> ", exerciseInfo.value);
const displayData = computed(() => formatExerciseInfo(exerciseInfo.value));
// console.log("displayData :>> ", displayData.value);

watch(
  () => userInfoList.value,
  (newList, oldList) => {
    exerciseInfo.value = newList;
  },

  { deep: true }
);

watch(() => exerciseInfo.value, { deep: true });

onMounted(() => {
  userInfoStore.getUserInfo();
});

const formattedData = (data) => {
  if (data === "1") return "운동 초보자";
  if (data === "2") return "기초 운동 경험자";
  if (data === "3") return "중급 운동자";
  if (data === "4") return "숙련된 운동자";
  if (data === "5") return "전문가 수준";
};

const goToUpdateUserInfo = () => {
  router.push({ name: "updateUserInfo" });
};

const goToCreateNewProgram = () => {
  router.push({ name: "createAIProgram" });
};
</script>

<style scoped>
.container {
  max-width: 800px;
  padding: 2rem;
}

.info-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); */
}

.info-card h2 {
  font-size: 1.5rem;
  color: #2c3e50;
  margin-bottom: 1.5rem;
  font-weight: 600;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

.info-column {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem;
  background: #f8f9fa;
  border-radius: 8px;
}

.label {
  color: #666;
  font-weight: 500;
}

.value {
  color: #2c3e50;
  font-weight: 600;
}

.keyword-section {
  padding: 1rem 0;
  border-top: 1px solid #eee;
  margin: 1rem 0;
}

.keyword-chips {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.keyword-chip {
  background: #c6e7ff;
  color: #2c3e50;
  padding: 0.4rem 0.8rem;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: 500;
}

.button-group {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 1.5rem;
  padding-top: 1rem;
  border-top: 1px solid #eee;
}

.btn-primary {
  background-color: #c6e7ff;
  border: none;
  color: #2c3e50;
  font-weight: 500;
  padding: 0.75rem 1.25rem;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.btn-primary:hover {
  opacity: 0.9;
}

.empty-state {
  text-align: center;
  padding: 3rem 2rem;
  background: white;
  border-radius: 12px;
  /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); */
}

.empty-state h2 {
  color: #2c3e50;
  font-size: 1.3rem;
  margin-bottom: 2rem;
}

.empty-state p {
  color: #666;
  margin-bottom: 1.5rem;
}

@media (max-width: 768px) {
  .container {
    padding: 1rem;
  }

  .info-grid {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .button-group {
    flex-direction: column;
  }

  .btn-primary {
    width: 100%;
  }
}
</style>
