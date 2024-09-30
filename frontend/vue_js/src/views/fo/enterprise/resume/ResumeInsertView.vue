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
            placeholder="제목" name="rsm_ttl" required />
        </div>
      </div>
      <div class="row">
        <section class="call-to-action with-full-borders mb-2 mainContainer">
          <div class="col-sm-2 col-lg-2">
            <div class="col-sm-12 col-lg-12 imgContainer">
              <div class="img-thumbnail d-block">
                <div v-if="imageUrl == ''">
                  <img class="img-fluid" src="@/assets/avatar.jpg" alt style="width: 200px" @click="showModal = true" />
                </div>
                <div v-else>
                  <img class="img-fluid" :src="imageUrl" alt style="width: 200px" @click="showModal = true" />
                  <div class="imgIcon" @click="delImg">
                    <i class="fa-regular fa-trash-can"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <ResumeImageModalView :isVisible="showModal" :imageSrc="imageUrl" @update:isVisible="showModal = $event"
            @change-image="changeimage" @update:imageSrc="updateImageUrl" />
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
              <input type="email" value="" v-model="rsmEml" maxlength="100" class="form-control text-3 h-auto py-2"
                name="rsm_eml" placeholder="이메일" required />
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

      <!-- 학력 -->
      <div v-for="(education, index) in educationsList" :key="index"
        class="education-item mb-3 p-2 border rounded shadow-sm">
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
                <option value="" disabled>상태</option>
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
            <div class="col-md-5">
              <label for="schlAdmsnDate-{{ index }}" class="form-label">입학 날짜</label>
              <input type="date" id="schlAdmsnDate-{{ index }}" class="form-control"
                v-model="education.schlAdmsnDate" />
            </div>
            <div class="col-md-5">
              <label for="schlGrdtnDate-{{ index }}" class="form-label">졸업 날짜</label>
              <input type="date" id="schlGrdtnDate-{{ index }}" class="form-control"
                v-model="education.schlGrdtnDate" />
            </div>
            <div class="col-md-2 text-end">
              <div type="button" class="btn btn-danger" @click="removeEducation(index)">삭제</div>
            </div>
          </div>
        </div>
      </div>

      <SearchPopup :isVisible="isSearchPopupModalOpen" @setResult="handleSchoolSelection"
        @update:isVisible="isSearchPopupModalOpen = $event" />
    </div>

    <!-- 경력 -->
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        경력 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="addCareer">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div v-for="(careerData, index) in careerDatas" :key="index"
        class="career-item mb-3 p-2 border rounded shadow-sm">
        <div class="">
          <div class="d-flex align-items-center mb-3 w-100">
            <input type="text" v-model="careerData.entrprsName" placeholder="회사 이름"
              class="form-control me-2 flex-grow-1" style="border-radius: 0.25rem;" />
            <button class="btn btn-primary flex-shrink-0" @click="openSearchCareerModal"
              style="border-radius: 0.25rem;">
              검색
            </button>
            <input type="text" v-model="careerData.entrprsPstn" placeholder="직책" class="form-control ms-2 flex-grow-1"
              style="border-radius: 0.25rem;" />
          </div>
          <div class="d-flex align-items-center mb-3 w-100">
            <input type="date" v-model="careerData.entrprsJacDate" class="form-control me-2 flex-grow-1"
              style="border-radius: 0.25rem;" />
            <input type="date" v-model="careerData.entrprsRsgntnDate" class="form-control me-2 flex-grow-1"
              style="border-radius: 0.25rem;" />
            <button class="btn btn-danger flex-shrink-0" @click="removeCareerComponents(careerData.id)"
              style="border-radius: 0.25rem;">
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 스킬 -->
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

    <!-- 자격/면허증 -->
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        자격/면허증 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="addComponents">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div v-for="(certificateData, index) in certificateDatas" :key="certificateData.id"
        class="certificate-item  mb-3 p-2 border rounded shadow-sm">
        <div class="d-flex align-items-center mb-3 w-100"><input type="text" v-model="certificateData.crtfctName"
            placeholder="자격증 이름" class="form-control" />
          <button class="btn btn-primary ml-2" @click="openSearchModal(index)">
            <!-- 회사 정보 api 없어서 자격증 api로 임시 -->
            검색
          </button>
          <input type="text" v-model="certificateData.crtfctIsr" placeholder="발행기관" class="form-control mt-2" />
          <input type="date" v-model="certificateData.crtfctAd" class="form-control mt-2" />
          <button class="btn btn-outline-danger mt-2" @click="removeComponents(certificateData.id)">
            삭제
          </button>
        </div>

      </div>

      <SearchCertificates v-if="showSearchModal" @update:isVisible="showSearchModal = false"
        @setResult="updateCertificateName" :isVisible="showSearchModal" />
    </div>

    <!-- 자기소개서 -->
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

    <!-- 첨부파일 -->
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
        <div class="btn btn-outline btn-xl btn-light mb-2" @click="backToList">취소</div>
        <!-- <button @click="testtest">테스트test</button> -->
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

