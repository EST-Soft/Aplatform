<template>
  <section id="examples" class="section section-default">
    <div class="container">
      <section class="card card-admin">
        <header class="card-header">
          <div class="card-actions">
            <a
              href="#"
              class="card-action card-action-toggle"
              data-card-toggle=""
            ></a>
            <a
              href="#"
              class="card-action card-action-dismiss"
              data-card-dismiss=""
            ></a>
          </div>
          <h2 class="card-title">공고</h2>
        </header>
      </section>

      <div v-if="loading" class="text-center">Loading...</div>
      <div v-else>
        <!-- 첫 번째 줄 -->
        <div class="row mt-3">
          <div class="col-md-3">
            <div class="mb-3">
              <label for="jbpTtl" class="form-label">제목</label>
              <input
                type="text"
                v-model="jbp.jbpTtl"
                class="form-control"
                id="jbpTtl"
                placeholder="제목"
                readonly
              />
            </div>
          </div>
          <div class="col-md-3">
            <div class="mb-3">
              <label for="enterpriseName" class="form-label">기업 이름</label>
              <input
                type="text"
                v-model="jbp.enterpriseName"
                class="form-control"
                id="enterpriseName"
                placeholder="기업 이름"
                readonly
              />
            </div>
          </div>
          <div class="col-md-2">
            <div class="mb-3">
              <label class="form-label">조회수: {{ jbp.hits }}</label>
            </div>
          </div>
          <div class="col-md-4">
            <div class="mb-3">
              <label class="form-label"
                >접수시작일: {{ formatDate(jbp.regstrStrtDtm) }}</label
              >
            </div>
          </div>
        </div>

        <!-- 두 번째 줄 -->
        <div class="row mt-3">
          <div class="col-md-4">
            <div class="mb-3">
              <label for="crrDrtn" class="form-label">경력</label>
              <input
                type="text"
                :value="careerText"
                class="form-control"
                id="crrDrtn"
                placeholder="경력"
                readonly
              />
            </div>
          </div>
          <div class="col-md-4">
            <div class="mb-3">
              <label for="edctn" class="form-label">학력</label>
              <input
                type="text"
                :value="educationText"
                class="form-control"
                id="edctn"
                placeholder="학력"
                readonly
              />
            </div>
          </div>
          <div class="col-md-4">
            <div class="mb-3">
              <label for="workArea" class="form-label">근무지역</label>
              <input
                type="text"
                v-model="jbp.workArea"
                class="form-control"
                id="workArea"
                placeholder="근무지역"
                readonly
              />
            </div>
          </div>
        </div>

        <!-- 세 번째 줄 -->
        <div class="row mt-3">
          <div class="col-md-4">
            <div class="mb-3">
              <label for="workForm" class="form-label">근무형태</label>
              <input
                type="text"
                v-model="jbp.workForm"
                class="form-control"
                id="workForm"
                placeholder="근무형태"
                readonly
              />
            </div>
          </div>
          <div class="col-md-4">
            <div class="mb-3">
              <label for="slry" class="form-label">급여</label>
              <input
                type="text"
                v-model="jbp.slry"
                class="form-control"
                id="slry"
                placeholder="급여"
                readonly
              />
            </div>
          </div>
          <div class="col-md-4">
            <div class="mb-3">
              <label for="slry" class="form-label">모집 직군</label>
              <input
                type="text"
                v-model="jbp.jobName"
                class="form-control"
                id="jobName"
                placeholder="직종"
                readonly
              />
            </div>
          </div>
        </div>
        <div class="col-md-12">
          <div class="mb-1">
            <label for="jbpCntnt" class="form-label">공고 내용</label>
            <QuillEditorComponent
              v-model="jbp.jbpCntnt"
              :isReadOnly="isEditable"
            />
          </div>
        </div>

        <!-- 입사지원 버튼 -->
        <div v-show="isMember" class="row mt-3">
          <div class="col d-flex justify-content-end">
            <button class="btn btn-primary" @click="applyJob">입사지원</button>
          </div>
        </div>

        <!-- 수정, 삭제 버튼 -->
        <div v-show="checkEnter" class="row mt-3">
          <div class="col d-flex justify-content-end">
            <button class="btn btn-success" @click="goUpdatePage(jbp)">
              수정
            </button>
            <button class="btn btn-danger ms-2" @click="confirmDelete">
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { api } from "@/axios.js";
import { useRoute, useRouter } from "vue-router";
import QuillEditorComponent from "@/components/common/Editor.vue";
import { useStore } from "vuex";

const store = useStore();
const isEditable = ref(true);

const route = useRoute();
const router = useRouter();

