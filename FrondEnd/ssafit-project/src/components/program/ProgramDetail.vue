<template>
  <div class="container my-4">
    <!-- 로딩 상태 표시 -->
    <div v-if="isLoading" class="text-center">
      <div class="spinner-border" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <!-- 에러 메시지 -->
    <div v-else-if="error" class="alert alert-danger">
      {{ error }}
    </div>

    <!-- 프로그램 상세 정보 -->
    <div v-else-if="program" class="program-detail">
      <!-- 헤더 섹션 -->
      <div class="row d-flex justify-content-center align-items-start">
        <div
          class="col-md-4 d-flex justify-content-center align-items-center"
          style="margin-top: 20px"
        >
          <img
            :src="program.programImgPath"
            :alt="program.title"
            class="img-fluid rounded responsive-img"
            style="height: 100%; object-fit: cover"
          />
        </div>
        <div class="col-md-8 px-4">
          <div
            class="mb-3 mt-3 d-flex col align-items-center justify-content-between"
          >
            <h3 class="fw-bold">{{ program.title }}</h3>
            <RouterLink
              :to="getRoute(program.userId)"
              class="btn"
              @click="closeMenu"
            >
              <UserNameTag :user-id="program.userId" />
            </RouterLink>
          </div>
          <div class="program-meta">
            <span
              class="badge me-2"
              :class="{
                'bg-green': program.level === 'BEGINNER',
                'bg-orange': program.level === 'INTERMEDIATE',
                'bg-red': program.level === 'ADVANCED',
              }"
              >{{ program.level }}</span
            >
            <span class="badge bg-week me-2"
              >{{ program.durationWeeks }}주 프로그램</span
            >
            <span class="badge bg-week"
              ><i class="bi bi-camera-video me-1"></i>영상
              {{ program.videoCnt }}개</span
            >
          </div>
          <p class="py-4">
            {{ program.description }}
          </p>
        </div>
      </div>

      <!-- 버튼 그룹 -->
      <div class="d-flex gap-2 my-4">
        <!-- 시작하기/진행중 표시 버튼 -->
        <button v-if="!isInProgress" @click="openDatePicker" class="btn-navy">
          프로그램 시작하기
        </button>
        <div
          v-else
          class="program-status-badge bg-navy text-white px-3 py-2 rounded"
        >
          진행중
        </div>

        <button
          @click="toggleLike"
          :disabled="isLoading"
          class="btn like-btn"
          :class="{ liked: isLiked, 'disabled-btn': isLoading }"
        >
          <i
            :class="isLiked ? 'bi bi-heart-fill text-danger' : 'bi bi-heart'"
          ></i>
          좋아요 <span v-if="likeCount > 0">{{ likeCount }}</span>
        </button>

        <!-- 작성자용 버튼 -->
        <div v-if="isAuthor" class="ms-auto">
          <button class="btn btn-outline-secondary me-2" @click="editProgram">
            수정
          </button>
          <button class="btn btn-outline-danger" @click="confirmDelete">
            삭제
          </button>
        </div>
      </div>
      <!-- 탭 네비게이션 -->
      <ul class="nav nav-tabs py-4">
        <li class="nav-item">
          <RouterLink
            class="nav-link"
            :class="{ active: activeTab === 'videos' }"
            @click="activeTab = 'videos'"
            :to="{ name: 'video' }"
          >
            운동 영상 보기
          </RouterLink>
        </li>
        <li class="nav-item">
          <RouterLink
            class="nav-link"
            :class="{ active: activeTab === 'reviews' }"
            @click="activeTab = 'reviews'"
            :to="{ name: 'review' }"
          >
            리뷰 보러가기
          </RouterLink>
        </li>
        <li class="nav-item">
          <RouterLink
            :to="{ name: 'question' }"
            class="nav-link"
            :class="{ active: activeTab === 'qna' }"
            @click="activeTab = 'qna'"
          >
            질문 게시판
          </RouterLink>
        </li>
      </ul>

      <!-- 탭 컨텐츠 -->
      <div class="tab-content">
        <div v-if="activeTab === 'videos'" class="tab-pane active">
          <ProgramVideoList
            :videos="videos"
            v-model:selected-video="selectedVideo"
          />
        </div>
        <div v-else-if="activeTab === 'reviews'" class="tab-pane active">
          <RouterView />
        </div>
        <div v-else-if="activeTab === 'qna'" class="tab-pane active">
          <RouterView />
        </div>
      </div>
    </div>

    <!-- 날짜 선택 모달 -->
    <Teleport to="body">
      <div v-if="showDatePicker" class="modal-overlay">
        <div class="custom-modal">
          <div class="modal-header">
            <h5 class="modal-title">프로그램 시작 날짜 선택</h5>
            <button
              type="button"
              class="btn-close"
              @click="closeDatePicker"
            ></button>
          </div>
          <div class="modal-body">
            <VCalendar
              v-model="selectedStartDate"
              :min-date="new Date()"
              :attributes="attributes"
              is-expanded
              @dayclick="onDateSelect"
              :model-config="{
                type: 'string',
                mask: 'YYYY-MM-DD',
              }"
            />
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-gray" @click="closeDatePicker">
              취소
            </button>
            <button
              type="button"
              class="btn btn-navy"
              @click="confirmStartDate"
              :disabled="!selectedStartDate"
            >
              시작하기
            </button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>
