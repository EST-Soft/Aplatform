<template>
    <div class="row">
      <div class="input-group mb-3">
        <input type="text" v-model="searchTerm" class="form-control" placeholder="검색어를 입력해주세요." >
        <button class="btn btn-primary" @click="$emit('setResult', searchTerm)">직접입력</button>
        <button class="btn btn-primary" @click="doNewSearch">검색</button>
        <button class="btn btn-primary" @click="$emit('close', false)">취소</button>
      </div>
    </div>
    <section class="call-to-action with-full-borders mb-2">
      <div class="row col-sm-12 col-lg-12">
        <!-- 자료없을때 예외 -->
        <div v-if="searchResult.length == 0">
            <strong class="font-weight-extra-bold"> 검색결과가 없습니다.</strong>
        </div>
        <!-- 자료있을때 for -->
        <div v-else>
          <ul class="list-group" v-for="item in searchResult" :key="item.schoolName">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              {{ item.schoolName + " ("+ item.campusName + ")"}}
              <button type="button" class="btn btn-primary">선택</button>
            </li>
          </ul>
        </div>
      </div>
      <div class="row">
        <div v-if="paginationData.totalPageGroupsCount != undefined && paginationData.totalPageGroupsCount != 0">
            <!-- <BasePagination :currentPage="currentPage" :goToPage="goToPage" :totalPages="totalPages"/> -->
            <PaginationData :paginationData="paginationData" @change-page-no="changePageNo" style="margin: 0 auto;" />
        </div>
      </div>
    </section>


    
  </template>
  
  <script setup>
    import { defineProps, reactive, ref } from 'vue';
    import { api } from '@/axios.js';
    import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
    /* eslint-disable */
    
    // emits 선언
    const emit = defineEmits(['setResult', 'closePopup']);
    
    //속성선언  
    const props = defineProps({
          modalData : {
              type : String
          },
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
      pageGroupsOfCurrentPage : 1,
      startNumOfPageGroups : 1,
      endNumOfPageGroups : 10,
      showPageGroupsCount : 10,
      totalPageGroupsCount : 0,
    });
 
    //검색 함수
    const performSearch = () => {
      
      if(searchType == "school"){

      }

      //인증키 : b5e83526a03f37b8349141b21fa2f6e7 (임시) JOB_VIEW
      const url = `http://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=b5e83526a03f37b8349141b21fa2f6e7&svcType=api&svcCode=` + searchType + `&contentType=json&perPage=10&gubun=univ_list&searchSchulNm=` + searchTerm.value +'&thisPage=' + paginationData.pageGroupsOfCurrentPage;
   
      api.$get(url).then(response => {
        console.log('Search response:', response);
        searchResult.value = response.dataSearch.content;
        if( response.dataSearch.content.length > 0 ){
          // totalPages.value = Math.ceil(response.dataSearch.content[0].totalCount / 10);
          // paginationData.endNumOfPageGroups = response.dataSearch.content[0].totalCount;
          paginationData.totalPageGroupsCount = Math.ceil(response.dataSearch.content[0].totalCount / 10);
          
        }else{
          //초기화
          initPage();
        }
      }).catch(error => {
        console.error('Error searching:', error);
      });
    }
    //검색 새로 실시 할때
    const doNewSearch = () => {
      paginationData.pageGroupsOfCurrentPage = 1;
      performSearch();
    }

    //페이징 함수 (자식 컴포넌트가 호출)
    const changePageNo = (changePageNo) => {
      paginationData.pageGroupsOfCurrentPage = changePageNo;
      if(paginationData.endNumOfPageGroups < changePageNo ){        
        paginationData.startNumOfPageGroups = paginationData.startNumOfPageGroups + paginationData.showPageGroupsCount;
        paginationData.endNumOfPageGroups = paginationData.endNumOfPageGroups + paginationData.showPageGroupsCount;
      }else if(changePageNo < paginationData.startNumOfPageGroups){
        if(changePageNo < paginationData.showPageGroupsCount){
          paginationData.startNumOfPageGroups = 1;
          paginationData.endNumOfPageGroups = paginationData.showPageGroupsCount;
        }else{
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
  .search-area {
    margin-bottom: 20px;
  }
  
  .error-message {
    color: red;
    margin-top: 5px;
  }
  
  .shake {
    animation: shake 0.5s ease;
  }
  
  @keyframes shake {
    0%, 100% { transform: translateX(0); }
    10%, 30%, 50%, 70%, 90% { transform: translateX(-10px); }
    20%, 40%, 60%, 80% { transform: translateX(10px); }
  }
  </style>
  