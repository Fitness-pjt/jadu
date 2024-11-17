<!-- TodoCalendar.vue -->
<template>
  <div class="calendar-section">
    <!-- 달력 헤더 -->
    <div class="calendar-header">
      <button class="month-nav-btn" @click="previousMonth">&lt;</button>
      <h2>{{ currentYearMonth }}</h2>
      <button class="month-nav-btn" @click="nextMonth">&gt;</button>
    </div>

    <!-- 요일 헤더 -->
    <div class="calendar-weekdays">
      <div
        v-for="day in weekDays"
        :key="day"
        class="weekday"
        :class="{ sunday: day === '일' }"
      >
        {{ day }}
      </div>
    </div>

    <!-- 달력 날짜들 -->
    <div class="calendar-days">
      <div
        v-for="{ date, current, today, selected } in calendarDays"
        :key="date"
        class="day"
        :class="{
          'other-month': !current,
          today: today,
          selected: selected,
          'has-todos': hasTodos(date),
        }"
        @click="selectDate(date)"
      >
        {{ new Date(date).getDate() }}
        <!-- 할 일이 있는 경우 표시할 도트 -->
        <div v-if="hasTodos(date)" class="todo-dot"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useTodoStore } from "@/stores/todo";

const todoStore = useTodoStore();
const emit = defineEmits(["dateSelect"]);

// 현재 보여지는 년월 상태
const currentDate = ref(new Date());

// 선택된 날짜
const selectedDate = ref(new Date());

// 요일 배열
const weekDays = ["일", "월", "화", "수", "목", "금", "토"];

// 현재 년월 표시
const currentYearMonth = computed(() => {
  return currentDate.value.toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "long",
  });
});

// 이전 달로 이동
const previousMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() - 1,
    1
  );
};

// 다음 달로 이동
const nextMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() + 1,
    1
  );
};

// 달력에 표시될 날짜들 계산
const calendarDays = computed(() => {
  const year = currentDate.value.getFullYear();
  const month = currentDate.value.getMonth();
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);
  const days = [];

  // 이전 달의 날짜들
  const prevMonthDays = firstDay.getDay();
  for (let i = prevMonthDays - 1; i >= 0; i--) {
    const date = new Date(year, month, -i);
    days.push({
      date: date.toISOString().split("T")[0],
      current: false,
      today: isSameDay(date, new Date()),
      selected: isSameDay(date, selectedDate.value),
    });
  }

  // 현재 달의 날짜들
  for (let i = 1; i <= lastDay.getDate(); i++) {
    const date = new Date(year, month, i);
    days.push({
      date: date.toISOString().split("T")[0],
      current: true,
      today: isSameDay(date, new Date()),
      selected: isSameDay(date, selectedDate.value),
    });
  }

  // 다음 달의 날짜들
  const remainingDays = 42 - days.length; // 6주 달력을 위해
  for (let i = 1; i <= remainingDays; i++) {
    const date = new Date(year, month + 1, i);
    days.push({
      date: date.toISOString().split("T")[0],
      current: false,
      today: isSameDay(date, new Date()),
      selected: isSameDay(date, selectedDate.value),
    });
  }

  return days;
});

// 날짜 선택 핸들러
const selectDate = (date) => {
  selectedDate.value = new Date(date);
  emit("dateSelect", date);
  // Todo 리스트 업데이트
  todoStore.getTodoList(props.userId, date);
};

// 해당 날짜에 할 일이 있는지 확인
const hasTodos = (date) => {
  return todoStore.todoList.some((todo) => todo.date === date);
};

// 같은 날짜인지 확인하는 유틸리티 함수
const isSameDay = (date1, date2) => {
  return (
    date1.getFullYear() === date2.getFullYear() &&
    date1.getMonth() === date2.getMonth() &&
    date1.getDate() === date2.getDate()
  );
};
</script>

<style scoped>
.calendar-section {
  background: white;
  border-radius: 15px;
  padding: 1.5rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.month-nav-btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 8px;
  background: #f5f5f5;
  cursor: pointer;
  transition: background-color 0.2s;
}

.month-nav-btn:hover {
  background: #e0e0e0;
}

.calendar-weekdays {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  text-align: center;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.weekday {
  padding: 0.5rem;
}

.sunday {
  color: #ff5252;
}

.calendar-days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 2px;
}

.day {
  position: relative;
  aspect-ratio: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border-radius: 50%;
  transition: all 0.2s;
}

.day:hover {
  background: #f5f5f5;
}

.other-month {
  color: #ccc;
}

.today {
  background: #e8f5e9;
  font-weight: bold;
}

.selected {
  background: #42b983;
  color: white;
}

.has-todos .todo-dot {
  position: absolute;
  bottom: 2px;
  width: 4px;
  height: 4px;
  border-radius: 50%;
  background: #42b983;
}

.selected .todo-dot {
  background: white;
}
</style>
