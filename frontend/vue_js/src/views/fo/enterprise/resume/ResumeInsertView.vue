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
          <input type="text" value="" v-model="rsmTtl" maxlength="20" class="form-control text-3 h-auto py-2"
            placeholder="제목" name="rsm_tl" required />
        </div>
      </div>
      <div class="row">
        <section class="call-to-action with-full-borders mb-2 mainContainer">
          <div class="col-sm-2 col-lg-2">
            <div class="col-sm-12 col-lg-12 imgContainer">
              <div class="img-thumbnail d-block">
                <!-- src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdraZyZ8CC81iPsZST1_TLB0SBOXC6wfvQ_Exx1uKkGsFpi4t_Mj4o8HhM_vPwI60yBF8&usqp=CAU" -->
                <img class="img-fluid"
                  :src="imageUrl ? imageUrl : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdraZyZ8CC81iPsZST1_TLB0SBOXC6wfvQ_Exx1uKkGsFpi4t_Mj4o8HhM_vPwI60yBF8&usqp=CAU'"
                  alt style="width: 200px" @click="showModal = true" />
                <div class="imgIcon" @click="delImg">
                  <i class="fa-regular fa-trash-can"></i>
                </div>
              </div>
            </div>
          </div>
          <ResumeImageModalView :isVisible="showModal" :imageSrc="imageUrl" @update:isVisible="showModal = $event"
            @update:imageSrc="updateImageUrl" />
          <div class="row col-sm-10 col-lg-10">
            <div class="col-sm-4 col-lg-4">
              <input type="text" value="" v-model="rsmName" maxlength="100" class="form-control text-3 h-auto py-2"
                placeholder="이름" name="rsm_name" required />
            </div>
            <div class="col-sm-3 col-lg-2">
              <div class="form-check form-check-inline">
                <label class="form-check-label">
                  <input class="form-check-input" v-model="rsmGndrCode" type="radio" id="rsm_gndr_cd1" value="m"
                    name="rsm_gndr_cd" required /> 남
                </label>
              </div>
              <div class="form-check form-check-inline">
                <label class="form-check-label">
                  <input class="form-check-input" v-model="rsmGndrCode" type="radio" id="rsm_gndr_cd2" value="f"
                    name="rsm_gndr_cd" _required /> 여
                </label>
              </div>
            </div>
            <div class="col-sm-2 col-lg-2">
              <input type="text" value="" v-model="rsmBd" maxlength="100" class="form-control text-3 h-auto py-2"
                name="rsm_bd" placeholder="생년월일" required />
            </div>
            <div class="col-sm-2 col-lg-4">
              <input type="text" value="" v-model="rsmMp" maxlength="100" class="form-control text-3 h-auto py-2"
                name="rsm_mp" placeholder="전화번호" required />
            </div>
            <hr class="mt-3 mb-1" />
            <div class="col-sm-2 col-lg-8">
              <input type="text" value="" v-model="rsmAdrs" maxlength="100" class="form-control text-3 h-auto py-2"
                name="rsm_adrs" placeholder="주소" required />
            </div>
            <div class="col-sm-3 col-lg-4">
              <input type="text" value="" v-on:mousedown="rsmEml" v-model="rsmEml" maxlength="100"
                class="form-control text-3 h-auto py-2" name="rsm_eml" placeholder="이메일" required />
            </div>
            <hr class="mt-3 mb-1" />
            <div class="col-sm-4 col-lg-3">
              <input type="text" value="" v-model="rsmEs" maxlength="100" class="form-control text-3 h-auto py-2"
                name="rsm_es" placeholder="희망연봉" required />
            </div>
            <div class="col-sm-3 col-lg-3">
              <select class="form-select form-control h-auto py-2" v-model="rsmFnlEdctnCode" required>
                <option value="" disabled>최종학력</option>
                <option v-for="education in educationOptions" :key="education.code" :value="education.code">
                  {{ education.name }}
                </option>
              </select>
            </div>
            <div class="col-sm-3 col-lg-3">
              <input type="text" value="" v-model="rsmGrd" maxlength="100" class="form-control text-3 h-auto py-2"
                name="rsm_grd" placeholder="학점" required />
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
        <div class="btn btn-primary btn-circle mb-2" @click="addEducation">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />

      <!--  -->
      <div v-for="(education, index) in educationsList" :key="index" class="row mb-3">
        <div class="col-md-12 mb-2">
          <div class="row">
            <div class="col-md-4">
              <label for="schlName-{{ index }}" class="form-label">학교 이름</label>
              <input type="text" :id="'schlName-' + index" class="form-control" v-model="education.schlName"
                @click="openModal(index)" placeholder="학교 이름" />
            </div>
            <div class="col-md-2">
              <label for="schlCndtnCode-{{ index }}" class="form-label">상태</label>
              <select id="schlCndtnCode-{{ index }}" class="form-select" v-model="education.schlCndtnCode">
                <option value="grdtn">졸업</option>
                <option value="loa">휴학</option>
                <option value="atndng">재학</option>
                <option value="wfts">퇴학</option>
              </select>
            </div>
            <div class="col-md-3">
              <label for="schlDprtmnt-{{ index }}" class="form-label">학과</label>
              <input type="text" id="schlDprtmnt-{{ index }}" class="form-control" v-model="education.schlDprtmnt"
                placeholder="학과" />
            </div>
          </div>
        </div>
        <div class="col-md-12">
          <div class="row">
            <div class="col-md-3">
              <label for="schlAdmsnDate-{{ index }}" class="form-label">입학 날짜</label>
              <input type="date" id="schlAdmsnDate-{{ index }}" class="form-control"
                v-model="education.schlAdmsnDate" />
            </div>
            <div class="col-md-3">
              <label for="schlGrdtnDate-{{ index }}" class="form-label">졸업 날짜</label>
              <input type="date" id="schlGrdtnDate-{{ index }}" class="form-control"
                v-model="education.schlGrdtnDate" />
            </div>
            <div class="col-md-6 text-end">
              <div type="button" class="btn btn-danger" @click="removeEducation(index)">삭제</div>
            </div>
          </div>
        </div>
      </div>

      <SearchPopup :isVisible="isSearchPopupModalOpen" @setResult="handleSchoolSelection"
        @update:isVisible="isSearchPopupModalOpen = $event" />
    </div>

    <!--       entrprsName: '', entrprsPstn: '', entrprsJacDate: '', entrprsRsgntnDate: ''  -->
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        경력 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="addCareer">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div v-for="(careerData, index) in careerDatas" :key="index" class="career-item">
        <div class="form-group">
          <label for="entrprsName-{{ index }}" class="form-label">회사 이름</label>
          <div class="input-group">
            <input type="text" v-model="careerData.entrprsName" placeholder="회사 이름" class="form-control" />
            <div class="btn btn-outline-secondary ml-2" @click="openSearchModal(index)">
              검색
            </div>
          </div>
        </div>

        <div class="form-group mt-3">
          <label for="entrprsPstn-{{ index }}" class="form-label">발행기관</label>
          <input type="text" v-model="careerData.entrprsPstn" placeholder="직책" class="form-control" />
        </div>

        <div class="form-group mt-3">
          <label for="entrprsJacDate-{{ index }}" class="form-label">입사일</label>
          <input type="date" v-model="careerData.entrprsJacDate" class="form-control" />
        </div>

        <div class="form-group mt-3">
          <label for="entrprsRsgntnDate-{{ index }}" class="form-label">퇴사일</label>
          <input type="date" v-model="careerData.entrprsRsgntnDate" class="form-control" />
        </div>

        <div class="btn btn-outline-danger mt-3" @click="removeCareerComponents(careerData.id)">
          삭제
        </div>
      </div>


    </div>

    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        스킬 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="openSkillsModal">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div v-for="(skill, category) in skillsData" :key="category">
        <div v-if="skill.length > 0">
          <h5>{{ category }}</h5>
          <div class="skill-container">
            <div v-for="(item, index) in skill" :key="index" class="skill-item">
              <div>
                <span>{{ item.sklScName }}</span>
                <div class="btn btn-outline-danger" @click="removeSkills(category, index)">X</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <SkillsResume :isVisible="showSkillsModal" :skillsData="skillsData" @update:isVisible="showSkillsModal = $event"
      @update:skillsData="updateSkillsData" />
    <!-- @updateSkills="handleUpdateSkills" -->

    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        자격/면허증 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="addComponents">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div v-for="(certificateData, index) in certificateDatas" :key="certificateData.id" class="certificate-item">
        <input type="text" v-model="certificateData.crtfctName" placeholder="자격증 이름" class="form-control" />
        <button class="btn btn-outline-secondary ml-2" @click="openSearchModal(index)">
          <!-- 회사 정보 api 없어서 자격증 api로 임시 -->
          검색
        </button>
        <input type="text" v-model="certificateData.crtfctIsr" placeholder="발행기관" class="form-control mt-2" />
        <input type="date" v-model="certificateData.crtfctAd" class="form-control mt-2" />
        <button class="btn btn-outline-danger mt-2" @click="removeComponents(certificateData.id)">
          삭제
        </button>
      </div>

      <SearchCertificates v-if="showSearchModal" @update:isVisible="showSearchModal = false"
        @setResult="updateCertificateName" :isVisible="showSearchModal" />
    </div>


    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        자기소개서 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="addSelfIntroduction">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div v-for="(selfIntroductionData, index) in selfIntroductionDatas" :key="index">
        <SelfintoductionsResume :index="index" :data="selfIntroductionData" @update="updateSelfIntroductionData"
          @remove="removeSelfIntroductionData" />
      </div>
    </div>

    <div class="col pb-3 pt-1">

      <h4 class="mb-0">
        첨부파일 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="openAttachmentModal">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div v-for="(attachment, index) in attachmentDatas" :key="index" class="attachment-item">
        <p style="display: flex; flex: 1; width: 100%; justify-content: space-between; align-items: center;">{{
          attachment.name }}
          <button class="btn btn-outline-danger mt-3" @click="removeAttachment(index)">
            삭제
          </button>
        </p>
      </div>
      <AttachmentsResume :isVisible="showAttachmentModal" :attachmentDatas="attachmentDatas"
        @update:isVisible="showAttachmentModal = $event" @update:attachmentDatas="updateAttachmentData" />

    </div>

    <div class="col-lg-12">
      <div class="float-end">
        <div class="btn btn-outline btn-xl btn-primary mb-2" @click="submitPost">저장</div>
        <div class="btn btn-outline btn-xl btn-light mb-2">취소</div>
        <button @click="testtest">테스트test</button>
      </div>
    </div>


  </form>
