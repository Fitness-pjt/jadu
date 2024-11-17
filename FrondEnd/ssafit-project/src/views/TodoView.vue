<template>
  <div class="todo-list">
    <h1>✨ My Cute Todo List ✨</h1>
    <div v-if="userId == loginUserId">
      <TodoCreate />
    </div>
    <TodoList />
  </div>
</template>

<script setup>
import { useTodoStore } from "@/stores/todo";
import { onMounted, ref } from "vue";
import { useLoginStore } from "@/stores/login";
import TodoCreate from "@/components/todo/TodoCreate.vue";
import TodoList from "@/components/todo/TodoList.vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId;

const props = defineProps({
  userId: Number,
});

// TodoList 불러오기
onMounted(() => {
  todoStore.getTodoList(props.userId, "2024-11-17");
});
</script>

<style scoped>
/* 전체 Todo List 스타일 */
.todo-list {
  max-width: 400px;
  margin: 2rem auto;
  padding: 1.5rem;
  text-align: center;
  font-family: "Comic Sans MS", "Arial", sans-serif;

  border-radius: 15px;

  transition: transform 0.2s ease-in-out;
}

.todo-list:hover {
  transform: scale(1.02);
}

/* 제목 스타일 */
.todo-list h1 {
  font-size: 1.8rem;
  margin-bottom: 1.5rem;
  color: #42b983;
  text-shadow: 1px 1px 2px #fff;
}

/* Todo 리스트 스타일 */
.todo-items {
  list-style: none;
  padding: 0;
  margin: 0;
}

/* 개별 Todo 아이템 스타일 */
.todo-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.8rem;
  margin-bottom: 0.5rem;
  border-radius: 10px;
  background-color: #e9f5ef;
  border: 1px solid #42b983;
  box-shadow: 2px 2px 5px rgba(66, 185, 131, 0.2);
  transition: transform 0.2s ease-in-out, background-color 0.3s ease;
}

.todo-item:hover {
  transform: scale(1.02);
  background-color: #dff0e9;
}

/* 날짜와 내용 스타일 */
.todo-date {
  font-weight: bold;
  color: #42b983;
}

.todo-content {
  color: #333;
  font-style: italic;
}
</style>
