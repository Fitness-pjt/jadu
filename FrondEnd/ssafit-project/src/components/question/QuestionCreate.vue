<template>
  <div class="container mt-4">
    <div class="question-form-card">
      <div class="card-body">
        <h3 class="form-title">질문 작성하기</h3>
        <form>
          <!-- 작성자 -->
          <div class="form-group">
            <label for="writer" class="form-label">작성자:</label>
            <input
              type="text"
              id="writer"
              class="form-input"
              v-model="question.writer"
              readonly
            />
          </div>

          <!-- 제목 -->
          <div class="form-group">
            <label for="title" class="form-label">제목:</label>
            <input
              type="text"
              id="title"
              class="form-input"
              v-model="question.title"
              placeholder="질문의 제목을 입력하세요."
            />
          </div>

          <!-- 파일 업로드 -->
          <div class="form-group">
            <div class="file-upload">
              <label class="file-label">
                <i class="bi bi-image me-2"></i>
                이미지 첨부
                <input
                  type="file"
                  accept="image/*"
                  @change="uploadFile"
                  class="file-input"
                />
              </label>
              <span v-if="question.questionFileName" class="file-name">
                {{ question.questionFileName }}
              </span>
            </div>
          </div>

          <!-- 내용 -->
          <div class="form-group">
            <label for="content" class="form-label">내용:</label>
            <textarea
              id="content"
              class="form-textarea"
              rows="8"
              v-model="question.content"
              placeholder="질문의 내용을 입력하세요."
            ></textarea>
          </div>

          <!-- 버튼 그룹 -->
          <div class="button-group">
            <button type="button" class="cancel-btn" @click="$router.go(-1)">
              취소
            </button>
            <button type="button" class="submit-btn" @click="createQuestion">
              <i class="bi bi-send"></i>
              등록하기
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script setup>
import { useQuestionStore } from "@/stores/question";
import { useRoute } from "vue-router";
import { computed, onMounted, ref, watch } from "vue";
import { useUserStore } from "@/stores/user";
import { useLoginStore } from "@/stores/login";
import router from "@/router";

const route = useRoute();
const programId = route.params.programId;

const questionStore = useQuestionStore();
const loginStore = useLoginStore();
const loginUserId = computed(() => loginStore.loginUserId);

const userStore = useUserStore();
const userNickname = computed(() => userStore.userNickname);

watch(
  () => loginStore.value,
  (newList, oldList) => {},
  {
    deep: true,
  }
);

const question = ref({
  writer: null,
  title: "",
  content: "",
  questionFileName: "",
});

// 파일 업로드
const uploadFile = async (event) => {
  const file = event.target.files[0];
  if (!file) return;

  if (!file.type.startsWith("image/")) {
    alert("이미지 파일만 업로드 가능합니다.");
    return;
  }

  if (file.size > 5 * 1024 * 1024) {
    alert("파일 크기는 5MB를 초과할 수 없습니다.");
    return;
  }

  const formData = new FormData();
  formData.append("file", file);

  try {
    const imagePath = await questionStore.uploadFile(formData);

    // URL 앞부분을 제외하고 나머지 값만 추출
    const baseURL = "https://attnnskybucket.s3.ap-northeast-2.amazonaws.com/";
    const remainingPath = imagePath.replace(baseURL, "");

    question.value.questionFileName = remainingPath;
  } catch (error) {
    console.error("이미지 업로드 에러:", error);
    alert("이미지 업로드에 실패했습니다.");
    event.target.value = "";
  }
};

// 질문 등록 메서드
const createQuestion = async () => {
  // 제목과 내용이 없으면 등록 되지 않음
  const { title, content } = question.value;

  if (!title.trim()) {
    alert("제목을 입력해 주세요.");
    return;
  }

  if (!content.trim()) {
    alert("내용을 입력해 주세요.");
    return;
  }

  await questionStore.createQuestion(question.value, programId);
  alert("질문 등록이 완료되었습니다.");
  router.replace({ name: "question" });
};

onMounted(async () => {
  // 로그인한 사용자의 프로필 정보 불러오기
  await userStore.getUserListProfileInfo(loginUserId.value);

  // userNickname을 writer에 설정
  question.value.writer = userNickname.value;
});
</script>

<style scoped>
.question-form-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); */
  max-width: 800px;
  margin: 0 auto;
}

.form-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 2rem;
  text-align: center;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-label {
  display: block;
  font-weight: 500;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

.form-input,
.form-textarea {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #eee;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s ease;
}

.form-textarea {
  min-height: 200px;
  resize: vertical;
}

.form-input:focus,
.form-textarea:focus {
  outline: none;
  border-color: #c6e7ff;
  box-shadow: 0 0 0 2px rgba(198, 231, 255, 0.2);
}

.file-upload {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.file-label {
  display: inline-flex;
  align-items: center;
  padding: 0.75rem 1.25rem;
  background: #f8f9fa;
  border: 1px solid #eee;
  border-radius: 8px;
  color: #666;
  cursor: pointer;
  transition: all 0.2s ease;
  width: 9rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-label:hover {
  background: #eee;
}

.file-input {
  display: none;
}

.file-name {
  color: #666;
  font-size: 0.9rem;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
  padding-top: 1.5rem;
  border-top: 1px solid #eee;
}

.cancel-btn,
.submit-btn {
  padding: 0.75rem 1.25rem;
  border-radius: 10px;
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.cancel-btn {
  background: none;
  border: 1px solid #eee;
  color: #666;
}

.submit-btn {
  background: #c6e7ff;
  border: none;
  color: #2c3e50;
}

.cancel-btn:hover {
  background: #f8f9fa;
}

.submit-btn:hover {
  opacity: 0.9;
}

@media (max-width: 768px) {
  .question-form-card {
    padding: 1.5rem;
    margin: 1rem;
  }

  .button-group {
    flex-direction: column-reverse;
  }

  .cancel-btn,
  .submit-btn {
    width: 100%;
    justify-content: center;
  }
}
</style>
