<template>
  <section id="recent-job-views" class="section section-default">
    <div class="container">
      <h2>최근 본 공고</h2>

      <!-- 검색 영역 -->
      <div class="d-flex mb-4">
        <div class="d-flex align-items-center position-relative">
          <select
            v-model="searchCriteria"
            class="form-control mr-2 custom-select"
          >
            <option value="enterpriseName">기업명</option>
            <option value="jbpTtl">공고명</option>
          </select>
          <i class="fas fa-chevron-down dropdown-arrow"></i>
        </div>

        <input
          v-model="searchQuery"
          class="form-control mr-2"
          placeholder="검색어 입력"
          @keyup.enter="searchJobView"
        />
        <button class="btn btn-outline-primary btn-sm" @click="searchJobView">
          검색
        </button>
      </div>

      <!-- 로딩 중 -->
      <div v-if="loading" class="text-center">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <!-- 검색된 공고가 없을 때 -->
      <div v-else-if="filteredJobViews.length === 0" class="text-center">
        <p>검색된 공고가 없습니다.</p>
      </div>

      <!-- 최근 본 공고 리스트 -->
      <div v-else>
        <div class="row">
          <div
            class="col-12 col-md-6 col-lg-4 mb-4"
            v-for="(jobView, index) in paginatedJobViews"
            :key="index"
          >
            <router-link
              :to="'/board/detail/jobPosting/' + jobView.jbpSq"
              class="card shadow-sm border-light rounded text-decoration-none"
            >
              <div class="card-body">
                <h5
                  class="card-title font-weight-bold text-truncate"
                  :title="jobView.jbpTtl"
                >
                  {{ jobView.jbpTtl }}
                </h5>

                <h6 class="card-subtitle mb-2 text-muted">
                  {{ jobView.enterpriseName }}
                </h6>

                <p class="card-text text-muted">
                  {{ formatDate(jobView.viewDate) }}
                </p>
              </div>
            </router-link>
          </div>
        </div>
      </div>

      <!-- 페이지네이션 -->
      <ul class="pagination justify-content-center">
        <!-- 맨 앞 페이지 버튼 -->
        <li class="page-item" :class="{ disabled: currentPage === 0 }">
          <a
            class="page-link"
            href="javascript:void(0);"
            @click="changePage(0)"
          >
            <i class="fas fa-angle-double-left"></i>
          </a>
        </li>

        <!-- 이전 페이지 버튼 -->
        <li class="page-item" :class="{ disabled: currentPage === 0 }">
          <a
            class="page-link"
            href="javascript:void(0);"
            @click="changePage(currentPage - 1)"
          >
            <i class="fas fa-chevron-left"></i>
          </a>
        </li>

        <!-- 페이지 번호들 -->
        <li
          v-for="pageNumber in totalPagesArray"
          :key="pageNumber"
          :class="{ active: currentPage === pageNumber }"
          class="page-item"
        >
          <a
            class="page-link"
            href="javascript:void(0);"
            @click="changePage(pageNumber)"
          >
            {{ pageNumber + 1 }}
          </a>
        </li>

        <!-- 다음 페이지 버튼 -->
        <li
          class="page-item"
          :class="{ disabled: currentPage === totalPages - 1 }"
        >
          <a
            class="page-link"
            href="javascript:void(0);"
            @click="changePage(currentPage + 1)"
          >
            <i class="fas fa-chevron-right"></i>
          </a>
        </li>

        <!-- 맨 뒤 페이지 버튼 -->
        <li
          class="page-item"
          :class="{ disabled: currentPage === totalPages - 1 }"
        >
          <a
            class="page-link"
            href="javascript:void(0);"
            @click="changePage(totalPages - 1)"
          >
            <i class="fas fa-angle-double-right"></i>
          </a>
        </li>
      </ul>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useStore } from "vuex";
import { api } from "@/axios.js"; // axios 인스턴스 임포트

// Vuex 스토어 사용
const store = useStore();

// 상태 변수들
const loading = ref(true);
const currentPage = ref(0);
const pageSize = ref(6); // 페이지 크기 6으로 설정 (한 페이지에 최대 6개)
const jobViews = ref({
  content: [],
  totalPages: 1,
  totalElements: 0,
});
const searchQuery = ref(""); // 검색어
const searchCriteria = ref("enterpriseName"); // 검색 기준 (기업명 또는 공고명)
const filteredJobViews = ref([]); // 필터링된 공고 데이터

