<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-logo">LOGO</div>
      <div class="footer-sections">
        <div class="footer-section">
          <h3>COMPANY</h3>
          <ul>
            <li>About Us</li>
            <li>Legal Information</li>
            <li>Contact Us</li>
            <li>Blogs</li>
          </ul>
        </div>
        <div class="footer-section">
          <h3>HELP CENTER</h3>
          <ul>
            <li>Find a Property</li>
            <li>How To Host?</li>
            <li>Why Us?</li>
            <li>FAQs</li>
            <li>Rental Guides</li>
          </ul>
        </div>
        <div class="footer-section">
          <h3>CONTACT INFO</h3>
          <ul>
            <li>Phone: 1234567890</li>
            <li>Email: company@email.com</li>
            <li>Location: 100 Smart Street, LA, USA</li>
          </ul>
        </div>
      </div>
      <div class="store-buttons">
        <button class="store-button">PlayStore</button>
        <button class="store-button">AppleStore</button>
      </div>
    </div>
  </footer>
</template>

<script setup>
// Footer에는 별도의 script 코드는 필요하지 않습니다.
</script>

<style scoped>
/* 전체 Footer 스타일 */
.footer {
  width: 100%;
  background-color: #f8f9fa;
  padding: 2rem 0;
  margin-top: auto;
  border-top: 1px solid #ddd; /* 상단 경계선 */
}

/* Footer 컨텐츠 레이아웃 */
.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

/* 로고 */
.footer-logo {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 2rem;
  color: #333;
}

/* 섹션 레이아웃 */
.footer-sections {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2rem;
}

.footer-section h3 {
  font-size: 1rem;
  margin-bottom: 1rem;
  color: #42b983; /* 포인트 컬러 */
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section li {
  margin-bottom: 0.5rem;
  color: #666;
}

/* 스토어 버튼 */
.store-buttons {
  display: flex;
  justify-content: flex-start;
  gap: 1rem;
  margin-top: 2rem;
}

.store-button {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  color: #333;
  font-size: 0.9rem;
  transition: background-color 0.3s ease, border-color 0.3s ease;
}

.store-button:hover {
  background-color: #42b983;
  border-color: #42b983;
  color: white;
}

/* 반응형 레이아웃 */
@media (max-width: 768px) {
  .footer-sections {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .store-buttons {
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>
