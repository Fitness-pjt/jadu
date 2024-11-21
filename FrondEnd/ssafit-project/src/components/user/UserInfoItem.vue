<template>
  <div class="container py-5">
    <div class="card shadow-sm p-4">
      <p class="fs-5 fw-bold mb-4 text-primary" v-if="question.index < 11">
        <span>{{ question.index }} . </span> {{ question.question }}
      </p>
      <p class="fs-5 fw-bold mb-4 text-primary" v-if="question.index === 11">
        {{ question.question }}
      </p>

      <!-- Text Input -->
      <div v-if="question.type === 'text'" class="mb-3">
        <input
          v-model="userInput"
          type="text"
          class="form-control"
          placeholder="답변을 입력하세요"
        />
      </div>

      <!-- Radio Buttons -->
      <div v-if="question.type === 'radio'" class="mb-3">
        <div
          v-for="option in question.options"
          :key="option"
          class="form-check"
        >
          <input
            type="radio"
            :value="option"
            v-model="userInput"
            class="form-check-input"
            :id="`radio-` + option"
          />
          <label class="form-check-label" :for="'radio-' + option">
            {{ option }}
          </label>
        </div>
      </div>

      <!-- Range Slider -->
      <div v-if="question.type === 'range'" class="mb-3">
        <input
          type="range"
          v-model="userInput"
          :min="question.min"
          :max="question.max"
          class="form-range"
        />
        <span class="text-muted">현재 값: {{ getRangeText(userInput) }}</span>
      </div>

      <!-- Checkbox -->
      <div v-if="question.type === 'checkbox'" class="mb-3">
        <div
          v-for="option in question.options"
          :key="option"
          class="form-check"
        >
          <label class="form-check-label" :for="'checkbox-' + option">
            <input
              type="checkbox"
              :value="option"
              v-model="userInput"
              class="form-check-input"
              :id="`checkbox-` + option"
            />
            {{ option }}
          </label>
        </div>
      </div>

      <!-- Buttons -->
      <div class="d-flex justify-content-between">
        <!-- <button
          @click="goBack"
          v-if="canGoBack"
          class="btn btn-outline-secondary"
        >
          이전
        </button> -->
        <button @click="submitAnswer" class="btn btn-primary">다음</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watchEffect } from "vue";

const props = defineProps({
  question: Object,
  onSubmit: Function,
});

const userInput = ref([]);

// question.type에 따라 초기값 설정하기
watchEffect(() => {
  switch (props.question.type) {
    case "text":
      userInput.value = ""; // 텍스트 입력은 빈 문자열로 초기화
      break;
    case "radio":
      userInput.value = props.question.options[0]; // 라디오 버튼은 첫 번째 옵션으로 초기화
      break;
    case "range":
      userInput.value = 3; // 범위는 중급자(3)로 초기화
      break;
    case "checkbox":
      userInput.value = []; // 체크박스는 빈 배열로 초기화
      break;
    default:
      userInput.value = ""; // 기본값은 빈 문자열
  }
});

// 정답 next 버튼 누를 때 부모 컴포넌트도 전송하기
const submitAnswer = () => {
  props.onSubmit(userInput.value);
};

// const goBack = () => {
//   props.onBack();
// };

// 범위 값에 해당하는 텍스트를 반환하는 함수
const getRangeText = (value) => {
  const rangeTexts = ["초보자", "입문자", "중급자", "고급자", "마스터"]; // 1부터 5까지의 텍스트 배열
  return rangeTexts[value - 1]; // 1부터 시작하므로 value - 1로 배열 인덱스에 접근
};
</script>

<style>
.text-primary {
  color: #42b983 !important;
}

.btn-primary {
  background-color: #42b983;
  border-color: #42b983;
}

.btn-primary:hover {
  background-color: #369e6f;
  border-color: #369e6f;
}

.form-check-input:checked {
  background-color: #42b983;
  border-color: #42b983;
}
</style>
