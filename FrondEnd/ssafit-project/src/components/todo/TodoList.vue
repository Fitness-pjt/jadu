<template>
  <div class="todo-list">
    <h1>✨ My Cute Todo List ✨</h1>
    <form class="todo-form">
      <!-- 날짜 입력 -->
      <input
        type="text"
        placeholder="📅 날짜 입력 (e.g., YYYY-MM-DD)"
        class="todo-date-input"
        v-model="todo.date"
      />
      <!-- 내용 입력 -->
      <input
        type="text"
        placeholder="📝 할 일 입력"
        class="todo-content-input"
        v-model="todo.content"
      />
      <!-- 추가 버튼 -->
      <button type="button" class="add-button">추가하기</button>
    </form>
    <ul class="todo-items">
      <li class="todo-item">
        <span class="todo-date">📅 2024-11-17</span>
        <span class="todo-content">📝 Sample Todo Content</span>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { useTodoStore } from "@/stores/todo";
import { onMounted, ref } from "vue";

const todoStore = useTodoStore();
onMounted(() => {
  todoStore.getTodoList("1", "2024-11-");
});
const todo = ref({
  date: "",
  content: "",
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
  background-color: #fefae0;
  border-radius: 15px;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
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

/* 입력 폼 스타일 */
.todo-form {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  margin-bottom: 1.5rem;
}

/* 입력 필드 공통 스타일 */
.todo-date-input,
.todo-content-input {
  width: 90%;
  padding: 0.8rem;
  border: 2px solid #42b983;
  border-radius: 8px;
  font-size: 1rem;
  box-shadow: 2px 2px 6px rgba(66, 185, 131, 0.2);
  transition: box-shadow 0.3s ease, border-color 0.3s ease;
}

.todo-date-input:focus,
.todo-content-input:focus {
  outline: none;
  border-color: #2a9d71;
  box-shadow: 0 0 10px #42b983;
}

/* 추가 버튼 스타일 */
.add-button {
  padding: 0.8rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: bold;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.add-button:hover {
  background-color: #2a9d71;
  transform: translateY(-3px);
}

.add-button:active {
  transform: translateY(1px);
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
