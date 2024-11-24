<template>
  <li :key="todo.todoId" class="todo-item">
    <div class="todo-main">
      <label class="todo-label" @click.stop>
        <input
          type="checkbox"
          :checked="todo.status"
          @change="toggleTodo(todo)"
          class="todo-checkbox"
          :disabled="loginStore.loginUserId !== todo.userId"
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
    <div v-if="todo.status" class="likes-container">
      <button @click="updateFavorite(todo)" class="favorite-btn">
       <div class="heart-with-count">
        <span class="heart-icon">{{ isFavorite ? "❤️" : "🤍" }}</span>
        <span class="like-count-overlay">{{ likeCount }}</span>
      </div>
      </button>
    </div>
    <div class="todo-actions" v-if="userId === loginUserId">
      <button class="action-btn edit-btn" @click="onClickUpdateTodo(todo)">
        <i class="bi bi-pencil"></i>
      </button>
      <button class="action-btn delete-btn" @click="onClickDeleteTodo(todo)">
        <i class="bi bi-trash"></i>
      </button>
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
      todoStore.getTodoLikeCount(props.todo.todoId, props.loginUserId)
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
  background-color: #dff0e9;
}

.todo-main {
  flex: 1;
}

.todo-label {
  display: flex;
  align-items: center;
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

.todo-label input[type="text"] {
  border: none;
  border-bottom: 2px solid #42b983;
  background: transparent;
  outline: none;
  color: #333;
  font-size: 1rem;
  padding: 0.2rem 0;
  transition: border-color 0.3s ease;
}

.todo-label input[type="text"]:focus {
  border-bottom: 2px solid #357abd;
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
  display: flex;
  align-items: center;
  gap: 0.3rem;
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
  transition: color 0.3s ease;
}

.favorite-btn:hover {
  color: #42b983;
}

.likes-container {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.like-count {
  font-size: 0.9rem;
  color: #666;
  min-width: 1.5rem;
  text-align: center;
}


.bi {
  font-size: 1.1rem;
  margin-right: 0.3rem;
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
  text-shadow: 
    -1px -1px 0 #fff,
    1px -1px 0 #fff,
    -1px 1px 0 #fff,
    1px 1px 0 #fff;
  pointer-events: none;
}

.favorite-btn {
  background-color: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  transition: transform 0.2s ease;
}

.favorite-btn:hover {
  transform: scale(1.1);
}

.likes-container {
  display: flex;
  align-items: center;
  margin: 0 0.5rem;
}
</style>
