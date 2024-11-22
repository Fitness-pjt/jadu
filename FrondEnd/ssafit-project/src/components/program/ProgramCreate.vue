<template>
  <div class="container my-5">
    <!-- 페이지 헤더 -->
    <h2 class="mb-4">프로그램 생성하기</h2>
    <div class="mb-4">
      <VideoSearch />
      <VideoList />
    </div>
    <!-- 프로그램 기본 정보 입력 폼 -->
    <div class="card mb-4">
      <div class="card-body">
        <div class="mb-3">
          <label class="form-label">프로그램 제목</label>
          <input type="text" class="form-control" v-model="programData.title" placeholder="프로그램 제목을 입력하세요">
        </div>

        <div class="mb-3">
          <label class="form-label">프로그램 설명</label>
          <textarea class="form-control" v-model="programData.description" rows="3"
            placeholder="프로그램에 대한 설명을 입력하세요"></textarea>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label class="form-label">난이도</label>
            <select class="form-select" v-model="programData.level">
              <option value="">난이도 선택</option>
              <option value="BEGINNER">초급</option>
              <option value="INTERMEDIATE">중급</option>
              <option value="ADVANCED">고급</option>
            </select>
          </div>

          <div class="col-md-6 mb-3">
            <label class="form-label">운동 기간 (주)</label>
            <input type="number" class="form-control" v-model="programData.durationWeeks" min="1" max="52">
          </div>
        </div>
      </div>
    </div>

    <!-- 비디오 검색 및 선택 영역 -->


    <!-- 에러 메시지 표시 -->
    <div v-if="programStore.error" class="alert alert-danger">
      {{ programStore.error }}
    </div>

    <!-- 제출 버튼 -->
    <div class="d-flex justify-content-center">
      <button class="btn btn-primary btn-lg px-5" @click="submitProgram"
        :disabled="!isFormValid || programStore.isLoading">
        {{ programStore.isLoading ? '생성 중...' : '프로그램 생성하기' }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useVideoStore } from '@/stores/video';
import { useProgramStore } from '@/stores/program';
import VideoList from '../video/VideoList.vue';
import VideoSearch from '../video/VideoSearch.vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const videoStore = useVideoStore();
const programStore = useProgramStore();

const programData = ref({
  title: '',
  description: '',
  level: '',
  durationWeeks: null,
  programImgPath: '',
  videoCnt: null
});

// 폼 유효성 검사
const isFormValid = computed(() => {
  return programData.value.title &&
    programData.value.description &&
    programData.value.level &&
    programData.value.durationWeeks &&
    videoStore.selectedVideos.length > 0;
});

// 프로그램 생성 제출
const submitProgram = async () => {
  if (!isFormValid.value) return;

  // try {
    // 선택된 비디오 ID 배열 생성
    const videoIds = videoStore.selectedVideos.map(video => video.id.videoId);
    const thumbnailUrl = videoStore.selectedVideos[0].snippet.thumbnails.default.url;
    const selectedVideoCount = videoIds.length;

    console.log('thumbnailUrl :>> ', thumbnailUrl);
    console.log('selectedVideoCount :>> ', selectedVideoCount);
    programData.value.programImgPath = thumbnailUrl;
    programData.value.videoCnt=selectedVideoCount;
    // 프로그램 데이터 구성
    const submitData = {
      ...programData.value,
      videoIds: videoIds
    };

    console.log('submitData :>> ', submitData);
    // store의 createProgram 액션 호출
    await programStore.createProgram(submitData);

    // 성공 처리
    alert('프로그램이 성공적으로 생성되었습니다!');

    // 상태 초기화
    programData.value = {
      title: '',
      description: '',
      level: '',
      durationWeeks: null,
      programImgPath: '',
      videoCnt: null
    };

    // 비디오 선택 초기화
    videoStore.clearSelectedVideos();

    // 프로그램 목록 페이지로 이동
    router.push('/programs');

  // } catch (error) {
  //   // 에러는 store에서 처리됨
  //   console.error('프로그램 생성 실패');
  // }

  // window.location.href = "/mypage";

};
</script>

<style scoped>
.card {
  border: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.form-control:focus,
.form-select:focus {
  border-color: #86b7fe;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, .25);
}

input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
  opacity: 1;
}
</style>