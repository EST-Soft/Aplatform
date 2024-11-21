<template>
  <form
    id="completeForm"
    class="contact-form form-style-4 form-style-4-text-dark"
    @submit.prevent="submit"
  >
    <div class="row">
      <h2 class="form-group col-12">
        <strong> 이력서 등록 </strong>
      </h2>
      <hr class="gradient" />
    </div>
    <div class="form-section">
      <!-- 제목 입력 -->
      <input
        type="text"
        v-model="rsmTtl"
        maxlength="20"
        class="form-control custom-input"
        placeholder="제목"
        name="rsm_ttl"
        required
      />
    </div>
    <!-- 이미지 및 기본 정보 -->
    <div class="form-section">
      <div class="row">
        <div class="col-12 col-md-4 text-center">
          <section class="call-to-action mb-3">
            <div class="img-container">
              <div v-if="imageUrl === ''">
                <img
                  class="img-fluid"
                  src="@/assets/avatar.jpg"
                  alt="프로필 이미지"
                  @click="triggerFileInput"
                  style="cursor: pointer"
                />
              </div>
              <div v-else>
                <img
                  class="img-fluid"
                  :src="imageUrl"
                  alt="프로필 이미지"
                  @click="triggerFileInput"
                  style="cursor: pointer"
                />
              </div>
            </div>
            <!-- 숨겨진 파일 input (이미지 선택용) -->
            <input
              type="file"
              ref="fileInput"
              style="display: none"
              @change="changeImage"
            />
            <ResumeImageModalView
              :imageSrc="imageUrl"
              @change-image="changeImage"
              @update:imageSrc="updateImageUrl"
            />
          </section>
        </div>

        <!-- 기본 정보 입력 -->
        <div class="col-12 col-md-8">
          <div class="row">
            <div class="col-md-6">
              <input
                type="text"
                v-model="rsmName"
                maxlength="100"
                class="form-control"
                placeholder="이름"
                name="rsm_name"
                required
              />
            </div>
            <div class="col-md-6">
              <div class="d-flex justify-content-start">
                <div
                  class="form-check form-check-inline"
                  style="margin-right: 10px"
                >
                  <!-- 오른쪽 마진을 10px로 설정 -->
                  <input
                    class="form-check-input"
                    v-model="rsmGndrCode"
                    type="radio"
                    value="m"
                    name="rsm_gndr_cd"
                    required
                  />
                  <label class="form-check-label">남</label>
                </div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    v-model="rsmGndrCode"
                    type="radio"
                    value="f"
                    name="rsm_gndr_cd"
                    required
                  />
                  <label class="form-check-label">여</label>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-md-6">
              <input
                type="text"
                v-model="rsmBd"
                maxlength="100"
                class="form-control"
                placeholder="생년월일"
                required
              />
            </div>
            <div class="col-md-6">
              <input
                type="text"
                v-model="rsmMp"
                maxlength="100"
                class="form-control"
                placeholder="전화번호"
                required
              />
            </div>
          </div>

          <!-- 주소, 이메일 입력 -->
          <div class="row mt-3">
            <div class="col-md-6">
              <input
                type="text"
                v-model="rsmAdrs"
                maxlength="100"
                class="form-control"
                placeholder="주소"
                required
              />
            </div>
            <div class="col-md-6">
              <input
                type="email"
                v-model="rsmEml"
                maxlength="100"
                class="form-control"
                placeholder="이메일"
                required
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 희망연봉, 학력, 학점 -->
    <div class="form-section">
      <div class="row mt-3">
        <div class="col-md-4">
          <select
            class="form-select"
            v-model="salaryOption"
            required
            @change="handleSalaryOptionChange"
          >
            <option value="" selected>희망연봉</option>
            <option value="면접 후 결정">면접 후 결정</option>
            <option value="회사 내규에 따름">회사 내규에 따름</option>
            <option value="manual">직접입력</option>
          </select>
        </div>

        <!-- 희망연봉 입력란, "직접입력"을 선택한 경우에만 보이도록 -->
        <div class="col-md-4" v-if="salaryOption === 'manual'">
          <input
            type="number"
            v-model="rsmEs"
            class="form-control"
            placeholder="희망연봉을 입력하세요"
            required
          />
        </div>

        <div class="col-md-4">
          <select class="form-select" v-model="rsmFnlEdctnCode" required>
            <option value="" disabled>최종학력</option>
            <option
              v-for="education in educationOptions"
              :key="education.code"
              :value="education.code"
            >
              {{ education.name }}
            </option>
          </select>
        </div>

        <!-- 학점 입력란, 대학교 졸업부터 보이도록 설정 -->
        <div
          class="col-md-4"
          v-if="rsmFnlEdctnCode === 'jc' || rsmFnlEdctnCode === 'unvrsty'"
        >
          <input
            type="text"
            v-model="rsmGrd"
            maxlength="100"
            class="form-control"
            placeholder="학점"
            required
          />
        </div>
      </div>
    </div>

    <!-- 학력 추가 -->
    <div class="form-section">
      <h4 class="mb-3">
        학력
        <div class="btn btn-primary btn-circle" @click="addEducation">
          <i class="fa fa-plus"></i>
        </div>
        <hr class="mt-1 mb-2" />
      </h4>
      <div
        v-for="(education, index) in educationsList"
        :key="index"
        class="education-item"
      >
        <div class="row">
          <div class="col-md-4">
            <input
              type="text"
              :id="'schlName-' + index"
              class="form-control"
              v-model="education.schlName"
              placeholder="학교 이름"
              @click="openModal(index)"
            />
          </div>
          <div class="col-md-4">
            <select
              :id="'schlCndtnCode-' + index"
              class="form-select"
              v-model="education.schlCndtnCode"
            >
              <option value="" disabled>상태</option>
              <option value="grdtn">졸업</option>
              <option value="loa">휴학</option>
              <option value="atndng">재학</option>
              <option value="wfts">퇴학</option>
            </select>
          </div>
          <div class="col-md-4">
            <input
              type="text"
              :id="'schlDprtmnt-' + index"
              class="form-control"
              v-model="education.schlDprtmnt"
              placeholder="학과"
            />
          </div>
        </div>
        <div class="row mt-2">
          <div class="col-md-5">
            <input
              type="date"
              :id="'schlAdmsnDate-' + index"
              class="form-control"
              v-model="education.schlAdmsnDate"
            />
          </div>
          <div class="col-md-5">
            <input
              type="date"
              :id="'schlGrdtnDate-' + index"
              class="form-control"
              v-model="education.schlGrdtnDate"
            />
          </div>
          <div class="col-md-2 text-end">
            <button
              type="button"
              class="btn btn-danger"
              @click="removeEducation(index)"
            >
              삭제
            </button>
          </div>
        </div>
      </div>
      <SearchPopup
        :isVisible="isSearchPopupModalOpen"
        @setResult="handleSchoolSelection"
        @update:isVisible="isSearchPopupModalOpen = $event"
      />
    </div>

    <!-- 경력 추가 -->
    <div class="form-section">
      <h4 class="mb-3">
        경력 &nbsp;
        <!-- Add Career Button -->
        <div class="btn btn-primary btn-circle mb-2" @click="addCareer">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />

      <!-- Render career fields dynamically -->
      <div
        v-for="careerDatas in careerDatas"
        :key="careerDatas.id"
        class="career-item mb-3"
      >
        <div class="row">
          <!-- 회사 이름 -->
          <div class="col-md-4">
            <input
              type="text"
              v-model="careerDatas.entrprsName"
              placeholder="회사 이름"
              class="form-control"
            />
          </div>

          <!-- 직책 -->
          <div class="col-md-4">
            <input
              type="text"
              v-model="careerDatas.entrprsPstn"
              placeholder="직책"
              class="form-control"
            />
          </div>

          <!-- 시작일과 종료일 -->
          <div class="col-md-3 d-flex align-items-center">
            <div class="d-flex flex-column w-100">
              <!-- 입사일 입력 -->
              <input
                type="date"
                v-model="careerDatas.entrprsJacDate"
                class="form-control mb-2"
                placeholder="입사일"
              />
              <!-- 퇴사일 입력 -->
              <input
                type="date"
                v-model="careerDatas.entrprsRsgntnDate"
                class="form-control"
                placeholder="퇴사일"
              />

              <!-- 실시간 경력 계산 결과 표시 -->
              <div
                v-if="
                  careerDatas.entrprsJacDate && careerDatas.entrprsRsgntnDate
                "
              >
                <p>
                  경력: {{ calculatedDuration(careerDatas).years }}년
                  {{ calculatedDuration(careerDatas).months }}개월
                </p>
              </div>
            </div>
          </div>

          <!-- 삭제 버튼 -->
          <div class="row">
            <div class="col-md-6 d-flex justify-content-between">
              <button
                class="btn btn-danger flex-shrink-0"
                @click.stop="removeCareerComponents(careerDatas.id)"
                style="border-radius: 0.25rem"
              >
                삭제
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 스킬 추가 -->
    <div class="form-section">
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
                <div
                  class="btn btn-outline-danger"
                  @click="removeSkills(category, index)"
                >
                  X
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <SkillsResume
      :isVisible="showSkillsModal"
      :skillsData="skillsData"
      @update:isVisible="showSkillsModal = $event"
      @update:skillsData="updateSkillsData"
    />

    <!-- 자격증 추가 -->
    <div class="form-section">
      <h4 class="mb-0">
        자격/면허증 &nbsp;
        <div class="btn btn-primary btn-circle mb-2" @click="addComponents">
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div
        v-for="(certificateData, index) in certificateDatas"
        :key="certificateData.id"
        class="certificate-item mb-3 p-2 border rounded shadow-sm"
      >
        <div class="d-flex align-items-center mb-3 w-100">
          <input
            type="text"
            v-model="certificateData.crtfctName"
            placeholder="자격증 이름"
            class="form-control"
          />
          <button class="btn btn-primary ml-2" @click="openSearchModal(index)">
            검색
          </button>
          <input
            type="text"
            v-model="certificateData.crtfctIsr"
            placeholder="발행기관"
            class="form-control mt-2"
          />
          <input
            type="date"
            v-model="certificateData.crtfctAd"
            class="form-control mt-2"
          />
          <button
            class="btn btn-outline-danger mt-2"
            @click="removeComponents(certificateData.id)"
          >
            삭제
          </button>
        </div>
      </div>

      <SearchCertificates
        v-if="showSearchModal"
        @update:isVisible="showSearchModal = false"
        @setResult="updateCertificateName"
        :isVisible="showSearchModal"
      />
    </div>

    <!-- 자기소개서 -->
    <div class="col pb-3 pt-1">
      <h4 class="mb-0">
        자기소개서 &nbsp;
        <div
          class="btn btn-primary btn-circle mb-2"
          @click="addSelfIntroduction"
        >
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />

      <div
        v-for="(selfIntroductionData, index) in selfIntroductionDatas"
        :key="index"
      >
        <SelfintoductionsResume
          :index="index"
          :data="selfIntroductionData"
          @update="updateSelfIntroductionData"
          @remove="removeSelfIntroductionData"
        />
      </div>
    </div>

    <!-- 첨부파일 추가 -->
    <div class="form-section">
      <h4 class="mb-0">
        첨부파일 &nbsp;
        <div
          class="btn btn-primary btn-circle mb-2"
          @click="openAttachmentModal"
        >
          <i class="fa fa-plus"></i>
        </div>
      </h4>
      <hr class="mt-1 mb-2" />
      <div
        v-for="(attachment, index) in attachmentDatas"
        :key="index"
        class="attachment-item"
      >
        <p
          style="
            display: flex;
            flex: 1;
            width: 100%;
            justify-content: space-between;
            align-items: center;
          "
        >
          {{ attachment.name }}
          <button
            class="btn btn-outline-danger mt-3"
            @click="removeAttachment(index)"
          >
            삭제
          </button>
        </p>
      </div>

      <AttachmentsResume
        :isVisible="showAttachmentModal"
        :attachmentDatas="attachmentDatas"
        @update:isVisible="showAttachmentModal = $event"
        @update:attachmentDatas="updateAttachmentData"
      />
    </div>

    <!-- 저장/취소 버튼 -->
    <div class="col-lg-12">
      <div
        class="btn btn-outline btn-sm btn-primary mb-2 postBtn"
        @click="submitPost"
      >
        저장
      </div>
      <div
        class="btn btn-outline btn-sm btn-light mb-2 cancleBtn"
        @click="backToList"
      >
        취소
      </div>
      <button @click="testtest">테스트test</button>
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
const rsmFnlEdctnCode = ref("");
const salaryOption = ref("");
const rsmGrd = ref("");
const rsmEs = ref("");
const rsmTtl = ref("");
const rsmName = ref("");
const rsmGndrCode = ref("");
const rsmBd = ref("");
const rsmMp = ref("");
const rsmAdrs = ref("");
const rsmEml = ref("");
// 이미지

