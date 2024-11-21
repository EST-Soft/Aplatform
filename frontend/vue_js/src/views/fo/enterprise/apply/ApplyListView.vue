<template>
  <div id="examples" class="container py-2">
    <div class="col pb-3 pt-3">
      <div class="row">
        <h1 class="form-group col-md-12">
          <strong class="font-weight-extra-bold"> 지원자 목록 </strong>
        </h1>
        <hr class="gradient" />
      </div>

      <div class="row">
        <!-- 구분 선택 -->
        <div class="form-group col-md-2">
          <select class="form-select form-control h-auto py-2" @change="changeDivision($event)">
            <option value="all">구분</option>
            <option value="apply">지원</option>
            <option value="proposal">제안</option>
          </select>
        </div>

        <!-- 상태 선택 -->
        <div class="form-group col-md-2" v-if="applyListData.applyConditions && applyListData.applyConditions.length > 0">
          <select class="form-select form-control h-auto py-2" @change="changeCondition($event)">
            <option value="0">상태</option>
            <option v-for="applyCondition in applyListData.applyConditions" :key="applyCondition.code_id"
              :value="applyCondition.code_id">
              {{ applyCondition.code_name }}
            </option>
          </select>
        </div>

        <div class="form-group col-md-6"></div>

        <!-- 정렬 선택 -->
        <div class="form-group col-md-2">
          <select class="form-select form-control h-auto py-2" @change="changeSort($event)">
            <option value="asc">올림차순</option>
            <option value="desc">내림차순</option>
          </select>
        </div>
      </div>

      <div class="row">
        <!-- 자료 없을 때 예외 처리 -->
        <div v-if="applyListData.applyDatas.length === 0">
          <strong class="font-weight-extra-bold"> 자료가 없습니다. </strong>
        </div>
        <!-- 자료 있으면 반복 출력 -->
        <div v-else>
          <div v-for="applyData in applyListData.applyDatas" :key="applyData.apy_sq">
            <ApplyDatas :applyData="applyData" />
          </div>
        </div>
      </div>
      <hr class="gradient" />
      <div class="row">
        <!-- 페이지네이션 -->
        <div v-if="applyListData.paginationData.totalDataCount != undefined && applyListData.paginationData.totalDataCount != 0">
          <PaginationData :paginationData="applyListData.paginationData" @change-page-no="changePageNo" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
import ApplyDatas from "@/components/fo/enterprise/apply/ApplyDatas.vue";
import { api } from '@/axios';

const applyListData = ref({
  applyDatas: [],
  paginationData: {},
  searchData: { jbp_sq: 86, division: 'all', condition: '', sort: 'asc', pageNo: 1 },
  applyConditions: [],  // 빈 배열로 초기화
});

onMounted(() => {
  console.log("온마운트");
  callAxios();  // 첫 로딩 시 데이터 요청
});

const callAxios = async () => { 
  const { jbp_sq, division, condition, sort, pageNo } = applyListData.value.searchData;
  
  const url = `/applys/apply-list/${jbp_sq}?division=${division}&condition=${condition}&sort=${sort}&pageNo=${pageNo}`;
  console.log("Request URL:", url);

  
  try {
    const response = await api.$get(url);
    console.log('axios 성공', response);
    
    // 중복된 apy_sq 값 제거
    const uniqueApplyDatas = response?.applyDatas.filter((value, index, self) =>
      index === self.findIndex((t) => (
        t.apy_sq === value.apy_sq
      ))
    );

    // 안전하게 응답 데이터 할당
    applyListData.value.applyConditions = response?.applyConditions || [];
    applyListData.value.applyDatas = uniqueApplyDatas; // 중복 제거된 데이터 할당
    applyListData.value.paginationData = response?.paginationData || {};

    console.log('applyConditions:', applyListData.value.applyConditions);
    console.log('applyDatas:', applyListData.value.applyDatas);
    console.log('paginationData:', applyListData.value.paginationData);

  } catch (error) {
    console.error('axios 실패', error);
    // 기본값으로 초기화
    applyListData.value.applyConditions = [];
    applyListData.value.applyDatas = [];
    applyListData.value.paginationData = {};
  }
};

// 페이지네이션 페이지 변경
const changePageNo = (pageNo) => {
  applyListData.value.searchData.pageNo = pageNo;
  callAxios();
};

// 구분 선택
const changeDivision = (event) => {
  applyListData.value.searchData.division = event.target.value;
  applyListData.value.searchData.pageNo = 1;  // 페이지를 처음으로 리셋
  callAxios();
};

// 상태 선택
const changeCondition = (event) => {
  // 문자열을 숫자로 변환
  applyListData.value.searchData.condition = event.target.value;

  console.log(applyListData.value.searchData.condition);
  console.log("ASD" + event.target.value);

  applyListData.value.searchData.pageNo = 1;  // 페이지를 처음으로 리셋
  callAxios();
};

// 정렬 선택
const changeSort = (event) => {
  applyListData.value.searchData.sort = event.target.value;
  applyListData.value.searchData.pageNo = 1;  // 페이지를 처음으로 리셋
  callAxios();
};
</script>

<style scoped>
/* 기본적으로 가로로 펼쳐짐 */
select.form-select {
  width: 100%; /* 전체 너비 사용 */
  height: 40px; /* 높이 조정 */
  font-size: 14px; /* 글자 크기 */
}

/* 모바일 화면에서 가로로 유지 */
@media (max-width: 768px) {
  .form-group select.form-select {
    width: 100%; /* 모바일에서 전체 너비 사용 */
    height: 40px; /* 높이 유지 */
  }
}

/* 데스크탑 화면에서 크기 조정 */
@media (min-width: 769px) {
  .form-group select.form-select {
    width: 100%; /* 데스크탑에서도 전체 너비 사용 */
    height: 40px; /* 높이 유지 */
  }
}
</style>
