<template>
  <VCalendar
    borderless
    :attributes="attrs"
    :color="selectedColor"
    @dayclick="onDayClick"
  />
</template>

<script setup>
import { useTodoStore } from "@/stores/todo";
import { onMounted, ref } from "vue";

const todoStore = useTodoStore();

const selectedColor = ref("blue");
let attrs = ref([
  {
    key: "workout",
    highlight: true,
    dates: new Date(),
  },
]);

const onDayClick = (day) => {
  // 날짜를 YYYY-MM-DD 형식의 문자열로 전달
  const selectedDate = day.id;
  todoStore.setSelectedDate(selectedDate);

  // 선택된 날짜 하이라트 업데이트
  attrs.value = [
    {
      key: "selected",
      highlight: true,
      dates: selectedDate,
    },
  ];
};
</script>

<style scoped></style>