const loading = ref(true);

const isMember = computed(() => {
  return store.getters.getMember?.mbrSq != null;
});

// const jobId = route.params.jbpSq;

const checkEnter = ref(false);

const enterCheck = () => {
  if (store.getters.getMember?.pk == jbp.value.entrprsSq) {
    checkEnter.value = true;
  }
};

const jbp = ref({
  jbpSq: 0,
  entrprsSq: 0,
  enterpriseName: "",
  jbpTtl: "",
  jbpCntnt: "",
  hits: 0,
  crrDrtn: "",
  edctn: "",
  workArea: "",
  jobName: "",
  workForm: "",
  slry: "",
  regstrStrtDtm: "",
});

const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const fetchJobPostingDetail = async () => {
  const jbpSq = route.params.jbpSq;
  console.log(
    "Fetching job posting detail from: ",
    `/board/detail/jobPosting/${jbpSq}`
  );

  try {
    const response = await api.$get(`/board/detail/jobPosting/${jbpSq}`);
    jbp.value = response;
    console.log("Job posting detail fetched:", response);

    enterCheck(); // 다른 필요한 작업이 있다면 실행

    // 최근 본 공고를 DB에 저장하는 API 호출
    if (isMember.value) {
      const mbrSq = store.getters.getMember?.mbrSq;
      const mbrId = store.getters.getMember?.mbrId;

      if (mbrSq && mbrId) {
        try {
          // POST 요청으로 변경 (page와 size도 포함)
          const postResponse = await api.$post("/user/mypage/jobView", {
            mbrSq,
            jbpSq,
            mbrId,
            page: 1, // 필요한 page 값 설정
            size: 10, // 필요한 size 값 설정
          });
          console.log("최근 본 공고 DB에 저장 완료", postResponse.data);
        } catch (error) {
          console.error("Error saving job view data:", error);
        }
      }
    }
  } catch (error) {
    console.error("Error fetching job posting detail:", error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchJobPostingDetail();

  // const entrprsSq = jbp.value.entrprsSq;
  // console.log("entrprsSqqqqqq", entrprsSq);
  // console.log("qqqq", store.getters.getMember?.pk);
  // console.log("www", jbp.value.entrprsSq);
  // console.log("asd : ", jobId);
});

const goUpdatePage = (jbp) => {
  // console.log(jbp.jbpSq);
  isEditable.value = false;
  router.push(`/board/jobPostingUpdate/${jbp.jbpSq}`);
};

const confirmDelete = () => {
  if (window.confirm("게시글을 삭제 하시겠습니까?")) {
    deleteBoard();
    alert("게시글이 삭제되었습니다.");
  }
};

const deleteBoard = async () => {
  try {
    await api.$delete(`/board/jobPostingDelete/${jbp.value.jbpSq}`);
    console.log("게시글이 삭제 되었습니다");
    router.push("/board/list/jobPosting");
  } catch (error) {
    console.error("게시글 삭제가 실패 되었습니다:", error);
  }
};

// alert창에서 이력서 순번을 넣어야 해서 닫아녾음
const applyJob = () => {
  alert("해당 기능은 수정중 입니다.");
  return;
  /* const resumeId = prompt('이력서 번호를 입력하세요:');
  if (resumeId) {
    insertApply(resumeId);
  } */
};

/* const insertApply = async (resumeId) => {
  const applyData = {
    resume: { rsmSq: resumeId }, 
    jobPosting: { jbpSq: jbp.value.jbpSq },
    apyDtm: new Date().toISOString()
  };

  try {
    const response = await api.$post('/apply/insert', applyData);
    alert(response); 
  } catch (error) {
    console.error('Error applying job:', error);
    alert('입사지원을 처리하는 중 오류가 발생했습니다.');
  }
}; */

// 학력 매핑 테이블
const educationMapping = {
  ednm: "학력무관",
  hs: "고등학교 이상",
  jc: "대학교(2,3년제) 이상",
  unvrsty: "대학교(4년제) 이상",
  mid: "석사",
  did: "박사",
};

// 경력 매핑 테이블
const careerMapping = {
  enr: "경력무관",
  exprncd: "신입",
  nwcmr: "경력",
};

// 학력 텍스트 반환 함수
const getEducationText = (value) => {
  return educationMapping[value] || value;
};

// 경력 텍스트 반환 함수
const getCareerText = (value) => {
  return careerMapping[value] || value;
};

const educationText = computed(() => getEducationText(jbp.value.edctn));
const careerText = computed(() => getCareerText(jbp.value.crrDrtn));
</script>

<style scoped>
/* 추가적인 스타일 정의 */
</style>
