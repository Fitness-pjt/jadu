<template>
  <div class="video-search">
    <!-- 헤더 -->
    <div class="header-content text-center">
      <div class="main-title mb-3">
        <i class="bi bi-search px-3"></i>운동 영상 검색
      </div>
      <p class="subtitle">
        키워드를 입력해 원하는 운동 영상을 찾아 프로그램을 완성하세요!
      </p>
    </div>

    <!-- 메인 콘텐츠 -->
    <div class="content-wrapper">
      <!-- 키워드 그룹 -->
      <div class="keyword-sections">
        <!-- 운동 목적 키워드 -->
        <div class="search-section purpose-section">
          <div class="d-flex gap-4 mb-3">
            <h3 class="section-title">운동 목적</h3>
            <span>*운동 목적 또는 부위 중 하나를 선택하세요. </span>
          </div>
          <div class="keyword-container">
            <button
              v-for="kw in purposeKeywords"
              :key="kw"
              class="btn keyword-btn purpose-btn"
              :class="{ 'btn-active': keyword === kw }"
              @click="selectKeyword(kw)"
            >
              {{ kw }}
            </button>
          </div>
        </div>

        <!-- 운동 부위 키워드 -->
        <div class="search-section body-section">
          <h3 class="section-title">운동 부위</h3>
          <div class="keyword-container">
            <button
              v-for="kw in bodyPartKeywords"
              :key="kw"
              class="btn keyword-btn body-btn"
              :class="{ 'btn-active': keyword === kw }"
              @click="selectKeyword(kw)"
            >
              {{ kw }}
            </button>
          </div>
        </div>

        <!-- 난이도 선택 -->
        <div class="search-section level-section">
          <div class="d-flex gap-4">
            <h3 class="section-title">난이도</h3>
            <span>*난이도 선택은 필수입니다. </span>
          </div>

          <div class="keyword-container">
            <button
              v-for="kw in levelKeywords"
              :key="kw"
              class="btn keyword-btn level-btn"
              :class="{ 'btn-active': selectedLevel === kw }"
              @click="selectLevel(kw)"
            >
              {{ getLevelText(kw) }}
            </button>
          </div>
        </div>
      </div>

      <!-- 현재 선택된 검색어 표시 영역 -->
      <div class="search-preview" v-if="getSearchKeyword && !showDirectSearch">
        <h4 class="section-title">선택한 검색어</h4>
        <div class="preview-box">
          <span class="preview-text">{{ getSearchKeyword }}</span>
        </div>
      </div>

      <!-- 직접 검색 -->
      <div class="direct-search-section">
        <button class="toggle-btn" @click="toggleDirectSearch">
          {{ showDirectSearch ? "키워드 선택으로 돌아가기" : "직접 검색하기" }}
        </button>

        <div
          v-if="showDirectSearch"
          class="direct-search animate__animated animate__fadeIn"
        >
          <div class="search-input-wrapper">
            <input
              type="text"
              v-model.trim="directKeyword"
              @keyup.enter="searchWithDirectKeyword"
              class="search-input"
              placeholder="검색어를 직접 입력하세요"
            />
          </div>
        </div>
      </div>

      <!-- 검색 버튼 -->
      <div class="search-button-container">
        <button
          @click="search"
          class="btn search-btn btn-outline-navy"
          :disabled="!isSearchable"
        >
          <i class="bi bi-search px-3"></i>
          운동 영상 검색하기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import { ref, computed } from "vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

const keyword = ref("");
const selectedLevel = ref("");
const directKeyword = ref("");
const showDirectSearch = ref(false);
const store = useVideoStore();
const purposeKeywords = [
  "다이어트",
  "체력 증진",
  "근력 강화",
  "근육량 증가",
  "심폐지구력 향상",
  "자세 교정",
];

const bodyPartKeywords = ["전신", "상체", "하체", "가슴", "등", "어깨", "복부"];
const levelKeywords = ["BEGINNER", "INTERMEDIATE", "ADVANCED"];

const getLevelText = (level) => {
  const levels = {
    BEGINNER: "초급자",
    INTERMEDIATE: "중급자",
    ADVANCED: "상급자",
  };
  return levels[level] || level;
};

const getSearchKeyword = computed(() => {
  if (directKeyword.value) return directKeyword.value;
  return (
    keyword.value +
    (selectedLevel.value ? ` ${getLevelText(selectedLevel.value)}` : "")
  );
});

const isSearchable = computed(() => {
  return Boolean(directKeyword.value || keyword.value);
});

const selectLevel = (value) => {
  selectedLevel.value = selectedLevel.value === value ? "" : value;
};

