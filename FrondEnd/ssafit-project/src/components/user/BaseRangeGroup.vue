<template>
  <div class="question-section">
    <div class="question-label">{{ label }}</div>
    <input
      type="range"
      class="form-range"
      :min="min"
      :max="max"
      :value="modelValue"
      @input="$emit('update:modelValue', Number($event.target.value))"
    />
    <div class="d-flex justify-content-between">
      <span>{{ leftLabel }}</span>
      <span>{{ rightLabel }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    min: Number,
    max: Number,
    modelValue: Number,
    leftLabel: String,
    rightLabel: String,
  },
  emits: ["update:modelValue"],
};
</script>
