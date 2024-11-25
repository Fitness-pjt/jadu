<template>
  <footer class="footer">
    <div class="footer-container">
      <!-- 브랜드 섹션 -->
      <div class="footer-section brand-section">
        <h2 class="brand-title">JADU</h2>
        <p class="brand-description">
          건강한 라이프스타일을 위한<br />
          맞춤형 운동 프로그램 플랫폼
        </p>
        <div class="social-links">
          <a href="#" class="social-link">
            <i class="bi bi-github"></i>
          </a>
          <a href="#" class="social-link">
            <i class="bi bi-twitter-x"></i>
          </a>
        </div>
      </div>

      <!-- 서비스 섹션 -->
      <div class="footer-section">
        <h3 class="section-title">서비스</h3>
        <ul class="footer-links">
          <li><a href="#" class="no-indent">맞춤 운동 프로그램</a></li>
          <li><a href="#" class="no-indent">커뮤니티</a></li>
          <li><a href="#" class="no-indent">운동 가이드</a></li>
          <li><a href="#" class="no-indent">FAQ</a></li>
        </ul>
      </div>

      <!-- 팀 멤버 섹션 -->
      <div class="footer-section">
        <h3 class="section-title">Member</h3>
        <ul class="team-members">
          <li class="member-item no-indent">
            <span class="member-name">KIM MINSUNG</span>
            <div class="member-links">
              <a href="https://github.com/MINSSUNGKIM" class="no-indent">
                <i class="bi bi-github"></i> GitHub
              </a>
              <a href="https://mikebraindb.tistory.com/" class="no-indent">
                <i class="bi bi-globe"></i> Blog
              </a>
            </div>
          </li>
          <li class="member-item no-indent">
            <span class="member-name">KIM HYUNJIN</span>
            <div class="member-links">
              <a href="https://github.com/hyun0zin" class="no-indent">
                <i class="bi bi-github"></i> GitHub
              </a>
              <a href="https://hyun0zinlog.tistory.com" class="no-indent">
                <i class="bi bi-globe"></i> Blog
              </a>
            </div>
          </li>
        </ul>
      </div>

      <!-- 연락처 섹션 -->
      <div class="footer-section">
        <h3 class="section-title">Contact</h3>
        <ul class="contact-info">
          <li class="no-indent">
            <i class="bi bi-geo-alt"></i>
            <span>Gangnam-gu, Seoul</span>
          </li>
          <li class="no-indent">
            <i class="bi bi-telephone"></i>
            <span>1544-9001</span>
          </li>
          <li class="no-indent">
            <i class="bi bi-envelope"></i>
            <span>attnnsky@gmail.com</span>
          </li>
        </ul>
      </div>
    </div>

    <!-- 카피라이트 -->
    <div class="copyright">
      <p>
        Developed by <span class="highlight">NCTA</span> | © 2024 JADU. All rights reserved.
      </p>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background: linear-gradient(to right, #f8f9fa, #f1f3f5);
  border-top: 1px solid #e9ecef;
  padding: 4rem 0 2rem;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.footer-section {
  padding: 0;
}

.brand-section {
  max-width: 300px;
}

.brand-title {
  font-size: 2rem;
  font-weight: 700;
  color: #1a365d;
  margin: 0 0 1rem 0;
}

.section-title {
  color: #2d3748;
  font-size: 1rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin: 0 0 1.25rem 0;
}

.footer-links, .team-members, .contact-info {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-links li, .contact-info li {
  margin-bottom: 0.75rem;
}

.member-item {
  margin-bottom: 1.25rem;
}

.member-name {
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 0.5rem;
}

.member-links {
  display: flex;
  gap: 1rem;
  font-size: 0.9rem;
}

.contact-info li {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.contact-info i {
  color: #3182ce;
  width: 1rem;
}

.social-links {
  display: flex;
  gap: 1rem;
  margin: 0;
  padding: 0;
}

.social-link {
  color: #4a5568;
  font-size: 1.25rem;
  transition: color 0.2s;
}

a {
  color: #4a5568;
  text-decoration: none;
  transition: color 0.2s;
}

a:hover {
  color: #3182ce;
}

.copyright {
  text-align: center;
  margin-top: 3rem;
  padding-top: 2rem;
  border-top: 1px solid #e2e8f0;
  color: #718096;
  font-size: 0.9rem;
}

.highlight {
  font-weight: 600;
  color: #4a5568;
}

@media (max-width: 768px) {
  .footer-container {
    grid-template-columns: 1fr;
  }
  
  .brand-section {
    max-width: 100%;
  }
  
  .footer {
    padding: 3rem 1rem 1.5rem;
  }
}
</style>