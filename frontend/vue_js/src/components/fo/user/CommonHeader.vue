<template>
  <header
    id="header"
    class="header-effect-shrink"
    data-plugin-options="{
      'stickyEnabled': true,
      'stickyEffect': 'shrink',
      'stickyEnableOnBoxed': true,
      'stickyEnableOnMobile': false,
      'stickyChangeLogo': true,
      'stickyStartAt': 120,
      'stickyHeaderContainerHeight': 70
    }"
    style="height: 110px"
  >
    <div class="header-body border-top-0" style="position: fixed">
      <div class="header-container container" style="height: 100px">
        <div class="header-row">
          <!-- 로고 및 사용자 유형 선택 -->
          <div class="header-column">
            <div class="header-row">
              <div class="header-logo" style="width: 100px; height: 48px">
                <router-link to="/">
                  <img
                    alt="Porto"
                    width="100"
                    height="48"
                    data-sticky-width="82"
                    data-sticky-height="40"
                    src="/img/jobs-icon.png"
                  />
                </router-link>
              </div>
            </div>
          </div>

          <!-- 일반회원 UI -->
          <div
            class="header-column justify-content-end"
            v-show="userType === 'user' || userType === ''"
          >
            <div class="header-row">
              <div
                class="header-nav header-nav-line header-nav-bottom-line header-nav-bottom-line-no-transform header-nav-bottom-line-active-text-dark header-nav-bottom-line-effect-1 order-2 order-lg-1"
              >
                <div
                  ref="userNavElement"
                  class="header-nav-main header-nav-main-square header-nav-main-dropdown-no-borders header-nav-main-effect-2 header-nav-main-sub-effect-1 collapse"
                >
                  <nav>
                    <ul class="nav nav-pills" id="mainNav">
                      <li class="dropdown">
                        <router-link
                          to="/"
                          class="dropdown-item dropdown-toggle font-weight-bold"
                        >
                          HOME
                          <i class="fas fa-chevron-down"></i>
                        </router-link>
                      </li>
                      <li class="dropdown">
                        <router-link
                          to="/board/list/jobPosting"
                          class="dropdown-item dropdown-toggle font-weight-bold"
                        >
                          채용정보
                          <i class="fas fa-chevron-down"></i>
                        </router-link>
                      </li>
                      <li class="dropdown">
                        <a
                          class="dropdown-item dropdown-toggle font-weight-bold"
                          href="#"
                          @click.prevent="toggleDropdown"
                        >
                          게시판
                          <i class="fas fa-chevron-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                          <li>
                            <router-link class="dropdown-item" to="/board/general">
                              일반게시판
                            </router-link>
                          </li>
                          <li>
                            <router-link
                              class="dropdown-item"
                              to="/board/feedback"
                            >
                              피드백게시판
                            </router-link>
                          </li>
                          <li>
                            <router-link class="dropdown-item" to="/board/qna">
                              Q&A게시판
                            </router-link>
                          </li>
                        </ul>
                      </li>

                      <li class="dropdown">
                        <router-link
                          to="/mypage/home"
                          class="dropdown-item dropdown-toggle font-weight-bold"
                          v-if="isLoggedIn"
                        >
                          마이페이지
                          <i class="fas fa-chevron-down"></i>
                        </router-link>
                      </li>
                    </ul>
                  </nav>
                </div>
                <button
                  class="btn header-btn-collapse-nav"
                  @click="toggleNav('user')"
                >
                  <i class="fas fa-bars"></i>
                </button>
              </div>

              <!-- 로그인 상태에 따른 UI -->
              <div
                class="header-nav-features header-nav-features-no-border header-nav-features-lg-show-border order-1 order-lg-2"
              >
                <template v-if="isLoggedIn">
                  <div
                    class="header-nav-feature header-nav-features-cart d-inline-flex ms-2"
                  >
                    <span
                      class="header-nav-features-toggle text-decoration-none me-3"
                      data-focus="headerSearch"
                      aria-label="Search"
                    >
                      {{ member.mbrName }}님
                    </span>
                    <a
                      href="#"
                      class="header-nav-features-toggle"
                      @click="logout"
                    >
                      로그아웃
                    </a>
                  </div>
                </template>
                <template v-else>
                  <div
                    class="header-nav-feature header-nav-features-search d-inline-flex"
                  >
                    <router-link class="nav-link" to="/user/login">
                      LOGIN
                    </router-link>
                  </div>
                </template>
              </div>
            </div>
          </div>

          <!-- 기업회원 UI -->
          <div
            class="header-column justify-content-end"
            v-show="userType === 'enter'"
          >
            <div class="header-row">
              <div
                class="header-nav header-nav-line header-nav-bottom-line header-nav-bottom-line-no-transform header-nav-bottom-line-active-text-dark header-nav-bottom-line-effect-1 order-2 order-lg-1"
              >
                <div
                  ref="enterNavElement"
                  class="header-nav-main header-nav-main-square header-nav-main-dropdown-no-borders header-nav-main-effect-2 header-nav-main-sub-effect-1 header-nav-main2 collapse"
                >
                  <nav>
                    <ul class="nav nav-pills" id="mainNav">
                      <li class="dropdown">
                        <router-link
                          to="/enter"
                          class="dropdown-item dropdown-toggle font-weight-bold"
                        >
                          HOME
                          <i class="fas fa-chevron-down"></i>
                        </router-link>
                      </li>
                      <li class="dropdown">
                        <router-link
                          to="/board/list/jobPosting"
                          class="dropdown-item dropdown-toggle font-weight-bold"
                        >
                          공고관리
                          <i class="fas fa-chevron-down"></i>
                        </router-link>
                      </li>
                      <li class="dropdown">
                        <router-link
                          to="/enter/recommendResumeList"
                          class="dropdown-item dropdown-toggle font-weight-bold"
                        >
                          인재리스트
                          <i class="fas fa-chevron-down"></i>
                        </router-link>
                      </li>
                      <li class="dropdown">
                        <a
                          class="dropdown-item dropdown-toggle font-weight-bold"
                          href="#"
                          @click.prevent="toggleDropdown"
                        >
                          게시판
                          <i class="fas fa-chevron-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                          <li>
                            <router-link class="dropdown-item" to="/board">
                              일반게시판
                            </router-link>
                          </li>
                          <li>
                            <router-link
                              class="dropdown-item"
                              to="/board/feedback"
                            >
                              피드백게시판
                            </router-link>
                          </li>
                          <li>
                            <router-link class="dropdown-item" to="/board/qna">
                              Q&A게시판
                            </router-link>
                          </li>
                        </ul>
                      </li>
                      <li class="dropdown">
                        <router-link
                          to="/enter/mypage"
                          class="dropdown-item dropdown-toggle font-weight-bold"
                          v-if="isLoggedIn"
                        >
                          마이페이지
                          <i class="fas fa-chevron-down"></i>
                        </router-link>
                      </li>
                    </ul>
                  </nav>
                </div>
                <button
                  class="btn header-btn-collapse-nav"
                  @click="toggleNav('enter')"
                >
                  <i class="fas fa-bars"></i>
                </button>
              </div>

              <!-- 로그인 상태에 따른 UI -->
              <div
                class="header-nav-features header-nav-features-no-border header-nav-features-lg-show-border order-1 order-lg-2"
              >
                <template v-if="isLoggedIn">
                  <div
                    class="header-nav-feature header-nav-features-cart d-inline-flex ms-2"
                  >
                    <span
                      class="header-nav-features-toggle text-decoration-none me-3"
                      data-focus="headerSearch"
                      aria-label="Search"
                    >
                      {{ member.entrprsId }}님
                    </span>
                    <a
                      href="#"
                      class="header-nav-features-toggle"
                      @click="logout"
                    >
                      로그아웃
                    </a>
                  </div>
                </template>
                <template v-else>
                  <div
                    class="header-nav-feature header-nav-features-search d-inline-flex"
                  >
                    <router-link class="nav-link" to="/enter/login">
                      LOGIN
                    </router-link>
                  </div>
                </template>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { api } from "../../../axios";
