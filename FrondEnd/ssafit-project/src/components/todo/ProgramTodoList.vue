<template>
  <div>
    <div v-if="programTodos.length > 0">
      <!-- 진행중인 프로그램 섹션 -->
      <div class="program-category">
        <h4 class="section-title d-flex gap-2">
          <i class="bi bi-file-earmark-text"></i>
          JADU 프로그램
        </h4>
        <ul class="program-todos">
          <TransitionGroup name="todo-list" tag="div">
            <ProgramTodoListItem
              v-for="todo in inProgressTodos"
              :key="todo.todoId"
              :todo="todo"
              :userId="userId"
              :loginUserId="loginUserId"
            />
          </TransitionGroup>
          <p v-if="inProgressTodos.length === 0" class="empty-message">
            진행중인 프로그램이 없습니다.
          </p>
        </ul>
      </div>

      <!-- 완료 된 프로그램 섹션   -->
      <div class="program-category completed">
        <h4 class="section-title d-flex gap-2">
          <i class="bi bi-check-circle"></i>
          완료된 프로그램
        </h4>
        <ul class="program-todos">
          <TransitionGroup name="todo-list" tag="div">
            <ProgramTodoListItem
              v-for="todo in completedTodos"
              :key="todo.todoId"
              :todo="todo"
              :userId="userId"
              :loginUserId="loginUserId"
            />
          </TransitionGroup>
          <p v-if="completedTodos.length === 0" class="empty-message">
            완료된 프로그램이 없습니다.
          </p>
        </ul>
      </div>
    </div>
    <p v-else class="empty-message">등록된 프로그램이 없습니다.</p>
  </div>
</template>
<script setup>
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { computed, ref, watch } from "vue";
import ProgramTodoListItem from "./ProgramTodoListItem.vue";

const props = defineProps({
  userId: Number,
});

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId;

const editingStates = ref({}); // 수정 상태 관리

// 전체 프로그램 todos 필터링
const programTodos = computed(() => {
  const list = todoStore.todoList;
  if (!Array.isArray(list)) return [];
  return list.filter((todo) => todo.programId != null);
});

// programTodos 변화 감지
watch(
  () => programTodos.value,
  (newList, oldList) => {
    if (newList.length > 0) {
      // 프로그램 투두 리스트 변경 시 수정 상태 초기화
      newList.forEach((todo) => {
        if (!editingStates.value.hasOwnProperty(todo.todoId)) {
          editingStates.value[todo.todoId] = false;
        }
      });
    }
  },
  { deep: true, immediate: true }
);

// 진행중인 프로그램 todos
const inProgressTodos = computed(() => {
  return programTodos.value.filter((todo) => !todo.status);
});

// 완료된 프로그램 todos
const completedTodos = computed(() => {
  return programTodos.value.filter((todo) => todo.status);
});
</script>

<style scoped>
.program-category {
  margin-bottom: 1.5rem;
  padding: 1.5rem;
  background: white;
  border-radius: 12px;
  border: 1px solid #c6e7ff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.program-category:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.program-category.completed {
  border-color: #d4f6ff;
  /* background: #fbfbfb; */
}

.section-title {
  color: #133e87;
  margin-bottom: 1.5rem;
  font-size: 1.25rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid #ffddae;
}

.title-icon {
  margin-right: 0.5rem;
  font-size: 1.3rem;
}

.program-todos {
  list-style: none;
  padding: 0;
  margin: 0;
}

.empty-message {
  text-align: center;
  color: #666;
  padding: 1rem;
  font-style: italic;
  background: #fbfbfb;
  border-radius: 8px;
  margin: 0.5rem 0;
}

.todo-list-enter-active,
.todo-list-leave-active {
  transition: all 0.3s ease;
}

.todo-list-enter-from,
.todo-list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

/* 반응형 디자인을 위한 미디어 쿼리 */
@media (max-width: 768px) {
  .program-category {
    padding: 1rem;
    margin-bottom: 1rem;
  }

  .section-title {
    font-size: 1.1rem;
    margin-bottom: 1rem;
  }

  .title-icon {
    font-size: 1.1rem;
  }
}
</style>