const imageUrl = ref("");
const imageDatas = ref("");
// 학력
const isSearchPopupModalOpen = ref(false);
const selectedEducationIndex = ref(null);
const educationsList = ref([]);
// 경력
const careerDatas = ref([]);
// 단일 경력 항목 객체

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
  { code: "hs", name: "고등학교 졸업" },
  { code: "jc", name: "대학교(2년제)" },
  { code: "unvrsty", name: "대학교(4년제)" },
  { code: "mid", name: "석사" },
  { code: "did", name: "박사" },
  // Add more options as needed
];
const addCareer = () => {
  careerDatas.value.push({
    id: careerDatas.value.length + 1, // Ensure unique ID for each career entry
    entrprsName: "",
    entrprsPstn: "",
    entrprsJacDate: "",
    entrprsRsgntnDate: "",
  });
};

// 첫페이지 입장시 정보 받아오기(최종학력 코드 리스트 , 성별 코드 리스트 , 스킬 코드리스트 )
onMounted(() => {
  console.log("온마운트");
});

const testtest = async () => {
  event.preventDefault();
  validateForm();
  validateEducationForm();
  validateCareerForm();
  validateCertificateForm();
  validateSelfIntroductionForm();

  const imageFormData = new FormData();
  if (imageDatas.value) {
    imageFormData.append("file", imageDatas.value);
    const response = await api
      .$post("/file/upload-image", imageFormData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
        return response;
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
    rsmImgOrgnlFn.value = response.imgOrgnlFn;
    rsmImgFileUrl.value = response.imgFileUrl;
  }

  const formData = new FormData();
  attachmentDatas.value.forEach((attachment) => {
    formData.append("file", attachment);
  });
  const attachmentList = [];

  skillsData.value = Object.values(skillsData.value).flat();

  console.log(formData);

  if (attachmentDatas.value.length != 0) {
    const response = await api
      .$post("http://localhost:80/file/upload-attachment", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
    console.log(response);
    const responseAttachment = response;

    if (Array.isArray(responseAttachment)) {
      responseAttachment.forEach((attachment) => {
        attachmentList.push({
          atchmntOrgnlFn: attachment.atchmntOrgnlFn,
          atchmntUrl: attachment.atchmntUrl,
        });
      });
    } else {
      console.error("Expected an array but received:", responseAttachment);
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
      rsmEml: rsmEml.value,
    },
    educationDtoList: educationsList.value,
    skilsDataDtoList: skillsData.value,
    certificateDtoList: certificateDatas.value,
    selfIntroductionDtoList: selfIntroductionDatas.value,
    careerDtoList: careerDatas.value,
    attachmentDtoList: attachmentList,
  });
};
const backToList = () => {
  window.location.href = `/resumes/resume-list`;
};

// form submit 함수
const submitPost = async () => {
  const isValidForm = validateForm();
  const isValidEducation =
    educationsList.value.length > 0 ? validateEducationForm() : true;
  const isValidCareer =
    careerDatas.value.length > 0 ? validateCareerForm() : true;
  const isValidCertificate =
    certificateDatas.value.length > 0 ? validateCertificateForm() : true;
  const isValidSelfIntroduction =
    selfIntroductionDatas.value.length > 0
      ? validateSelfIntroductionForm()
      : true;

  if (
    !isValidForm ||
    !isValidEducation ||
    !isValidCareer ||
    !isValidCertificate ||
    !isValidSelfIntroduction
  ) {
    console.log("Validation failed, aborting submission.");
    return; // 유효성 검사 실패 시 요청을 중단
  }

  const imageFormData = new FormData();
  if (imageDatas.value) {
    imageFormData.append("file", imageDatas.value);
    await api
      .$post("/file/upload-image", imageFormData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
        rsmImgOrgnlFn.value = response.imgOrgnlFn;
        rsmImgFileUrl.value = response.imgFileUrl;
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  }

  const formData = new FormData();
  attachmentDatas.value.forEach((attachment) => {
    formData.append("file", attachment);
  });
  const attachmentList = [];

  skillsData.value = Object.values(skillsData.value).flat();

  if (attachmentDatas.value.length != 0) {
    try {
      const response = await api
        .$post("/file/upload-attachment", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          console.log(response);
          return response;
        })
        .catch((error) => {
          console.error("Error: ", error);
        });

      const responseAttachment = response;

      if (Array.isArray(responseAttachment)) {
        responseAttachment.forEach((attachment) => {
          attachmentList.push({
            atchmntOrgnlFn: attachment.atchmntOrgnlFn,
            atchmntUrl: attachment.atchmntUrl,
          });
        });
      } else {
        console.error("Expected an array but received:", responseAttachment);
      }
    } catch (error) {
      console.log("Error: ", error);
    }
  }

  await api
    .$post(`/resumes/insert-resume?mbrSq=${store.state.member.mbrSq}`, {
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
        rsmEml: rsmEml.value,
      },
      educationDtoList: educationsList.value,
      skilsDataDtoList: skillsData.value,
      certificateDtoList: certificateDatas.value,
      selfIntroductionDtoList: selfIntroductionDatas.value,
      careerDtoList: careerDatas.value,
      attachmentDtoList: attachmentList,
    })
    .then((response) => {
      console.log(response);
      //educationsList.value = [];
      //clearForm();
      console.log("Response:", response);
      backToList();
    })
    .catch((error) => {
      console.error("Error:", error);
    });
};

