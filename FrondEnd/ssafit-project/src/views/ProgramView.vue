<template>
  <div class="container-fluid py-5 bg-light">
    <div class="container">
      <div class="card shadow-lg border-0 rounded-4 overflow-hidden">
        <div class="card-body p-0">
          <!-- Header Section with Animated Background -->
          <div class="header-section p-5 position-relative overflow-hidden">
            <div class="animated-bg"></div>
            <h2 class="display-6 fw-bold text-white mb-4 position-relative">
              <font-awesome-icon icon="fa-solid fa-dumbbell" class="me-2" />
              내 프로그램 관리
            </h2>

            <!-- Action Cards -->
            <div class="row g-4 mb-5">
              <div class="col-md-6">
                <div class="action-card bg-white p-4 rounded-4 h-100 animate__animated animate__fadeInLeft">
                  <div class="d-flex align-items-center mb-3">
                    <div class="icon-circle bg-primary-subtle me-3">
                      <font-awesome-icon icon="fa-solid fa-pencil-alt" class="text-primary" />
                    </div>
                    <h3 class="h5 mb-0">직접 프로그램 만들기</h3>
                  </div>
                  <p class="text-muted mb-4">나만의 맞춤 운동 프로그램을 직접 제작해보세요.</p>
                  <RouterLink to="/program/create" class="btn btn-primary btn-lg w-100 rounded-3">
                    프로그램 만들기
                    <font-awesome-icon icon="fa-solid fa-arrow-right" class="ms-2" />
                  </RouterLink>
                </div>
              </div>

              <div class="col-md-6">
                <div class="action-card bg-white p-4 rounded-4 h-100 animate__animated animate__fadeInRight">
                  <div class="d-flex align-items-center mb-3">
                    <div class="icon-circle bg-success-subtle me-3">
                      <font-awesome-icon icon="fa-solid fa-robot" class="text-success" />
                    </div>
                    <h3 class="h5 mb-0">AI 추천 받기</h3>
                  </div>
                  <p class="text-muted mb-4">AI가 당신에게 맞는 최적의 프로그램을 추천해드립니다.</p>
                  <RouterLink to="/program/createAi" class="btn btn-success btn-lg w-100 rounded-3">
                    AI 추천받기
                    <font-awesome-icon icon="fa-solid fa-magic" class="ms-2" />
                  </RouterLink>
                </div>
              </div>
            </div>

            <!-- Custom Tabs -->
            <div class="custom-tabs mb-4">
              <button @click="activeTab = 'registered'"
                :class="['tab-button', activeTab === 'registered' ? 'active' : '']">
                <font-awesome-icon icon="fa-solid fa-folder-plus" class="me-2" />
                내가 등록한 프로그램
              </button>
              <button @click="activeTab = 'ongoing'" :class="['tab-button', activeTab === 'ongoing' ? 'active' : '']">
                <font-awesome-icon icon="fa-solid fa-running" class="me-2" />
                진행중인 프로그램
              </button>
            </div>
          </div>

          <!-- Content Section -->
          <div class="content-section p-5">
            <!-- Loading State -->
            <div v-if="programStore.isLoading || isLoadingProgress"
              class="text-center py-5 animate__animated animate__pulse animate__infinite">
              <div class="spinner-grow text-primary mb-3" role="status">
                <span class="visually-hidden">Loading...</span>
              </div>
              <p class="text-muted">{{ isLoadingProgress ? '진행중인 프로그램을 확인하고 있습니다...' : '로딩 중...' }}</p>
            </div>

            <!-- Error State -->
            <div v-else-if="programStore.error"
              class="alert alert-danger d-flex align-items-center animate__animated animate__shakeX">
              <font-awesome-icon icon="fa-solid fa-exclamation-circle" class="me-2" />
              {{ programStore.error }}
            </div>

            <!-- Program Lists -->
            <div v-else class="programs-wrapper">
              <!-- Registered Programs -->
              <div v-if="activeTab === 'registered'" class="animate__animated animate__fadeIn">
                <div v-if="registeredPrograms.length === 0" class="empty-state">
                  <div class="empty-icon">
                    <font-awesome-icon icon="fa-solid fa-folder-open" />
                  </div>
                  <p class="text-muted mb-4">등록한 프로그램이 없습니다.</p>
                  <RouterLink to="/program/create" class="btn btn-primary btn-lg rounded-3">
                    첫 프로그램 만들기
                    <font-awesome-icon icon="fa-solid fa-plus" class="ms-2" />
                  </RouterLink>
                </div>
                <div v-else class="programs-container">
                  <ProgramList :programs="registeredPrograms" :useContainer="false" :cols="{ sm: 1, md: 2, lg: 3 }"
                    :gap="4" />
                </div>
              </div>

              <!-- Ongoing Programs -->
              <div v-if="activeTab === 'ongoing'" class="animate__animated animate__fadeIn">
                <div v-if="ongoingPrograms.length === 0" class="empty-state">
                  <div class="empty-icon">
                    <font-awesome-icon icon="fa-solid fa-dumbbell" />
                  </div>
                  <p class="text-muted mb-4">진행중인 프로그램이 없습니다.</p>
                  <RouterLink to="/" class="btn btn-success btn-lg rounded-3">
                    프로그램 찾아보기
                    <font-awesome-icon icon="fa-solid fa-search" class="ms-2" />
                  </RouterLink>
                </div>
                <div v-else class="programs-container">
                  <ProgramList :programs="ongoingPrograms" :useContainer="false" :cols="{ sm: 1, md: 2, lg: 3 }"
                    :gap="4" />
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
import { ref, computed, onMounted, watch } from 'vue'
import { useProgramStore } from '@/stores/program'
import { useLoginStore } from '@/stores/login'
import { useTodoStore } from '@/stores/todo'
import ProgramList from '@/components/program/ProgramList.vue'
import { RouterLink, useRouter } from 'vue-router'

