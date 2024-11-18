<template>
  <div>
    <section class="page-header page-header-modern bg-color-grey page-header-md">
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="text-dark font-weight-bold text-8">공고 등록 현황</h1>
          </div>
          <div class="col-md-12 align-self-center order-1">
            <ul class="breadcrumb d-block text-center">
              <li><a href="/">Home</a></li>
              <li class="active">공고 등록 현황</li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <section class="card card-admin">
      <div class="container py-4">
        
        <div class="list-container">
          <div class="list-header" v-if="paginatedItems.length > 0">
            <div class="search-options">
            </div>
            <div v-show="isEnter">
              <router-link to="/board/jobPostingInsert" class="btn btn-primary">공고 등록</router-link>
            </div>
            <div>
              <select v-model="sortOption" @change="fetchSortedItems">
               <option value="예정">예정</option>
               <option value="진행중">진행중</option>
               <option value="채용종료">채용종료</option>
              </select>
              <button @click="clearAllFilter" class="btn btn-primary">초기화</button>
            </div>
            <SearchComponent @searchResult="handleSearchResult" />
          </div>
          <div class="list-body">
  <div v-if="paginatedItems.length > 0">
    <div v-for="(item, idx) in paginatedItems" :key="idx" class="custom">
      <table class="info-table">
        <tr>
          <td style="font-size:16px;">{{ item.enterpriseMember.entrprsName }}</td>
          <td style="font-size:16px;">{{ item.jbpTtl }}</td>
          <td colspan="3" class="action-buttons-cell">
            <!-- 수정 삭제 버튼  -->
            <div>
              <button class="btn btn-success" @click="goUpdatePage(item)">수정</button>
              <button class="btn btn-danger ms-2" @click="confirmDelete(item)">삭제</button>
            </div>
          </td>
          <td style="font-size:18px;">{{ getJobStatusClass(item) }}</td>
          <td>
            <button v-if="getJobStatus(item) === '채용종료'" class="btn btn-warning ms-2" @click="addDeadline(item)">마감일 추가</button>
          </td>
        </tr>
        <tr>
          <td style="font-size:16px;">{{ formatWorkArea(item.workArea) }}</td>
          <td style="font-size:16px;">{{ getCareerText(item.crrDrtn) }}</td>
          <td style="font-size:16px;">{{ getEducationText(item.edctn) }}</td>
          <td style="font-size:16px;">{{ formatJobName(item.jobName) }}</td>
          <td style="font-size:16px;">{{ item.regstrStrtDtm }}</td>
          <td style="font-size:16px;">{{ item.regstrDlnDtm }}</td>
          <td>
            <div>
              <button class="btn btn-apply ms-2" @click="gotoapplyPage(item)">지원자 현황</button>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>
  <!-- 검색 결과가 없을 때 표시될 메시지 -->
  <div v-else class="no-items text-center py-5">
              <p class="text-muted">등록된 공고가 없습니다.</p>
              <router-link to="/board/jobPostingInsert" class="btn btn-primary mt-3">공고 등록하기</router-link>
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
import { useRouter } from 'vue-router';




const store = useStore();
// const enterprise = computed(() => store.getters.enterMember);

const state = reactive({
  items: [],
  currentPage: 1,
  itemsPerPage: 10
});

const sortOption = ref('regstrStrtDtm');


const isEnter = computed(() => {
  return store.getters.getMember?.entrprsId != null;
});

// 초기 데이터 로드
const fetchItems = async () => {
  try {
    // 로그인된 회사의 정보를 가져옵니다.
    const test = store.getters.getMember;
    console.log("entrprsId:", test.entrprsId);

    // 로그인한 회사의 ID를 쿼리에 추가하여 필터링
    const response = await api.$get('/board/list/jobPosting', {
      params: { 
        sortBy: sortOption.value,
        entrprsId: test.entrprsId  // 기업의 pk로 필터링
      }
    });
    // 가져온 공고 목록에서 enterpriseMember.entrprsId를 기준으로 필터링
    if (response && response) {
     // enterpriseMember.entrprsId 값이 test.entrprsId와 일치하는 항목만 필터링
      const filteredItems = response.filter(item => {
        return item.enterpriseMember && item.enterpriseMember.entrprsId === test.entrprsId;
      });

      // 필터링된 결과가 있다면 상태에 저장
      if (filteredItems.length > 0) {
        state.items = filteredItems;
        console.log("Fetched and filtered items:", filteredItems);
      } else {
        console.log("No data found for entrprsId:", test.entrprsId);
        state.items = []; // 필터링된 결과가 없으면 빈 배열로 설정
      }
    } else {
      console.error('No data found in response');
      state.items = [];
    }

  } catch (error) {
    console.error('Error fetching items:', error);
    state.items = [];
  }
};




const fetchSortedItems = () => {
  state.currentPage = 1; // 페이지를 첫 페이지로 초기화
  
  // 선택된 값에 따라 공고 상태 필터링
  if (sortOption.value === '예정') {
    state.items = state.items.filter(item => getJobStatus(item) === '예정');
  } else if (sortOption.value === '진행중') {
    state.items = state.items.filter(item => getJobStatus(item) === '진행중');
  } else if (sortOption.value === '채용종료') {
    state.items = state.items.filter(item => getJobStatus(item) === '채용종료');
  }

  // 필터링 후 항목들 다시 로드
  console.log('Filtered items:', state.items);
};

