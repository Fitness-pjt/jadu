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
                    <div class="row">
                        <!-- 선택된 비디오 재생 영역 -->
                        <div class="col-12 mb-4" v-if="selectedVideo">
                            <div class="video-player-wrapper">
                                <iframe :src="`https://www.youtube.com/embed/${selectedVideo.id.videoId}`"
                                    frameborder="0"
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                    allowfullscreen class="video-player"></iframe>
                            </div>
                            <div class="video-info mt-3">
                                <h3>{{ selectedVideo.snippet.title }}</h3>
                                <!-- <p>{{ selectedVideo.snippet.description }}</p> -->
                            </div>
                        </div>

                        <!-- 비디오 목록 -->
                        <div class="col-12">
                            <div class="row g-4">
                                <!-- 비디오가 없는 경우 -->
                                <div v-if="!videos.length" class="col-12">
                                    <div class="alert alert-info">
                                        등록된 비디오가 없습니다.
                                    </div>
                                </div>

                                <!-- 비디오 리스트 -->
                                <div v-for="video in videos" :key="video.id.videoId" class="col-md-6 col-lg-4"
                                    @click="selectVideo(video)" style="cursor: pointer;">
                                    <div class="card h-100"
                                        :class="{ 'border-primary': selectedVideo?.id.videoId === video.id.videoId }">
                                        <div class="thumbnail-wrapper">
                                            <img :src="video.snippet.thumbnails.medium.url" :alt="video.snippet.title"
                                                class="card-img-top">
                                            <div class="play-overlay">
                                                <i class="bi bi-play-circle-fill"></i>
                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <h5 class="card-title">{{ video.snippet.title }}</h5>
                                            <!-- <p class="card-text">{{ video.snippet.description }}</p> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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
import { useVideoStore } from '@/stores/video';

const route = useRoute();
const router = useRouter();
const loginStore = useLoginStore();
const programStore = useProgramStore();
const videoStore = useVideoStore();
const selectedVideo = ref(null);

const programId = route.params.programId;

const program = ref(null);
const isLoading = ref(true);
const error = ref(null);
const activeTab = ref('videos');
const isLiked = ref(false);
const videos = ref([]);


const selectVideo = (video) => {
    selectedVideo.value = video;
};
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
        await fetchProgramVideos(); // 프로그램 정보를 가져온 후 비디오 목록도 가져오기
    } catch (err) {
        error.value = '프로그램 정보를 불러오는데 실패했습니다.';
        console.error(err);
    } finally {
        isLoading.value = false;
    }
};
const fetchProgramVideos = async () => {
    try {
        // 프로그램 정보에서 비디오 ID 목록을 가져와서 처리
        if (program.value?.videoIds) {
            const programVideos = await videoStore.getVideosByIds(program.value.videoIds);
            videos.value = programVideos;
        }
    } catch (err) {
        console.error('비디오 로딩 실패:', err);
        error.value = '비디오 목록을 불러오는데 실패했습니다.';
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
    fetchProgramDetail().then(() => {
        if (videos.value.length > 0) {
            selectedVideo.value = videos.value[0];
        }
    });
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

.card-title {
    font-size: 1rem;
    margin-bottom: 0.5rem;
    /* 2줄 제한, 말줄임표 */
    display: block;
    max-height: 2.4em;
    line-height: 1.2;
    overflow: hidden;
    position: relative;
}

.card-title::after {
    content: '...';
    position: absolute;
    right: 0;
    bottom: 0;
    background: white;
    padding-left: 4px;
}

.card-text {
    /* 3줄 제한, 말줄임표 */
    font-size: 0.9rem;
    color: #666;
    max-height: 3.6em;
    line-height: 1.2;
    overflow: hidden;
    position: relative;
}

.card-text::after {
    content: '...';
    position: absolute;
    right: 0;
    bottom: 0;
    background: white;
    padding-left: 4px;
}

.thumbnail-wrapper {
    width: 100%;
    position: relative;
    padding-top: 56.25%;
    /* 16:9 비율 */
    overflow: hidden;
}

.card-img-top {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.card {
    transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
}

.card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.btn {
    transition: all 0.2s ease-in-out;
}

.btn:hover {
    transform: scale(0.98);
}
.video-player-wrapper {
    position: relative;
    padding-top: 56.25%; /* 16:9 비율 */
    width: 100%;
    background: #000;
}

.video-player {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}

.play-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;
    opacity: 0;
    transition: opacity 0.2s ease-in-out;
}

.play-overlay i {
    font-size: 3rem;
    color: white;
}

.card:hover .play-overlay {
    opacity: 1;
}

.video-info {
    padding: 1rem;
    background: #f8f9fa;
    border-radius: 0.5rem;
}

.video-info h3 {
    font-size: 1.25rem;
    margin-bottom: 0.5rem;
}

.video-info p {
    color: #666;
    margin-bottom: 0;
}

.border-primary {
    box-shadow: 0 0 0 2px #0d6efd;
}
</style>