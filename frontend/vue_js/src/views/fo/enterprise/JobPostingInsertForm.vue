<template>
  <section id="examples" class="section section-default">
    <div class="container">
      <section class="card card-admin">
        <header class="card-header">
          <h2 class="card-title">공고 등록</h2>
        </header>
      </section>

      <div class="mb-3">
        <label for="jbpTtl" class="form-label">제목</label>
        <input type="text" v-model="jbpTtl" class="form-control" id="jbpTtl" placeholder="제목을 입력하세요">
      </div>

      <div class="row">
        <div class="col-md-3 mb-3">
          <label for="jobName" class="form-label">모집 직군</label>
          <div id="jobName" class="form-control" style="height: auto;">
            <div v-for="job in jobs" :key="job.jobSq" class="form-check">
              <input type="checkbox" v-model="selectedJobs" :value="job" class="form-check-input"
                :id="'job-' + job.jobSq">
              <label class="form-check-label" :for="'job-' + job.jobSq">{{ job.jobScName }}</label>
            </div>
          </div>
        </div>
        <div class="col-md-3 mb-3">
          <label for="workArea" class="form-label">근무지역</label>
          <div id="workArea" class="form-control" style="height: auto;">
            <div v-for="area in areas" :key="area.areaSq" class="form-check">
              <input type="checkbox" v-model="selectedWorkAreas" :value="area" class="form-check-input"
                :id="'area-' + area.areaSq">
              <label class="form-check-label" :for="'area-' + area.areaSq">{{ area.areaName }}</label>
            </div>
          </div>
        </div>
        <!-- <div class="col-md-3 mb-3">
          <label for="cr" class="form-label">경력</label>
          <select v-model="cr" class="form-control" id="cr">
            <option value="801">경력무관</option>
            <option value="802">신입</option>
            <option value="803">경력</option>
          </select>
        </div>
        <div class="col-md-3 mb-3">
          <label for="edctn" class="form-label">학력</label>
          <select v-model="edctn" class="form-control" id="edctn">
            <option value="606">학력무관</option>
            <option value="605">고등학교 이상</option>
            <option value="604">대학교(2,3년제) 이상</option>
            <option value="603">대학교(4년제) 이상</option>
            <option value="602">석사</option>
            <option value="601">박사</option>
          </select>
        </div> -->
        <div class="col-md-3 mb-3">
          <label for="sklName" class="form-label">요구 스킬</label>
          <input type="text" v-model="sklName" class="form-control" id="sklName" placeholder="기술명을 입력하세요">
        </div>
      </div>

      <div class="row">
        <div class="col-md-3 mb-3">
          <label for="crrDrtn" class="form-label">경력</label>
          <select v-model="crrDrtn" class="form-control" id="crrDrtn">
            <option value="enr">경력무관</option>
            <option value="nwcmr">신입</option>
            <option value="exprncd">경력</option>
          </select>
        </div>
        <div class="col-md-3 mb-3">
          <label for="edctn" class="form-label">학력</label>
          <select v-model="edctn" class="form-control" id="edctn">
            <option value="ednm">학력무관</option>
            <option value="hs">고등학교 이상</option>
            <option value="jc">대학교(2,3년제) 이상</option>
            <option value="unvrsty">대학교(4년제) 이상</option>
            <option value="mid">석사</option>
            <option value="did">박사</option>
          </select>
        </div>
        <!-- <div class="col-md-3 mb-3">
          <label for="workArea" class="form-label">근무지역</label>
          <div id="workArea" class="form-control" style="height: auto;">
            <div v-for="area in areas" :key="area.areaSq" class="form-check">
              <input type="checkbox" v-model="selectedWorkAreas" :value="area" class="form-check-input" :id="'area-' + area.areaSq">
              <label class="form-check-label" :for="'area-' + area.areaSq">{{ area.areaName }}</label>
            </div>
          </div>
        </div> -->
        <div class="col-md-2 mb-3">
          <label for="workForm" class="form-label">근무형태</label>
          <select v-model="workForm" class="form-control" id="workForm">
            <option value="정규직">정규직</option>
            <option value="계약직">계약직</option>
            <option value="인턴">인턴</option>
          </select>
        </div>
        <div class="col-md-3 mb-3">
          <label class="form-label">급여</label>
          <div class="input-group">
            <input type="text" v-model="slry" class="form-control" id="slry" placeholder="연봉"
              :disabled="interviewAgreement">
            <div class="input-group-text">
              <input type="checkbox" v-model="interviewAgreement"> 면접 후 협의
            </div>
          </div>
        </div>
        <div class="col-md-2 mb-3">
          <label for="workStartTime" class="form-label">출근 시간</label>
          <input type="time" v-model="workStartTime" class="form-control" id="workStartTime">
        </div>
        <div class="col-md-2 mb-3">
          <label for="workEndTime" class="form-label">퇴근 시간</label>
          <input type="time" v-model="workEndTime" class="form-control" id="workEndTime">
        </div>
      </div>

      <div class="mb-3">
        <label for="jbpCntnt" class="form-label">내용</label>
        <QuillEditorComponent v-model="jbpCntnt" />
      </div>
      <div class="row">
        <div class="col-md-6 mb-3">
          <label for="regstrStrtDtm" class="form-label">공고 시작일</label>
          <input type="datetime-local" v-model="regstrStrtDtm" class="form-control" id="regstrStrtDtm" :min="minRegstrStrtDtm" >
        </div>
        <div class="col-md-6 mb-3">
          <label for="regstrDlnDtm" class="form-label">공고 마감일</label>
          <input type="datetime-local" v-model="regstrDlnDtm" class="form-control" id="regstrDlnDtm" :min="minRegstrStrtDtm">
        </div>
      </div>

      <div class="row">
        <div class="col-md-4 mb-3">
          <label for="picName" class="form-label">담당자 이름</label>
          <input type="text" v-model="picName" class="form-control" id="picName" placeholder="담당자 이름을 입력하세요">
        </div>
        <div class="col-md-4 mb-3">
          <label for="picMp" class="form-label">담당자 연락처</label>
          <input type="text" v-model="picMp" class="form-control" id="picMp" placeholder="담당자 연락처를 입력하세요">
        </div>
        <div class="col-md-4 mb-3">
          <label for="picEml" class="form-label">담당자 이메일</label>
          <input type="email" v-model="picEml" class="form-control" id="picEml" placeholder="담당자 이메일을 입력하세요">
        </div>
      </div>

      <div>
        <button @click="submitPost">작성</button>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { api } from '@/axios.js';
