<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <button class="btn-close" @click="$emit('update:isVisible', false)">×</button>
      <div class="modal-body">
        <div class="row">
          <div class="input-group mb-3">
            <input type="text" v-model="searchTerm" class="form-control" placeholder="검색어를 입력해주세요.">
            <div class="btn btn-primary" @click="$emit('setResult', searchTerm)">직접입력</div>
            <div class="btn btn-primary" @click="doNewSearch">검색</div>
            <div class="btn btn-primary" @click="$emit('update:isVisible', false)">취소</div>
          </div>
        </div>
        <section class="call-to-action with-full-borders mb-2">
          <div class="row col-sm-12 col-lg-12">
            <!-- 자료없을때 예외 -->
            <div v-if="searchResult.length == 0">
              <strong class="font-weight-extra-bold"> 검색결과가 없습니다.</strong>
            </div>
            <!-- 자료있을때 for -->
            <div v-else class="list-wrap">
              <ul class="list-group" v-for="(item, index) in searchResult" :key="index">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                  {{ item.schoolName + " (" + item.campusName + ")" }}
                  <button type="button" class="btn btn-primary" @click="selectSchool(item)">선택</button>
                </li>
              </ul>
            </div>
          </div>
          <div class="row" id="pagination-container">
            <div class="pagination-wrapper" v-if="paginationData.totalPageCount > 1">
              <!-- <BasePagination :currentPage="currentPage" :goToPage="goToPage" :totalPages="totalPages"/> -->
              <PaginationData :paginationData="paginationData" @change-page-no="changePageNo" style="margin: 0 auto;" />
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>





</template>

<script setup>
import { defineProps, reactive, ref, onMounted, watch } from 'vue';
import { api } from '@/axios.js';
import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
/* eslint-disable */

// emits 선언
const emit = defineEmits(['update:isVisible', 'setResult']);

//속성선언  
const props = defineProps({
  isVisible: Boolean
});
//검색 타입 선언 (ex> 학력 : SCHOOL , 직업 : JOB_VIEW)
const searchType = 'SCHOOL';
//검색어 변수 선언
const searchTerm = ref('');
//검색결과 변수 선언
const searchResult = ref([]);

//페이징 관련 변수 선언
// const currentPage = ref('1');
// const totalPages = ref('');

const paginationData = reactive({
  pageNo: 1, // 현재 페이지 번호
  totalPageCount: 0, // 총 페이지 수
  startNumOfPageGroups: 1, // 현재 페이지 그룹의 시작 번호
  endNumOfPageGroups: 5, // 현재 페이지 그룹의 끝 번호 (예: 1~5)
  showPageGroupsCount: 5 // 한 페이지 그룹에서 보여줄 페이지 수
});

watch(() => props.isVisible, (newVal) => {
  if (newVal) {
    initPage(); // 모달이 열릴 때 초기화
    performSearch(); // 첫 검색 수행
  }
});

onMounted(
  async () => {
    try {
      const url = `http://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=b5e83526a03f37b8349141b21fa2f6e7&svcType=api&svcCode=` + "SCHOOL" + `&contentType=json&perPage=5&gubun=univ_list&searchSchulNm=` + searchTerm.value + '&thisPage=1';
      api.$get(url).then(response => {
        console.log('Search response:', response);
        searchResult.value = response.dataSearch.content;
        if (response.dataSearch.content.length > 0) {
          const totalCount = response.dataSearch.content[0].totalCount; // 전체 데이터 수
          paginationData.totalPageCount = Math.ceil(totalCount / 5); // 총 페이지 수 계산
          paginationData.endNumOfPageGroups = Math.min(paginationData.showPageGroupsCount, paginationData.totalPageCount); // 페이지 그룹 끝 번호 설정
        } else {
          initPage(); // 검색 결과가 없으면 초기화
        }
      }).catch(error => {
        console.error('Error searching:', error);
      });
    } catch (error) {
      console.error('Error searching:', error);
    }
  }
);

