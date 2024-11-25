<template>
  <div class="card todo-create-card">
    <div class="card-body p-3">
      <form class="todo-form" @submit.prevent="addTodo">
        <div class="input-group">
          <input
            type="text"
            class="form-control custom-input"
            placeholder="📝 새로운 할 일을 입력하세요"
            v-model="todo.content"
            :class="{ 'is-invalid': showError }"
          />
          <button 
            type="submit" 
            class="btn custom-btn"
          >
            추가
          </button>
        </div>
        <div class="invalid-feedback" v-if="showError">
          할 일을 입력해주세요.
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import { computed, ref } from "vue";

const todoStore = useTodoStore();
const loginStore = useLoginStore();
const loginUserId = loginStore.loginUserId;

const selectedDate = computed(() => todoStore.selectedDate);
const showError = ref(false);

const todo = ref({
  content: "",
  date: selectedDate,
  programId: null,
  videoId: null,
  status: false,
});

const addTodo = async () => {
  if (!todo.value.content.trim()) {
    showError.value = true;
    return;
  }
  
  showError.value = false;

  try {
    await todoStore.addTodo(
      {
        ...todo.value,
        date: selectedDate.value,
      },
      loginUserId
    );
    
    todo.value.content = "";
  } catch (error) {
    console.error('Failed to add todo:', error);
  }
};
</script>

<style scoped>
.todo-create-card {
  border: none;
  background-color: #FBFBFB;
  margin-bottom: 1rem;
}

.todo-form {
  position: relative;
}

.input-group {
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.custom-input {
  border: 1px solid #e0e7ff;
  padding: 0.8rem 1rem;
  font-size: 0.95rem;
  border-right: none;
  background-color: white;
}

.custom-input:focus {
  box-shadow: none;
  border-color: #C6E7FF;
  background-color: white;
}

.custom-btn {
  background-color: #C6E7FF;
  border: 1px solid #C6E7FF;
  color: #2c3e50;
  padding: 0.5rem 1.5rem;
  font-weight: 500;
  font-size: 0.95rem;
  transition: all 0.2s ease;
  min-width: 100px;
}

.custom-btn:hover {
  background-color: #D4F6FF;
  border-color: #D4F6FF;
}

.invalid-feedback {
  position: absolute;
  bottom: -20px;
  left: 10px;
  margin-top: 0.25rem;
  font-size: 0.875rem;
  color: #dc3545;
}

/* 반응형 디자인 */
@media (max-width: 576px) {
  .custom-input {
    font-size: 0.9rem;
  }
  
  .custom-btn {
    padding: 0.5rem 1rem;
    min-width: 80px;
  }
}
</style>