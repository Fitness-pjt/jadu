<template>
  <div class="todo-page">
    <h1 class="todo-title">✨ My Todo List ✨</h1>
    <div class="todo-container">
      <div class="calendar-section">
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
import TodoCreate from "@/components/todo/TodoCreate.vue";
import TodoList from "@/components/todo/TodoList.vue";
import TodoVCalendar from "@/components/todo/TodoVCalendar.vue";
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { computed, onMounted, ref } from "vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId;

const props = defineProps({
  userId: Number,
});

// 컴포넌트 마운트 시 초기 투두 목록을 불러옴
const selectedDate = computed(() => todoStore.selectedDate);

onMounted(() => {
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
