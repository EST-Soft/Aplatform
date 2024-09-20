<template>
    <div v-if="isVisible" class="modal-overlay">
        <div class="modal-content">
            <button class="btn-close" @click="$emit('update:isVisible', false)">×</button>
            <div class="modal-body">
                <div class="row">
                    <div class="input-group mb-3">
                        <input type="text" v-model="searchTerm" class="form-control" placeholder="검색어를 입력해주세요.">
                        <div class="btn btn-primary" @click="$emit('setResult', searchTerm)">직접입력</div>
                        <div class="btn btn-primary" @click="performSearch">검색</div>
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
                    <div class="row">
                        <div
                            v-if="paginationData.totalPageGroupsCount != undefined && paginationData.totalPageGroupsCount != 0">
                            <!-- <BasePagination :currentPage="currentPage" :goToPage="goToPage" :totalPages="totalPages"/> -->
                            <PaginationData :paginationData="paginationData" @change-page-no="changePageNo"
                                style="margin: 0 auto;" />
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>





</template>

<script setup>
import { defineProps, reactive, ref, onMounted } from 'vue';
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

const paginationData = reactive({
    pageGroupsOfCurrentPage: 1,
    startNumOfPageGroups: 1,
    endNumOfPageGroups: 10,
    showPageGroupsCount: 10,
    totalPageGroupsCount: 0,
});

onMounted(
    async () => {
        try {
            const url = `https://api.odcloud.kr/api/15082998/v1/uddi:6569a851-1216-48d0-a76b-87ac2ee24f07?page=${paginationData.pageGroupsOfCurrentPage}&perPage=5&returnType=JSON&serviceKey=Rdp%2B6D4z4OrIhzIApel52dKDn3rEeTq4Akt0hW6vCmD16vVP6PwmdWqo69jANEEkL0sne7yv1%2B%2FyA%2FvBuysw1g%3D%3D&certificationName=${searchTerm.value}`;
            const response = await axios.get(url);
            console.log('Search response:', response.data);
            searchResult.value = response.data.data;
            if (response.data.data.length > 0) {
                paginationData.totalPageGroupsCount = Math.ceil(response.data.totalCount / 10); // Update based on response
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
        const url = `https://api.odcloud.kr/api/15082998/v1/uddi:6569a851-1216-48d0-a76b-87ac2ee24f07?page=${paginationData.pageGroupsOfCurrentPage}&perPage=5&returnType=JSON&serviceKey=Rdp%2B6D4z4OrIhzIApel52dKDn3rEeTq4Akt0hW6vCmD16vVP6PwmdWqo69jANEEkL0sne7yv1%2B%2FyA%2FvBuysw1g%3D%3D&certificationName=${searchTerm.value}`;
        const response = await axios.get(url).then(response => { console.log('Search response:', response.data); return response.data; });


        if (searchTerm.value.trim() === '') {
            searchResult.value = response.data; // 검색어가 비어있다면 모든 결과
        } else {
            searchResult.value = response.data.filter(item =>
                item.종목명.includes(searchTerm.value) // 검색어가 포함된 요소만
            );
        }

        if (response.data.data.length > 0) {
            paginationData.totalPageGroupsCount = Math.ceil(response.data.totalCount / 10); // Update based on response
        } else {
            initPage();
        }
    } catch (error) {
        console.error('Error searching:', error);
    }
};

/* //검색 새로 실시 할때
const doNewSearch = () => {
    event.preventDefault();
    paginationData.pageGroupsOfCurrentPage = 1;
    performSearch();
} */

const selectCertification = (item) => {
    console.log(item);
    emit('setResult', item);
    searchResult.value = [];
};

//페이징 함수 (자식 컴포넌트가 호출)
const changePageNo = (changePageNo) => {
    paginationData.pageGroupsOfCurrentPage = changePageNo;
    if (paginationData.endNumOfPageGroups < changePageNo) {
        paginationData.startNumOfPageGroups = paginationData.startNumOfPageGroups + paginationData.showPageGroupsCount;
        paginationData.endNumOfPageGroups = paginationData.endNumOfPageGroups + paginationData.showPageGroupsCount;
    } else if (changePageNo < paginationData.startNumOfPageGroups) {
        if (changePageNo < paginationData.showPageGroupsCount) {
            paginationData.startNumOfPageGroups = 1;
            paginationData.endNumOfPageGroups = paginationData.showPageGroupsCount;
        } else {
            paginationData.startNumOfPageGroups = paginationData.startNumOfPageGroups - paginationData.showPageGroupsCount;
            paginationData.endNumOfPageGroups = paginationData.endNumOfPageGroups - paginationData.showPageGroupsCount;
        }

    }
    // currentPage.value = changePageNo;
    performSearch();
};
//페이지 초기화 함수
const initPage = () => {
    paginationData.startNumOfPageGroups = 1;
    paginationData.endNumOfPageGroups = 10;
    paginationData.pageGroupsOfCurrentPage = 1;
    paginationData.totalPageGroupsCount = 0;
}
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
</style>