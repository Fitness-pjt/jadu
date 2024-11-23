<template>
  <div class="container">
    <!-- 등록된 운동 정보 수정 -->
    <div class="form-container">
      <h2 class="text-center mb-4">운동 정보 수정</h2>
      <form @submit.prevent="submitForm">
        <BaseRadioGroup
          label="1. 성별을 선택하세요."
          name="gender"
          :options="['남', '여']"
          v-model="displayData.gender"
        />

        <BaseRadioGroup
          label="2. 나이를 선택해주세요."
          name="age"
          :options="['10대', '20~30대', '40~50대', '60대 이상']"
          v-model="displayData.age"
        />

        <BaseRadioGroup
          label="3. 체형을 선택해주세요."
          name="shape"
          :options="['마름', '보통', '살집 있음']"
          v-model="displayData.shape"
        />

        <BaseRadioGroup
          label="4. 운동 목표를 선택해주세요."
          name="goal"
          :options="['다이어트', '근육 증진', '재활', '체력 향상']"
          v-model="displayData.goal"
        />

        <BaseRangeGroup
          label="5. 본인의 체력 수준을 선택해주세요."
          :min="1"
          :max="5"
          v-model="displayData.experience"
          leftLabel="초보"
          rightLabel="전문"
        />

        <BaseRadioGroup
          label="6. 운동을 주로 어디서 하나요?"
          name="location"
          :options="['헬스장', '집', '야외', '기타']"
          v-model="displayData.location"
        />

        <BaseCheckboxGroup
          label="7. 운동하고 싶은 부위를 선택해주세요."
          :options="['상체', '하체', '가슴', '등', '어깨', '복부']"
          v-model="displayData.keyword"
        />

        <BaseRadioGroup
          label="8. 주 몇 회 운동하실 예정이신가요?"
          name="frequency"
          :options="['주 1회', '주 2회', '주 3회', '주 4회', '주 5회']"
          v-model="displayData.frequency"
        />

        <BaseRadioGroup
          label="9. 몇 주짜리 프로그램을 원하시나요?"
          name="duration"
          :options="['1주', '2주', '3주', '4주']"
          v-model="displayData.duration"
        />

        <div class="text-center mt-4">
          <button
            type="submit"
            class="btn btn-primary btn-lg"
            @click="submitForm"
          >
            정보 수정 완료
          </button>
          <button type="submit" class="btn btn-lg" @click="goToDetailUserInfo">
            취소하기
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
import { formatExerciseInfo } from "@/utils/formatDisplayAnswer";
import { formatExerciseInfoForServer } from "@/utils/formattedAnswer";
import router from "@/router";

const userInfoStore = useUserInfoStore();
const exerciseInfo = computed(() => userInfoStore.exerciseInfo);
const userInfoList = computed(() => userInfoStore.userInfoList);
const displayData = computed(() => formatExerciseInfo(exerciseInfo.value));

watch(
  () => userInfoList.value,
  (newList, oldList) => {
    exerciseInfo.value = newList;
  },

  { deep: true }
);

const submitForm = () => {
  userInfoStore.sendAnswerToServer(
    formatExerciseInfoForServer(displayData.value)
  );
  alert("운동정보가 수정되었습니다.");
  router.replace({ name: "detailUserInfo" });
};

onMounted(() => {
  userInfoStore.getUserInfo();
});

const goToDetailUserInfo = (event) => {
  event.preventDefault();
  const confirm = window.confirm("정말로 취소하시겠습니까?");
  if (confirm) {
    router.replace({ name: "detailUserInfo" });
  }
  return;
};
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
