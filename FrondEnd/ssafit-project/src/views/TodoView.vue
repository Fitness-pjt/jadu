<template>
  <div class="todo-page">
    <div class="todo-container">
      <!-- Left Section: Calendar + Program Todos -->
      <div class="left-section">
        <div class="calendar-section">
          <TodoVCalendar />
        </div>
      </div>

      <!-- Right Section: Create Todo + Regular Todos -->
      <div class="content-section">
        <div class="program-todo-section">
          <ProgramTodoListItem :userId="userId" />
        </div>
        <div v-if="userId == loginUserId" class="create-section">
          <TodoCreate />
        </div>

        <div class="regular-todo-section">
          <TodoList :userId="userId" />
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import TodoCreate from "@/components/todo/TodoCreate.vue";
import TodoList from "@/components/todo/TodoList.vue";
import TodoVCalendar from "@/components/todo/TodoVCalendar.vue";
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { computed, onMounted, ref } from "vue";
import ProgramTodoListItem from "@/components/todo/ProgramTodoList.vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId;

const props = defineProps({
  userId: Number,
});

const selectedDate = computed(() => todoStore.selectedDate);

onMounted(() => {
  const today = new Date().toLocaleDateString();
  const formattedDate = today
    .split(".")
    .map((item) => item.trim())
    .join("-")
    .slice(0, 10);
  todoStore.setSelectedDate(formattedDate);
  todoStore.getTodoList(props.userId, selectedDate.value);
});
</script>
<style scoped>
:root {
  --primary-blue: #c6e7ff;
  --secondary-blue: #d4f6ff;
  --neutral: #fbfbfb;
  --accent: #ffddae;
}

.todo-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  background-color: var(--neutral);
  min-height: 100vh;
}

.todo-container {
  display: flex;
  gap: 2rem;
  min-height: 600px;
}

/* Left Section */
.left-section {
  flex: 0 0 500px;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.calendar-section {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  min-width: 500px;
}

.program-todo-section {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  /* box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); */
  min-width: 500px;
}

/* VCalendar 크기 조정을 위한 스타일 */
:deep(.vc-container) {
  width: 100%;
  font-family: inherit;
  --day-content-height: 40px;
  --day-content-width: 40px;
}

:deep(.vc-weeks) {
  padding: 0 12px;
}

:deep(.vc-day) {
  height: 50px;
  min-width: 50px;
}

:deep(.vc-day-content) {
  font-size: 1.1rem !important;
  width: 40px !important;
  height: 40px !important;
}

/* Right Section */
.content-section {
  flex: 1;
  display: flex;
  flex-direction: column;

  min-width: 0;
}

.create-section {
  background: white;
  border-radius: 12px;

  /* box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); */
}

.regular-todo-section {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  /* box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); */
  flex-grow: 1;
}

.section-sub-title {
  color: #2c3e50;
  font-size: 1.2rem;
  font-weight: 500;
  margin-bottom: 1.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid var(--accent);
}

@media (max-width: 1200px) {
  .todo-container {
    flex-direction: column;
  }

  .left-section {
    width: 100%;
    flex: none;
  }

  .calendar-section,
  .program-todo-section {
    min-width: 100%;
  }

  .content-section {
    width: 100%;
  }

  /* 반응형에서의 캘린더 크기 조정 */
  :deep(.vc-day) {
    height: 45px;
    min-width: 45px;
  }

  :deep(.vc-day-content) {
    font-size: 1rem !important;
    width: 35px !important;
    height: 35px !important;
  }
}

@media (max-width: 768px) {
  .todo-page {
    padding: 1rem;
  }

  .calendar-section,
  .program-todo-section,
  .create-section,
  .regular-todo-section {
    padding: 1rem;
  }

  /* 모바일에서의 캘린더 크기 조정 */
  :deep(.vc-day) {
    height: 40px;
    min-width: 40px;
  }

  :deep(.vc-day-content) {
    font-size: 0.9rem !important;
    width: 30px !important;
    height: 30px !important;
  }
}
</style>
