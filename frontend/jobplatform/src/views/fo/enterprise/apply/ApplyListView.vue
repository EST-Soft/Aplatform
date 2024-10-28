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
        <!-- 코드화 안되어있어서 하드코딩 -->
        <div class="form-group col-md-2">
          <!-- 여기 해야함 -->
          <select class="form-select form-control h-auto py-2" @change="changeDivision($event)">
            <option value="all">구분</option>
            <option value="apply">지원</option>
            <option value="proposal">제안</option>
          </select>
        </div>

        <!-- 코드화 -->
        <div class="form-group col-md-2">
          <select class="form-select form-control h-auto py-2" @change="changeCondition($event)">
            <option value="0">상태</option>
            <option v-for="applyCondition in applyListData.applyConditions" :key="applyCondition.code_id"
              :value="applyCondition.code_id">
              {{ applyCondition.code_name }}
            </option>
          </select>
        </div>

        <div class="form-group col-md-6"></div>

        <!-- 코드화 안되어있어서 하드코딩 -->
        <div class="form-group col-md-2">
          <select class="form-select form-control h-auto py-2" @change="changeSort($event)">
            <option value="asc">올림차순</option>
            <option value="desc">내림차순</option>
          </select>
        </div>
      </div>

      <div class="row">
        <!-- 자료없을때 예외 -->
        <div v-if="applyListData.applyDatas.length === 0">
          <strong class="font-weight-extra-bold"> 자료가 없습니다. </strong>
        </div>
        <!-- 자료있을때 for -->
        <div v-else>
          <div v-for="applyData in applyListData.applyDatas" :key="applyData.apy_sq">
            <ApplyDatas :applyData="applyData" />
          </div>
        </div>
      </div>
      <hr class="gradient" />
      <div class="row">
        <div
          v-if="applyListData.paginationData.totalDataCount != undefined && applyListData.paginationData.totalDataCount != 0">
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
import { api } from '@/axios.js';

const applyListData = ref({
  applyDatas: [],
  paginationData: {},
  searchData: {},
  applyConditions: [],
});

//공고리스트에서 지원자 보기 클릭시 보내기
// @click="goToApplyList(jbp_sq데이터)""
// const goToApplyList = (jbp_sq) => {
//   router.push({
//     name: "applyListView",
//     state: {
//       jbp_sq: jbp_sq,
//     },
//   });
// };
// 받기
// const { jbp_sq } = history.state;
// 온마운트에이 값을 넣어주고 시작

onMounted(() => {
  // 첫페이지 입장시 정보 받아오기
  console.log("온마운트");
  //console.log(applyListData.value.paginationData.totalDataCount);
  applyListData.value.searchData = { jbp_sq: 1, division: "all", condition: 0, sort: "asc", pageNo: 1 };
  callAxios();
});

// axios 함수
const callAxios = async () => {
  await api.$get("/applys/apply-list/" +
    applyListData.value.searchData.jbp_sq +
    "/" +
    applyListData.value.searchData.division +
    "/" +
    applyListData.value.searchData.condition +
    "/" +
    applyListData.value.searchData.sort +
    "/" +
    applyListData.value.searchData.pageNo)
    .then((success) => {
      console.log('axios 성공' + success);
      applyListData.value = success;
    })
    .catch((error) => {
      console.log('axios 실패' + error);

    });

};

// 이벤트 함수
// 페이지네이션 페이지 변경 클릭
const changePageNo = (emit) => {
  console.log(emit);
  applyListData.value.searchData.pageNo = emit;
  callAxios();
};
// 구분 select 변경
const changeDivision = (event) => {
  applyListData.value.searchData.division = event.target.value;
  applyListData.value.searchData.pageNo = 1;
  callAxios();
};
// 상태 select 변경
const changeCondition = (event) => {
  applyListData.value.searchData.condition = event.target.value;
  applyListData.value.searchData.pageNo = 1;
  callAxios();
};
// 정렬 select 변경
const changeSort = (event) => {
  applyListData.value.searchData.sort = event.target.value;
  applyListData.value.searchData.pageNo = 1;
  callAxios();
};
</script>

<style scoped></style>
