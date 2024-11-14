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
// 필요한 경우 props나 emit 등을 여기에 정의
</script>

<style scoped>
.footer {
  width: 100%;
  background-color: #f8f9fa;
  padding: 2rem 0;
  margin-top: auto;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.footer-logo {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 2rem;
}

.footer-sections {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2rem;
}

.footer-section h3 {
  font-size: 1rem;
  margin-bottom: 1rem;
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section li {
  margin-bottom: 0.5rem;
  color: #666;
}

.store-buttons {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.store-button {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  background: white;
  cursor: pointer;
}

@media (max-width: 768px) {
  .footer-sections {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .store-buttons {
    flex-direction: column;
  }
}
</style>
