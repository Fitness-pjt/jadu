<template>
  <div class="container">
    <!-- 등록된 운동 정보가 없을 때 -->
    <div v-if="exerciseInfo.age == ''" class="empty-state text-center mt-5">
      <h2 class="text-muted mb-4">운동 정보가 없습니다.</h2>
      <p class="text-muted mb-4">
        현재 운동 정보가 등록되지 않았습니다. 운동 정보를 추가하여 맞춤
        프로그램을 추천받아 보세요.
      </p>
      <RouterLink :to="{ name: 'userInfo' }"
        ><button @click="navigateToForm" class="btn btn-primary btn-lg">
          운동 정보 등록하기
        </button></RouterLink
      >
    </div>

    <!-- 등록된 운동 정보 수정 -->
    <div v-else class="form-container">
      <h2 class="text-center mb-4">운동 정보 수정</h2>
      <form @submit.prevent="submitForm">
        <BaseRadioGroup
          label="1. 성별을 선택하세요."
          name="gender"
          :options="['남', '여']"
          v-model="exerciseInfo.gender"
        />

        <BaseRadioGroup
          label="2. 나이를 선택해주세요."
          name="age"
          :options="['10대', '20~30대', '40~50대', '60대 이상']"
          v-model="exerciseInfo.age"
        />

        <BaseRadioGroup
          label="3. 체형을 선택해주세요."
          name="shape"
          :options="['마름', '보통', '살집 있음']"
          v-model="exerciseInfo.shape"
        />

        <BaseRadioGroup
          label="4. 운동 목표를 선택해주세요."
          name="goal"
          :options="['다이어트', '근육 증진', '재활', '체력 향상']"
          v-model="exerciseInfo.goal"
        />

        <BaseRangeGroup
          label="5. 본인의 체력 수준을 선택해주세요."
          :min="1"
          :max="5"
          v-model="exerciseInfo.experience"
          leftLabel="초보"
          rightLabel="전문"
        />

        <BaseRadioGroup
          label="6. 운동을 주로 어디서 하나요?"
          name="location"
          :options="['헬스장', '집', '야외', '기타']"
          v-model="exerciseInfo.location"
        />

        <BaseCheckboxGroup
          label="7. 운동하고 싶은 부위를 선택해주세요."
          :options="['상체', '하체', '가슴', '등', '어깨', '복부']"
          v-model="exerciseInfo.keyword"
        />

        <BaseRadioGroup
          label="8. 주 몇 회 운동하실 예정이신가요?"
          name="frequency"
          :options="['주 1회', '주 2회', '주 3회', '주 4회', '주 5회']"
          v-model="exerciseInfo.frequency"
        />

        <BaseRadioGroup
          label="9. 몇 주짜리 프로그램을 원하시나요?"
          name="duration"
          :options="['1주', '2주', '3주', '4주']"
          v-model="exerciseInfo.duration"
        />

        <div class="text-center mt-4">
          <button type="submit" class="btn btn-primary btn-lg">
            정보 수정 완료
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, watch } from "vue";
import BaseCheckboxGroup from "./BaseCheckboxGroup.vue";
import BaseRadioGroup from "./BaseRadioGroup.vue";
import BaseRangeGroup from "./BaseRangeGroup.vue";
import { useUserInfoStore } from "@/stores/userInfo";

const userInfoStore = useUserInfoStore();
const exerciseInfo = computed(() => userInfoStore.exerciseInfo);
const userInfoList = computed(() => userInfoStore.userInfoList);

watch(
  () => userInfoList.value,
  (newList, oldList) => {
    exerciseInfo.value = newList;
    console.log("exerciseInfo :>> ", exerciseInfo.value);
  },
  { deep: true }
);

console.log("exerciseInfo :>> ", exerciseInfo.gender == "");

const submitForm = () => {
  // 폼 제출 로직 (예: API 호출, 유효성 검사 등)
  console.log("제출된 운동 정보:", exerciseInfo.value);
  // 필요한 추가 로직 구현
};

onMounted(() => {
  userInfoStore.getUserInfo();
});
</script>

<style scoped>
.form-container {
  background-color: white;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: 0 auto;
}

.question-section {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 5px;
}

.question-label {
  font-weight: bold;
  margin-bottom: 10px;
}
</style>
