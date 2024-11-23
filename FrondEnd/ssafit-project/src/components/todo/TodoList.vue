<template>
  <div>
    <h3 class="date-header">📅 {{ selectedDate }}</h3>
    <div v-if="todoList.length > 0" :key="todoListKey">
      <ul class="todo-items">
        <!-- 진행 중인 투두 -->
        <h4>진행 중</h4>
        <ul class="todo-items">
          <TodoListItem
            v-for="todo in isNotDoneTodoList"
            :key="todo.todoId"
            :todo="todo"
            :userId="userId"
            :loginUserId="loginUserId"
          />
        </ul>

        <!-- 완료된 투두 -->
        <h4>완료된 투두</h4>
        <ul class="todo-items">
          <TodoListItem
            v-for="todo in isDoneTodoList"
            :key="todo.todoId"
            :todo="todo"
            :userId="userId"
            :loginUserId="loginUserId"
          />
        </ul>
      </ul>
    </div>
    <p v-else>작성된 투두가 없습니다.</p>
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
const todoListKey = ref(0);

const selectedDate = computed(() => todoStore.selectedDate);
// const todoList = computed(() => todoStore.todoList);
const editingStates = ref({}); // 수정 상태

const props = defineProps({
  userId: Number,
});

// todoList 변화 시 key 업데이트
watch(
  () => todoList.value,
  () => {
    todoListKey.value += 1;
  }
);

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
</style>
