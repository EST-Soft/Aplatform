<template>
  <div id="examples" class="container py-2">
    <div class="col pb-3 pt-3">
      <div class="row">
        <h1 class="form-group col-md-12">
          <strong class="font-weight-extra-bold"> 지원자 상세 </strong>
        </h1>
        <hr class="gradient" />
      </div>
      <div class="row">
        <h3 class="form-group col-md-6">
          <div class="col-sm-2 col-lg-4"> 제목 </div>
        </h3>
      </div>
      <div class="row">
        <section class="call-to-action with-full-borders mb-2 mainContainer">
          <div class="col-sm-2 col-lg-2">
            <div class="col-sm-12 col-lg-12 imgContainer">
              <span class="img-thumbnail d-block">
                <img class="img-fluid"
                  src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdraZyZ8CC81iPsZST1_TLB0SBOXC6wfvQ_Exx1uKkGsFpi4t_Mj4o8HhM_vPwI60yBF8&usqp=CAU"
                  alt style="width: 200px" />
              </span>
            </div>
          </div>
          <div class="row col-sm-10 col-lg-10">
            <div class="col-sm-4 col-lg-4">이름</div>
            <div class="col-sm-3 col-lg-1">성별</div>
            <div class="col-sm-3 col-lg-1">나이</div>
            <div class="col-sm-2 col-lg-2">생년월일</div>
            <div class="col-sm-2 col-lg-4">전화번호</div>
            <hr class="gradient mt-4" />
            <div class="col-sm-2 col-lg-8">주소</div>
            <div class="col-sm-3 col-lg-4">이메일</div>
            <hr class="gradient mt-4" />
            <div class="col-sm-4 col-lg-3">희망연봉</div>
            <div class="col-sm-3 col-lg-3">최종학력</div>
            <div class="col-sm-3 col-lg-3">학점</div>
            <div class="col-sm-3 col-lg-3"></div>
          </div>
        </section>
        <div class="row"></div>
      </div>
    </div>
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        학력 &nbsp;
        <a href="#" class="btn btn-primary btn-circle mb-2">
          <i class="fa fa-plus"></i>
        </a>
      </h4>
      <hr class="mt-1 mb-2" />
      <div>여기에 컴포넌트</div>
    </div>
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        경력 &nbsp;
        <a href="#" class="btn btn-primary btn-circle mb-2">
          <i class="fa fa-plus"></i>
        </a>
      </h4>
      <hr class="mt-1 mb-2" />
      <div>여기에 컴포넌트</div>
    </div>
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        스킬 &nbsp;
        <a href="#" class="btn btn-primary btn-circle mb-2">
          <i class="fa fa-plus"></i>
        </a>
      </h4>
      <hr class="mt-1 mb-2" />
      <div>여기에 컴포넌트</div>
    </div>
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        자격/면허증 &nbsp;
        <a href="#" class="btn btn-primary btn-circle mb-2">
          <i class="fa fa-plus"></i>
        </a>
      </h4>
      <hr class="mt-1 mb-2" />
      <div>여기에 컴포넌트</div>
    </div>
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        자기소개서 &nbsp;
        <a href="#" class="btn btn-primary btn-circle mb-2">
          <i class="fa fa-plus"></i>
        </a>
      </h4>
      <hr class="mt-1 mb-2" />
      <div>여기에 컴포넌트</div>
    </div>
    <div class="col pb-3 pt-1">
      <div class="col-lg-4">
        <h4 class="mb-0">
          첨부파일 &nbsp;
          <a href="#" class="btn btn-primary btn-circle mb-2">
            <i class="fa fa-plus"></i>
          </a>
        </h4>
        <hr class="mt-1 mb-2" />
        <div>여기에 컴포넌트</div>
      </div>
      <div class="col-lg-12">
        <div class="float-end">
          <a href="#" class="btn btn-outline btn-xl btn-primary mb-2">저장</a>
          <a href="#" class="btn btn-outline btn-xl btn-light mb-2">취소</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { api } from '@/axios';


//루트에서 들고온 PK 값
const { apy_sq } = history.state;

// axios 함수
const callAxios = async () => {
  await api.$get("/applys/apply-detail/" + apy_sq)
    .then((success) => {
      console.log('axios 성공' + success);
    })
    .catch((error) => {
      console.log('axios 실패' + error);

    });
};

onMounted(() => {
  // 첫페이지 입장시 정보 받아오기
  callAxios();
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
</style>
