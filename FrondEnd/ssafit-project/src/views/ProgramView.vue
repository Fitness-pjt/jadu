<template>
  <div>
    <div>
      <div class="card border-0 overflow-hidden">
        <div class="card-body p-0">
          <!-- Header Section with Animated Background -->
          <div class="header-section p-5 position-relative overflow-hidden">
            <div class="program-header">
              <div class="animated-bg"></div>
              <p
                class="fw-bold mb-4 position-relative"
                style="font-size: x-large; color: #133e87"
              >
                <font-awesome-icon icon="fa-solid fa-dumbbell" class="me-2" />
                내 프로그램 관리
              </p>
              <!-- Action Cards -->
              <div class="row g-4 mb-5">
                <div class="col-md-6">
                  <div
                    class="action-card bg-white p-4 rounded-4 h-100 animate__animated animate__fadeInLeft"
                  >
                    <div class="d-flex align-items-center mb-3">
                      <div class="icon-circle bg-color-blue me-3">
                        <font-awesome-icon
                          icon="fa-solid fa-pencil-alt"
                          class=""
                        />
                      </div>
                      <h3 class="h5 mb-0">직접 프로그램 만들기</h3>
                    </div>
                    <p class="text-muted mb-4">
                      나만의 맞춤 운동 프로그램을 직접 제작해보세요.
                    </p>
                    <RouterLink
                      to="/program/create"
                      class="btn btn-primary-blue btn-lg w-100 rounded-3"
                    >
                      프로그램 만들기
                      <font-awesome-icon
                        icon="fa-solid fa-arrow-right"
                        class="ms-2"
                      />
                    </RouterLink>
                  </div>
                </div>

                <div class="col-md-6">
                  <div
                    class="action-card bg-white p-4 rounded-4 h-100 animate__animated animate__fadeInRight"
                  >
                    <div class="d-flex align-items-center mb-3">
                      <div class="icon-circle bg-color-orange me-3">
                        <font-awesome-icon icon="fa-solid fa-robot" class="" />
                      </div>
                      <h3 class="h5 mb-0">AI 추천 받기</h3>
                    </div>
                    <p class="text-muted mb-4">
                      AI가 당신에게 맞는 최적의 프로그램을 추천해드립니다.
                    </p>
                    <RouterLink
                      to="/program/createAi"
                      class="btn btn-color-orange btn-lg w-100 rounded-3"
                    >
                      AI 추천받기
                      <font-awesome-icon
                        icon="fa-solid fa-magic"
                        class="ms-2"
                      />
                    </RouterLink>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- Body -->
          <div class="program-body-container">
            <div class="program-body">
              <!-- Custom Tabs -->
              <div class="custom-tabs mb-4 pt-4">
                <button
                  @click="activeTab = 'registered'"
                  :class="[
                    'tab-button',
                    activeTab === 'registered' ? 'active' : '',
                  ]"
                >
                  <font-awesome-icon
                    icon="fa-solid fa-folder-plus"
                    class="me-2"
                  />
                  내가 등록한 프로그램
                </button>
                <button
                  @click="activeTab = 'ongoing'"
                  :class="[
                    'tab-button',
                    activeTab === 'ongoing' ? 'active' : '',
                  ]"
                >
                  <font-awesome-icon icon="fa-solid fa-running" class="me-2" />
                  진행 중인 프로그램
                </button>
              </div>
              <!-- Content Section -->
              <div class="p-2">
                <!-- Loading State -->
                <div
                  v-if="programStore.isLoading || isLoadingProgress"
                  class="text-center py-5 animate__animated animate__pulse animate__infinite"
                >
                  <div class="spinner-grow text-primary mb-3" role="status">
                    <span class="visually-hidden">Loading...</span>
                  </div>
                  <p class="text-muted">
                    {{
                      isLoadingProgress
                        ? "진행중인 프로그램을 확인하고 있습니다..."
                        : "로딩 중..."
                    }}
                  </p>
                </div>

                <!-- Error State -->
                <div
                  v-else-if="programStore.error"
                  class="alert alert-danger d-flex align-items-center animate__animated animate__shakeX"
                >
                  <font-awesome-icon
                    icon="fa-solid fa-exclamation-circle"
                    class="me-2"
                  />
                  {{ programStore.error }}
                </div>

                <!-- Program Lists -->
                <div v-else class="programs-wrapper">
                  <!-- Registered Programs -->
                  <div
                    v-if="activeTab === 'registered'"
                    class="animate__animated animate__fadeIn"
                  >
                    <div
                      v-if="registeredPrograms.length === 0"
                      class="empty-state"
                    >
                      <div class="empty-icon">
                        <font-awesome-icon icon="fa-solid fa-folder-open" />
                      </div>
                      <p class="text-muted mb-4" style="font-size: large">
                        등록한 프로그램이 없습니다.
                      </p>
                      <RouterLink
                        to="/program/create"
                        class="btn btn-outline-primary btn-lg rounded-3"
                      >
                        첫 프로그램 만들기
                        <font-awesome-icon
                          icon="fa-solid fa-plus"
                          class="ms-2"
                        />
                      </RouterLink>
                    </div>
                    <div v-else class="programs-container">
                      <ProgramList
                        :programs="registeredPrograms"
                        :useContainer="false"
                        :cols="{ sm: 1, md: 2, lg: 3 }"
                        :gap="4"
                      />
                    </div>
                  </div>

                  <!-- Ongoing Programs -->
                  <div
                    v-if="activeTab === 'ongoing'"
                    class="animate__animated animate__fadeIn"
                  >
                    <div
                      v-if="ongoingPrograms.length === 0"
                      class="empty-state"
                    >
                      <div class="empty-icon">
                        <font-awesome-icon icon="fa-solid fa-dumbbell" />
                      </div>
                      <p class="text-muted mb-4" style="font-size: large">
                        진행 중인 프로그램이 없습니다.
                      </p>
                      <RouterLink
                        to="/"
                        class="btn btn-outline-primary btn-lg rounded-3"
                      >
                        프로그램 찾아보기
                        <font-awesome-icon
                          icon="fa-solid fa-search"
                          class="ms-2"
                        />
                      </RouterLink>
                    </div>
                    <div v-else class="programs-container">
                      <ProgramList
                        :programs="ongoingPrograms"
                        :useContainer="false"
                        :cols="{ sm: 1, md: 2, lg: 3 }"
                        :gap="4"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useProgramStore } from "@/stores/program";
