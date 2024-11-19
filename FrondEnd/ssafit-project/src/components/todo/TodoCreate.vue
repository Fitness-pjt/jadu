<template>
  <div>
    <form class="todo-form">
      <!-- 내용 입력 -->
      <input
        type="text"
        placeholder="📝 할 일 입력"
        class="todo-content-input"
        v-model="todo.content"
      />
      <!-- 추가 버튼 -->
      <button type="button" class="add-button" @click="addTodo">
        추가하기
      </button>
    </form>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { ref } from "vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();

const loginUserId = loginStore.loginUserId;

// console.log("loginUserId", loginUserId);

const todo = ref({
  date: "",
  content: "",
});

// Todo 추가하기
const addTodo = () => {
  todoStore.addTodo(todo.value, loginUserId);
};
</script>

<style scoped>
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
}
</style>