</template>

<script setup>
import { api } from "@/axios";
import AttachmentsResume from "@/components/fo/enterprise/resume/AttachmentsResume.vue";
import SelfintoductionsResume from "@/components/fo/enterprise/resume/SelfintoductionsResume.vue";
import SkillsResume from "@/components/fo/enterprise/resume/SkillsResume.vue";
import SearchPopup from "@/components/fo/user/common/SearchPopup.vue";
import SearchCertificates from "../../../../components/fo/user/common/SearchCertificates.vue";
import ResumeImageModalView from "./ResumeImageModalView.vue";
import { onMounted, ref } from "vue";
import store from "../../../../store";


const rsmImgOrgnlFn = ref(null);
const rsmImgFileUrl = ref(null);
const rsmFnlEdctnCode = ref('');
const rsmGrd = ref('');
const rsmEs = ref('');
const rsmTtl = ref('');
const rsmName = ref('');
const rsmGndrCode = ref('');
const rsmBd = ref('');
const rsmMp = ref('');
const rsmAdrs = ref('');
const rsmEml = ref('');
// 이미지
const showModal = ref(false);
const imageUrl = ref('');
// 학력
const isSearchPopupModalOpen = ref(false);
const selectedEducationIndex = ref(null);
const educationsList = ref([]);
// 경력
const careerDatas = ref([]);
// 스킬
const showSkillsModal = ref(false);
const skillsData = ref([]);
// 자격 면허증
const certificateDatas = ref([]);
// 자기소개서
const selfIntroductionDatas = ref([]);
// 첨부파일
const showAttachmentModal = ref(false);
const attachmentDatas = ref([]);

