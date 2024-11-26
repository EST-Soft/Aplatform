<template>
  <section class="call-to-action with-full-borders mb-2 mainContainer">
    <div class="col-sm-2 col-lg-2">
      <div class="col-sm-12 col-lg-12 imgContainer">
        <span class="img-thumbnail d-block">
          <img class="img-fluid" v-if="applyData && applyData.rsm_img_file_url && applyData.rsm_img_file_url !== ''" 
          :src="applyData.rsm_img_file_url" alt="대체이미지" style="width: 170px; height: 170px;">
          <img v-else class="img-fluid"
            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdraZyZ8CC81iPsZST1_TLB0SBOXC6wfvQ_Exx1uKkGsFpi4t_Mj4o8HhM_vPwI60yBF8&usqp=CAU"
            alt style="width: 200px" />
        </span>
      </div>
    </div>
    <div class="row col-sm-10 col-lg-10">
      <div class="col-sm-4 col-lg-4">이름 : {{ props.applyData.rsm_name }}</div>
      <div class="col-sm-3 col-lg-3">희망연봉 : {{ fomatSalaryNull(props.applyData.rsm_es) }}</div>
      <div class="col-sm-3 col-lg-3">최종학력 : {{ props.applyData.rsm_fnl_edctn_name }}</div>
      <div class="col-sm-2 col-lg-2">학점 : {{ fomatGradeNull(props.applyData.rsm_grd) }}</div>
      <hr class="gradient mt-4" />
      <div class="col-sm-2 col-lg-3">상태 : {{ props.applyData.apy_cndtn_name }}</div>
      <div class="col-sm-3 col-lg-2">구분 : {{ props.applyData.rnqns_name }}</div>
      <div class="col-sm-4 col-lg-4">지원일시 :{{ fomatDate(props.applyData.apy_dtm) }}</div>
      <div class="col-sm-3 col-lg-3">
        <div class="call-to-action-btn">
          <a class="btn btn-outline btn-rounded btn-light" @click="goToDetail(props.applyData.apy_sq)"> 상세열람 </a>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import router from "@/router";
import { defineProps, defineEmits } from "vue";

const props = defineProps(["applyData"]);
const emit = defineEmits(["updateApplyData"]);

// 포멧 함수
// 날짜 포멧 변경
const fomatDate = (dateTime) => {
  const returnData = new Date(dateTime).toLocaleDateString();
  return returnData;
};
// 학점 포멧 변경
const fomatGradeNull = (data) => {
  let returnData = data;

  if (data == 0 || data == null) {
    returnData = "미입력";
  } else {
    returnData = data + " / 4.5";
  }

  return returnData;
};
// 연봉 포멧 변경
const fomatSalaryNull = (data) => {
  let returnData = data;

  if (data == 0 || data == null) {
    returnData = "미입력";
  } else {
    returnData = data + " 만원";
  }

  return returnData;
};

// 함수
// 루트에 정보들고 이동
const goToDetail = (apy_sq) => {
  // 상태가 "unrd"이면 "read"로 변경
  if (props.applyData.apy_cndtn_name === "unrd") {
    // 부모 컴포넌트로 상태 변경을 알림
    emit("updateApplyData", { ...props.applyData, apy_cndtn_name: "read" });  // 새로운 객체를 만들어 상태 변경
  }

  // 상세 페이지로 이동
  router.push({
    name: "applyDetailView",
    params: { apySq: apy_sq },  // URL 파라미터로 apySq 값을 전달
  });
};
</script>



<style scoped>
.mainContainer {
  padding-left: 10px;
  padding-right: 10px;
}

.imgContainer {
  padding-right: 5px;
}
</style>
