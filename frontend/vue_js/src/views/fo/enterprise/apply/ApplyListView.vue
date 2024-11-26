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

            <!-- 상태 선택 및 저장 -->
            <div class="form-group">
              <select v-model="applyData.selectedCondition" class="form-select form-control h-auto py-2">
                <option v-for="applyCondition in applyListData.applyConditions" :key="applyCondition.code_id"
                        :value="applyCondition.code_id">
                  {{ applyCondition.code_name }}
                </option>
              </select>
              <button class="btn btn-primary mt-2" @click="updateStatus(applyData)">
                상태 저장
              </button>
            </div>
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
import { useRoute } from "vue-router";
import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
import ApplyDatas from "@/components/fo/enterprise/apply/ApplyDatas.vue";
import { api } from "@/axios";
import { showAlert } from "../../../../utill/utillModal";

// 지원자 목록 데이터
const applyListData = ref({
  applyDatas: [],
  paginationData: {},
  searchData: { jbp_sq: 1, division: "all", condition: "", sort: "", pageNo: 1 },
  applyConditions: [], // 초기 상태 빈 배열
});

const route = useRoute();

onMounted(() => {
  const jbpSqFromRoute = route.params.jbpSq || 1;
  applyListData.value.searchData.jbp_sq = jbpSqFromRoute;
  callAxios();
});

// API 호출 함수
const callAxios = async () => {
  const { jbp_sq, division, condition, sort, pageNo } = applyListData.value.searchData;

  const url = `/applys/apply-list/${jbp_sq}?division=${division}&condition=${condition || ''}&sort=${sort || ''}&pageNo=${pageNo}`;
  
  try {
    const response = await api.$get(url);
    // 중복된 apy_sq 값 제거
    const uniqueApplyDatas = response?.applyDatas.filter((value, index, self) =>
      index === self.findIndex((t) => t.apy_sq === value.apy_sq)
    );

    // 데이터 할당
    applyListData.value.applyConditions = response?.applyConditions || [];
    applyListData.value.applyDatas = uniqueApplyDatas;
    applyListData.value.paginationData = response?.paginationData || {};

    // 초기 selectedCondition 설정
    applyListData.value.applyDatas.forEach(applyData => {
      applyData.selectedCondition = applyData.apply_condition || ''; // 기존 상태 값으로 초기화
    });
  } catch (error) {
    console.error("axios 실패", error);
    applyListData.value.applyConditions = [];
    applyListData.value.applyDatas = [];
    applyListData.value.paginationData = {};
  }
};

const updateStatus = async (applyData) => {
  try {
    const { apy_sq , selectedCondition  } = applyData;
    
    // URL을 경로 변수와 쿼리 파라미터로 구성
    const url = `/applys/condition/${apy_sq}/${selectedCondition}`;
    
    // PATCH 요청을 보냄
    console.log("상태 업데이트 성공1111");
    var response = await api.$put(url);
    console.log("상태 업데이트 성공2222", response);


    // 상태 업데이트 후 메시지 처리
    showAlert('상태가 업데이트되었습니다.');
    callAxios();
  } catch (error) {
    console.error("상태 업데이트 실패", error);
    showAlert('상태 업데이트에 실패했습니다.');
  }
};


// 페이지네이션 변경
const changePageNo = (pageNo) => {
  applyListData.value.searchData.pageNo = pageNo;
  callAxios();
};

// 구분 변경
const changeDivision = (event) => {
  applyListData.value.searchData.division = event.target.value;
  applyListData.value.searchData.pageNo = 1; // 페이지 초기화
  callAxios();
};

// 상태 변경
const changeCondition = (event) => {
  applyListData.value.searchData.condition = event.target.value;
  applyListData.value.searchData.pageNo = 1; // 페이지 초기화
  callAxios();
};

// 정렬 변경
const changeSort = (event) => {
  applyListData.value.searchData.sort = event.target.value;
  applyListData.value.searchData.pageNo = 1; // 페이지 초기화
  callAxios();
};
</script>


<style scoped>
/* 기본 스타일 */
select.form-select {
  width: 100%; /* 전체 너비 */
  height: 40px; /* 높이 조정 */
  font-size: 14px; /* 글자 크기 */
}

/* 모바일 화면 */
@media (max-width: 768px) {
  .form-group select.form-select {
    width: 100%; /* 전체 너비 */
    height: 40px; /* 높이 유지 */
  }
}

/* 데스크탑 화면 */
@media (min-width: 769px) {
  .form-group select.form-select {
    width: 100%; /* 전체 너비 */
    height: 40px; /* 높이 유지 */
  }
}
</style>