import { useRouter } from 'vue-router';
import QuillEditorComponent from '@/components/common/Editor.vue';
import store from '../../../store';
import { showAlert } from '../../../utill/utillModal';

const areas = ref([]);
const jobs = ref([]);
const selectedWorkAreas = ref([]);
const selectedJobs = ref([]);


const jbpSq = ref(0);
const entrprsSq = store.getters.getMember.pk;
const jbpTtl = ref('');
const jbpCntnt = ref('');
const crrDrtn = ref(801);
const sklName = ref('');
const edctn = ref(606);
const workForm = ref('정규직');
const slry = ref('');
const workStartTime = ref('');
const workEndTime = ref('');
const regstrStrtDtm = ref('');
const regstrDlnDtm = ref('');
const picName = ref('');
const picMp = ref('');
const picEml = ref('');
const jbpEndYn = ref('N');
const jbpCndtn = ref('');
const insrtMbrSq = ref(1);

const interviewAgreement = ref(false);
const router = useRouter();

const minRegstrStrtDtm = computed(() => {
      const today = new Date();
      const year = today.getFullYear();
      const month = String(today.getMonth() + 1).padStart(2, '0');
      const day = String(today.getDate()).padStart(2, '0');
      const hours = String(today.getHours()).padStart(2, '0');
      const minutes = String(today.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day}T${hours}:${minutes}`; // YYYY-MM-DDTHH:MM 형식
    });

const fetchAreasAndJobs = async () => {
  try {
    const areasResponse = await api.$get('/areas');
    areas.value = areasResponse;
    console.log(areasResponse);

    const jobsResponse = await api.$get('/jobs');
    jobs.value = jobsResponse;
    console.log(jobsResponse);
  } catch (error) {
    console.error('데이터를 가져오는 중 오류가 발생했습니다.', error);
  }
};

onMounted(() => {
  console.log('Component mounted');
  fetchAreasAndJobs();
});

const submitPost = () => {
  if (jbpTtl.value.trim() === '' || jbpCntnt.value.trim() === '') {
    alert('제목과 내용을 확인하세요.');
    return;
  }

  const jobNames = selectedJobs.value.map(job => job.jobScName);
  const areaNames = selectedWorkAreas.value.map(area => area.areaName);

  console.log("전송할 데이터:", {
  jbpSq: jbpSq.value,
  enterpriseMember: {
    entrprsSq: entrprsSq,
  },
  jbpTtl: jbpTtl.value,
  jbpCntnt: jbpCntnt.value,
  crrDrtn: crrDrtn.value,
  sklName: sklName.value,
  jobName: jobNames,
  edctn: edctn.value,
  workArea: areaNames,
  workForm: workForm.value,
  slry: interviewAgreement.value ? '면접 후 협의' : `${slry.value} 만원`,
  workHour: `${workStartTime.value} ~ ${workEndTime.value}`,
  regstrStrtDtm: regstrStrtDtm.value.split('T')[0],
  regstrDlnDtm: regstrDlnDtm.value.split('T')[0],
  picName: picName.value,
  picMp: picMp.value,
  picEml: picEml.value,
  jbpEndYn: jbpEndYn.value,
  jbpCndtn: jbpCndtn.value,
  insrtMbrSq: insrtMbrSq.value,
});

  api.$post('/board/jobPostingInsert', {
    jbpSq: jbpSq.value,
    enterpriseMember: {
      entrprsSq: entrprsSq,
    },
    jbpTtl: jbpTtl.value,
    jbpCntnt: jbpCntnt.value,
    crrDrtn: crrDrtn.value,
    sklName: sklName.value,
    jobName: jobNames,
    edctn: edctn.value,
    workArea: areaNames,
    workForm: workForm.value,
    slry: interviewAgreement.value ? '면접 후 협의' : slry.value + " 만원",
    workHour: `${workStartTime.value} ~ ${workEndTime.value}`,
    regstrStrtDtm: regstrStrtDtm.value.split('T')[0],
    regstrDlnDtm: regstrDlnDtm.value.split('T')[0],
    picName: picName.value,
    picMp: picMp.value,
    picEml: picEml.value,
    jbpEndYn: jbpEndYn.value,
    jbpCndtn: jbpCndtn.value,
    insrtMbrSq: insrtMbrSq.value,
  })
    .then(response => {
      console.log("bbbbbb");
      const { detailUrl, jbpSq: insertedJbpSq } = response;
      jbpSq.value = insertedJbpSq;
      console.log("글 번호: " + jbpSq.value);
      showAlert('공고가 등록되었습니다.');
      clearForm();
      router.push(detailUrl);
    })
    .catch(error => {
      console.error('게시물 작성 오류:', error);
      alert('게시물 작성 중 오류가 발생했습니다. 다시 시도해주세요.');
    });
};

const clearForm = () => {
  jbpTtl.value = '';
  jbpCntnt.value = '';
  crrDrtn.value = 801;
  sklName.value = '';
  edctn.value = "606";
  workForm.value = '정규직';
  slry.value = '';
  workStartTime.value = '';
  workEndTime.value = '';
  regstrStrtDtm.value = '';
  regstrDlnDtm.value = '';
  picName.value = '';
  picMp.value = '';
  picEml.value = '';
  jbpEndYn.value = 'N';
  jbpCndtn.value = '';
  insrtMbrSq.value = 1;
  interviewAgreement.value = false;
  selectedJobs.value = [];
  selectedWorkAreas.value = [];
};
</script>

<style>
.form-control {
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  max-height: 200px;
  /* 드롭다운의 최대 높이 설정 */
  overflow-y: auto;
  /* 세로 스크롤 처리 */
}

/* Quill 에디터 스타일 수정 */
.quill-editor .ql-container {
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  height: 200px;
}

.quill-editor .ql-editor {
  min-height: 200px;
  max-height: 400px;
  /* 에디터의 최대 높이 설정 */
  overflow-y: auto;
  /* 세로 스크롤 처리 */
}
</style>