// 마감일 추가 함수
const addDeadline = (item) => {
  api.$put(`/board/jobPostingUpdate/deadline/${item.jbpSq}`)
    .then(() => {
      alert('마감일이 30일 연장되었습니다.');
      fetchItems();  // 갱신된 데이터를 다시 불러옵니다.
    })
    .catch((error) => {
      console.error('마감일 갱신 실패:', error.response ? error.response.data : error.message);
    });
};

// 페이지가 로드될 때 fetchItems 한 번만 호출
onMounted(() => {
  fetchItems();
});


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


// 선택된 값, 검색 값 초기화
const clearAllFilter =()=>{
  fetchItems();
}


// 필터된 아이템들 계산
const filteredItems = computed(() => {
  if (!state.items) return [];
  const today = moment().format('YYYY-MM-DD');
  return state.items.filter(item => {
    let isValid = true;
    // 공고 시작일이 오늘 날짜보다 작은지 확인
    if (moment(item.regstrStrtDtm).isAfter(today)) {
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


watch(sortOption, fetchSortedItems);

// 검색 결과 처리 함수
const handleSearchResult = (searchResults) => {
  state.items = searchResults;
  state.currentPage = 1;
}

const isEditable = ref(true);
const router = useRouter();

const goUpdatePage = (item) => {
  isEditable.value = false;
  router.push(`/board/jobPostingUpdate/${item.jbpSq}`);
};

const gotoapplyPage = (item) => {
  router.push(`/applys/apply-list/${item.jbpSq}`);
}

const confirmDelete = (item) => {
  if (window.confirm('게시글을 삭제 하시겠습니까?')) {
    deleteBoard(item);
    alert('게시글이 삭제되었습니다.');
  }
};

const deleteBoard = async (jbp) => {
  try {
    await api.$delete(`/board/jobPostingDelete/${jbp.jbpSq}`);
    console.log('게시글이 삭제 되었습니다');
    router.push('/board/list/jobPosting');
  } catch (error) {
    console.error('게시글 삭제가 실패 되었습니다:', error);
  }
};

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

const getCareerText = (value) => {
  return careerMapping[value] || value;
};

const getEducationText = (value) => {
  return educationMapping[value] || value;
};

// 공고 상태 가져오기 함수
const getJobStatus = (item) => {
  const currentDate = moment();  
  const startDate = moment(item.regstrStrtDtm); 
  const endDate = moment(item.regstrDlnDtm);    

  // 공고 상태 체크
  if (currentDate.isBefore(startDate)) {
    return "예정"; 
  } else if (currentDate.isAfter(endDate)) {
    return "채용종료";  
  } else {
    return "진행중";  
  }
};
// 공고 상태에 맞는 클래스 반환 함수
const getJobStatusClass = (item) => {
  return getJobStatus(item);
};

</script>

<style scoped>
.list-container {
  margin-bottom: 60px;
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 0 20px;
  width: 100%;
}

.list-body {
  margin-top: 30px;
}

.custom {
  background-color: #ffffff;
  border-radius: 0.5rem;
  padding: 1rem;
  margin: 15px 0;
  width: 100%;
  border: 1px solid #002C7B;
}

table {
  width: 100%;
}

td {
  padding: 8px;
  border: 1px solid #ccc; /* 각 셀에 solid border 추가 */
  text-align: center; /* 중앙 정렬 */
}

th {
  padding: 10px;
  border: 1px solid #ccc; /* 헤더 셀도 동일한 스타일 */
  background-color: #f4f4f4; /* 헤더 배경색 */
  text-align: center;
  font-weight: bold;
}

td:first-child {
  width: 10%;
}

td:nth-child(2) {
  width: 20%;
}

td:nth-child(3){
  width: auto;
}

td:nth-child(4){
  width: 10%;
}

td:last-child{
  width: 10%;
}

.custom:hover {
  background-color: #f9f9f9; /* 테이블 항목 hover 시 색상 변경 */
  transition: background-color 0.3s ease;
}

/* 버튼 그룹을 테이블 안쪽으로 배치 */
.button-group {
  display: flex;
  gap: 8px;
}

.btn {
  padding: 8px 16px;
  font-size: 14px;
  cursor: pointer;
}

.btn-apply {
  background-color: #007bff; /* 기본 파란색 버튼 */
  color: #ffffff;
}

.btn-apply:hover {
  background-color: #0056b3; /* 호버 시 더 어두운 파란색 */
}

.action-buttons-cell {
  text-align: right; /* 버튼을 오른쪽으로 정렬 */
  vertical-align: middle; /* 세로 가운데 정렬 */
}

/* Responsive Design */
@media (max-width: 768px) {
  td {
    padding: 10px;
    font-size: 14px;
  }

  td:first-child,
  td:nth-child(2),
  td:nth-child(3),
  td:nth-child(4),
  td:last-child {
    width: auto;
  }
  

  .button-group {
    margin-top: 10px;
    justify-content: center;
  }
  
  
  
}
</style>


