<template>
  <footer class="footer bg-light py-4">
    <div class="container">
      <div class="row align-items-start">
        <!-- 로고 -->
        <div class="col-md-3 text-md-start text-center mb-4 mb-md-0">
          <h2 class="footer-logo text-dark">JADU</h2>
        </div>
        <!-- COMPANY 섹션 -->
        <div class="col-md-4 text-center text-md-start mb-4 mb-md-0">
          <h5 class="footer-color">COMPANY</h5>
          <ul class="list-unstyled">
            <li>About Us</li>
            <li>Legal Information</li>
            <li>Contact Us</li>
            <li>Blogs</li>
          </ul>
        </div>
        <!-- TEAM MEMBERS 섹션 -->
        <div class="col-md-5 text-center text-md-start">
          <h5 class="footer-color">TEAM MEMBERS</h5>
          <ul class="list-unstyled d-flex flex-column flex-md-row gap-4">
            <!-- KIM MINSUNG -->
            <li>
              <strong>KIM MINSUNG</strong>
              <ul class="list-unstyled ms-3">
                <li>
                  <i class="bi bi-github me-2"></i
                  ><a
                    href="https://github.com/MINSSUNGKIM"
                    class="text-muted text-decoration-none"
                    >GitHub</a
                  >
                </li>
                <li>
                  <i class="bi bi-globe me-2"></i
                  ><a
                    href="https://mikebraindb.tistory.com/"
                    class="text-muted text-decoration-none"
                    >Blog</a
                  >
                </li>
              </ul>
            </li>
            <!-- KIM HYUNJIN -->
            <li>
              <strong>KIM HYUNJIN</strong>
              <ul class="list-unstyled ms-3">
                <li>
                  <i class="bi bi-github me-2"></i
                  ><a
                    href="https://github.com/hyun0zin"
                    class="text-muted text-decoration-none"
                    >GitHub</a
                  >
                </li>
                <li>
                  <i class="bi bi-globe me-2"></i
                  ><a
                    href="https://hyun0zinlog.tistory.com"
                    class="text-muted text-decoration-none"
                    >Blog</a
                  >
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
      <!-- 하단 -->
      <div class="row mt-4 text-center">
        <div class="col">
          <p class="mb-1 text-muted">
            Developed by
            <span style="font-weight: 700">NCTA</span>
          </p>
          <p class="mb-0 text-muted">&copy; 2024 JADU. All rights reserved.</p>
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer-logo {
  font-size: 2rem;
  font-weight: bold;
}

.footer-color {
  color: #133e87;
  font-weight: 700;
}
</style>