<script setup>
import { useLoginStore } from "@/stores/login";
import { useProgramStore } from "@/stores/program";
import { useTodoStore } from "@/stores/todo";
import { useVideoStore } from "@/stores/video";
import { computed, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import UserNameTag from "../common/UserNameTag.vue";
import ProgramVideoList from "./ProgramVideoList.vue";

const route = useRoute();
const router = useRouter();
const loginStore = useLoginStore();
const programStore = useProgramStore();
const videoStore = useVideoStore();
const todoStore = useTodoStore();

const programId = route.params.programId;

const program = ref(null);
const isLoading = ref(true);
const error = ref(null);
const activeTab = ref("videos");
const videos = ref([]);
const selectedVideo = ref(null);
const isInProgress = ref(false);

const isLiked = computed(() => programStore.isLiked);
const likeCount = computed(() => programStore.likeCount);

// 현재 사용자가 프로그램 작성자인지 확인
const isAuthor = computed(() => {
  return program.value?.userId === loginStore.loginUserId;
});

// 프로그램 수정
const editProgram = () => {
  router.push(`/program/edit/${programId}`);
};

// 프로그램 삭제 확인
const confirmDelete = async () => {
  if (!confirm("정말로 이 프로그램을 삭제하시겠습니까?")) {
    return;
  }

  try {
    await programStore.deleteProgram(programId);
    alert("프로그램이 성공적으로 삭제되었습니다.");
    router.push("/programs"); // 프로그램 목록으로 이동
  } catch (err) {
    console.error(err);
    alert("프로그램 삭제에 실패했습니다.");
  }
};

// 프로그램 정보 가져오기
const fetchProgramDetail = async () => {
  try {
    isLoading.value = true;
    program.value = await programStore.getProgramById(programId);

    // 프로그램 데이터를 가져온 후 비디오와 좋아요 정보도 함께 가져오기
    await Promise.all([
      fetchProgramVideos(),
      programStore.checkLikeStatus(programId),
      programStore.getLikeCount(programId),
    ]);
  } catch (err) {
    error.value = "프로그램 정보를 불러오는데 실패했습니다.";
    console.error(err);
  } finally {
    isLoading.value = false;
  }
};

const fetchProgramVideos = async () => {
  try {
    // 프로그램 정보에서 비디오 ID 목록을 가져와서 처리
    if (program.value?.videoIds) {
      const programVideos = await videoStore.getVideosByIds(
        program.value.videoIds
      );
      videos.value = programVideos;
    }
  } catch (err) {
    console.error("비디오 로딩 실패:", err);
    error.value = "비디오 목록을 불러오는데 실패했습니다.";
  }
};

// 프로그램 시작하기
const showDatePicker = ref(false);
const selectedStartDate = ref(null);

const onDateSelect = (date) => {
  // console.log("date", date.id);
  selectedStartDate.value = date.id;
};

const openDatePicker = () => {
  showDatePicker.value = true;
};

const closeDatePicker = () => {
  showDatePicker.value = false;
  selectedStartDate.value = null;
};

const confirmStartDate = async () => {
  try {
    const userId = loginStore.loginUserId;
    if (!userId) {
      throw new Error("로그인이 필요합니다.");
    }

    // 선택된 날짜가 있는지 확인
    if (!selectedStartDate.value) {
      throw new Error("시작 날짜를 선택해주세요.");
    }

    await todoStore.startProgram(
      route.params.programId,
      userId,
      selectedStartDate.value
    );
    closeDatePicker();
    router.push({ name: "mypage" });
  } catch (err) {
    console.error(err.message);
  }
};

const attributes = [
  {
    key: "today",
    highlight: {
      color: "blue",
      fillMode: "light",
    },
    dates: new Date(),
  },
  {
    key: "selected",
    highlight: {
      color: "blue",
      fillMode: "outline",
    },
    dates: null, // 처음에는 null, 이후 선택된 날짜로 동적 업데이트
  },
];

// 좋아요 토글
const toggleLike = async () => {
  if (!loginStore.loginUserId) {
    alert("로그인이 필요한 기능입니다.");
    router.push("/login");
    return;
  }

  try {
    await programStore.toggleProgramLike(programId);
  } catch (err) {
    console.error("좋아요 처리 실패:", err);
    alert("좋아요 처리에 실패했습니다.");
  }
};
const checkProgramStatus = async () => {
  if (loginStore.loginUserId) {
    try {
      const status = await todoStore.checkProgramProgress(
        programId, // route.params.programId 대신 상수 사용
        loginStore.loginUserId
      );
      isInProgress.value = status.inProgress;
      console.log("Program status:", status); // 디버깅용
    } catch (err) {
      console.error("프로그램 상태 체크 실패:", err);
    }
  }
};
const getRoute = (userId) => {
  return loginStore.loginUserId === userId
    ? { name: "mypage", params: { userId } }
    : { name: "profile", params: { userId } };
};

// 컴포넌트 마운트 시 데이터 가져오기
onMounted(async () => {
  await fetchProgramDetail(); // program 정보 가져오기 기다림
  if (videos.value.length > 0) {
    selectedVideo.value = videos.value[0];
  }
  await checkProgramStatus(); // program 정보 이후에 상태 체크
});
</script>

<style scoped>
.program-detail {
  max-width: 1200px;
  margin: 0 auto;
}

.program-meta {
  font-size: 1.1rem;
}

/* 기본 이미지 크기 (모든 화면 크기에서 기본 크기 적용) */
.responsive-img {
  width: 100%;
  height: auto;
}

/* 화면 크기가 768px 이상일 때 */
@media (min-width: 768px) {
  .responsive-img {
    width: 60%;
  }
}

/* 화면 크기가 1024px 이상일 때 */
@media (min-width: 1024px) {
  .responsive-img {
    width: 80%;
  }
}

/* 화면 크기가 1200px 이상일 때 */
@media (min-width: 1200px) {
  .responsive-img {
    width: 100%;
  }
}

.nav-link {
  cursor: pointer;
  color: #133e87;
  font-weight: 600;
}

.nav-link:hover {
  color: #133e87;
}

.tab-content {
  padding: 20px 0;
}

.badge {
  font-size: 0.9rem;
  padding: 0.5em 1em;
}

.btn {
  transition: all 0.2s ease-in-out;
  border: none;
}

.btn:hover {
  transform: scale(0.98);
}

.border-primary {
  box-shadow: 0 0 0 2px #133e87;
}

.btn-primary .bi-heart-fill {
  color: #fff;
}

.btn .bi {
  margin-right: 0.5rem;
}

/* 좋아요 버튼 호버 효과 */
.btn-outline-primary:hover {
  transform: scale(1.05);
}

/* 좋아요 활성화 상태의 아이콘 애니메이션 */
.bi-heart-fill {
  animation: pulse 0.3s ease-in-out;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }

  50% {
    transform: scale(1.2);
  }

  100% {
    transform: scale(1);
  }
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1050;
}

.custom-modal {
  background-color: white;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.modal-header {
  padding: 1rem;
  border-bottom: 1px solid #dee2e6;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-body {
  padding: 1rem;
}

.modal-footer {
  padding: 1rem;
  border-top: 1px solid #dee2e6;
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

:deep(.vc-container) {
  border: none;
  width: 100%;
}

:deep(.vc-header) {
  padding: 10px 0;
  margin-top: 0px;
  height: auto;
}

:deep(.vc-title-wrapper) > button {
  background-color: transparent;
}

:deep(.vc-weeks) {
  padding: 0;
}
</style>
