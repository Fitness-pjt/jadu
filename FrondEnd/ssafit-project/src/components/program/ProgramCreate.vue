<template>
  <div class="program-create">
    <!-- 헤더 섹션 -->
    <div class="header-content text-center py-5">
      <h3 class="main-title">나만의 운동 프로그램을 만들어보세요!</h3>
      <p class="subtitle">
        자신만의 운동 목표에 맞춰,
        <span class="highlight">맞춤형 프로그램</span>을 만들고, 다른 사람들과
        공유해보세요. <br />함께 성장하는 운동 커뮤니티에 참여하세요!
      </p>
    </div>

    <!-- 진행 상태 표시 -->
    <div
      class="steps-container d-flex justify-content-center align-items-center mb-3"
    >
      <div class="step" :class="{ active: step >= 1 }">
        <div class="step-circle">1</div>
        <div class="step-label">영상 선택</div>
      </div>
      <div class="step-line" :class="{ active: step >= 2 }"></div>
      <div class="step" :class="{ active: step >= 2 }">
        <div class="step-circle">2</div>
        <div class="step-label">프로그램 정보</div>
      </div>
      <div class="step-line" :class="{ active: step >= 3 }"></div>
      <div class="step" :class="{ active: step === 3 }">
        <div class="step-circle">3</div>
        <div class="step-label">최종 확인</div>
      </div>
    </div>

    <!-- 메인 콘텐츠 -->
    <div class="content-wrapper">
      <!-- 스텝 1: 비디오 검색 및 선택 -->
      <div v-if="step === 1">
        <div class="search-header">
          <h5 class="mb-3">운동 프로그램을 위한 영상을 선택해보세요.</h5>
          <p class="text-muted small">
            현재 선택된 영상: {{ videoStore.selectedVideos.length }}개
          </p>
        </div>
        <VideoSearch class="mb-2" />
        <VideoList />
      </div>

      <!-- 스텝 2: 프로그램 기본 정보 -->
      <div v-if="step === 2" class="program-create-section">
        <div class="create-form">
          <div class="form-header">
            <h5 class="mb-3">프로그램에 대한 기본 정보를 입력하세요.</h5>
            <p class="text-muted small">
              프로그램 제목, 프로그램 설명과 함께 프로그램의 난이도와 수행 목표
              기간을 작성해주세요.
            </p>
          </div>
          <div class="form-body">
            <div class="form-group">
              <label>프로그램 제목</label>
              <input
                type="text"
                class="form-control"
                v-model="programData.title"
                placeholder="프로그램 제목을 입력하세요."
              />
            </div>

            <div class="form-group">
              <label>프로그램 설명</label>
              <textarea
                class="form-control"
                v-model="programData.description"
                rows="4"
                placeholder="프로그램에 대한 설명을 입력하세요."
              ></textarea>
            </div>

            <div class="row">
              <div class="col-md-6">
                <div class="form-group">
                  <label>난이도</label>
                  <select class="form-control" v-model="programData.level">
                    <option value="" disabled selected>난이도 선택</option>
                    <option value="BEGINNER">초급</option>
                    <option value="INTERMEDIATE">중급</option>
                    <option value="ADVANCED">고급</option>
                  </select>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <label>운동 기간 (주)</label>
                  <input
                    type="number"
                    class="form-control"
                    v-model="programData.durationWeeks"
                    min="1"
                    max="52"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 스텝 3: 최종 확인 -->
      <div v-if="step === 3" class="preview-section">
        <div class="check-header">
          <h5 class="mb-3">최종 확인</h5>
          <p class="text-muted small">
            마지막 단계입니다! 프로그램 생성 전 모든 정보를 확인해주세요.
          </p>
        </div>

        <div class="preview-content">
          <p>
            <strong>선택된 영상:</strong>
            {{ videoStore.selectedVideos.length }}개
          </p>
          <p><strong>프로그램 제목:</strong> {{ programData.title }}</p>
          <p><strong>프로그램 설명:</strong> {{ programData.description }}</p>
          <p><strong>난이도:</strong> {{ getLevelText(programData.level) }}</p>
          <p><strong>기간:</strong> {{ programData.durationWeeks }}주</p>
        </div>
      </div>

      <!-- 버튼 영역 -->
      <div class="button-container">
        <button
          v-if="step > 1"
          class="btn btn-outline-secondary me-2"
          @click="step--"
        >
          이전
        </button>
        <button
          v-if="step < 3"
          class="btn btn-primary"
          @click="nextStep"
          :disabled="!isStepValid"
        >
          다음
        </button>
        <button
          v-if="step === 3"
          class="btn btn-primary"
          @click="submitProgram"
          :disabled="!isFormValid || programStore.isLoading"
        >
          {{ programStore.isLoading ? "생성 중..." : "프로그램 생성하기" }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue"; // onMounted 추가
import { useVideoStore } from "@/stores/video";
import { useProgramStore } from "@/stores/program";
import VideoList from "../video/VideoList.vue";
import VideoSearch from "../video/VideoSearch.vue";
import { useRouter } from "vue-router";

const router = useRouter();
const videoStore = useVideoStore();
const programStore = useProgramStore();

// 컴포넌트 마운트 시 초기화
onMounted(() => {
  initializeForm();
});

// 초기화 함수
const initializeForm = () => {
  programData.value = {
    title: "",
    description: "",
    level: "",
    durationWeeks: null,
    programImgPath: "",
    videoCnt: null,
  };
  videoStore.clearSelectedVideos();
  videoStore.videoList.value = []; // videoList도 초기화
};

const programData = ref({
  title: "",
  description: "",
  level: "",
  durationWeeks: null,
  programImgPath: "",
  videoCnt: null,
});

// 폼 유효성 검사 (동일)
const isFormValid = computed(() => {
  return (
    programData.value.title &&
    programData.value.description &&
    programData.value.level &&
    programData.value.durationWeeks &&
    videoStore.selectedVideos.length > 0
  );
});

// 프로그램 생성 제출
const submitProgram = async () => {
  if (!isFormValid.value) return;

  try {
    const videoIds = videoStore.selectedVideos.map((video) => video.id);
    const thumbnailUrl =
      videoStore.selectedVideos[0].snippet.thumbnails.medium.url;
    const selectedVideoCount = videoIds.length;

    programData.value.programImgPath = thumbnailUrl;
    programData.value.videoCnt = selectedVideoCount;

    const submitData = {
      ...programData.value,
      videoIds: videoIds,
    };

    // console.log("submitData :>> ", submitData);
    await programStore.createProgram(submitData);

    alert("프로그램이 성공적으로 생성되었습니다!");

    // 초기화 함수 호출
    initializeForm();

    router.push({ name: "program" });
  } catch (error) {
    console.error("프로그램 생성 실패:", error);
  }

  // window.location.href = "/";
};

const step = ref(1);
const isStepValid = computed(() => {
  switch (step.value) {
    case 1:
      return videoStore.selectedVideos.length > 0;
    case 2:
      return (
        programData.value.title &&
        programData.value.description &&
        programData.value.level &&
        programData.value.durationWeeks
      );
    case 3:
      return true;
    default:
      return false;
  }
});

const nextStep = () => {
  if (isStepValid.value && step.value < 3) {
    step.value++;
  }
};

const getLevelText = (level) => {
  const levels = {
    BEGINNER: "초급자",
    INTERMEDIATE: "중급자",
    ADVANCED: "고급자",
  };
  return levels[level] || level;
};
</script>

<style scoped>
:root {
  --primary-blue: #c6e7ff;
  --secondary-blue: #d4f6ff;
  --neutral: #fbfbfb;
  --accent: #ffddae;
}

.program-create {
  max-width: 1000px; /* 1200px에서 1000px로 변경 */
  margin: 0 auto;
  padding: 2rem;
  background-color: var(--neutral);
}

/* 프로그램 생성 헤더 */
.header-content {
  padding: 3rem 2rem; /* 충분한 여백 */
}

.main-title {
  font-size: 2rem;
  font-weight: 700;
  color: #2c3e50; /* 다크 네이비 색상 */
  letter-spacing: -0.5px; /* 타이틀 텍스트 간격 조정 */
  margin-bottom: 2rem;
  text-transform: uppercase; /* 대문자 변환 */
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1); /* 타이틀 그림자 */
}

