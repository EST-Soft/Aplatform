<template>
  <div>
    <section class="page-header page-header-modern bg-color-grey page-header-md">
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="text-dark font-weight-bold text-8">채용 정보 목록</h1>
          </div>
          <div class="col-md-12 align-self-center order-1">
            <ul class="breadcrumb d-block text-center">
              <li><a href="/">Home</a></li>
              <li class="active">채용 정보 목록</li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <section class="card card-admin">
      <div class="container py-4">
        

        <!-- 검색 옵션 UI -->

        <!-- 게시글 목록 -->
        <div class="list-container">
          <div class="list-header">
            <div class="search-options">
              <div class="dropdown-wrapper">
                <button @click="toggleDropdown('region')">지역</button>
                <div v-if="showDropdown.region" class="dropdown">
                  <!-- 검색창 -->
                  <input type="text" v-model="regionSearch" placeholder="지역 검색">
                  <!-- 지역 목록 -->
                  <label v-for="area in filteredAreas" :key="area.id">
                    <input type="checkbox" v-model="selectedLocations" :value="area.areaName"> {{ area.areaName }}
                  </label>
                </div>
              </div>

              <div class="dropdown-wrapper">
                <button @click="toggleDropdown('career')">경력</button>
                <div v-if="showDropdown.career" class="dropdown">
                  <label><input type="checkbox" v-model="selectedCareers" value="enr"> 경력무관</label>
                  <label><input type="checkbox" v-model="selectedCareers" value="nwcmr"> 신입</label>
                  <label><input type="checkbox" v-model="selectedCareers" value="exprncd"> 경력</label>
                </div>
              </div>

              <div class="dropdown-wrapper">
                <button @click="toggleDropdown('education')">학력</button>
                <div v-if="showDropdown.education" class="dropdown">
                  <label><input type="checkbox" v-model="selectedEducations" value="ednm"> 학력무관</label>
                  <label><input type="checkbox" v-model="selectedEducations" value="hs"> 고등학교 이상</label>
                  <label><input type="checkbox" v-model="selectedEducations" value="jc"> 대학교(2,3년제) 이상</label>
                  <label><input type="checkbox" v-model="selectedEducations" value="unvrsty"> 대학교(4년제) 이상</label>
                  <label><input type="checkbox" v-model="selectedEducations" value="mid"> 석사</label>
                  <label><input type="checkbox" v-model="selectedEducations" value="did"> 박사</label>
                </div>
              </div>

              <div class="dropdown-wrapper">
                <button @click="toggleDropdown('jobType')">직종</button>
                <div v-if="showDropdown.jobType" class="dropdown">
                  <!-- 검색창 -->
                  <input type="text" v-model="jobTypeSearch" placeholder="직종 검색">
                  <!-- 직종 목록 -->
                  <label v-for="job in filteredJobTypes" :key="job.id">
                    <input type="checkbox" v-model="selectedJobTypes" :value="job.jobScName"> {{ job.jobScName }}
                  </label>
                </div>
              </div>
            </div>
            <SearchComponent @searchResult="handleSearchResult" />
            <div>
              <select v-model="sortOption" @change="fetchSortedItems">
                <option value="regstrStrtDtm">최신순</option>
                <option value="apply">지원자순</option>
                <option value="hits">조회수순</option>
              </select>
              <button @click="clearAllFilter" class="btn btn-primary">초기화</button>
            </div>
            <div v-show="isEnter">
              <router-link to="/board/jobPostingInsert" class="btn btn-primary">공고 등록</router-link>
            </div>
          </div>
          <div class="list-body">
          <div v-if="paginatedItems.length > 0">
            <div class="row">
              <div v-for="(item, idx) in paginatedItems" :key="idx" class="custom col-md-3">
              <router-link :to="`/board/detail/jobPosting/${item.jbpSq}`" class="routerLink" 
                 @mouseover="showDetails(idx)" @mouseleave="hideDetails(idx)">
              <!-- 프로필 이미지와 D-Day -->
             <div class="profile-image-container" v-if="hoveredItem !== idx">
              <div class="profile-image">
              <!-- logoFileUrl이 없으면 대체 이미지 표시 -->
              <img v-if="item.enterpriseMember.logoFileUrl" :src="item.enterpriseMember.logoFileUrl" alt="Company Logo" />
              <img v-else src="@/assets/alterimage.png" alt="Default Logo" /> <!-- 대체 이미지 -->
            </div>
              <!-- D-Day 라벨 -->
            <div v-if="item.regstrDlnDtm" class="d-day">
              D-{{ calculateDDay(item.regstrDlnDtm) }}
            </div>
            </div>
      
              <!-- 조회수 및 스크랩 아이콘 - hover 시에만 보임 -->
            <div v-if="hoveredItem === idx" class="details-overlay">
             <div>
              <label class="form-label" :style="{ fontSize: hoveredItem === idx ? '18px' : '12px' }">
            <img src="@/assets/eye-removebg-preview.png" alt="eye image" class="small-image"> {{ item.hits }}
          </label>
             </div>
            </div>
      
              <!-- 타이틀과 기타 정보 - hover 시 숨김 -->
            <div v-if="hoveredItem !== idx" style="font-size: 20px;">{{ item.jbpTtl }}</div>
            <div v-if="hoveredItem !== idx" style="font-size: 16px">{{ item.enterpriseMember.entrprsName }}</div>
            <div v-if="hoveredItem !== idx" style="font-size: 14px">
            {{ formatWorkArea(item.workArea) }}&nbsp;/&nbsp;{{ getCareerText(item.crrDrtn) }}&nbsp;/&nbsp;{{ getEducationText(item.edctn) }}&nbsp;/&nbsp;{{ formatJobName(item.jobName) }}
             </div>
            </router-link>
          </div>
        </div>
      </div>
            <!-- 검색 결과가 없을 때 표시될 메시지 -->
            <div v-else class="text-center py-5">
              <p class="text-muted">결과가 없습니다.</p>
            </div>
          </div>
         </div>
         <div class="pagenation-wrapper">
          <BasePagination :currentPage="state.currentPage" :totalPages="totalPages" @goToPage="goToPage" />
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, reactive, computed, watch, onMounted } from 'vue';
import { api } from '@/axios.js';
import BasePagination from "@/components/common/BasePagination.vue";
import SearchComponent from '@/components/fo/enterprise/SearchComponent.vue';
import moment from 'moment';
import{ useStore } from "vuex";