import { showAlert } from "../../../utill/utillModal";

const isUserNavVisible = ref(false); // 유저 네비게이션 가시성 상태
const isEnterNavVisible = ref(false); // 기업 네비게이션 가시성 상태

const store = useStore();
const router = useRouter();

const userType = computed(() => store.getters.getUserType);
const member = computed(() => store.getters.getMember);
const isLoggedIn = computed(() => member.value !== null);

function toggleNav(type) {
  if (type === "user") {
    isUserNavVisible.value = !isUserNavVisible.value;
    const userNavElement = document.querySelector(".header-nav-main");

    if (isUserNavVisible.value) {
      userNavElement.classList.add("show");
    } else {
      userNavElement.classList.remove("show");
    }
  } else if (type === "enter") {
    isEnterNavVisible.value = !isEnterNavVisible.value;
    const enterNavElement = document.querySelector(".header-nav-main2");

    if (isEnterNavVisible.value) {
      enterNavElement.classList.add("show");
    } else {
      enterNavElement.classList.remove("show");
    }
  }
}
function toggleDropdown(event) {
  console.log("Dropdown toggle triggered"); // 이 로그가 출력되는지 확인
  event.preventDefault(); // 기본 동작 방지 (필요한 경우)
  const dropdownMenu = event.target
    .closest("li")
    .querySelector("ul.dropdown-menu");
  if (dropdownMenu) {
    console.log("Dropdown menu found:", dropdownMenu); // dropdownMenu가 null이 아닌지 확인
    if (dropdownMenu.classList.contains("show")) {
      dropdownMenu.classList.remove("show");
    } else {
      closeAllDropdowns(); // 다른 열린 드롭다운 메뉴 닫기
      dropdownMenu.classList.add("show");
    }
  } else {
    console.log("Dropdown menu not found");
  }
}

