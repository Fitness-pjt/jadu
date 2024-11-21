<template>
  <div>
    <p>
      <span>{{ question.index }} . </span> {{ question.question }}
    </p>
    <div v-if="question.type === 'text'">
      <input v-model="userInput" type="text" />
    </div>
    <div v-if="question.type === 'radio'">
      <label v-for="option in question.options" :key="option">
        <input type="radio" :value="option" v-model="userInput" /> {{ option }}
      </label>
    </div>
    <div v-if="question.type === 'range'">
      <input
        type="range"
        v-model="userInput"
        :min="question.min"
        :max="question.max"
      />
      <span>{{ userInput }}</span>
    </div>
    <button @click="goBack" v-if="canGoBack">이전</button>
    <button @click="submitAnswer">다음</button>
  </div>
</template>

<script setup>
import { ref } from "vue";

const props = defineProps({
  question: Object,
  onSubmit: Function,
  onBack: Function,
  canGoBack: Boolean,
});

const userInput = ref("");

const submitAnswer = () => {
  props.onSubmit(userInput.value);
};

const goBack = () => {
  props.onBack();
};
</script>
