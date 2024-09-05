<template>
  <form id="completeForm" class="contact-form form-style-4 form-style-4-text-dark" @submit.prevent="submit">

    
      <div class="row">
        <h1 class="form-group col-md-12">
          <strong class="font-weight-extra-bold"> 이력서 등록 </strong>
        </h1>
        <hr class="gradient" />
      </div>
      <div class="col pb-3 pt-3">
        <div class="row">
          <div class="form-group col-md-6">
            <input type="text" value="" maxlength="20" class="form-control text-3 h-auto py-2" placeholder="제목"
              name="rsm_ttl" required />
          </div>
        </div>
        <div class="row">
          <section class="call-to-action with-full-borders mb-2 mainContainer">
            <div class="col-sm-2 col-lg-2">
              <div class="col-sm-12 col-lg-12 imgContainer">
                <span class="img-thumbnail d-block">
                  <img class="img-fluid"
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdraZyZ8CC81iPsZST1_TLB0SBOXC6wfvQ_Exx1uKkGsFpi4t_Mj4o8HhM_vPwI60yBF8&usqp=CAU"
                    alt style="width: 200px" />
                  <a href="#" class="imgIcon">
                    <i class="fa-regular fa-trash-can"></i>
                  </a>
                </span>
              </div>
            </div>
            <div class="row col-sm-10 col-lg-10">
              <div class="col-sm-4 col-lg-4">
                <input type="text" value="" maxlength="100" class="form-control text-3 h-auto py-2" placeholder="이름"
                  name="rsm_name" required />
              </div>
              <div class="col-sm-3 col-lg-2">
                <div class="form-check form-check-inline">
                  <label class="form-check-label">
                    <input class="form-check-input" type="radio" id="rsm_gndr_code1" value="m" name="rsm_gndr_code"
                      required /> 남
                  </label>
                </div>
                <div class="form-check form-check-inline">
                  <label class="form-check-label">
                    <input class="form-check-input" type="radio" id="rsm_gndr_code2" value="f" name="rsm_gndr_code"
                      _required /> 여
                  </label>
                </div>
              </div>
              <div class="col-sm-2 col-lg-2">
                <input type="text" value="" maxlength="100" class="form-control text-3 h-auto py-2" name="rsm_bd"
                  placeholder="생년월일" required />
              </div>
              <div class="col-sm-2 col-lg-4">
                <input type="text" value="" maxlength="100" class="form-control text-3 h-auto py-2" name="rsm_mp"
                  placeholder="전화번호" required />
              </div>
              <hr class="mt-3 mb-1" />
              <div class="col-sm-2 col-lg-8">
                <input type="text" value="" maxlength="100" class="form-control text-3 h-auto py-2" name="rsm_adrs"
                  placeholder="주소" required />
              </div>
              <div class="col-sm-3 col-lg-4">
                <input type="text" value="" maxlength="100" class="form-control text-3 h-auto py-2" name="rsm_eml"
                  placeholder="이메일" required />
              </div>
              <hr class="mt-3 mb-1" />
              <div class="col-sm-4 col-lg-3">
                <input type="text" value="" maxlength="100" class="form-control text-3 h-auto py-2" name="rsm_es"
                  placeholder="희망연봉" required />
              </div>
              <div class="col-sm-3 col-lg-3">
                <!-- 셀랙박스로 만들어야할듯 -->
                <input type="text" value="000" maxlength="100" class="form-control text-3 h-auto py-2"
                  placeholder="최종학력" required />

                <!-- <select class="form-select form-control h-auto py-2" @change="changeCondition($event)">
                  <option v-for="applyCondition in applyListData.applyConditions" :key="applyCondition.code_id" :value="applyCondition.code_id">
                    {{ applyCondition.code_name }}
                  </option>
                  </select> -->

              </div>
              <div class="col-sm-3 col-lg-3">
                <input type="text" value="" maxlength="100" class="form-control text-3 h-auto py-2" name="rsm_grd"
                  placeholder="학점" required />
              </div>
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
        <div>
          <SearchPopup></SearchPopup>
        </div>
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
      <SkillsResume />
      <!-- <div class="col pb-3 pt-1">
        <h4 class="mb-0">
          기술 &nbsp;
          <a href="#" class="btn btn-primary btn-circle mb-2">
            <i class="fa fa-plus"></i>
          </a>
        </h4>
        <hr class="mt-1 mb-2" />
        <div>여기에 컴포넌트</div>
      </div> -->

      <CertificatesResume />
      <!-- <div class="col pb-3 pt-1">
        <h4 class="mb-0">
          자격/면허증 &nbsp;
          <a href="#" class="btn btn-primary btn-circle mb-2">
            <i class="fa fa-plus"></i>
          </a>
        </h4>
        <hr class="mt-1 mb-2" />
        <div>여기에 컴포넌트</div>
      </div> -->
      <SelfintoductionsResume />
      <!-- <div class="col pb-3 pt-1">
                  <h4 class="mb-0">
                      자기소개서 &nbsp;
                      <a href="#" class="btn btn-primary btn-circle mb-2">
                          <i class="fa fa-plus"></i>
                      </a>
                  </h4>
                  <hr class="mt-1 mb-2" />
                  <div>여기에 컴포넌트</div>
              </div>  -->
      <div class="col pb-3 pt-1">
        <!-- 여긴 다시 생각해봐야할듯 드래그인 드랍으로 하면 될꺼같기도?-->
        <AttachmentsResume />
        <!-- <div class="col-lg-4">
                      <h4 class="mb-0">
                          첨부파일 &nbsp;
                          <a href="#" class="btn btn-primary btn-circle mb-2">
                              <i class="fa fa-plus"></i>
                          </a>
                      </h4>
                      <hr class="mt-1 mb-2" />
                      <div>여기에 컴포넌트</div>
                  </div> -->
        <div class="col-lg-12">
          <div class="float-end">
            <a href="#" class="btn btn-outline btn-xl btn-primary mb-2">저장</a>
            <a href="#" class="btn btn-outline btn-xl btn-light mb-2">취소</a>
            <button type="submit">테스트</button>
          </div>
        </div>
      </div>
    

  </form>