//검색 함수
const performSearch = () => {
  const url = `http://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=b5e83526a03f37b8349141b21fa2f6e7&svcType=api&svcCode=${searchType}&contentType=json&perPage=5&gubun=univ_list&searchSchulNm=${searchTerm.value}&thisPage=${paginationData.pageGroupsOfCurrentPage}`;

  api.$get(url).then(response => {
    console.log('Search response:', response);
    searchResult.value = response.dataSearch.content;
    if (response.dataSearch.content.length > 0) {
      const totalCount = response.dataSearch.content[0].totalCount;
      paginationData.totalPageCount = Math.ceil(totalCount / 5); // 5개씩 페이지 나눔
      // 페이지 그룹 끝 번호 조정
      paginationData.endNumOfPageGroups = Math.min(paginationData.startNumOfPageGroups + 4, paginationData.totalPageCount);
    } else {
      initPage();
    }
  }).catch(error => {
    console.error('Error searching:', error);
  });
};
const highformSearch = () => {
  const url = `http://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=b5e83526a03f37b8349141b21fa2f6e7&svcType=api&svcCode=${searchType}&contentType=json&perPage=5&gubun=high_list&searchSchulNm=${searchTerm.value}&thisPage=${paginationData.pageGroupsOfCurrentPage}`;

  api.$get(url).then(response => {
    console.log('Search response:', response);
    searchResult.value = response.dataSearch.content;
    if (response.dataSearch.content.length > 0) {
      const totalCount = response.dataSearch.content[0].totalCount;
      paginationData.totalPageCount = Math.ceil(totalCount / 5); // 5개씩 페이지 나눔
      // 페이지 그룹 끝 번호 조정
      paginationData.endNumOfPageGroups = Math.min(paginationData.startNumOfPageGroups + 4, paginationData.totalPageCount);
    } else {
      initPage();
    }
  }).catch(error => {
    console.error('Error searching:', error);
  });
};
//검색 새로 실시 할때
const doNewSearch = () => {
  paginationData.pageGroupsOfCurrentPage = 1;
  performSearch(),highformSearch();
}

const selectSchool = (item) => {
  console.log(item);
  emit('setResult', item);
  searchResult.value = [];
} // selectSchool


//페이징 함수 (자식 컴포넌트가 호출)
const changePageNo = (changePageNo) => {
  paginationData.pageGroupsOfCurrentPage = changePageNo; // 현재 페이지를 변경
  paginationData.pageNo = changePageNo;

  // 페이지 그룹 업데이트
  if (paginationData.endNumOfPageGroups < changePageNo) {
    paginationData.startNumOfPageGroups += paginationData.showPageGroupsCount;
    paginationData.endNumOfPageGroups += paginationData.showPageGroupsCount;
  } else if (changePageNo < paginationData.startNumOfPageGroups) {
    paginationData.startNumOfPageGroups -= paginationData.showPageGroupsCount;
    paginationData.endNumOfPageGroups -= paginationData.showPageGroupsCount;
  }

  // 페이지 변경 후 검색 결과 가져오기
  performSearch();
};

//페이지 초기화 함수
const initPage = () => {
  paginationData.startNumOfPageGroups = 1;
  paginationData.endNumOfPageGroups = Math.min(5, paginationData.totalPageCount);
  paginationData.pageGroupsOfCurrentPage = 1;
  paginationData.totalPageCount = 0; 
  searchResult.value = []; // 검색 결과 초기화
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1050;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  max-width: 500px;
  /* Adjust as needed */
  position: relative;
}

.btn-close {
  position: absolute;
  top: 10px;
  right: 10px;
  border: none;
  background: transparent;
  font-size: 1.5rem;
  cursor: pointer;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  max-width: 500px;
  position: relative;
}

.list-wrap {
  margin-top: 15px;
}

.error-message {
  color: red;
  margin-top: 5px;
}

.shake {
  animation: shake 0.5s ease;
}

@keyframes shake {

  0%,
  100% {
    transform: translateX(0);
  }

  10%,
  30%,
  50%,
  70%,
  90% {
    transform: translateX(-10px);
  }

  20%,
  40%,
  60%,
  80% {
    transform: translateX(10px);
  }
}

#pagination-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>