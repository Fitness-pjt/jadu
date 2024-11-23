<template>
  <li class="program-todo-item" :class="{ 'completed': todo.status }">
    <!-- 기존 아이템 내용 -->
    <div class="todo-main">
      <label class="todo-label" @click.stop>
        <input type="checkbox" :checked="todo.status" @change="toggleStatus" class="todo-checkbox"
          :disabled="loginUserId !== todo.userId" />
        <div class="program-info">
          <h4 class="program-title">{{ programTitle }}</h4>
          <div class="workout-info">
            <span class="todo-content" v-if="!editingStates[todo.todoId]">
              {{ todo.content }}
            </span>
            <input v-else type="text" v-model="todo.content" @blur="saveUpdateTodo(todo)"
              @keyup.enter.prevent="saveUpdateTodo(todo)" class="edit-input" />
          </div>
          <!-- 썸네일/상세보기 버튼 추가 -->
          <button v-if="todo.videoId" @click="showModal = true" class="view-detail-btn">
            <i class="bi bi-play-circle-fill"></i>
            상세보기
          </button>
        </div>
      </label>
    </div>

    <div class="todo-actions-wrapper">
      <!-- 좋아요 버튼 -->
      <div v-if="todo.status" class="favorite-container">
        <button @click="updateFavorite(todo)" class="favorite-btn">
          <span>{{ isFavorite[todo.todoId] ? "❤️" : "🤍" }}</span>
        </button>
      </div>

      <!-- 수정/삭제 버튼 -->
      <div class="todo-actions" v-if="userId === loginUserId">
        <button class="action-btn edit-btn" @click="onClickUpdateTodo(todo)">
          <i class="bi bi-pencil"></i>
        </button>
        <button class="action-btn delete-btn" @click="onClickDeleteTodo(todo)">
          <i class="bi bi-trash"></i>
        </button>
      </div>
    </div>

    <!-- 모달 -->
    <div v-if="showModal" class="modal-backdrop" @click="showModal = false">
      <div class="modal-content" @click.stop>
        <button class="modal-close" @click="showModal = false">
          <i class="bi bi-x-lg"></i>
        </button>

        <div class="modal-header">
          <h3 class="modal-title">{{ programTitle }}</h3>
          <p class="modal-subtitle">{{ todo.content }}</p>
        </div>

        <div class="modal-body">
          <!-- 비디오 플레이어 -->
          <div class="modal-video-container">
            <iframe :src="`https://www.youtube.com/embed/${todo.videoId}`" frameborder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen class="modal-video"></iframe>
          </div>

          <!-- 프로그램 상세 정보 -->
          <div class="program-details" v-if="programDetails">
            <div class="detail-item">
              <i class="bi bi-calendar3"></i>
              <span>기간: {{ programDetails.durationWeeks }}주</span>
            </div>
            <div class="detail-item">
              <i class="bi bi-person-walking"></i>
              <span>난이도: {{ programDetails.difficulty }}</span>
            </div>
            <p class="program-description">{{ programDetails.description }}</p>
          </div>
        </div>

        <div class="modal-footer">
          <div class="completion-status">
            <input type="checkbox" :checked="todo.status" @change="toggleStatus" :disabled="loginUserId !== todo.userId"
              class="modal-checkbox" />
            <span>완료 상태</span>
          </div>
          <button v-if="todo.status" @click="updateFavorite(todo)" class="modal-favorite-btn">
            {{ isFavorite[todo.todoId] ? "❤️ 찜하기 취소" : "🤍 찜하기" }}
          </button>
        </div>
      </div>
    </div>
  </li>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from '@/stores/todo';
import { useProgramStore } from '@/stores/program';
import { computed, ref, watch, onMounted } from 'vue';


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

const editingStates = ref({}); // 수정 상태
const isFavorite = ref({}); // 좋아요 상태

const programTitle = computed(() => {
  const program = programStore.programs.find(p => p.programId === props.todo.programId);
  return program ? program.title : '프로그램';
});

// 초기 수정 상태 설정
watch(
  () => props.todo.todoId,
  (newId) => {
    if (!editingStates.value.hasOwnProperty(newId)) {
      editingStates.value[newId] = false;
    }
  }
);

// 상태 토글 - TodoListItem과 동일한 방식으로 변경
const toggleStatus = async () => {
  if (loginStore.loginUserId === props.todo.userId) {
    try {
      props.todo.status = !props.todo.status; // 로컬 상태 즉시 변경
      await todoStore.updateTodoStatus(props.userId, props.todo.todoId, props.todo.status);
    } catch (error) {
      // 실패 시 상태 롤백
      props.todo.status = !props.todo.status;
      console.error('Failed to update todo status:', error);
    }
  }
};

// 수정 관련 함수들
const onClickUpdateTodo = (todo) => {
  editingStates.value[todo.todoId] = !editingStates.value[todo.todoId];
};

const saveUpdateTodo = async (todo) => {
  if (!todo.content.trim()) return;


  try {
    await todoStore.updateTodoContent(props.userId, todo.todoId, todo);
    editingStates.value[todo.todoId] = false;
  } catch (error) {
    console.error('Failed to update todo content:', error);
  }
};

const onClickDeleteTodo = (todo) => {
  if (confirm('정말 이 프로그램을 삭제하시겠습니까?')) {
    todoStore.deleteTodo(props.userId, todo.todoId);
  }
};