// 이미지
// 파일 업로드 input을 클릭하도록 트리거하는 함수
const triggerFileInput = () => {
  const fileInput = document.querySelector('input[type="file"]'); // 파일 input 요소를 선택
  if (fileInput) {
    fileInput.click(); // 클릭 이벤트 트리거
  }
};

const updateImageUrl = (newImageUrl) => {
  //imageUrl.value = newImageUrl.imgFileUrl;
  rsmImgOrgnlFn.value = newImageUrl.imgOrgnlFn;
  rsmImgFileUrl.value = newImageUrl.imgFileUrl;
}; // updateImageUrl

// 파일이 선택되면 호출되는 함수
const changeImage = (event) => {
  const file = event.target.files[0]; // 선택된 파일 가져오기
  if (file) {
    const reader = new FileReader(); // FileReader를 사용하여 이미지 URL 생성

    reader.onload = () => {
      imageUrl.value = reader.result; // imageUrl 상태에 읽은 이미지 URL 저장
    };

    reader.readAsDataURL(file); // 파일을 Data URL로 변환
  }
};

// 학력
const openModal = (index) => {
  selectedEducationIndex.value = index;
  isSearchPopupModalOpen.value = true;
};

const addEducation = () => {
  educationsList.value.push({
    schlName: "",
    schlCndtnCode: "",
    schlDprtmnt: "",
    schlAdmsnDate: "",
    schlGrdtnDate: "",
  });
}; // addEducation