import { useLoginStore } from "@/stores/login";
import { useTodoStore } from "@/stores/todo";
import ProgramList from "@/components/program/ProgramList.vue";
import { RouterLink, useRouter } from "vue-router";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

const router = useRouter();
const programStore = useProgramStore();
const loginStore = useLoginStore();
const todoStore = useTodoStore();

const activeTab = ref("registered");
const isLoadingProgress = ref(false);
const programProgress = ref(new Map());

// 로그인 상태 감시
watch(
  () => loginStore.loginUserId,
  (newVal) => {
    if (!newVal) {
      router.push("/login");
    }
  }
);

const checkAllProgramsProgress = async () => {
  if (!loginStore.loginUserId) return;

  isLoadingProgress.value = true;
  try {
    await Promise.all(
      programStore.programs.map(async (program) => {
        const progress = await todoStore.checkProgramProgress(
          program.programId,
          loginStore.loginUserId
        );
        programProgress.value.set(program.programId, progress.inProgress);
      })
    );
  } catch (error) {
    console.error("프로그램 진행상태 확인 중 오류:", error);
  } finally {
    isLoadingProgress.value = false;
  }
};

watch(activeTab, async (newTab) => {
  if (newTab === "ongoing") {
    await checkAllProgramsProgress();
  }
});

