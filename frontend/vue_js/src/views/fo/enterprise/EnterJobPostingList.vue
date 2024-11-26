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
  <div class="list-header">
    <div class="search-options"></div>

    <!-- 공고 등록 버튼 -->
    <div v-show="isEnter">
      <router-link to="/board/jobPostingInsert" class="btn btn-primary">공고 등록</router-link>
    </div>

    <!-- 진행 상태 필터 -->
    <div>
      <select v-model="sortOption" class="section" @change="fetchSortedItems" required>
        <option value="" disabled selected>진행상태</option>
        <option value="예정">예정</option>
        <option value="진행중">진행중</option>
        <option value="채용종료">채용종료</option>
      </select>
      <button @click="clearAllFilter" class="btn btn-primary">초기화</button>
    </div>

    <!-- 검색 컴포넌트 -->
    <SearchComponent @searchResult="handleSearchResult" />
  </div>

  <div class="list-body">
    <!-- 공고 리스트 -->
    <div v-if="paginatedItems.length > 0">
      <div v-for="(item, idx) in paginatedItems" :key="idx" class="custom">
        <table class="info-table" @click="goToDetailPage(item)">
          <tr>
            <td style="font-size:16px;">{{ item.enterpriseMember.entrprsName }}</td>
            <td style="font-size:16px;">제목:<br>{{ item.jbpTtl }}</td>
            <td colspan="3" class="action-buttons-cell">
              <div>
                <button class="btn btn-success" @click.stop="goUpdatePage(item)">수정</button>
                <button class="btn btn-danger ms-2" @click.stop="confirmDelete(item)">삭제</button>
              </div>
            </td>
            <td style="font-size:18px;">{{ getJobStatusClass(item) }}</td>
            <td>
              <button v-if="getJobStatus(item) === '채용종료'" class="btn btn-warning ms-2" @click="addDeadline(item)">마감일 추가</button>
            </td>
          </tr>
          <tr>
            <td style="font-size:14px;" v-html="formatWorkArea(item.workArea)"></td>
            <td style="font-size:14px;">경력:<br><span style="font-size:18px;">{{ getCareerText(item.crrDrtn) }}</span></td>
            <td style="font-size:14px;">학력:<br><span style="font-size:18px;">{{ getEducationText(item.edctn) }}</span></td>
            <td style="font-size:14px;" v-html="formatJobName(item.jobName)"></td>
            <td style="font-size:14px;">시작일:<br><span style="font-size:16px;">{{ item.regstrStrtDtm }}</span></td>
            <td style="font-size:14px;">마감일:<br><span style="font-size:16px;">{{ item.regstrDlnDtm }}</span></td>
            <td>
              <div>
                <button class="btn btn-apply ms-2" @click.stop="gotoapplyPage(item)">지원자 현황</button>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <!-- 등록된 공고가 없을 때 -->
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
import {  showAlert} from '../../../utill/utillModal';




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



// 원본 데이터를 저장하는 변수 추가
const originalItems = ref([]);

// 초기 데이터 로드 함수에서 원본 데이터를 저장
const fetchItems = async () => {
  try {
    const test = store.getters.getMember;

    const response = await api.$get('/board/list/jobPosting', {
      params: { 
        sortBy: sortOption.value,
        entrprsId: test.entrprsId
      }
    });

    if (response) {
      const filteredItems = response.filter(item => item.enterpriseMember?.entrprsId === test.entrprsId);
      originalItems.value = filteredItems; // 원본 데이터 저장
      state.items = filteredItems;
    } else {
      state.items = [];
      originalItems.value = [];
    }
  } catch (error) {
    console.error('Error fetching items:', error);
    state.items = [];
    originalItems.value = [];
  }
};

// 진행 상태 필터링 함수 수정
const fetchSortedItems = () => {
  state.items = [...originalItems.value]; // 항상 원본 데이터로 초기화
  state.currentPage = 1; // 페이지를 첫 페이지로 초기화

  // 선택된 값에 따라 공고 상태 필터링
  if (sortOption.value === '예정') {
    state.items = state.items.filter(item => getJobStatus(item) === '예정');
  } else if (sortOption.value === '진행중') {
    state.items = state.items.filter(item => getJobStatus(item) === '진행중');
  } else if (sortOption.value === '채용종료') {
    state.items = state.items.filter(item => getJobStatus(item) === '채용종료');
  }
};