const handleSchoolSelection = (selectedSchool) => {
  if (selectedEducationIndex.value !== null) {
    educationsList.value[selectedEducationIndex.value].schlName =
      selectedSchool.schoolName;
  }
  isSearchPopupModalOpen.value = false;
  const inputId = "schlName-" + selectedEducationIndex.value;
  const inputElement = document.getElementById(inputId);
  console.log(inputElement);
  if (inputElement) {
    inputElement.focus();
  }
}; // handleSchoolSelection

const removeEducation = (index) => {
  educationsList.value.splice(index, 1);
}; // removeEducation

// 경력

// Method to calculate the experience in years and months

// 경험 업데이트 함수
// 경험 업데이트 함수
const calculateDuration = (entrprsJacDate, entrprsRsgntnDate) => {
  // 날짜 값이 비어 있으면 0년 0개월 반환
  if (!entrprsJacDate) return { years: 0, months: 0 };

  // 퇴사일이 없으면 현재 날짜를 사용
  const start = new Date(entrprsJacDate);
  const end = entrprsRsgntnDate ? new Date(entrprsRsgntnDate) : new Date(); // 퇴사일이 없으면 현재 날짜로 처리

  let years = end.getFullYear() - start.getFullYear();
  let months = end.getMonth() - start.getMonth();

  // 월 계산이 음수일 경우 연도를 하나 빼고, 월을 12로 보정
  if (months < 0) {
    years--;
    months += 12;
  }

  return { years, months };
};