const store = useStore();


const state = reactive({
  items: [],
  currentPage: 1,
  itemsPerPage: 12
});

const sortOption = ref('regstrStrtDtm');



const isEnter = computed(() => {
  return store.getters.getMember?.entrprsId != null;
});



// 초기 데이터 로드
const fetchItems = async () => {
  try {
    const response = await api.$get('/board/list/jobPosting', {
      params: { sortBy: sortOption.value }
    });
    state.items = response;
    console.log("asdasdas", response);
  } catch (error) {
    console.error('Error fetching items:', error);
  }
};

const formatJobName = (jobName) => {
  if (typeof jobName === 'string') {
    // 문자열에서 대괄호와 따옴표 제거
    let cleaned = jobName.replace(/^\[|\]$/g, '').replace(/"/g, '');
    // 쉼표로 분리된 항목들을 배열로 만들고 다시 조인
    return cleaned.split(',').map(item => item.trim()).join(', ');
  } else if (Array.isArray(jobName)) {
    // 이미 배열인 경우 직접 조인
    return jobName.join(', ');
  }
  // 그 외의 경우 원래 값 반환
  return jobName;
};

const formatWorkArea = (workArea) => {
  if (typeof workArea === 'string') {
    // 문자열에서 대괄호와 따옴표 제거
    let cleaned = workArea.replace(/^\[|\]$/g, '').replace(/"/g, '');
    // 쉼표로 분리된 항목들을 배열로 만들고 다시 조인
    return cleaned.split(',').map(item => item.trim()).join(', ');
  } else if (Array.isArray(workArea)) {
    // 이미 배열인 경우 직접 조인
    return workArea.join(', ');
  }
  // 그 외의 경우 원래 값 반환
  return workArea;
};

// 컴포넌트가 마운트된 후 데이터 로드
onMounted(() => {
  fetchItems();
});

const fetchSortedItems = () => {
  state.currentPage = 1; // 페이지를 첫 페이지로 초기화
  fetchItems();
};

// 페이지네이션 계산
const paginatedItems = computed(() => {
  const startIndex = (state.currentPage - 1) * state.itemsPerPage;
  const endIndex = startIndex + state.itemsPerPage;
  return filteredItems.value.slice(startIndex, endIndex);
});

// 전체 페이지 수 계산
const totalPages = computed(() => Math.ceil(filteredItems.value.length / state.itemsPerPage));

// 지역, 직업목록
const areas = ref([]);
const jobs = ref([]);

const fetchAreas = async () => {
  try {
    const response = await api.$get(`/areas`);
    areas.value = response;
  } catch (error) {
    console.error('Error fetching areas:', error);
  }
};

const fetchJobs = async () => {
  try {
    const response = await api.$get(`/jobs`);
    jobs.value = response;
  } catch (error) {
    console.error('Error fetching jobs:', error);
  }
};

onMounted(() => {
  fetchAreas();
  fetchJobs();
});

// 선택된 옵션들
const selectedLocations = ref([]);
const selectedCareers = ref([]);
const selectedEducations = ref([]);
const selectedJobTypes = ref([]);

// 선택값 초기화 

const clearAll= ()=>{
  selectedCareers.value = [];
  selectedEducations.value = [];
  selectedJobTypes.value = [];
  selectedLocations.value = [];
}

// 선택된 값, 검색 값 초기화
const clearAllFilter =()=>{
  clearAll();
  fetchItems();
}

// 드롭다운 메뉴 표시 상태
const showDropdown = reactive({
  region: false,
  career: false,
  education: false,
  jobType: false
});

// 필터된 아이템들 계산
const filteredItems = computed(() => {
  if (!state.items) return [];
  const today = moment().format('YYYY-MM-DD');
  return state.items.filter(item => {
    let isValid = true;

    if (selectedLocations.value.length > 0 && !selectedLocations.value.some(location => item.workArea.includes(location))) {
      isValid = false;
    }
    if (selectedCareers.value.length > 0 && !selectedCareers.value.includes(item.cr)) {
      isValid = false;
    }
    if (selectedEducations.value.length > 0 && !selectedEducations.value.includes(item.edctn)) {
      isValid = false;
    }
    if (selectedJobTypes.value.length > 0 && !selectedJobTypes.value.some(jobType => item.jobName.includes(jobType))) {
      isValid = false;
    }

    // 공고 시작일이 오늘 날짜보다 작은지 확인
    if (moment(item.regstrStrtDtm).isAfter(today)) {
      isValid = false;
    }

    const dDay = calculateDDay(item.regstrDlnDtm);
    if(dDay === 0) {
      isValid = false;
    }

    return isValid;
  });
});

// 페이지 변경 함수
function goToPage(page) {
  if (page >= 1 && page <= totalPages.value) {
    state.currentPage = page;
  }
}

// Watchers
watch(selectedLocations, () => {
  state.currentPage = 1;
});
watch(selectedCareers, () => {
  state.currentPage = 1;
});
watch(selectedEducations, () => {
  state.currentPage = 1;
});
watch(selectedJobTypes, () => {
  state.currentPage = 1;
});

watch(sortOption, fetchSortedItems);

// 검색 결과 처리 함수
const handleSearchResult = (results) => {
  // 검색된 결과에 대해 필터링을 적용
  const filtered = results.filter(item => {
    return item;
  });  
  console.log("검색 결과:", results);
  state.items = filtered;
  state.currentPage = 1;
  
};


// 드롭다운 토글 함수
function toggleDropdown(type) {
  // console.log("asdas : " , enterprise);
  // console.log("aaaaaa : " , store.getters.getMember.entrprsId);
  for (const key in showDropdown) {
    if (key === type) {
      showDropdown[key] = !showDropdown[key];
    } else {
      showDropdown[key] = false;
    }
  }
}

const regionSearch = ref('');
const jobTypeSearch = ref('');

// 필터링된 지역 목록 계산
const filteredAreas = computed(() => {
  return areas.value.filter(area => {
    return area.areaName.toLowerCase().includes(regionSearch.value.toLowerCase());
  });
});

// 필터링된 직종 목록 계산
const filteredJobTypes = computed(() => {
  return jobs.value.filter(job => {
    return job.jobScName.toLowerCase().includes(jobTypeSearch.value.toLowerCase());
  });
});

// 학력 매핑 테이블
const educationMapping = {
  'ednm': '학력무관',
  'hs': '고등학교 이상',
  'jc': '대학교(2,3년제) 이상',
  'unvrsty': '대학교(4년제) 이상',
  'mid': '석사',
  'did': '박사'
};

// 경력 매핑 테이블
const careerMapping = {
  'enr': '경력무관',
  'exprncd': '신입',
  'nwcmr': '경력'
};

// 학력 텍스트 반환 함수
const getEducationText = (value) => {
  return educationMapping[value] || value;
};

// 경력 텍스트 반환 함수
const getCareerText = (value) => {
  return careerMapping[value] || value;
};

//d-day계산 함수 
const calculateDDay = (regstrDlnDtm) => {
  const today = moment().startOf('day'); // 오늘 날짜 (시간 제외)
  const due = moment(regstrDlnDtm).startOf('day'); // 마감일 (시간 제외)
  const daysLeft = due.diff(today, 'days'); // 날짜 차이 계산
  
  return daysLeft >= 0 ? daysLeft : 0; // 남은 일수를 반환, 만약 마감일이 지나면 0을 반환
};


// 마우스 오버 시 표시될 항목 인덱스 추적
const hoveredItem = ref(null);

// 마우스를 올렸을 때 조회수와 스크랩을 표시
const showDetails = (idx) => {
  hoveredItem.value = idx;
};

// 마우스를 내렸을 때 표시된 정보 숨기기
const hideDetails = () => {
  hoveredItem.value = null;
};

</script>

<style scoped>
.list-container {
  margin-bottom: 60px; /* 페이지네이션 영역과의 여백을 추가 */
  position: relative; /* 부모 요소에 상대적인 위치 설정 */
}

.list-header {
  display: flex;
}

.list-header > div {
  margin-left: 50px;
}

.list-body {
  margin-top: 50px;
}

.custom {
  background-color: #ffffff;
  border-radius: 0.5rem;
  padding: 1rem;
  width: 100%;
  border: 1px solid #002C7B;
  text-align: left;
  position: relative;
  height: 350px;
}

.custom .profile-image {
  width: 230px;
  height: 180px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

.custom .profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 영역을 꽉 채우도록 설정 */
}

/* flexbox로 아이템들을 4개씩 나누기 */
.row {
  display: flex;
  flex-wrap: wrap;
  gap: 15px; /* 아이템 사이의 간격 */
}

.col-md-3 {
  flex: 1 1 22%; /* 한 줄에 4개의 아이템 배치 */
  max-width: 22%;
}

/* 화면 크기가 작아지면 한 줄에 하나만 표시되도록 설정 */
/* 모바일 환경에서 mouse event를 클릭으로 바꾸고, overlay 숨기기 */
@media (max-width: 768px) {
  .custom {
    flex-direction: column; /* 세로로 배치 */
    align-items: center; /* 중앙 정렬 */
    text-align: center; /* 텍스트 중앙 정렬 */
    height: 300px;
  }

  .custom .profile-image {
    margin-right: 0;
    margin-bottom: 10px;
  }

  .col-md-3 {
    flex: 1 1 100%; /* 한 줄에 하나만 표시 */
    max-width: 100%;
  }


  /* 클릭 시 overlay 나타나게 하기 */
  .custom.clicked .detail-overlay {
    display: block; /* overlay를 보이도록 설정 */
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%; /* custom 영역의 크기에 맞게 overlay를 덮도록 */
    background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
    z-index: 10; /* overlay가 custom 영역 위로 올라오도록 */
  }

  /* 모바일에서는 hover를 막고, 클릭으로 처리 */
  .custom {
    pointer-events: auto; /* 마우스 오버를 클릭으로 변환 */
  }
}

/* desktop에서는 hover 상태로 overlay 활성화 */
@media (min-width: 769px) {
  .custom:hover .detail-overlay {
    display: block;
  }
}

.search-options {
  margin-top: 20px;
  display: flex;
  align-items: center;
}

.search-options button {
  padding: 8px 16px;
  font-size: 16px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
}

.search-options button:hover {
  background-color: #2980b9;
}

.dropdown-wrapper {
  position: relative; /* 부모 요소에 대해 상대 위치 */
}

.dropdown {
  position: absolute;
  top: 100%; /* 버튼 바로 아래에 위치 */
  left: 0;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  z-index: 1;
  margin-top: 5px;
  max-height: 200px; /* 최대 높이 설정 */
  overflow-y: auto;
}

.dropdown label {
  display: block;
  padding: 10px;
}

.dropdown label:hover {
  background-color: #f1f1f1;
}

.pagenation-wrapper {
  position: absolute;
  width: 100%;
  z-index: 1000;
  bottom: -50px; /* 페이지네이션 위치 조정 */
}

.container {
  position: relative;
  margin-bottom: 50px;
}

.router-link {
  display: inline-block;
  padding: 10px 20px;
  font-size: 16px;
  text-decoration: none;
  box-sizing: border-box;
  transition: all 0.3s ease;
}

.router-link:hover {
  background-color: #ddd;
}

.details-overlay {
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
}

.profile-image-container {
  position: relative;
  display: inline-block;
  transition: 0.3s ease;
}

.profile-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 10px;
}

.profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: opacity 0.3s ease;
}

.profile-image-container:hover .profile-image img {
  opacity: 0.6; /* 마우스 오버 시 어두워짐 */
}

.d-day {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 5px;
  border-radius: 5px;
}

.small-image, .small-heart-image {
  width: 48px;
  height: 48px;
}
</style>
