<template>
  <div class="container my-5">
    <!-- 로딩 상태 -->
    <div v-if="programStore.isLoading" class="text-center">
      <div class="spinner-border" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <!-- 에러 메시지 -->
    <div v-if="programStore.error" class="alert alert-danger">
      {{ programStore.error }}
    </div>

    <!-- 편집 폼 -->
    <div v-else class="card">
      <div class="card-body">
        <h2 class="card-title mb-4">프로그램 수정</h2>

        <div class="mb-3">
          <label class="form-label">프로그램 제목</label>
          <input type="text" class="form-control" v-model="programData.title"
            :placeholder="programStore.currentProgram?.title || '프로그램 제목을 입력하세요'">
        </div>

        <div class="mb-3">
          <label class="form-label">프로그램 설명</label>
          <textarea class="form-control" v-model="programData.description" rows="3"
            :placeholder="programStore.currentProgram?.description || '프로그램에 대한 설명을 입력하세요'"></textarea>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label class="form-label">난이도</label>
            <select class="form-select" v-model="programData.level">
              <option value="">난이도 선택</option>
              <option value="BEGINNER" :selected="programStore.currentProgram?.level === 'BEGINNER'">초급</option>
              <option value="INTERMEDIATE" :selected="programStore.currentProgram?.level === 'INTERMEDIATE'">중급</option>
              <option value="ADVANCED" :selected="programStore.currentProgram?.level === 'ADVANCED'">고급</option>
            </select>
          </div>

          <div class="col-md-6 mb-3">
            <label class="form-label">운동 기간 (주)</label>
            <input type="number" class="form-control" v-model="programData.durationWeeks"
              :placeholder="programStore.currentProgram?.durationWeeks" min="1" max="52">
          </div>
        </div>

        <!-- 현재 썸네일 이미지 표시 -->
        <div class="mb-4">
          <label class="form-label">프로그램 썸네일</label>
          <div class="thumbnail-upload-container">
            <img :src="programStore.currentProgram?.programImgPath" alt="프로그램 썸네일" class="img-thumbnail"
              style="max-width: 200px">
            <div class="thumbnail-edit-overlay">
              <label class="btn btn-light">
                썸네일 변경
                <input type="file" accept="image/*" @change="handleThumbnailChange" class="d-none" />
              </label>
            </div>
          </div>
        </div>

        <!-- 비디오 목록/수정 영역 -->
        <!-- 비디오 섹션 수정 -->
        <div class="mb-4">
          <h3 class="h5 mb-3">운동 영상 관리</h3>

          <!-- 비디오 검색 -->
          <div class="mb-4">
            <h4 class="h6">새 영상 검색</h4>
            <VideoSearch />
          </div>
          <VideoList ref="videoListRef" :initial-videos="initialVideos" />

          <!-- 비디오 리스트 -->
        </div>

        <!-- 버튼 그룹 -->
        <div class="d-flex gap-2">
          <button class="btn btn-primary" @click="updateProgram" :disabled="isSubmitting">
            {{ isSubmitting ? '수정 중...' : '수정하기' }}
          </button>
          <button class="btn btn-outline-secondary" @click="goBack" :disabled="isSubmitting">
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useProgramStore } from '@/stores/program';
import { useVideoStore } from '@/stores/video';
import VideoList from '@/components/video/VideoList.vue';
import VideoSearch from '@/components/video/VideoSearch.vue';

const route = useRoute();
const router = useRouter();
const programStore = useProgramStore();
const videoStore = useVideoStore();
const videoListRef = ref(null);
const initialVideos = ref([]);
const isSubmitting = ref(false);

const programId = route.params.programId;

const programData = ref({
  title: '',
  description: '',
  level: '',
  durationWeeks: null,
  programImgPath: '',
  videoCnt: null
});

// 프로그램 정보 가져오기
const fetchProgramData = async () => {
  try {
    await programStore.getProgramById(programId);

    // 기존 데이터로 폼 초기화
    programData.value = {
      title: programStore.currentProgram.title,
      description: programStore.currentProgram.description,
      level: programStore.currentProgram.level,
      durationWeeks: programStore.currentProgram.durationWeeks,
      programImgPath: programStore.currentProgram.programImgPath,
      videoCnt: programStore.currentProgram.videoCnt
    };

    // 비디오 정보 초기화
    if (programStore.currentProgram?.videoIds) {
      const videos = await videoStore.getVideosByIds(programStore.currentProgram.videoIds);
      initialVideos.value = videos;
    }
  } catch (err) {
    console.error('프로그램 정보 로딩 실패:', err);
  }
};

// 프로그램 수정
const updateProgram = async () => {
  try {
    isSubmitting.value = true;

    // VideoList 컴포넌트에서 현재 선택된 비디오들 가져오기
    const currentVideos = videoListRef.value.getCurrentVideos();

    const updatedProgram = {
      ...programData.value,
      videoIds: currentVideos.map(video => video.id.videoId),
      videoCnt: currentVideos.length,
      programImgPath: currentVideos[0]?.snippet.thumbnails.medium.url || programData.value.programImgPath
    };

    console.log('videoCnt :>> ', currentVideos.length);
    await programStore.updateProgram(programId, updatedProgram);
    alert('프로그램이 성공적으로 수정되었습니다.');
    router.push(`/program/${programId}`);
  } catch (err) {
    console.error('프로그램 수정 실패:', err);
    alert(err.message || '프로그램 수정에 실패했습니다.');
  } finally {
    isSubmitting.value = false;
  }
};
const handleThumbnailChange = async (event) => {
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
  formData.append("fileCase", "PROGRAM");

  try {
    isSubmitting.value = true;
    const response = await programStore.updateProgramThumbnail(formData, programId);

    // 프로그램 데이터 업데이트
    programData.value = {
      ...programData.value,
      programImgPath: response.filePath
    };

    // currentProgram 직접 업데이트
    programStore.currentProgram = {
      ...programStore.currentProgram,
      programImgPath: response.filePath
    };

    await programStore.getProgramById(programId); // 프로그램 정보 다시 불러오기
    
    alert("썸네일이 성공적으로 변경되었습니다.");
  } catch (error) {
    console.error("썸네일 업로드 에러:", error);
    alert("썸네일 업로드에 실패했습니다.");
  } finally {
    isSubmitting.value = false;
    event.target.value = "";
  }
};
// 뒤로 가기
const goBack = () => {
  router.back();
};

onMounted(() => {
  fetchProgramData();
});
</script>

<style scoped>
.form-control:focus,
.form-select:focus {
  border-color: #86b7fe;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, .25);
}

.img-thumbnail {
  border-radius: 8px;
}

.thumbnail-upload-container {
  position: relative;
  display: inline-block;
}

.thumbnail-edit-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.2s ease-in-out;
}

.thumbnail-upload-container:hover .thumbnail-edit-overlay {
  opacity: 1;
}

.thumbnail-edit-overlay .btn {
  color: #000;
  background: #fff;
  border: none;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  transition: all 0.2s ease-in-out;
}

.thumbnail-edit-overlay .btn:hover {
  transform: scale(1.05);
  background: #f8f9fa;
}

.img-thumbnail {
  border-radius: 8px;
  object-fit: cover;
}
</style>