// 최종 학력 목록
const educationOptions = [
  { code: 'hs', name: '고등학교 졸업' },
  { code: 'jc', name: '대학교(2년제)' },
  { code: 'unvrsty', name: '대학교(4년제)' },
  { code: 'mid', name: '석사' },
  { code: 'did', name: '박사' },
  // Add more options as needed
];

// 첫페이지 입장시 정보 받아오기(최종학력 코드 리스트 , 성별 코드 리스트 , 스킬 코드리스트 )
onMounted(() => {
  console.log("온마운트");
});

/* const testtest = async () => {
  event.preventDefault();

  const formData = new FormData();
  attachmentDatas.value.forEach(attachment => {
    formData.append('file', attachment);
  })
  const attachmentList = [];

  skillsData.value = Object.values(skillsData.value).flat();

  console.log(formData)

  if (attachmentDatas.value.length != 0) {
    const response = await axios.post('http://localhost:80/file/upload-attachment', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then((response) => {
      console.log(response.data)
      return response.data;
    }).catch((error) => {
      console.error('Error: ', error)
    });
    console.log(response)
    const responseAttachment = response;


    if (Array.isArray(responseAttachment)) {
      responseAttachment.forEach(attachment => {
        attachmentList.push({
          atchmntOrgnlFn: attachment.atchmntOrgnlFn,
          atchmntUrl: attachment.atchmntUrl
        });
      });
    } else {
      console.error('Expected an array but received:', responseAttachment);
    }
  }

  console.log({
    resumeDataDTO: {
      rsmImgOrgnlFn: rsmImgOrgnlFn.value,
      rsmImgFileUrl: rsmImgFileUrl.value,
      rsmFnlEdctnCode: rsmFnlEdctnCode.value,
      rsmGrd: rsmGrd.value,
      rsmEs: rsmEs.value,
      rsmTtl: rsmTtl.value,
      rsmName: rsmName.value,
      rsmGndrCode: rsmGndrCode.value,
      rsmBd: rsmBd.value,
      rsmMp: rsmMp.value,
      rsmAdrs: rsmAdrs.value,
      rsmEml: rsmEml.value
    },
    educationDtoList: educationsList.value,
    skilsDataDtoList: skillsData.value,
    certificateDtoList: certificateDatas.value,
    selfIntroductionDtoList: selfIntroductionDatas.value,
    careerDtoList: careerDatas.value,
    attachmentDtoList: attachmentList
  })
} */


