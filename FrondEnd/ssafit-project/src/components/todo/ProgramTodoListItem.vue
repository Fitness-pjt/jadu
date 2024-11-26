<template>
  <div class="program-todo-item card mb-3" :class="{ completed: todo.status }">
    <div class="card-body">
      <!-- Todo Main Content -->
      <div class="d-flex align-items-start gap-1">
        <!-- 체크 박스 -->
        <div class="form-check me-3">
          <input
            type="checkbox"
            class="form-check-input"
            :checked="todo.status"
            @change="toggleStatus"
            :disabled="loginUserId !== todo.userId"
          />
        </div>

        <!-- 제목 + 상세보기 버튼  -->
        <div class="flex-grow-1">
          <h5 class="card-title mb-2">{{ programTitle }}</h5>
          <div class="workout-content mb-2">
            <span v-if="!editingStates[todo.todoId]" class="text-muted">
              {{ todo.content }}
            </span>
            <input
              v-else
              type="text"
              class="form-control"
              v-model="todo.content"
              @blur="saveUpdateTodo(todo)"
              @keyup.enter="saveUpdateTodo(todo)"
            />
          </div>

          <!-- 상세보기 버튼 -->
          <div
            class="d-flex flex-row justify-content-between align-items-center gap-2"
          >
            <button
              v-if="todo.videoId"
              @click="showModal = true"
              class="btn btn-navy btn-sm"
            >
              <i class="bi bi-play-circle me-1"></i>
              상세보기
            </button>
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="d-flex align-items-center" style="width: 150px !important">
          <!-- 좋아요 버튼 -->
          <div v-if="todo.status" class="me-2">
            <button
              @click="updateFavorite(todo)"
              class="btn btn-outline-danger btn-sm border-0"
            >
              <i :class="[isFavorite ? 'bi bi-heart-fill' : 'bi bi-heart']"></i>
              <span class="ms-1">{{ likeCount }}</span>
            </button>
          </div>
          <!-- 수정, 삭제 버튼  -->
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
    </div>

    <!-- Detail Modal -->
    <div v-if="showModal" class="modal show d-block" tabindex="-1">
      <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ programTitle }}</h5>
            <button
              type="button"
              class="btn-close"
              @click="showModal = false"
            ></button>
          </div>

          <div class="modal-body">
            <!-- Video Player -->
            <div class="ratio ratio-16x9 mb-3">
              <iframe
                :src="'https://www.youtube.com/embed/' + todo.videoId"
                allowfullscreen
              ></iframe>
            </div>

            <!-- Program Details -->
            <div v-if="programDetails" class="card">
              <div class="card-body">
                <div class="mb-2">
                  <i class="bi bi-calendar3 me-2"></i>
                  기간: {{ programDetails.durationWeeks }}주
                </div>
                <div class="mb-2">
                  <i class="bi bi-person-walking me-2"></i>
                  난이도: {{ programDetails.level }}
                </div>
                <p class="mb-0">{{ programDetails.description }}</p>
              </div>
            </div>
          </div>

          <div class="modal-footer">
            <div class="form-check me-auto">
              <input
                type="checkbox"
                class="form-check-input"
                :checked="todo.status"
                @change="toggleStatus"
                :disabled="loginUserId !== todo.userId"
              />
              <label class="form-check-label">완료 상태</label>
            </div>
            <div class="button-group">
              <button
                v-if="todo.status"
                @click="updateFavorite(todo)"
                class="btn btn-outline-danger me-2"
              >
                <i
                  :class="[isFavorite ? 'bi bi-heart-fill' : 'bi bi-heart']"
                ></i>
                <span class="ms-1">{{ likeCount }}</span>
              </button>
              <RouterLink
                :to="{
                  name: 'programDetail',
                  params: { programId: todo.programId },
                }"
                class="btn btn-navy me-2"
              >
                <i class="bi bi-eye-fill me-1"></i>
                프로그램 상세보기
              </RouterLink>
              <button
                type="button"
                class="btn btn-gray"
                @click="showModal = false"
              >
                닫기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="showModal" class="modal-backdrop fade show"></div>
  </div>
