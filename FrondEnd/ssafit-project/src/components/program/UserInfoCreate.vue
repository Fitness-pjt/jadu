<template>
  <div>
    <UserInfoItem
      :question="questions[currentQuestionIndex]"
      :on-submit="handleNextQuestion"
      :on-back="handlePreviousQuestion"
      :can-go-back="currentQuestionIndex > 0"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import UserInfoItem from "./UserInfoItem.vue";

const questions = [
  {
    index: 1,
    question: "성별을 선택하세요.",
    type: "radio",
    options: ["남", "여"],
    id: "gender",
  },
  {
    index: 2,
    question: "나이를 선택해주세요.",
    type: "radio",
    options: ["10대", "20~30대", "40~50대", "60대 이상"],
    id: "age",
  },
  {
    index: 3,
    question: "체형을 선택해주세요.",
    type: "radio",
    options: ["마름", "보통", "살집 있음"],
    id: "shape",
  },
  {
    index: 4,
    question: "운동 목표를 선택해주세요.",
    type: "radio",
    options: ["다이어트", "근육 증진", "재활", "체력 향상"],
    id: "goal",
  },
  {
    index: 5,
    question: "본인의 체력 수준을 선택해주세요.",
    type: "range",
    min: 1,
    max: 5,
    id: "experience",
  },
  {
    index: 6,
    question: "운동을 주로 어디서 하나요?",
    type: "radio",
    options: ["헬스장", "집", "야외", "기타"],
    id: "location",
  },
  {
    index: 7,
    question: "운동을 주로 어디서 하나요?",
    type: "checkbox",
    options: ["헬스장", "집", "야외", "기타"],
    id: "location",
  },
];

const currentQuestionIndex = ref(0);
const answers = ref([]);

const handleNextQuestion = (answer) => {
  answers.value.push(answer);
  if (currentQuestionIndex.value < questions.length - 1) {
    currentQuestionIndex.value++;
  }
};

const handlePreviousQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;
  }
};
</script>
