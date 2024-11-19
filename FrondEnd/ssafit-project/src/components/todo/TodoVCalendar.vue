<template>
  <div>
    <VCalendar borderless :attributes="attrs" :color="selectedColor" />
  </div>
</template>

<script setup>
import { ref } from "vue";

const selectedColor = ref("blue");
const attrs = ref([
  {
    key: "workout",
    highlight: true,
    dates: new Date(),
  },
]);
</script>

<style scoped></style>