const toggleDirectSearch = () => {
  showDirectSearch.value = !showDirectSearch.value;
  if (showDirectSearch.value) {
    keyword.value = "";
    selectedLevel.value = "";
  } else {
    directKeyword.value = "";
  }
};

const searchWithDirectKeyword = () => {
  if (directKeyword.value) {
    search();
  }
};
// script setup 부분에 추가
const selectKeyword = (value) => {
  if (showDirectSearch.value) {
    showDirectSearch.value = false;
    directKeyword.value = "";
  }
  keyword.value = keyword.value === value ? "" : value;
};

const search = () => {
  const searchTerm = getSearchKeyword.value;
  if (searchTerm) {
    store.youtubeSearch(searchTerm + " workout");
    getSearchKeyword.value = "";
  }
};
</script>
<style scoped>
:root {
  --primary-blue: #c6e7ff;
  --secondary-blue: #d4f6ff;
  --neutral: #fbfbfb;
  --accent: #ffddae;
}

.video-search {
  max-width: 1000px;
  margin: 0 auto;
  padding: 2rem;
}

.main-title {
  font-size: 2rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 0.5rem;
}

.subtitle {
  color: #666;
  font-size: 1.1rem;
}

.content-wrapper {
  background: white;
  padding: 2.5rem;
  border-radius: 20px;
  /* box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08); */
}

.search-preview {
  background: #c6e7ff;
  display: flex;
  flex-direction: column;

  padding: 1.5rem;
  border-radius: 15px;
  margin-top: 2rem;
  text-align: center;
  animation: fadeIn 0.5s ease;
}

