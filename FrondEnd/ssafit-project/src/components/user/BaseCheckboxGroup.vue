<template>
  <div class="question-section">
    <div class="question-label">{{ label }}</div>
    <div class="form-check" v-for="option in options" :key="option">
      <input
        class="form-check-input"
        type="checkbox"
        :id="option.toLowerCase()"
        :value="option"
        :checked="modelValue.includes(option)"
        @change="toggleOption(option)"
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
    options: Array,
    modelValue: Array,
  },
  emits: ["update:modelValue"],
  methods: {
    toggleOption(option) {
      const currentValue = [...this.modelValue];
      const index = currentValue.indexOf(option);

      if (index > -1) {
        currentValue.splice(index, 1);
      } else {
        currentValue.push(option);
      }

      this.$emit("update:modelValue", currentValue);
    },
  },
};
</script>
