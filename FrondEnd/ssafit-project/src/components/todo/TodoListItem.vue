<template>
  <li :key="todo.todoId" class="todo-item">
    <div class="todo-main">
      <label class="todo-label" @click.stop>
        <input
          type="checkbox"
          :checked="todo.status"
          @change="toggleTodo(todo)"
          class="todo-checkbox"
        />
        <span class="todo-content" v-if="!editingStates[todo.todoId]">
          {{ todo.content }}
        </span>
        <input
          v-else
          type="text"
          v-model="todo.content"
          @blur="saveUpdateTodo(todo)"
          @keyup.enter.prevent="saveUpdateTodo(todo)"
        />
      </label>
    </div>
    <div class="todo-actions" v-if="userId === loginUserId">
      <button class="action-btn edit-btn" @click="onClickUpdateTodo(todo)">
        <!-- {{ editingStates[todo.todoId] ? "완료" : "수정" }} -->
        수정
      </button>
      <button class="action-btn delete-btn" @click="onClickDeleteTodo(todo)">
        삭제
      </button>
    </div>
  </li>
</template>

<script setup>
import { useTodoStore } from "@/stores/todo";
import { ref, watch } from "vue";

const todoStore = useTodoStore();

const props = defineProps({
  todo: Object, // 투두 객체
  userId: Number, // 유저 아이디
  loginUserId: Number, // 로그인한 유저 아이디
});

const editingStates = ref({}); // 수정 상태

watch(
  () => props.todo.todoId,
  (newId) => {
    if (!editingStates.value.hasOwnProperty(newId)) {
      editingStates.value[newId] = false;
    }
  }
);
// 투두 상태 변경 시, toggle
const toggleTodo = async (todo) => {
  todo.status = !todo.status;

  await todoStore.updateTodoStatus(props.userId, todo.todoId, todo.status);
};

// 투두 수정하기
const onClickUpdateTodo = (todo) => {
  editingStates.value[todo.todoId] = !editingStates.value[todo.todoId];
};

const saveUpdateTodo = async (todo) => {
  if (!todo.content.trim()) {
    return; // 내용이 없으면 반환
  }

  // 서버에 수정된 내용 업데이트
  await todoStore.updateTodoContent(props.userId, todo.todoId, todo);
  editingStates.value[todo.todoId] = false; // 수정 모드 종료
};

// 투두 삭제하기
const onClickDeleteTodo = (todo) => {
  todoStore.deleteTodo(props.userId, todo.todoId);
};
</script>

<style scoped>
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
  /* cursor: pointer; */
  gap: 1rem;
}

.todo-checkbox {
  width: 1.2rem;
  height: 1.2rem;
  cursor: pointer;
  accent-color: #42b983;
}

/* 텍스트 */
.todo-content {
  color: #333;
  font-size: 1rem;
}

/* .todo-content.completed {
  text-decoration: line-through;
  color: #888;
} */

/* Input 스타일 */
.todo-label input[type="text"] {
  border: none;
  border-bottom: 2px solid #42b983; /* 밑줄 */
  background: transparent;
  outline: none;
  color: #333;
  font-size: 1rem;
  padding: 0.2rem 0;
  transition: border-color 0.3s ease;
}

.todo-label input[type="text"]:focus {
  border-bottom: 2px solid #357abd; /* 포커스 시 색 변경 */
}

.todo-label input[type="text"]::placeholder {
  color: #aaa;
  font-style: italic;
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