.preview-box {
  background: white;
  padding: 1rem 2rem;
  /* border: 1px solid #333; */
  border-radius: 10px;
  display: inline-block;
  min-width: 200px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.preview-text {
  font-size: 1.2rem;
  font-weight: 600;
  color: #333;
}

.keyword-sections {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.section-title {
  font-size: 1.2rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 1.5rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.search-section {
  background: white;
  padding-top: 1rem;
  padding-bottom: 1rem;
  border-radius: 15px;
  /* border: 1px solid #eee; */
  transition: all 0.3s ease;
}

.search-section:hover {
  border-color: var(--primary-blue);
  /* box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05); */
}

.keyword-container {
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
}

/* 기본 버튼 스타일 */
.btn {
  padding: 0.3rem 0.8rem; /* 버튼 크기 작게 */
  font-size: 0.8rem;
  font-weight: 600;
  /* border-radius: 30px; */
  border: 1.5px solid;
  transition: all 0.3s ease;
  cursor: pointer;
}

/* 부위별 버튼 스타일 */
.body-btn {
  border: 2px solid #c6e7ff;
  /* border-color: #c6e7ff;  */
  color: #7e7e7e;
  border-radius: 30px;
}

.body-btn:hover,
.body-btn.btn-active {
  background: #c6e7ff;
  color: #7e7e7e;
}

/* 난이도 버튼 스타일 */
.level-btn {
  border: 1px solid #133e87; /* secondary-blue */
  color: #133e87;
  border-radius: 30px;
}

.level-btn:hover,
.level-btn.btn-active {
  background: #133e87;
  color: white;
}

/* 목적별 버튼 스타일 */
.purpose-btn {
  border: 2px solid #ffddae; /* accent */
  color: #7e7e7e;
  border-radius: 30px;
}

.purpose-btn:hover,
.purpose-btn.btn-active {
  background: #ffddae;
  color: #7e7e7e;
}

.direct-search-section {
  margin: 2rem 0;
  text-align: center;
}

.toggle-btn {
  margin-bottom: 1rem;
  padding: 0.6rem 1rem; /* 적당한 크기로 조정 */
  font-size: 1rem;
  font-weight: 600;
  border: none; /* 기본 상태에서 border 제거 */
  background: transparent; /* 배경색 제거 */
  color: #333; /* 텍스트 색상 */
  border-radius: 8px; /* 약간 둥글게 */
  transition: all 0.3s ease;
  cursor: pointer;
}

.toggle-btn:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.search-input-wrapper {
  max-width: 600px;
  margin: 0 auto;
  animation: slideDown 0.3s ease;
}

.search-input {
  width: 100%;
  padding: 1rem 1.5rem;
  border: 2px solid var(--primary-blue);
  border-radius: 10px;
  font-size: 1.1rem;
  transition: all 0.3s ease;
  background: white;
}

.search-input:focus {
  outline: none;
  border-color: #ffddae;
  box-shadow: 0 0 0 4px rgba(255, 221, 174, 0.25);
}

.search-button-container {
  /* margin-top: 3rem; */
  text-align: center;
}

.search-btn {
  padding: 1rem 3rem;
  font-size: 1.2rem;
  /* background: var(--primary-blue); */
  min-width: 250px;
  border-radius: 20px;
}

/* 애니메이션 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 기존 스타일 유지 후 미디어 쿼리 추가 */

/* Large Desktop (1200px 이상) */
@media (min-width: 1200px) {
  .video-search {
    max-width: 1000px;
    padding: 2rem;
  }

  .content-wrapper {
    padding: 2.5rem;
  }

  .keyword-container {
    gap: 1rem;
  }
}

/* Desktop & Tablet (992px ~ 1199px) */
@media (max-width: 1199px) {
  .video-search {
    max-width: 90%;
    padding: 1.5rem;
  }

  .main-title {
    font-size: 1.8rem;
  }

  .content-wrapper {
    padding: 2rem;
  }

  .preview-text {
    font-size: 1.1rem;
  }
}

/* Tablet (768px ~ 991px) */
@media (max-width: 991px) {
  .video-search {
    padding: 1rem;
  }

  .main-title {
    font-size: 1.6rem;
  }

  .subtitle {
    font-size: 1rem;
  }

  .content-wrapper {
    padding: 1.5rem;
  }

  .keyword-container {
    gap: 0.6rem;
  }

  .btn {
    padding: 0.3rem 0.6rem;
    font-size: 0.75rem;
  }

  .search-preview {
    padding: 1.2rem;
  }

  .preview-text {
    font-size: 1rem;
  }

  .search-input {
    padding: 0.8rem 1.2rem;
    font-size: 1rem;
  }
}

/* Mobile (576px ~ 767px) */
@media (max-width: 767px) {
  .video-search {
    padding: 0.8rem;
  }

  .main-title {
    font-size: 1.4rem;
  }

  .subtitle {
    font-size: 0.9rem;
  }

  .content-wrapper {
    padding: 1rem;
    border-radius: 15px;
  }

  .section-title {
    font-size: 1.1rem;
    margin-bottom: 1rem;
  }

  .search-section {
    padding: 0.8rem;
  }

  .keyword-container {
    gap: 0.5rem;
  }

  .btn {
    padding: 0.25rem 0.5rem;
    font-size: 0.7rem;
  }

  .search-preview {
    padding: 1rem;
    margin-top: 1.5rem;
  }

  .preview-box {
    padding: 0.8rem 1.5rem;
    min-width: 150px;
  }

  .preview-text {
    font-size: 0.9rem;
  }

  .direct-search-section {
    margin: 1.5rem 0;
  }

  .toggle-btn {
    padding: 0.5rem 0.8rem;
    font-size: 0.9rem;
  }

  .search-input {
    padding: 0.8rem 1rem;
    font-size: 0.9rem;
  }

  .search-button-container {
    margin-top: 1.5rem;
  }

  .search-btn {
    padding: 0.8rem 2rem;
    font-size: 1rem;
    min-width: 200px;
  }
}

/* Small Mobile (375px 이하) */
@media (max-width: 375px) {
  .video-search {
    padding: 0.5rem;
  }

  .main-title {
    font-size: 1.2rem;
  }

  .content-wrapper {
    padding: 0.8rem;
    border-radius: 10px;
  }

  .section-title {
    font-size: 1rem;
  }

  .search-section {
    flex-direction: column;
  }

  .keyword-container {
    gap: 0.4rem;
  }

  .btn {
    padding: 0.2rem 0.4rem;
    font-size: 0.75rem;
  }

  .preview-box {
    padding: 0.6rem 1rem;
    min-width: 120px;
  }

  .toggle-btn {
    padding: 0.4rem 0.6rem;
    font-size: 0.8rem;
  }

  .search-input {
    padding: 0.6rem 0.8rem;
    font-size: 0.8rem;
  }

  .search-btn {
    padding: 0.6rem 1.5rem;
    font-size: 0.9rem;
    min-width: 180px;
  }
}

/* 키워드 그룹 반응형 조정 */
@media (max-width: 576px) {
  .search-section .d-flex {
    flex-direction: column;
    gap: 0.5rem;
  }

  .search-section .d-flex span {
    font-size: 0.8rem;
  }
}

/* 애니메이션 최적화 */
@media (prefers-reduced-motion: reduce) {
  .search-preview,
  .search-input-wrapper,
  .btn,
  .search-section {
    animation: none;
    transition: none;
  }
}
</style>
