<template>
  <div id="examples" class="container py-2">
    <!-- 로딩 중일 때 로딩 메시지 표시 -->
    <div v-if="loading" class="text-center">
    </div>

    <!-- 데이터가 있을 때만 상세정보 표시 -->
    <div v-if="!loading && applyData.rsm_name" class="col pb-3 pt-3">
      <div class="row">
        <h1 class="form-group col-md-12">
          <strong class="font-weight-extra-bold"> 지원자 상세 </strong>
        </h1>
        <hr class="gradient" />
      </div>
      <div class="row">
        <h3 class="form-group col-md-6">
          <div class="col-sm-2 col-lg-5">{{ applyData.rsm_ttl }}</div>
        </h3>
      </div>
      <div class="row">
        <section class="call-to-action with-full-borders mb-2 mainContainer">
          <div class="col-sm-2 col-lg-2">
            <div class="col-sm-12 col-lg-12 imgContainer">
              <span class="img-thumbnail d-block">
                <img 
                  class="img-fluid" 
                  v-if="applyData && applyData.rsm_img_file_url && applyData.rsm_img_file_url !== ''" 
                  :src="applyData.rsm_img_file_url" 
                  alt="대체이미지" 
                  style="width: 200px; height: 170px;">
                <img 
                  v-else 
                  class="img-fluid" 
                  src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdraZyZ8CC81iPsZST1_TLB0SBOXC6wfvQ_Exx1uKkGsFpi4t_Mj4o8HhM_vPwI60yBF8&usqp=CAU" 
                  alt 
                  style="width: 200px" />
              </span>
            </div>
          </div>
          <div class="row col-sm-10 col-lg-10">
            <div class="col-sm-4 col-lg-4">{{ applyData.rsm_name }}</div>
            <div class="col-sm-3 col-lg-1">{{ applyData.rsm_gndr_code_name }}</div>
            <div class="col-sm-3 col-lg-1">{{ applyData.age }}</div>
            <div class="col-sm-2 col-lg-2">{{ applyData.rsm_bd }}</div>
            <div class="col-sm-2 col-lg-4">{{ applyData.rsm_mp }}</div>
            <hr class="gradient mt-4" />
            <div class="col-sm-2 col-lg-8">{{ applyData.rsm_adrs }}</div>
            <div class="col-sm-3 col-lg-4">{{ applyData.rsm_eml }}</div>
            <hr class="gradient mt-4" />
            <div class="col-sm-4 col-lg-3">{{ applyData.rsm_es }}</div>
            <div class="col-sm-3 col-lg-3">{{ applyData.rsm_fnl_edctn_code_name || '정보 없음' }}</div>
            <div class="col-sm-3 col-lg-3">{{ applyData.rsm_grd }}</div>
            <div class="col-sm-3 col-lg-3">{{ applyData.apy_cndtn_name }}</div>
          </div>
        </section>
      </div>
    </div>

    <!-- 데이터가 없을 때 접근권한 메시지 표시 -->
    <div v-else-if="!loading" class="access-denied-message">
      <h3 class="text-center text-danger">
        상세정보에 접근권한이 없습니다.
      </h3>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router';
import { onMounted, ref } from "vue";
import { api } from '@/axios';

const route = useRoute();  // 현재 라우터 정보 가져오기
const applyData = ref({});  // 데이터 저장용 ref 변수
const loading = ref(true);  // 로딩 상태를 추적하는 변수

const callAxios = async () => {
  try {
    const apySq = route.params.apySq;  // URL에서 apySq 파라미터를 직접 가져오기
    if (apySq) {  // apySq 값이 있을 때만 실행
      const response = await api.$get(`/applys/apply-detail/${apySq}`);  // API 호출
      console.log('axios 성공', response);  // 응답 구조 확인
      applyData.value = response;  // 응답 데이터를 applyData에 할당
      loading.value = false;  // 데이터 로드 후 로딩 상태 종료
      console.log('applyData:', applyData.value);  // applyData에 할당된 값 출력
    } else {
      console.log("apySq 값이 없습니다.");
      loading.value = false;  // apySq 값이 없으면 로딩 상태 종료
    }
  } catch (error) {
    console.log('axios 실패', error);  // 오류 처리
    loading.value = false;  // 오류 발생 시 로딩 상태 종료
  }
};

onMounted(() => {
  const apySq = route.params.apySq;
  callAxios(apySq);
});
</script>

<style scoped>
.mainContainer {
  padding-left: 10px;
  padding-right: 10px;
}

.imgContainer {
  padding-right: 5px;
}

/* 접근 권한 메시지를 화면 중앙에 배치 */
.access-denied-message {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;  /* 화면 전체 높이를 차지하도록 설정 */
}

/* 로딩 중 메시지 스타일 */
.text-center {
  text-align: center;
}
</style>