.subtitle {
  font-size: 1.2rem;
  color: #34495e;
  font-weight: 400;
  line-height: 1.6;
}

.highlight {
  font-weight: 700;
  color: #133e87; /* 강조할 텍스트에 빨간색 */
}

@media (max-width: 768px) {
  .main-title {
    font-size: 2rem; /* 작은 화면에서 글자 크기 줄이기 */
  }

  .subtitle {
    font-size: 1rem; /* 작은 화면에서 본문 크기 줄이기 */
  }
}

/* 진행 상태 확인 */
.steps-container {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.step {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  color: #bbb;
}

.step-circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #eee;
  font-weight: bold;
  font-size: 16px;
}

.step-label {
  margin-top: 0.5rem;
  font-size: 14px;
}

.step-line {
  flex-grow: 1;
  height: 2px;
  background-color: #eee;
}

.step.active .step-circle {
  background-color: #133e87;
  color: #fff;
}

.step.active .step-label {
  color: #133e87;
}

.step-line.active {
  background-color: #133e87;
}

.content-wrapper {
  padding: 2.5rem; /* 2rem에서 3rem으로 패딩 증가 */
  border-radius: 20px; /* 10px에서 20px로 증가 */
}

.form-group {
  margin-bottom: 2rem; /* 1.5rem에서 2rem으로 증가 */
}