</template>

<style scoped>
.card-title {
  font-size: 1.2rem;
}

.program-todo-item {
  border: 1px solid rgba(0, 0, 0, 0.125);
  transition: all 0.3s ease;
}

.program-todo-item:hover {
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
}

.workout-content {
  font-size: 0.95rem;
}

.modal-backdrop {
  /* background-color: rgba(0, 0, 0, 0.5); */
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

.button-group {
  display: flex;
  align-items: center;
}

.btn-primary {
  background-color: #c6e7ff;
  border-color: #c6e7ff;
  color: #133e87;
}

.btn-primary:hover {
  background-color: #d4f6ff;
  border-color: #d4f6ff;
  color: #133e87;
}

.btn-outline-secondary,
.btn-outline-danger {
  border: none;
  background-color: transparent;
}

@media (max-width: 768px) {
  .btn-group {
    display: flex;
    flex-direction: column;
  }

  .workout-content {
    font-size: 0.9rem;
  }

  .modal-footer {
    flex-direction: column;
    gap: 1rem;
  }

  .button-group {
    width: 100%;
    justify-content: flex-end;
    gap: 0.5rem;
  }
}
</style>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { useProgramStore } from "@/stores/program";

const props = defineProps({
  todo: {
    type: Object,
    required: true,
  },
  userId: {
    type: Number,
    required: true,
  },
  loginUserId: {
    type: Number,
    required: true,
  },
});

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const programStore = useProgramStore();

const showModal = ref(false);
const editingStates = ref({});

const isFavorite = computed(
  () => todoStore.todoLikes.get(props.todo.todoId) || false
);
const likeCount = computed(
  () => todoStore.todoLikeCounts.get(props.todo.todoId) || 0
);

const programTitle = computed(() => {
  const program = programStore.programs.find(
    (p) => p.programId === props.todo.programId
  );
  return program ? program.title : "프로그램";
});

const programDetails = computed(() => {
  return programStore.programs.find(
    (p) => p.programId === props.todo.programId
  );
});

// Methods
const toggleStatus = async () => {
  if (loginStore.loginUserId === props.todo.userId) {
    try {
      props.todo.status = !props.todo.status;
      await todoStore.updateTodoStatus(
        props.userId,
        props.todo.todoId,
        props.todo.status
      );
    } catch (error) {
      props.todo.status = !props.todo.status;
      console.error("Status update failed:", error);
    }
  }
};

const updateFavorite = async (todo) => {
  try {
    if (!isFavorite.value) {
      await todoStore.pushTodoLikes(todo.todoId, props.loginUserId);
    } else {
      await todoStore.cancelTodoLikes(todo.todoId, props.loginUserId);
    }
    await todoStore.getTodoLikeCount(todo.todoId, props.loginUserId);
  } catch (error) {
    console.error("Favorite update failed:", error);
  }
};

const onClickUpdateTodo = (todo) => {
  editingStates.value[todo.todoId] = !editingStates.value[todo.todoId];
};

const saveUpdateTodo = async (todo) => {
  if (!todo.content.trim()) return;
  try {
    await todoStore.updateTodoContent(props.userId, todo.todoId, todo);
    editingStates.value[todo.todoId] = false;
  } catch (error) {
    console.error("Content update failed:", error);
  }
};

const onClickDeleteTodo = (todo) => {
  if (confirm("정말 이 할 일을 삭제하시겠습니까?")) {
    todoStore.deleteTodo(props.userId, todo.todoId);
  }
};

watch(
  () => props.todo.todoId,
  (newId) => {
    if (!editingStates.value.hasOwnProperty(newId)) {
      editingStates.value[newId] = false;
    }
  }
);

onMounted(() => {
  programStore.getAllPrograms();
});
</script>
