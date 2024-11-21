<template>
  <div class="question-section">
    <div class="question-label">{{ label }}</div>
    <div class="form-check" v-for="option in options" :key="option">
      <input
        class="form-check-input"
        type="radio"
        :name="name"
        :id="option.toLowerCase()"
        :value="option"
        :checked="modelValue === option"
        @change="$emit('update:modelValue', option)"
      />
      <label class="form-check-label" :for="option.toLowerCase()">
        {{ option }}
      </label>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    name: String,
    options: Array,
    modelValue: String,
  },
  emits: ["update:modelValue"],
};
</script>
