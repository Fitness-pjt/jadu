<template>
  <div class="program-todo-item card mb-3" :class="{ 'completed': todo.status }">
    <div class="card-body">
      <!-- Todo Main Content -->
      <div class="d-flex align-items-start">
        <div class="form-check me-3">
          <input type="checkbox" class="form-check-input" :checked="todo.status" @change="toggleStatus"
            :disabled="loginUserId !== todo.userId">
        </div>

        <div class="flex-grow-1">
          <h5 class="card-title mb-2">{{ programTitle }}</h5>
          <div class="workout-content mb-2">
            <span v-if="!editingStates[todo.todoId]" class="text-muted">
              {{ todo.content }}
            </span>
            <input v-else type="text" class="form-control" v-model="todo.content" @blur="saveUpdateTodo(todo)"
              @keyup.enter="saveUpdateTodo(todo)" />
          </div>

          <!-- 상세보기 버튼 -->
          <button v-if="todo.videoId" @click="showModal = true" class="btn btn-outline-primary btn-sm">
            <i class="bi bi-play-circle me-1"></i>
            상세보기
          </button>
        </div>

        <!-- Action Buttons -->
        <div class="d-flex align-items-center gap-2">
          <div v-if="todo.status" class="me-2">
            <button @click="updateFavorite(todo)" class="btn btn-outline-danger btn-sm border-0">
              <i :class="[isFavorite ? 'bi bi-heart-fill' : 'bi bi-heart']"></i>
              <span class="ms-1">{{ likeCount }}</span>
            </button>
          </div>

          <div v-if="userId === loginUserId" class="btn-group">
            <button @click="onClickUpdateTodo(todo)" class="btn btn-outline-secondary btn-sm">
              <i class="bi bi-pencil"></i>
            </button>
            <button @click="onClickDeleteTodo(todo)" class="btn btn-outline-danger btn-sm">
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
            <button type="button" class="btn-close" @click="showModal = false"></button>
          </div>

          <div class="modal-body">
            <!-- Video Player -->
            <div class="ratio ratio-16x9 mb-3">
              <iframe :src="'https://www.youtube.com/embed/' + todo.videoId" allowfullscreen></iframe>
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
              <input type="checkbox" class="form-check-input" :checked="todo.status" @change="toggleStatus"
                :disabled="loginUserId !== todo.userId">
              <label class="form-check-label">완료 상태</label>
            </div>
            <div class="button-group">
              <RouterLink :to="{ name: 'programDetail', params: { programId: todo.programId } }"
                class="btn btn-primary me-2">
                <i class="bi bi-eye-fill me-1"></i>
                프로그램 상세보기
              </RouterLink>
              <button v-if="todo.status" @click="updateFavorite(todo)" class="btn btn-outline-danger me-2">
                <i :class="[isFavorite ? 'bi bi-heart-fill' : 'bi bi-heart']"></i>
                <span class="ms-1">{{ likeCount }}</span>
              </button>
              <button type="button" class="btn btn-secondary" @click="showModal = false">
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
.program-todo-item {
  border: 1px solid rgba(0, 0, 0, 0.125);
  transition: all 0.3s ease;
}

.program-todo-item:hover {
  box-shadow: 0 .5rem 1rem rgba(0, 0, 0, .15);
}

.program-todo-item.completed {
  background-color: rgba(0, 0, 0, 0.05);
}

.workout-content {
  font-size: 0.95rem;
}

.modal-backdrop {
  background-color: rgba(0, 0, 0, 0.5);
}

/* 프로젝트 메인 컬러 적용 */
.btn-outline-primary {
  --bs-btn-color: #C6E7FF;
  --bs-btn-border-color: #C6E7FF;
  --bs-btn-hover-bg: #C6E7FF;
  --bs-btn-hover-border-color: #C6E7FF;
}

.form-check-input:checked {
  background-color: #D4F6FF;
  border-color: #D4F6FF;
}

.card {
  background-color: #FBFBFB;
}

.btn:focus,
.form-check-input:focus {
  box-shadow: 0 0 0 0.25rem rgba(255, 221, 174, 0.25);
}.button-group {
  display: flex;
  align-items: center;
}

.btn-primary {
  background-color: #C6E7FF;
  border-color: #C6E7FF;
  color: #133e87;
}

.btn-primary:hover {
  background-color: #D4F6FF;
  border-color: #D4F6FF;
  color: #133e87;
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
import { ref, computed, watch } from 'vue';
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from '@/stores/todo';
import { useProgramStore } from '@/stores/program';

const props = defineProps({
  todo: {
    type: Object,
    required: true
  },
  userId: {
    type: Number,
    required: true
  },
  loginUserId: {
    type: Number,
    required: true
  }
});

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const programStore = useProgramStore();

const showModal = ref(false);
const editingStates = ref({});

const isFavorite = computed(() => todoStore.todoLikes.get(props.todo.todoId) || false);
const likeCount = computed(() => todoStore.todoLikeCounts.get(props.todo.todoId) || 0);

const programTitle = computed(() => {
  const program = programStore.programs.find(p => p.programId === props.todo.programId);
  return program ? program.title : '프로그램';
});

const programDetails = computed(() => {
  return programStore.programs.find(p => p.programId === props.todo.programId);
});

// Methods
const toggleStatus = async () => {
  if (loginStore.loginUserId === props.todo.userId) {
    try {
      props.todo.status = !props.todo.status;
      await todoStore.updateTodoStatus(props.userId, props.todo.todoId, props.todo.status);
    } catch (error) {
      props.todo.status = !props.todo.status;
      console.error('Status update failed:', error);
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
    console.error('Favorite update failed:', error);
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
    console.error('Content update failed:', error);
  }
};

const onClickDeleteTodo = (todo) => {
  if (confirm('정말 이 할 일을 삭제하시겠습니까?')) {
    todoStore.deleteTodo(props.userId, todo.todoId);
  }
};

watch(() => props.todo.todoId, (newId) => {
  if (!editingStates.value.hasOwnProperty(newId)) {
    editingStates.value[newId] = false;
  }
});
</script>