// 사용자의 최근 본 공고 데이터 불러오기
const fetchRecentJobViews = async (page = 0) => {
  const mbrSq = store.getters.getMember?.mbrSq; // 로그인한 사용자의 mbrSq 값

  if (!mbrSq) {
    loading.value = false;
    return;
  }

  try {
    loading.value = true; // 로딩 시작

    // 페이지네이션에 필요한 파라미터를 포함하여 API 호출
    const response = await api.$get(`/user/mypage/jobView/${mbrSq}`, {
      params: { page, size: pageSize.value },
    });

    if (Array.isArray(response)) {
      jobViews.value.content = response;
      filteredJobViews.value = response;

      // 페이지네이션 관련 정보 설정
      jobViews.value.totalPages = Math.ceil(response.length / pageSize.value);
      jobViews.value.totalElements = response.length;
      currentPage.value = page; // 현재 페이지 번호
    } else {
      console.error("응답 데이터가 비어 있거나 예상과 다릅니다.");
    }
  } catch (error) {
    console.error(
      "최근 본 공고 데이터를 가져오는 데 오류가 발생했습니다:",
      error
    );
  } finally {
    loading.value = false; // 로딩 끝
  }
};

// 페이지 변경 처리
const changePage = (newPage) => {
  if (newPage < 0 || newPage >= jobViews.value.totalPages) return;
  currentPage.value = newPage;
  fetchRecentJobViews(newPage);
};

// 페이지네이션 계산된 속성
const totalPages = computed(() => jobViews.value.totalPages);

// 페이지 번호 배열
const totalPagesArray = computed(() => {
  return Array.from({ length: totalPages.value }, (_, index) => index);
});

// 검색 버튼 클릭 시 실행
const searchJobView = () => {
  filteredJobViews.value = jobViews.value.content.filter((jobView) => {
    const isSearchMatch =
      !searchQuery.value ||
      jobView[searchCriteria.value]
        ?.toLowerCase()
        .includes(searchQuery.value.toLowerCase());
    return isSearchMatch;
  });
};

// 페이지 로드 시, 최근 본 공고 데이터 불러오기
onMounted(() => {
  fetchRecentJobViews();
});

// formatDate 함수 정의 추가
const formatDate = (date) => {
  if (!date) return "";
  const options = { year: "numeric", month: "long", day: "numeric" };
  return new Date(date).toLocaleDateString("ko-KR", options);
};

// 현재 페이지에 해당하는 항목만 가져오기
const paginatedJobViews = computed(() => {
  const start = currentPage.value * pageSize.value;
  const end = start + pageSize.value;
  return filteredJobViews.value.slice(start, end);
});
</script>

<style scoped>
/* 카드 스타일 */
.card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
}

/* 카드 링크의 기본 스타일 제거 */
.card .card-body {
  color: inherit;
  text-decoration: none;
}

.card-title {
  font-size: 1.1rem;
  line-height: 1.5;
}

.card-title:hover {
  cursor: pointer;
}

.card-body {
  padding: 1.25rem;
}

.card-subtitle {
  font-size: 0.9rem;
}

h2 {
  margin-left: 2%;
  padding-bottom: 5%;
}

/* 로딩 중 */
.spinner-border {
  width: 3rem;
  height: 3rem;
}

.form-control {
  max-width: 150px;
  min-width: 150px;
}
.custom-select {
  margin-right: 5px;
}
/* 검색 버튼 */
.btn-outline-primary {
  max-width: 100px;
  margin-left: 5px;
  padding: 3 6px; /* 상하 0px, 좌우 4px */
}

/* 커스텀 드롭다운 화살표 */
.custom-select {
  padding-right: 30px; /* 화살표 공간 확보 */
}

/* 드롭다운 화살표 아이콘 스타일 */
.dropdown-arrow {
  font-size: 1.2rem;
  color: #6c757d;
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: none; /* 클릭 방지 */
  font-size: 13px;
}

/* 반응형: 작은 화면에서 카드 간격 조정 */
@media (max-width: 576px) {
  .card-body {
    padding: 1rem;
  }

  .card-title {
    font-size: 1rem;
  }
}
</style>
