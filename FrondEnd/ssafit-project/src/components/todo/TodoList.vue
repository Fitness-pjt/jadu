<template>
  <div>
    <div v-if="todoStore.todoList.length > 0">
      <h3 class="date-header">📅 {{ selectedDate }}</h3>
      <ul class="todo-items">
        <li v-for="todo in todoList" :key="todo.todoId" class="todo-item">
          <div class="todo-main">
            <label class="todo-label">
              <input
                type="checkbox"
                :checked="todo.status"
                @change="toggleTodo(todo)"
                class="todo-checkbox"
              />
              <span class="todo-content" :class="{ completed: todo.status }">
                {{ todo.content }}
              </span>
            </label>
          </div>
          <div class="todo-actions" v-if="userId === loginUserId">
            <button class="action-btn edit-btn">수정</button>
            <button
              class="action-btn delete-btn"
              @click="onClickDeleteTodo(todo)"
            >
              삭제
            </button>
          </div>
        </li>
      </ul>
    </div>
    <p v-else>작성된 투두가 없습니다.</p>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { computed, watch } from "vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId; // 로그인한 유저 아이디

const props = defineProps({
  userId: Number,
});

const selectedDate = computed(() => todoStore.selectedDate);
const todoList = computed(() => todoStore.todoList);

// 날짜 변화 감지
watch(selectedDate, (newDate) => {
  todoStore.getTodoList(props.userId, newDate);
});

// todoList 변화 감지
watch(
  () => todoList.value,
  (newList, oldList) => {
    console.log("TodoList 변경됨 :>> ", { newList, oldList });
  },
  { deep: true }
);

// 투두 상태 변경 시, toggle
const toggleTodo = (todo) => {
  todo.status = !todo.status;
};

// 투두 수정하기

// 투두 삭제하기
const onClickDeleteTodo = (todo) => {
  todoStore.deleteTodo(props.userId, todo.todoId);
};
</script>

<style scoped>
.date-group {
  margin-bottom: 2rem;
}

.date-header {
  color: #42b983;
  font-size: 1.2rem;
  margin-bottom: 1rem;
  padding-left: 0.5rem;
}

.todo-items {
  list-style: none;
  padding: 0;
  margin: 0;
}

.todo-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.8rem 1rem;
  margin-bottom: 0.5rem;
  border-radius: 10px;
  background-color: #e9f5ef;
  border: 1px solid #42b983;
  box-shadow: 2px 2px 5px rgba(66, 185, 131, 0.2);
  transition: transform 0.2s ease-in-out, background-color 0.3s ease;
}

.todo-item:hover {
  /* transform: scale(1.01); */
  background-color: #dff0e9;
}

.todo-main {
  flex: 1;
}

.todo-label {
  display: flex;
  align-items: center;
  cursor: pointer;
  gap: 1rem;
}

.todo-checkbox {
  width: 1.2rem;
  height: 1.2rem;
  cursor: pointer;
  accent-color: #42b983;
}

.todo-content {
  color: #333;
  font-size: 1rem;
}

.todo-content.completed {
  text-decoration: line-through;
  color: #888;
}

.todo-actions {
  display: flex;
  gap: 0.5rem;
}

.action-btn {
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  font-size: 0.9rem;
}

.edit-btn {
  background-color: #4a90e2;
  color: white;
}

.edit-btn:hover {
  background-color: #357abd;
}

.delete-btn {
  background-color: #e25c5c;
  color: white;
}

.delete-btn:hover {
  background-color: #c54646;
}
</style>
