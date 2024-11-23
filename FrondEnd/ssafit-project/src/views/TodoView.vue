<template>
  <div class="todo-page">
    <h1 class="todo-title">✨ My Todo List ✨</h1>
    <div class="todo-container">
      <div class="calendar-section">
        <TodoVCalendar />
      </div>
      <div class="content-section">
        <!-- 프로그램 투두 리스트 -->
        <ProgramTodoListItem :userId="userId" />
        
        <!-- 일반 투두 섹션 -->
        <div class="regular-todo-section">
          <h3 class="section-title">📝 Todo List</h3>
          <div v-if="userId == loginUserId" class="todo-create">
            <TodoCreate />
          </div>
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
import ProgramTodoListItem from '@/components/todo/ProgramTodoList.vue';

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId;

const props = defineProps({
  userId: Number,
});

// 컴포넌트 마운트 시 초기 투두 목록을 불러옴
const selectedDate = computed(() => todoStore.selectedDate);

onMounted(() => {
  const today = new Date().toLocaleDateString(); // 한국 날짜 기준으로 날짜 불러오기 => 2024.11.20 형식으로 날짜 들어옴
  // 2024-11-20 형식으로 날짜 바꿔주기 : DB와 통신하기 위함
  const formattedDate = today
    .split(".")
    .map((item) => item.trim())
    .join("-")
    .slice(0, 10);
  todoStore.setSelectedDate(formattedDate); // 기본 날짜 설정

  todoStore.getTodoList(props.userId, selectedDate.value);
});
</script>

<style scoped>
.todo-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.todo-title {
  text-align: center;
  font-size: 2rem;
  font-weight: bold;
  color: #42b983;
  margin-bottom: 2rem;
}

.todo-container {
  display: flex;
  gap: 2rem;
  min-height: 600px;
}

.calendar-section {
  min-width: 300px;
  background: white;
  border-radius: 12px;
  padding: 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.content-section {
  flex: 1;
  background: white;
  border-radius: 12px;
  padding: 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.todo-create {
  margin-bottom: 1.5rem;
  padding: 1rem;
  border-bottom: 1px solid #eee;
}

@media (max-width: 768px) {
  .todo-container {
    flex-direction: column;
  }

  .calendar-section {
    min-width: 100%;
  }
}
</style>