// form submit 함수
const submitPost = async () => {

  const formData = new FormData();
  attachmentDatas.value.forEach(attachment => {
    formData.append('file', attachment);
  })
  const attachmentList = [];

  skillsData.value = Object.values(skillsData.value).flat();

  if (attachmentDatas.value.length != 0) {
    try {
      const response = await api.$post('/file/upload-attachment', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((response) => {
        console.log(response)
        return response;
      }).catch((error) => {
        console.error('Error: ', error)
      });

      const responseAttachment = response;

      if (Array.isArray(responseAttachment)) {
        responseAttachment.forEach(attachment => {
          attachmentList.push({
            atchmntOrgnlFn: attachment.atchmntOrgnlFn,
            atchmntUrl: attachment.atchmntUrl
          });
        });
      } else {
        console.error('Expected an array but received:', responseAttachment);
      }

    } catch (error) {
      console.log('Error: ', error)
    }
  }


  await api.$post(`/resumes/insert-resume?mbrSq=${store.state.member.mbrSq}`,
    {
      resumeDataDTO: {
        rsmImgOrgnlFn: rsmImgOrgnlFn.value,
        rsmImgFileUrl: rsmImgFileUrl.value,
        rsmFnlEdctnCode: rsmFnlEdctnCode.value,
        rsmGrd: rsmGrd.value,
        rsmEs: rsmEs.value,
        rsmTtl: rsmTtl.value,
        rsmName: rsmName.value,
        rsmGndrCode: rsmGndrCode.value,
        rsmBd: rsmBd.value,
        rsmMp: rsmMp.value,
        rsmAdrs: rsmAdrs.value,
        rsmEml: rsmEml.value
      },
      educationDtoList: educationsList.value,
      skilsDataDtoList: skillsData.value,
      certificateDtoList: certificateDatas.value,
      selfIntroductionDtoList: selfIntroductionDatas.value,
      careerDtoList: careerDatas.value,
      attachmentDtoList: attachmentList
    },
  ).then(response => {
    console.log(response)
    //educationsList.value = [];
    //clearForm();

    console.log('Response:', response);
  })
    .catch(error => {
      console.error('Error:', error);
    });

}



/* const clearForm = () => {
  rsmImgFileUrl.value = null
  rsmFnlEdctnCode.value = null
  rsmGrd.value = ''
  rsmEs.value = ''
  rsmTtl.value = ''
  rsmName.value = ''
  rsmGndrCode.value = ''
  rsmBd.value = ''
  rsmMp.value = ''
  rsmAdrs.value = ''
  rsmEml.value = ''
}; */

// 이미지
const updateImageUrl = (newImageUrl) => {
  imageUrl.value = newImageUrl.imgFileUrl;
  rsmImgOrgnlFn.value = newImageUrl.imgOrgnlFn;
  rsmImgFileUrl.value = newImageUrl.imgFileUrl;
}; // updateImageUrl

const delImg = () => {
  imageUrl.value = '';
  rsmImgOrgnlFn.value = '';
  rsmImgFileUrl.value = '';
}; // delImg

const addEducation = () => {
  educationsList.value.push({
    schlName: '',
    schlCndtnCode: '',
    schlDprtmnt: '',
    schlAdmsnDate: '',
    schlGrdtnDate: '',
  });
};

// 학력
const openModal = (index) => {
  selectedEducationIndex.value = index;
  isSearchPopupModalOpen.value = true;
};

const handleSchoolSelection = (selectedSchool) => {
  if (selectedEducationIndex.value !== null) {
    educationsList.value[selectedEducationIndex.value].schlName = selectedSchool.schoolName;
  }
  isSearchPopupModalOpen.value = false;
  const inputId = 'schlName-' + selectedEducationIndex.value
  const inputElement = document.getElementById(inputId);
  console.log(inputElement)
  if (inputElement) {
    inputElement.focus();
  }
};

const removeEducation = (index) => {
  educationsList.value.splice(index, 1);
};

// 경력
const addCareer = () => {
  careerDatas.value.push({ entrprsName: '', entrprsPstn: '', entrprsJacDate: '', entrprsRsgntnDate: '' });
};
const removeCareerComponents = (index) => {
  careerDatas.value.splice(index, 1);
};

// 스킬
const openSkillsModal = () => {
  showSkillsModal.value = true
};

const updateSkillsData = (newSkills) => {
  skillsData.value = newSkills;
  //const allSelectedSkills = Object.values(newSkills).flat();
  //skillsData.value = allSelectedSkills;
};

const removeSkills = (category, index) => {
  skillsData.value[category].splice(index, 1);
};


/* const handleUpdateSkills = (selectedSkills) => {
  const allSelectedSkills = Object.values(selectedSkills).flat();
  skillsData.value = allSelectedSkills;
}; */

// 자격증
const showSearchModal = ref(false);
const selectedCertificateIndex = ref(null);
// 입력창 생성
const addComponents = () => {
  certificateDatas.value.push({ crtfctName: '', crtfctIsr: '', crtfctAd: '' });
};
// 검색 모달 열기
const openSearchModal = (index) => {
  selectedCertificateIndex.value = index;
  showSearchModal.value = true;
};
// 자격증 검색 결과 업데이트
const updateCertificateName = (item) => {
  if (selectedCertificateIndex.value !== null) {
    certificateDatas.value[selectedCertificateIndex.value].crtfctName = item.종목명;
  }
  showSearchModal.value = false;
};
const removeComponents = (event) => {
  let index = 0;

  certificateDatas.value.forEach((certificateData, dataIndex) => {
    if (certificateData.id == event) {
      index = dataIndex;
      return;
    }
  });

  certificateDatas.value.splice(index, 1);
};
// 자기소개서
// 항목 추가
const addSelfIntroduction = () => {
  selfIntroductionDatas.value.push({ siTtl: '', siCntnt: '' });
}; // addSelfIntroduction
// 업데이트
const updateSelfIntroductionData = (index, updatedData) => {
  selfIntroductionDatas.value[index] = updatedData;
}; // updateSelfIntroduction
// 삭제
const removeSelfIntroductionData = (index) => {
  selfIntroductionDatas.value.splice(index, 1);
}; // removeSelfIntroduction

// 첨부파일
const openAttachmentModal = () => {
  showAttachmentModal.value = true;
};

const updateAttachmentData = (newAttachments) => {
  attachmentDatas.value = newAttachments;
};

const removeAttachment = (index) => {
  attachmentDatas.value.splice(index, 1);
};

</script>

<style scoped>
.modal {
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

.modal-dialog {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  max-width: 900px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-body {
  padding: 15px 0;
}

.form-control {
  margin-bottom: 10px;
}

.btn-close {
  border: none;
  background: transparent;
  font-size: 1.5rem;
}

.btn-danger {
  background-color: #dc3545;
  color: #fff;
  border: none;
}

.btn-circle {
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
}

.certificate-item,
.career-item,
.attachment-item {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.certificate-item input[type="text"],
.certificate-item input[type="date"],
.career-item input[type="text"],
.career-item input[type="date"],
.attachment-item input[type="text"] {
  margin-right: 0.5rem;
  flex: 1;
}

.input-group .btn {
  margin-left: 0.5rem;
}

.certificate-item button,
.career-item button,
.attachment-item button {
  margin-left: 0.5rem;
}

.modal-dialog {
  max-width: 600px;
}

.skill-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.skill-item {
  display: flex;
  align-items: center;
  background-color: #f8f9fa;
  border: 1px solid #ccc;
  padding: 5px;
  border-radius: 5px;
}

.skill-item .btn {
  padding: 3px 5px;
  border-radius: 3px;
  margin-left: 15px;
}

.form-control {
  width: 180px !important;
}
</style>