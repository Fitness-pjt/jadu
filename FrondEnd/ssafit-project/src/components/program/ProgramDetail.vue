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
            <div class="row mb-4">
                <div class="col-md-4">
                    <img :src="program.programImgPath" :alt="program.title" class="img-fluid rounded">
                </div>
                <div class="col-md-8">
                    <h1 class="mb-3">{{ program.title }}</h1>
                    <div class="program-meta mb-3">
                        <span class="badge bg-primary me-2">{{ program.level }}</span>
                        <span class="badge bg-secondary me-2">{{ program.durationWeeks }}주 프로그램</span>
                        <span class="badge bg-info">영상 {{ program.videoCnt }}개</span>
                    </div>
                    <p class="lead">{{ program.description }}</p>
                </div>
            </div>

            <!-- 버튼 그룹 -->
            <div class="d-flex gap-2 mb-4">
                <!-- 일반 사용자용 버튼 -->
                <button class="btn btn-primary" @click="startProgram" :disabled="isLoading">
                    프로그램 시작하기
                </button>
                <button class="btn btn-outline-primary" @click="toggleLike" :disabled="isLoading">
                    <i :class="isLiked ? 'bi bi-heart-fill' : 'bi bi-heart'"></i>
                    좋아요
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
            <ul class="nav nav-tabs mb-3">
                <li class="nav-item">
                    <a class="nav-link" :class="{ active: activeTab === 'videos' }" @click="activeTab = 'videos'"
                        href="#">
                        운동 영상
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" :class="{ active: activeTab === 'reviews' }" @click="activeTab = 'reviews'"
                        href="#">
                        리뷰
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" :class="{ active: activeTab === 'qna' }" @click="activeTab = 'qna'" href="#">
                        Q&A
                    </a>
                </li>
            </ul>

            <!-- 탭 컨텐츠 -->
            <div class="tab-content">
                <div v-if="activeTab === 'videos'" class="tab-pane active">
                    <!-- 비디오 리스트 컴포넌트 자리 -->
                    <p>비디오 리스트가 들어갈 자리입니다.</p>
                </div>
                <div v-else-if="activeTab === 'reviews'" class="tab-pane active">
                    <!-- 리뷰 컴포넌트 자리 -->
                    <p>리뷰가 들어갈 자리입니다.</p>
                </div>
                <div v-else-if="activeTab === 'qna'" class="tab-pane active">
                    <!-- Q&A 컴포넌트 자리 -->
                    <p>Q&A가 들어갈 자리입니다.</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useProgramStore } from '@/stores/program';
import { useLoginStore } from '@/stores/login';

const route = useRoute();
const router = useRouter();
const loginStore = useLoginStore();
const programStore = useProgramStore();
const programId = route.params.programId;

const program = ref(null);
const isLoading = ref(true);
const error = ref(null);
const activeTab = ref('videos');
const isLiked = ref(false);

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
  if (!confirm('정말로 이 프로그램을 삭제하시겠습니까?')) {
    return;
  }

  try {
    await programStore.deleteProgram(programId);
    alert('프로그램이 성공적으로 삭제되었습니다.');
    router.push('/programs'); // 프로그램 목록으로 이동
  } catch (err) {
    console.error(err);
    alert('프로그램 삭제에 실패했습니다.');
  }
};

// 프로그램 정보 가져오기
const fetchProgramDetail = async () => {
    try {
        isLoading.value = true;
        program.value = await programStore.getProgramById(programId);
    } catch (err) {
        error.value = '프로그램 정보를 불러오는데 실패했습니다.';
        console.error(err);
    } finally {
        isLoading.value = false;
    }
};

// 프로그램 시작하기
const startProgram = async () => {
    try {
        // TODO: 프로그램 시작 로직 구현
        await programStore.startProgram(programId);
        // TODO: Todo 리스트에 추가하는 로직 구현
    } catch (err) {
        console.error(err);
        alert('프로그램 시작에 실패했습니다.');
    }
};

// 좋아요 토글
const toggleLike = async () => {
    try {
        isLiked.value = !isLiked.value;
        // TODO: 좋아요 API 호출 로직 구현
        await programStore.toggleProgramLike(programId);
    } catch (err) {
        isLiked.value = !isLiked.value; // 실패시 원상복구
        console.error(err);
        alert('좋아요 처리에 실패했습니다.');
    }
};

// 컴포넌트 마운트 시 데이터 가져오기
onMounted(() => {
    fetchProgramDetail();
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

.nav-link {
    cursor: pointer;
}

.tab-content {
    padding: 20px 0;
}

.badge {
    font-size: 0.9rem;
    padding: 0.5em 1em;
}
</style>