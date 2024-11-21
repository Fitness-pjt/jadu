<template>
  <div class="container py-4">
    <div class="card shadow-sm border-0">
      <div class="card-body">
        <!-- 헤더 -->
        <h2 class="card-title text-center mb-4">
          <i class="bi bi-search me-2"></i>운동 키워드 선택
        </h2>

        <!-- 키워드 그룹 -->
        <div class="row g-4">
          <!-- 키워드 (부위 + 목적) -->
          <div class="col-12">
            <div class="card h-100 border-0 bg-light">
              <div class="card-body">
                <h3 class="group-title">
                  <i class="bi bi-tags me-2"></i>키워드
                </h3>
                <div class="d-flex flex-wrap gap-2">
                  <button
                    v-for="kw in mainKeywords"
                    :key="kw"
                    class="btn btn-outline-primary rounded-pill btn-sm"
                    :class="{ 'active': keyword === kw }"
                    @click="selectKeyword(kw)"
                  >
                    {{ kw }}
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- 난이도 -->
          <div class="col-12">
            <div class="card border-0 bg-light">
              <div class="card-body">
                <h3 class="group-title">
                  <i class="bi bi-graph-up me-2"></i>난이도
                </h3>
                <div class="d-flex flex-wrap gap-2 justify-content-center">
                  <button
                    v-for="kw in levelKeywords"
                    :key="kw"
                    class="btn btn-outline-success rounded-pill btn-sm"
                    :class="{ 'active': selectedLevel === kw }"
                    @click="selectLevel(kw)"
                  >
                    {{ kw }}
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 직접 검색 토글 -->
        <div class="text-center my-4">
          <button 
            class="btn btn-outline-secondary rounded-pill btn-sm"
            @click="toggleDirectSearch"
          >
            <i class="bi" :class="showDirectSearch ? 'bi-chevron-up' : 'bi-chevron-down'"></i>
            직접 검색 {{ showDirectSearch ? '닫기' : '열기' }}
          </button>
        </div>

        <!-- 직접 검색 입력창 -->
        <div v-if="showDirectSearch" 
             class="collapse show direct-search-wrapper">
          <div class="input-group">
            <span class="input-group-text bg-white">
              <i class="bi bi-keyboard"></i>
            </span>
            <input
              type="text"
              v-model.trim="directKeyword"
              @keyup.enter="searchWithDirectKeyword"
              class="form-control"
              placeholder="검색어를 직접 입력하세요"
            />
          </div>
        </div>

        <!-- 검색 버튼 -->
        <div class="text-center mt-4" v-if="keyword || directKeyword">
          <div class="selected-keywords mb-3">
            <span class="badge bg-primary rounded-pill" v-if="keyword || directKeyword">
              {{ getSearchKeyword }}
            </span>
            <span class="badge bg-success rounded-pill ms-2" v-if="selectedLevel">
              {{ selectedLevel }}
            </span>
          </div>
          <button @click="search" class="btn btn-primary rounded-pill px-4">
            <i class="bi bi-search me-2"></i>운동 영상 검색하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import { ref, computed } from "vue";

const keyword = ref("");
const selectedLevel = ref("");
const directKeyword = ref("");
const showDirectSearch = ref(false);
const store = useVideoStore();

// 키워드를 하나의 배열로 통합
const mainKeywords = [
  '상체', '하체', '가슴', '등', '어깨', '복부',
  '다이어트', '체력 증진', '근력 강화', '근육량 증가', 
  '심폐지구력 향상', '자세 교정', '스트레스 해소', '체형 개선'
];
const levelKeywords = ['초급', '중급', '고급'];

const getSearchKeyword = computed(() => {
  if (directKeyword.value) return directKeyword.value;
  return keyword.value + (selectedLevel.value ? ` ${selectedLevel.value}` : '');
});

const selectKeyword = (value) => {
  keyword.value = keyword.value === value ? '' : value;
  directKeyword.value = '';
};

const selectLevel = (value) => {
  selectedLevel.value = selectedLevel.value === value ? '' : value;
};

const toggleDirectSearch = () => {
  showDirectSearch.value = !showDirectSearch.value;
  if (showDirectSearch.value) {
    keyword.value = '';
    selectedLevel.value = '';
  } else {
    directKeyword.value = '';
  }
};

const searchWithDirectKeyword = () => {
  if (directKeyword.value) {
    search();
  }
};

const search = () => {
  const searchTerm = getSearchKeyword.value;
  if (searchTerm) {
    store.youtubeSearch(searchTerm + " workout");
  }
};
</script>

<style scoped>
.group-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #495057;
  margin-bottom: 1rem;
}

.card {
  transition: all 0.3s ease;
}

.btn-outline-primary,
.btn-outline-success {
  --bs-btn-hover-transform: translateY(-2px);
  transition: all 0.2s ease;
}

.btn-outline-primary:hover,
.btn-outline-success:hover {
  transform: translateY(-2px);
}

.btn-outline-primary.active,
.btn-outline-success.active {
  transform: translateY(0);
}

.direct-search-wrapper {
  max-width: 500px;
  margin: 0 auto;
}

.input-group-text {
  border-right: none;
  background-color: transparent;
}

.form-control {
  border-left: none;
}

.form-control:focus {
  border-color: #dee2e6;
  box-shadow: none;
}

.input-group:focus-within {
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
  border-radius: 0.375rem;
}

.selected-keywords {
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.badge {
  font-size: 0.9rem;
  padding: 0.5rem 1rem;
}

@media (max-width: 768px) {
  .btn-sm {
    font-size: 0.75rem;
    padding: 0.25rem 0.5rem;
  }
  
  .group-title {
    font-size: 1rem;
  }
  
  .badge {
    font-size: 0.8rem;
    padding: 0.4rem 0.8rem;
  }
}
</style>