// 기본 정보
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
const imageDatas = ref('');
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
  validateForm();
  validateEducationForm();
  validateCareerForm();
  validateCertificateForm();
  validateSelfIntroductionForm();

  const imageFormData = new FormData();
  if (imageDatas.value) {
    imageFormData.append('file', imageDatas.value);
    const response = await api.$post('/file/upload-image', imageFormData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then((response) => {
      console.log(response)
      return response;
    }).catch((error) => {
      console.error('Error: ', error)
    });
    rsmImgOrgnlFn.value = response.imgOrgnlFn;
    rsmImgFileUrl.value = response.imgFileUrl;
  }

  const formData = new FormData();
  attachmentDatas.value.forEach(attachment => {
    formData.append('file', attachment);
  })
  const attachmentList = [];

  skillsData.value = Object.values(skillsData.value).flat();

  console.log(formData)

  if (attachmentDatas.value.length != 0) {
    const response = await api.$post('http://localhost:80/file/upload-attachment', formData, {
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
const backToList = () => {
  window.location.href = `/resumes/resume-list`
}

// form submit 함수
const submitPost = async () => {
  const isValidForm = validateForm();
  const isValidEducation = educationsList.value.length > 0 ? validateEducationForm() : true;
  const isValidCareer = careerDatas.value.length > 0 ? validateCareerForm() : true;
  const isValidCertificate = certificateDatas.value.length > 0 ? validateCertificateForm() : true;
  const isValidSelfIntroduction = selfIntroductionDatas.value.length > 0 ? validateSelfIntroductionForm() : true;

  if (!isValidForm || !isValidEducation || !isValidCareer || !isValidCertificate || !isValidSelfIntroduction) {
    console.log('Validation failed, aborting submission.');
    return; // 유효성 검사 실패 시 요청을 중단
  }

  const imageFormData = new FormData();
  if (imageDatas.value) {
    imageFormData.append('file', imageDatas.value);
    const response = await api.$post('/file/upload-image', imageFormData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then((response) => {
      console.log(response)
      window.location.href = `/resumes/resume-list`
    }).catch((error) => {
      console.error('Error: ', error)
    });
    rsmImgOrgnlFn.value = response.imgOrgnlFn;
    rsmImgFileUrl.value = response.imgFileUrl;
  }

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
    backToList();
  })
    .catch(error => {
      console.error('Error:', error);
    });

}

// 이미지
const updateImageUrl = (newImageUrl) => {
  //imageUrl.value = newImageUrl.imgFileUrl;
  rsmImgOrgnlFn.value = newImageUrl.imgOrgnlFn;
  rsmImgFileUrl.value = newImageUrl.imgFileUrl;
}; // updateImageUrl

const changeimage = (file) => {
  imageDatas.value = file;
  if (imageDatas.value) {
    const reader = new FileReader();
    reader.onloadend = () => {
      imageUrl.value = reader.result;
    }
    reader.readAsDataURL(file)
  }
  console.log(imageDatas)
} // changeimage

const delImg = () => {
  imageUrl.value = '';
  rsmImgOrgnlFn.value = '';
  rsmImgFileUrl.value = '';
}; // delImg

// 학력
const openModal = (index) => {
  selectedEducationIndex.value = index;
  isSearchPopupModalOpen.value = true;
};

const addEducation = () => {
  educationsList.value.push({
    schlName: '',
    schlCndtnCode: '',
    schlDprtmnt: '',
    schlAdmsnDate: '',
    schlGrdtnDate: '',
  });
}; // addEducation

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
}; // handleSchoolSelection

const removeEducation = (index) => {
  educationsList.value.splice(index, 1);
}; // removeEducation

// 경력
const addCareer = () => {
  careerDatas.value.push({ entrprsName: '', entrprsPstn: '', entrprsJacDate: '', entrprsRsgntnDate: '' });
}; // addCareer

const openSearchCareerModal = () => {
  alert('현재 경력 부분 api가 없습니다. 직접 입력해주세요.')
} // openSearchCareerModal

const removeCareerComponents = (index) => {
  careerDatas.value.splice(index, 1);
}; // removeCareerComponents

// 스킬
const openSkillsModal = () => {
  showSkillsModal.value = true
}; // openSkillsModal

const updateSkillsData = (newSkills) => {
  skillsData.value = newSkills;
}; // updateSkillsData

const removeSkills = (category, index) => {
  skillsData.value[category].splice(index, 1);
}; // removeSkills

// 자격증
const showSearchModal = ref(false);
const selectedCertificateIndex = ref(null);
// 입력창 생성
const addComponents = () => {
  certificateDatas.value.push({ crtfctName: '', crtfctIsr: '', crtfctAd: '' });
}; // addComponents

const openSearchModal = (index) => {
  selectedCertificateIndex.value = index;
  showSearchModal.value = true;
}; // openSearchModal

const updateCertificateName = (item) => {
  if (selectedCertificateIndex.value !== null) {
    certificateDatas.value[selectedCertificateIndex.value].crtfctName = item.종목명;
  }
  showSearchModal.value = false;
}; // updateCertificateName

const removeComponents = (event) => {
  let index = 0;

  certificateDatas.value.forEach((certificateData, dataIndex) => {
    if (certificateData.id == event) {
      index = dataIndex;
      return;
    }
  });

  certificateDatas.value.splice(index, 1);
}; // removeComponents

// 자기소개서
const addSelfIntroduction = () => {
  selfIntroductionDatas.value.push({ siTtl: '', siCntnt: '' });
}; // addSelfIntroduction

const updateSelfIntroductionData = (index, updatedData) => {
  selfIntroductionDatas.value[index] = updatedData;
}; // updateSelfIntroduction

const removeSelfIntroductionData = (index) => {
  selfIntroductionDatas.value.splice(index, 1);
}; // removeSelfIntroduction

// 첨부파일
const openAttachmentModal = () => {
  showAttachmentModal.value = true;
}; // openAttachmentModal

const updateAttachmentData = (newAttachments) => {
  attachmentDatas.value = newAttachments;
}; // updateAttachmentData

const removeAttachment = (index) => {
  attachmentDatas.value.splice(index, 1);
}; // removeAttachment

const validateForm = () => {
  if (rsmTtl.value.trim() === '') {
    alert("제목을 입력하세요.");
    return false;
  }
  if (rsmName.value.trim() === '') {
    alert("이름을 입력하세요.");
    return false;
  }
  if (rsmGndrCode.value.trim() === '') {
    alert("성별을 선택하세요.");
    return false;
  }
  if (rsmBd.value.trim() === '' || !/^\d{8}$/.test(rsmBd.value.trim())) {
    alert("생년월일을 입력하세요. 8자리의 숫자만 입력 가능합니다.");
    return false;
  }
  if (rsmMp.value.trim() === '' || !/^\d{11}$/.test(rsmMp.value.trim())) {
    alert("전화번호를 입력하세요. 11자리의 숫자만 입력 가능합니다.");
    return false;
  }
  if (rsmAdrs.value.trim() === '') {
    alert("주소을 입력하세요.");
    return false;
  }
  if (rsmEml.value.trim() === '' || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(rsmEml.value.trim())) {
    alert(rsmEml.value.trim() === '' ? "이메일을 입력하세요." : "유효한 이메일 주소를 입력하세요.");
    return false;
  }
  if (rsmEs.value.trim() === '' || !/^\d+$/.test(rsmEs.value.trim())) {
    alert("희망연봉을 입력하세요. 숫자만 입력 가능합니다.");
    return false;
  }
  if (rsmFnlEdctnCode.value.trim() === '') {
    alert("최종학력을 선택해주세요.");
    return false;
  }
  if (rsmGrd.value.trim() === '' || !/^\d+$/.test(rsmGrd.value.trim())) {
    alert("학점을 입력하세요. 숫자만 입력 가능합니다.");
    return false;
  }
  return true;
} // validateForm

const validateEducationForm = () => {
  for (const education of educationsList.value) {
    if (education.schlName.trim() === '') {
      alert("학교 이름을 입력해 주세요.");
      return false;
    }
    if (education.schlCndtnCode.trim() === '') {
      alert("학교 졸업 상태를 선택해 주세요.");
      return false;
    }
    if (education.schlDprtmnt.trim() === '') {
      alert("학과를 입력해 주세요.");
      return false;
    }
    if (education.schlAdmsnDate.trim() === '') {
      alert("입학 날짜를 입력해 주세요.");
      return false;
    }
    if (education.schlGrdtnDate.trim() === '') {
      alert("졸업 날짜를 입력해 주세요.");
      return false;
    }
  }
  return true;
} // validateEducationForm

const validateCareerForm = () => {
  for (const career of careerDatas.value) {
    if (career.entrprsName.trim() === '') {
      alert("회사 이름을 입력해 주세요.");
      return false;
    }
    if (career.entrprsPstn.trim() === '') {
      alert("직책을 입력해 주세요.");
      return false;
    }
    if (career.entrprsJacDate.trim() === '') {
      alert("입사일를 입력해 주세요.");
      return false;
    }
    if (career.entrprsRsgntnDate.trim() === '') {
      alert("퇴사일를 입력해 주세요.");
      return false;
    }
  }
  return true;
} // validateCareerForm

const validateCertificateForm = () => {
  for (const certificate of certificateDatas.value) {
    if (certificate.crtfctName.trim() === '') {
      alert("자격증 이름을 입력해 주세요.");
      return false;
    }
    if (certificate.crtfctIsr.trim() === '') {
      alert("발행기관을 입력해 주세요.");
      return false;
    }
    if (certificate.crtfctAd.trim() === '') {
      alert("취득일을 입력해 주세요.");
      return false;
    }
  }
  return true;
} // validateCertificateForm

const validateSelfIntroductionForm = () => {
  for (const selfIntroduction of selfIntroductionDatas.value) {
    if (selfIntroduction.siTtl.trim() === '') {
      alert("자기소개서 제목을 입력해 주세요.");
      return false;
    }
    if (selfIntroduction.siCntnt.trim() === '') {
      alert("자기소개서 내용을 입력해 주세요.");
      return false;
    }
  }
  return true;
} // validateSelfIntroductionForm

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