// 실시간 경력 계산 (각 경력 항목에 대해 계산)
const calculatedDuration = (careerDatas) => {
  return calculateDuration(
    careerDatas.entrprsJacDate,
    careerDatas.entrprsRsgntnDate
  );
};

const removeCareerComponents = (id) => {
  const index = careerDatas.value.findIndex((career) => career.id === id);
  if (index !== -1) {
    careerDatas.value.splice(index, 1);
  }
};
// removeCareerComponents

// 스킬
const openSkillsModal = () => {
  showSkillsModal.value = true;
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
  certificateDatas.value.push({ crtfctName: "", crtfctIsr: "", crtfctAd: "" });
}; // addComponents

const openSearchModal = (index) => {
  selectedCertificateIndex.value = index;
  showSearchModal.value = true;
}; // openSearchModal

const updateCertificateName = (item) => {
  if (selectedCertificateIndex.value !== null) {
    certificateDatas.value[selectedCertificateIndex.value].crtfctName =
      item.종목명;
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
  selfIntroductionDatas.value.push({ siTtl: "", siCntnt: "" });
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

// Form Validation
const validateForm = () => {
  // Validate Title
  if (rsmTtl.value.trim() === "") {
    alert("제목을 입력하세요.");
    return false;
  }
  // Validate Name
  if (rsmName.value.trim() === "") {
    alert("이름을 입력하세요.");
    return false;
  }
  // Validate Gender
  if (rsmGndrCode.value.trim() === "") {
    alert("성별을 선택하세요.");
    return false;
  }
  // Validate Birth Date
  if (rsmBd.value.trim() === "" || !/^\d{8}$/.test(rsmBd.value.trim())) {
    alert("생년월일을 입력하세요. 8자리의 숫자만 입력 가능합니다.");
    return false;
  }
  // Validate Mobile Number
  if (rsmMp.value.trim() === "" || !/^\d{11}$/.test(rsmMp.value.trim())) {
    alert("전화번호를 입력하세요. 11자리의 숫자만 입력 가능합니다.");
    return false;
  }
  // Validate Address
  if (rsmAdrs.value.trim() === "") {
    alert("주소를 입력하세요.");
    return false;
  }
  // Validate Email
  if (
    rsmEml.value.trim() === "" ||
    !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(rsmEml.value.trim())
  ) {
    alert(
      rsmEml.value.trim() === ""
        ? "이메일을 입력하세요."
        : "유효한 이메일 주소를 입력하세요."
    );
    return false;
  }
  // Validate Salary if "manual" is selected
  if (salaryOption.value === "manual" && !rsmEs.value) {
    alert("희망연봉을 입력하세요. 숫자만 입력 가능합니다.");
    return false;
  }
  // Validate Education Selection
  if (rsmFnlEdctnCode.value.trim() === "") {
    alert("최종학력을 선택해주세요.");
    return false;
  }
  // Validate Grade Input if Required
  if (
    (rsmFnlEdctnCode.value === "jc" || rsmFnlEdctnCode.value === "unvrsty") &&
    (rsmGrd.value.trim() === "" || !/^\d+$/.test(rsmGrd.value.trim()))
  ) {
    alert("학점을 입력하세요. 숫자만 입력 가능합니다.");
    return false;
  }
  return true;
};

// Handle Salary Option Change
const handleSalaryOptionChange = () => {
  // Check if High School is selected
  const isHighschool = salaryOption.value === "hs";

  // If High School is selected, hide or disable Grade input
  if (isHighschool) {
    rsmGrd.value = ""; // Clear the grade value if high school is selected
  }
};

// Education Validation
const validateEducationForm = () => {
  for (const education of educationsList.value) {
    if (education.schlName.trim() === "") {
      alert("학교 이름을 입력해 주세요.");
      return false;
    }
    if (education.schlCndtnCode.trim() === "") {
      alert("학교 졸업 상태를 선택해 주세요.");
      return false;
    }
    if (education.schlDprtmnt.trim() === "") {
      alert("학과를 입력해 주세요.");
      return false;
    }
    if (education.schlAdmsnDate.trim() === "") {
      alert("입학 날짜를 입력해 주세요.");
      return false;
    }
    if (education.schlGrdtnDate.trim() === "") {
      alert("졸업 날짜를 입력해 주세요.");
      return false;
    }
  }
  return true;
};

const validateCareerForm = () => {
  for (const career of careerDatas.value) {
    if (career.entrprsName.trim() === "") {
      alert("회사 이름을 입력해 주세요.");
      return false;
    }
    if (career.entrprsPstn.trim() === "") {
      alert("직책을 입력해 주세요.");
      return false;
    }
    if (career.entrprsJacDate.trim() === "") {
      alert("입사일를 입력해 주세요.");
      return false;
    }
    if (career.entrprsRsgntnDate.trim() === "") {
      alert("퇴사일를 입력해 주세요.");
      return false;
    }
  }
  return true;
}; // validateCareerForm

const validateCertificateForm = () => {
  for (const certificate of certificateDatas.value) {
    if (certificate.crtfctName.trim() === "") {
      alert("자격증 이름을 입력해 주세요.");
      return false;
    }
    if (certificate.crtfctIsr.trim() === "") {
      alert("발행기관을 입력해 주세요.");
      return false;
    }
    if (certificate.crtfctAd.trim() === "") {
      alert("취득일을 입력해 주세요.");
      return false;
    }
  }
  return true;
}; // validateCertificateForm

const validateSelfIntroductionForm = () => {
  for (const selfIntroduction of selfIntroductionDatas.value) {
    if (selfIntroduction.siTtl.trim() === "") {
      alert("자기소개서 제목을 입력해 주세요.");
      return false;
    }
    if (selfIntroduction.siCntnt.trim() === "") {
      alert("자기소개서 내용을 입력해 주세요.");
      return false;
    }
  }
  return true;
}; // validateSelfIntroductionForm
</script>

<style scoped>
/* General Form Layout */
.contact-form {
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

/* Section spacing */
.form-section {
  margin-bottom: 30px;
}

/* Header style for sections */
h4 {
  font-size: 1.25rem;
  font-weight: bold;
  margin-bottom: 15px;
}

/* Input Field styling */
.custom-input {
  border: none !important;
  border-bottom: 1px solid #ced4da !important;
  outline: none !important;
  padding: 10px 0 !important;
  font-size: 16px;
  width: 100%;
  margin-top: 20px;
}

/* Form control input styles */
.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ced4da;
  border-radius: 5px;
  font-size: 1rem;
}

/* Adjust button style */
.btn {
  padding: 8px 12px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary,
.btn-outline-danger {
  border-radius: 5px;
}

/* Button hover effects */
.btn:hover,
.btn-outline-danger:hover {
  background-color: #e53935;
  color: white;
}

/* Image container */
.img-container {
  position: relative;
  width: 100%;
  max-width: 150px;
  margin: 0 auto;
}

.img-container img {
  width: 100%;
  height: auto;
  border-radius: 50%;
  cursor: pointer;
}

/* Date input fields */
input[type="date"] {
  padding: 12px;
  font-size: 1rem;
  border-radius: 5px;
  width: 100%;
  margin: 10px 0;
}

/* Container for dynamic elements (career, education) */
.career-item,
.education-item,
.certificate-item {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
}

.career-item .row,
.education-item .row,
.certificate-item .row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

/* Each column inside dynamic form elements */
.career-item .col-md-4,
.education-item .col-md-4,
.certificate-item .col-md-4 {
  padding: 10px;
  flex: 1 1 30%;
}

/* Buttons inside dynamic form elements */
.career-item .btn-danger,
.education-item .btn-danger,
.certificate-item .btn-outline-danger {
  background-color: #e53935;
  color: white;
  border-radius: 5px;
  padding: 8px 16px;
  cursor: pointer;
  margin-top: 10px;
}

.career-item .btn-danger:hover,
.education-item .btn-danger:hover,
.certificate-item .btn-outline-danger:hover {
  background-color: #c62828;
}

/* Alignment of sections with add button */
.career-item,
.education-item,
.certificate-item {
  display: flex;
  flex-direction: column;
}

.skill-container,
.career-item,
.education-item,
.certificate-item {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

/* Adjust row layout for inputs inside career/education/certificate */
.career-item .row,
.education-item .row,
.certificate-item .row {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 10px;
}

/* Form fields */
input[type="text"],
input[type="email"],
input[type="number"],
input[type="date"],
select {
  padding: 12px;
  font-size: 1rem;
  width: 100%;
  border: 1px solid #ced4da;
  border-radius: 5px;
  margin: 10px 0;
}

/* Styling for dynamic skill sections */
.skill-container {
  display: flex;
  flex-direction: column;
}

.skill-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 5px 0;
}

/* Skill item remove button */
.skill-item .btn-outline-danger {
  background-color: #e53935;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.skill-item .btn-outline-danger:hover {
  background-color: #c62828;
}

/* Modal button style */
.btn-circle {
  width: 35px;
  height: 35px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  padding: 0;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

.btn-circle i {
  font-size: 1rem;
}

/* Alignment for the file input */
input[type="file"] {
  display: none;
}

/* 모바일 뷰 */
@media (max-width: 767px) {
  .contact-form {
    padding: 15px;
  }

  .img-container {
    width: 120px;
    height: 120px;
  }

  .form-section {
    margin-bottom: 1.5rem;
  }

  .float-end {
    flex-direction: column;
    align-items: flex-start;
  }

  .btn-primary,
  .btn-outline {
    width: 100%;
    margin-bottom: 10px;
  }
}
</style>
