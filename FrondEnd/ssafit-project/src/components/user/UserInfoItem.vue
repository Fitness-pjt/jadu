<template>
  <div class="container py-5">
    <div class="question-card">
      <!-- Progress Bar 추가 -->
      <div class="progress-wrapper">
        <div class="progress">
          <div
            class="progress-bar"
            :style="{ width: `${(question.index / 10) * 100}%` }"
          ></div>
        </div>
        <span class="progress-text">{{ question.index }} / 10</span>
      </div>

      <p class="question-title" v-if="question.index < 11">
        <span>{{ question.index }}. </span> {{ question.question }}
      </p>
      <p class="question-title" v-if="question.index === 11">
        {{ question.question }}
      </p>

      <!-- Text Input -->
      <div v-if="question.type === 'text'" class="input-wrapper">
        <input
          v-model="userInput"
          type="text"
          class="form-control custom-input"
          placeholder="답변을 입력하세요"
        />
      </div>

      <!-- Radio Buttons -->
      <div v-if="question.type === 'radio'" class="options-wrapper">
        <div
          v-for="option in question.options"
          :key="option"
          class="form-check custom-radio"
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
      <div v-if="question.type === 'range'" class="range-wrapper">
        <input
          type="range"
          v-model="userInput"
          :min="question.min"
          :max="question.max"
          class="form-range custom-range"
        />
        <div class="range-value">
          현재 값: <span>{{ getRangeText(userInput) }}</span>
        </div>
      </div>

      <!-- Checkbox -->
      <div v-if="question.type === 'checkbox'" class="options-wrapper">
        <div
          v-for="option in question.options"
          :key="option"
          class="form-check custom-checkbox"
        >
          <label class="form-check-label" :for="'checkbox-' + option">
            <input
              type="checkbox"
              :value="option"
              v-model="userInput"
              class="form-check-input"
              :id="`checkbox-` + option"
            />
            <span class="checkmark"></span>
            {{ option }}
          </label>
        </div>
      </div>

      <!-- Buttons -->
      <div class="button-wrapper">
        <button @click="submitAnswer" class="next-button">다음</button>
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

<style scoped>
.question-card {
  background: white;
  border-radius: 15px;
  padding: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  position: relative;
}

/* Progress Bar 스타일 */
.progress-wrapper {
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.progress {
  flex-grow: 1;
  height: 8px;
  background-color: #edf2f7;
  border-radius: 10px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background: linear-gradient(to right, #c6e7ff, #d4f6ff);
  transition: width 0.3s ease;
}

.progress-text {
  color: #133e87;
  font-weight: 600;
  font-size: 0.9rem;
}

.question-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  color: #133e87;
  border-bottom: 2px solid #ffddae;
}

.input-wrapper,
.options-wrapper {
  margin-bottom: 2rem;
}

.custom-input {
  border: 2px solid #eee;
  border-radius: 8px;
  padding: 0.75rem;
  transition: all 0.2s ease;
}

.custom-input:focus {
  border-color: #c6e7ff;
  box-shadow: 0 0 0 0.25rem rgba(198, 231, 255, 0.25);
}

/* 라디오 버튼 스타일 */
.form-check {
  padding: 0.75rem 1rem;
  margin-bottom: 0.5rem;
  background: #fbfbfb;
  border-radius: 8px;
  border: 1px solid #eee;
}

.form-check-input {
  width: 1.2rem;
  height: 1.2rem;
  margin-right: 0.75rem;
  cursor: pointer;
}

.form-check-input:checked {
  background-color: #c6e7ff;
  border-color: #133e87;
}

.form-check-label {
  color: #2c3e50;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
}

/* 체크박스 커스텀 스타일 */
.custom-checkbox {
  position: relative;
}

.custom-checkbox input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

.checkmark {
  position: relative;
  display: inline-block;
  height: 20px;
  width: 20px;
  background-color: #fff;
  border: 2px solid #c6e7ff;
  border-radius: 4px;
  margin-right: 10px;
}

.custom-checkbox input:checked ~ .checkmark {
  background-color: #c6e7ff;
  border-color: #133e87;
}

.checkmark:after {
  content: "";
  position: absolute;
  display: none;
  left: 6px;
  top: 2px;
  width: 5px;
  height: 10px;
  border: solid #133e87;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.custom-checkbox input:checked ~ .checkmark:after {
  display: block;
}

/* Range Slider 스타일 */
.range-wrapper {
  padding: 1rem 0;
}

.custom-range {
  height: 5px;
  border-radius: 5px;
  background: #d4f6ff;
}

.custom-range::-webkit-slider-thumb {
  width: 20px;
  height: 20px;
  background: #c6e7ff;
  border: 2px solid #133e87;
  border-radius: 50%;
  cursor: pointer;
  -webkit-appearance: none;
}

.range-value {
  text-align: center;
  margin-top: 1rem;
  color: #666;
}

.range-value span {
  color: #133e87;
  font-weight: 600;
}

.button-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 2rem;
  padding-top: 1.5rem;
  border-top: 1px solid #eee;
}

.next-button {
  padding: 0.75rem 2rem;
  background: #c6e7ff;
  color: #133e87;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.next-button:hover {
  background: #d4f6ff;
}

@media (max-width: 768px) {
  .question-card {
    margin: 1rem;
    padding: 1.5rem;
  }

  .question-title {
    font-size: 1.1rem;
  }

  .progress-wrapper {
    margin-bottom: 1.5rem;
  }

  .next-button {
    width: 100%;
  }
}
</style>