function closeAllDropdowns() {
  const openDropdowns = document.querySelectorAll("ul.dropdown-menu.show");
  openDropdowns.forEach((dropdown) => {
    dropdown.classList.remove("show");
  });
}

function loginUser() {
  if (isLoggedIn.value) {
    console.log("현재 로그인된 사용자:", member.value);
    console.log("현재 로그인된 사용자:", userType.value);
  } else {
    console.log("로그인된 사용자가 없습니다.");
    router.push("/login");
  }
}

function logout() {
  store.commit("clearMember");
  store.commit("changeUserType", "user");

  api.$post("/member/logout").then(() => {
    router.push("/login");
    showAlert("로그아웃 되었습니다.");
  });
}
router.afterEach(() => {
  closeNav(); // 라우터 이동 후 메뉴 닫기
  closeAllDropdowns();
});
onMounted(() => {
  loginUser();
});
function closeNav() {
  const userNavElement = document.querySelector(".header-nav-main");
  const enterNavElement = document.querySelector(".header-nav-main2");

  if (isUserNavVisible.value) {
    userNavElement.classList.remove("show");
    isUserNavVisible.value = false;
  }

  if (isEnterNavVisible.value) {
    enterNavElement.classList.remove("show");
    isEnterNavVisible.value = false;
  }
}
</script>

<style scoped>
.dropdown-menu {
  top: 70px !important;
}

ul.dropdown-menu {
  display: none; /* 기본적으로 숨겨져 있음 */
  visibility: hidden;
  opacity: 0;
  transition: visibility 0.3s, opacity 0.3s ease-in-out;
}

ul.dropdown-menu.show {
  display: block !important;
  visibility: visible !important;
  opacity: 1 !important;
}

ul.dropdown-menu {
  z-index: 1050; /* 다른 요소 위에 배치되도록 충분히 높은 값으로 설정 */
}

.header-nav-main,
.header-nav-main2 {
  position: relative; /* 드롭다운의 기준점을 설정 */
}
</style>
