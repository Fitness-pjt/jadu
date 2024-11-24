<template>
  <div class="banner-wrapper py-5">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-lg-6 text-content">
          <div class="banner-text p-4">
            <h1
              class="display-4 fw-bold text-primary mb-3 animate__animated animate__fadeInUp"
            >
              맞춤형 운동 추천
            </h1>
            <p
              class="lead text-secondary mb-4 animate__animated animate__fadeInUp animate__delay-1s"
            >
              AI가 분석하여 제공하는<br />
              당신만의 완벽한 운동 루틴
            </p>
            <div
              class="d-flex gap-3 animate__animated animate__fadeInUp animate__delay-2s"
            >
              <RouterLink
                :to="{ name: 'createAIProgram' }"
                class="text-decoration-none"
              >
                <button
                  class="btn btn-primary btn-lg d-flex align-items-center gap-2 shadow-lg"
                >
                  맞춤 추천 받기
                  <i class="bi bi-arrow-right"></i>
                </button>
              </RouterLink>
              <button
                class="btn btn-outline-primary btn-lg d-flex align-items-center gap-2"
              >
                <i class="bi bi-play-circle"></i>
                둘러보기
              </button>
            </div>
            <div
              class="mt-5 statistics d-flex gap-4 animate__animated animate__fadeInUp animate__delay-3s"
            >
              <div class="stat-item text-center">
                <h3 class="fw-bold text-primary mb-2">1000+</h3>
                <p class="text-muted">운동 영상</p>
              </div>
              <div class="stat-item text-center">
                <h3 class="fw-bold text-primary mb-2">98%</h3>
                <p class="text-muted">만족도</p>
              </div>
              <div class="stat-item text-center">
                <h3 class="fw-bold text-primary mb-2">5.0</h3>
                <p class="text-muted">평균 별점</p>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-6">
          <div class="visual-content position-relative">
            <div class="design-elements">
              <div
                class="floating-card shadow-lg bg-white p-4 rounded-4 position-absolute"
              >
                <div class="d-flex align-items-center gap-3">
                  <div
                    class="rounded-circle bg-primary"
                    style="width: 40px; height: 40px"
                  ></div>
                  <div>
                    <h6 class="mb-1">AI 맞춤 추천</h6>
                    <p class="small text-muted mb-0">실시간 분석 진행중...</p>
                  </div>
                </div>
              </div>
              <div
                class="floating-card shadow-lg bg-white p-4 rounded-4 position-absolute"
              >
                <div class="progress" style="height: 8px; width: 200px">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 75%"
                  ></div>
                </div>
                <p class="small text-muted mt-2 mb-0">주간 목표 달성률 75%</p>
              </div>
            </div>
            <div class="pattern-grid">
              <div class="grid-item rounded-4 shadow-sm"></div>
              <div class="grid-item rounded-4 shadow-sm"></div>
              <div class="grid-item rounded-4 shadow-sm"></div>
              <div class="grid-item rounded-4 shadow-sm"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 기본 스타일 */
.banner-wrapper {
  background: linear-gradient(45deg, #d4f6ff 0%, #fbfbfb 100%);
  min-height: 80vh;
  display: flex;
  align-items: center;
}

.text-primary {
  color: #133e87 !important; /* 진한 파란색으로 변경 */
}

.btn-primary {
  background-color: #133e87;
  border-color: #133e87;
  color: white; /* 버튼 텍스트 색상 변경 */
}

.btn-primary:hover {
  background-color: #ffddae;
  border-color: #ffddae;
  /* color: #133e87; */
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

/* 시각적 요소 스타일 */
.visual-content {
  height: 500px;
  position: relative;
  overflow: hidden;
}

.pattern-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 2rem;
  transform: rotate(-15deg) scale(1.2);
}

.grid-item {
  aspect-ratio: 1;
  background: #d4f6ff;
  transition: all 0.3s ease;
}

.grid-item:hover {
  background: #ffddae;
  transform: scale(1.05);
}

/* 플로팅 카드 스타일 */
.floating-card {
  z-index: 2;
}

.floating-card:nth-child(1) {
  top: 20%;
  right: 10%;
  animation: float 6s ease-in-out infinite;
}

.floating-card:nth-child(2) {
  bottom: 20%;
  left: 10%;
  animation: float 6s ease-in-out infinite;
  animation-delay: -3s;
}

.bg-primary {
  background-color: #c6e7ff !important;
}

.progress-bar {
  background-color: #ffddae;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

/* 통계 섹션 스타일 */
.statistics {
  border-top: 1px solid rgba(198, 231, 255, 0.3);
  padding-top: 2rem;
}

.stat-item {
  flex: 1;
}

/* 반응형 스타일 */
@media (max-width: 991.98px) {
  .visual-content {
    height: 400px;
    margin-top: 3rem;
  }

  .floating-card {
    transform: scale(0.8);
  }
}

@media (max-width: 767.98px) {
  .banner-text {
    text-align: center;
  }

  .d-flex {
    justify-content: center;
  }

  .visual-content {
    height: 300px;
  }
}

@media (max-width: 575.98px) {
  .btn-lg {
    padding: 0.5rem 1rem;
    font-size: 1rem;
  }

  .statistics {
    flex-direction: column;
    gap: 2rem;
  }

  .floating-card {
    transform: scale(0.7);
  }
}
</style>