.form-group label {
  display: block;
  margin-bottom: 0.8rem;
  color: #444;
  font-weight: 500;
  font-size: 1.1rem;
}

.form-control {
  border: 1px solid #eee;
  border-radius: 10px;
  padding: 0.8rem 1.2rem;
  width: 100%;
  transition: all 0.3s ease;
}

.form-control:focus {
  outline: none;
  border-color: #c6e7ff;
}

.preview-section {
  padding: 2rem;
}

.preview-content {
  background: var(--neutral);
  padding: 2rem;
  border-radius: 15px;
  border: 1px solid var(--primary-blue);
}
.button-container {
  margin-top: 3rem;
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
}

.btn {
  padding: 0.8rem 2rem;
  border-radius: 10px;
  font-weight: 600;
  transition: all 0.3s ease;
  font-size: 1.1rem;
  border: none;
  color: #333;
  background: var(--primary-blue);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Primary 버튼 (다음, 생성하기, 이전 모두 동일하게) */
.btn-primary,
.btn-outline-secondary {
  background: var(--primary-blue);
  border: none;
  color: #333;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.btn-primary:hover:not(:disabled),
.btn-outline-secondary:hover {
  background: var(--accent);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  color: #333; /* hover 시에도 검정색 유지 */
}

.btn-primary:disabled {
  background: #e0e0e0;
  color: #999;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* 로딩 상태의 버튼 */
.btn-primary:disabled {
  position: relative;
  overflow: hidden;
}

.btn-primary:disabled::after {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(255, 255, 255, 0.3),
    transparent
  );
  animation: loading 1.5s infinite;
}

.search-header,
.form-header,
.check-header {
  margin-bottom: 2rem;
  text-align: center;
  padding: 1rem 2rem; /* 상하 패딩 줄임 */
}

.search-header,
.form-header,
.check-header h5 {
  color: #333;
  font-size: 1.8rem;
  margin-bottom: 1rem;
}

.text-muted.small {
  font-size: 1rem;
  color: #666;
}

@keyframes loading {
  0% {
    left: -100%;
  }
  100% {
    left: 100%;
  }
}

/* 모바일 반응형 스타일 유지 */
@media (max-width: 768px) {
  .program-create {
    padding: 1rem;
  }

  .content-wrapper {
    padding: 1.5rem;
  }

  .steps-container {
    flex-direction: column;
    align-items: center;
    padding: 1rem;
  }

  .step {
    margin: 0.5rem 0;
    width: 100%;
    text-align: center;
  }

  .step::after {
    display: none;
  }

  .button-container {
    flex-direction: column;
    gap: 0.5rem;
  }

  .button-container button {
    padding: 1rem 2rem; /* 모바일에서 터치 영역 증가 */
    font-size: 1rem;
  }
}
</style>
