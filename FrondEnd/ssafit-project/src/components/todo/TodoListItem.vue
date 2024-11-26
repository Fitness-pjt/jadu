<template>
  <li class="todo-item card mb-3" :class="{ completed: todo.status }">
    <div class="card-body d-flex justify-content-between align-items-center">
      <!-- Todo Main Content -->
      <div class="d-flex align-items-center flex-grow-1">
        <div class="form-check me-3">
          <input
            type="checkbox"
            class="form-check-input"
            :checked="todo.status"
            @change="toggleTodo(todo)"
            :disabled="loginStore.loginUserId !== todo.userId"
          />
        </div>

        <div class="todo-content">
          <span v-if="!editingStates[todo.todoId]" class="todo-text">
            {{ todo.content }}
          </span>
          <input
            v-else
            type="text"
            class="form-control"
            v-model="todo.content"
            @blur="saveUpdateTodo(todo)"
            @keyup.enter.prevent="saveUpdateTodo(todo)"
          />
        </div>
      </div>

      <!-- Action Buttons -->
      <div class="d-flex align-items-center" style="width: 150px !important">
        <!-- Like Button -->
        <div v-if="todo.status" class="me-2">
          <button
            @click="updateFavorite(todo)"
            class="btn btn-outline-danger btn-sm border-0"
          >
            <i :class="[isFavorite ? 'bi bi-heart-fill' : 'bi bi-heart']"></i>
            <span class="ms-1">{{ likeCount }}</span>
          </button>
        </div>

        <!-- Edit/Delete Buttons -->
        <div v-if="userId === loginUserId" class="btn-group">
          <button
            @click="onClickUpdateTodo(todo)"
            class="btn btn-outline-secondary btn-sm"
          >
            <i class="bi bi-pencil"></i>
          </button>
          <button
            @click="onClickDeleteTodo(todo)"
            class="btn btn-outline-danger btn-sm"
          >
            <i class="bi bi-trash"></i>
          </button>
        </div>
      </div>
    </div>
  </li>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { onMounted, ref, watch, computed } from "vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();

const props = defineProps({
  todo: Object, // 투두 객체
  userId: Number, // 유저 아이디
  loginUserId: Number, // 로그인한 유저 아이디
});

const editingStates = ref({}); // 수정 상태

const isFavorite = computed(() => {
  return todoStore.todoLikes.get(props.todo.todoId) || false;
});
const likeCount = computed(() => {
  return todoStore.todoLikeCounts.get(props.todo.todoId) || 0;
});

// 초기 완료 상태 설정
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
  if (loginStore.loginUserId === todo.userId) {
    todo.status = !todo.status;
    await todoStore.updateTodoStatus(props.userId, todo.todoId, todo.status);
  }
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

// 초기 좋아요 상태 설정
const fetchFavoriteStatus = async () => {
  try {
    const isLiked = await todoStore.getTodoLikesStatus(
      props.todo.todoId,
      props.loginUserId
    );
    isFavorite.value[props.todo.todoId] = isLiked; // 개별 todoId 의 상태 업데이트
  } catch (error) {
    console.error("Failed to fetch favorite status :>> ", error);
  }
};

const updateFavorite = async (todo) => {
  try {
    if (!isFavorite.value) {
      await todoStore.pushTodoLikes(todo.todoId, props.loginUserId);
    } else {
      await todoStore.cancelTodoLikes(todo.todoId, props.loginUserId);
    }
    // 좋아요 개수 갱신
    await todoStore.getTodoLikeCount(todo.todoId, props.loginUserId);
  } catch (error) {
    console.error("Failed to update favorite status:", error);
  }
};
const fetchInitialStatus = async () => {
  try {
    await Promise.all([
      todoStore.getTodoLikesStatus(props.todo.todoId, props.loginUserId),
      todoStore.getTodoLikeCount(props.todo.todoId, props.loginUserId),
    ]);
  } catch (error) {
    console.error("Failed to fetch initial status:", error);
  }
};

// 초기 상태 설정 (마운트 시에 한 번만 호출)
onMounted(fetchInitialStatus);
</script>

<style scoped>
.todo-item {
  /* border-bottom: 1px solid rgba(0, 0, 0, 0.125); */
  border: none;
  transition: all 0.3s ease;
  padding: auto;
}

.todo-item:hover {
  /* box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15); */
}

.todo-item.completed {
  /* background-color: rgba(0, 0, 0, 0.05); */
}

.todo-content {
  flex: 1;
}

.todo-text {
  font-size: 1rem;
  color: #2c3e50;
}

.heart-with-count {
  position: relative;
  display: inline-block;
}

.heart-icon {
  font-size: 1.5rem;
  display: inline-block;
}

.like-count-overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 0.7rem;
  font-weight: bold;
  color: #000;
  text-shadow: -1px -1px 0 #fff, 1px -1px 0 #fff, -1px 1px 0 #fff,
    1px 1px 0 #fff;
  pointer-events: none;
}

/* 프로젝트 메인 컬러 적용 */
.btn-outline-primary {
  --bs-btn-color: #c6e7ff;
  --bs-btn-border-color: #c6e7ff;
  --bs-btn-hover-bg: #c6e7ff;
  --bs-btn-hover-border-color: #c6e7ff;
}

.form-check-input:checked {
  background-color: #133e87;
  border-color: #133e87;
}

.btn-outline-secondary,
.btn-outline-danger {
  border: none;
  background-color: transparent;
}
@media (max-width: 576px) {
  .btn-group {
    flex-direction: column;
  }

  .todo-content {
    font-size: 0.9rem;
  }
}
</style>
