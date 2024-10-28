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
                        <div v-if="searchResult.length === 0">
                            <strong class="font-weight-extra-bold"> 검색결과가 없습니다.</strong>
                        </div>
                        <!-- 자료있을때 for -->
                        <div v-else class="list-wrap">
                            <ul class="list-group">
                                <li class="list-group-item d-flex justify-content-between align-items-center"
                                    v-for="item in searchResult" :key="item.자격구분코드">
                                    {{ item.종목명 }}
                                    <button type="button" class="btn btn-primary"
                                        @click="selectCertification(item)">선택</button>
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
//import { api } from '@/axios.js';
import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
import axios from 'axios';
/* eslint-disable */

// emits 선언
const emit = defineEmits(['update:isVisible', 'setResult']);

//속성선언  
const props = defineProps({
    isVisible: Boolean
});
//검색어 변수 선언
const searchTerm = ref('');
//검색결과 변수 선언
const searchResult = ref([]);

//페이징 관련 변수 선언
// const currentPage = ref('1');
// const totalPages = ref('');

watch(() => props.isVisible, (newVal) => {
  if (newVal) {
    initPage(); // 모달이 열릴 때 초기화
    performSearch(); // 첫 검색 수행
  }
});

const paginationData = reactive({
  pageNo: 1, // 현재 페이지 번호
  totalPageCount: 0, // 총 페이지 수
  startNumOfPageGroups: 1, // 현재 페이지 그룹의 시작 번호
  endNumOfPageGroups: 5, // 현재 페이지 그룹의 끝 번호 (예: 1~5)
  showPageGroupsCount: 5 // 한 페이지 그룹에서 보여줄 페이지 수
});

onMounted(
    async () => {
        try {
            const url = `https://api.odcloud.kr/api/15082998/v1/uddi:6569a851-1216-48d0-a76b-87ac2ee24f07?page=${paginationData.pageGroupsOfCurrentPage}&perPage=5&returnType=JSON&serviceKey=Rdp%2B6D4z4OrIhzIApel52dKDn3rEeTq4Akt0hW6vCmD16vVP6PwmdWqo69jANEEkL0sne7yv1%2B%2FyA%2FvBuysw1g%3D%3D&certificationName=${searchTerm.value}`;
            const response = await axios.get(url);
            console.log('Search response:', response.data);
            searchResult.value = response.data.data;
            if (response.data.data.length > 0) {
                const totalCount = response.data.totalCount; // 전체 데이터 수
                paginationData.totalPageCount = Math.ceil(totalCount / 5); // 총 페이지 수 계산
                paginationData.endNumOfPageGroups = Math.min(paginationData.showPageGroupsCount, paginationData.totalPageCount);
            } else {
                initPage();
            }
        } catch (error) {
            console.error('Error searching:', error);
        }
    }
);

//검색 함수
const performSearch = async () => {
    try {
        const url = `https://api.odcloud.kr/api/15082998/v1/uddi:6569a851-1216-48d0-a76b-87ac2ee24f07?page=1&perPage=4931&returnType=JSON&serviceKey=Rdp%2B6D4z4OrIhzIApel52dKDn3rEeTq4Akt0hW6vCmD16vVP6PwmdWqo69jANEEkL0sne7yv1%2B%2FyA%2FvBuysw1g%3D%3D`;
        const response = await axios.get(url);
        console.log('Search response:', response.data); // 응답 구조 확인
        searchResult.value = response.data.data;

        if (!response.data || !response.data.data) {
            console.error('Invalid response structure', response.data);
            return;
        }

        const data = response.data.data;

        // 전체 데이터를 검색 조건에 맞춰 필터링
        if (searchTerm.value.trim() === '') {
            searchResult.value = data; // 검색어가 없으면 전체 데이터
        } else {
            searchResult.value = data.filter(item =>
                item.종목명 && item.종목명.toLowerCase().includes(searchTerm.value.toLowerCase())
            );
        }

        // 페이지네이션 설정
        paginationData.totalPageCount = Math.ceil(searchResult.value.length / 5);

        // 현재 페이지에 맞는 데이터만 보여주기
        const startIndex = (paginationData.pageGroupsOfCurrentPage - 1) * 5;
        const endIndex = startIndex + 5;
        
        // 슬라이스 처리
        searchResult.value = searchResult.value.slice(startIndex, endIndex);
        console.log('Filtered Results:', searchResult.value);

        // 결과가 없으면 초기화
        if (searchResult.value.length === 0) {
            initPage();
        }

    } catch (error) {
        console.error('Error searching:', error);
    }
};

//검색 새로 실시 할때
const doNewSearch = () => {
    paginationData.pageGroupsOfCurrentPage = 1; // 페이지를 1로 초기화
    paginationData.pageNo = 1;
    paginationData.startNumOfPageGroups = 1; // 페이지 그룹 시작
    paginationData.endNumOfPageGroups = Math.min(paginationData.startNumOfPageGroups + 4, paginationData.totalPageCount); // 페이지 그룹 끝
    performSearch(); // 검색 실행
};

const selectCertification = (item) => {
    console.log(item);
    emit('setResult', item);
    searchResult.value = [];
};

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