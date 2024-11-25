<template>
  <div class="calendar-container">
    <VCalendar
    borderless
    :attributes="attrs"
    :color="selectedColor"
    @dayclick="onDayClick"
    class="custom-calendar"
    trim
  />
  </div>
</template>

<script setup>
import { useTodoStore } from "@/stores/todo";
import { onMounted, ref } from "vue";

const todoStore = useTodoStore();

const selectedColor = ref("blue");
let attrs = ref([
  {
    key: "workout",
    highlight: {
      fillMode: 'solid',
      color: '#ffaa2a',
      contentClass: 'custom-content'
    },
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
      highlight: {
        fillMode: 'solid',
        color: '#ffaa2a',
        contentClass: 'custom-content'
      },
      dates: selectedDate,
    },
  ];
};
</script>

<style scoped>
.calendar-container {
  width: 100%;
  height: 100%;
  min-height: 350px;
  padding: 1rem;
}

.custom-calendar {
  --calendar-width: 100%;
  --calendar-height: 100%;
  
  /* 날짜 셀 크기 조정 */
  :deep(.vc-weeks) {
    width: 100%;
  }

  :deep(.vc-day) {
    min-height: 40px;
    padding: 0;
  }

  :deep(.vc-day-content) {
    width: 100%;
    height: 100%;
    min-height: 40px;
    font-size: 1rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  /* 헤더(요일) 스타일링 */
  :deep(.vc-header) {
    padding: 10px 0;
    font-weight: bold;
  }

  :deep(.vc-weeks) {
    flex-grow: 1;
  }

  :deep(.vc-day) {
    position: relative;
    min-height: 0;
    padding: 0;

    &::before {
      content: '';
      display: block;
      padding-top: 100%; /* 정사각형 비율 유지 */
    }

    .vc-day-content {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
    }
  }
}

.custom-content {
  color: #2c3e50 !important;
  font-weight: 600;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 반응형 스타일링 */
@media (max-width: 768px) {
  .calendar-container {
    min-height: 300px;
  }

  .custom-calendar {
    :deep(.vc-day-content) {
      font-size: 0.9rem;
    }
  }
}

@media (min-width: 769px) {
  .calendar-container {
    min-height: 400px;
  }
}
</style>