onMounted(async () => {
  if (loginStore.loginUserId) {
    await programStore.getAllPrograms();
    if (activeTab.value === "ongoing") {
      await checkAllProgramsProgress();
    }
  }
});

const registeredPrograms = computed(() => {
  return programStore.programs.filter(
    (program) => program.userId === loginStore.loginUserId
  );
});

const ongoingPrograms = computed(() => {
  return programStore.programs.filter((program) =>
    programProgress.value.get(program.programId)
  );
});
</script>

<style scoped>
/* Animated Background */
.header-section {
  /* background: linear-gradient(120deg, #d3eafa 0%, #ecfafe 80%); */
  background-color: #e6f5ff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.program-header {
  display: flex;
  flex-direction: column;
  justify-content: center;

  width: 960px;
  height: 250px;

  padding-top: 30px;
}

.program-body-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.program-body {
  width: 1280px;
}

.animated-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg"><circle cx="50" cy="50" r="40" fill="rgba(255,255,255,0.1)"/></svg>')
    0 0/50px 50px;
  animation: slide 20s linear infinite;
  opacity: 0.5;
}

@keyframes slide {
  from {
    background-position: 0 0;
  }

  to {
    background-position: 100px 100px;
  }
}

/* Action Cards */
.action-card {
  transition: transform 0.3s, box-shadow 0.3s;
  border: 1px solid rgba(0, 0, 0, 0.1);
}

.action-card:hover {
  transform: translateY(-5px);
  /* box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1); */
}

.icon-circle {
  width: 48px;
  height: 48px;
  border-radius: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
}

/* Button style */
.text-primary {
  color: #133e87 !important; /* 진한 파란색으로 변경 */
  font-weight: 00;
}

/* Custom Tabs */
.custom-tabs {
  display: flex;
  gap: 1rem;
  border-bottom: 2px solid rgba(0, 0, 0, 0.1);
}

.tab-button {
  font-size: large;
  padding: 1rem 2rem;
  border: none;
  background: none;
  color: #6c757d;
  font-weight: 500;
  position: relative;
  transition: all 0.3s;
}

.tab-button::after {
  content: "";
  position: absolute;
  bottom: -2px;
  left: 0;
  right: 0;
  height: 2px;
  background: #133e87;
  transform: scaleX(0);
  transition: transform 0.3s;
}

.tab-button:hover {
  color: #133e87;
}

.tab-button.active {
  color: #133e87;
}

.tab-button.active::after {
  transform: scaleX(1);
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 3rem;
}

.empty-icon {
  font-size: 4rem;
  color: #dee2e6;
  margin-bottom: 1.5rem;
}

/* Programs Layout - Updated */
.programs-wrapper {
  width: 100%;
}

.programs-container {
  width: 100%;
}

/* Custom Button Styles */
.btn {
  transition: all 0.3s;
}

.btn:hover {
  /* transform: translateY(-2px); */
}

/* Custom Colors */
.btn-primary-blue {
  background-color: #c6e7ff;
  border-color: #c6e7ff;
  color: black;
}

.btn-primary-blue:hover {
  background-color: #94d2ff;
  border-color: #94d2ff;
  color: black;
}

.btn-color-orange {
  background-color: #ffddae;
  border-color: #ffddae;
  color: black;
}

.btn-color-orange:hover {
  background-color: #ffc069;
  border-color: #ffc069;
  color: black;
}

.btn-outline-primary {
  color: #133e87;
  border-color: #133e87;
}

.btn-outline-primary:hover {
  background-color: #133e87;
  border-color: #133e87;
  color: white;
}

.bg-color-blue {
  background-color: #c6e7ff;
}

.bg-color-orange {
  background-color: #ffddae;
}

/* Responsive Adjustments */
@media (max-width: 768px) {
  .custom-tabs {
    flex-direction: column;
    gap: 0.5rem;
  }

  .tab-button {
    width: 100%;
    text-align: center;
  }

  .programs-container {
    padding: 0.5rem;
  }
}
</style>