const router = useRouter()
const programStore = useProgramStore()
const loginStore = useLoginStore()
const todoStore = useTodoStore()

const activeTab = ref('registered')
const isLoadingProgress = ref(false)
const programProgress = ref(new Map())

// 로그인 상태 감시
watch(() => loginStore.loginUserId, (newVal) => {
  if (!newVal) {
    router.push('/login')
  }
})

const checkAllProgramsProgress = async () => {
  if (!loginStore.loginUserId) return

  isLoadingProgress.value = true
  try {
    await Promise.all(
      programStore.programs.map(async (program) => {
        const progress = await todoStore.checkProgramProgress(
          program.programId,
          loginStore.loginUserId
        )
        programProgress.value.set(program.programId, progress.inProgress)
      })
    )
  } catch (error) {
    console.error('프로그램 진행상태 확인 중 오류:', error)
  } finally {
    isLoadingProgress.value = false
  }
}

watch(activeTab, async (newTab) => {
  if (newTab === 'ongoing') {
    await checkAllProgramsProgress()
  }
})

onMounted(async () => {
  if (loginStore.loginUserId) {
    await programStore.getAllPrograms()
    if (activeTab.value === 'ongoing') {
      await checkAllProgramsProgress()
    }
  }
})

const registeredPrograms = computed(() => {
  return programStore.programs.filter(program =>
    program.userId === loginStore.loginUserId
  )
})

const ongoingPrograms = computed(() => {
  return programStore.programs.filter(program =>
    programProgress.value.get(program.programId)
  )
})
</script>

<style scoped>
/* Animated Background */
.header-section {
  background: linear-gradient(135deg, #C6E7FF 0%, #D4F6FF 100%);
}

.animated-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg"><circle cx="50" cy="50" r="40" fill="rgba(255,255,255,0.1)"/></svg>') 0 0/50px 50px;
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
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
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

/* Custom Tabs */
.custom-tabs {
  display: flex;
  gap: 1rem;
  border-bottom: 2px solid rgba(0, 0, 0, 0.1);
}

.tab-button {
  padding: 1rem 2rem;
  border: none;
  background: none;
  color: #6c757d;
  font-weight: 500;
  position: relative;
  transition: all 0.3s;
}

.tab-button::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  right: 0;
  height: 2px;
  background: #0d6efd;
  transform: scaleX(0);
  transition: transform 0.3s;
}

.tab-button:hover {
  color: #0d6efd;
}

.tab-button.active {
  color: #0d6efd;
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
  padding: 1rem 0;
}

/* Custom Button Styles */
.btn {
  transition: all 0.3s;
}

.btn:hover {
  transform: translateY(-2px);
}

/* Custom Colors */
.btn-primary {
  background-color: #C6E7FF;
  border-color: #C6E7FF;
  color: #000;
}

.btn-success {
  background-color: #D4F6FF;
  border-color: #D4F6FF;
  color: #000;
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