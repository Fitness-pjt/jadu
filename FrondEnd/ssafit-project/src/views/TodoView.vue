<template>
  <div class="todo-page">
    <h1 class="todo-title">✨ My Todo List ✨</h1>
    <div class="todo-container">
      <div class="calendar-section">
        <!-- <TodoCalendar @dateSelect="handleDateSelect" :userId="userId" /> -->
        <TodoVCalendar />
      </div>
      <div class="content-section">
        <div v-if="userId == loginUserId" class="todo-create">
          <TodoCreate />
        </div>
        <TodoList :userId="userId" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { useTodoStore } from "@/stores/todo";
import { onMounted } from "vue";
import { useLoginStore } from "@/stores/login";
import TodoCreate from "@/components/todo/TodoCreate.vue";
import TodoList from "@/components/todo/TodoList.vue";
import TodoCalendar from "@/components/todo/TodoCalendar.vue";
import TodoVCalendar from "@/components/todo/TodoVCalendar.vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId;

const props = defineProps({
  userId: Number,
});

onMounted(() => {
  todoStore.getTodoList(props.userId, "2024-11-18");
});

const handleDateSelect = (date) => {
  // 선택된 날짜의 Todo 리스트를 가져옵니다
  todoStore.getTodoList(props.userId, date);
};
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
