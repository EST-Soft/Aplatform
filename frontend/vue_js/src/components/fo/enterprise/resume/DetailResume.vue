<template>
    <div v-if="result">
        <div class="row">
          <h2 class="form-group col-md-12">
            <strong class="font-weight-extra-bold"> {{ result.rsmInfo.rsm_ttl }} </strong>
          </h2>
          <hr class="gradient" />
        </div>

        <div class="col pb-3 pt-3" id="basicInfo">
          <div class="row">
            <section class="call-to-action with-full-borders mb-2 mainContainer">
                <span class="img-thumbnail d-block">
                <img
                    src="@/assets/profile.webp"
                    class="img-fluid"
                    alt=""
                    style="width: 130px"
                />
                </span>
              <div class="row col-sm-10 col-lg-10">
                <div class="col-sm-2 col-lg-2" style="width: 124px;">
                    <label for="rsm_name" class="form-label">이름</label>
                    <input type="text" maxlength="100" readonly class="form-control-plaintext" id="rsm_name" v-bind:value="result.rsmInfo.rsm_name">
                </div>
                <div v-if="result.rsmInfo.rsm_gndr_code === 'm'" class="col-sm-2 col-lg-2">
                    <label for="rsm_gndr_code" class="form-label">성별</label>
                    <input type="text" maxlength="50" readonly class="form-control-plaintext" id="rsm_gndr_code" value="남">
                </div>
                <div v-else class="col-sm-2 col-lg-2">
                    <label for="rsm_gndr_code" class="form-label">성별</label>
                    <input type="text" maxlength="50" readonly class="form-control-plaintext" id="rsm_gndr_code" value="여">
                </div>
                <div class="col-sm-2 col-lg-3">
                    <label for="rsm_bd" class="form-label">생년월일</label>
                    <input type="text" maxlength="100" readonly class="form-control-plaintext" name="rsm_bd" id="rsm_bd" v-bind:value="result.rsmInfo.rsm_bd">
                </div>
                <div class="col-sm-2 col-lg-4">
                    <label for="rsm_mp" class="form-label">연락처</label>
                    <input type="text" maxlength="100" readonly class="form-control-plaintext" name="rsm_mp" id="rsm_mp" v-bind:value="result.rsmInfo.rsm_mp">
                </div>
                <hr class="mt-3 mb-1" style="width: 580px; margin-right: 50px;"/>
                <div class="col-sm-2 col-lg-7">
                    <label for="rsm_adrs" class="form-label">주소</label>
                  <input type="text" maxlength="100" readonly class="form-control-plaintext" name="rsm_adrs" id="rsm_adrs" v-bind:value="result.rsmInfo.rsm_adrs">
                </div>
                <div class="col-sm-3 col-lg-4" style="width: 220px;">
                    <label for="rsm_eml" class="form-label">이메일</label>
                  <input type="text" maxlength="100" readonly class="form-control-plaintext" name="rsm_eml" id="rsm_eml" v-bind:value="result.rsmInfo.rsm_eml">
                </div>
                <hr class="mt-3 mb-1" style="width: 580px; margin-right: 50px;"/>
                <div class="col-sm-4 col-lg-4">
                    <label for="rsm_es" class="form-label">희망연봉</label>
                  <input type="text" maxlength="100" readonly class="form-control-plaintext" name="rsm_es" id="rsm_es" v-bind:value="result.rsmInfo.rsm_es">
                </div>
                <div class="col-sm-4 col-lg-4">
                    <label for="rsm_fnl_edctn_code" class="form-label">최종학력</label>
                    <input type="text" maxlength="100" readonly class="form-control-plaintext" name="rsm_fnl_edctn_code" id="rsm_fnl_edctn_code" v-bind:value="result.rsmInfo.code_name">
                </div>
                <div class="col-sm-4 col-lg-4">
                    <label for="rsm_grd" class="form-label">학점</label>
                   <input type="text" maxlength="100" readonly class="form-control-plaintext" name="rsm_grd" id="rsm_grd" v-bind:value="result.rsmInfo.rsm_grd">
                </div>
                <div class="col-sm-3 col-lg-3"></div>
              </div>
            </section>
          </div>
        </div>

      <div class="adinfo">
        <!-- 학력 정보 -->
        <div v-if="result.rsmedctnInfo" class="titem">
          <div v-if="result.rsmedctnInfo.length > 0">
            <label for="rsm_edctn" class="llabel">학력</label>
            <div v-for="item in result.rsmedctnInfo" :key="item.id">
              <div class="row g-4" id="rsm_edctn">
                <div class="col-auto flex-grow-1"></div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="100" class="form-control-plaintext" :name="'schl_name_' + item.id" :id="'schl_name_' + item.id" :value="item.schl_name">
                  </div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="50" style="width: 100px;" class="form-control-plaintext" :name="'schl_cndtn_code_' + item.id" :id="'schl_cndtn_code_' + item.id" :value="item.code_name">
                  </div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="50" style="width: 100px;"  class="form-control-plaintext" :name="'schl_dprtmnt_' + item.id" :id="'schl_dprtmnt_' + item.id" :value="item.schl_dprtmnt">
                  </div>
                  <div class="col-auto ms-auto">
                    <input type="text" readonly maxlength="100" style="width: 200px;" class="form-control-plaintext" :name="'schl_n_date_' + item.id" :id="'schl_n_date_' + item.id" :value="item.schl_admsn_date + ' ~ ' + item.schl_grdtn_date">
                  </div>
              </div>
            </div>
          </div>
          <div v-else>
            <label class="llabel">학력</label>
            <p>학력 정보가 없습니다.</p>
          </div>
        </div>
        <div v-else class="titem">
          <label class="llabel">학력</label>
          <p>학력 정보를 로드할 수 없습니다.</p>
        </div>

        <hr>

        <!-- 경력 정보 -->
        <div v-if="result.rsmcrInfo" class="titem">
          <div v-if="result.rsmcrInfo.length > 0">
            <label for="rsm_crr" class="llabel">경력</label>
            <div v-for="item in result.rsmcrInfo" :key="item.id">
              <div class="row g-4" id="rsm_crr">
                 <div class="col-auto flex-grow-1"></div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="100" class="form-control-plaintext" :name="'entrprs_name_' + item.id" :id="'entrprs_name_' + item.id" :value="item.entrprs_name">
                  </div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="50" style="width: 100px;" class="form-control-plaintext" :name="'entrprs_pstn_' + item.id" :id="'entrprs_pstn_' + item.id" :value="item.entrprs_pstn">
                  </div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="50" style="width: 200px;"  class="form-control-plaintext" :name="'entrprs_date_' + item.id" :id="'entrprs_date_' + item.id" :value="item.entrprs_jac_date + ' ~ ' + item.entrprs_rsgntn_date">
                  </div>
                  <div class="col-auto ms-auto">
                    <input type="text" readonly maxlength="100" style="width: 100px;" class="form-control-plaintext" :name="'entrprs_drtn_' + item.id" :id="'entrprs_drtn_' + item.id" :value="calculateDuration(item)">
                  </div>
              </div>
            </div>
          </div>
          <div v-else>
            <label class="llabel">경력</label>
            <p>경력 정보가 없습니다.</p>
          </div>
        </div>
        <div v-else class="titem">
          <label class="llabel">경력</label>
          <p>경력 정보를 로드할 수 없습니다.</p>
        </div>

        <hr>

        <!-- 기술 정보 -->
        <div v-if="result.rsmsklcdInfo" class="titem">
          <div v-if="result.rsmsklcdInfo.length > 0">
            <label for="rsm_skill" class="llabel">기술</label>
            <div id="rsm_skill">

              <div class="skitem">
                <div v-if="filteredLanguages.length">
                  <label for="rsm_skill_1" class="form-label">언어</label>
                  <div class="flex-container">
                    <div v-for="item in filteredLanguages" :key="item.skl_code_sq" class="flex-item">
                      <div id="rsm_skill_1">
                          <div >
                            <input type="text" readonly maxlength="100" class="form-control-plaintext" :name="'skl_mc_name_' + item.id" :id="'skl_mc_name_' + item.id" :value="item.skl_mc_name">
                          </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div v-else>
                  <label class="form-label">언어</label>
                  <p>언어 정보가 없습니다.</p>
                </div>
              </div>

              <div class="skitem">
                <div v-if="filteredFrameworks.length">
                  <label for="rsm_skill_2" class="form-label">프레임워크</label>
                  <div class="flex-container">
                    <div v-for="item in filteredFrameworks" :key="item.skl_code_sq" class="flex-item">
                      <div class="" id="rsm_skill_2">
                          <div class="">
                            <input type="text" readonly maxlength="100" class="form-control-plaintext" :name="'skl_mc_name_' + item.id" :id="'skl_mc_name_' + item.id" :value="item.skl_mc_name">
                          </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div v-else>
                  <label class="form-label">프레임워크</label>
                  <p>프레임워크 정보가 없습니다.</p>
                </div>
              </div>

              <div class="skitem">
                <div v-if="filteredTools.length">
                  <label for="rsm_skill_3" class="form-label">툴</label>
                  <div class="flex-container">
                    <div v-for="item in filteredTools" :key="item.skl_code_sq" class="flex-item">
                      <div class="" id="rsm_skill_3">
                          <div class="">
                            <input type="text" readonly maxlength="100" class="form-control-plaintext" :name="'skl_mc_name_' + item.id" :id="'skl_mc_name_' + item.id" :value="item.skl_mc_name">
                          </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div v-else>
                  <label class="form-label">툴</label>
                  <p>툴 정보가 없습니다.</p>
                </div>
              </div>

            </div>
          </div>
          <div v-else>
            <label class="llabel">기술</label>
            <p>기술 정보가 없습니다.</p>
          </div>
        </div>
        <div v-else class="titem">
          <label class="llabel">기술</label>
          <p>기술 정보를 로드할 수 없습니다.</p>
        </div>

        <hr>

        <!-- 자격/면허증 정보 -->
        <div v-if="result.rsmcrtfctInfo" class="titem">
          <div v-if="result.rsmcrtfctInfo.length > 0">
            <label for="rsm_crtfct" class="llabel">자격/면허증</label>
            <div v-for="item in result.rsmcrtfctInfo" :key="item.id">
              <div class="row g-4" id="rsm_crtfct">
                  <div class="col-auto flex-grow-1"></div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="100" class="form-control-plaintext" :name="'crtfct_name_' + item.id" :id="'crtfct_name_' + item.id" :value="item.crtfct_name">
                  </div>
                  <div class="col-auto">
                    <input type="text" readonly maxlength="100" class="form-control-plaintext" :name="'crtfct_isr_' + item.id" :id="'crtfct_isr_' + item.id" :value="item.crtfct_isr">
                  </div>
                  <div class="col-auto ms-auto">
                    <input type="text" readonly maxlength="100"  class="form-control-plaintext" :name="'crtfct_ad_' + item.id" :id="'crtfct_ad_' + item.id" :value="item.crtfct_ad">
                  </div>
              </div>
            </div>
          </div>
          <div v-else>
            <label class="llabel">자격/면허증</label>
            <p>자격/면허증 정보가 없습니다.</p>
          </div>
        </div>
        <div v-else class="titem">
          <label class="llabel">자격/면허증</label>
          <p>자격/면허증 정보를 로드할 수 없습니다.</p>
        </div>

        <hr>

        <!-- 자기소개서 정보 -->
        <div v-if="result.rsmsiInfo" class="titem">
          <div v-if="result.rsmsiInfo.length > 0">
            <label for="rsm_si" class="llabel">자기소개서</label>
            <div class="cards-container">
              <div v-for="item in result.rsmsiInfo" :key="item.id" class="card-item">
                <div id="rsm_si">
                  <div class="card">
                    <div class="card-body">
                      <h4 class="card-title mb-1 text-4 font-weight-bold" :name="'si_ttl_' + item.id" :id="'si_ttl_' + item.id">{{item.si_ttl}}</h4>
                      <p class="card-text" :name="'si_cntnt_' + item.id" :id="'si_cntnt_' + item.id">{{ item.si_cntnt }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else>
            <label class="llabel">자기소개서</label>
            <p>자기소개서 정보가 없습니다.</p>
          </div>
        </div>
        <div v-else class="titem">
          <label class="llabel">자기소개서</label>
          <p>자기소개서 정보를 로드할 수 없습니다.</p>
        </div>

        <hr>

        <!-- 첨부파일 정보 -->
        <div v-if="result.rsmatchmntInfo" class="titem">
          <div v-if="result.rsmatchmntInfo.length > 0">
            <label for="rsm_atchmnt" class="llabel">첨부파일</label>
            <div class="attachment-container">
              <div v-for="item in result.rsmatchmntInfo" :key="item.id" class="attachment-item">
                <div id="rsm_atchmnt">
                    <button @click="downloadImage(item.atchmnt_url, item.atchmnt_orgnl_fn)" id="bbtn" class="btn btn-outline btn-rounded btn-quaternary  btn-with-arrow mb-2">{{item.atchmnt_orgnl_fn}}<span><i class="fas fa-chevron-right"></i></span></button>
                </div>
              </div>
            </div>
          </div>
          <div v-else>
            <label class="llabel">첨부파일</label>
            <p>첨부파일 정보가 없습니다.</p>
          </div>
        </div>
        <div v-else class="titem">
          <label class="llabel">첨부파일</label>
          <p>첨부파일 정보를 로드할 수 없습니다.</p>
        </div>

        <hr>
      </div>
    </div>
  </template>
  
  <script setup>
    import { ref, onMounted, computed } from 'vue';
    import { useRoute } from 'vue-router';
    import { api } from '@/axios';  // API 인스턴스 임포트

    const route = useRoute();
    const result = ref(null);
  
    onMounted(async () => {
    try {
        const resSq = route.params.resSq;  // 라우터 파라미터에서 resSq 값 가져오기
        result.value = await api.$get(`/resumes/resume-detail/${resSq}`);
    } catch (error) {
        console.error('Failed to fetch resume details:', error);
    }
    });

    // computed로 각 항목별 필터링
    const filteredLanguages = computed(() => {
      return Array.isArray(result.value.rsmsklcdInfo)
        ? result.value.rsmsklcdInfo.filter(item => item.skl_lc_name === '언어')
        : [];
    });

    const filteredFrameworks = computed(() => {
      return Array.isArray(result.value.rsmsklcdInfo)
        ? result.value.rsmsklcdInfo.filter(item => item.skl_lc_name === '프레임워크')
        : [];
    });

    const filteredTools = computed(() => {
      return Array.isArray(result.value.rsmsklcdInfo)
        ? result.value.rsmsklcdInfo.filter(item => item.skl_lc_name === '툴')
        : [];
    });

    //일한 기간 
    const calculateDuration = (item) => {
      if (item.entrprs_rsgntn_date && item.entrprs_jac_date) {
        const gradDate = new Date(item.entrprs_rsgntn_date);
        const admDate = new Date(item.entrprs_jac_date);

        // 년도와 월 차이를 계산
        let yearsDiff = gradDate.getFullYear() - admDate.getFullYear();
        let monthsDiff = gradDate.getMonth() - admDate.getMonth();
        
        // 연도와 월 차이 조정
        if (monthsDiff < 0) {
          yearsDiff--;
          monthsDiff += 12;
        }

        if (yearsDiff > 0 && monthsDiff > 0) {
          return `총 ${yearsDiff}년 ${monthsDiff}개월`;
        } else if (yearsDiff > 0) {
          return `총 ${yearsDiff}년`;
        } else if (monthsDiff > 0) {
          return `총 ${monthsDiff}개월`;
        } else {
          return '';
        }
      }
      return ''; // 날짜가 없는 경우 빈 문자열 반환
    };

    // const downloadImage = async(url, originalFN) => {
    //   const filename = url.split('/').pop();
    //   console.log(originalFN);
    //   const response = await api.$get(`/file/${filename}`);
    //   console.log(response.ok);

    //   if (response){
    //     const blob = await response.blob();
    //     console.log("blob" + blob);
    //     const url = window.URL.createObjectURL(blob);

    //     const link = document.createElement('a');
    //     link.href = url;
    //     link.download = originalFN;
    //     document.body.appendChild(link);
    //     link.click();
    //     document.body.removeChild(link);
    //     window.URL.revokeObjectURL(url);
    //   }else{
    //     console.error('Image not found');
    //   }
    // }
    const downloadImage = async (url, originalFN) => {
    const filename = url.split('/').pop();
    console.log(originalFN);
    
    try {
        const response = await api.$get(`/file/${filename}`, { responseType: 'blob' }); // responseType을 blob으로 설정

        // 응답이 유효한지 확인
        if (response) {
            const blob = response; // response가 blob이면 바로 사용
            console.log("blob", blob);
            const objectUrl = window.URL.createObjectURL(blob);

            const link = document.createElement('a');
            link.href = objectUrl;
            link.download = originalFN;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(objectUrl);
        } else {
            console.error('Image not found');
        }
    } catch (error) {
        console.error('Error downloading image:', error);
    }
};
  
  </script>
  
  <style scoped>
  .mainContainer {
    padding-left: 10px;
    padding-right: 10px;
  }
  .adinfo{
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
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
  label{
    font-weight: bold;
  }
  input.form-control-plaintext {
    text-align: center;
  }

  /* 기술 옆으로 정렬 */
  .flex-container {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
  }

  .flex-item {
    flex: 0 1 auto;
    width: 150px;
  }

  /* 자기소개서 카드 */
  .cards-container {
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 10px;
  }

  .card-item {
    width: 100%;
    box-sizing: border-box;
  }

  .titem {
    margin-bottom: 80px;
    width: 100%;
  }
  .skitem{
    margin-bottom: 20px;
  }
  .llabel{
    margin-bottom: 50px;
    font-size: 30px;
  }
  
  /* 첨부파일 아이콘 */
  .attachment-container {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
    padding: 0;
  }
  
  .attachment-item {
    display: inline-block;
  }
  #bbtn{
    display: inline-block;
  }
  
  /* 이미지 중앙 정렬 */
  .call-to-action {
    display: flex;
    align-items: center;
  }

  .img-container {
    display: flex;
    align-items: center;
  }

  .img-thumbnail {
    display: flex;
    justify-content: center;
  }

  .row.g-4 {
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    margin-bottom: 1rem;
  }

  .col-auto {
    margin-right: 1rem;
  }

  .col-auto.ms-auto {
    margin-left: auto;
  }

  .col-auto.flex-grow-1 {
    flex-grow: 1; /* 빈 공간을 채우기 위한 div가 남은 공간을 채우도록 설정 */
  }
  
  </style>
  