// 좋아요 관련 함수들
const fetchFavoriteStatus = async () => {
  try {
    const isLiked = await todoStore.getTodoLikesStatus(props.todo.todoId, props.loginUserId);
    isFavorite.value[props.todo.todoId] = isLiked;
  } catch (error) {
    console.error('Failed to fetch favorite status:', error);
  }
};

const updateFavorite = async (todo) => {
  const currentStatus = isFavorite.value[todo.todoId];
  isFavorite.value[todo.todoId] = !currentStatus;

  try {
    if (!currentStatus) {
      await todoStore.pushTodoLikes(todo.todoId, props.loginUserId);
    } else {
      await todoStore.cancelTodoLikes(todo.todoId, props.loginUserId);
    }
  } catch (error) {
    console.error('Failed to update favorite status:', error);
    isFavorite.value[todo.todoId] = currentStatus;
  }
};


const showModal = ref(false);

// 프로그램 상세 정보 가져오기
const programDetails = computed(() => {
  const program = programStore.programs.find(p => p.programId === props.todo.programId);
  return program;
});
onMounted(fetchFavoriteStatus);
</script>
<style scoped>
/* 기존 스타일 */
.program-todo-item {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 1rem;
  margin-bottom: 0.5rem;
  border-radius: 10px;
  background-color: #e9f5ef;
  border: 1px solid #42b983;
  box-shadow: 2px 2px 5px rgba(66, 185, 131, 0.2);
  transition: all 0.3s ease;
}

.program-todo-item:hover {
  background-color: #dff0e9;
}

.program-todo-item.completed {
  background-color: #f8f9fa;
  border-color: #dee2e6;
}

.todo-main {
  flex: 1;
}

.todo-label {
  display: flex;
  align-items: flex-start;
  gap: 1rem;
  width: 100%;
}

.todo-checkbox {
  width: 1.2rem;
  height: 1.2rem;
  margin-top: 0.25rem;
  cursor: pointer;
  accent-color: #42b983;
}

.program-info {
  flex: 1;
}

.program-title {
  margin: 0 0 0.5rem 0;
  color: #2c3e50;
  font-size: 1.1rem;
}

.workout-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
}

.todo-content {
  color: #666;
}

.video-info {
  flex-shrink: 0;
}

.video-frame {
  width: 200px;
  height: 112.5px;
  border-radius: 4px;
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
  display: flex;
  align-items: center;
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

.favorite-btn {
  background-color: transparent;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
  padding: 0.5rem;
  transition: transform 0.2s ease;
}

.favorite-btn:hover {
  transform: scale(1.1);
}

.edit-input {
  border: none;
  border-bottom: 2px solid #42b983;
  background: transparent;
  outline: none;
  color: #666;
  font-size: 1rem;
  padding: 0.2rem 0;
  width: 100%;
}

/* 새로 추가된 상세보기 버튼 스타일 */
.view-detail-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  margin-top: 0.75rem;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.2s ease;
}

.view-detail-btn:hover {
  background-color: #357abd;
  transform: translateY(-1px);
}

.view-detail-btn i {
  font-size: 1.1rem;
}

/* 모달 스타일 */
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  position: relative;
  width: 90%;
  max-width: 800px;
  max-height: 90vh;
  background-color: white;
  border-radius: 12px;
  padding: 2rem;
  overflow-y: auto;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-close {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: transparent;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #64748b;
  padding: 0.5rem;
  border-radius: 50%;
  transition: all 0.2s ease;
}

.modal-close:hover {
  background-color: #f1f5f9;
  color: #1e293b;
}

.modal-header {
  margin-bottom: 1.5rem;
}

.modal-title {
  font-size: 1.5rem;
  color: #1e293b;
  margin-bottom: 0.5rem;
}

.modal-subtitle {
  color: #64748b;
  font-size: 1.1rem;
}

.modal-video-container {
  position: relative;
  width: 100%;
  padding-top: 56.25%;
  /* 16:9 비율 */
  margin-bottom: 1.5rem;
}

.modal-video {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 8px;
}

.program-details {
  padding: 1.5rem;
  background-color: #f8fafc;
  border-radius: 8px;
  margin-bottom: 1.5rem;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.75rem;
  color: #64748b;
}

.program-description {
  color: #475569;
  line-height: 1.6;
  margin-top: 1rem;
}

.modal-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 1rem;
  border-top: 1px solid #e2e8f0;
}

.completion-status {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.modal-checkbox {
  width: 1.5rem;
  height: 1.5rem;
  accent-color: #42b983;
}

.modal-favorite-btn {
  padding: 0.75rem 1.5rem;
  background-color: #f1f5f9;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.2s ease;
}

.modal-favorite-btn:hover {
  background-color: #e2e8f0;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .workout-info {
    flex-direction: column;
    align-items: flex-start;
  }

  .video-frame {
    width: 100%;
    height: auto;
    aspect-ratio: 16/9;
  }

  .modal-content {
    width: 95%;
    padding: 1.5rem;
    margin: 1rem;
  }

  .modal-title {
    font-size: 1.25rem;
  }

  .modal-footer {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }

  .program-details {
    padding: 1rem;
  }
}

.bi {
  font-size: 1.1rem;
}
</style>