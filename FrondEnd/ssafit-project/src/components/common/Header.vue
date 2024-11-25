<template>
  <header class="header container-fluid shadow-sm py-3" ref="header">
    <div class="container d-flex justify-content-between align-items-center">
      <!-- Logo Section -->
      <h1 class="logo fs-2 fw-bold m-0">
        <RouterLink
          :to="{ name: 'home' }"
          class="text-decoration-none logo-text"
        >
          <img src="@/assets/image/logo.png" style="width: 50px" />
          JADU
        </RouterLink>
      </h1>

      <!-- Hamburger Menu Button -->
      <button
        class="navbar-toggler d-lg-none"
        type="button"
        @click="toggleMenu"
        aria-label="Toggle navigation"
      >
        <i class="bi" :class="isMenuOpen ? 'bi-x' : 'bi-list'"></i>
      </button>

      <!-- Navigation Links -->
      <nav class="nav gap-4" :class="{ show: isMenuOpen }">
        <RouterLink
          :to="{ name: 'home' }"
          class="nav-link px-3 rounded-pill"
          @click="closeMenu"
        >
          <i class="bi bi-house-door me-1"></i>홈
        </RouterLink>
        <RouterLink
          :to="{ name: 'program' }"
          class="nav-link px-3 rounded-pill"
          @click="closeMenu"
        >
          <i class="bi bi-play-circle me-1"></i>프로그램
        </RouterLink>
        <RouterLink
          :to="{ name: 'mypage' }"
          class="nav-link px-3 rounded-pill"
          v-if="token"
          @click="closeMenu"
        >
          <i class="bi bi-person me-1"></i>마이페이지
        </RouterLink>
      </nav>

      <!-- Authentication Links -->
      <div
        class="auth d-flex align-items-center gap-3"
        :class="{ show: isMenuOpen }"
        :style="{ top: authTopValue }"
      >
        <div v-if="!token" class="d-flex gap-3 logout-status">
          <RouterLink
            :to="{ name: 'login' }"
            class="btn btn-outline-primary rounded-pill px-4"
            @click="closeMenu"
          >
            <i class="bi bi-box-arrow-in-right me-1"></i>로그인
          </RouterLink>
          <RouterLink
            :to="{ name: 'signup' }"
            class="btn btn-outline-primary rounded-pill px-4"
            @click="closeMenu"
          >
            <i class="bi bi-person-plus me-1"></i>회원가입
          </RouterLink>
        </div>
        <div v-else class="d-flex align-items-center gap-3 login-status">
          <div class="welcome">
            <RouterLink
              :to="getRoute(loginStore.loginUserId)"
              class="btn btn-name-tag"
              @click="closeMenu"
            >
              <UserNameTag :user-id="loginStore.loginUserId" />
            </RouterLink>
          </div>
          <button @click="logout" class="btn btn-outline-logout">
            <i class="bi bi-box-arrow-right me-1"></i>로그아웃
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, computed } from "vue";
import { useLoginStore } from "@/stores/login";
import UserNameTag from "./UserNameTag.vue";

const loginStore = useLoginStore();
const token = sessionStorage.getItem("access-token");
const isMenuOpen = ref(false);
const header = ref(null);

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value;
};

const closeMenu = () => {
  isMenuOpen.value = false;
};

// 동적으로 top 값을 계산하는 computed 속성
const authTopValue = computed(() => {
  return token ? "calc(5rem + 200px)" : "calc(5rem + 130px)";
});

const logout = () => {
  loginStore.logout();
  closeMenu();
};

const getRoute = (userId) => {
  return loginStore.loginUserId === userId
    ? { name: "mypage", params: { userId } }
    : { name: "profile", params: { userId } };
};

// Event listener for detecting clicks outside the header
const handleClickOutside = (event) => {
  if (header.value && !header.value.contains(event.target)) {
    closeMenu();
  }
};

onMounted(() => {
  document.addEventListener("click", handleClickOutside);
});

onBeforeUnmount(() => {
  document.removeEventListener("click", handleClickOutside);
});
</script>

<style scoped>
.header {
  height: 6rem;
  background-color: #fbfbfb;
  border-bottom: 1px solid rgba(198, 231, 255, 0.3);
}

.logo-text {
  color: #133e87;
}

.nav-link {
  color: #133e87;
  font-size: 1.2rem;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.nav-link:hover,
.nav-link.router-link-active {
  color: #133e87;
  font-weight: 700;
  transition: all 0.3s ease;
}

.navbar-toggler {
  display: none;
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #133e87;
  cursor: pointer;
}

.btn-primary {
  background-color: #c6e7ff;
  border-color: #c6e7ff;
  color: #133e87;
}

.btn-primary:hover {
  background-color: #d4f6ff;
  border-color: #c6e7ff;
  color: #133e87;
}

.btn-outline-primary {
  color: #133e87;
  border-color: #c6e7ff;
}

.btn-outline-primary:hover {
  background-color: #d4f6ff;
  border-color: #c6e7ff;
  color: #133e87;
}

.btn {
  border: none;
  transition: all 0.3s ease;
  font-size: 1.2rem;
}

.btn-name-tag {
  background-color: #fbfbfb;
  color: #133e87;
}

.btn-name-tag:hover {
  color: #133e87;
}

.btn-outline-logout {
  color: #ff6b6b;
}

.btn-outline-logout:hover {
  background-color: #ff6b6b;
  color: white;
  border-radius: 40px;
}

@media (max-width: 991px) {
  .header {
    height: auto;
    padding: 1rem 0;
  }

  .navbar-toggler {
    display: block;
  }

  .nav,
  .auth {
    display: none !important;
    position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    background-color: #fbfbfb;
    padding: 1rem;
    /* box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); */
    z-index: 1000;
  }

  .nav.show,
  .auth.show {
    display: flex !important;
    flex-direction: column;
  }

  .auth.show {
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  }

  .nav {
    top: 4rem;
    z-index: 1000;
  }

  .auth {
    top: calc(4rem + 150px);
    z-index: 999;
  }

  .auth > div {
    flex-direction: row;
    width: 100%;
  }

  .auth > div > button {
    max-width: 200px;
  }

  .logout-status {
    max-width: 400px;
  }

  .nav-link {
    width: 100%;
    /* text-align: center; */
    visibility: visible !important; /* 링크가 화면에 보이도록 */
  }

  .btn {
    width: 100%;
    text-align: center;
    margin: 0.5rem 0;
  }

  .welcome {
    width: 100%;
  }

  .welcome .btn {
    width: 100%;
  }
}

@media (max-width: 576px) {
  .logo {
    font-size: 1.5rem !important;
  }
}
</style>