</template>

<script setup>
import AttachmentsResume from "@/components/fo/enterprise/resume/AttachmentsResume.vue";
import SelfintoductionsResume from "@/components/fo/enterprise/resume/SelfintoductionsResume.vue";
import CertificatesResume from "@/components/fo/enterprise/resume/CertificatesResume.vue";
import SkillsResume from "@/components/fo/enterprise/resume/SkillsResume.vue";
import SearchPopup from "@/components/fo/user/common/SearchPopup.vue";
import axios from "axios";
import { onMounted } from "vue";



// 첫페이지 입장시 정보 받아오기(최종학력 코드 리스트 , 성별 코드 리스트 , 스킬 코드리스트 )
onMounted(() => {
  console.log("온마운트");

});



// form submit 함수
const submit = () => {
  console.log("오나?");
  // form 정보 formData화
  const formData = new FormData(document.getElementById('completeForm'));

  // 자기소개서 List 형식으로 추가시키기
  for (let i = 0; i < formData.getAll("title").length; i++) {
    formData.append("selfintoductionData[" + i + "].title", formData.getAll("title")[i]);
    formData.append("selfintoductionData[" + i + "].content", formData.getAll("content")[i]);
  }

  // axios 보내기
  axios.post("http://localhost:80/resumes/test", formData, {
    header: {
      'Context-Type': 'multipart/form-data',
    }
  })
    .then((success) => {
      console.log(success);
    })
    .catch((error) => {
      console.log(error)
    })

}

</script>

<style scoped>
.mainContainer {
  padding-left: 10px;
  padding-right: 10px;
}

.imgContainer {
  padding-right: 5px;
}

.imgIcon {
  position: absolute;
  right: 10px;
  bottom: 5px;
  font-size: larger;
}
</style>
