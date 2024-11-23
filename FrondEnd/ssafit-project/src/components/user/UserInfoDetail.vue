<template>
  <div class="container mt-4">
    <h1 class="text-center mb-4">운동 정보 디테일 페이지</h1>
    <!-- 등록된 운동 정보가 없을 때 -->
    <div
      v-if="displayData.age == undefined"
      class="empty-state text-center mt-5"
    >
      <h2 class="text-muted mb-4">운동 정보가 없습니다.</h2>
      <p class="text-muted mb-4">
        현재 운동 정보가 등록되지 않았습니다. 운동 정보를 추가하여 맞춤
        프로그램을 추천받아 보세요.
      </p>
      <RouterLink :to="{ name: 'createAIProgram' }"
        ><button @click="navigateToForm" class="btn btn-primary btn-lg">
          운동 정보 등록하기
        </button></RouterLink
      >
    </div>
    <div v-else>
      <!-- 기본 정보 섹션 -->
      <section class="info-section mb-4">
        <h2 class="h4 text-primary">기본 정보</h2>
        <ul class="list-group">
          <li class="list-group-item">
            <strong>성별:</strong> {{ displayData.gender }}
          </li>
          <li class="list-group-item">
            <strong>연령대:</strong> {{ displayData.age }}
          </li>
          <li class="list-group-item">
            <strong>체형:</strong> {{ displayData.shape }}
          </li>
        </ul>
      </section>

      <!-- 운동 목표 섹션 -->
      <section class="info-section mb-4">
        <h2 class="h4 text-primary">운동 목표</h2>
        <ul class="list-group">
          <li class="list-group-item">
            <strong>운동 목표:</strong> {{ displayData.goal }}
          </li>
          <li class="list-group-item">
            <strong>운동 경험:</strong>
            {{ formattedData(displayData.experience) }}
          </li>
        </ul>
      </section>

      <!-- 운동 환경 섹션 -->
      <section class="info-section mb-4">
        <h2 class="h4 text-primary">운동 환경</h2>
        <ul class="list-group">
          <li class="list-group-item">
            <strong>운동 장소:</strong> {{ displayData.location }}
          </li>
          <li class="list-group-item">
            <strong>운동 키워드:</strong>
            <span v-for="(keyword, index) in displayData.keyword" :key="index">
              {{ keyword
              }}<span v-if="index < displayData.keyword.length - 1">, </span>
            </span>
          </li>
          <li class="list-group-item">
            <strong>운동 빈도:</strong> {{ displayData.frequency }}
          </li>
          <li class="list-group-item">
            <strong>운동 기간:</strong> {{ displayData.duration }}
          </li>
        </ul>
      </section>

      <!-- 수정 버튼 -->
      <div class="text-center">
        <button class="btn btn-primary" @click="goToUpdateUserInfo">
          운동 정보 수정하기
        </button>
        <button class="btn btn-primary" @click="goToCreateNewProgram">
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
/* 기본적인 레이아웃 및 간격 설정은 부트스트랩 클래스로 해결 */
.container {
  max-width: 800px;
}

h1 {
  font-size: 2.5rem;
}

.info-section h2 {
  font-size: 1.25rem;
}

.text-primary {
  color: #007bff;
}

/* 버튼 스타일을 부트스트랩으로 적용 */
.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-primary:hover {
  background-color: #0056b3;
  border-color: #004085;
}
</style>