// 초기화 버튼 로직 수정
const clearAllFilter = () => {
  state.items = [...originalItems.value]; // 원본 데이터를 복원
  sortOption.value = ''; // 선택된 필터를 초기화
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
    return cleaned.split(',').map(item => item.trim()).join('<br>');
  } else if (Array.isArray(jobName)) {
    // 이미 배열인 경우 직접 조인
    return jobName.join('<br>');
  }
  // 그 외의 경우 원래 값 반환
  return jobName;
};

const formatWorkArea = (workArea) => {
  if (typeof workArea === 'string') {
    // 문자열에서 대괄호와 따옴표 제거
    let cleaned = workArea.replace(/^\[|\]$/g, '').replace(/"/g, '');
    // 쉼표로 분리된 항목들을 배열로 만들고 다시 조인
    return cleaned.split(',').map(item => item.trim()).join('<br>');
  } else if (Array.isArray(workArea)) {
    // 이미 배열인 경우 직접 조인
    return workArea.join('<br>');
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


// 필터된 아이템들 계산
const filteredItems = computed(() => {
  if (!state.items) return [];
    return state.items;
  });


// 페이지 변경 함수
function goToPage(page) {
  if (page >= 1 && page <= totalPages.value) {
    state.currentPage = page;
  }
}


watch(sortOption, fetchSortedItems);


// 검색 결과 처리 함수
const handleSearchResult = (results) => {
  // 검색된 결과에 대해 필터링을 적용
  const filtered = results.filter(item => {
    return item.enterpriseMember && item.enterpriseMember.entrprsId === store.getters.getMember?.entrprsId;
  });  
  console.log("검색 결과:", results);
  state.items = filtered;
  state.currentPage = 1;
  
};


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
    showAlert('게시글이 삭제되었습니다.');
  }
};

const deleteBoard = async (item) => {
  try {
    await api.$delete(`/board/jobPostingDelete/${item.jbpSq}`);
    fetchItems();
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

const goToDetailPage = (item) => {
  router.push(`/board/detail/jobPosting/${item.jbpSq}`);
}

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

.sortOption {
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
  width: 10%;
}

tr:nth-child(2) td:nth-of-type(3) {
  width: 15%;
}

/* tr2에서 4번째 셀 (셀 4에만 적용) */
tr:nth-child(2) td:nth-of-type(4) {
  width: 10%;
}

/* tr2에서 5번째 셀 (셀 5에만 적용) */
tr:nth-child(2) td:nth-of-type(5) {
  width: 10%;
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
@media (max-width: 570px) {

  .list-header {
    flex-direction: column; 
    align-items: flex-start; 
    gap: 10px; 
  }

  .list-header > div {
    width: 100%; /* 모바일에서 각 div 요소가 100% 너비를 차지하도록 설정 */
    margin-bottom: 10px; /* 버튼 간격 */
  }

  .btn-primary{
    width: 100%;
  }

  .section{
    width: 100%;
  }

  .section :optional{
    width: 100%;
  }

  td {
    padding: 10px;
    font-size: 14px;
    white-space: normal;  /* 기본적으로 텍스트가 가로로 표시되도록 */
    word-wrap: break-word; /* 텍스트가 넘칠 경우 줄바꿈 처리 */
    word-break: break-word; /* 넘치는 텍스트를 줄바꿈으로 처리 */
    overflow-wrap: break-word; /* 넘칠 경우 자동으로 줄바꿈 */
  }

  td:first-child,
  td:nth-child(3),
  td:nth-child(4),
  td:last-child {
    width: auto;
  }
 td:first-child{
  width: 10%;
 }

  td:nth-child(2){
    width: 10%;
  }

  td:nth-child(4){
    width: 20%;
  }
  td:last-child{
    width: 20%;
  }

  .button-group {
    margin-top: 10px;
    justify-content: center;
  } 
}
</style>


