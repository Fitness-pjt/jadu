<template>
  <div class="todo-list">
    <h4 class="date-header mb-4 gap-2">
      <i class="bi bi-calendar3"></i>
      {{ selectedDate }}
    </h4>

    <div v-if="todoStore.todoList.length > 0">
      <!-- 진행 중인 투두 -->
      <div class="todo-section mb-4">
        <h4 class="section-title">
          <i class="bi bi-hourglass-split me-2"></i>진행 중
        </h4>
        <ul class="todo-items list-unstyled">
          <TodoListItem
            v-for="todo in isNotDoneTodoList"
            :key="todo.todoId"
            :todo="todo"
            :userId="userId"
            :loginUserId="loginUserId"
          />
        </ul>
      </div>

      <!-- 완료된 투두 -->
      <div class="todo-section">
        <h4 class="section-title">
          <i class="bi bi-check2-circle me-2"></i>완료됨
        </h4>
        <ul class="todo-items list-unstyled">
          <TodoListItem
            v-for="todo in isDoneTodoList"
            :key="todo.todoId"
            :todo="todo"
            :userId="userId"
            :loginUserId="loginUserId"
          />
        </ul>
      </div>
    </div>

    <div v-else class="no-todos text-center py-4">
      <i class="bi bi-clipboard-x display-4 text-muted"></i>
      <p class="text-muted mt-2">작성된 투두가 없습니다.</p>
    </div>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { computed, ref, watch } from "vue";
import TodoListItem from "./TodoListItem.vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId; // 로그인한 유저 아이디

const selectedDate = computed(() => todoStore.selectedDate);
// const todoList = computed(() => todoStore.todoList);
const editingStates = ref({}); // 수정 상태

const props = defineProps({
  userId: Number,
});

const nonProgramTodos = computed(() => {
  const todoList = todoStore.todoList;
  if (!Array.isArray(todoList)) return [];
  return todoList.filter((todo) => todo.programId == null);
});

// 날짜 변화 감지
watch(selectedDate, async (newDate) => {
  await todoStore.getTodoList(props.userId, newDate);
});

// todoList 변화 감지
watch(
  () => nonProgramTodos.value,
  (newList, oldList) => {
    if (newList.length > 0) {
      // 투두 리스트 변경 시 수정 상태 초기화
      newList.forEach((todo) => {
        if (!editingStates.value.hasOwnProperty(todo.todoId)) {
          editingStates.value[todo.todoId] = false;
        }
      });
    }
  },
  { deep: true, immediate: true }
);

// 완료된 투두 리스트
const isDoneTodoList = computed(() => {
  return nonProgramTodos.value.filter((todo) => todo.status === true);
});

// 완료되지 않은 투두 리스트
const isNotDoneTodoList = computed(() => {
  return nonProgramTodos.value.filter((todo) => todo.status === false);
});
</script>

<style scoped>
.todo-list {
  margin-bottom: 1.5rem;
  padding: 1.5rem;
  background: white;
  border-radius: 12px;
  border: 1px solid #c6e7ff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.date-header {
  color: #133e87;
  margin-bottom: 1.5rem;
  font-size: 1.25rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid #ffddae;
}

.section-title {
  color: #2c3e50;
  font-size: 1.1rem;
  margin-bottom: 1rem;
  font-weight: 500;
}

.todo-section {
  margin-bottom: 1.5rem;
  padding: 1.5rem;
  background: white;
  border-radius: 12px;
  border: 1px solid #c6e7ff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.todo-section + .todo-section {
  margin-top: 1.5rem;
}

.no-todos {
  color: #6c757d;
  font-size: 1.1rem;
}

@media (max-width: 768px) {
  .todo-list {
    padding: 1rem;
  }

  .section-title {
    font-size: 1rem;
  }

  .todo-section {
    padding: 1rem;
  }
}
</style>
